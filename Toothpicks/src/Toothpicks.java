import java.util.ArrayList;
import java.util.Scanner;

public class Toothpicks {
    private ArrayList<String> params = new ArrayList<>();
    private double percent;
    private int numberOfPicks;
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Toothpicks main = new Toothpicks();
        while(scan.hasNext()){
            main.params.add(scan.next());
        }
        for(int i = 0; i<main.params.size(); i++){
            if(i == 0) {
                main.numberOfPicks = Integer.parseInt(main.params.get(i));
            }else if(i == 1){
                main.percent = Double.parseDouble(main.params.get(i));
            }
        }
        main.drawEnds(main.numberOfPicks, main.percent);
    }

    public void drawEnds(int amount, double percentage){
        //Graphics, each new generation should be percentage of last ones size
    }

}
