package Stubs;

import Service.UIController;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Scanner;

public class UIControllerStub extends UIController {

    private static int selector;

    public static void setSelector(int selector) {
        UIControllerStub.selector = selector;
    }

    public static String receiveString()
    {
        if(selector == 0)
        {
            return "rosengal";
        }

        return "";
    }

    public static int receiveInt()
    {
        if(selector == 0)
        {
            return 0;
        }

        return -1;
    }
}
