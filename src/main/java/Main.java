import DB.*;
import Domain.EntityManager;
import Domain.Users.SystemUser;
import Domain.Users.Unregistered;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("System is Loading...");
        EntityManager.getInstance().initSystem();
    }

    private static void initSystem()
    {

        System.out.println("Please Insert System Admin credentials.");


        int retries = 3;
        while(retries > 0)
        {
            if(authenticateUser())
            {
                break;
            }
            retries--;
            System.out.println("Please Try Again");
        }
        if (retries == 0)
        {
            // Exit with Error code 100
            System.out.println("Wrong Username or password please try again.");
            System.exit(100);
        }

        System.out.println("System Booted Successfully");

    }

    private static boolean authenticateUser()
    {
        int getUserIndex = -1;
        Scanner input = new Scanner(System.in);
        DBManager dbManager = DBManager.getInstance();
        Table users = dbManager.getSystemUsers();
        System.out.print("Username: ");
        String usrnm = input.nextLine();
        System.out.print("Password: ");
        String pswd = input.nextLine();


        for (int i = 0; i < users.size(); i++) {
            if (users.getRecord(i).contains(usrnm)) {
                getUserIndex = i;
            }
        }
        if(getUserIndex != -1)
        {
            if(approvePassword(pswd,users,getUserIndex) )
            {
                if(!isSystemAdmin(users,getUserIndex))
                {
                    System.out.println("The User " + users.getRecordValue(getUserIndex, Table.USERNAME) + " is not a System Admin, therefor cannot boot the System.");
                    return false;
                }
                return true;
            }
        }

        return false;
    }

    //TODO: Table.ROLE returns the entire role segment with ; and stuff. Fix It!!!!
    private static boolean isSystemAdmin(Table users, int getUserIndex) {
        String role = users.getRecordValue(getUserIndex, Table.ROLE);
        return "System Admin".equals(role);

    }

    private static boolean approvePassword(String pswd, Table users,int getUserIndex)
    {
        String dbPass = users.getRecordValue(getUserIndex, Table.PASSWORD);
        return dbPass.equals(pswd);
    }

}
