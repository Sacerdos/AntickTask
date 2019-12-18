import java.util.*;

public class Task9 {
    public static List operations = Arrays.asList('~', '-', '+', '*', '=', '%', '>', '<', '!', '(', ')');
    public static List arithoperations = Arrays.asList('~', '-', '+', '*', '=', '%', '>', '<', '!');
    public static List<String> originToPart = new ArrayList<String>();
    public static List<String> transformed = new ArrayList<String>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String origin = in.nextLine();

        for (int i = 0; i < origin.length(); i++) {
            //System.out.println(origin.charAt(i));
            if (operations.contains(origin.charAt(i))) {
                originToPart.add(String.valueOf(origin.charAt(i)));
            } else {
                String temp = "";
                if (origin.charAt(i) == 'i' && origin.charAt(i + 1) == 'f') {
                    temp += "if";
                    originToPart.add(temp);
                    i++;
                } else if (origin.charAt(i) == 't' && origin.charAt(i + 1) == 'h' && origin.charAt(i + 2) == 'e') {
                    temp += "then";
                    originToPart.add(temp);
                    i += 3;
                } else if (origin.charAt(i) == 'e' && origin.charAt(i + 1) == 'l' && origin.charAt(i + 2) == 's') {
                    temp += "else";
                    originToPart.add(temp);
                    i += 3;
                } else {
                    while (i < origin.length() && !operations.contains(origin.charAt(i)) &&
                            !(origin.charAt(i) == 'i' && origin.charAt(i + 1) == 'f') &&
                            !(origin.charAt(i) == 't' && origin.charAt(i + 1) == 'h' && origin.charAt(i + 2) == 'e') &&
                            !(origin.charAt(i) == 'e' && origin.charAt(i + 1) == 'l' && origin.charAt(i + 2) == 's')) {
                        temp += String.valueOf(origin.charAt(i));
                        i++;
                    }
                    i--;
                    originToPart.add(temp);
                }
            }
        }
        //if a > 0 then a = b else a = - b
        //System.out.println(originToPart);

        for (int i = 0; i < originToPart.size(); i++) {
            List<String> temper = new ArrayList<String>();
            if (originToPart.get(i).equals("if") || originToPart.get(i).equals("then") || originToPart.get(i).equals("else")) {
                if (originToPart.get(i).equals("if")) {
                    transformed.add(originToPart.get(i));
                    i++;
                    while (i < originToPart.size() && !originToPart.get(i).equals("if") && !originToPart.get(i).equals("then") && !originToPart.get(i).equals("else")) {
                        temper.add(originToPart.get(i));
                        i++;
                    }
                    if (temper.size() > 0) {
                        transformed.add(toRPNlog(temper));
                    }
                    i--;
                } else {
                    transformed.add(originToPart.get(i));
                    i++;
                    while (i < originToPart.size() && !originToPart.get(i).equals("if") && !originToPart.get(i).equals("then") && !originToPart.get(i).equals("else")) {
                        temper.add(originToPart.get(i));
                        i++;
                    }
                    if (temper.size() > 0) {
                        transformed.add(toRPNset(temper));
                    }
                    i--;
                }

            }
        }
        System.out.println(transformed);
        System.out.println("Длина: " + (transformed.size()-1));
        for (int i = 1; i < transformed.size(); i++) {
            if (transformed.get(i - 1).equals("if")) {
                System.out.print("[" + transformed.get(i) + "]" + " p" + (transformed.indexOf("else")) + "F ");
            }
            if (transformed.get(i - 1).equals("then")) {
                System.out.print("[" + transformed.get(i) + "]" + " p" + (transformed.indexOf("else") + 1) + " ");
            }
            if (transformed.get(i - 1).equals("else")) {
                System.out.print("[" + transformed.get(i) + "]");
            }
        }

    }

    private static int getPrioritylog(String operation) {
        switch (operation) {
            case "(":
                return 0;
            case ")":
                return 1;
            case "~":
                return 2;
            case "-":
                return 3;
            case "+":
                return 3;
            case "*":
                return 4;
            case "=":
                return 5;
            case "%":
                return 5;
            case ">":
                return 5;
            case "<":
                return 5;
            case "!":
                return 6;
            default:
                return 7;

        }
    }

    public static List operationslog = Arrays.asList('~', '-', '+', '*', '=', '%', '>', '<', '!', '(', ')');
    public static List arithoperationslog = Arrays.asList('~', '-', '+', '*', '=', '%', '>', '<', '!');

    private static String toRPNlog(List<String> temp) {
        String answer = "";
        //System.out.println(temp);
        Stack stack = new Stack();
        for (String element : temp) {
            if (!operationslog.contains(element.toCharArray()[0])) {
                answer += (element + " ");
            } else if (stack.isEmpty()) {
                stack.push(element);
            } else if (arithoperationslog.contains(element.toCharArray()[0])) {
                while (!stack.isEmpty() && getPrioritylog(stack.lastElement().toString()) >= getPrioritylog(element)) {
                    answer += (stack.pop().toString() + " ");
                }
                stack.push(element);
            } else if (element.equals("(")) {
                stack.push(element);
            } else if (element.equals(")")) {
                while (!stack.isEmpty() && !stack.lastElement().equals("(")) {
                    //System.out.println(stack);
                    //System.out.println(answer);
                    answer += (stack.pop().toString() + " ");
                }
                stack.pop();
            }

        }
        while (!stack.isEmpty()) {
            answer += (stack.pop().toString() + " ");
        }
        return answer;
    }

    private static int getPriorityset(String operation) {
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

    public static List operationsset = Arrays.asList('+', '-', '*', '/', '(', ')', '^', '=');
    public static List arithoperationsset = Arrays.asList('~', '-', '+', '*', '=', '%', '>', '<', '!');

    private static String toRPNset(List<String> temp) {
        int s = temp.size();
        for (int i = 0; i < s; i++) {
            if (temp.get(i).equals("-") && (i == 0 || (operationsset.contains(temp.get(i - 1).toCharArray()[0]) && !temp.get(i + 1).equals("=")))) {
                List<String> originToPartTemp = new ArrayList<>();
                originToPartTemp.addAll(temp);
                temp.clear();
                int temper = originToPartTemp.size();
                for (int j = 0; j < temper; j++) {
                    if (i == j) {
                        temp.add("(");
                        temp.add("0");
                        temp.add("-");
                        temp.add(originToPartTemp.get(j + 1));
                        //System.out.println(originToPartTemp.get(j+1));
                        temp.add(")");
                        j++;
                    } else {
                        temp.add(originToPartTemp.get(j));
                    }
                }
                s = temp.size();
            }

        }
        String answer = "";
        Stack<String> stack = new Stack();
        String operator = "+-/*^()=";
        boolean flag = false;
        for (int i = 0; i < temp.size(); i++) {
            if (operator.indexOf(temp.get(i)) == -1) {
                answer += temp.get(i);
                answer += " ";
                if (temp.size() > i + 2) {
                    if (temp.get(i + 2).equals("=")) {
                        answer += temp.get(i) + " ";
                    }
                }
            }
            if (operator.indexOf(temp.get(i)) != -1) {
                //Если аргумент = (, добавляем в стек
                if (temp.get(i).equals("("))
                    stack.push("(");

                //Если аргумент = ), выталкиваем элементы из стека до (
                if (temp.get(i).equals(")")) {
                    while (!stack.empty() && !stack.peek().equals("("))
                        answer += stack.pop() + " ";
                    if (!stack.empty())
                        stack.pop();

                }
            	  /* Если стек не пуст и приоритетность аргумента <= приоритетности последнего
                  элемента в стеке, выталкиваем из стека */
                if (temp.get(i) != "(" && temp.get(i) != ")") {
                    if (!stack.empty()) {
                        while (!stack.empty() && getPriorityset(temp.get(i)) <= getPriorityset(stack.peek()))

                            answer += stack.pop() + " ";
                    }
                    //Добавление аргумента в стек
                    if (!temp.get(i + 1).equals("="))
                        stack.push(temp.get(i));
                    if (temp.get(i + 1).equals("=") && flag == false) {
                        stack.push(temp.get(i + 1));
                        stack.push(temp.get(i));
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
