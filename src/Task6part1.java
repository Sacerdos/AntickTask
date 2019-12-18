import java.util.*;

public class Task6part1 {
    public static List operations = Arrays.asList('+', '-', '*', '/', '(', ')', '^');
    public static List arithoperations = Arrays.asList('+', '-', '*', '/', '^');
    public static List<String> originToPart= new ArrayList<String>();
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String origin = in.nextLine();

        for(int i=0; i<origin.length(); i++){
            //System.out.println(origin.charAt(i));
            if(operations.contains(origin.charAt(i))){
                originToPart.add(String.valueOf(origin.charAt(i)));
            } else {
                String temp = "";
                while(i<origin.length() && !operations.contains(origin.charAt(i))){
                    temp+=String.valueOf(origin.charAt(i));
                    i++;
                }
                i--;
                originToPart.add(temp);
            }
        }
        int s = originToPart.size();
        for (int i = 0; i < s; i++) {
            if(originToPart.get(i).equals("-") && ( i==0 || operations.contains(originToPart.get(i-1).toCharArray()[0]))){
                List<String> originToPartTemp = new ArrayList<>();
                originToPartTemp.addAll(originToPart);
                originToPart.clear();
                int temp = originToPartTemp.size();
                for (int j = 0; j < temp; j++) {
                    if(i==j){
                        originToPart.add("(");
                        originToPart.add("0");
                        originToPart.add("-");
                        originToPart.add(originToPartTemp.get(j+1));
                        originToPart.add(")");
                        j++;
                    } else{
                        originToPart.add(originToPartTemp.get(j));
                    }
                }
                s = originToPart.size();
            }

        }
        System.out.println(originToPart);
        originToPart.size();
        System.out.println(toRPN());
    }
    private static int getPriority(String operation){
        switch(operation){
            case("("): return 0;
            case(")"): return 1;
            case("+"): return 2;
            case("-"): return 2;
            case("*"): return 3;
            case("/"): return 3;
            case("^"): return 3;
            default: return -1;
        }
    }
    private static String toRPN(){
        String answer = "";
        Stack stack = new Stack();
        for (String element:originToPart) {
            if(!operations.contains(element.toCharArray()[0])){
                answer+=(element+" ");
            } else if(stack.isEmpty()){
                stack.push(element);
            } else if(arithoperations.contains(element.toCharArray()[0])){
                while(!stack.isEmpty() && getPriority(stack.lastElement().toString())>=getPriority(element)){
                    answer+=(stack.pop().toString()+" ");
                }
                stack.push(element);
            } else if(element.equals("(")){
                stack.push(element);
            } else if(element.equals(")")){
                while(!stack.isEmpty() && !stack.lastElement().equals("(")){
                    //System.out.println(stack);
                    //System.out.println(answer);
                    answer+=(stack.pop().toString()+" ");
                }
                stack.pop();
            }

        }
        while(!stack.isEmpty()){
            answer+=(stack.pop().toString()+" ");
        }
        return answer;
    }
}
