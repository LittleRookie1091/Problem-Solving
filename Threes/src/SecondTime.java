//
//import java.util.ArrayList;
//
//public class Threes {
//    private long x = 1;
//    private long y = 2;
//    private long z = 0;
//    private int increment = 1;
//    // private int count = 0;
//    private int changeIncrease = 0;
//    //private int hundred = 0;
//    //private int hundredSmall = 0;
//    private int numSkip = 100;
//    private int numSkipSmall = 10;
//    private boolean fastCheck = false;
//
//    private ArrayList<String> finalString = new ArrayList<>();
//    private ArrayList<Long> checker = new ArrayList<>();
//    private ArrayList<String> finalStringX = new ArrayList<>();
//
//    /*
//    Set x,y,z to be 1,2,0 to satisfy the condition.
//    loop until we have 70 sets
//    in each loop go through checker(), which will go through the common factor check and the equation check,
//    then if both return true, the set of numbers will be added and increment is increased by 1.
//
//    To make the program faster and less brute force, I have set it so that as the program gets to higher values
//    of x, y , and z, higher increments on y occur, the large increments occur so that if for example x^2 + y+n ^2 is
//    smaller than z^3, then set y to y+n, the n addition gets bigger the bigger x, y , and z are.
//
//    However this wasn't enough to speed it up, so when Z or X is incremented, id do the following process:
//    h = z^3 + 1
//    h = h- x^2
//    h = sqrt(h)
//    y = h-5
//
//    What this does is find a number value of y that  when squared is close to the upper roof of the equation.
//    This skips heaps of values of y that we know will be to small to satisfy the equation.
//     */
//    public static void main(String[] args){
//        Threes three = new Threes();
//        while(three.increment<75){
//            //if(three.print == 10000000){
//            // System.out.println("Runthrough: "+ three.count + ", Increment: "+ three.increment);
//            //System.out.println( "Hundred: "+ three.hundred + " HundredSmall: "+ three.hundredSmall);
//            //  }
//            // three.count ++;
//            three.checker();
//            three.reBalance();
//        }
//
//        three.setUpX();
//        for(int i = 0; i<three.finalStringX.size();i++){
//            System.out.println((i+1) +three.finalStringX.get(i));
//        }
//        System.out.println();
//        for(int i = 0; i<three.finalString.size();i++){
//            System.out.println((i+1) +three.finalString.get(i));
//        }
//
//    }
//
//    public void reBalance() {
//        if(changeIncrease == 100000){
//            if ((Math.pow(x, 2) + Math.pow(y +numSkip+100, 2)) < (1 + Math.pow(z, 3))) {
//                numSkip+= 100;
//            }
//            if ((Math.pow(x, 2) + Math.pow(y +numSkipSmall+10, 2)) < (1 + Math.pow(z, 3))) {
//                if(numSkipSmall < numSkip/4) {
//                    numSkipSmall += 10;
//                }
//            }
//        }
//        if(changeIncrease == 10000000) {
//            //   System.out.println("x: " + Long.toString(x).length() + ", y: " + Long.toString(y).length() + ", z: " + Long.toString(z).length());
//            //  System.out.println("NumSkip: " + numSkip+", NumSkipSmall: " + numSkipSmall);
//            System.out.println("x: " + x + ", y: " + y + ", z: " + z + " Increment: "+ increment);
//            changeIncrease = 0;
//        }
//        changeIncrease++;
//        if ((Math.pow(x, 2) + Math.pow(y + numSkipSmall, 2)) < (1 + Math.pow(z, 3))) {
//            y = y + numSkipSmall;
//            //hundredSmall +=1;
//        }
//        if ((Math.pow(x, 2) + Math.pow(y + numSkip, 2)) < (1 + Math.pow(z, 3))) {
//            y = y + numSkip;
//            // hundred +=1;
//        }else {
//            if (y == (x + 1)) {
//                if ((Math.pow(x, 2) + Math.pow(y, 2)) >= (1 + Math.pow(z, 3))) {
//                    z++;
//                    x = z+1;
//                    y = x +1;
//                    long speed = (long) (Math.pow(z, 3)+1);
//                    speed = (speed - ((long)(Math.pow(x, 2))));
//                    double newSpeed = Math.sqrt((double)speed);
//                    y = (long) newSpeed-5;
//                } else {
//                    y++;
//                }
//            } else if ((Math.pow(x, 2) + Math.pow(y, 2)) >= (1 + Math.pow(z, 3))) {
//                x++;
//                y = x + 1;
//                fastCheck = true;
//            } else if(increment > 20 && fastCheck){
//                long speed = (long) (Math.pow(z, 3)+1);
//                speed = (speed - ((long)(Math.pow(x, 2))));
//                double newSpeed = Math.sqrt((double)speed);
//                y = (long) newSpeed-5;
//                fastCheck = false;
//            }else{
//                y ++;
//            }
//        }
//    }
//
//    public boolean checker(){
//        if(check3()){
//            if(check2()) {
//                finalString.add(" "+ x+ " "+ y+ " " + z);
//                checker.add(x);
//                increment +=1;
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    // z<x<y
//    public boolean check1(){
//        if(z>=x){
//            return false;
//        }
//        if(x>=y){
//            return false;
//        }
//        return true;
//    }
//
//    // x,y, and z can't have a common factor except for 1
//    public boolean check2(){
//        int gcd = 0;
//        //x and y common factor
//        for(int i =1; i<=x && i<=y; i++){
//            if(x%i == 0 && y%i == 0){
//                if(i != 1){
//                    return false;
//                }
//            }
//        }
//        //x and z common factor
//        for(int i =1; i<=x && i<=z; i++){
//            if(x%i == 0 && z%i == 0){
//                if(i != 1){
//                    return false;
//                }
//            }
//        }
//        //z and y common factor
//        for(int i =1; i<=z && i<=y; i++){
//            if(z%i == 0 && y%i == 0){
//                if(i != 1){
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    //  x^2 + y^2 = 1 + z^3
//    public boolean check3(){
//        if((Math.pow(x, 2)+ Math.pow(y, 2)) == (1+ Math.pow(z,3))){
//            return true;
//        }
//        //System.out.println("CHECK3" + " "+(Math.pow(x, 2)+ Math.pow(y, 2)) + " OThER Side: "+ (1+ Math.pow(z,3)));
//        return false;
//    }
//
//    public void setUpX(){
//        ArrayList<String> use = new ArrayList<>();
//        for(int i = 0; i<finalString.size();i++){
//            use.add(finalString.get(i));
//        }
//        int a = use.size();
//        int i = 0;
//        while(finalStringX.size()<a){
//            long smallest = checker.get(0);
//            int index = 0;
//            for(int j = 1; j <checker.size(); j++){
//                if(checker.get(j)< smallest){
//                    smallest = checker.get(j);
//                    index = j;
//                }
//            }
//            finalStringX.add(use.get(index));
//            use.remove(index);
//            checker.remove(index);
//        }
//    }
//}
//   boolean oddEven = false;
//           //Because we add 1 at the end, this even finder is actually finding the odd number
//           if((x%2) == 0){
////
////            if(z%2 == 0 && !(y%2==0)){
////                oddEven = true;
////            }else if(!(z%2 == 0) && (y%2==0)){
////                oddEven = true;
////            }else{
////                y++;
////            }
////
//           }else {
////            if(z%2 == 0 && (y%2==0)){
////                oddEven = true;
////            }else if(!(z%2 == 0) && !(y%2==0)){
////                oddEven = true;
////            }else{
////                y++;
////            }
//           }