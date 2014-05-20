package net.thesyndicate.utilities;

import java.util.Arrays;
import java.util.Collections;

/**
 * Java implementation of the algorithm based on the research by
 * Jeffery A. Johnson's, Brigham Young University-Hawaii Campus, in their paper
 * 'SEPA: A Simple, Efficient Permutation Algorithm'.
 *
 * http://www.oocities.org/permute_it/soda_submit.html
 *
 * Created by dboolbe on 5/20/14.
 */
public class LexicographicPermutationGenerator {

    String permutationString;
    boolean incremental;

    /**
     * Constructs a new LexicographicPermutationGenerator that produces incremental permutations of the provided
     * permutationString.
     * @param permutationString initial input permutation string
     */
    public LexicographicPermutationGenerator(String permutationString) {
        this.permutationString = permutationString;
        incremental = true;
    }

    /**
     * Constructs a new LexicographicPermutationGenerator that produces permutations of the provided permutationString.
     * Incremental or decremental permutations are determine by incremental boolean value.
     * @param permutationString initial input permutation string
     * @param incremental If true increments string "ab" to "ba", else decrements string "ba" to "ab".
     */
    public LexicographicPermutationGenerator(String permutationString, boolean incremental) {
        this.permutationString = permutationString;
        this.incremental = incremental;
    }

    /**
     * Returns true if this scanner has another permutation in its input.
     * @return true if and only if permutation generator has another permutation
     */
    public boolean hasNext() {
        if(incremental)
            return hasNextIncrementalPermutation(permutationString);
        return hasNextDecrementalPermutation(permutationString);
    }

    /**
     * Returns true if this scanner has another incremental permutation in its input.
     * @param initialPermute initial input permutation
     * @return true if and only if permutation generator has another incremental permutation
     */
    private boolean hasNextIncrementalPermutation(String initialPermute) {
        boolean permutePossible = false;
        int key = initialPermute.length();
        for(int i = 0; i < initialPermute.length(); i++) {
            key--;
            if((key + 1) < initialPermute.length()) {
                if(initialPermute.charAt(key) < initialPermute.charAt(key + 1)) {
                    permutePossible = true;
                    break;
                }
            }
        }

        return permutePossible;
    }

    /**
     * Returns true if this scanner has another decremental permutation in its input.
     * @param initialPermute initial input permutation
     * @return true if and only if permutation generator has another decremental permutation
     */
    private boolean hasNextDecrementalPermutation(String initialPermute) {
        boolean permutePossible = false;
        int key = initialPermute.length();
        for(int i = 0; i < initialPermute.length(); i++) {
            key--;
            if((key + 1) < initialPermute.length()) {
                if(initialPermute.charAt(key) > initialPermute.charAt(key + 1)) {
                    permutePossible = true;
                    break;
                }
            }
        }

        return permutePossible;
    }

    /**
     * Prints out all of the permutations.
     */
    public void printAllPermutations() {
        int num = 0;
        System.out.println(++num + ":" + permutationString);
        while(hasNext()) {
            permutationString = next();
            System.out.println(++num + ":" + permutationString);
        }
    }

    /**
     * Finds and returns the next permutation from this permutation generator.
     * @return the next permutation
     */
    public String next() {
        if(incremental)
            return nextIncrementalPermutation(permutationString);
        return nextDecrementalPermutation(permutationString);
    }

    /**
     * Finds and returns the next incremental permutation from this permutation generator.
     * @param initialPermute initial input permutation
     * @return the next incremental permutation
     */
    private String nextIncrementalPermutation(String initialPermute) {
        boolean permutePossible = false;
        int key = initialPermute.length();
        for(int i = 0; i < initialPermute.length(); i++) {
            key--;
            if((key + 1) < initialPermute.length()) {
                if(initialPermute.charAt(key) < initialPermute.charAt(key + 1)) {
                    permutePossible = true;
                    break;
                }
            }
        }

        if(!permutePossible)
            return initialPermute;

        int rKey = initialPermute.length();
        for(int i = 0; i < initialPermute.length(); i++) {
            rKey--;
            if(key == rKey) {
                break;
            }
            if(initialPermute.charAt(key) < initialPermute.charAt(rKey)) {
                break;
            }
        }

        String swappedString = swapString(initialPermute, key, rKey);

        String sortedString = sortSubstring(swappedString, key + 1);

        return sortedString;
    }

    /**
     * Finds and returns the next decremental permutation from this permutation generator.
     * @param initialPermute initial input permutation
     * @return the next decremental permutation
     */
    private String nextDecrementalPermutation(String initialPermute) {
        boolean permutePossible = false;
        int key = initialPermute.length();
        for(int i = 0; i < initialPermute.length(); i++) {
            key--;
            if((key + 1) < initialPermute.length()) {
                if(initialPermute.charAt(key) > initialPermute.charAt(key + 1)) {
                    permutePossible = true;
                    break;
                }
            }
        }

        if(!permutePossible)
            return initialPermute;

        int rKey = initialPermute.length();
        for(int i = 0; i < initialPermute.length(); i++) {
            rKey--;
            if(key == rKey) {
                break;
            }
            if(initialPermute.charAt(key) > initialPermute.charAt(rKey)) {
                break;
            }
        }

        String swappedString = swapString(initialPermute, key, rKey);

        String sortedString = sortSubstring(swappedString, key + 1);

        return sortedString;
    }

    /**
     * Swaps two characters in a string.
     * @param originalString original string
     * @param index0 position of one character in the string (zero indexed)
     * @param index1 position of the other character in the string (zero indexed)
     * @return string with the two character swapped.
     */
    private String swapString(String originalString, int index0, int index1) {
        if(originalString == null)
            return null;
        else if((index0 < 0) || (index1 >= originalString.length()))
            return null;
        else if(index0 == index1)
            return originalString;
        else if(index0 > index1)
            return swapString(originalString, index1, index0);

        char[] originalArray = originalString.toCharArray();
        char tempCharacter = originalArray[index0];
        originalArray[index0] = originalArray[index1];
        originalArray[index1] = tempCharacter;
        return new String(originalArray);
    }

    /**
     * Sorts a tail subset of an entire string.
     * @param originalString original string
     * @param index position of the first character in the beginning of the string to be sorted (zero indexed)
     * @return string with its tail sorted
     */
    private String sortSubstring(String originalString, int index) {
        if(incremental)
            return sortSubstringIncremental(originalString, index);
        return sortSubstringDecremental(originalString, index);
    }

    /**
     * Sorts incremental a tail subset of an entire string.
     * @param originalString original string
     * @param index position of the first character in the beginning of the string to be sorted incremental (zero indexed)
     * @return string with its tail sorted incremental
     */
    private String sortSubstringIncremental(String originalString, int index) {
        if(originalString == null)
            return null;
        else if((index < 0) || (index >= originalString.length()))
            return null;

        String sortString = originalString.substring(index);
        char[] sortCharacters = sortString.toCharArray();
        Arrays.sort(sortCharacters);
        return originalString.substring(0,index) + new String(sortCharacters);
    }

    /**
     * Sorts decremental a tail subset of an entire string.
     * @param originalString original string
     * @param index position of the first character in the beginning of the string to be sorted decremental (zero indexed)
     * @return string with its tail sorted decremental
     */
    private String sortSubstringDecremental(String originalString, int index) {
        if(originalString == null)
            return null;
        else if((index < 0) || (index >= originalString.length()))
            return null;

        String sortString = originalString.substring(index);
        char[] sortChars = sortString.toCharArray();
        Character[] sortCharacters = new Character[sortChars.length];
        // To use Collections in conjunction with Arrays.sort(), char[] has to be converted to Character[].
        for(int i = 0; i < sortChars.length; i++)
            sortCharacters[i] = sortChars[i];
        Arrays.sort(sortCharacters, Collections.reverseOrder());
        // To create a new String object, Character[] has to be converted back to char[].
        for(int i = 0; i < sortCharacters.length; i++)
            sortChars[i] = sortCharacters[i];
        return originalString.substring(0,index) + new String(sortChars);
    }

    public static void main(String[] args) {
        String incrementalString = "abc";
        String decrementalString = "cba";

        LexicographicPermutationGenerator generator = new LexicographicPermutationGenerator(incrementalString);
        System.out.println();
        System.out.println(incrementalString + " " + generator.hasNext());
        generator.printAllPermutations();

        generator = new LexicographicPermutationGenerator(incrementalString, false);
        System.out.println();
        System.out.println(incrementalString + " " + generator.hasNext());
        generator.printAllPermutations();

        generator = new LexicographicPermutationGenerator(decrementalString);
        System.out.println();
        System.out.println(decrementalString + " " + generator.hasNext());
        generator.printAllPermutations();

        generator = new LexicographicPermutationGenerator(decrementalString, false);
        System.out.println();
        System.out.println(decrementalString + " " + generator.hasNext());
        generator.printAllPermutations();
    }
}
