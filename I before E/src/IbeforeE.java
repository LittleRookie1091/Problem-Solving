import java.util.ArrayList;
import java.util.Scanner;

public class IbeforeE {
    private ArrayList<String> params = new ArrayList<String>();

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        IbeforeE main = new IbeforeE();
        while(scan.hasNextLine()){
            main.params.add(scan.nextLine());
        }
    }
}