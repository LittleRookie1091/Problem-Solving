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
    private ArrayList<String> strings = new ArrayList<>();
    private int leng = 0;
    private int maxer = 0;


    /*
    Methods to check:
    precursor +
    setRules +
    initial +
    checkrules +
    setFirst +
    checkrulesWord
     */
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
        ArrayList<Long> numUse = new ArrayList<>();
        use = main.initial(main.alphabet);
        main.number.put(1, (long)use.size());
        main.leng = main.longestRule();
        int x = 0;
        for (int i = 2; i <= main.maxer; i++) {

           // until we get to longest rule length
                if (i < main.leng) {
                    use = main.amountOfWords(i, use);
                    main.number.put(i, (long) use.size());
                } else {
                    for(int ex = 0 ; ex< main.rules.size(); ex ++){
                       // System.out.println(main.rules.get(ex)[0]);
                    }
                    if (i == main.leng) {
                        numUse = main.amountCheckedFirst(use, numUse, i);
                    } else {
                        numUse = main.amountChecked(use, numUse, i);
                    }
                }
/*
            System.out.println(i);
            main.printer(numUse, i);
            System.out.println();
            System.out.println();

 */
        }
        main.outPut();


    }

    //This will go up to the index in getting all words that fit, including those that may cause cases
    //on their own.
    public ArrayList<String> setFirst(ArrayList<String> use, int index, int check){
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
            //System.out.println(check);
            return setFirst(temp, index, check+1);
        }else {
            return use;
        }

    }

    public ArrayList<Long> amountCheckedFirst(ArrayList<String> use, ArrayList<Long> temp, int index){
        ArrayList<String> it = setFirst(alphabet, leng, 1);
        //System.out.println(it.size());
        temp = rules(it);
       //printer(temp, index);
        long total = 0;
        for (int i = 0; i < location.size(); i++) {
            long num = 0;
            for (int j = 0; j < location.get(i).size(); j++) {
                num = num + temp.get(location.get(i).get(j));
            }
           total = total + temp.get(i);
        }
        //System.out.println("Here");
        number.put(index, total);
        //System.out.println(temp);
        //System.out.println(location);
        return temp;
    }

    public ArrayList<Long> amountChecked(ArrayList<String> use, ArrayList<Long> temp, int index){
            ArrayList<Long> newTemp = new ArrayList<>();
           // printer(temp, index);
            long total = 0;
            for (int i = 0; i < location.size(); i++) {
                long num = 0;
                for (int j = 0; j < location.get(i).size(); j++) {
                    num = num + temp.get(location.get(i).get(j));
                }
                newTemp.add(num);
                total = total + num;
            }

            number.put(index, total);
            return newTemp;
    }

    public void printer(ArrayList<Long> temp, int index){
        System.out.println(number.get(index));

        for(int i = 0; i< temp.size(); i++){
            System.out.println(i+"  "+strings.get(i)+" "+ temp.get(i)+" Locations: "+location.get(i));

        }


    }


    /*
    Sets up the temp array with the scores for each iteration so far, and sets up the location array

    Location array: One array for each word, specifying it's precursor words indices
     */
    public ArrayList<Long> rules(ArrayList<String> use){
        ArrayList<Long> temp = new ArrayList<>();
        for(int i = 0; i<use.size();i++){
            if(checkRulesWord(use.get(i))){
                ArrayList<Integer> newArray = new ArrayList<>();
                location.add(newArray);
                //System.out.println("true");
                strings.add(use.get(i));
                temp.add((long) 1);
            }else{
               // if(checkRulesWord(use.get(i))) {
                    ArrayList<Integer> newArray = new ArrayList<>();
                    location.add(newArray);
                    //System.out.println("false");
                    strings.add(use.get(i));
                    temp.add((long) 0);
               // }
            }
        }
        for(long i = 0; i< location.size(); i++){
            String a = use.get((int) i);
          //  System.out.println("HERE OMG" + location.size());
            for(int j = 0; j<use.size();j++){
                if(precursor(a, use.get(j))){
                    location.get((int) i).add(j);
                }
            }
        }
        return temp;
    }

    public boolean precursor(String first, String second){
        boolean works = false;
        String checkA = second.substring(1)+first.substring(first.length()-1);
        String checkB = second + first.substring(first.length()-1);
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
            for (int j = 1; j < rules.get(i).length; j++) {
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

    public ArrayList<String> initial(ArrayList<String> use) {
        ArrayList<String> temp = new ArrayList<>();
        HashMap<String, Boolean> doubles = new HashMap<>();
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
        //.println(1 + " " + temp.size());
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
        //System.out.println(a);
        //Go through each rule
        for (int i = 0; i < rules.size(); i++) {
            int alpha = rules.get(i)[0].length();
            if(!(alpha>a.length())) {
                String bet = a.substring(a.length() - alpha);
                if (bet.equals(rules.get(i)[0])) {
                    valid = false;
                    //System.out.println("Rule found "+ rules.get(i)[0]);
                    for (int j = 1; j < rules.get(i).length; j++) {
                        int x = a.length() - alpha;
                        int y = (a.length() - alpha) - rules.get(i)[j].length();
                        if(y>-1) {
                            String it = a.substring(y, x);
                            if (it.equals(rules.get(i)[j])) {
                                valid = true;
                            }
                        }
                    }
                }
            }
        }
        //System.out.println(valid);
        return valid;
    }

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
                        if (index - num < 0) {
                            return false;
                        }
                        //System.out.println();
                       // System.out.println(rules.get(i)[0]+" "+rules.get(i)[j]+" "+j+" " + a);
                        String b = a.substring(index - num, index);
                        if (b.equals(rules.get(i)[j])) {
                            valid = true;
                            j = rules.get(i).length;
                        } else {
                            // System.out.println(b+" "+a+ " "+rules.get(i)[j] );
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

    public ArrayList<String> setUpArray(ArrayList<String> use) {
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
        //.println(1 + " " + temp.size());
        number.put(1, (long) temp.size());
        return temp;
    }

    public void outPut() {
        for (int i = 0; i < instances.size(); i++) {
            try {
                if (Integer.parseInt(instances.get(i)) > -1) {
                    System.out.println(number.get(Integer.parseInt(instances.get(i))));
                }
            } catch (NumberFormatException e) {
                boolean type = true;
                for(int x = 0; x< instances.get(i).length();x++){
                    if(!alphabet.contains(instances.get(i).substring(x,x+1))){
                        type = false;
                    }
                }
                if (!checkRulesWord(instances.get(i))) {
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
