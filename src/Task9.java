import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Task9 {
    public static List operations = Arrays.asList('~', '-', '+', '*', '=', '%', '>', '<', '!', '(', ')');
    public static List arithoperations = Arrays.asList('+', '-', '*', '/', '^');
    public static List<String> originToPart= new ArrayList<String>();
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String origin = in.nextLine();

        for (int i = 0; i < origin.length(); i++) {
            //System.out.println(origin.charAt(i));
            if (operations.contains(origin.charAt(i))) {
                originToPart.add(String.valueOf(origin.charAt(i)));
            } else {
                String temp = "";
                if(origin.charAt(i)=='i' && origin.charAt(i+1)=='f'){
                    temp += "if";
                    originToPart.add(temp);
                    i++;
                } else if(origin.charAt(i)=='t' && origin.charAt(i+1)=='h' && origin.charAt(i+2)=='e'){
                    temp += "then";
                    originToPart.add(temp);
                    i+=3;
                } else if (origin.charAt(i)=='e' && origin.charAt(i+1)=='l' && origin.charAt(i+2)=='s'){
                    temp += "else";
                    originToPart.add(temp);
                    i+=3;
                } else{
                    while (i < origin.length() && !operations.contains(origin.charAt(i)) &&
                    !(origin.charAt(i)=='i' && origin.charAt(i+1)=='f') &&
                    !(origin.charAt(i)=='t' && origin.charAt(i+1)=='h' && origin.charAt(i+2)=='e')&&
                    !(origin.charAt(i)=='e' && origin.charAt(i+1)=='l' && origin.charAt(i+2)=='s')) {
                        temp += String.valueOf(origin.charAt(i));
                        i++;
                    }
                    i--;
                    originToPart.add(temp);
                }
            }
        }
        //if a > 0 then a = b else a = - b
        int s = originToPart.size();
        for (int i = 0; i < s; i++) {
            if (originToPart.get(i).equals("-") && (i == 0 || operations.contains(originToPart.get(i - 1).toCharArray()[0]))) {
                System.out.println("ass");
                List<String> originToPartTemp = new ArrayList<>();
                originToPartTemp.addAll(originToPart);
                originToPart.clear();
                int temp = originToPartTemp.size();
                for (int j = 0; j < temp; j++) {
                    if (i == j) {
                        //originToPart.add("(");
                        originToPart.add("0");
                        originToPart.add("-");
                        originToPart.add(originToPartTemp.get(j + 1));
                        //originToPart.add(")");
                        j++;
                    } else {
                        originToPart.add(originToPartTemp.get(j));
                    }
                }
                s = originToPart.size();
            }

        }
        System.out.println(originToPart);
        originToPart.size();
    }
}
