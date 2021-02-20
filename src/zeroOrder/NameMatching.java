package zeroOrder; /******************
 * Name Matching
 *
 *   At Checkr, one of the most important aspects of our work is accurately matching records
 * to candidates. One of the ways that we do this is by comparing the name on a given record
 * to a list of known aliases for the candidate. In this exercise, we will implement a
 * `nameMatch` method that accepts the list of known aliases as well as the name returned
 * on a record. It should return true if the name matches any of the aliases and false otherwise.
 *
 * The nameMatch method will be required to pass the following tests:
 *
 * 1. Exact match
 *
 *   knownAliases = ["Alphonse Gabriel Capone", "Al Capone"]
 *   nameMatch(knownAliases, "Alphonse Gabriel Capone") => true
 *   nameMatch(knownAliases, "Al Capone")               => true
 *   nameMatch(knownAliases, "Alphonse Francis Capone") => false
 *
 *
 * 2. Middle name missing (on alias)
 *
 *   knownAliases = ["Alphonse Capone"]
 *   nameMatch(knownAliases, "Alphonse Gabriel Capone") => true
 *   nameMatch(knownAliases, "Alphonse Francis Capone") => true
 *   nameMatch(knownAliases, "Alexander Capone")        => false
 *
 *
 * 3. Middle name missing (on record name)
 *
 *   knownAliases = ["Alphonse Gabriel Capone"]
 *   nameMatch(knownAliases, "Alphonse Capone")         => true
 *   nameMatch(knownAliases, "Alphonse Francis Capone") => false
 *   nameMatch(knownAliases, "Alexander Capone")        => false
 *
 *
 * 4. More middle name tests
 *    These serve as a sanity check of your implementation of cases 2 and 3
 *
 *   knownAliases = ["Alphonse Gabriel Capone", "Alphonse Francis Capone"]
 *   nameMatch(knownAliases, "Alphonse Gabriel Capone") => true
 *   nameMatch(knownAliases, "Alphonse Francis Capone") => true
 *   nameMatch(knownAliases, "Alphonse Edward Capone")  => false
 *
 *
 * 5. Middle initial matches middle name
 *
 *   knownAliases = ["Alphonse Gabriel Capone", "Alphonse F Capone"]
 *   nameMatch(knownAliases, "Alphonse G Capone")       => true
 *   nameMatch(knownAliases, "Alphonse Francis Capone") => true
 *   nameMatch(knownAliases, "Alphonse E Capone")       => false
 *   nameMatch(knownAliases, "Alphonse Edward Capone")  => false
 *   nameMatch(knownAliases, "Alphonse Gregory Capone") => false
 *
 *
 * Bonus: Transposition
 *
 * Transposition (swapping) of the first name and middle name is relatively common.
 * In order to accurately match the name returned from a record we should take this
 * into account.
 *
 * All of the test cases implemented previously also apply to the transposed name.
 *
 *
 * 6. First name and middle name can be transposed
 *
 *   "Gabriel Alphonse Capone" is a valid transposition of "Alphonse Gabriel Capone"
 *
 *   knownAliases = ["Alphonse Gabriel Capone"]
 *   nameMatch(knownAliases, "Gabriel Alphonse Capone") => true
 *   nameMatch(knownAliases, "Gabriel A Capone")        => true
 *   nameMatch(knownAliases, "Gabriel Capone")          => true
 *   nameMatch(knownAliases, "Gabriel Francis Capone")  => false
 *
 *
 * 7. Last name cannot be transposed
 *
 *   "Alphonse Capone Gabriel" is NOT a valid transposition of "Alphonse Gabriel Capone"
 *   "Capone Alphonse Gabriel" is NOT a valid transposition of "Alphonse Gabriel Capone"
 *
 *   knownAliases = ["Alphonse Gabriel Capone"]
 *   nameMatch(knownAliases, "Alphonse Capone Gabriel") => false
 *   nameMatch(knownAliases, "Capone Alphonse Gabriel") => false
 *   nameMatch(knownAliases, "Capone Gabriel")          => false
 */

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class NameMatching {
    public static boolean nameMatch(String[] knownAliases, String recordName) {
        // Implement me

        Map<String, List<String>> nameAliasMap = new HashMap<>();

        // map < lastName, List<aliases> >
        for(String alias : knownAliases){
            String[] aliasParts = alias.split("\\s+"); // split alias into parts
            if (aliasParts.length == 3) {
                if(nameAliasMap.containsKey(aliasParts[2])){
                    List<String> aliases = nameAliasMap.get(aliasParts[2]);
                    aliases.add(alias);
                    nameAliasMap.put(aliasParts[2], aliases); // map has key on last name since that is constant in presence and position across all the conditions
                } else {
                    ArrayList<String> aliases = new ArrayList<>();
                    aliases.add(alias);
                    nameAliasMap.put(aliasParts[2], aliases);
                }

            } else if(aliasParts.length == 2){
                if(nameAliasMap.containsKey(aliasParts[1])){
                    List<String> aliases = nameAliasMap.get(aliasParts[1]);
                    aliases.add(alias);
                    nameAliasMap.put(aliasParts[1], aliases);
                } else {
                    ArrayList<String> aliases = new ArrayList<>();
                    aliases.add(alias);
                    nameAliasMap.put(aliasParts[1], aliases);
                }
            }
        }

        // split the given name into parts
        String[] nameParts = recordName.split("\\s+");

        if(checkNameAndAliases(nameParts, nameAliasMap))
            return true;

        // transpose given name
        String transposedName = null;
        if(nameParts.length == 3){
            transposedName = nameParts[1] + " " + nameParts[0] + " " + nameParts[2];
        } else if(nameParts.length == 2){
            transposedName = nameParts[1] + " " + nameParts[0];
        }

        nameParts = transposedName.split("\\s+");
        if(checkNameAndAliases(nameParts, nameAliasMap))
            return true;


        // transpose aliases
        return (transposeAliasesAndVerify(recordName, nameAliasMap));

        /*boolean result = false;
        nameParts = recordName.split("\\s+");
        String lastName = nameParts[nameParts.length - 1];

        if(nameAliasMap.containsKey(lastName)){
            for(String alias : nameAliasMap.get(lastName)){
                String[] aliasParts = alias.split("\\s+");

                // create transpose alias
                if(aliasParts.length == 3){
                    alias = aliasParts[1] + " " + aliasParts[0] + " " + aliasParts[2];
                } else if(aliasParts.length == 2){
                    alias = aliasParts[1] + " " + aliasParts[0];
                }

                aliasParts = alias.split("\\s+");
                String firstName = aliasParts[0];
                if(firstName.equals(nameParts[0])){

                    // both alias and name have 3 parts
                    if(aliasParts.length == 3 && nameParts.length == 3){
                        // check if either name or alias has middle initial
                        if(aliasParts[1].length() == 1 || nameParts[1].length() == 1){
                            result = ( aliasParts[1].charAt(0) == nameParts[1].charAt(0) );
                            if(result)
                                break;
                        } else {
                            // exact match check
                            result = ( aliasParts[1].equals(nameParts[1]));
                            if(result)
                                break;
                        }
                    } else if(nameParts.length == 2 || aliasParts.length == 2) { // at this point the last name has matched from the map and the first name is checked to be true too
                        result = true;
                        break;

                    } *//*else if(aliasParts.length == 2) {
                        result = true;
                        break;
                    }*//*
                }
            }
        }*/

    }

    private static boolean transposeAliasesAndVerify(String recordName, Map<String, List<String>> nameAliasMap) {
        boolean result = false;
        String[] nameParts = recordName.split("\\s+");
        String lastName = nameParts[nameParts.length - 1];

        if(nameAliasMap.containsKey(lastName)){
            for(String alias : nameAliasMap.get(lastName)){
                String[] aliasParts = alias.split("\\s+");

                // create transpose alias
                if(aliasParts.length == 3){
                    alias = aliasParts[1] + " " + aliasParts[0] + " " + aliasParts[2];
                } else if(aliasParts.length == 2){
                    alias = aliasParts[1] + " " + aliasParts[0];
                }

                aliasParts = alias.split("\\s+");
                String firstName = aliasParts[0];
                if(firstName.equals(nameParts[0])){

                    // both alias and name have 3 parts
                    if(aliasParts.length == 3 && nameParts.length == 3){
                        // check if either name or alias has middle initial
                        if(aliasParts[1].length() == 1 || nameParts[1].length() == 1){
                            result = ( aliasParts[1].charAt(0) == nameParts[1].charAt(0) );
                            if(result)
                                break;
                        } else {
                            // exact match check
                            result = ( aliasParts[1].equals(nameParts[1]));
                            if(result)
                                break;
                        }
                    } else if(nameParts.length == 2 || aliasParts.length == 2) { // at this point the last name has matched from the map and the first name is checked to be true too
                        result = true;
                        break;

                    } /*else if(aliasParts.length == 2) {
                        result = true;
                        break;
                    }*/
                }
            }
        }
        return result;
    }

    private static boolean checkNameAndAliases(String[] nameParts, Map<String, List<String>> nameAliasMap){
        String lastName = nameParts[nameParts.length - 1];

        // do the verification checks
        boolean result = false;

        if(nameAliasMap.containsKey(lastName)){
            for(String alias : nameAliasMap.get(lastName)){
                String[] aliasParts = alias.split("\\s+");
                String firstName = aliasParts[0];
                if(firstName.equals(nameParts[0])){

                    // both alias and name have 3 parts
                    if(aliasParts.length == 3 && nameParts.length == 3){
                        // check if either name or alias has middle initial
                        if(aliasParts[1].length() == 1 || nameParts[1].length() == 1){
                            result = ( aliasParts[1].charAt(0) == nameParts[1].charAt(0) );
                        } else {
                            // exact match check
                            result = ( aliasParts[1].equals(nameParts[1]));
                        }
                        if(result)
                            break;
                    } else if(nameParts.length == 2 || aliasParts.length == 2) {
                        result = true;
                        break;

                    } /*else if(aliasParts.length == 2) {
                        result = true;
                        break;
                    }*/
                }
            }
        }

        return result;
    }

    private static void assertEqual(boolean expected, boolean result, String errorMessage) {
        if (result != expected) {
            System.out.println(errorMessage);
            System.out.println("expected: " + expected);
            System.out.println("actual: " + result);
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        String[] knownAliases = new String[]{"Alphonse Gabriel Capone", "Al Capone"};
        assertEqual(true,  nameMatch(knownAliases, "Alphonse Gabriel Capone"), "error 1.1");
        assertEqual(true,  nameMatch(knownAliases, "Al Capone"),               "error 1.2");
        assertEqual(false, nameMatch(knownAliases, "Alphonse Francis Capone"), "error 1.3");

        knownAliases = new String[]{"Alphonse Capone"};
        assertEqual(true,  nameMatch(knownAliases, "Alphonse Gabriel Capone"), "error 2.1");
        assertEqual(true,  nameMatch(knownAliases, "Alphonse Francis Capone"), "error 2.2");
        assertEqual(false, nameMatch(knownAliases, "Alexander Capone"),        "error 2.3");

        knownAliases = new String[]{"Alphonse Gabriel Capone"};
        assertEqual(true,  nameMatch(knownAliases, "Alphonse Capone"),         "error 3.1");
        assertEqual(false, nameMatch(knownAliases, "Alphonse Francis Capone"), "error 3.2");
        assertEqual(false, nameMatch(knownAliases, "Alphonse Edward Capone"),  "error 3.3");

        knownAliases = new String[]{"Alphonse Gabriel Capone", "Alphonse Francis Capone"};
        assertEqual(true,  nameMatch(knownAliases, "Alphonse Gabriel Capone"), "error 4.1");
        assertEqual(true,  nameMatch(knownAliases, "Alphonse Francis Capone"), "error 4.2");
        assertEqual(false, nameMatch(knownAliases, "Alphonse Edward Capone"),  "error 4.3");

        knownAliases = new String[]{"Alphonse Gabriel Capone", "Alphonse F Capone"};
        assertEqual(true,  nameMatch(knownAliases, "Alphonse G Capone"),       "error 5.1");
        assertEqual(true,  nameMatch(knownAliases, "Alphonse Francis Capone"), "error 5.2");
        assertEqual(false, nameMatch(knownAliases, "Alphonse E Capone"),       "error 5.3");
        assertEqual(false, nameMatch(knownAliases, "Alphonse Edward Capone"),  "error 5.4");
        assertEqual(false, nameMatch(knownAliases, "Alphonse Gregory Capone"), "error 5.5");

        knownAliases = new String[]{"Alphonse Gabriel Capone"};
        assertEqual(true,  nameMatch(knownAliases, "Gabriel Alphonse Capone"), "error 6.1");
        assertEqual(true,  nameMatch(knownAliases, "Gabriel A Capone"),        "error 6.2");
        assertEqual(true,  nameMatch(knownAliases, "Gabriel Capone"),          "error 6.3");
        assertEqual(false, nameMatch(knownAliases, "Gabriel Francis Capone"),  "error 6.4");

        knownAliases = new String[]{"Alphonse Gabriel Capone"};
        assertEqual(false, nameMatch(knownAliases, "Alphonse Capone Gabriel"), "error 7.1");
        assertEqual(false, nameMatch(knownAliases, "Capone Alphonse Gabriel"), "error 7.2");
        assertEqual(false, nameMatch(knownAliases, "Capone Gabriel"),          "error 7.3");

        System.out.println("zeroOrder.Test run finished");
    }
}