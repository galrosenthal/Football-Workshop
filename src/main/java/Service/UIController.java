package Service;

import java.util.Scanner;

public class UIController {



    public void printMessage(String msg)
    {
        System.out.println(msg);
    }


    public String receiveString()
    {
        Scanner sc = new Scanner(System.in);

        String line = sc.nextLine();

        return line;
    }

    public int receiveInt()
    {
        Scanner sc = new Scanner(System.in);

        int integer = sc.nextInt();

        return integer;
    }
}
