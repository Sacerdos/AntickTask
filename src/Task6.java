import java.util.*;

public class Task6 {
    public static List operations = Arrays.asList('+', '-', '*', '/', '(', ')', '^'); //операторы, для разделения элементов
    public static List arithoperations = Arrays.asList('+', '-', '*', '/', '^');
    public static List<String> originToPart = new ArrayList<String>();
    public static List<String> partToRPN = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Введите арифметическое выражение\n" +
                "Допускаются + - * / ( ) ^\n" +
                "Ваше выражение: ");
        Scanner in = new Scanner(System.in);
        String origin = in.nextLine();
        for (int i = 0; i < origin.length(); i++) { // разделение входной строки на элементы
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
            if (originToPart.get(i).equals("-") && (i == 0 || operations.contains(originToPart.get(i - 1).toCharArray()[0]))) {
                List<String> originToPartTemp = new ArrayList<>();
                originToPartTemp.addAll(originToPart);
                originToPart.clear();
                int temp = originToPartTemp.size();
                for (int j = 0; j < temp; j++) {
                    if (i == j) {
                        originToPart.add("(");
                        originToPart.add("0");
                        originToPart.add("-");
                        originToPart.add(originToPartTemp.get(j + 1));
                        originToPart.add(")");
                        j++;
                    } else {
                        originToPart.add(originToPartTemp.get(j));
                    }
                }
                s = originToPart.size();
            }

        }
        //System.out.println(originToPart);
        originToPart.size();
        toRPN();
        //System.out.println(partToRPN);
        for (String element :
                partToRPN) {
            System.out.print(element + " ");
        }
        System.out.println();
        for (int i = 0; i < partToRPN.size(); i++) { //замена буквенных значений на числовые
            if ("0987654321*^+()/-~ ".indexOf(partToRPN.get(i).toCharArray()[0]) == -1) {
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

    private static int getPriority(String operation) { //приоритеты операций
        switch (operation) {
            case ("("):
                return 0;
            case (")"):
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
            default:
                return -1;
        }
    }

    private static void toRPN() {
        Stack stack = new Stack();
        for (String element : originToPart) {
            if (!operations.contains(element.toCharArray()[0])) { //добавляем в стек если не оператор
                partToRPN.add(element + " ");
            } else if (stack.isEmpty()) {
                stack.push(element);
            } else if (arithoperations.contains(element.toCharArray()[0])) { //если оператор то...
                while (!stack.isEmpty() && getPriority(stack.lastElement().toString()) >= getPriority(element)) {//пока не дойдем до элемента с меньшим приоритетом или конца стека
                    partToRPN.add((stack.pop().toString())); // вытаскиваем из стека значения и кладем в "вывод"
                }
                stack.push(element); //кладем оператор
            } else if (element.equals("(")) {
                stack.push(element); //открывающуюся скобку кладем
            } else if (element.equals(")")) {
                while (!stack.isEmpty() && !stack.lastElement().equals("(")) { //при закрывающейся скобке вытаскиваем элементы в вывод вплоть до ближайшей открывающей
                    //System.out.println(stack);
                    //System.out.println(answer);
                    partToRPN.add(stack.pop().toString());
                }
                stack.pop();//убираем открывающую скобку
            }

        }
        while (!stack.isEmpty()) {
            partToRPN.add(stack.pop().toString()); // "добиваем" оставшееся в стеке
        }
    }

    private static double fromRPN() {
        Stack<Double> stack = new Stack<Double>();
        for (int i = 0; i < partToRPN.size(); i++) {
            if (!operations.contains(partToRPN.get(i).toCharArray()[0])) {
                stack.push(Double.valueOf(partToRPN.get(i))); //в стек если число
                //System.out.println(stack);
            } else {
                double arg2 = stack.pop(); //достаем два последних значения из стека для проведения операции
                double arg1 = stack.pop();
                switch (partToRPN.get(i)) { //в стек кладется результат выполнения
                    case ("+"):
                        stack.push(arg1 + arg2);
                        break;
                    case ("-"):
                        stack.push(arg1 - arg2);
                        break;
                    case ("*"):
                        stack.push(arg1 * arg2);
                        break;
                    case ("/"):
                        stack.push(arg1 / arg2);
                        break;
                    case ("^"):
                        stack.push(Math.pow(arg1, arg2));
                        break;
                }
            }
        }
        return stack.pop();//возвращаем вычисленное значение
    }
}
