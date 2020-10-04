import java.util.ArrayList;
import java.util.Scanner;

public class PlayIce {
    private ArrayList<String> params = new ArrayList<String>();
    private ArrayList<String> instances = new ArrayList<String>();

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        PlayIce main = new PlayIce();
        Boolean trigger = true;
        while(scan.hasNextLine()){
            String use = scan.nextLine();
            if(!use.isBlank()) {
                if(trigger) {
                    main.params.add(use);
                }else{
                    main.instances.add(use);
                }
            }else{
                trigger = false;
            }
        }
        main.setRules();
        main.outPut();
    }

    public void setRules(){

    }

    public void outPut(){

    }
}