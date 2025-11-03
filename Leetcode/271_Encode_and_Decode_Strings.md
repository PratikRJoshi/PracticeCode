### 271. Encode and Decode Strings
#### Problem Statement
[ Encode and Decode Strings](https://leetcode.com/problems/encode-and-decode-strings/)

Design an algorithm to encode a list of strings to a single string. The encoded string is then decoded back to the original list of strings.

Please implement `encode` and `decode`:
- `String encode(List<String> strs)`: Encodes a list of strings to a single string.
- `List<String> decode(String s)`: Decodes a single string to a list of strings.

**Note**: The input strings may contain any possible characters out of 256 valid ASCII characters. The algorithm should be "stateless" - you may not encode the string's length somewhere.

---

### Main Idea & Intuition

This problem asks us to design an algorithm to encode a list of strings into a single string, and then decode that string back to the original list of strings. The key challenge is handling strings that might contain any character, including delimiters we might want to use.

The main insight is to use a **length-prefixed encoding scheme**:
1. For each string, we first encode its length, followed by a delimiter
2. Then we append the actual string content
3. When decoding, we first read the length, then extract exactly that many characters

This approach handles all edge cases, including:
- Empty strings
- Strings containing special characters or delimiters
- Strings with varying lengths

#### Core Concept: Length-Prefixed Encoding

1. **Encode**: For each string, prepend its length followed by a delimiter (e.g., "#")
2. **Decode**: Parse the length prefix, then extract the exact number of characters

### Code Implementation with Detailed Comments

```java
public class Codec {
    // Delimiter to separate length from content
    private static final String DELIMITER = "#";
    
    /**
     * Encodes a list of strings to a single string.
     * Format: [length]#[string content][length]#[string content]...
     */
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        
        for (String str : strs) {
            // Append length, delimiter, and then the string itself
            sb.append(str.length()).append(DELIMITER).append(str);
        }
        
        return sb.toString();
    }
    
    /**
     * Decodes a single string to a list of strings.
     */
    public List<String> decode(String s) {
        List<String> result = new ArrayList<>();
        int i = 0;
        
        while (i < s.length()) {
            // Find the position of the delimiter
            int delimiterPos = s.indexOf(DELIMITER, i);
            
            // Parse the length of the next string
            int length = Integer.parseInt(s.substring(i, delimiterPos));
            
            // Calculate the start position of the string content
            int contentStart = delimiterPos + 1;
            
            // Extract the string based on its length
            String str = s.substring(contentStart, contentStart + length);
            result.add(str);
            
            // Move the pointer to the start of the next length indicator
            i = contentStart + length;
        }
        
        return result;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(strs));
```

### Example Walkthrough

Let's trace through an example to understand how this works:

**Input**: `["Hello", "World", ""]`

**Encoding process**:
1. "Hello" → length = 5 → "5#Hello"
2. "World" → length = 5 → "5#World"
3. "" → length = 0 → "0#"
4. Combined: "5#Hello5#World0#"

**Decoding process**:
1. Read "5" → delimiter → extract 5 characters: "Hello"
2. Read "5" → delimiter → extract 5 characters: "World"
3. Read "0" → delimiter → extract 0 characters: ""
4. Result: `["Hello", "World", ""]`

### Alternative Approaches

1. **Escaping Special Characters**:
   - Use a special character as a delimiter
   - Escape that character when it appears in the strings
   - Drawback: Requires scanning each character and potentially doubling the size

2. **Fixed-Width Length Field**:
   - Use a fixed number of bytes to represent string length
   - Drawback: Wastes space for short strings or limits maximum string length

3. **Base64 Encoding**:
   - Convert the entire list to bytes and use Base64
   - Drawback: Significant overhead and complexity

The length-prefixed approach offers the best balance of simplicity and efficiency.

### Complexity Analysis

* **Time Complexity**:
  * ** Encode**: `O(n)` where n is the total length of all strings combined
  * **Decode**: `O(n)` where n is the length of the encoded string

* **Space Complexity**:
  * ** Encode**: `O(n)` for the output string
  * **Decode**: `O(n)` for the output list of strings
  * Additional space: `O(1)` for variables

### Edge Cases Handled

* **Empty strings**: Encoded as "0#"
* **Strings containing numbers or the delimiter**: Works correctly because we extract based on length
* **Empty list**: Returns an empty string when encoding, and an empty list when decoding
* **Unicode characters**: Works correctly as Java strings handle Unicode