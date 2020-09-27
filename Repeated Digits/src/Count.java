import java.util.ArrayList;

public class Count {

    public static void main(String[] args){
        Count u = new Count();
        int base = 10;
        ArrayList<Integer> use = new ArrayList<>();
        use.add(0);
        for(int i =0; i<120; i++) {
            use = u.counter(base, use);
            u.checkRepeat(use);
            System.out.println("The elements: "+use);
        }
    }

    public ArrayList<Integer> counter(int base, ArrayList<Integer> use){
        use.set(use.size()-1, use.get(use.size()-1)+1);
        for(int i = use.size()-1; i>-1;i--){
                if(i>0){
                    if(use.get(i)==base) {
                        use.set(i, 0);
                        use.set(i - 1, use.get(i - 1) + 1);
                    }
                }else{
                    if(use.get(i)==base) {
                        use.set(i, 0);
                        use.add(0, 1);
                    }
                }
        }
        return use;
    }

    public boolean checkRepeat(ArrayList<Integer> use){
        for(int i = 0; i<use.size();i++){
            for(int j = i+1; j< use.size(); j++){
                if(use.get(i)==use.get(j)){
                    return true;
                }
            }
        }
        return false;
    }
}
