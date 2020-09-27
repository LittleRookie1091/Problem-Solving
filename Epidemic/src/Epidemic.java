import java.util.*;

public class Epidemic {
    private ArrayList<ArrayList<String>> universe = new ArrayList<>();
    private int county = -1;
    private int area;
    private int amountOfS = 0;
    private ArrayList<String> theFinal;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Epidemic main = new Epidemic();
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
        int length = use.get(0).length();
        int width = use.size();
        int immune = 0;
        int sick = 0;

        for (int i = 0; i < use.size(); i++) {
            for (int j = 0; j < use.get(i).length(); j++) {
                //System.out.println(use.get(i).substring(j,j+1));
                if (use.get(i).substring(j, j + 1).equals("I")) {
                    immune++;
                } else if (use.get(i).substring(j, j + 1).equals("S")) {
                    sick++;
                }
            }
        }
        int perim = (2 * length) + (2 * width) + (immune * 4);
        if (sick > 0) {
            System.out.println("");
            finalState(use);
        } else {
            infect(length, width, use, immune);
        }
        return false;
    }

    /*
    Get grid and determine scores of each box.
    Scores based on following:
        If a block is blocked off from any others by I, it is compulsory
        If a block only has two I or wall neighbours, it has a +2 added
        If a block only has one I or wall neighbours, it has a +1 added
        If a block has 4 neighbours, nothing is added

    Lookahead, and prediction:
        If infecting a block gets one more neighbour infected, +1
            -Do this rule for increasing amount of new neighbours infected.
     */
    public boolean infect(int length, int width, ArrayList<String> use, int immune) {
        int efficient = mostEfficient(use);
        int bestScount = efficient/4;
        if(efficient%4 != 0){
            bestScount = (efficient+2)/4;
        }
        county = bestScount;
        use = rank(use);
        System.out.println(bestScount);
        use = lookAhead(use, bestScount-amountOfS);
        for (int i = 0; i < use.size(); i++) {
            System.out.println(use.get(i));
        }
        return false;
    }

    public int mostEfficient(ArrayList<String>use){
        int i = 0;
        int perim = use.size()*2 + use.get(0).length()*2;
        int touching = 0;
        int iLine =0;
        int variable = 0;
        for (int row = 0; row < use.size(); row++) {
            for (int col = 0; col < use.get(row).length(); col++) {
                if (use.get(row).substring(col, col + 1).equals("I")) {
                    touching = touching + iCheck(use, row, col);
                    i++;
                    iLine++;
                    if(iLine==use.get(row).length()-1){
                        variable = variable +2;
                    }
                }else {
                    iLine = 0;
                }
            }
        }
        //System.out.println("I: "+i);
        //System.out.println("Perim: "+perim);
        //System.out.println("Touching: "+touching);
        return (((i*4)+perim)-touching+variable);
    }
    //Rank should  return an arraylist with infected in compulsary places
    //It could also return a second arraylist with ranking of squares.
    public ArrayList<String> rank(ArrayList<String> use) {

        for (int row = 0; row < use.size(); row++) {
            for (int col = 0; col < use.get(row).length(); col++) {
                if (use.get(row).substring(col, col + 1).equals(".")) {
                    use = neighbourCheck(use, row, col);
                }
            }
        }
        return use;
    }

    
    /*
    Creates a weight array with 0's, goes through the possible S positions, and sees outcome
    of them, then weights accordingly.
    returns the weight array.
     */
    private int ba = 0;
    public ArrayList<ArrayList<Integer>> getNew(ArrayList<String> use, int on, int depth){
        //use = finalState(use);
//                for(int ad = 0; ad<use.size();ad++){
//                           System.out.println(use.get(ad));
//                        }
        ArrayList<ArrayList<Integer>> weight = new ArrayList<>();
        //Filling weight array to be same size as use
        for(int i  = 0; i<use.size();i++){
            ArrayList<Integer> a = new ArrayList<>();
            weight.add(a);
            for(int j =0;j<use.get(0).length();j++){
                weight.get(i).add(0);
            }
        }

        //If I is found, the weight is set negative 30
        //If S is found creates a temp array with all possible S locations
        int g = 0;
        for (int row = 0; row < use.size(); row++) {
            for (int col = 0; col < use.get(row).length(); col++) {
                //System.out.println(g);
                g++;
                if (use.get(row).substring(col, col + 1).equals("I")) {
                    weight.get(row).set(col, -45);
                }
                    //System.out.println("Checking "+col);
                if (use.get(row).substring(col, col + 1).equals("S")) {
                    weight.get(row).set(col, -30);

                }
                //Creates a temp array version of use and replaces  a single . with S, and then at the ahead
                //possibilities according to look ahead.
                if (use.get(row).substring(col, col + 1).equals(".")) {
                        //Set the . to an S
                        ArrayList<String> temp = new ArrayList<>();
                        for (int i = 0; i < use.size(); i++) {
                           // System.out.println(use.get(i));
                            temp.add(use.get(i));
                        }
                    ArrayList<String> temp2 = new ArrayList<>();
                    for (int i = 0; i < use.size(); i++) {
                        // System.out.println(use.get(i));
                        temp2.add(use.get(i));
                    }
                        temp = finalState(temp);
                       // System.out.println();
                        String str = temp.get(row);
                        if (col == temp.get(row).length() - 1) {
                            str = str.substring(0, col) + "S";
                        } else {
                            str = str.substring(0, col) + "S" + str.substring(col + 1);
                        }
                        String str1 = temp.get(row);
                        if (col == temp2.get(row).length() - 1) {
                            str1 = str1.substring(0, col) + "S";
                        } else {
                            str1 = str1.substring(0, col) + "S" + str1.substring(col + 1);
                        }
                        temp.set(row, str);
                        temp2.set(row, str1);

                        ba = 0;
                            temp = finalState(temp);
                            int stop = 0;
                            for(int check = 0; check <temp.size();check++){
                                for(int check2 = 0; check2<temp.get(check).length(); check2++){
                                   if(temp.get(check).substring(check2,check2+1).equals("S")){
                                       stop ++;
                                   }
                                }
                            }
                            int set = 0;
                           // if(stop<=county){
                                set = depth(temp, depth, temp2);
                           //}

                            if(set==-5){
                                for(int i  = 0; i<use.size();i++){
                                    for(int j =0;j<use.get(0).length();j++){
                                        weight.get(i).set(j, -900);
                                    }
                                }
                                return weight;
                            }
                            weight.get(row).set(col, set);
                }
            }
        }

        return weight;
    }

    public boolean s3InARow( ArrayList<String> tempBones){
        int x = 0;
        for(int i = 0; i<tempBones.size();i++){
            for(int j = 0; j<tempBones.get(i).length();j++){
                if(tempBones.get(i).substring(j,j+1).equals("S")){
                    x++;
                }else{
                    x = 0;
                }
                if(x >=3){
                    return false;
                }
            }
        }
        x =0;
        for(int i = 0; i<tempBones.get(0).length();i++){
            for(int j = 0; j<tempBones.size();j++){
                if(tempBones.get(j).substring(i,i+1).equals("S")){
                    x++;
                }else{
                    x = 0;
                }
                if(x >=3){
                    return false;
                }
            }
        }
        return true;
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
//        for (int i = 0; i < use.size(); i++) {
//            System.out.println(use.get(i));
//        }
//        System.out.println();
        return use;
    }

    /*
    This will check the surrounding neighbours and count how many
    I's or walls there are.
     */
    public ArrayList<String> neighbourCheck(ArrayList<String> use, int row, int col) {
        int count = 0;
        //Check right side of .
        if(col==use.get(0).length()-1){
            count++;
        }else if(use.get(row).substring(col+1,col+2).equals("I")){
            count ++;
        }

        //Check left side of .
        if(col==0){
            count++;
        }else if(use.get(row).substring(col-1,col).equals("I")){
            count++;
        }

        //Check above
        if(row==0){
            count++;
        }else if(use.get(row-1).substring(col, col+1).equals("I")){
            count ++;
        }

        //check below
        if(row == use.size()-1){
            count++;
        }else if(use.get(row+1).substring(col, col+1).equals("I")){
            count++;
        }

        if(count>=3){
            String infect = use.get(row);
            infect = infect.substring(0,col) +"S"+infect.substring(col+1);
            use.set(row, infect);
            amountOfS = amountOfS +1;
        }
        return use;
    }

    /*
    Will check an I for surrounding I's, this is to find the most efficient number
    of I's to use. A wall surrounding an I is also checked for
     */
    public int iCheck(ArrayList<String> use, int row, int col) {
        int count = 0;
        //Check right side of .
        if(col==use.get(0).length()-1){
            count +=2;
        }else if(use.get(row).substring(col+1,col+2).equals("I")){
            count ++;
        }

        //Check left side of .
        if(col==0){
            count+=2;
        }else if(use.get(row).substring(col-1,col).equals("I")){
            count++;
        }

        //Check above
        if(row==0){
            count+=2;
        }else if(use.get(row-1).substring(col, col+1).equals("I")){
            count ++;
        }

        //check below
        if(row == use.size()-1){
            count+=2;
        }else if(use.get(row+1).substring(col, col+1).equals("I")){
            count++;
        }
        return count;
    }
}