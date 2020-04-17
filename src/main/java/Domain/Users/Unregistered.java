package Domain.Users;

import DB.DBManager;
import Domain.EntityManager;
import Service.UIController;

import java.util.ArrayList;
import java.util.List;

public class Unregistered {
    private SystemUser systemUser = null;


    /**
     * Receives user name and password from the unregistered user who wants to log in to the system,
     * performs validation and returns the relevant user.
     * @param usrNm User name
     * @param pswrd Password
     * @return The user in the system with those credentials.
     * @throws Exception If user name or password are incorrect.
     */
    public SystemUser login(String usrNm, String pswrd) throws Exception {
        EntityManager entManager = EntityManager.getInstance();
        SystemUser userWithUsrNm = entManager.getUser(usrNm);
        if(userWithUsrNm == null) //User name does not exists.
            throw new Exception("Username or Password was incorrect!");

        //User name exists, checking password.
        //List<String> userDetails = DBManager.getInstance().getSystemUsers().getRecord(new String[]{"username"}, new String[]{usrNm});
        if (userWithUsrNm.getPassword().equals(pswrd)) {
            this.systemUser = userWithUsrNm;
            return userWithUsrNm;
        }
        throw new Exception("Username or Password was incorrect!");
    }

    /**
     * Receives name, user name and password from the unregistered user who wants to sign up to the system,
     * performs validation - checks whether the user name is not already belongs to a user in the system,
     * and whether the given password meets the following security requirements:
     * At least 8 characters.
     * At least 1 number.
     * At least 1 upper case letter.
     * At least 1 lower case letter.
     * Must not contain any spaces.
     * Adds new user with the role fan to the system, and returns the relevant user.
     * @param name Name.
     * @param usrNm User name.
     * @param pswrd Password.
     * @return New user with those credentials.
     * @throws Exception If user name is already belongs to a user in the system, or
     * the password does not meet the security requirements.
     */
    public SystemUser signUp(String name, String usrNm, String pswrd) throws Exception {
        //Checking if user name is already exists
        EntityManager entManager = EntityManager.getInstance();
        if(entManager.getUser(usrNm) != null){
            throw new Exception("Username already exists");
        }

        //Checking if the password meets the security requirements
        // at least 8 characters
        // at least 1 number
        // at least 1 upper case letter
        // at least 1 lower case letter
        // must not contain any spaces
        String pswrdRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        if(!pswrd.matches(pswrdRegEx)){
            throw new Exception("Password does not meet the requirements");
        }

        SystemUser newUser = new SystemUser(usrNm, pswrd, name);
        newUser.addNewRole(new Fan(newUser)); //add the role "fan" to the new user
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

    /**
     * Returns the System User that the unregistered user has become to - by signing up or logging in.
     * @return the SystemUser that the unregistered user has become to.
     */
    public SystemUser getSystemUser() {
        return systemUser;
    }


}
