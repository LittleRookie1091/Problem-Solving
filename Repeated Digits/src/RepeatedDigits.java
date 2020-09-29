import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepeatedDigits {
    public static void main(String[] args){
        ArrayList<String> lines = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        RepeatedDigits main = new RepeatedDigits();
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            lines.add(line);
        }

        for(int i = 0; i<lines.size(); i++){
            main.checkForRepeat(lines.get(i));
        }
    }


    public boolean checkForRepeat(String line){
        if(line.startsWith("A") || line.startsWith("B")) {
            boolean one = false;
            boolean two = false;
            String[] use = line.split(" ");
            try {
                int d = Integer.parseInt(use[1]);
                one = true;
            } catch (NumberFormatException nfe) {
            }
            try {
                int d = Integer.parseInt(use[2]);
                two = true;
            } catch (NumberFormatException nfe) {
            }
            if (use.length==3&& one && two) {
                if (use[0].equals("A")) {
                    partA(use);
                } else if(use[0].equals("B")){
                    partB(use);
                }else{
                    System.out.println("Bad line: "+line);
                }
            }else{
                System.out.println("Bad line: "+line);
            }
        }
        return false;
    }

    public ArrayList<Integer> counter(int base, ArrayList<Integer> use){
        use.set(use.size()-1, use.get(use.size()-1)+1);
        for(int i = use.size()-1; i>-1;i--){
            if(i>0){
                if(use.get(i)==base) {
                    use.set(i, 0);
                    use.set(i - 1, use.get(i - 1) + 1);
                }
            }else{
                if(use.get(i)==base) {
                    use.set(i, 0);
                    use.add(0, 1);
                }
            }
        }
        return use;
    }

    public boolean checkRepeat(ArrayList<Integer> use){
        HashMap<Integer, Integer> check = new HashMap<>();
        for(int i = 0; i<use.size();i++){
            if(check.containsKey(use.get(i))){
                return true;
            }else{
                check.put(use.get(i), 1);
            }
        }


        return false;
    }

    public boolean partA(String[] line){
        int n = Integer.parseInt(line[2]);
        int base = Integer.parseInt(line[1]);
        int count = 0;
        ArrayList<Integer> use = new ArrayList<>();
        use.add(0);
        int block =0;
        int start = 0;
        int finalBlock = 0;
        int finalStart = 0;

        while(count<n){
           boolean rep = checkRepeat(use);
           if(rep){
               block++;
               if(block == 1){
                   start = count;
               }
               if(block>finalBlock){
                   finalBlock = block;
                   finalStart = start;
               }
           }else{
               block = 0;
           }

           use = counter(base, use);
           count ++;
        }
        System.out.println(finalStart+" "+finalBlock);
        return true;
    }

    public boolean partB(String[] line) {
        int base1 = Integer.parseInt(line[1]);
        int base2 = Integer.parseInt(line[2]);
        int count = 0;
        ArrayList<Integer> one = new ArrayList<>();
        one.add(0);
        ArrayList<Integer> two = new ArrayList<>();
        two.add(0);
        boolean set = true;
        while(set) {
            if (checkRepeat(one) && checkRepeat(two)) {
                set = false;
                System.out.println(count);
            }
            one = counter(base1, one);
            two = counter(base2, two);
            count++;
        }
        return true;
    }
}
