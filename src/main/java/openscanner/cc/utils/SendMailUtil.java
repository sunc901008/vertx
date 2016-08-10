package openscanner.cc.utils;

import java.security.Security;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMailUtil {

    public static void main(String[] args) throws Exception {
        sendMail("I am the error message for testing!");
    }

    public static void sendMail(String log) {

        String host = "smtp.qq.com";
        String port = "465";
        String protocol = "smtp";
        String username = "service@appinner.com";
        String password = "Sunc1123.";

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.transport.protocol", protocol);
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.port", port);
        props.put("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.auth", "true");
        //There are 5 steps for send email via JavaMail
        try {
            //1. Create session
            Session session = Session.getInstance(props);
            //Open the Session debug model
            session.setDebug(true);
            //2. Get transport via session
            Transport ts = session.getTransport();
            //3. Connect the email server via user name and password
            ts.connect(host, username, password);
            //4. Create email
            Message message = createSimpleMail(session, log);
            //5. Send email
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static MimeMessage createSimpleMail(Session session, String log) throws Exception {
        //Create email object
        MimeMessage message = new MimeMessage(session);
        //Set sender
        message.setFrom(new InternetAddress("service@appinner.com"));
        //Set receiver(s) (TO)
        String to1 = "sunc@openscanner.cn";
        InternetAddress[] toTotal = new InternetAddress[]{new InternetAddress(to1)};
        //Invoke the setRecipinents when multiple recipients, otherwise invoke setRecipinent
        message.setRecipients(Message.RecipientType.TO, toTotal);

        //Set receiver(s) (CC)
//        String cc = "IAmCCReceiver@qq.com";
//        message.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
        //Set email subject
        message.setSubject("I AM The Subject");
        //Set email content
        MimeBodyPart mp = new MimeBodyPart();
        StringBuffer content = new StringBuffer();
        content.append("ContentHeader");
        // Error log
        content.append(log);
        content.append("ContentFooter");
        mp.setContent(content.toString(), "text/html;charset=UTF-8");

        MimeMultipart mmp = new MimeMultipart();
        mmp.addBodyPart(mp);
        message.setContent(mmp);
        //Return email object
        return message;
    }
}