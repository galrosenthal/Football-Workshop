package Domain;

import Domain.Users.SystemUser;
import Service.AllSubscribers;
import Service.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Alert implements Subject, Observer {
    private static Alert alertInstance = null;
    private final String emailAddress = "notifyfootballworkshop@gmail.com";
    private final String host =  "smtp.gmail.com";

    private Alert()
    {

    }

    /**
     * Returns an instance of Alert. part of the Singleton design
     *
     * @return - Alert - an instance of Alert
     */
    public static Alert getInstance() {
        if (alertInstance == null) {
            alertInstance = new Alert();
        }

        return alertInstance;
    }

    @Override
    public void notifyObserver(List<SystemUser> systemUsers, String alert) {
        AllSubscribers allSubscribers = AllSubscribers.getInstance();
        List<String> onlineSystemUsers = allSubscribers.getSystemUsers();
        List<SystemUser> updateSystemUsers = new ArrayList<>();
        for (int i = 0; i < systemUsers.size(); i++) {
            SystemUser systemUser = systemUsers.get(i);
            /*In case system user wants email alert*/
            if (systemUser.isAlertEmail()) {
                this.sendEmail(systemUser.getEmail(), this.emailAddress, "Football-Workshop Notification", alert);
            }
            /*otherwise - In case system user online*/
            else if (onlineSystemUsers.contains(systemUser.getUsername())) {
                updateSystemUsers.add(systemUser);
            } else {
                //todo: save alert on db
                    EntityManager.getInstance().saveAlert(systemUser.getUsername(),alert);

            }
        }
        allSubscribers.update(updateSystemUsers, alert);
    }

    /**
     * @param to   - Recipient's email ID needs to be mentioned.
     * @param from - Sender's email ID needs to be mentioned
     * @return true - send Email successfully
     *         false - otherwise
     */
    public boolean sendEmail(String to, String from, String subject, String content) {

        // Add sender detail's
        final String username = this.emailAddress;//your Gmail username
        final String password = "footBALL!!12";//your Gmail password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", "587");

        // Get the Session object
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject
            message.setSubject(subject);

            // Put the content of your message
            message.setText(content);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void update(List<SystemUser> systemUsers, String alert) {
        notifyObserver(systemUsers, alert);
    }
}
