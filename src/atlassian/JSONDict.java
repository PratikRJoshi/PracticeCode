package atlassian;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * Create output string for
 *  Input : dict("a": "apple", "b": dict("b": "blueberry", "c": "cranberry"))
 *  Expected output String : "{a:apple,b:{b:blueberry,c:cranberry}}"

 */

/* Name of the class has to be "Main" only if the class is public. */
public class JSONDict {

    public interface DictEntry {
        public boolean isDict();
    }

    public static class Dict implements DictEntry {
        private Map<String, DictEntry> map;

        public Dict(Map<String, DictEntry> map) {
            this.map = map;
        }

        public boolean isDict() {
            return true;
        }

        public Set<String> getKeys() {
            return map.keySet();
        }

        public DictEntry get(String key) {
            return map.get(key);
        }
    }

    public static class StringWrapper implements DictEntry {
        private String str;

        public StringWrapper(String str) {
            this.str = str;
        }

        public boolean isDict() {
            return false;
        }

        public String getValue() {
            return str;
        }
    }


    public static void main(String[] args) {
        Map<String, DictEntry> map1 = new HashMap<>();
        map1.put("b", new StringWrapper("blueberry"));
        map1.put("c", new StringWrapper("cranberry"));

        DictEntry dict1 = new Dict(map1);

        Map<String, DictEntry> map = new HashMap<>();
        map.put("a", new StringWrapper("apple"));
        map.put("b", dict1);

        DictEntry dict = new Dict(map);

        System.out.println("Json string : " + convert(dict));
    }


    public static String convert(DictEntry entry) {

        StringBuilder sb = new StringBuilder();
        return convert(entry, sb);
    }

    private static String convert(DictEntry entry, StringBuilder sb){

        if (entry instanceof Dict){
            for(String key : ((Dict) entry).getKeys()){
                sb.append(key).append(",");
            }
        }
        return null;
    }
}