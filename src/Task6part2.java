import java.util.*;

public class Task6part2 {
    public static List operations = Arrays.asList('+', '-', '*', '/', '^');
    public static List<String> originToPart= new ArrayList<String>();
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String origin = in.nextLine();
        originToPart = Arrays.asList(origin.split(" "));
        System.out.println(originToPart);
        Stack<Double> stack2 = new Stack<Double>();
        originToPart.size();
        System.out.println(fromRPN());
    }

    private static double fromRPN(){
        Stack<Double> stack = new Stack<Double>();
        for(int i=0; i<originToPart.size();i++){
            if(!operations.contains(originToPart.get(i).toCharArray()[0])){
                stack.push(Double.valueOf(originToPart.get(i)));
                System.out.println(stack);
            } else{
                double arg2=stack.pop();
                double arg1=stack.pop();
                switch (originToPart.get(i)){
                    case("+"): stack.push(arg1+arg2);
                        break;
                    case("-"): stack.push(arg1-arg2);
                        break;
                    case("*"): stack.push(arg1*arg2);
                        break;
                    case("/"): stack.push(arg1/arg2);
                        break;
                    case("^"): stack.push(Math.pow(arg1, arg2));
                        break;
                }
            }
        }
        return stack.pop();
    }
}
