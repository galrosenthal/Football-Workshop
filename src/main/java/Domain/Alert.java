package Domain;

import Domain.Users.AssociationRepresentative;
import Domain.Users.SystemAdmin;
import Domain.Users.SystemUser;
import Service.AllSubscribers;
import Service.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Alert implements Subject, Observer{
    private static Alert alertInstance = null;

    /**
     * Returns an instance of dbManager. part of the Singleton design
     *
     * @return - DBManager - an instance of dbManager
     */
    public static Alert getInstance() {
        if (alertInstance == null) {
            alertInstance = new Alert();
            SystemUser a = new SystemUser("Administrator","Aa123456","admin","test@gmail.com", false);
            a.addNewRole(new SystemAdmin(a));
            a.addNewRole(new AssociationRepresentative(a));
        }

        return alertInstance;
    }

    @Override
    public void notifyObserver(List<SystemUser> systemUsers, String alert) {
        AllSubscribers allSubscribers = AllSubscribers.getInstance();
        List<String> onlineSystemUsers = allSubscribers.getSystemUsers();
        List<SystemUser> updateSystemUsers = new ArrayList<>();
        for (int i = 0; i < systemUsers.size() ; i++) {
            /*In case system user online*/
            if(onlineSystemUsers.contains(systemUsers.get(i).getUsername()))
            {
                updateSystemUsers.add(systemUsers.get(i));
            }
            //todo: save alert on db
        }
        allSubscribers.update(updateSystemUsers, alert);
    }

    /**
     *
     * @param to - Recipient's email ID needs to be mentioned.
     * @param from - Sender's email ID needs to be mentioned
     * @param host - Assuming you are sending email from localhost
     * @return
     */
    private boolean sendEmail(String to, String from, String host)
    {
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return true;
    }

    @Override
    public void update(List<SystemUser> systemUsers, String alert) {
        notifyObserver(systemUsers,alert);
    }
}
