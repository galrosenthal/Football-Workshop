package Service;

import java.util.Scanner;

public class UIController {

    private static boolean isTest = false;
    private static int selector = 0;

    public static void setSelector(int selector) {
        UIController.selector = selector;
    }

    public static void setIsTest(boolean isTest) {
        UIController.isTest = isTest;
    }

    public static void printMessage(String msg)
    {
        System.out.println(msg);
    }


    public static String receiveString()
    {
        if(!isTest){
            Scanner sc = new Scanner(System.in);

            String line = sc.nextLine();

            return line;
        }
        else
        {
            if(selector == 0)
            {
                return "Not a username";
            }
            else
            {
                return "rosengal";
            }
        }
    }

    public static int receiveInt()
    {
        if(!isTest)
        {
            Scanner sc = new Scanner(System.in);
            int integer = sc.nextInt();

            return integer;
        }
        else
        {
            if(selector == 0)
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
    }
}
