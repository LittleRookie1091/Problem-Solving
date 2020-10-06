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
    private int maxer = 0;

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        PlayIce main = new PlayIce();
        Boolean trigger = true;
        while(scan.hasNextLine()){
            String use = scan.nextLine();
            if(!use.isBlank()) {
                if(trigger) {
                    main.params.add(use);
                }else{
                    main.instances.add(use);
                }
            }else{
                trigger = false;
            }
        }
        main.setRules();
        ArrayList<String> use = new ArrayList<>();
        use = main.initial(main.alphabet);
        int leng = main.longestRule();
        //System.out.println(use);
        for(int i = 2;i<=main.maxer;i++){
            if(i<leng) {
                use = main.amountOfWords(i, use);
            }else{

            }
        }
        //System.out.println(main.number);
       // main.outPut();
    }

    public int longestRule(){
        String longest = "";
        for(int i = 0; i<rules.size();i++){
            String rule = rules.get(i)[0];
            String len = "";
            for(int j = 1; j<rules.get(i)[j].length();j++){
                if(rules.get(i)[j].length()>len.length()) {
                    len = rules.get(i)[j];
                }
            }
            String utilise = rule + len;
            if(utilise.length()>longest.length()){
                longest = utilise;
            }
        }
        return longest.length();
    }

    
    public void setRules(){
        //Set alphabet
        for(int i = 0; i< params.get(0).length();i++){
            alphabet.add(params.get(0).substring(i,i+1));
        }
        //Set rules, setting up array of arrays of rule
        //Each array/rule starts with the bad string, and the exceptions for that string are the following
        for(int i = 1; i< params.size();i++){
            String[] rule = params.get(i).split(" ");
            rules.add(rule);
        }
        //Find biggest number
        for(int i=0;i<instances.size();i++){
            try {
                if(Integer.parseInt(instances.get(i))>maxer){
                    maxer = Integer.parseInt(instances.get(i));
                }
            } catch (NumberFormatException e) {
            }
        }
    }

    public ArrayList<String> initial(ArrayList<String> use){
        ArrayList<String> temp = new ArrayList<>();
        HashMap<String, Boolean> doubles = new HashMap<>();
        for(int i =0; i<use.size();i++){
            String a = use.get(i);
            if(rules.size()!=0) {
                for (int j = 0; j < rules.size(); j++) {
                    if (!a.contains(rules.get(j)[0])) {
                        if (!doubles.containsKey(a)) {
                            doubles.put(a, true);
                            temp.add(a);
                        }
                    }
                }
            }else{
                temp.add(a);
            }
        }
        System.out.println(1+" "+temp.size());
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
            HashMap<String, Boolean> doubles = new HashMap<>();
            ArrayList<String> temp = new ArrayList<>();
            for (int i = 0; i < use.size(); i++) {
                for (int j = 0; j < alphabet.size(); j++) {
                    String a = use.get(i) + alphabet.get(j);
                    if(!doubles.containsKey(a)) {
                        doubles.put(a, true);
                        if(rules.size()!=0) {
                            if (checkRules(a, i, j, use)) {
                                temp.add(a);
                                //System.out.println(a);
                            }
                        }else{
                            temp.add(a);
                        }
                    }
                }
            }
            number.put(iteration, (long) temp.size());
            return temp;
    }

    public boolean checkRules(String a, int i, int j, ArrayList<String> use) {
        boolean check = true;
        //Go through each rule
        for (int r = 0; r < rules.size(); r++) {
            //Check if the bad letter sequence is present
            if (a.lastIndexOf(rules.get(r)[0]) != -1) {
                check = false;
                int last = a.lastIndexOf(rules.get(r)[0]);
                //Check if exception for bad letter sequence is present
                for (int exc = 1; exc < rules.get(r).length; exc++) {
                    String exception = rules.get(r)[exc];
                    int len = exception.length();
                    String b = use.get(i) + alphabet.get(j);
                    //If word is smaller than exception then it cant have it
                    if ((last - len) > -1) {
                        int point = (last - len);
                        b = b.substring(point, last);
                        if (b.contains(exception)) {
                            check = true;
                        }
                    }
                }
            }
        }
        return check;
    }



    public void outPut(){
        for(int i = 0; i<instances.size();i++){
            try {
                if(Integer.parseInt(instances.get(i))>-1){
                    System.out.println(number.get(Integer.parseInt(instances.get(i))));
                }
            } catch (NumberFormatException e) {
                boolean type = true;
                for(int j = 0; j<rules.size();j++){
                    if(instances.get(i).contains(rules.get(j)[0])){
                        if(!checkWord(instances.get(i), j)){
                            type = false;
                        }
                    }
                }
                if(type){
                    System.out.println("Valid");
                }else{
                    System.out.println("Invalid");
                }
            }
        }
    }

    public boolean checkWord(String word, int j){
        String exc = rules.get(j)[0];
        int place = word.indexOf(exc);
        for(int i =0; i<rules.get(j).length;i++){
            String pro = rules.get(j)[i];
            if(word.substring(place-pro.length(),place).contains(pro)){
                return true;
            }
        }
        return false;
    }
}