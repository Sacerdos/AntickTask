import java.util.*;

public class Task7part2 {
    public static List operations = Arrays.asList('~', '-', '+', '*', '=', '%', '>', '<', '!');;
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
    public static boolean toBoolean(double input)
    {
        return input!=0;
    }
    private static boolean fromRPN(){
        Stack<Double> stack = new Stack<Double>();
        for(int i=0; i<originToPart.size();i++){
            if(!operations.contains(originToPart.get(i).toCharArray()[0])){
                stack.push(Double.valueOf(originToPart.get(i)));
                System.out.println(stack);
            } else{
                if(originToPart.get(i).equals("!")){
                    boolean arg=toBoolean(stack.pop());
                    stack.push(arg?0:1.0);
                } else{
                    double arg2=stack.pop();
                    double arg1=stack.pop();
                    switch (originToPart.get(i)){
                        case("~"): stack.push((toBoolean(arg1)&toBoolean(arg2)|(!toBoolean(arg1)&!toBoolean(arg2)))?1.0:0);
                            break;
                        case("-"): stack.push((!toBoolean(arg1)|toBoolean(arg2))?1.0:0);
                            break;
                        case("+"): stack.push((toBoolean(arg1)|toBoolean(arg2))?1.0:0);
                            break;
                        case("*"): stack.push((toBoolean(arg1)&toBoolean(arg2))?1.0:0);
                            break;
                        case("="): stack.push((toBoolean(arg1)==toBoolean(arg2))?1.0:0);
                            break;
                        case("%"): stack.push((toBoolean(arg1)!=toBoolean(arg2))?1.0:0);
                            break;
                        case(">"): stack.push(arg1>arg2?1.0:0);
                            break;
                        case("<"): stack.push(arg1<arg2?1.0:0);
                            break;
                    }
                }

            }
        }
        return toBoolean(stack.pop());
    }
}
