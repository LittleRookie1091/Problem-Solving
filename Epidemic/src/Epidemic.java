import java.util.*;

public class Epidemic {
    private ArrayList<ArrayList<String>> universe = new ArrayList<>();
    private int county = -1;
    private int area;
    private int amountOfS = 0;
    private ArrayList<String> theFinal;

    private class Node{
        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }

        private int row;
        private int col;
        public Node(int row, int col){
            this.row = row;
            this.col = col;
        }


    }
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
        //System.out.println(bestScount);
        use = findOptimal(use);
        use = edgeCase(use);
        int amountOfS = countS(use);
        System.out.println(amountOfS);
        for (int i = 0; i < use.size(); i++) {
            System.out.println(use.get(i));
        }
        System.out.println();
        return false;
    }

    public ArrayList<String> edgeCase(ArrayList<String> use){
        int check = 0;
        for(int i = 0; i<use.size();i++){
            for(int j = 0; j<use.get(i).length();j++){
                if(use.get(i).substring(j,j+1).equals("S")) {
                    if (i > 0 && i < use.size() && j > 0 && j < use.get(i).length() && i + 1 < use.size() && j + 2 < use.get(i).length()) {
                        if (use.get(i).substring(j, j + 1).equals("S") && use.get(i).substring(j + 1, j + 2).equals("S") && use.get(i + 1).substring(j, j + 1).equals("S")) {
                            String a = "." + use.get(i).substring(j + 1);
                            use.set(0, a);
                        }
                    }
                }
                    if (i > 0 && i < use.size() && j > 0 && j < use.get(i).length() && i + 1 < use.size() && j + 2 < use.get(i).length()) {
                        if (use.get(i).substring(j, j + 1).equals("S") && use.get(i).substring(j + 1, j + 2).equals("S") && use.get(i + 1).substring(j + 1, j + 2).equals("S")) {
                            String a = use.get(i + 1).substring(j, j + 1) + "." + use.get(i + 1).substring(j + 2);
                            use.set(0, a);
                        }
                    }
                }
        }

        return use;
    }

    public int countS(ArrayList<String> use){
        int s = 0;
        for (int i = 0; i < use.size(); i++) {
            for(int j = 0; j<use.get(i).length();j++) {
                if(use.get(i).substring(j, j +1).equals("S")){
                    s++;
                }
            }
        }
        return s;
    }
    public  ArrayList<String> findOptimal(ArrayList<String> use){
        ArrayList<String> temp;
        ArrayList<Integer> row = new ArrayList<>();
        ArrayList<Integer> col = new ArrayList<>();
        for(int i = 0; i< use.size();i++){
            for(int j = 0; j<use.get(i).length();j++){
                if(use.get(i).substring(j,j+1).equals("S")){
                    row.add(i);
                    col.add(j);
                }
            }
        }
        int amount, rowNew, colNew;
        boolean check = true;
        int ace = 0;
        while(check){
            amount = 900000;
            rowNew = 0;
            colNew = 0;
//            if(ace<10) {
//                for (int i = 0; i < use.size(); i++) {
//                    System.out.println(use.get(i));
//                }
//                System.out.println();
//            }
            ace++;
            if(row.size()>0){
                for(int i = 0; i<row.size();i++){
                    int  rowNum = row.get(i);
                    int colNum = col.get(i);
                    ArrayList<Node> children = getChildren(rowNum, colNum, use);
                    for(int child = 0; child<children.size();child++){
                        ArrayList<String> childArray = newArray(use);
                        childArray = finalState(childArray);
                        childArray = insertS(childArray, children.get(child).getRow(), children.get(child).getCol());
                        //System.out.println(children.get(child).getRow()+" "+children.get(child).getCol());
                        childArray = finalState(childArray);
                        int dots = countDots(childArray);
                        //System.out.println(dots);
                        if(dots<amount){
                            amount = dots;
                            rowNew = children.get(child).getRow();
                            colNew = children.get(child).getCol();
                        }
                    }
                }
            }
            use = insertS(use, rowNew, colNew);
            row.add(rowNew);
            col.add(colNew);
            temp = newArray(use);
            temp = finalState(temp);
            if(countDots(temp)==0){
                check = false;
            }
        }
        return use;
    }

    //Counts the dots in the array
    public int countDots(ArrayList<String> use){
        int dots = 0;
        for(int i =0; i<use.size();i++){
            for(int j = 0; j<use.get(i).length();j++){
                if(use.get(i).substring(j,j+1).equals(".")){
                    dots++;
                }
            }
        }
        return dots;
    }

    //Sets the specified element in array to be S
    public ArrayList<String> insertS(ArrayList<String> use, int row, int col){
        String str = use.get(row);
        str = str.substring(0,col)+"S"+str.substring(col+1);
        use.set(row, str);
        return use;
    }

    //Creates a new array so as to not effect the original
    public ArrayList<String> newArray(ArrayList<String> use){
        ArrayList<String> newArray = new ArrayList<>();
        for(int i = 0; i<use.size(); i++){
            newArray.add(use.get(i));
        }
        return newArray;
    }

    public ArrayList<Node> getChildren(int row, int col, ArrayList<String> use){
        ArrayList<Node> nodes = new ArrayList<>();
        for(int i = row-2; i<row+3;i++){
            for(int j = col-2; j<col+3;j++){
                if(i>-1 && i<use.size() && j>-1 && j<use.get(i).length()){
                    int rowN = java.lang.Math.abs(i-row);
                    int colN = java.lang.Math.abs(j-col);
                    if((rowN+colN)<3){
                        if(!use.get(i).substring(j,j+1).equals("S") && !use.get(i).substring(j,j+1).equals("I")) {
                            Node node = new Node(i, j);
                            nodes.add(node);
                        }
                    }
                }
            }
        }
        return nodes;
    }
    //Finds an estimated guess of the most efficient amount of S
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

    //Finds the final state
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