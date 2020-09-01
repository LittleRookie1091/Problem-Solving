public class Sequencium {
    public static void main(String[] args){
        Sequencium seq = new Sequencium();
        seq.test("hello L oi LL");
    }

    public void test(String line){
        if(line.contains("LL")){
            int indexOfLon = line.indexOf("LL");
            line = line.replaceFirst("LL", "");
        }
        System.out.println(line);
        if(line.contains("L")){
            int indexOfLat = line.indexOf("L");
            line =line.replaceFirst("L", "");
        }
        System.out.println(line);
    }
}
