
public class Threes {
    private long x = 1;
    private long y = 2;
    private long z = 0;
    private long yIncrement = 1;
    private int increment = 1;
    private boolean fastCheck = false;

    /*

     */
    public static void main(String[] args){
        Threes z = new Threes();
        Threes x = new Threes();

        while(x.increment<71){
            x.checkerX();
            x.reBalanceX();
        }
        System.out.println();
        while(z.increment<71){
            z.checker();
            z.reBalance();
        }
    }

    public void reBalance() {
            if (y == (x + 1)) {
                if ((Math.pow(x, 2) + Math.pow(y, 2)) >= (1 + Math.pow(z, 3))) {
                    z++;
                    x = z + 1;
                    y = x + 1;
                    long speed = (long) (Math.pow(z, 3) + 1);
                    speed = (speed - ((long) (Math.pow(x, 2))));
                    double newSpeed = Math.sqrt((double) speed);
                    y = (long) newSpeed - 1;
                    if((z%2) == 0){
                        if(!(x%2==0) && !(y%2 == 0)){
                            y++;
                        }else if((x%2==0) && (y%2 == 0)){
                            y++;
                        }
                    }else{
                        if (x % 2 == 0 && !(y % 2 == 0)) {
                            y++;
                        } else if (!(x % 2 == 0) && (y % 2 == 0)) {
                            y++;
                        }
                    }
                } else {
                    y+=yIncrement;
                }
            } else if ((Math.pow(x, 2) + Math.pow(y, 2)) >= (1 + Math.pow(z, 3))) {
                x++;
                y = x + 1;
                fastCheck = true;
            } else if (increment > 5 && fastCheck) {
                yIncrement =2;
                long speed = (long) (Math.pow(z, 3) + 1);
                speed = (speed - ((long) (Math.pow(x, 2))));
                double newSpeed = Math.sqrt((double) speed);
                y = (long) newSpeed - 5;
                fastCheck = false;
                if((z%2) == 0){
                    if(!(x%2==0) && !(y%2 == 0)){
                        y++;
                    }else if((x%2==0) && (y%2 == 0)){
                        y++;
                    }
                }else {
                    if (x % 2 == 0 && !(y % 2 == 0)) {
                        y++;
                    } else if (!(x % 2 == 0) && (y % 2 == 0)) {
                        y++;
                    }
                }
            } else {
                y+=yIncrement;
            }
        }


    public void reBalanceX() {
            if (z == x) {
                x++;
                z = (long) Math.cbrt(Math.pow(x, 2));
                y = x + 1;
                long speed = (long) (Math.pow(z, 3) + 1);
                speed = (speed - ((long) (Math.pow(x, 2))));
                double newSpeed = Math.sqrt((double) speed);
                speed = (long) newSpeed - 2;
                y = speed;
                if((x%2) == 0){
                    if((z%2==0) && (y%2 == 0)){
                        y++;
                    }else if(!(z%2==0) && !(y%2 == 0)){
                        y++;
                    }
                }else{
                    if (z % 2 == 0 && !(y % 2 == 0)) {
                        y++;
                    } else if (!(z % 2 == 0) && (y % 2 == 0)) {
                        y++;
                    }
                }
            } else if ((Math.pow(x, 2)) >= (1 + Math.pow(z, 3) - Math.pow(y, 2))) {
                z++;
                y = x + 1;
                fastCheck = true;
            } else if (increment > 2 && fastCheck) {
                yIncrement =2;
                long speed = (long) (Math.pow(z, 3) + 1);
                speed = (speed - ((long) (Math.pow(x, 2))));
                double newSpeed = Math.sqrt((double) speed);
                speed = (long) newSpeed - 2;
                y = speed;
                fastCheck = false;
                if((x%2) == 0){
                    if((z%2==0) && (y%2 == 0)){
                        y++;
                    }else if(!(z%2==0) && !(y%2 == 0)){
                        y++;
                    }
                }else{
                    if (z % 2 == 0 && !(y % 2 == 0)) {
                        y++;
                    } else if (!(z % 2 == 0) && (y % 2 == 0)) {
                        y++;
                    }
                }
            } else {
                y+=yIncrement;
            }
        }

    public boolean checker(){
            if(check3()){
                if(check2()) {
                    System.out.println(increment+" "+ x+ " "+ y+ " " + z);
                    increment +=1;
                    return true;
                }
            }
        return false;
    }

    public boolean checkerX(){
        if(check3x()){
            if(check2()) {
                System.out.println(increment+" "+ x+ " "+ y+ " " + z);
                increment +=1;
                return true;
            }
        }
        return false;
    }
    public boolean check2(){
        long a = x;
        long b = y;
        long c = z;

        while (a != 0) {
            long temp = a;
            a = b % a;
            b = temp;
        }
        if (b!= 1){
            return false;
        };
        a = x;
        while (a != 0) {
            long temp = a;
            a = c % a;
            c = temp;
        }
        if (c!= 1){
            return false;
        };
        b = y;
        c = z;
        while (c != 0) {
            long temp = c;
            c = b % c;
            b = temp;
        }
        if (b!= 1){
            return false;
        };
        return true;
    }

    //  x^2 + y^2 = 1 + z^3
    public boolean check3(){
        if((Math.pow(x, 2)+ Math.pow(y, 2)) == (1+ Math.pow(z,3))){
            return true;
        }
        return false;
    }

    public boolean check3x(){
        if((Math.pow(x, 2)== (1+ Math.pow(z,3))-Math.pow(y, 2)) ){
            return true;
        }
        return false;
    }

}
