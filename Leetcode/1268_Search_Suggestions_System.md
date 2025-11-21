# Search Suggestions System

## Problem Description

**Problem Link:** [Search Suggestions System](https://leetcode.com/problems/search-suggestions-system/)

You are given an array of strings `products` and a string `searchWord`.

Design a system that suggests at most three product names from `products` after each character of `searchWord` is typed. Suggested products should have common prefix with `searchWord`. If there are more than three products with a common prefix return the three lexicographically smallest products.

Return *a list of lists of the suggested products after each character of* `searchWord` *is typed*.

**Example 1:**
```
Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
Output: [
["mobile","moneypot","monitor"],
["mobile","moneypot","monitor"],
["mouse","mousepad"],
["mouse","mousepad"],
["mouse","mousepad"]
]
Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"]
After typing m and mo, products and filter system shows the three lexicographically smallest matching products "mobile", "moneypot" and "monitor".
After typing mou, mous and mouse the system shows "mouse" and "mousepad".
```

**Constraints:**
- `1 <= products.length <= 1000`
- `1 <= products[i].length <= 3000`
- `1 <= sum(products[i].length) <= 2 * 10^4`
- All the strings of `products` are **unique**.
- `products[i]` consists of lowercase English letters.
- `1 <= searchWord.length <= 1000`
- `searchWord` consists of lowercase English letters.

## Intuition/Main Idea

We need to find products with common prefix and return top 3 lexicographically smallest.

**Core Algorithm:**
- Sort products lexicographically
- For each prefix of searchWord, find products starting with that prefix
- Return first 3 matches

**Why sorting:** Sorting allows us to get lexicographically smallest products easily. We can use binary search or Trie for efficient prefix matching.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Sort products | Arrays.sort - Line 6 |
| Find prefix matches | Prefix check - Lines 10-15 |
| Return top 3 | Limit to 3 - Line 13 |
| Build result per character | Loop through searchWord - Lines 8-18 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        // Sort products lexicographically
        Arrays.sort(products);
        
        List<List<String>> result = new ArrayList<>();
        String prefix = "";
        
        // For each character in searchWord
        for (char ch : searchWord.toCharArray()) {
            prefix += ch;
            List<String> suggestions = new ArrayList<>();
            
            // Find products with current prefix
            for (String product : products) {
                // Check if product starts with current prefix
                if (product.startsWith(prefix)) {
                    suggestions.add(product);
                    
                    // Only need top 3 lexicographically smallest
                    // Since products are sorted, first 3 matches are the smallest
                    if (suggestions.size() == 3) {
                        break;
                    }
                }
            }
            
            result.add(suggestions);
        }
        
        return result;
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(n \log n + m * n)$ where $n$ is products length and $m$ is searchWord length. Sorting takes $O(n \log n)$, and for each prefix we scan products.

**Space Complexity:** $O(1)$ excluding output. We use $O(m)$ for result list.

## Similar Problems

- [Implement Trie (Prefix Tree)](https://leetcode.com/problems/implement-trie-prefix-tree/) - Trie data structure
- [Add and Search Word](https://leetcode.com/problems/add-and-search-word-data-structure-design/) - Trie with wildcard
- [Longest Common Prefix](https://leetcode.com/problems/longest-common-prefix/) - Find common prefix

