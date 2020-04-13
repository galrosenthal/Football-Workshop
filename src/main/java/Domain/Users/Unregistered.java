package Domain.Users;

import DB.DBManager;
import Domain.EntityManager;
import Service.UIController;

import java.util.ArrayList;
import java.util.List;

public class Unregistered {
    SystemUser systemUser = null;



    public SystemUser login(String usrNm, String pswrd) throws Exception {
        EntityManager entManager = EntityManager.getInstance();
        SystemUser userWithUsrNm = entManager.getUser(usrNm);
        if(userWithUsrNm == null) //User name does not exists.
            throw new Exception("Username or Password was incorrect!!!!!");

        //User name exists, checking password.
        List<String> userDetails = DBManager.getInstance().getSystemUsers().getRecord(new String[]{"username"}, new String[]{usrNm});
        if (userDetails.get(1).equals(pswrd)) {
            UIController.printMessage("Successful login. Welcome back, "+ usrNm);
            this.systemUser = userWithUsrNm;
            return userWithUsrNm;
        }
        throw new Exception("Username or Password was incorrect!!!!!");

    }

    public SystemUser signUp(String name, String usrNm, String pswrd) throws Exception {
        //TODO: Check if password is according to security requirements (what are them?)

        //Checking if user name is already exists
        EntityManager entManager = EntityManager.getInstance();
        if(entManager.getUser(usrNm) != null){
            throw new Exception("Username already exists");
        }

        SystemUser newUser = new SystemUser(usrNm, name);
        Fan newFan = new Fan(newUser);
        entManager.addUser(newUser);

        //Adding user to system-users table
   /*     ArrayList<String> newRecord = new ArrayList<>();
        newRecord.add(usrNm);
        newRecord.add(pswrd);
        newRecord.add(name);
        newRecord.add("fan");
        dbManager.getSystemUsers().addRecord(newRecord);
        dbManager.close();*/

        this.systemUser = newUser;
        UIController.printMessage("Successful sign up. Welcome, "+ usrNm);
        return newUser;

    }
}
