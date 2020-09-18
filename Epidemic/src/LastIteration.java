import java.util.ArrayList;
import java.util.Scanner;

public class LastIteration {

    private ArrayList<ArrayList<String>> universe = new ArrayList<>();

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        LastIteration main = new LastIteration();
        ArrayList<String> alpha = new ArrayList<>();
        main.universe.add(alpha);
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            if(line.isBlank()){
                ArrayList<String> beta = new ArrayList<>();
                main.universe.add(beta);
            }else {
                main.universe.get(main.universe.size() - 1).add(line);
            }
        }
        for(int i = 0; i<main.universe.size(); i++) {
            main.findPerim(main.universe.get(i));
        }
    }

    public boolean findPerim(ArrayList<String> use){
        int length = use.get(0).length();
        int width = use.size();
        int immune = 0;
        int sick = 0;

        for(int i = 0; i<use.size();i++){
            for(int j = 0; j<use.get(i).length();j++){
                //System.out.println(use.get(i).substring(j,j+1));
                if(use.get(i).substring(j,j+1).equals("I")){
                    immune ++;
                }else if(use.get(i).substring(j,j+1).equals("S")){
                    sick ++;
                }
            }
        }
        int perim = (2*length) + (2*width) + (immune*4);
        if(sick>0){
            System.out.println("");
            finalState(use);
        }else{
            infect(length, width, use, immune);
        }
        return false;
    }


    public boolean infect(int length, int width, ArrayList<String> use, int immune) {

        if (length > width || length == width) {
            int count = 0;
            boolean reverse = false;
            for (int col = 0; col < use.get(0).length(); col++) {
                //System.out.println(col);
                String str = use.get(count).substring(col, col + 1);
                String rework = "";
                if (col == use.get(0).length() - 1) {
                    rework = use.get(count).substring(0, col) + "S";
                } else {

                    rework = use.get(count).substring(0, col) + "S" + use.get(count).substring(col + 1);
                }
                if (str.equals(".")) {
                    use.set(count, rework);
                }
                if (count == use.size() - 1 && !reverse) {
                    reverse = true;
                }
                if (count == 0 && reverse) {
                    reverse = false;
                }
                if (reverse) {
                    count--;
                } else {
                    count++;
                }
                //System.out.println(rework);
            }

        } else {
            int count = 0;
            boolean reverse = false;
            for(int row = 0; row<use.size();row++){
                String str = use.get(row).substring(count, count + 1);
                String rework = "";
                if (count == use.get(0).length() - 1) {
                    rework = use.get(row).substring(0, count) + "S";
                } else {
                    rework = use.get(row).substring(0, count) + "S" + use.get(row).substring(count + 1);
                }
                if (str.equals(".")) {
                    use.set(row, rework);
                }
                if (count == use.get(0).length() - 1 && !reverse) {
                    reverse = true;
                }
                if (count == 0 && reverse) {
                    reverse = false;
                }
                if (reverse) {
                    count--;
                } else {
                    count++;
                }
                //System.out.println(rework);
            }

        }
        finalState(use);
        return false;
    }


    public ArrayList<String> finalState(ArrayList<String> use){
        int rCount = 1;
//        for(int i = 0; i<use.size();i++) {
//            System.out.println(use.get(i));
//        }
//        System.out.println();
        while(rCount>0) {
            rCount = 0;
            for (int i = 0; i < use.size(); i++) {
                for (int j = 0; j < use.get(i).length(); j++) {
                    if (use.get(i).substring(j, j + 1).equals(".")) {
                        int neighbour = 0;
                        if (j != use.get(i).length() - 1) {
                            if (use.get(i).substring(j + 1, j + 2).equals("S")) {
                                neighbour++;
                            }
                        }
                        if (j != 0) {
                            if (use.get(i).substring(j - 1, j).equals("S")) {
                                neighbour++;
                            }
                        }
                        if (i != 0) {
                            if (use.get(i - 1).substring(j, j + 1).equals("S")) {
                                neighbour++;
                            }
                        }
                        if (i != use.size() - 1) {
                            if (use.get(i + 1).substring(j, j + 1).equals("S")) {
                                neighbour++;
                            }
                        }
                        if (neighbour > 1) {
                            String str = use.get(i);
                            String str1 = str.substring(0, j);
                            String str2 = str.substring(j + 1);
                            str = str1 + "R" + str2;
                            use.set(i, str);
                            rCount ++;
                        }
                    }
                }
            }
            for(int i = 0; i<use.size();i++) {
                use.set(i, use.get(i).replace("R", "S"));
            }
        }
        for(int i = 0; i<use.size();i++) {
            System.out.println(use.get(i));
        }
        System.out.println();
        return use;
    }

}
