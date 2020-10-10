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
    private HashMap<String, Long> iterations = new HashMap<>();
    private HashMap<String, Long> previous = new HashMap<>();
    private int maxer = 0;

    private class Node{
        private String word ="";
        private String preString = "";
        private long score = 0;
        private long newScore = 0;
        private ArrayList<Integer> precursors = new ArrayList<>();

        public long getNewScore() {
            return newScore;
        }

        public void setNewScore(long newScore) {
            this.newScore = newScore;
        }
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
        //main.nodeSet(words);
        //System.out.println(main.checkRulesWord("ab"));
        //Find the amount of words per generation

        for(int i = 1; i <= main.maxer;i++){
            if(i<5){
                firstSets = main.countWords(firstSets);
                System.out.println(i);
                main.finalNumbers.add((long) firstSets.size());
            }else{
                if(i==5){
                    System.out.println(i);
                    for(int j = 0; j<firstSets.size(); j++){
                        main.previous.put(firstSets.get(j), (long) 1);
                    }
                    firstSets = main.iteratorOfGods(firstSets);
                }else{
                    System.out.println(i);
                    firstSets = main.iteratorOfGods(firstSets);
                }
                //long total = main.findIterations();
                //main.finalNumbers.add(total);
            }
        }
        System.out.println(main.finalNumbers);
    }

    public ArrayList<String> iteratorOfGods(ArrayList<String> words){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i< words.size(); i++){
            System.out.println(i);
            String word = words.get(i);
            long num = previous.get(word);
            for(int j =0 ; j<alphabet.size();j++){
                String newWord = word.substring(1)+alphabet.get(j);
                if(checkEndOfWord(newWord)){
                    if(iterations.containsKey(newWord)){
                        long a = iterations.get(newWord);
                        iterations.replace(newWord, a+num);
                    }else{
                        iterations.put(newWord, num);
                        temp.add(newWord);
                    }
                }
            }
        }
        long total = 0;
        for(int i = 0; i<temp.size();i++){
            total = total + iterations.get(temp.get(i));
        }
        finalNumbers.add(total);
        previous = iterations;
        iterations = new HashMap<>();
        return temp;
    }

    public long findIterations(){
        System.out.println("Entered");
        System.out.println();
        for(int i = 0; i< nodes.size(); i++){
            System.out.println("Node: "+i +" "+nodes.get(i).getWord());
            System.out.println();
            long num = 0;
            if(checkRulesWord(nodes.get(i).getWord())) {
                ArrayList<Integer> precursors = nodes.get(i).getPrecursors();
                for (int j = 0; j < precursors.size(); j++) {
                    System.out.println("Precursors: " + nodes.get(precursors.get(j)).getWord());
                    System.out.println();
                    num = num + nodes.get(precursors.get(j)).getScore();
                }
            }
            nodes.get(i).setNewScore(num);
        }
        long total = 0;
        for(int i = 0; i< nodes.size(); i++){
            nodes.get(i).setScore(nodes.get(i).getNewScore());
            total = total + nodes.get(i).getNewScore();
        }
        return total;
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
        //Set score of each node
        for(int i = 0; i< nodes.size();i++){
            String word = nodes.get(i).getWord();
           // System.out.println(word);
           // System.out.println();
            if(checkRulesWord(word)){
                nodes.get(i).setScore(1);
              //  System.out.println(1);
            }else{
                nodes.get(i).setScore(0);
             //   System.out.println(0);
            }
            String preword = nodes.get(i).getPreString();
            ArrayList<Long> pre = strNum.get(preword);
            ArrayList<Integer> precursors = new ArrayList<>();
            for(int j = 0; j<pre.size();j++){
                //System.out.println(nodes.get(Math.toIntExact(pre.get(j))).getWord());
                String other = nodes.get(Math.toIntExact(pre.get(j))).getWord()+word.substring(word.length()-1);
                precursors.add(Math.toIntExact(pre.get(j)));
                nodes.get(i).setPrecursors(precursors);
            }
            //System.out.println();
            //System.out.println();
        }
    }

    //This will check the full word for exceptions, exception being the first characters
    //because these could be preceded by another word.
    public boolean checkRulesWord(String x) {
        boolean valid = true;
        //System.out.println(x);
        //Go through each rule
        for(int i = 0; i<rules.size(); i++){
            String a = x;
            int place = 0;
            while(a.indexOf(rules.get(i)[0], place)!= -1){
                valid = false;
                int index = a.indexOf(rules.get(i)[0], place);
                place = index+rules.get(i)[0].length();
                if(!(rules.get(i).length<2)) {
                    for (int j = 1; j < rules.get(i).length; j++) {
                        int num = rules.get(i)[j].length();
                        if (index - num > -1) {
                            //System.out.println();
                            // System.out.println(rules.get(i)[0]+" "+rules.get(i)[j]+" "+j+" " + a);
                            String b = a.substring(index - num, index);
                            if (b.equals(rules.get(i)[j])) {
                                valid = true;
                                j = rules.get(i).length;
                            } else {
                                valid = false;
                            }
                        }else if(index==0){
                            return false;
                        }
                        //  a = a.substring(index + rules.get(i)[0].length());
                    }
                }else{
                    return valid;
                }
            }
        }
        return valid;
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
