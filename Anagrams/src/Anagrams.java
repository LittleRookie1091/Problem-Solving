import java.util.*;
import java.lang.*;

/*
The program sequence will be:
    Take all words into word array and dictionary words into dictionary array
    Then sort the dictionary array alphabetically done using method wordIn()
    Find the permutation (permutations(w) or hashKey(w))for each word in dictionary and enter it into hashtable as well as an arraylist

    The arraylist will be sorted alphabetically and split into 26 letter arrays A-Z,
    and also split according to length this will be done with method setDictionary()
    The hashtable will only store the first permutation for a word as to get the best alphabetical one.
    This will also be the case for the arraylist as we add the words to both lists at the same time

    Then we will start looking for anagrams for words
    We begin checking by first seeing if there is a one word or two word match in hashtable which is instant
    (we permutate a word and use that as a hash code to find them, e.g hello becomes ehllo and that is used to make hash)
    If we can't find that, then we go through the array list and get all possible words that could be part of an
    anagram.
    Using this pool of words we use a depth first search to find the best outcome, each time we find a
    new best group of words, we then know we don't have to go deeper than that group of words.
 */

public class Anagrams {
    //Arraylists for taking in dictionary and words
    private ArrayList<String> dictionary = new ArrayList<String>();
    private ArrayList<String> words = new ArrayList<String>();
    //Should change method and remove this array but for now it is alright
    //Stores the dictionary words that can actually be used
    private ArrayList<String> temp = new ArrayList<String>();
    //Arraylist for substring anagrams
    private ArrayList<String> anagramArray = new ArrayList<String>();
    //Hashtable to check for 1 word or 2 word combination anagrams very fast
    private Hashtable<String, String> hash = new Hashtable<String, String>();
    //Arraylist's that split by alphabet and then length
    private ArrayList<ArrayList<ArrayList<String>>> finalDict = new ArrayList<ArrayList<ArrayList<String>>>();

    private int depthCheck = -1;
    private ArrayList<String> bestCase = new ArrayList<String>();
    private int bestCaseSize = 0;
    private ArrayList<String> useArray = new ArrayList<String>();
    private ArrayList<String> finalOutput = new ArrayList<String>();
    public static void main(String[] args){
        Anagrams ana = new Anagrams();
        //Setup methods
        ana.wordIn();
        ana.hasher();
        ana.setDictionary();

        //Find anagram methods
        ana.anagramFinder();

    }

    public Anagrams(){
    }

    public void wordIn(){
        Boolean check = true;
        Scanner scan = new Scanner(System.in);
        check = true;
        while(scan.hasNextLine()){
            String word = scan.nextLine();
            word = word.replaceAll("(?:-|[\\[\\]{}()+/\\\\',])", "");
            if(word.isBlank()){
                check = false;
            }
            else if(check){
                words.add(word);
            }
            else {
                dictionary.add(word);
            }
        }
        Collections.sort(dictionary);

    }

    public void setDictionary(){
        // initialize array list for A-Z
         finalDict  = new ArrayList<ArrayList<ArrayList<String>>>();
        for(int i=0;i<26;i++){
            finalDict.add(new ArrayList<ArrayList<String>>());
        }

        for(String s: temp){
            char firstChar = s.charAt(0);
            int index = firstChar - 'a'; //convert char to index
            int length = s.length()-1;

            if(finalDict.get(index).size() == 0){
                finalDict.get(index).add(new ArrayList<String>());
               // System.out.println("NEW ");
            }
            //System.out.println(finalDict.get(index).size());
            if(finalDict.get(index).size()-1 < length){ // need array that hold more characters
                int max = length;
                while(finalDict.get(index).size()-1 < length){
                    finalDict.get(index).add(new ArrayList<String>()); //add empty array
                    max++;
                }
            }
            // System.out.println(finalDict.get(index).size());
            finalDict.get(index).get(length).add(s);
        }
/*
        for(char alphabet = 'a'; alphabet<='z'; alphabet++){
            //System.out.println("\nLetter : " + alphabet);
            int index = alphabet - 'a';
            int l = 0;
            for(ArrayList<String> length : finalDict.get(index)){
                //if(l>0){
                System.out.println("length: " + l);
                // }
                for(String word: length){
                    System.out.println(word);
                }
                l++;
            }
        }
        */
    }

    public void anagramFinder(){
        for(String each : words){
            if (!hashFind(each)) {
                anagramPool(each);
                if(!depthSearch(each)){
                    finalOutput.add(each + ": " );
                }else{

                    String outputString = bestCase.get(0);;

                    for(int i = 1; i<bestCase.size();i++){
                        outputString = outputString+" "+bestCase.get(i);
                    }
                    finalOutput.add(each + ": " +outputString);
                }
            }
        }
        for(int i = 0; i<finalOutput.size();i++){
            System.out.println(finalOutput.get(i));
        }
    }

    /*Will check hash array for for 1 word or 2 word anagrams, if found will add them
    to the finalOutput array, and return true, else it will return false
     */
    public boolean hashFind(String word){
        //retrieve
        String use = permutations(word);
        if(hash.get(use) != null){
            finalOutput.add(word + ": "+ hash.get(use));
            return true;
        }else{
            for(int i = use.length()-1; i!= -1; i--){
                String sub = hash.get(use.substring(0,i)+use.substring(i+1));
                if(hash.get(use.charAt(i)) != null &&
                        sub != null){
                    finalOutput.add(word + ": "+ hash.get(use.charAt(i))+ " "+ sub);
                    return true;
                }
                sub = hash.get(use.substring(i));
                String dom = hash.get(use.substring(0,i));
                if(dom != null && sub != null){
                    finalOutput.add(word + ": "+ dom+ " "+ sub);
                    return true;
                }
            }
        }
        return false;
    }
    /*
   Creates the pool of words to use for finding anagrams
    */
    public void anagramPool(String word){
       // System.out.println(word);
        //Have to empty array for new word
        anagramArray.clear();
        ArrayList<String> tempArray = new ArrayList<>();
        String perm = permutations(word);
        boolean[] alphaCheck = new boolean[26];
        //set all A-Z arrays to false
        for(int i = 0; i< alphaCheck.length; i++){
            alphaCheck[i] = false;
        }
        //Decide which alphabet arrays to use from A-Z
        for(int i = 0; i< perm.length(); i++){
            for(int j = 0; j<26; j++){
                if(perm.charAt(i) == (char)(j+97)){
                    alphaCheck[j] = true;
                }
            }
        }

        //Iterates through alphabet arrays
        for(int i = 0; i<alphaCheck.length; i++){
            if(alphaCheck[i]){
                //Goes only through arrays with size of anagram word-2 in size
                for(int lengthOfAn = 0; lengthOfAn< perm.length(); lengthOfAn++){
                    if(lengthOfAn<finalDict.get(i).size()){
                        for(int len = 0; len< finalDict.get(i).get(lengthOfAn).size(); len++) {
                            if(compareTwoWords(finalDict.get(i).get(lengthOfAn).get(len), perm)){
                                tempArray.add(finalDict.get(i).get(lengthOfAn).get(len));
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(tempArray);
        int j = tempArray.size();
        for(int i = 0; i< j; i++){
            String largestSoFar = tempArray.get(0);
            int largestIndex = 0;
            for(int index = 1; index< tempArray.size(); index++){
                if(tempArray.get(index).length()>largestSoFar.length()){
                    largestSoFar = tempArray.get(index);
                    largestIndex = index;
                }
            }
            anagramArray.add(largestSoFar);
            tempArray.remove(largestIndex);
        }
    }

    /*A depth search of anagram words for anagram solutions
    Calls deepening on each word in anagramarray
    **/
    public boolean depthSearch(String word){
        String letters = word;
        depthCheck = -1;
        useArray.clear();
        bestCase.clear();
        bestCaseSize = 100;
        for(int i = 0; i<anagramArray.size(); i++) {
            deepening(letters, anagramArray.get(i), i);
        }
        if(bestCaseSize<100){
            if(bestCaseSize>1){
                return true;
            }
        }
        return false;
    }

    /*
    This  method will check the anagram word against the letters left for  anagram,
    there are three outcomes of this method, if the anagram word completes the anagram
    and returns an empty string, then the anagram is checked against the best case. If it is better it
    replaces the best case.

    Else if -1 is returned from comparing strings, then the anagram word does not fit into the
    letters that need to  be used and deepening is recursively called for the next word in array.

    The last option is if the anagram word did fit and but still needs another word. This will recursively call
    deepening with the same word in array.
     */
    public boolean deepening(String letters1, String anagramWord, int count){
        if(count+1 < anagramArray.size()) {
            if (anagramWord.length() <= letters1.length()) {
                String letters = getLettersLeft(letters1, anagramWord);
                if (letters.isBlank()) {
                    useArray.add(anagramWord);
                    if (useArray.size() < bestCaseSize) {
                        bestCase.clear();
                        for(int i = 0; i<useArray.size();i++){
                            bestCase.add(useArray.get(i));
                        }
                        bestCaseSize = bestCase.size();
                        return true;
                    }else if(bestCaseSize== 100){
                        bestCase.clear();
                        for(int i = 0; i<useArray.size();i++){
                            bestCase.add(useArray.get(i));
                        }
                        bestCaseSize = bestCase.size();
                        return true;
                    }
                } else if (letters.equals("-1")) {
                    deepening(letters1, anagramArray.get(count + 1), count + 1);
                } else {
                    useArray.add(anagramWord);
                    if (useArray.size() < bestCaseSize) {
                        deepening(letters, anagramArray.get(count), count);
                    }
                }
            }else{
            deepening(letters1, anagramArray.get(count + 1), count + 1);
            }
        }else{
                if (anagramWord.length() <= letters1.length()) {
                    String letters = getLettersLeft(letters1, anagramWord);
                    if (letters.isEmpty()) {
                        useArray.add(anagramWord);
                        if (useArray.size() < bestCaseSize) {
                            bestCase.clear();
                            for(int i = 0; i<useArray.size();i++){
                                bestCase.add(useArray.get(i));
                            }
                            bestCaseSize = bestCase.size();
                            return true;
                        }else if(bestCaseSize== 100){
                            bestCase.clear();
                            for(int i = 0; i<useArray.size();i++){
                                bestCase.add(useArray.get(i));
                            }
                            bestCaseSize = bestCase.size();
                            return true;
                        }
                    }else if(letters.equals("-1")){
                    return false;
                    }
                    else{
                        useArray.add(anagramWord);
                         deepening(letters, anagramArray.get(count), count);
                    }
                }
            }
          return false;
        }


    //Figures out  if the wordTest can be an anagram for ana
    public boolean compareTwoWords(String ana, String wordTest){
        String w = wordTest;
        StringBuilder word = new StringBuilder(w);
        String anagram = ana;
        boolean isAnagram = true;

        for(char letter: anagram.toCharArray()){
            int index = word.indexOf(Character.toString(letter));
            if(index != -1){
                word.deleteCharAt(index);
            }else{
                isAnagram = false;
                break;
            }
        }
        if(isAnagram){
            return true;
        }
        return false;
    }


    public String getLettersLeft(String ana, String wordTest){
        String w = wordTest;
        StringBuilder word = new StringBuilder(w);
        String anagram = ana;
        boolean isAnagram = true;
        StringBuilder returnLetters = new StringBuilder(ana);
        for(char letter: w.toCharArray()){
            int index = returnLetters.indexOf(Character.toString(letter));
            if(index != -1){
                returnLetters.deleteCharAt(returnLetters.indexOf(Character.toString(letter)));
            }else{
                isAnagram = false;
                break;
            }
            if(word.toString().isEmpty()){
                return returnLetters.toString();
            }
        }
        if(isAnagram){
            return returnLetters.toString();
        }
        return "-1";
    }

    /*
    Adds the words to the hashtable and multi dimensional array
     */
    public void hasher(){
        //add to dict
        for(String each :dictionary ){
            String word = hash.get(permutations(each));
            if(word == null){
                hash.put(permutations(each),each);
                temp.add(each);
            }
        }
    }


    /*
    Creates permutation of word, in alphabetic order
     */
    public String permutations(String w){

        String permutation = "";
        String word = w;
        for(int abc = 0; abc<26; abc++){
            for(int j = 0; j< word.length(); j++){
                char c = (char) (abc+ 97);
                String check = Character.toString(c);
                if(word.charAt(j) == check.charAt(0)){
                    permutation += word.charAt(j);
                }
            }
        }
        return permutation;
    }

    /*
    Creates permutation of word in reduced form, alphabetic order
     */
    public String hashKey(String word){

        int i = 0;
        String s = "";

        while(i<word.length()){
            int count = 1;
            char c = word.charAt(i);
            int j = i+1;
            while( j < word.length() && c == word.charAt(j)){
                j++;
                count++;
            }

            if(count > 0){
                s += count;
                s += c;
            }
            i+= count;
        }
        System.out.println(s);
        return s;
    }
}

