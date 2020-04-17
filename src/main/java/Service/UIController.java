package Service;

import java.util.Scanner;

public class UIController {

    private static boolean isTest = false;
    private static int selector = 0; //latest = 8

    public static void setSelector(int selector) {
        UIController.selector = selector;
    }

    public static void setIsTest(boolean isTest) {
        UIController.isTest = isTest;
    }

    public static void printMessage(String msg) {
        //  if (!isTest) {
        System.out.println(msg);
        //  }
    }

    public static String receiveString() {
        if (!isTest) {
            Scanner sc = new Scanner(System.in);

            String line = sc.nextLine();

            return line;
        } else {
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
            } else if (selector == 7) {
                return "01/11/1199";
            } else if (selector == 8) {
                return "CoachJob";
            } else if (selector == 9) {
                return "StadiumName";
            }
            else if (selector == 6136 ||selector == 6137) {
                return "Test ";
            }else
                return null;
        }
    }

    public static int receiveInt() {
        if (!isTest) {
            Scanner sc = new Scanner(System.in);
            int integer = sc.nextInt();

            return integer;
        } else {
            if (selector == 0 || selector == 1 || selector == 2 || selector == 7 || selector == 8 ) {
                return 0;
            }
            else if (selector == 6132 || selector == 6133 ||selector == 6134 || selector == 61341 || selector == 6135 || selector == 6136 || selector == 6137){
                if(selector == 6134)
                {
                    selector = 61341;
                }
                else if(selector == 61341)
                {
                    selector = 61342;
                }
                return 1;
            }
            else if (selector == 61342){
                return 2;
            }
            else {
                return 0;
            }
        }
    }

    public static boolean receiveChoice(String message) {
        UIController.printMessage(message);
        String choice = "";
        if (!isTest) {
            do {
                choice = UIController.receiveString();
            } while (!(choice.equals("y") || choice.equals("n")));

            if (choice.equals("y")) {
                return true;
            }
            return false;
        }

        if (selector == 4) {
            return true;
        } else {
            return false;
        }
    }
}
