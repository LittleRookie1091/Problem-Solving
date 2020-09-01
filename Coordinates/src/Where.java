import java.util.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Where{

    private ArrayList<String> coordinates = new ArrayList<String>();
    private boolean north = false;
    private boolean south = false;
    private int southCount = 0;
    private boolean east = false;
    private boolean west = false;
    private String editing = "";
    private Double[] work = new Double[6];
    private Double lat;
    private Double lon;
    private boolean southNeg = false;
    private boolean westNeg = false;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Where main = new Where();

        while (scan.hasNextLine()) {
            String scannedLine =  scan.nextLine();
            if(!scannedLine.isBlank()) {
                main.coordinates.add(scannedLine);
            }
        }
        for(int i = 0; i<main.coordinates.size();i++) {
            main.southCount = 0;
            main.editing = "";
            main.north = false;
            main.west = false;
            main.east = false;
            main.south = false;
            main.lat = 0.0;
            main.lon = 0.0;
            main.southNeg = false;
            main.westNeg = false;
            for(int j =0; j<main.work.length;j++){
                main.work[j] = null;
            }

            Pattern pattern = Pattern.compile("[^0-9.\\-, ]", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(main.coordinates.get(i));
            boolean match = matcher.find();
            if(!match){
                if((main.coordinates.get(i)).contains(",")){
                    if(main.twoNumbersNoLetters(main.coordinates.get(i).split(","))){
                        main.zeroPad(main.editing.split(" "));
                        System.out.println(main.editing);
                    }else{
                        System.out.println("Unable to process: " + main.coordinates.get(i));
                    }
                }else {
                    if(main.twoNumbersNoLetters(main.coordinates.get(i).split(" "))){
                        main.zeroPad(main.editing.split(" "));
                        System.out.println(main.editing);
                    }else{
                        System.out.println("Unable to process: " + main.coordinates.get(i));
                    }
                }
            }else {
                if (main.checkInvalid(main.coordinates.get(i))) {
                    if (main.latAndLon(main.editing)) {
                        if(main.twoNumbersNoLetters(main.editing.split(" "))) {
                            main.zeroPad(main.editing.split(" "));
                            System.out.println(main.editing);
                        }else{
                            System.out.println("Unable to process: " + main.coordinates.get(i));
                        }
                    } else {
                        System.out.println("Unable to process: " + main.coordinates.get(i));
                    }
                } else {
                    System.out.println("Unable to process: " + main.coordinates.get(i));
                }
            }
        }
    }
    //Determine if there are invalid characters
    public boolean checkInvalid(String use){
        String line = use.toLowerCase();
        line = line.replaceAll(",", " ");
        Pattern pattern = Pattern.compile("[^0-9newsdm.°′'″ (north|west|south|east)]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        boolean match = matcher.find();
        if(!match){
            if(nESW(line)) {
                //System.out.println("1");
                    return true;
            }
        }
        return false;
    }

    //Determine if it has NESW and lat and longitude coordinates
    //Will mark N and S with |, will mark east and west with ||
    public Boolean nESW(String use){
        String line = use;
        Pattern pattern  = Pattern.compile("[rthuao]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        boolean match = matcher.find();
        while(match){
            pattern  = Pattern.compile("[rth]", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(line);
            match = matcher.find();
            if(match){
                if(line.contains("north")) {
                    line = line.replaceFirst("north", "L ");
                    if(north){
                        return false;
                    }
                    north = true;
                }
            }
            pattern  = Pattern.compile("[st]", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(line);
            match = matcher.find();
            if(match){
                if(line.contains("west")){
                    line = line.replaceFirst("west", "LL ");
                    if(west){
                        return false;
                    }
                    westNeg = true;
                    west = true;
                }
            }
            pattern  = Pattern.compile("[utho]", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(line);
            match = matcher.find();
            if(match){
                if(line.contains("south")){
                    line = line.replaceFirst("south", "L ");
                    if(south){
                        return false;
                    }
                    southNeg = true;
                    south = true;
                }
            }
            pattern  = Pattern.compile("[at]", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(line);
            match = matcher.find();
            if(match){
                if(line.contains("east")){
                    if(east){
                        return false;
                    }
                    line = line.replace("east", "LL ");
                    east = true;
                }
            }
            if(!north && !south && !east && !west){
                return false;
            }

            pattern  = Pattern.compile("[rthuao]", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(line);
            match = matcher.find();
        }
        pattern  = Pattern.compile("[nwe]", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(line);
        match = matcher.find();
//        if(!match){
//            return true;
//        }
//        System.out.println("Here");
        while(match){
            if(line.contains("n")) {
                line = line.replaceFirst("n", "L ");
                if(north){
                    return false;
                }
                north = true;
            }
            if(line.contains("w")) {
                line = line.replaceFirst("w", "LL ");
                if(west){
                    return false;
                }
                west = true;
                westNeg = true;
            }
            if(line.contains("e")) {
                line = line.replaceFirst("e", "LL ");
                if(east){
                    return false;
                }
                east = true;
            }

            pattern  = Pattern.compile("[nwe]", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(line);
            match = matcher.find();
        }
        pattern  = Pattern.compile("[s]", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(line);
        match = matcher.find();
        while(match){
            if(line.contains("s")) {
                line = line.replaceFirst("s", "abc ");
                southCount ++;
                if(south && southCount>3){
                    return false;
                }
                southNeg = true;
                south = true;
            }
            if(!north && !south && !east && !west){
                return false;
            }
            if(!north && !south){
                return false;
            }
            if(!east && !west){
                return false;
            }
            pattern  = Pattern.compile("[s]", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(line);
            match = matcher.find();
        }
        if(southCount == 2){
            line = line.replaceFirst("abc", "s");
            line = line.replaceFirst("abc", "L");
        }
        else if(southCount ==3){
            line = line.replaceFirst("abc", "s");
            line = line.replaceFirst("abc", "s");
            line = line.replaceFirst("abc", "L");
        }else{
            line = line.replaceFirst("abc", "L");
        }
        editing = line;
        return true;
    }

    //convert to lat or lon
    public boolean latAndLon(String use){
        String line = use;
        ArrayList<String> cood = new ArrayList<>();
        int indexOfLat = 0;
        int indexOfLon = 0;
        boolean latAndLonFound = false;
        if(line.contains("LL")){
            if(line.contains("L")) {
                latAndLonFound = true;
                indexOfLon = line.indexOf("LL");
                line = line.replaceFirst("LL", "");
                indexOfLat = line.indexOf("L");
                line = line.replaceFirst("L", "");
                if (indexOfLat < indexOfLon) {
                    cood.add(line.substring(0,indexOfLat));
                    cood.add(line.substring(indexOfLat));
                }else{
                    cood.add(line.substring(indexOfLon));
                    cood.add(line.substring(0,indexOfLon));
                }
            }
        }
        if(latAndLonFound) {
            for(int i = 0; i<cood.size();i++) {
                int latOrLon = 0;
                int dIndex = -1;
                int mIndex = -1;
                int sIndex = -1;
                boolean dIndice = false;
                boolean mIndice = false;
                boolean sIndice = false;
                if(i==1){
                    latOrLon = 3;
                }
               // System.out.println("lat and lon "+ cood.get(0)+" "+cood.get(1) + " "+ latOrLon);
                Pattern pattern = Pattern.compile("[dms°′'″]", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(cood.get(i));
                boolean match = matcher.find();
                if(match){
                    if(cood.get(i).contains("d")&&cood.get(i).contains("°")){
                        return false;
                    }
                    if(cood.get(i).contains("m")&&(cood.get(i).contains("′")||cood.get(i).contains("'"))){
                        return false;
                    }
                    if(cood.get(i).contains("s")&&cood.get(i).contains("\"")){
                        return false;
                    }

                    if(cood.get(i).contains("d")) {
                        if (cood.get(i).indexOf("d") != cood.get(i).lastIndexOf("d")) {
                            return false;
                        }
                        dIndice = true;
                        dIndex = cood.get(i).indexOf("d");
                    }else if(cood.get(i).contains("°")){
                        if (cood.get(i).indexOf("°") != cood.get(i).lastIndexOf("°")) {
                            return false;
                        }
                        dIndice = true;
                        dIndex = cood.get(i).indexOf("°");
                    }
                    if(cood.get(i).contains("m")) {
                        if (cood.get(i).indexOf("m") != cood.get(i).lastIndexOf("m")) {
                            return false;
                        }
                        mIndice = true;
                        mIndex = cood.get(i).indexOf("m");
                    }else if(cood.get(i).contains("'")){
                        if (cood.get(i).indexOf("'") != cood.get(i).lastIndexOf("'")) {
                            return false;
                        }
                        mIndice = true;
                        mIndex = cood.get(i).indexOf("'");
                    }else if(cood.get(i).contains("′")){
                        if (cood.get(i).indexOf("′") != cood.get(i).lastIndexOf("′")) {
                            return false;
                        }
                        mIndice = true;
                        mIndex = cood.get(i).indexOf("′");
                    }
                    if(cood.get(i).contains("s")) {
                        if (cood.get(i).indexOf("s") != cood.get(i).lastIndexOf("s")) {
                            return false;
                        }
                        sIndice = true;
                        sIndex = cood.get(i).indexOf("s");
                    }else if(cood.get(i).contains("″")){
                        if (cood.get(i).indexOf("″") != cood.get(i).lastIndexOf("″")) {
                            return false;
                        }
                        sIndice = true;
                        sIndex = cood.get(i).indexOf("″");
                    }
                    if(dIndice) {
                        if(mIndice) {
                            if(sIndice) {
                                if (dIndex < mIndex && dIndex < sIndex) {
                                    work[latOrLon] = Double.parseDouble(cood.get(i).substring(0, dIndex));
                                    if(mIndex< sIndex){
                                        work[latOrLon+1] = Double.parseDouble(cood.get(i).substring(dIndex+1, mIndex));
                                        work[latOrLon+2] = Double.parseDouble(cood.get(i).substring(mIndex+1, sIndex));
                                    }else{
                                        work[latOrLon+2] = Double.parseDouble(cood.get(i).substring(dIndex+1, sIndex));
                                        work[latOrLon+1] = Double.parseDouble(cood.get(i).substring(sIndex+1, mIndex));
                                    }
                                }else if(mIndex < dIndex && mIndex < sIndex){
                                    work[latOrLon+1] = Double.parseDouble(cood.get(i).substring(0, mIndex));
                                    if(dIndex< sIndex){
                                        work[latOrLon] = Double.parseDouble(cood.get(i).substring(mIndex+1, dIndex));
                                        work[latOrLon+2] = Double.parseDouble(cood.get(i).substring(dIndex+1, sIndex));
                                    }else{
                                        work[latOrLon+2] = Double.parseDouble(cood.get(i).substring(mIndex+1, sIndex));
                                        work[latOrLon] = Double.parseDouble(cood.get(i).substring(sIndex+1, dIndex));
                                    }
                                }else{
                                    work[latOrLon+2] = Double.parseDouble(cood.get(i).substring(0, sIndex));
                                    if(dIndex< mIndex){
                                        work[latOrLon] = Double.parseDouble(cood.get(i).substring(sIndex+1, dIndex));
                                        work[latOrLon+1] = Double.parseDouble(cood.get(i).substring(dIndex+1, mIndex));
                                    }else{
                                        work[latOrLon+1] = Double.parseDouble(cood.get(i).substring(sIndex+1, mIndex));
                                        work[latOrLon] = Double.parseDouble(cood.get(i).substring(mIndex+1, dIndex));
                                    }
                                }
                            }else{
                                if(dIndex <mIndex){
                                    work[latOrLon] = Double.parseDouble(cood.get(i).substring(0, dIndex));
                                    work[latOrLon+1] = Double.parseDouble(cood.get(i).substring(dIndex+1, mIndex));
                                }else{
                                    work[latOrLon+1] = Double.parseDouble(cood.get(i).substring(0, mIndex));
                                    work[latOrLon] = Double.parseDouble(cood.get(i).substring(mIndex+1, dIndex));
                                }
                            }
                        }else if(sIndice){
                            if(dIndex <  sIndex){
                                work[latOrLon] = Double.parseDouble(cood.get(i).substring(0, dIndex));
                                work[latOrLon+2] = Double.parseDouble(cood.get(i).substring(dIndex+1, sIndex));
                            }else{
                                work[latOrLon+2] = Double.parseDouble(cood.get(i).substring(0, sIndex));
                                work[latOrLon] = Double.parseDouble(cood.get(i).substring(sIndex+1, dIndex));
                            }
                        }else{
                            work[latOrLon] = Double.parseDouble(cood.get(i).substring(0, dIndex));
                        }
                    }else if(mIndice){
                        if(sIndice){
                            if(mIndex<sIndex){
                                work[latOrLon+1] = Double.parseDouble(cood.get(i).substring(0, mIndex));
                                work[latOrLon+2] = Double.parseDouble(cood.get(i).substring(mIndex+1, sIndex));
                            }else{
                                work[latOrLon+2] = Double.parseDouble(cood.get(i).substring(0, sIndex));
                                work[latOrLon+1] = Double.parseDouble(cood.get(i).substring(sIndex+1, mIndex));
                            }
                        }else{
                            work[latOrLon+1] = Double.parseDouble(cood.get(i).substring(0, mIndex));
                        }
                    }else{
                        work[latOrLon+2] = Double.parseDouble(cood.get(i).substring(0, sIndex));
                    }
                }else{
                    if(cood.get(i).contains(",")){
                        String[] a = cood.get(i).split(",");
                        for(int indexer = 0; indexer<a.length;indexer++){
                            work[latOrLon+indexer] = Double.parseDouble(a[indexer]);
                        }
                    }else{
                        String[] a = cood.get(i).split(" ");
                        int spaceCheck= 0;
                        for(int indexer = 0; indexer<a.length;indexer++){
                            if(a[indexer].equals("")){
                                spaceCheck ++;
                            }
                        }
                        String[] b = new String[a.length-spaceCheck];
                        spaceCheck = 0;
                        for(int indexer = 0; indexer<a.length;indexer++){
                            if(a[indexer].equals("")){
                            }else{
                                b[spaceCheck] = a[indexer];
                                spaceCheck ++;
                            }
                        }
                        for(int indexer = 0; indexer<b.length;indexer++){
                            work[latOrLon+indexer] = Double.parseDouble(b[indexer]);
                        }
                    }
                }
            }
        }
        if(work[1]!= null){
            if(work[1]>60){
                return false;
            }
        }
        if(work[2]!= null){
            if(work[2]>60){
                return false;
            }
        }
        if(work[4]!= null){
            if(work[4]>60){
                return false;
            }
        }
        if(work[5]!= null){
            if(work[5]>60){
                return false;
            }
        }
        if(work[0] != null){
            if(work[1]!=null){
                if(work[2]!= null){
                    lat = work[0] + work[1]/60 + work[2]/3600;
                 }else{
                    lat = work[0] + work[1]/60;
                }
            }else if(work[2]!= null){
                lat = work[0] + work[2]/3600;
            }else{
                lat = work[0];
            }
        }else if(work[1]!=null){
            if(work[2]!= null){
                lat = work[1]/60 + work[2]/3600;
            }else{
                lat = work[1]/60;
            }
        }else if(work[2]!= null){
            lat = work[2]/3600;
        }else{
            return false;
        }
        if(work[3] != null){
            if(work[4]!=null){
                if(work[5]!= null){
                    lon = work[3] + work[4]/60 + work[5]/3600;
                }else{
                    lon = work[3] + work[4]/60;
                }
            }else if(work[5]!= null){
                lon = work[3] + work[5]/3600;
            }else{
                lon = work[3];
            }
        }else if(work[4]!=null){
            if(work[5]!= null){
                lon = work[4]/60 + work[5]/3600;
            }else{
                lon = work[4]/60;
            }
        }else if(work[5]!= null){
            lon = work[5]/3600;
        }else{
            return false;
        }
        if(southNeg && westNeg){
            editing = "-"+lat + " -"+ lon;
        }else if(southNeg){
            editing = "-"+lat + " "+ lon;
        }else if(westNeg){
            editing = ""+lat + " -"+ lon;
        }else {
            editing = lat + " " + lon;
        }
        return true;
    }

    //Add zeros to the number
    public String zeroPad(String[] cood){
        boolean firstNeg = false;
        boolean secondNeg = false;
        boolean firstDec = false;
        boolean  secondDec = false;
        int fZero = 0;
        int sZero = 0;
        int fZero2 = 0;
        int sZero2 = 0;
        String first = cood[0];
        String second = cood[1];
        String[] firstArray = new String[2];
        String[] secondArray = new String[2];
        Character firstLetter = first.charAt(0);
        Character secondLetter = second.charAt(0);

        //Check for negatives
        if(0 == firstLetter.compareTo('-')){
            first = first.substring(1);
            firstNeg = true;
        }
        if(0 == secondLetter.compareTo('-')){
            second = second.substring(1);
            secondNeg = true;
        }

        if(first.contains(".")){
            firstArray = first.split("\\.");
            firstDec = true;
        }else{
            firstArray[0] = first;
            firstArray[1] = "";
        }

        if(second.contains(".")){
            secondArray = second.split("\\.");
            secondDec = true;
        }else{
            secondArray[0] = second;
            secondArray[1] = "";
        }

        if(firstArray[0].length()<2 ){
            if(firstArray[0].length()==1){
                fZero = 1;
            }
            if(firstArray[0].length()==0){
                fZero = 2;
            }
        }
        if(secondArray[0].length()<3){
            if(secondArray[0].length()==2){
                sZero = 1;
            }
            if(secondArray[0].length()==1){
                sZero = 2;
            }
            if(secondArray[0].length()==0){
                sZero = 3;
            }
        }

        if(firstArray[1].length()<6) {
            if (firstArray[1].length() == 5) {
                fZero2 = 1;
            }
            if (firstArray[1].length() == 4) {
                fZero2 = 2;
            }
            if (firstArray[1].length() == 3) {
                fZero2 = 3;
            }
            if (firstArray[1].length() == 2) {
                fZero2 = 4;
            }
            if (firstArray[1].length() == 1) {
                fZero2 = 5;
            }
            if (firstArray[1].length() == 0) {
                fZero2 = 6;
            }
        }
        if(secondArray[1].length()<6){
            if(secondArray[1].length()== 5){
                sZero2 = 1;
            }
            if(secondArray[1].length()== 4){
                sZero2 = 2;
            }
            if(secondArray[1].length()== 3){
                sZero2 = 3;
            }
            if(secondArray[1].length()== 2){
                sZero2 = 4;
            }
            if(secondArray[1].length()== 1){
                sZero2 = 5;
            }
            if(secondArray[1].length()== 0){
                sZero2 = 6;
            }
        }
        String zeroFirstBack = new String(new char[fZero2]).replace("\0", "0");
        String zeroSecondBack = new String(new char[sZero2]).replace("\0", "0");
        String finalString = "";
        if(firstDec && secondDec) {
            if (firstNeg) {
                if (secondNeg) {
                    finalString = "-" + first + zeroFirstBack + ", -"  + second + zeroSecondBack;
                } else {
                    finalString = "-"  + first + zeroFirstBack + ", "  + second + zeroSecondBack;
                }
            } else if (secondNeg) {
                finalString =   first + zeroFirstBack + ", -" +  second + zeroSecondBack;
            } else {
                finalString =  first + zeroFirstBack + ", " +  second + zeroSecondBack;
            }
        }else if(!firstDec && secondDec){
            if (firstNeg) {
                if (secondNeg) {
                    finalString = "-"  + first+"." + zeroFirstBack + ", -"  + second + zeroSecondBack;
                } else {
                    finalString = "-"  + first+"." + zeroFirstBack + ", "  + second + zeroSecondBack;
                }
            } else if (secondNeg) {
                finalString =   first+"." + zeroFirstBack + ", -"  + second + zeroSecondBack;
            } else {
                finalString = first+"." + zeroFirstBack + ", "  + second + zeroSecondBack;
            }
        }else if(firstDec && !secondDec) {
            if (firstNeg) {
                if (secondNeg) {
                    finalString = "-"  + first + zeroFirstBack + ", -"  + second+"." + zeroSecondBack;
                } else {
                    finalString = "-"  + first + zeroFirstBack + ", "  + second+"." + zeroSecondBack;
                }
            } else if (secondNeg) {
                finalString =   first + zeroFirstBack + ", -"  + second+"." + zeroSecondBack;
            } else {
                finalString = first + zeroFirstBack + ", "  + second+"." + zeroSecondBack;
            }
        }else{
            if (firstNeg){
                if (secondNeg) {
                    finalString = "-"  + first+"." + zeroFirstBack + ", -"  + second+"." + zeroSecondBack;
                } else {
                    finalString = "-"  + first+"." + zeroFirstBack + ", "  + second+"." + zeroSecondBack;
                }
            } else if (secondNeg) {
                finalString =  first+"." + zeroFirstBack + ", -"  + second+"." + zeroSecondBack;
            } else {
                finalString =  first+"." + zeroFirstBack + ", "  + second+"." + zeroSecondBack;
            }
        }
        editing = finalString;
        return finalString;
    }

    public boolean twoNumbersNoLetters(String[] cood){

    try {
        cood[0] = cood[0].replaceAll(" ", "");
        cood[1] = cood[1].replaceAll(" ", "");
    }catch(ArrayIndexOutOfBoundsException  e){
        return false;
    }
        boolean firstNeg = false;
        boolean secondNeg = false;
        String first = cood[0];
        String second = cood[1];
        Character firstLetter = first.charAt(0);
        Character secondLetter = second.charAt(0);
        //Check for negatives
        if(0 == firstLetter.compareTo('-')){
            first = first.substring(1);
            firstNeg = true;
        }
        if(0 == secondLetter.compareTo('-')){
            second = second.substring(1);
            secondNeg = true;
        }
        int count = 0;
        if(first.contains(".")){
            if(first.indexOf(".") == 1 || first.indexOf(".") == 2) {
               //System.out.println(zero+ " first");
                String use = first.substring(first.indexOf(".") + 1);
                if (use.length() > 5) {
                    use = use.substring(0, 6);
                }
                if(firstNeg){
                    first = "-"+first.substring(0, first.indexOf(".") + 1) + use;
                    //  System.out.println(first+ " first");
                }else {
                    first = first.substring(0, first.indexOf(".") + 1) + use;

                }
            }else{
                return false;
            }
        }else{
            if(first.length()>1){
                return false;
            }
        }
        if(second.contains(".")){
            if(second.indexOf(".") == 1 ||second.indexOf(".") == 2 || second.indexOf(".") == 3) {
                String use = second.substring(second.indexOf(".") + 1);

                if (use.length() > 5) {
                    use = use.substring(0, 6);
                }
                if(secondNeg){
                    second = "-"+second.substring(0, second.indexOf(".") + 1) + use;
                }else {
                    second = second.substring(0, second.indexOf(".") + 1) + use;
                }
            }else{
                return false;
            }
        }else{
            if(second.length()>2){
                return false;
            }
        }
        if(isNumeric(first)){
            if(isNumeric(second)){
                editing = first +" "+ second;
                return true;
            }
        }
        return false;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}