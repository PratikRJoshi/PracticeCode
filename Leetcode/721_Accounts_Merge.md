# Accounts Merge

## Problem Description

**Problem Link:** [Accounts Merge](https://leetcode.com/problems/accounts-merge/)

Given a list of `accounts` where each element `accounts[i]` is a list of strings, where the first element `accounts[i][0]` is a name, and the rest of the elements are **emails** representing emails of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails **in sorted order**. The accounts themselves can be returned in **any order**.

**Example 1:**
```
Input: accounts = [["John","johnsmith@mail.com","john_newyork@mail.com"],["John","johnsmith@mail.com","john00@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
Output: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
```

**Constraints:**
- `1 <= accounts.length <= 1000`
- `2 <= accounts[i].length <= 10`
- `1 <= accounts[i][j].length <= 30`
- `accounts[i][0]` consists of English letters.
- `accounts[i][j]` (for j > 0) is a valid email.

## Intuition/Main Idea

This is a Union-Find problem. Emails connected through accounts belong to the same person.

**Core Algorithm:**
- Use Union-Find to group emails that appear together
- Build email-to-account mapping
- For each account, union all emails
- Group emails by root parent, sort, and format result

**Why Union-Find:** We need to merge sets of emails that are connected. Union-Find efficiently groups connected components.

## Code Mapping

| Problem Requirement (@) | Java Code Section (Relevant Lines) |
|------------------------|-----------------------------------|
| Merge accounts | Union-Find - Lines 8-50 |
| Group emails | Find root - Lines 30-35 |
| Sort emails | TreeMap sorting - Lines 52-60 |
| Format result | Result building - Lines 52-65 |

## Final Java Code & Learning Pattern (Full Content)

```java
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // Union-Find data structure
        Map<String, String> parent = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();
        
        // Initialize: each email is its own parent
        // Also map email to account name
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                parent.put(email, email);
                emailToName.put(email, name);
            }
        }
        
        // Union: connect all emails in same account
        for (List<String> account : accounts) {
            String firstEmail = account.get(1);
            for (int i = 2; i < account.size(); i++) {
                union(firstEmail, account.get(i), parent);
            }
        }
        
        // Group emails by root parent
        Map<String, TreeSet<String>> merged = new HashMap<>();
        for (String email : parent.keySet()) {
            String root = find(email, parent);
            merged.computeIfAbsent(root, k -> new TreeSet<>()).add(email);
        }
        
        // Build result
        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<String, TreeSet<String>> entry : merged.entrySet()) {
            List<String> account = new ArrayList<>();
            account.add(emailToName.get(entry.getKey()));
            account.addAll(entry.getValue());
            result.add(account);
        }
        
        return result;
    }
    
    private String find(String email, Map<String, String> parent) {
        if (!parent.get(email).equals(email)) {
            parent.put(email, find(parent.get(email), parent)); // Path compression
        }
        return parent.get(email);
    }
    
    private void union(String email1, String email2, Map<String, String> parent) {
        String root1 = find(email1, parent);
        String root2 = find(email2, parent);
        if (!root1.equals(root2)) {
            parent.put(root1, root2);
        }
    }
}
```

## Complexity Analysis

**Time Complexity:** $O(A \log A)$ where $A$ is total account length. Union-Find operations are nearly $O(1)$ with path compression, and sorting takes $O(A \log A)$.

**Space Complexity:** $O(A)$ for the maps.

## Similar Problems

- [Number of Connected Components](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/) - Union-Find for connectivity
- [Redundant Connection](https://leetcode.com/problems/redundant-connection/) - Union-Find application
- [Friend Circles](https://leetcode.com/problems/friend-circles/) - Similar grouping problem

