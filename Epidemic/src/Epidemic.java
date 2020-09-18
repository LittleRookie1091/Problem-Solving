import java.util.*;

public class Epidemic {
    private ArrayList<ArrayList<String>> universe = new ArrayList<>();

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
        System.out.println(efficient+ " "+ bestScount);
        use = rank(use);
        use = lookAhead(use, 3);
        for (int i = 0; i < use.size(); i++) {
            System.out.println(use.get(i));
        }
        return false;
    }

    public int mostEfficient(ArrayList<String>use){
        int i = 0;
        int perim = use.size()*2 + use.get(0).length()*2;
        int touching = 0;
        for (int row = 0; row < use.size(); row++) {
            for (int col = 0; col < use.get(row).length(); col++) {
                if (use.get(row).substring(col, col + 1).equals("I")) {
                    touching = touching + iCheck(use, row, col);
                    i++;
                }
            }
        }
        //System.out.println("I: "+i);
        //System.out.println("Perim: "+perim);
        //System.out.println("Touching: "+touching);
        return (((i*4)+perim)-touching);
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


    //Lookahead should find the optimal solution with a certain amount
    //of lookaheads.
    //Look at the results of placing an S within 2 blocks of another S, the one with
    //the most blocks gained is the next S placed.
    //
    //Will use finalState to find outcome of each S  added.
    //count the amount of empty squares left, the S that results in the least
    //amount of empty squares is next best option.
    public ArrayList<String> lookAhead(ArrayList<String> use, int lookAheads) {
        boolean complete = true;
        int on = 1;
        while(complete) {
            //Weight array

            ArrayList<String> temp1 = new ArrayList<>();
            for(int i = 0; i<use.size();i++){
                temp1.add(use.get(i));
            }
            ArrayList<ArrayList<Integer>> weight = getNew(temp1, on);
            on++;

            //System.out.println("Here is achieved");
            int col = 0;
            int row = 0;
            int biggest = 100;
            //Find biggest weight
            for (int i = 0; i < weight.size(); i++) {
                for (int j = 0; j < weight.get(i).size(); j++) {
                    if(on<15) {
                      // System.out.print(weight.get(i).get(j)+" ");
                    }
                    if (weight.get(i).get(j) < biggest && weight.get(i).get(j)>0) {
                        col = j;
                        row = i;
                        biggest = weight.get(i).get(j);
                    }
                }
//                if(on<15) {
//                    System.out.println();
//                }

            }
//            if(on<15) {
//                System.out.println();
//            }
            //Set new string

            String str = use.get(row);
            if(col==use.get(row).length()-1){
                str = str.substring(0, col) + "S";
            }else {
                str = str.substring(0, col) + "S" + str.substring(col + 1);
            }
            //System.out.println(row+" "+col);
            use.set(row, str);
            ArrayList<String> temp = new ArrayList<>();
            for(int i = 0; i<use.size();i++){
                temp.add(use.get(i));
            }
            ArrayList<String> finish = finalState(temp);
            int set = 0;
            for (int rowCheck = 0; rowCheck< use.size(); rowCheck++) {
                for (int colCheck = 0; colCheck < use.get(rowCheck).length(); colCheck++) {
                    if(finish.get(rowCheck).substring(colCheck, colCheck+1).equals(".")){
                        set ++;
                    }
                }
            }
            if(set == 0){
                complete = false;
            }

        }


        return use;
    }

    /*
    Creates a weight array with 0's, goes through the possible S positions, and sees outcome
    of them, then weights accordingly.
    returns the weight array.
     */
    public ArrayList<ArrayList<Integer>> getNew(ArrayList<String> use, int on){
        if(on<15){
            for(int i =0; i<use.size();i++){
                System.out.println(use.get(i));
            }
            System.out.println();
        }
        use = finalState(use);
        ArrayList<ArrayList<Integer>> weight = new ArrayList<>();
        //Filling weight array to be same size as use
        for(int i  = 0; i<use.size();i++){
            ArrayList<Integer> a = new ArrayList<>();
            weight.add(a);
            for(int j =0;j<use.get(0).length();j++){
                weight.get(i).add(0);
            }
        }

        //If I is found, the wieght is set negative 30
        //If S is found creates a temp array with all possible S locations
        for (int row = 0; row < use.size(); row++) {
            for (int col = 0; col < use.get(row).length(); col++) {
                if (use.get(row).substring(col, col + 1).equals("I")) {
                    weight.get(row).set(col, -45);
                }
                    //System.out.println("Checking "+col);
                if (use.get(row).substring(col, col + 1).equals("S")) {
                    weight.get(row).set(col, -30);
//                    if(on<15){
//                        System.out.println(row+" "+col);
//                        System.out.println();
//                    }
                    //System.out.println("Iteration: "+ col+" "+row);
                    int one = possibleS(use, row-2, col, on);
                    if(one != -45){
                        weight.get(row-2).set(col, one);
                    }
                    int two = possibleS(use, row-1, col+1, on);
                    if(two != -45){
                        weight.get(row-1).set(col+1, two);
                    }
                    int three = possibleS(use, row, col+2, on);
                    if(three != -45){
                        weight.get(row).set(col+2, three);
                    }
                    int four = possibleS(use, row+1, col+1, on);
                    if(four != -45){
                       // System.out.println((row+1)+" "+ (col+1));
                        weight.get(row+1).set(col+1, four);
                    }
                    int five = possibleS(use, row+2, col, on);
                    if(five != -45){
                        weight.get(row+2).set(col, five);
                    }
                    int six = possibleS(use, row+1, col-1, on);
                    if(six != -45){
                        weight.get(row+1).set(col-1, six);
                    }
                    int seven = possibleS(use, row, col-2, on);
                    if(seven != -45){
                        weight.get(row).set(col-2, seven);
                    }
                    int eight = possibleS(use, row-1, col-1, on);
                    if(eight != -45){
                        weight.get(row-1).set(col-1, eight);
                    }
                }
            }
        }
        for (int i = 0; i < weight.size(); i++) {
            for (int j = 0; j < weight.get(i).size(); j++) {
//                if(on<15) {
//                    System.out.print(weight.get(i).get(j)+" ");
//                }

            }
//            if(on<15) {
//                System.out.println();
//            }

        }
        return weight;
    }

        /*
       1 row-2,col
       2 row-1,col+1
       3 row,col+2
       4 row+1,col+1
       5 row+2,col
       6 row+1,col-1
       7 row,col-2
       8 row-1,col-1
         */
    public int possibleS(ArrayList<String> use, int row, int col, int on){

            ArrayList<String> temp = new ArrayList<>();
            for (int i = 0; i < use.size(); i++) {
                temp.add(use.get(i));
            }
            int set = 0;

            //System.out.println(col+ " "+row);
            if (col >= 0 && col < temp.get(0).length()) {
                if (row >= 0 && row < temp.size() - 1) {
                    if(!use.get(row).substring(col,col+1).equals("S")) {
                        if (!use.get(row).substring(col, col + 1).equals("I")) {
                            String str = temp.get(row);
                            if (col == temp.get(row).length() - 1) {
                                str = str.substring(0, col) + "S";
                            } else {
                                str = str.substring(0, col) + "S" + str.substring(col + 1);
                            }
                            temp.set(row, str);

                            temp = finalState(temp);

                            for (int rowCheck = 0; rowCheck < temp.size(); rowCheck++) {
                                for (int colCheck = 0; colCheck < temp.get(rowCheck).length(); colCheck++) {
                                    if (temp.get(rowCheck).substring(colCheck, colCheck + 1).equals(".")) {
                                        set++;
                                    }
                                }
                            }
                        } else {
                            set = -45;
                        }
                    }else{
                        set = -45;
                    }
            } else {
                set = -45;
            }
        }else{
                set = -45;
            }
        return set;
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