import java.util.*;

public class EpidemicFinalState {
private ArrayList<ArrayList<String>> universe = new ArrayList<>();
public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
    EpidemicFinalState main = new EpidemicFinalState();
        ArrayList<String> alpha = new ArrayList<>();
        main.universe.add(alpha);
        while (scan.hasNextLine()) {
        String line = scan.nextLine();
        if (line.isBlank()) {
        ArrayList<String> beta = new ArrayList<>();
        main.universe.add(beta);
        } else {
        main.universe.get(main.universe.size() - 1).add(line);
        }
        }
        for (int i = 0; i < main.universe.size(); i++) {
        main.findPerim(main.universe.get(i));
        }
        }

public boolean findPerim(ArrayList<String> use) {
        finalState(use);
        return false;
        }


public ArrayList<String> finalState(ArrayList<String> use) {
        int rCount = 1;
//        for(int i = 0; i<use.size();i++) {
//            System.out.println(use.get(i));
//        }
//        System.out.println();
        while (rCount > 0) {
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
        rCount++;
        }
        }
        }
        }
        for (int i = 0; i < use.size(); i++) {
        use.set(i, use.get(i).replace("R", "S"));
        }
        }
        for (int i = 0; i < use.size(); i++) {
            System.out.println(use.get(i));
        }
        System.out.println();
        return use;
        }

        }