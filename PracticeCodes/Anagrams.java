/**
 * Given an array of strings, return all groups of strings that are anagrams.
 * Note: All inputs will be in lower-case.
 * 
 *  
 *  IDEA: 
 *  		Take string in the array one by one. For each string, sort the characters and then check if that set of characters
 *  is present in the map. If so, that means you have found an anagram and thus you add the original (unsorted) string to 
 *  the corresponding existing list of string. 
 *  If the set of characters is not present, then add it a new list in the map.
 *  Keep repeating the above steps for all the strings.
 *  Once all the strings are checked, scan the map to get the lists of size > 1 because only those lists will have multiple
 *  original strings which are anagrams of each other.
 *  Return this set of strings as a list, as needed.
 **/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Anagrams {
    public List<String> anagrams(String[] strs) {
        if(strs.length == 0 || strs == null)
            return null;
        
        HashMap<String, ArrayList<String>> anagramMap = new HashMap<String, ArrayList<String>>();
        for(int i=0;i<strs.length;i++){
            String tempString = strs[i];
            char[] sortedArrayFromString = tempString.toCharArray();
            Arrays.sort(sortedArrayFromString);
//            String sortedString = sortedArrayFromString.toString();
            String sortedString = String.valueOf(sortedArrayFromString);
            
            if(anagramMap.containsKey(sortedString)){
                anagramMap.get(sortedString).add(tempString);
            }
            else{
                ArrayList<String> tempList = new ArrayList<String>();
                tempList.add(tempString);
                anagramMap.put(sortedString, tempList);
            }
        }
        // ArrayList<String> listsFromMap[] = anagramMap.values();
        List<String> resultList = new ArrayList<String>();
        
        for(ArrayList<String>list: anagramMap.values()){
            if(list.size()>1)
                resultList.addAll(list);
        }
        return resultList;
    }
    
    public static void main(String args[]){
    	Anagrams a = new Anagrams();
    	String[] input = {"", ""};
    	List<String> result = new ArrayList<String>();
    	result = a.anagrams(input);
    	
    	for(String s: result)
    		System.out.println(s);
    }
}