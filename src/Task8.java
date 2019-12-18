import java.util.*;

public class Task8 {
    public static List operations = Arrays.asList('+', '-', '*', '/', '(', ')', '^', '=');
    public static List arithoperations = Arrays.asList('~', '-', '+', '*', '=', '%', '>', '<', '!');
    public static List<String> originToPart = new ArrayList<String>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String origin = in.nextLine();

        for (int i = 0; i < origin.length(); i++) {
            //System.out.println(origin.charAt(i));
            if (operations.contains(origin.charAt(i))) {
                originToPart.add(String.valueOf(origin.charAt(i)));
            } else {
                String temp = "";
                while (i < origin.length() && !operations.contains(origin.charAt(i))) {
                    temp += String.valueOf(origin.charAt(i));
                    i++;
                }
                i--;
                originToPart.add(temp);
            }
        }
        int s = originToPart.size();
        for (int i = 0; i < s; i++) {
            if(originToPart.get(i).equals("-") && ( i==0 || (operations.contains(originToPart.get(i-1).toCharArray()[0]) && !originToPart.get(i+1).equals("=")))){
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
                        //System.out.println(originToPartTemp.get(j+1));
                        originToPart.add(")");
                        j++;
                    } else{
                        originToPart.add(originToPartTemp.get(j));
                    }
                }
                s = originToPart.size();
            }

        }
        //0 b - 0 3 - - =
        System.out.println(originToPart);
        originToPart.add(")");
        //System.out.println(originToPart);
        originToPart.size();
        System.out.println(toRPN());
    }

    private static int getPriority(String operation) {
        switch (operation) {
            case "(":
                return 0;
            case ")":
                return 1;
            case ("+"):
                return 2;
            case ("-"):
                return 2;
            case ("*"):
                return 3;
            case ("/"):
                return 3;
            case ("^"):
                return 3;
            case "=":
                return -1;

            default:
                return 5;
        }
    }

    private static String toRPN() {
        String answer = "";
        Stack<String> stack = new Stack();
        String operator = "+-/*^()=";
        boolean flag = false;
        for (int i = 0; i < originToPart.size(); i++) {
            if (operator.indexOf(originToPart.get(i)) == -1) {
                answer += originToPart.get(i);
                answer += " ";
                if (originToPart.size() > i + 2) {
                    if (originToPart.get(i + 2).equals("=")) {
                        answer += originToPart.get(i) + " ";
                    }
                }
            }
            if (operator.indexOf(originToPart.get(i)) != -1) {
                //Если аргумент = (, добавляем в стек
                if (originToPart.get(i).equals("("))
                    stack.push("(");

                //Если аргумент = ), выталкиваем элементы из стека до (
                if (originToPart.get(i).equals(")")) {
                    while (!stack.empty() && !stack.peek().equals("("))
                        answer += stack.pop() + " ";
                    if (!stack.empty())
                        stack.pop();

                }
            	  /* Если стек не пуст и приоритетность аргумента <= приоритетности последнего
                  элемента в стеке, выталкиваем из стека */
                if (originToPart.get(i) != "(" && originToPart.get(i) != ")") {
                    if (!stack.empty()) {
                        while (!stack.empty() && getPriority(originToPart.get(i)) <= getPriority(stack.peek()))

                            answer += stack.pop() + " ";
                    }
                    //Добавление аргумента в стек
                    if (!originToPart.get(i + 1).equals("="))
                        stack.push(originToPart.get(i));
                    if (originToPart.get(i + 1).equals("=") && flag == false) {
                        stack.push(originToPart.get(i + 1));
                        stack.push(originToPart.get(i));
                        stack.push("(");
                        flag = true;
                        i++;
                    }

                }
            }
        }

        while (!stack.isEmpty()) {
            answer += (stack.pop() + " ");
        }
        return answer;
    }
}
