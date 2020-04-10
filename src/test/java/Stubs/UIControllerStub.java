package Stubs;

import Service.UIController;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Scanner;

public class UIControllerStub extends UIController {

    private static int selector;

    public void setSelector(int selector) {
        UIControllerStub.selector = selector;
    }

    public String receiveString()
    {
        if(selector == 0)
        {
            return "rosengal";
        }

        return "";
    }





    public int receiveInt()
    {
        if(selector == 0)
        {
            return 0;
        }

        return -1;
    }
}
