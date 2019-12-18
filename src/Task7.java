import java.util.*;

public class Task7 {
    public static List operations = Arrays.asList('~', '-', '+', '*', '=', '%', '>', '<', '!', '(', ')');
    public static List arithoperations = Arrays.asList('~', '-', '+', '*', '=', '%', '>', '<', '!');
    public static List<String> originToPart = new ArrayList<String>();
    public static List<String> partToRPN = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Введите логическое выражение\n" +
                "Допускаются логические операции:\n 1) инверсия !;\n 2) дизъюнкция +;\n 3) конъюкция *;\n "
                        + "4) импликация -;\n 5) эквивалентность ~;\n 6) больше  >;\n 7) меньше <;\n 8) равно =;\n 9) неравно %\n " +
                "Цифровые значения будут приведены к виду 0==false, !0==true. Пожалуйста, используйте 0 и 1" +
                "Ваше выражение: ");
        Scanner in = new Scanner(System.in);
        String origin = in.nextLine();

        for (int i = 0; i < origin.length(); i++) {// разделение входной строки на элементы
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
        System.out.println(originToPart);
        originToPart.size();
        toRPN();
        System.out.println(partToRPN);
        for (int i = 0; i < partToRPN.size(); i++) {//замена буквенных значений на числовые
            if ("0987654321*^+()/-~%<>= ".indexOf(partToRPN.get(i).toCharArray()[0]) == -1) {
                System.out.print("Введите значение буквы " + partToRPN.get(i) + ": ");
                String tmp = in.nextLine();
                char[] tmp1 = tmp.toCharArray();
                partToRPN.remove(i);
                partToRPN.add(i, String.valueOf(tmp1));
            }
        }
        System.out.println();
        for (String element :
                partToRPN) {
            System.out.print(element + " ");
        }


        System.out.println("\nОтвет: " + fromRPN());
    }

    private static int getPriority(String operation) {//приоритеты операций
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

    private static void toRPN() {
        String answer = "";
        Stack stack = new Stack();
        for (String element : originToPart) {
            if (!operations.contains(element.toCharArray()[0])) {
                partToRPN.add(element);
            } else if (stack.isEmpty()) {
                stack.push(element);//добавляем в стек если не оператор
            } else if (arithoperations.contains(element.toCharArray()[0])) { //если оператор то...
                while (!stack.isEmpty() && getPriority(stack.lastElement().toString()) >= getPriority(element)) {//пока не дойдем до элемента с меньшим приоритетом или конца стека
                    partToRPN.add(stack.pop().toString());  //вытаскиваем из стека значения и кладем в "вывод"
                }
                stack.push(element);
            } else if (element.equals("(")) {
                stack.push(element);
            } else if (element.equals(")")) {

                while (!stack.isEmpty() && !stack.lastElement().equals("(")) {
                    /*System.out.println(stack);
                    System.out.println(answer);*/
                    partToRPN.add(stack.pop().toString());
                }
                stack.pop();
            }

        }
        while (!stack.isEmpty()) {
            partToRPN.add(stack.pop().toString());
        }
    }

    public static boolean toBoolean(double input) {
        return input != 0;
    }//приведение значений к 0 и 1

    private static boolean fromRPN() {
        Stack<Double> stack = new Stack<Double>();
        for (int i = 0; i < originToPart.size(); i++) {
            if (!operations.contains(partToRPN.get(i).toCharArray()[0])) {
                stack.push(Double.valueOf(partToRPN.get(i)));
                //System.out.println(stack);
            } else {
                if (partToRPN.get(i).equals("!")) { //условие инверсии
                    boolean arg = toBoolean(stack.pop());
                    stack.push(arg ? 0 : 1.0);
                } else {
                    double arg2 = stack.pop();
                    double arg1 = stack.pop();
                    switch (partToRPN.get(i)) { //выполнение операций
                        case ("~"):
                            stack.push((toBoolean(arg1) & toBoolean(arg2) | (!toBoolean(arg1) & !toBoolean(arg2))) ? 1.0 : 0);
                            break;
                        case ("-"):
                            stack.push((!toBoolean(arg1) | toBoolean(arg2)) ? 1.0 : 0);
                            break;
                        case ("+"):
                            stack.push((toBoolean(arg1) | toBoolean(arg2)) ? 1.0 : 0);
                            break;
                        case ("*"):
                            stack.push((toBoolean(arg1) & toBoolean(arg2)) ? 1.0 : 0);
                            break;
                        case ("="):
                            stack.push((toBoolean(arg1) == toBoolean(arg2)) ? 1.0 : 0);
                            break;
                        case ("%"):
                            stack.push((toBoolean(arg1) != toBoolean(arg2)) ? 1.0 : 0);
                            break;
                        case (">"):
                            stack.push(arg1 > arg2 ? 1.0 : 0);
                            break;
                        case ("<"):
                            stack.push(arg1 < arg2 ? 1.0 : 0);
                            break;
                    }
                }

            }
        }
        return toBoolean(stack.pop());//возвращаем результат
    }
}
