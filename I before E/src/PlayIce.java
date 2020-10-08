import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class PlayIce {
    private ArrayList<String> params = new ArrayList<String>();
    private ArrayList<String> instances = new ArrayList<String>();
    private ArrayList<String> alphabet = new ArrayList<String>();
    private ArrayList<String[]> rules = new ArrayList<>();
    private HashMap<Integer, Long> number = new HashMap<>();
    private ArrayList<ArrayList<Integer>> location = new ArrayList<>();
    private int leng = 0;
    private int maxer = 0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        PlayIce main = new PlayIce();
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
        main.setRules();
        ArrayList<String> use = new ArrayList<>();
        ArrayList<Integer> numUse = new ArrayList<>();
        use = main.initial(main.alphabet);
        main.leng = main.longestRule();

        //System.out.println(use);
        for (int i = 2; i <= main.maxer; i++) {
            if (i < main.leng) {
                use = main.amountOfWords(i, use);
                main.number.put(i, (long) use.size());
            } else {
                numUse = main.amountChecked(use, i);
            }
        }
        //System.out.println(main.number);
        // main.outPut();
    }

    public ArrayList<Integer> amountChecked(ArrayList<String> use, int index){
        ArrayList<Integer> temp = new ArrayList<>();
        if(index==leng){
            temp = rules(use);
        }
        for(int i = 0; i<location.size();i++){
            int num = 0;
            for(int j = 0; j<location.get(i).size();j++){
                num  = num + location.get(i).get(j);
            }
            temp.add(num);
        }

        return temp;
    }

    /*
    Sets up the temp array with the scores for each iteration so far, and sets up the location array

    Location array: One array for each word, specifying it's precursor words indices
     */
    public ArrayList<Integer> rules(ArrayList<String> use){
        ArrayList<Integer> temp = new ArrayList<>();
        for(int i = 0; i<use.size();i++){
            if(checkRules(use.get(i))){
                ArrayList<Integer> newArray = new ArrayList<>();
                location.add(newArray);
                temp.add(1);
            }else{
                ArrayList<Integer> newArray = new ArrayList<>();
                location.add(newArray);
                temp.add(0);
            }
        }
        for(int i = 0; i< location.size(); i++){
            String a = use.get(i);
            for(int j = 0; j<use.size();j++){
                if(precursor(a, use.get(j))){
                    location.get(i).add(j);
                }
            }
        }
        return temp;
    }

    public boolean precursor(String first, String second){
        boolean works = false;
        String checkA = second.substring(1)+first.substring(first.length()-2);
        String checkB = second + first.substring(first.length()-2);
        if(first.equals(checkA)){
            if(checkRules(checkB)){
                works = true;
            }
        }
        return works;
    }

    public int longestRule() {
        String longest = "";
        for (int i = 0; i < rules.size(); i++) {
            String rule = rules.get(i)[0];
            String len = "";
            for (int j = 1; j < rules.get(i)[j].length(); j++) {
                if (rules.get(i)[j].length() > len.length()) {
                    len = rules.get(i)[j];
                }
            }
            String utilise = rule + len;
            if (utilise.length() > longest.length()) {
                longest = utilise;
            }
        }
        return longest.length();
    }

    public void setRules() {
        //Set alphabet
        for (int i = 0; i < params.get(0).length(); i++) {
            alphabet.add(params.get(0).substring(i, i + 1));
        }
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

    public ArrayList<String> initial(ArrayList<String> use) {
        ArrayList<String> temp = new ArrayList<>();
        HashMap<String, Boolean> doubles = new HashMap<>();
        for (int i = 0; i < use.size(); i++) {
            String a = use.get(i);
            if (rules.size() != 0) {
                for (int j = 0; j < rules.size(); j++) {
                    if (!a.contains(rules.get(j)[0])) {
                        if (!doubles.containsKey(a)) {
                            doubles.put(a, true);
                            temp.add(a);
                        }
                    }
                }
            } else {
                temp.add(a);
            }
        }
        System.out.println(1 + " " + temp.size());
        number.put(1, (long) temp.size());
        return temp;
    }

    /*
    For each number up to N, find words that work for it.
    first letter, find possible words.
    For possible words, check for validity.
    if valid, add to array.

    create new UseArray
    For each word in , find possible new words with additional letter
    Check these new words for validity,
    if valid add to UseArray
    recurse with incremented number and useArray
     */
    public ArrayList<String> amountOfWords(int iteration, ArrayList<String> use) {
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < use.size(); i++) {
            for (int j = 0; j < alphabet.size(); j++) {
                String a = use.get(i) + alphabet.get(j);
                if (rules.size() != 0) {
                    if (checkRules(a)) {
                        temp.add(a);
                        //System.out.println(a);
                    }
                } else {
                    temp.add(a);
                }
            }
        }
        return temp;
    }

    public boolean checkRules(String a) {
        boolean valid = true;
        //Go through each rule
        for (int i = 0; i < rules.size(); i++) {
            int alpha = rules.get(i)[0].length();
            String bet = a.substring(a.length() - alpha);
            if (bet.equals(rules.get(i)[0])) {
                valid = false;
                for (int j = 1; j < rules.get(i).length; j++) {
                    int x = a.length() - alpha;
                    int y = (a.length() - alpha) - rules.get(i)[j].length();
                    String it = a.substring(y, x);
                    if (it.equals(rules.get(i)[j])) {
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }


    public void outPut() {
        for (int i = 0; i < instances.size(); i++) {
            try {
                if (Integer.parseInt(instances.get(i)) > -1) {
                    System.out.println(number.get(Integer.parseInt(instances.get(i))));
                }
            } catch (NumberFormatException e) {
                boolean type = true;
                if (!checkRules(instances.get(i))) {
                    type = false;
                }
                if (type) {
                    System.out.println("Valid");
                } else {
                    System.out.println("Invalid");
                }
            }
        }
    }
}
