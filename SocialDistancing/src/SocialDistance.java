import java.util.*;

public class SocialDistance {
    private int pathTotal = 0;
    private int rowV = 0;
    private int colV = 0;
    private Node primeNode = new Node(null,0,0,0);

    private class Node{
        Node parent;
        int row;
        int col;
        int i;
        String word = "";
        public Node(){

        }
        public ArrayList<ArrayList<Integer>> print(ArrayList<ArrayList<Integer>> use){
            ArrayList<Integer> a = new ArrayList<>();
            a.add(row);
            a.add(col);
            use.add(0,a);
           // System.out.println(row+" "+col);
            if(parent != null){
                return parent.print(use);
            }else{
                return use;
            }
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

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

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public Node(Node par, int row, int col, int i){
            this.parent = par;
            this.row = row;
            this.col = col;
            this.i = i;
        }


    }

    public static void main(String[] args){
       Scanner scan = new Scanner(System.in);
       ArrayList<ArrayList<String>> scenarios = new ArrayList<>();
       ArrayList<String[]> use = new ArrayList<>();
       ArrayList<String> each = new ArrayList<>();
       SocialDistance main = new SocialDistance();
       boolean next = false;
       while(scan.hasNextLine()){
           String a = scan.nextLine();

           if(!a.isBlank()) {
               next = true;
               each.add(a);
           }else{
               next = false;
               scenarios.add(each);
               each = new ArrayList<>();
           }
       }
       if(next){
           scenarios.add(each);
       }

       for(int i = 0; i<scenarios.size();i++){
           use.clear();
           String[] a = scenarios.get(i).get(0).split(" ");
           int rowIn = Integer.parseInt(a[0]);
           int colIn = Integer.parseInt(a[1]);
           main.rowV = rowIn;
           main.colV = colIn;
           for(int j = 0; j<rowIn; j++) {
               String[] board = new String[colIn];
               for(int x = 0; x<board.length;x++){
                   board[x] = "O";
               }
               use.add(board);
           }

           for(int insert = 1; insert<scenarios.get(i).size(); insert++){
               String[] in  = scenarios.get(i).get(insert).split(" ");
               int row = Integer.parseInt(in[0]);
               int col = Integer.parseInt(in[1]);
               use.get(row)[col] = "P";
           }
           main.pathTotal = 0;
           main.distance(use, rowIn, colIn);
       }
//       for(int i = 0; i<use.size(); i++){
//           for(int j = 0; j<use.get(i).length; j++){
//               System.out.println(use.get(i)[j]);
//           }
//       }
    }

    public void distance(ArrayList<String[]> use, int row, int col){
        int n = 0;
        boolean check = true;
        int p = 0;
        for(int i = 0; i<use.size();i++){
            for(int j = 0; j<use.get(i).length;j++){
                if(use.get(i)[j].equals("P")){
                    p++;
                }
            }
        }
        if(p>0) {
            Node node1 = new Node(null, 0, 0, 0);
            node1.setWord("end");
            Node nodeFinal = node1;
            Node node = search(use, row - 1, col - 1);
            nodeFinal = node;
            if (nodeFinal.getWord().equals("goalState")) {
                nodeFinal = findTheOptimal(nodeFinal, use, row - 1, col - 1);
                ArrayList<ArrayList<Integer>> x = new ArrayList<>();
                x = nodeFinal.print(x);
                String path1 = getPath(x, use, node.getI()+1);
                System.out.println(path1);
            }else{
                ArrayList<ArrayList<Integer>> x = new ArrayList<>();
                x = nodeFinal.print(x);
                String path1 = getPath(x, use, node.getI()+1);
                System.out.println(path1);
            }
        }else{
            System.out.println("min "+0+", total "+0);
        }

    }

    public Node findTheOptimal(Node node, ArrayList<String[]> use, int row, int col){
        int p = 0;
        for(int i = 0; i<use.size();i++){
            for(int j = 0; j<use.get(i).length;j++){
                if(use.get(i)[j].equals("P")){
                    p++;
                }
            }
        }
        ArrayList<ArrayList<Boolean>> bool = createBool(use);
        //bool = setBool(bool, node.getI(),use);
        Node changeNode = new Node();
        for(int i = 1; i<=p;i++){
            boolean checker = true;
            int dist = node.getI();
            ArrayList<ArrayList<Boolean>> temp = createBool(use);
            while(checker) {
                temp = copyBool(temp, bool);
                temp = newBool(temp, dist, use, i);
                ArrayList<ArrayList<Boolean>> edit = createBool(use);
                edit = copyBool(edit, temp);

                ArrayList<Node> children = new ArrayList<>();
                Node src = new Node(null, 0, 0, 0);
                children.add(src);
                changeNode = breadthSearch(edit, children,row,col,node.getI(), p);
                if(changeNode.getWord().equals("goalState")){
                    bool = copyBool(bool, temp);
                }else{
                    checker = false;
                }
                dist++;
            }
        }
        ArrayList<Node> children = new ArrayList<>();
        Node src = new Node(null, 0, 0, 0);
        children.add(src);
        changeNode = breadthSearch(bool, children,row,col,node.getI(), p);

        return changeNode;
    }


    public ArrayList<ArrayList<Boolean>> createBool(ArrayList<String[]> use){
        ArrayList<ArrayList<Boolean>> bool = new ArrayList<>();
        for(int i = 0; i<use.size();i++){
            ArrayList<Boolean> newB = new ArrayList<>();
            bool.add(newB);
            for(int j = 0; j<use.get(i).length;j++){
                if(use.get(i)[j].equals("P")){
                    bool.get(i).add(true);
                }else{
                    bool.get(i).add(true);
                }
            }
        }
        return bool;
    }
    public ArrayList<ArrayList<Boolean>> copyBool(ArrayList<ArrayList<Boolean>> edit, ArrayList<ArrayList<Boolean>> bool){
        for(int i = 0; i<bool.size();i++){
            for(int j = 0; j<bool.get(i).size();j++){
                edit.get(i).set(j, bool.get(i).get(j));
            }
        }
        return edit;
    }
    public ArrayList<ArrayList<Boolean>> newBool(ArrayList<ArrayList<Boolean>> bool, int n, ArrayList<String[]> use, int p ) {
        int pCount = 0;
        for (int i = 0; i < bool.size(); i++) {
            for (int j = 0; j < bool.get(i).size(); j++) {
                if (use.get(i)[j].equals("P")) {
                    pCount++;
                    if(pCount == p) {
                      //  System.out.println("HERE "+ p);
                        for (int row = i - n; row < i + n + 1; row++) {
                            for (int col = j - n; col < j + n + 1; col++) {
                                int checkRow = java.lang.Math.abs(row - i);
                                int checkCol = java.lang.Math.abs(col - j);
                                if ((checkCol + checkRow) <= n) {
                                    if (row < bool.size() && row > -1 && col < bool.get(0).size() && col > -1) {
                                        bool.get(row).set(col, false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
//            for (int i = 0; i < bool.size(); i++) {
//                System.out.println(bool.get(i));
//           }
//            System.out.println(n);

        return bool;
    }
    public ArrayList<ArrayList<Boolean>> setBool(ArrayList<ArrayList<Boolean>> bool, int n, ArrayList<String[]> use) {
        for (int i = 0; i < bool.size(); i++) {
            for (int j = 0; j < bool.get(i).size(); j++) {
                if (use.get(i)[j].equals("P")) {
                    for (int row = i - n; row < i + n+1; row++) {
                        for (int col = j - n; col < j + n+1; col++) {
                            int checkRow = java.lang.Math.abs(row-i);
                            int checkCol = java.lang.Math.abs(col-j);
                            if((checkCol+checkRow)<=n) {
                                if (row < bool.size() && row > -1 && col < bool.get(0).size() && col > -1) {
                                    bool.get(row).set(col, false);
                                }
                            }
                        }
                    }
                }
            }
        }
        return bool;
    }


    public Node setFirst(ArrayList<String[]> use, int row, int col){
        ArrayList<ArrayList<Boolean>> bool = new ArrayList<>();
        int p = 0;
        for(int i = 0; i<use.size();i++){
            ArrayList<Boolean> newB = new ArrayList<>();
            bool.add(newB);
            for(int j = 0; j<use.get(i).length;j++){
                if(use.get(i)[j].equals("P")){
                    p++;
                    bool.get(i).add(true);
                }else{
                    bool.get(i).add(true);
                }
            }
        }
        ArrayList<Node> children = new ArrayList<>();
        Node src = new Node(null, 0, 0, 0);
        children.add(src);
        bool.get(0).set(0, false);
        Node node =  breadthSearch(bool, children,row,col,0,p);
        return node;
    }


    public String getPath(ArrayList<ArrayList<Integer>> use, ArrayList<String[]> cood, int a){
        ArrayList<Integer> row = new ArrayList<>();
        ArrayList<Integer> col = new ArrayList<>();

        for(int i = 0; i<use.size();i++){
            row.add(use.get(i).get(0));
            col.add(use.get(i).get(1));
        }
        for(int i = 0; i<cood.size();i++){
            for(int j = 0; j<cood.get(i).length;j++){
                for(int add = 0; add<row.size();add++){
                    if(row.get(add).equals(i) && col.get(add).equals(j)){
                        if(cood.get(i)[j].equals("P")){
                            a = 0;
                        }
                        cood.get(i)[j] = "X";
                    }
                }
            }
        }
        int distAdd = 0;
        for(int i = 0; i<cood.size();i++){
            for(int j = 0; j<cood.get(i).length;j++){
                    if(cood.get(i)[j].equals("P")){
                        //if(checkMin(cood,i,j)){
                           // a = 0;
                      //  }
                        distAdd = distAdd +dist(i, j, cood);
                    }
            }
        }

        String finalString = "min "+a+", total "+distAdd;

        return finalString;
    }
    public int dist(int row, int col, ArrayList<String[]> use){
        int dist = 0;
        boolean check = true;
        int n = 1;
        while(check){

            for(int i = row-n; i<row+n+1;i++){
                for(int j = col-n; j<col+n+1;j++) {
                    if(i>-1 && i<use.size() && j>-1 && j<use.get(i).length) {
                        //  System.out.print(use.get(i)[j]);
                        int checkRow = java.lang.Math.abs(row - i);
                        int checkCol = java.lang.Math.abs(col - j);
                        if ((checkCol + checkRow) <= n) {
                            if (use.get(i)[j].equals("X")) {
                                int num = java.lang.Math.abs(row - i);
                                int num2 = java.lang.Math.abs(col - j);
                                return num + num2;
                            }
                        }
                    }
                }
                //System.out.println();
            }
           // System.out.println();
            n++;
        }
        return dist;
    }


    public Node search(ArrayList<String[]> use, int goalRow, int goalCol){

        boolean findBest = true;
        Node node = setFirst(use, goalRow,goalCol);
        //System.out.println(node.getWord());
        int n = 0;
        while(findBest) {
            ArrayList<ArrayList<Boolean>> bool = new ArrayList<>();
            int p = 0;
            for(int i = 0; i<use.size();i++){
                ArrayList<Boolean> newB = new ArrayList<>();
                bool.add(newB);
                for(int j = 0; j<use.get(i).length;j++){
                    if(use.get(i)[j].equals("P")){
                        p++;
                        bool.get(i).add(true);
                    }else{
                        bool.get(i).add(true);
                    }
                }
            }
            bool = setBool(bool, n, use);

            ArrayList<Node> children = new ArrayList<>();
            Node src = new Node(null, 0, 0, 0);
            children.add(src);
            bool.get(0).set(0, false);
            Node node1 = breadthSearch(bool, children, goalRow, goalCol, n, p);
            if(node1.getWord().equals("noPath")){
               // System.out.println("1");
                findBest = false;
            }else if(node1.getWord().equals("goalState")){
              //  System.out.println("2");
                node = node1;
            }else if(node1.getWord().equals("end")){
              //  System.out.println("3");
                findBest = false;
            }
            n++;
        }
        //System.out.println(node.getWord()+" "+n);
        return node;
    }
    public Node breadthSearch(ArrayList<ArrayList<Boolean>> bool, ArrayList<Node> children, int goalRow, int goalCol, int n, int p){
        boolean check = true;
        int rowSet = 0;
        int colSet = 0;
        Node node1 = new Node(null, 0,0,0);
        while(check) {
//
////           // if(n==1) {
//            for (int i = 0; i < bool.size(); i++) {
//                // for(int j =0; j<bool.get(i).size();j++){
//                System.out.println(bool.get(i));
//                //  }
//           }
//            System.out.println(n);
////           // }
            ArrayList<Node> child = new ArrayList<>();
            for (int i = 0; i < children.size(); i++) {
                int row = children.get(i).getRow();
                int col = children.get(i).getCol();
                if(row+1>-1 && row+1<bool.size() && col >-1 && col<bool.get(0).size()) {
                    if (bool.get(row + 1).get(col)) {
                        bool.get(row + 1).set(col, false);
                        Node a = new Node(children.get(i), row + 1, col, n);
                        child.add(a);
                        if((row+col)>rowSet+colSet){
                            node1 = a;
                        }
                        if (row + 1 == goalRow && col == goalCol) {
                            a.setWord("goalState");
                           // System.out.println("HERE");
                            primeNode = a;
                            return a;
                        }
                    }
                }
                if(row-1>-1 && row-1<bool.size() && col >-1 && col<bool.get(0).size()) {
                    if (bool.get(row - 1).get(col)) {
                        bool.get(row - 1).set(col, false);
                        Node a = new Node(children.get(i), row - 1, col, n);
                        child.add(a);
                        if((row+col)>rowSet+colSet){
                            node1 = a;
                        }
                        if (row - 1 == goalRow && col == goalCol) {
                            a.setWord("goalState");
                            //System.out.println("HERE");
                            primeNode = a;
                            return a;
                        }
                    }
                }
                if(row>-1 && row<bool.size() && col-1 >-1 && col-1<bool.get(0).size()) {
                    if (bool.get(row).get(col - 1)) {
                        bool.get(row).set(col - 1, false);
                        Node a = new Node(children.get(i), row, col - 1, n);
                        child.add(a);
                        if((row+col)>rowSet+colSet){
                            node1 = a;
                        }
                        if (row == goalRow && col - 1 == goalCol) {
                            a.setWord("goalState");
                           // System.out.println("HERE");
                            primeNode = a;
                            return a;
                        }
                    }
                }
                if(row>-1 && row<bool.size() && col+1 >-1 && col+1<bool.get(0).size()) {
                    if (bool.get(row).get(col + 1)) {
                        bool.get(row).set(col + 1, false);
                        Node a = new Node(children.get(i), row, col + 1, n);
                        child.add(a);
                        if((row+col)>rowSet+colSet){
                            node1 = a;
                        }
                        if (row == goalRow && col + 1 == goalCol) {
                            a.setWord("goalState");
                           // System.out.println("HERE");
                            primeNode = a;
                            return a;
                        }
                    }
                }

            }
            children = child;
            if(child.isEmpty()){
                check = false;
                node1.setWord("noPath");
            }
            if(n>0) {
                if (p == 0) {
                    check = false;
                    node1.setWord("end");
                }
            }
        }

        return node1;
    }


}
