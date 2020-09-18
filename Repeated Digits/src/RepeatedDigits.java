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

            String[] use = line.split(" ");
            System.out.println(Arrays.deepToString(use)+" "+ use.length);
            if (use.length==3) {
                if (use[0].equals("A")) {
                    System.out.println("A");
                    partA(use);
                } else {
                    System.out.println("B");
                    partB(use);
                }
            }
        }
        return false;
    }

    public boolean partA(String[] line){
        int n = Integer.parseInt(line[2]);
        int base = Integer.parseInt(line[1]);
        int count = 0;
        int current = 0;
        int blockCount = 0;
        int finalBlockCount= 0;
        int blockInt = 0;
        int finalBlockInt = 0;
        boolean isBlock = false;
        boolean increment = true;
        ArrayList<String> use = new ArrayList<>();
        ArrayList<Integer> number = new ArrayList<>();
        number.add(0);
        number.add(0);

        while(count<n){
            if(increment) {
                number.set(number.size() - 1, number.get(number.size() - 1) + 1);
            }else{
                increment = true;
            }
            count ++;
            System.out.println(number+ " "+ " "+ count);
            //Will add numbers to start of array as number gets bigger
            if(base == number.get(number.size()-1)){
                number.set(number.size()-1, 0);
                increment = false;
                number.set(number.size()-2,number.get(number.size()-2)+1);
                for(int i = number.size()-1; i>-1; i--){
                    if(number.get(i)==base){
                        number.set(i, 0);
                        if(i == 0){
                            number.add(0,1);
                        }else{
                            number.set(i-1, number.get(i-1)+1);
                        }
                    }
                }
            }

            boolean isRepeat = false;
            if(increment) {
                for (int i = 0; i < number.size(); i++) {
                    int check = 0;
                    for (int j = 0; j < number.size(); j++) {
                        if (i != j) {
                            if (number.get(i) == number.get(j)) {
                                //System.out.println(number + "   " + "c");
                                System.out.println(number.get(i) + " " + number.get(j) + " " + blockInt);
                                isRepeat = true;
                                check++;
                                if (check > blockCount) {
                                    isBlock = true;
                                }
                            }
                        }
                    }
                }
            }
            if(!isRepeat){
                blockCount = 0;
            }

            if(isBlock){
                blockCount ++;
                if(blockCount == 1){
                    blockInt = count;
                }
                if(blockCount> finalBlockCount){
                    finalBlockCount = blockCount;
                    finalBlockInt = blockInt;
                }
            }
        }
        System.out.println(finalBlockInt+1+" "+finalBlockCount);
        return true;
    }

    public boolean partB(String[] line) {
        int base1 = Integer.parseInt(line[1]);
        int base2 = Integer.parseInt(line[2]);
        boolean done = true;
        int base1Count = 0;
        int base2Count = 0;
        boolean increment1 = true;
        boolean increment2 = true;
        int currentCountDec = 0;
        ArrayList<Integer> base1Number = new ArrayList<>();
        base1Number.add(0);
        base1Number.add(0);
        ArrayList<Integer> base2Number = new ArrayList<>();
        base2Number.add(0);
        base2Number.add(0);

        while(done) {
            base1Count ++;
            base2Count ++;
            currentCountDec ++;

            if(increment1) {
                base1Number.set(base1Number.size() - 1, base1Number.get(base1Number.size() - 1) + 1);
            }else{
                increment1 = true;
            }
            //System.out.println(base1Number+ " "+ current+ " "+ count);
            //Will add numbers to start of array as number gets bigger
            base1Count++;
            if(base1 == base1Number.get(base1Number.size()-1)){
                base1Number.set(base1Number.size()-1, 0);
                increment1 = false;
                base1Number.set(base1Number.size()-2,base1Number.get(base1Number.size()-2)+1);
                for(int i = base1Number.size()-1; i>-1; i--){
                    if(base1Number.get(i)==base1){
                        base1Number.set(i, 0);
                        if(i == 0){
                            base1Number.add(0,1);
                        }else{
                            base1Number.set(i-1, base1Number.get(i-1)+1);
                        }
                    }
                }
            }
            if(increment2) {
                base2Number.set(base2Number.size() - 1, base2Number.get(base2Number.size() - 1) + 1);
            }else{
                increment2 = true;
            }
            base2Count ++;
            //Will add numbers to start of array as number gets bigger
            if(base2 == base2Number.get(base2Number.size()-1)){
                base2Number.set(base2Number.size()-1, 0);
                increment2 = false;
                base2Number.set(base2Number.size()-2,base2Number.get(base2Number.size()-2)+1);
                for(int i = base2Number.size()-1; i>-1; i--){
                    if(base2Number.get(i)==base2){
                        base2Number.set(i, 0);
                        if(i == 0){
                            base2Number.add(0,1);
                        }else{
                            base2Number.set(i-1, base2Number.get(i-1)+1);
                        }
                    }
                }
            }
            if (base2 == base2Count) {
                base2Count = 0;
                base2Number.set(base2Number.size() - 1, base2Number.get(base2Number.size() - 1) + 1);
                for (int i = base2Number.size() - 1; i > -1; i--) {
                    if (base2Number.get(i) == base2) {
                        base2Number.set(i, 0);
                        if (i == 0) {
                            base2Number.add(0, 1);
                        } else {
                            base2Number.set(i - 1, base2Number.get(i - 1) + 1);
                        }
                    }
                }
            }
            boolean b1 = false;
            boolean b2 = false;
            for(int i = 0; i<base1Number.size(); i++){
                for(int j = 0; j<base1Number.size(); j++){
                    if(i != j){
                        //System.out.println(base1Number+" "+base2Number);
                        if(base1Number.get(i)== base1Number.get(j)){
                            b1 = true;
                        }
                    }
                }
            }
            for(int i = 0; i<base2Number.size(); i++){
                for(int j = 0; j<base2Number.size(); j++){
                    if(i != j){
                        if(base2Number.get(i)== base2Number.get(j)){
                            b2 = true;
                        }
                    }
                }
            }

            if(b2 && b1){
                done = false;
            }
           // System.out.println(base2Number+" "+base1Number+" "+currentCountDec);
        }
        System.out.println(currentCountDec);
        return true;
    }

}
