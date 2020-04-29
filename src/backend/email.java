/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;

public class email {
    private static String SMTP_SERVER;
    private static String USERNAME;
    private static String PASSWORD;

    private static String EMAIL_FROM;
    private static String EMAIL_TO;
    private static String EMAIL_TO_CC;

    private static String EMAIL_SUBJECT;
    private static String EMAIL_TEXT;
    
    public email(String SMTP_SERVER, String USERNAME, String PASSWORD, String EMAIL_FROM, 
            String EMAIL_TO, String EMAIL_TO_CC, String EMAIL_SUBJECT, String EMAIL_TEXT) {        
        this.SMTP_SERVER = SMTP_SERVER;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.EMAIL_FROM = EMAIL_FROM;
        this.EMAIL_TO = EMAIL_TO;
        this.EMAIL_TO_CC = EMAIL_TO_CC;
        this.EMAIL_SUBJECT = EMAIL_SUBJECT;
        this.EMAIL_TEXT = EMAIL_TEXT;
    }
    
    public static void send(email email) {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", email.SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587"); // default port 25

        Session session = Session.getDefaultInstance(prop); 
        SmtpAuthenticator authentication = new SmtpAuthenticator();
        javax.mail.Message msg = new MimeMessage(Session.getInstance(prop, authentication));
        try {
            msg.setFrom(new InternetAddress(email.EMAIL_FROM));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.EMAIL_TO, false));
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(email.EMAIL_TO_CC, false));
            msg.setSubject(email.EMAIL_SUBJECT);
            msg.setText(email.EMAIL_TEXT);
            msg.setSentDate(new Date());
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            t.setStartTLS(true);
            t.connect(email.SMTP_SERVER, email.USERNAME, email.PASSWORD);
            try {
                t.sendMessage(msg, msg.getAllRecipients());
            } catch (Exception ex){
                System.err.println("no valid email");
            }
            System.out.println("Response: " + t.getLastServerResponse());
            System.out.println("Email sent");
            t.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        email email1 = new email(   "smtp.gmail.com",
                                    "coapWarning@gmail.com",
                                    "mVi1969gg",
                                    "coapWarning@gmail.com",
                                    "tobiaszwinger.tz@gmail.com",
                                    "",
                                    "Warning from CoapProject",
                                    "Too high value"
                                    );
        send(email1);
        
        
    }
}