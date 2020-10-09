import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class IbeforeE {
    private ArrayList<String> params = new ArrayList<String>();
    private ArrayList<String> instances = new ArrayList<String>();
    private ArrayList<String> alphabet = new ArrayList<String>();
    private ArrayList<String[]> rules = new ArrayList<>();
    private HashMap<String, ArrayList<Long>> strNum = new HashMap<>();
    private ArrayList<Long> finalNumbers = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private int maxer = 0;

    private class Node{
        private String word ="";
        private String preString = "";
        private long score = 0;
        private ArrayList<Integer> precursors = new ArrayList<>();

        public Node(){

        }
        public Node(String word){
            this.word= word;
            this.preString= word.substring(0,word.length()-1);
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getPreString() {
            return preString;
        }

        public void setPreString(String preString) {
            this.preString = preString;
        }

        public long getScore() {
            return score;
        }

        public void setScore(long score) {
            this.score = score;
        }

        public ArrayList<Integer> getPrecursors() {
            return precursors;
        }

        public void addPrecursors(int i){
            precursors.add(i);
        }

        public void setPrecursors(ArrayList<Integer> precursors) {
            this.precursors = precursors;
        }
    }

    /*
    Methods to test;

    CheckValidWords - potentially works
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        IbeforeE main = new IbeforeE();
        Boolean trigger = true;
        while (scan.hasNextLine()) {
            String use = scan.nextLine();
            if (!use.isBlank()) {
                if (trigger) {
                    main.params.add(use);
                } else {
                    main.instances.add(use);
                }
            } else {
                trigger = false;
            }
        }
        //Set the required rules
        main.setRules();
        ArrayList<String> words = main.getRuleSizeWords(main.alphabet, 4, 0);
        ArrayList<String> firstSets = main.checkedAlphabet(main.alphabet);
        main.finalNumbers.add((long) firstSets.size());
        main.nodeSet(words);
        //System.out.println(main.checkEndOfWord("aab"));
        //Find the amount of words per generation

        for(int i = 1; i <= main.maxer;i++){
            if(i<5){
                firstSets = main.countWords(firstSets);
                main.finalNumbers.add((long) firstSets.size());
            }else{

            }
        }
        System.out.println(words.size());
    }

    public void nodeSet(ArrayList<String> words){
        //Set up nodes of data, and use hashmap for faster comparison
        for(int i = 0; i<words.size();i++){
            String word = words.get(i);
            Node node = new Node(word);
            nodes.add(node);
            if(strNum.containsKey(word.substring(1))){
                strNum.get(word.substring(1)).add((long) i);
            }else{
                ArrayList<Long> newArray = new ArrayList<>();
                newArray.add((long)i);
                strNum.put(word.substring(1), newArray);
            }
        }
        //Find the precursor strings for each node
        for(int i = 0; i< nodes.size();i++){
            String word = nodes.get(i).getWord();
            String preword = nodes.get(i).getPreString();
            ArrayList<Long> pre = strNum.get(preword);
            ArrayList<Long> precursors = new ArrayList<>();
            for(int j = 0; j<pre.size();j++){
                String other = nodes.get(Math.toIntExact(pre.get(j))).getWord()+word.substring(word.length()-1);
                if(fullWordCheck(other)){
                    precursors.add(pre.get(j));
                }
            }
        }
    }

    //This will check the full word for exceptions, exception being the first characters
    //because these could be preceded by another word.
    public boolean fullWordCheck(String word){
        boolean check = true;

        return check;
    }

    public ArrayList<String> countWords(ArrayList<String> use) {
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < use.size(); i++) {
            for (int j = 0; j < alphabet.size(); j++) {
                String a = use.get(i) + alphabet.get(j);
                if (checkEndOfWord(a)) {
                    temp.add(a);
                }
            }
        }
        return temp;
    }








    public ArrayList<String> checkedAlphabet(ArrayList<String> use) {
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < use.size(); i++) {
            String a = use.get(i);
            boolean valid = true;
            if (rules.size() != 0) {
                for (int j = 0; j < rules.size(); j++) {
                    if (a.equals(rules.get(j)[0])) {
                        // System.out.println(a+" "+rules.get(j)[0]);
                        valid = false;
                        j = rules.size();
                    }
                }
            } else {
                temp.add(a);
            }
            if(valid){
                // doubles.put(a, true);
                temp.add(a);
            }
        }
        return temp;
    }

    public boolean checkEndOfWord(String a){
        boolean check = true;
        for(int i = 0; i< rules.size();i++){
            String rule = rules.get(i)[0];
            int ruleLen = rule.length();
            int indexRule = a.length()-ruleLen;
            if(ruleLen<=a.length()){
                if(a.substring(indexRule).equals(rule)) {
                    check = false;
                    //System.out.println(a.substring(indexRule));
                    for (int j = 1; j < rules.get(i).length; j++) {
                        int excLen = rules.get(i)[j].length();
                        if(indexRule-excLen>-1) {
                            if (a.substring(indexRule - excLen, indexRule).equals(rules.get(i)[j])) {
                                check = true;
                            }
                        }
                    }
                }
            }
            if(check== false){
                return false;
            }
        }
        return check;
    }

    public ArrayList<String> getRuleSizeWords(ArrayList<String> use, int index, int check){
        ArrayList<String> temp = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        if(check<index) {
            for (int i = 0; i < use.size(); i++) {
                for (int j = 0; j < alphabet.size(); j++) {
                    if(!map.containsKey(use.get(i) + alphabet.get(j))) {
                        temp.add(use.get(i) + alphabet.get(j));
                        map.put(use.get(i) + alphabet.get(j), use.get(i) + alphabet.get(j));
                    }
                }
            }
            return getRuleSizeWords(temp, index, check+1);
        }else {
            return use;
        }
    }

    public void setRules() {
        //Set alphabet
        for (int i = 0; i < params.get(0).length(); i++) {
            alphabet.add(params.get(0).substring(i, i + 1));
        }
        String[] ruler = new String[1];
        ruler[0] = "$$";
        rules.add(ruler);
        //Set rules, setting up array of arrays of rule
        //Each array/rule starts with the bad string, and the exceptions for that string are the following
        for (int i = 1; i < params.size(); i++) {
            String[] rule = params.get(i).split(" ");
            rules.add(rule);
        }
        //Find biggest number
        for (int i = 0; i < instances.size(); i++) {
            try {
                if (Integer.parseInt(instances.get(i)) > maxer) {
                    maxer = Integer.parseInt(instances.get(i));
                }
            } catch (NumberFormatException e) {
            }
        }
    }
}
