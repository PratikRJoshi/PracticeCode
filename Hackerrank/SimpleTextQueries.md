# Simple Text Queries ([HackerRank Problem Link](https://www.hackerrank.com/challenges/simple-text-queries/problem))

## 1) Problem Description

You are given:
- `sentences`: an array of sentence strings (space-separated words)
- `queries`: an array of query strings (space-separated words)

For each query, return all sentence indices where **all words from the query are present**.

Rules:
- Matching is **exact** and **case-sensitive**.
- If no sentence satisfies a query, return `[-1]` for that query.
- If a sentence can satisfy the query multiple times (because of repeated words and frequencies), include that sentence index multiple times.

Example:

```text
sentences = [
  "sam and jordan like to text each other",
  "sam does not like to ski but does not like to fall",
  "Jordan likes to ski"
]

queries = ["sam jordan", "jordan", "like", "non occurrence"]
```

Output:

```text
[[0], [0], [0, 1, 1], [-1]]
```

Why:
- `"sam jordan"` -> only sentence `0`
- `"jordan"` -> only sentence `0` (`Jordan` in sentence `2` does not match)
- `"like"` -> sentence `0` once, sentence `1` twice
- `"non occurrence"` -> no match

## 2) Intuition/Main Idea

The slow way is: for each query, scan every sentence and count words each time. That repeats work and is expensive.

The better way is to preprocess once using an inverted index.

### Step-by-step intuition build-up

1. If a query asks for a word, we should instantly know which sentences contain it.
2. So store: `word -> {sentenceIndex -> frequencyInSentence}`.
3. For each query, first build `requiredFrequency` for query words.
4. A sentence is valid only if it contains every query word with enough frequency.
5. If a sentence has extra frequency, it can contribute multiple copies of its index. The number of copies is:

```text
min( have[word] / need[word] ) across all words in query
```

### Why this intuition works

- Query validity requires satisfying **all** words, so taking minimum capacity across words is correct.
- Frequency division captures repeated words in query (for example, query: `"a a b"`).
- Using an inverted index avoids repeatedly parsing all sentences for each query.

### How to derive it in interviews

- Start with brute force (query x sentence x word count).
- Notice repeated work: sentence word counting is recomputed many times.
- Move repeated computation into preprocessing (index).
- Then each query becomes a targeted lookup + intersection/verification.

## 3) Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
| --- | --- |
| @ExactAndCaseSensitiveMatch | `index` keys use original tokens directly; no lowercase transformation in preprocessing/query parsing |
| @AllQueryWordsMustExist | `evaluateSingleQuery`: loop over `queryNeed.entrySet()` ensures every word exists in candidate sentence |
| @RepeatedWordHandling | `queryNeed.merge(word, 1, Integer::sum)` stores required frequency per query word |
| @RepeatedIndexOutput | `maxCopies = Math.min(maxCopies, available / required)` then append index `maxCopies` times |
| @ReturnMinusOneWhenNoMatch | caller adds `Collections.singletonList(-1)` when query result is empty |

## 4) Final Java Code & Learning Pattern (Full Content)

```java
import java.util.*;

class Result {

    public static List<List<Integer>> textQueries(List<String> sentences, List<String> queries) {
        // Preprocessing pattern:
        // Build an inverted index so each query can be resolved by direct lookups
        // instead of scanning every sentence repeatedly.
        Map<String, Map<Integer, Integer>> wordToSentenceFrequencyMap = new HashMap<>();

        for (int sentenceIndex = 0; sentenceIndex < sentences.size(); sentenceIndex++) {
            String[] sentenceWords = sentences.get(sentenceIndex).split(" ");

            for (String sentenceWord : sentenceWords) {
                wordToSentenceFrequencyMap
                        .computeIfAbsent(sentenceWord, key -> new HashMap<>())
                        .merge(sentenceIndex, 1, Integer::sum);
            }
        }

        List<List<Integer>> finalAnswer = new ArrayList<>();

        for (String query : queries) {
            Map<String, Integer> queryWordRequiredFrequencyMap = new HashMap<>();
            String[] queryWords = query.split(" ");

            for (String queryWord : queryWords) {
                // Learning pattern:
                // Count required frequency of each query word once.
                queryWordRequiredFrequencyMap.merge(queryWord, 1, Integer::sum);
            }

            List<Integer> currentQueryResult = evaluateSingleQuery(
                    wordToSentenceFrequencyMap,
                    queryWordRequiredFrequencyMap
            );

            if (currentQueryResult.isEmpty()) {
                finalAnswer.add(Collections.singletonList(-1));
            } else {
                finalAnswer.add(currentQueryResult);
            }
        }

        return finalAnswer;
    }

    private static List<Integer> evaluateSingleQuery(
            Map<String, Map<Integer, Integer>> wordToSentenceFrequencyMap,
            Map<String, Integer> queryWordRequiredFrequencyMap
    ) {
        // Quick fail:
        // If any query word does not exist in index at all, answer is empty.
        for (String queryWord : queryWordRequiredFrequencyMap.keySet()) {
            if (!wordToSentenceFrequencyMap.containsKey(queryWord)) {
                return Collections.emptyList();
            }
        }

        // Optimization pattern:
        // Pick the rarest query word as candidate source to reduce checks.
        String seedQueryWord = null;
        int minimumCandidateSentenceCount = Integer.MAX_VALUE;

        for (String queryWord : queryWordRequiredFrequencyMap.keySet()) {
            int candidateCount = wordToSentenceFrequencyMap.get(queryWord).size();
            if (candidateCount < minimumCandidateSentenceCount) {
                minimumCandidateSentenceCount = candidateCount;
                seedQueryWord = queryWord;
            }
        }

        List<Integer> resolvedIndices = new ArrayList<>();
        Map<Integer, Integer> seedSentenceFrequencyMap = wordToSentenceFrequencyMap.get(seedQueryWord);

        for (int sentenceIndex : seedSentenceFrequencyMap.keySet()) {
            boolean sentenceSatisfiesAllWords = true;
            int maxCopies = Integer.MAX_VALUE;

            for (Map.Entry<String, Integer> queryRequirement : queryWordRequiredFrequencyMap.entrySet()) {
                String queryWord = queryRequirement.getKey();
                int required = queryRequirement.getValue();

                Integer available = wordToSentenceFrequencyMap.get(queryWord).get(sentenceIndex);
                if (available == null || available < required) {
                    sentenceSatisfiesAllWords = false;
                    break;
                }

                // Core frequency logic:
                // how many times can this sentence satisfy this query word
                // while respecting required multiplicity.
                maxCopies = Math.min(maxCopies, available / required);
            }

            if (sentenceSatisfiesAllWords) {
                for (int count = 0; count < maxCopies; count++) {
                    resolvedIndices.add(sentenceIndex);
                }
            }
        }

        Collections.sort(resolvedIndices);
        return resolvedIndices;
    }
}
```

Learning Pattern to reuse:
- If many queries ask about overlap/containment on text tokens, prefer inverted index preprocessing.
- If query contains multiplicity (same token repeated), track frequency maps for both source and demand.
- If output needs repeated answers, convert capacity checks into `min(available / required)` logic.

## 5) Complexity Analysis

- Let `S` be total number of words across all sentences.
- Let `Uq` be number of unique words in one query.

Time Complexity:
- Preprocessing index: `O(S)`
- Per query: `O(Uq * C)` where `C` is candidate sentence count from seed word (small in this problem due to constraints)

Space Complexity:
- Inverted index storage: `O(S)`
- Per-query frequency map: `O(Uq)`

## Similar Problems

- No close LeetCode problem has exactly the same output rule (repeated sentence index by frequency capacity + exact case-sensitive token matching over sentence/query arrays).
- Closest pattern to practice is inverted-index based lookup and multi-condition intersection, which commonly appears in information retrieval style problems rather than a direct canonical LeetCode ID.