import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
public class Main {

/*
	
if a > 0 then a = b else a = - b
a 0 > 10 !F a b = 15 !! a 0 b - = 
	*/

    //Возвращает приоритет операции
    public static int GetPriority(String oper)
    {
        switch (oper)
        {
            case "(": return 0;
            case ")": return 1;
            case "=": return 2;
            case "+": return 3;
            case "-": return 3;
            case "->": return 4;
            case "&": return 4;
            case "!": return 5;
            case "~": return 6;
            case "!=": return 6;
            case ">": return 6;
            case "<": return 6;
            default: return 100;
        }
    }
    //перевод в PN
    public static String Transfer(String str) {
        //проверка на унарный минус
        str = UnarMinus(str);
        String[] stringArray = str.split(" ");
        String output = "";//итоговое значение
        Stack<String> operStack = new Stack<String>();//стек символов
        ArrayList<Integer> list = new ArrayList<Integer>();
        String[] resStrArray;
        int tF = 0;
        int tE = -1;
        for (int i = 0; i < stringArray.length; i++)
        {
            resStrArray = output.split(" ");
            //Если встречается if - записываем стек в строку
            if (stringArray[i].equals("if"))
            {
                while (!operStack.isEmpty())
                    output += operStack.pop() + " ";
            }
            //Если встречается then - записываем стек в строку и значек перехода по лжи
            else if (stringArray[i].equals("then"))
            {
                while (!operStack.isEmpty())
                    output += operStack.pop() + " ";
                output += "tF" + tF + " !F ";
                tF++;

            }
            //Если встречается then - записываем стек в строку и значек прямого перехода
            else if (stringArray[i].equals("else"))
            {
                while (!operStack.isEmpty())
                    output += operStack.pop() + " ";
                list.add(resStrArray.length);
                tE++;
                output += "tE" + tE + " !! ";
            }
            //Иначе работаем как с обычной PN
            else
            {
                if (checkNum(stringArray[i]) || isLetters(stringArray[i]))
                {
                    output += stringArray[i] + " ";
                }
                else
                {
                    if (operStack.isEmpty() || stringArray[i].equals("("))
                    {
                        operStack.push(stringArray[i]);
                    }
                    else
                    {
                        if (stringArray[i].equals(")"))
                        {
                            while (!operStack.peek().equals("("))
                            {
                                output += operStack.pop() + " ";
                            }
                            operStack.pop();
                        }
                        else
                        {

                            while(GetPriority(stringArray[i]) <= GetPriority(operStack.peek()))
                            {
                                output += operStack.pop() + " ";
                                if (operStack.empty()) break;
                            }
                            operStack.push(stringArray[i]);
                        }
                    }

                }
            }
        }

        //Добавление в итоговою строку, пока стек не пуст
        while (!operStack.isEmpty())
        {
            output += operStack.pop() + " ";
        }
        //Постановка переходов
        resStrArray = output.split(" ");
        int tFIndex = 0;
        int tEIndex = 0;
        for(int i = 0; i < resStrArray.length; i++)
        {
            if(resStrArray[i].equals("tF" + tFIndex))
            {

                for (int j = i+2; j < resStrArray.length; j++)
                {
                    if(resStrArray[j].equals("!!"))
                    {
                        int tFind = i+1;
                        while (!resStrArray[tFind].equals("!!") && (tFind < resStrArray.length - 1))
                            tFind++;

                        resStrArray[i] = String.valueOf((tFind +1));
                        tFIndex++;
                        break;
                    }
                    if(resStrArray[j].equals("!F"))
                    {

                        resStrArray[i] = String.valueOf(resStrArray.length);
                        tFIndex++;
                        break;
                    }
                }
            }

///=================================================================================
            if(resStrArray[i].equals("tE" + tEIndex))
            {
                boolean isEndElse = false;
                for (int j = i+2; j < resStrArray.length; j++)
                {
                    if(resStrArray[j].equals("!!"))
                    {
                        int indF = i + 1;
                        while (!resStrArray[indF].equals("!F") && !resStrArray[indF].equals("!!") && !resStrArray[indF].contains("tE") && !resStrArray[indF].contains("tF") && (indF < resStrArray.length - 1))
                            indF++;

                        resStrArray[i] = String.valueOf((indF + 1));
                        tEIndex++;
                        isEndElse = true;
                        break;
                    }
                    if(resStrArray[j].equals("!F")||!isEndElse)
                    {
                        resStrArray[i] = String.valueOf(resStrArray.length);
                        tEIndex++;
                        break;
                    }
                }

            }

        }
        //записываем массив в строку
        output = "";

        for(int i = 0; i < resStrArray.length; i++)
        {
            output += resStrArray[i] + " ";
        }
        return output;
    }

    //является ли строка числом
    public static boolean checkNum(String string)
    {
        if (string == null) return false;
        return string.matches("^-?\\d+$");
    }


    //является ли строка словом
    private static boolean isLetters(String s)
    {
        for(char c : s.toCharArray())
        {
            if(!Character.isLetter(c)) return false;
        }
        return true;
    }


    //проверка на унарный минус
    private static String UnarMinus(String s)
    {
        String[] temp =  s.split(" ");
        for(int i = 1; i < temp.length-1; i++)
        {
            if(GetPriority(temp[i])!=100 && !(checkNum(temp[i-1]) || isLetters(temp[i-1]) || temp[i].equals("if") || temp[i].equals("else") || temp[i].equals("then")))
            {
                temp[i] = "";
                temp[i + 1] = "0 - " + temp[i + 1];
            }
        }
        s = "";
        for(int i = 0; i < temp.length; i++)
        {
            if(!temp[i].equals("")) s += temp[i] + " ";
        }
        return s;
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println(" эквивалентность - ~ \n логическое следование - -> \n логическое сложение - + \n логическое умножение - & \n не равно - != \n больше - > \n меньше - <\n логическое отрицание - !\n" );
        System.out.println("Введите выражение\n");
        //Чтение исходной строки
        String  c = in.nextLine();
        //Перевод в PN
        String itog = Transfer(c);
        //Вывод итогового значения
        System.out.println(itog);
    }
}