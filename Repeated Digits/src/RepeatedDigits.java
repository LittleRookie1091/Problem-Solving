import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RepeatedDigits {
    public static void main(String[] args){
        ArrayList<String> lines = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        RepeatedDigits main = new RepeatedDigits();
        while(scan.hasNextLine()){
            lines.add(scan.nextLine());
        }

        for(int i = 0; i<lines.size(); i++){
            main.checkForRepeat(lines.get(i));
        }
    }


    public boolean checkForRepeat(String line){
        Pattern pattern = Pattern.compile("[^ab]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line.substring(0,1));
        boolean match = matcher.find();
        if(match) {
            String[] use = line.split(" ");
            if (use.length==3) {
                if (use[0].equals("A")) {
                    partA(use);
                } else {
                    partB(use);
                }
            }
        }
        return false;
    }

    public boolean partA(String[] line){
        return false;
    }

    public boolean partB(String[] line) {
        return false;
    }
}
