package zeroOrder;

import java.util.ArrayList;
import java.util.List;

public class EncodeDecodeStrings {
    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        if (strs == null || strs.size() == 0)
            return null;

        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s.length()).append("/").append(s);
        }
        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String str) {
        List<String> stringList = new ArrayList<>();
        if (str == null || str.length() == 0)
            return stringList;

        int slashIndex = 0, ptr = 0;
        while (ptr < str.length()) {
            slashIndex = str.indexOf("/", ptr);
            int strLen = Integer.parseInt(str.substring(ptr, slashIndex));
            String s = str.substring(slashIndex + 1, slashIndex + 1 + strLen);
            stringList.add(s);
            ptr = slashIndex + 1 + strLen;
        }

        return stringList;
    }

}
