package Service;

import GUI.FootballMain;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

import java.util.Date;
import java.util.Scanner;

public class UIController {

    private static boolean isTest = false;
    private static int selector = 0; //latest = 10

    public static final String STRING_DELIMETER = ";";
    public static final String SEND_TYPE_FOR_GUI_STRING = "string";
    public static final String SEND_TYPE_FOR_GUI_INT = "int";
    public static final String SEND_TYPE_FOR_GUI_MULTIPLE_STRINGS = "multiple";
    public static final String CANCEL_TASK_VALUE = "canceled";


    public static void setSelector(int selector) {
        UIController.selector = selector;
    }

    public static void setIsTest(boolean isTest) {
        UIController.isTest = isTest;
    }

    /**
     * This Function is showing a notification to the user
     * do not use for messaging the user
     *
     * @param notification the message to include in the notification
     */
    public static void showNotification(String notification) {
        if (!isTest) {
            UI lastUI = UI.getCurrent();
            VaadinSession se = VaadinSession.getCurrent();
            se.access(() -> FootballMain.showNotification(notification));
        } else {
            System.out.println(notification);
        }
    }


    /**
     * Receiving multiple inputs from the user.
     *
     * @param messagesToDisplay String which contains the message to put for each input,
     *                          with the delimiter ";" between each message
     * @return String - The inputs from the user to each input, with the delimiter ";" between each input
     */
    public static String receiveStringFromMultipleInputs(String messagesToDisplay, Collection<String>... valuesToChooseFrom) throws CancellationException {
        if (isTest) {
            printMessageAndValuesForTest(messagesToDisplay, valuesToChooseFrom);
            if (selector == 9511 || selector == 9521 || selector == 95211) {
                return "1;-1;0";
            } else if (selector == 962) {
                return "1;1;1";
            } else if (selector == 9514) {
                return "1;1;-1";
            } else if (selector == 9512) {
                return "-1;0;1";
            } else if (selector == 961) {
                return "0;1;1";
            } else {
                return "-2,1,a"; // not legal
            }
        } else {
            StringBuilder line = new StringBuilder();
            UI lastUI = UI.getCurrent();
            Thread t = Thread.currentThread();
            VaadinSession se = VaadinSession.getCurrent();
            VaadinService srvc = VaadinService.getCurrent();

            Future<Void> returnValue = se.access(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                FootballMain.showDialog(lastUI, messagesToDisplay, SEND_TYPE_FOR_GUI_MULTIPLE_STRINGS, line, t, valuesToChooseFrom);
                lastUI.access(() -> {
                    lastUI.push();
                });
                System.out.println("Closed Dialog");
            });

            while (line.length() == 0) {
                try {
                    //waiting for the user to close the dialog
                    sleep(1000);
//                    System.out.println("Waiting for the user input");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (line.toString().equals(CANCEL_TASK_VALUE)) {
                throw new CancellationException();
            }
            System.out.println(line.toString());
            return line.toString();

        }
    }

    /**
     * This function is receiving a string from the user
     * in the production this function will call a popup window with the message for the user
     *
     * @param messageToDisplay a message to display to the user
     * @return
     */
    public static String receiveString(String messageToDisplay, Collection<String>... valuesToChooseFrom) throws CancellationException {
        if (!isTest) {
            StringBuilder line = new StringBuilder();
            UI lastUI = UI.getCurrent();
            Thread t = Thread.currentThread();
            VaadinSession se = VaadinSession.getCurrent();

            Future<Void> returnValue = se.access(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                FootballMain.showDialog(lastUI, messageToDisplay, SEND_TYPE_FOR_GUI_STRING, line, t, valuesToChooseFrom);
                System.out.println("Closed Dialog");
            });

            while (line.length() == 0) {
                try {
                    //waiting for the user to close the dialog
                    sleep(1000);
//                    System.out.println("Waiting for the user input");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (line.toString().equals(CANCEL_TASK_VALUE)) {
                throw new CancellationException();
            }
            System.out.println(line.toString());
            return line.toString();

        } else {
            printMessageAndValuesForTest(messageToDisplay, valuesToChooseFrom);
            if (selector == 0) {
                return "Not a username";
            } else if (selector == 1) {
                return "rosengal";
            } else if (selector == 2) {
                return "newTOUsername";
            } else if (selector == 3) {
                selector = 4;
                return "admin";
            } else if (selector == 4) {
                return "12345678";
            } else if (selector == 5) {
                return "newLeagueName";
            } else if (selector == 6) {
                return "Premier League";
            } else if (selector == 921) {//7
                selector = 922;
                return "2020/21";
            } else if (selector == 922) {//8
                selector = 923;
                return "2020/21";
            } else if (selector == 923) {//9
                return "2021/22";
            } else if (selector == 924) {//10
                selector = 923;
                return "wrong Format";
            } else if (selector == 6117) {
                return "01/11/1199";
            } else if (selector == 6118) {
                return "CoachJob";
            } else if (selector == 6119) {
                return "StadiumName";
            } else if (selector == 61110) {
                setSelector(6117);
                return "StadiumName";
            } else if (selector == 6136 || selector == 6137) {
                return "Test ";
            } else if (selector == 6132) {
                return "11/11/2011";
            } else if (selector == 61113) {
                return "test";
            } else if (selector == 61114) {
                return "anotherUser";
            } else if (selector == 61116) {
                setSelector(61117);
                return "anotherUser";
            } else if (selector == 61117) {
                return "01/11/1199";
            } else if (selector == 61118) {
                setSelector(61119);
                return "elevy";
            } else if (selector == 61119) {
                return "01/11/1199";
            } else if (selector == 61310) {
                return "AESSEAL";
            } else if (selector == 921) {//7
                selector = 922;
                return "2020/21";
            } else if (selector == 922) {//8
                selector = 923;
                return "2020/21";
            } else if (selector == 923) {//9
                return "2021/22";
            } else if (selector == 924) {//10
                selector = 923;
                return "wrong Format";
            } else if (selector == 9311 || selector == 91012) {
                selector = 9313;
                return "AviCohen";
            } else if (selector == 9312 || selector == 91032 || selector == 910311) {
                selector = 9311;
                return "NOTaUSERNAME";
            } else if (selector == 9313) { //training
                return "VAR";
            } else if (selector == 91011 || selector == 91021 || selector == 91031 || selector == 9103
                    || selector == 91041 || selector == 91051 || selector == 91052 || selector == 91053 || selector == 912321 || selector == 911321) { //team name
                if (selector == 91031)
                    selector = 91032;
                else if (selector == 9103)
                    selector = 910311;
                else
                    selector = 91012; // avi cohen
                return "Hapoel Beit Shan";
            } else if (selector == 91112 || selector == 91212) {
//                setSelector(91213);
                return "Hapoel Beit Shan;Hapoel Beer Sheva";
            } else if (selector == 911262) {
                return "stubTeam91126";
            } else if (selector == 9102) {
                return "stubTeam9102";
            } else if (selector == 631) {
                selector = 632;
                return "Hapoel Ta";
            } else if (selector == 632) {
                return "gal";
            } else if (selector == 633) {
                return "merav";
            } else if (selector == 634) {
                return "nir";
            } else if (selector == 635) {
                return "ifatch";
            } else
                return null;
        }
    }


    private static void printMessageAndValuesForTest(String messageToDisplay, Collection<String>... valuesToChooseFrom) {
        System.out.println(messageToDisplay);
        int indexForCount = 1;
        if (valuesToChooseFrom.length > 0) {
            for (String value :
                    valuesToChooseFrom[0]) {
                System.out.println(indexForCount++ + ". " + value);
            }
        }
    }

    public static int receiveInt(String messageToDisplay, Collection<String>... valuesToDisplay) throws CancellationException {
        if (!isTest) {
            StringBuilder line = new StringBuilder();
            UI lastUI = UI.getCurrent();
            Thread t = Thread.currentThread();
            VaadinSession se = VaadinSession.getCurrent();

            Future<Void> returnValue = se.access(() -> {
                UI.setCurrent(lastUI);
                VaadinSession.setCurrent(se);
                String type = "";
                if (valuesToDisplay.length == 0) {
                    type = "number";
                } else {
                    type = "int";
                }
                FootballMain.showDialog(lastUI, messageToDisplay, SEND_TYPE_FOR_GUI_INT, line, t, valuesToDisplay);
                System.out.println("Closed Dialog");
            });


            while (line.length() == 0) {
                try {
                    //waiting for the user to close the dialog
                    sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (line.toString().equals(CANCEL_TASK_VALUE)) {
                throw new CancellationException();
            }


            return Integer.parseInt(line.toString());
        } else {
            printMessageAndValuesForTest(messageToDisplay, valuesToDisplay);
            if (selector == 0 || selector == 1 || selector == 2 || selector == 6117 || selector == 6118 || selector == 921 || selector == 922 || selector == 924 || selector == 9321) {
                return 0;
            } else if (selector == 61111) {
                setSelector(61112);
                return 1000;
            } else if (selector == 61112) {
                setSelector(61113);
                return 0;
            } else if (selector == 61113) {
                setSelector(61112);
                return 1000;
            } else if (selector == 61114) {
                return 0;
            } else if (selector == 61115) {
                setSelector(61116);
                return 0;
            } else if (selector == 61116) {
                return 0;
            } else if (selector == 61117) {
                return 0;
            } else if (selector == 61118) {
                return 0;
            } else if (selector == 61119) {
                return 0;
            } else if (selector == 61120 || selector == 6623 || selector == 6622 || selector == 6612 || selector == 6611
                    || selector == 6621 || selector == 66151 || selector == 66144 || selector == 66143 || selector == 6625
                    || selector == 66163 || selector == 66251 || selector == 91125 || selector == 91225 ||
                    selector == 91131 || selector == 91231) {
                return 0;
            } else if (selector == 6132 || selector == 6133 || selector == 6134 || selector == 61341 || selector == 6135 || selector == 6136 || selector == 6137 || selector == 6138 || selector == 61383 || selector == 61381 || selector == 61310) {
                if (selector == 6134) {
                    selector = 61341;
                } else if (selector == 61341) {
                    selector = 61342;
                } else if (selector == 6138) {
                    selector = 61381;
                } else if (selector == 61381) {
                    selector = 61382;
                }
                return 1;
            } else if (selector == 61342 || selector == 61382) {
                if (selector == 61382)
                    selector = 61383;
                return 2;
            } else if (selector == 6139) {
                setSelector(61310);
                return 0;
            } else if (selector == 632 || selector == 633 || selector == 634 || selector == 635) {
                return 0;
            } else if (selector == 9111) {
                setSelector(91112);
                return 0;
            } else if (selector == 91112) {
                setSelector(91113);
                return 0;
            } else if (selector == 91113) {
                setSelector(91114);
                return 1;
            } else if (selector == 91114 || selector == 91214 || selector == 911263 || selector == 911322
                    || selector == 912322) {
                return -1;
            } else if (selector == 9121) {
                setSelector(91212);
                return 0;
            } else if (selector == 91213) {
                setSelector(91214);
                return 1;
            } else if (selector == 91126) {
                setSelector(911262);
                return 0;
            } else if (selector == 911262) {
                setSelector(911263);
                return 0;
            } else if (selector == 91226) {
                setSelector(912262);
                return 0;
            } else if (selector == 912262) {
                setSelector(912263);
                return 0;
            } else if (selector == 91132) {
                setSelector(911321);
                return 0;
            } else if (selector == 911321) {
                setSelector(911322);
                return 0;
            } else if (selector == 91232) {
                setSelector(912321);
                return 0;
            } else if (selector == 912321) {
                setSelector(912322);
                return 0;
            } else if (selector == 9511) //victory points
            {
                setSelector(9512);
                return 1;
            } else if (selector == 9512) //Loss points
            {
                setSelector(9513);
                return -1;
            } else if (selector == 9513)  //Tie points
            {
                setSelector(9511);
                return 0;
            } else if (selector == 9514) {
                setSelector(9511);
                return 1;
            } else if (selector == 9521)  //0,0,1
            {
                setSelector(9513);
                return 0;
            } else if (selector == 95211) {
                selector = 95212;
                return 0;
            } else if (selector == 95212) {
                return 1;
            } else if (selector == 1031)  //0,0,0,1 - success
            {
                setSelector(9521);
                return 0;
            } else if (selector == 1032)  //0,1,0,1
            {
                setSelector(1033);
                return 0;
            } else if (selector == 1033)  //1,0,1
            {
                setSelector(1034);
                return 1;
            } else if (selector == 1034)  //0,1
            {
                setSelector(1035);
                return 0;
            } else if (selector == 1035)  //1
            {
                //setSelector(1035);
                return 1;
            } else if (selector == 1036)  //0,2,1,0,1
            {
                setSelector(1037);
                return 0;
            } else if (selector == 1037)  //2,1,0,1
            {
                setSelector(1033);
                return 2;
            } else if (selector == 1038)  //0,3,0,1
            {
                setSelector(1039);
                return 0;
            } else if (selector == 1039)  //3,0,1
            {
                setSelector(1034);
                return 3;
            } else if (selector == 10310)  //0,4,0,1
            {
                setSelector(10311);
                return 0;
            } else if (selector == 10311)  //4,0,1
            {
                setSelector(1034);
                return 4;
            } else if (selector == 10312)  //0,5,0,1,0,1
            {
                setSelector(10313);
                return 0;
            } else if (selector == 10313)  //5,0,1,0,1
            {
                setSelector(1032);
                return 5;
            } else if (selector == 10314)  //0,6,0,1
            {
                setSelector(10315);
                return 0;
            } else if (selector == 10315)  //6,0,1
            {
                setSelector(1034);
                return 6;
            } else {
                //random number to crash test that were not checked
                return 123812;
            }
        }
    }

    public static boolean receiveChoice(String message) {
        String choice = "";
        if (!isTest) {
            do {
                choice = UIController.receiveString(message);
            } while (!(choice.equals("y") || choice.equals("n")));

            if (choice.equals("y")) {
                return true;
            }
            return false;
        }

        if (selector == 6611 || selector == 6621 || selector == 6623 || selector == 4 || selector == 66151 || selector == 66251) {
            return true;
        } else {
            return false;
        }
    }


    public static String getUsernameFromUser(String msg) {

        String username = UIController.receiveString("Enter new " + msg + " Username:");
        return username;

    }

    public static Date receiveDate(String s) {
        if (!isTest) {
            //TODO: fill
        } else {
            if (selector == 0) {
                return new Date(2020, 01, 01);
            }
        }
        return null;
    }

    public static void showPopupWindow(WrappedSession session, String alert) {

        //todo: GUI - maybe need to be observer for mvvm model
    }
}
