"""
Generate an Anki deck (.apkg) for LeetCode revision from lc-by-order-of-difficulty.md.

Three card types per problem:
  A. Problem -> Approach        (recall the algorithm)
  B. Problem -> Pattern         (recall the technique)
  C. Pattern -> Problems        (one card per unique pattern; recognize when to use it)

Dependencies:
  pip install genanki pygments      # pygments is optional (syntax highlighting)

Run:
  cd ~/Documents/Learning/PracticeCode
  .venv/bin/python Leetcode/anki_generator.py

Output: Leetcode/leetcode-revision.apkg

Re-import tips (Anki Desktop -> Import File):
  - "Import any learning progress" = OFF  (preserves your review history)
  - "Update notes" = Always               (forces new code/content in; If newer
                                            can skip updates when mod times tie)
"""

from __future__ import annotations

import re
import time
from collections import defaultdict
from dataclasses import dataclass, field
from pathlib import Path

import genanki

# Optional syntax highlighting. Falls back to plain escaped code if unavailable.
try:
    from pygments import highlight as _pyg_highlight
    from pygments.formatters import HtmlFormatter as _HtmlFormatter
    from pygments.lexers import JavaLexer as _JavaLexer

    _PYG_LEXER = _JavaLexer()
    # noclasses=True inlines all colors (works offline in AnkiDroid, no external CSS/JS);
    # nowrap=True emits only the colored spans so we control the surrounding <pre>.
    _PYG_FORMATTER = _HtmlFormatter(style="monokai", noclasses=True, nowrap=True)
except Exception:  # pragma: no cover - pygments not installed
    _pyg_highlight = None

SOURCE = Path(__file__).parent / "lc-by-order-of-difficulty.md"
OUTPUT = Path(__file__).parent / "leetcode-revision.apkg"

# Stable random IDs (do NOT change between runs or Anki will treat as new decks/models)
DECK_ID = 1928374651
MODEL_PROBLEM_APPROACH_ID = 1928374652
MODEL_PROBLEM_PATTERN_ID = 1928374653
MODEL_PATTERN_PROBLEMS_ID = 1928374654


@dataclass
class Problem:
    number: int
    name: str
    url: str
    difficulty: str = ""
    pattern: str = ""
    concepts: list[str] = field(default_factory=list)


HEADER_RE = re.compile(r"^### (\d+)\. \[(.+?)\]\((.+?)\)\s*$")
DIFF_RE = re.compile(r"^\*\*Difficulty:\*\*\s*(.+?)\s*$")
PATTERN_RE = re.compile(r"^\*\*Pattern:\*\*\s*(.+?)\s*$")
BULLET_RE = re.compile(r"^- (.+)$")


def parse_problems(text: str) -> list[Problem]:
    lines = text.splitlines()
    problems: list[Problem] = []
    current: Problem | None = None
    in_concepts = False

    for line in lines:
        if line.startswith("## Key Patterns Learned"):
            if current:
                problems.append(current)
                current = None
            break

        header_match = HEADER_RE.match(line)
        if header_match:
            if current:
                problems.append(current)
            current = Problem(
                number=int(header_match.group(1)),
                name=header_match.group(2),
                url=header_match.group(3),
            )
            in_concepts = False
            continue

        if current is None:
            continue

        diff_match = DIFF_RE.match(line)
        if diff_match:
            current.difficulty = diff_match.group(1)
            continue

        pattern_match = PATTERN_RE.match(line)
        if pattern_match:
            current.pattern = pattern_match.group(1)
            continue

        if line.startswith("**Key Concepts:**"):
            in_concepts = True
            continue

        if in_concepts:
            bullet_match = BULLET_RE.match(line)
            if bullet_match:
                current.concepts.append(bullet_match.group(1).strip())
            elif line.strip() == "" or line.startswith("---") or line.startswith("###"):
                in_concepts = False

    if current:
        problems.append(current)

    return problems


def html_concepts(concepts: list[str]) -> str:
    if not concepts:
        return ""
    items = "".join(f"<li>{escape(c)}</li>" for c in concepts)
    return f"<ul>{items}</ul>"


def escape(s: str) -> str:
    return (
        s.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
    )


def normalize(s: str) -> str:
    """Lowercase and strip everything except alphanumerics, for fuzzy matching."""
    return re.sub(r"[^a-z0-9]", "", s.lower())


def slug_of(url: str) -> str:
    match = re.search(r"/problems/([^/]+)/?", url)
    return match.group(1) if match else ""


SOLUTION_FILE_RE = re.compile(r"^(\d+)_(.+)\.md$")
JAVA_BLOCK_RE = re.compile(r"```java\s*\n(.*?)```", re.DOTALL)


def build_solution_index() -> dict[str, Path]:
    """Map normalized problem title -> solution file path (files named <num>_<Title>.md)."""
    index: dict[str, Path] = {}
    # Search recursively so solution files in subfolders (e.g. contest/) are found too.
    for path in SOURCE.parent.rglob("*.md"):
        match = SOLUTION_FILE_RE.match(path.name)
        if match:
            index.setdefault(normalize(match.group(2)), path)
    return index


SOLUTION_INDEX = build_solution_index()


def highlight_java(code: str) -> str:
    """Syntax-highlight Java as inline-styled HTML, or plain escaped code as fallback."""
    if _pyg_highlight is None:
        return escape(code)
    return _pyg_highlight(code, _PYG_LEXER, _PYG_FORMATTER).rstrip("\n")


def matching_solution_path(name: str, url: str) -> Path | None:
    """Locate the solution file for a problem by normalized title or URL slug."""
    return SOLUTION_INDEX.get(normalize(name)) or SOLUTION_INDEX.get(normalize(slug_of(url)))


def real_lc_number(name: str, url: str) -> int | None:
    """Real LeetCode number, parsed from the matching solution filename (<num>_Title.md)."""
    path = matching_solution_path(name, url)
    if not path:
        return None
    match = SOLUTION_FILE_RE.match(path.name)
    return int(match.group(1)) if match else None


def find_code_html(name: str, url: str) -> str:
    """Return the first Java code block of the matching solution file as HTML, or ''."""
    path = matching_solution_path(name, url)
    if not path:
        return ""
    block = JAVA_BLOCK_RE.search(path.read_text())
    if not block:
        return ""
    code = block.group(1).strip("\n")
    return f'<pre class="code-block"><code>{highlight_java(code)}</code></pre>'


CARD_CSS = """
.card {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
  font-size: 16px;
  color: #1a1a1a;
  background: #fafafa;
  padding: 24px;
  line-height: 1.5;
}
.front-title { font-size: 20px; font-weight: 600; margin-bottom: 8px; }
.meta { color: #666; font-size: 13px; margin-bottom: 16px; }
.difficulty-Easy { color: #00af9b; }
.difficulty-Medium { color: #ffa116; }
.difficulty-Hard { color: #ff375f; }
.section-label { font-weight: 600; margin-top: 14px; color: #444; }
ul { margin: 6px 0 0 20px; padding: 0; }
li { margin-bottom: 4px; }
code { background: #eee; padding: 1px 5px; border-radius: 3px; font-size: 14px; }
a { color: #0a66c2; text-decoration: none; }
hr { border: 0; border-top: 1px solid #ddd; margin: 16px 0; }
pre.code-block {
  background: #272822;
  color: #f8f8f2;
  padding: 14px 16px;
  border-radius: 6px;
  overflow-x: auto;
  font-size: 13px;
  line-height: 1.45;
  text-align: left;
  margin: 6px 0 0 0;
}
pre.code-block code {
  background: none;
  padding: 0;
  font-family: "SF Mono", Menlo, Consolas, monospace;
  white-space: pre;
}
"""


# ---- Model A: Problem -> Approach ----
model_problem_approach = genanki.Model(
    MODEL_PROBLEM_APPROACH_ID,
    "LC Problem -> Approach",
    fields=[
        {"name": "ProblemId"},
        {"name": "Title"},
        {"name": "Url"},
        {"name": "Difficulty"},
        {"name": "Pattern"},
        {"name": "Concepts"},
        {"name": "Code"},
    ],
    templates=[
        {
            "name": "Approach",
            "qfmt": (
                '<div class="front-title">{{Title}}</div>'
                '<div class="meta">'
                'LeetCode {{ProblemId}} &middot; '
                '<span class="difficulty-{{Difficulty}}">{{Difficulty}}</span>'
                "</div>"
                '<div class="section-label">Recall the approach.</div>'
            ),
            "afmt": (
                "{{FrontSide}}<hr>"
                '<div class="section-label">Pattern</div>{{Pattern}}'
                '<div class="section-label">Key Concepts</div>{{Concepts}}'
                "{{#Code}}"
                '<div class="section-label">Solution</div>{{Code}}'
                "{{/Code}}"
                '<div class="meta" style="margin-top:18px;">'
                '<a href="{{Url}}">Open on LeetCode</a></div>'
            ),
        }
    ],
    css=CARD_CSS,
)

# ---- Model B: Problem -> Pattern ----
model_problem_pattern = genanki.Model(
    MODEL_PROBLEM_PATTERN_ID,
    "LC Problem -> Pattern",
    fields=[
        {"name": "ProblemId"},
        {"name": "Title"},
        {"name": "Url"},
        {"name": "Difficulty"},
        {"name": "Pattern"},
    ],
    templates=[
        {
            "name": "Pattern",
            "qfmt": (
                '<div class="front-title">{{Title}}</div>'
                '<div class="meta">'
                'LeetCode {{ProblemId}} &middot; '
                '<span class="difficulty-{{Difficulty}}">{{Difficulty}}</span>'
                "</div>"
                '<div class="section-label">Which pattern does this problem use?</div>'
            ),
            "afmt": (
                "{{FrontSide}}<hr>"
                '<div class="section-label">Pattern</div>{{Pattern}}'
                '<div class="meta" style="margin-top:18px;">'
                '<a href="{{Url}}">Open on LeetCode</a></div>'
            ),
        }
    ],
    css=CARD_CSS,
)

# ---- Model C: Pattern -> Problems ----
model_pattern_problems = genanki.Model(
    MODEL_PATTERN_PROBLEMS_ID,
    "LC Pattern -> Problems",
    fields=[
        {"name": "Pattern"},
        {"name": "ProblemList"},
    ],
    templates=[
        {
            "name": "PatternList",
            "qfmt": (
                '<div class="section-label">When should you reach for this pattern?</div>'
                '<div class="front-title" style="margin-top:8px;">{{Pattern}}</div>'
            ),
            "afmt": (
                "{{FrontSide}}<hr>"
                '<div class="section-label">Problems using this pattern</div>'
                "{{ProblemList}}"
            ),
        }
    ],
    css=CARD_CSS,
)


def slug(text: str) -> str:
    return re.sub(r"[^a-z0-9]+", "-", text.lower()).strip("-")


def build_deck(problems: list[Problem]) -> genanki.Deck:
    deck = genanki.Deck(DECK_ID, "LeetCode Revision")

    pattern_to_problems: dict[str, list[Problem]] = defaultdict(list)

    for p in problems:
        concepts_html = html_concepts(p.concepts)
        code_html = find_code_html(p.name, p.url)

        # Display the real LeetCode number when known; fall back to tracker order.
        lc_number = real_lc_number(p.name, p.url)
        display_id = str(lc_number) if lc_number is not None else str(p.number)
        lc_tag = f"lc-{lc_number}" if lc_number is not None else f"lc-{p.number}"

        # GUIDs stay tracker-based and stable so re-running updates (not duplicates) cards.
        guid_approach = genanki.guid_for(f"lc-{p.number}-approach")
        guid_pattern = genanki.guid_for(f"lc-{p.number}-pattern")

        deck.add_note(
            genanki.Note(
                model=model_problem_approach,
                fields=[
                    display_id,
                    escape(p.name),
                    p.url,
                    escape(p.difficulty),
                    escape(p.pattern),
                    concepts_html,
                    code_html,
                ],
                guid=guid_approach,
                tags=[slug(p.pattern), lc_tag, slug(p.difficulty)],
            )
        )

        deck.add_note(
            genanki.Note(
                model=model_problem_pattern,
                fields=[
                    display_id,
                    escape(p.name),
                    p.url,
                    escape(p.difficulty),
                    escape(p.pattern),
                ],
                guid=guid_pattern,
                tags=[slug(p.pattern), lc_tag, slug(p.difficulty)],
            )
        )

        if p.pattern:
            pattern_to_problems[p.pattern].append(p)

    for pattern, items in pattern_to_problems.items():
        list_html = "<ul>" + "".join(
            f'<li><a href="{p.url}">{escape(p.name)}</a> '
            f'<span class="meta">(LC {real_lc_number(p.name, p.url) or p.number}, {escape(p.difficulty)})</span></li>'
            for p in sorted(items, key=lambda x: x.number)
        ) + "</ul>"

        deck.add_note(
            genanki.Note(
                model=model_pattern_problems,
                fields=[escape(pattern), list_html],
                guid=genanki.guid_for(f"pattern-{slug(pattern)}"),
                tags=[slug(pattern), "pattern-card"],
            )
        )

    return deck


def main() -> None:
    text = SOURCE.read_text()
    problems = parse_problems(text)
    print(f"Parsed {len(problems)} problems")
    if not problems:
        raise SystemExit("No problems parsed — check source format")

    deck = build_deck(problems)
    package = genanki.Package(deck)
    # Stamp every generated note/card with the current build time so Anki's
    # "Update notes: If newer" sees each fresh export as newer than what's
    # already in your collection. If a re-import still shows stale content,
    # use "Update notes: Always" in the import dialog (timestamps can tie).
    build_ts = time.time()
    package.write_to_file(str(OUTPUT), timestamp=build_ts)

    pattern_count = len({p.pattern for p in problems if p.pattern})
    total_cards = len(problems) * 2 + pattern_count
    with_code = sum(1 for p in problems if find_code_html(p.name, p.url))
    print(f"Wrote {OUTPUT}")
    print(f"  {len(problems)} problems x 2 cards + {pattern_count} pattern cards "
          f"= {total_cards} cards")
    print(f"  {with_code}/{len(problems)} approach cards include solution code")
    print(f"  note mod timestamp: {int(build_ts)} "
          f"(re-import with 'Update notes: Always' if old content persists)")


if __name__ == "__main__":
    main()
