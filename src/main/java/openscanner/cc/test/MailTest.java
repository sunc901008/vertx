package openscanner.cc.test;

import io.vertx.core.Vertx;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.StartTLSOptions;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created : sunc
 * Date : 16-8-9
 * Description :
 */
public class MailTest {

    private static final String hostname = "smtp.exmail.qq.com";
    private static final int port = 587;
    private static final String username = "service@appinner.com";
    private static final String password = "Sunc1123.";

    public static void main(String[] args) throws UnsupportedEncodingException, MessagingException {

        sendEmail1("nihao", "test", "sunc@openscanner.cn");
//        sendMail2();
    }


    public static void sendEmail1(String body, String subject, String recipient) throws UnsupportedEncodingException, javax.mail.MessagingException {
        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.from", username);
        mailProps.put("mail.smtp.host", hostname);
        mailProps.put("mail.smtp.port", port);
        mailProps.put("mail.smtp.auth", true);
        mailProps.put("mail.smtp.socketFactory.port", port);
        mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProps.put("mail.smtp.socketFactory.fallback", "false");
        mailProps.put("mail.smtp.starttls.enable", "true");
        mailProps.put("mail.smtp.EnableSSL.enable", "true");
//        smtp.exmail.qq.com ，使用SSL，端口号465
        Session mailSession = Session.getDefaultInstance(mailProps, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }

        });

        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(username));
        String[] emails = {recipient};
        InternetAddress dests[] = new InternetAddress[emails.length];
        for (int i = 0; i < emails.length; i++) {
            dests[i] = new InternetAddress(emails[i].trim().toLowerCase());
        }
        message.setRecipients(Message.RecipientType.TO, dests);
        message.setSubject(subject, "UTF-8");
        Multipart mp = new MimeMultipart();
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setContent(body, "text/html;charset=utf-8");
        mp.addBodyPart(mbp);
        message.setContent(mp);
        message.setSentDate(new java.util.Date());

        Transport.send(message);
    }

    public static void sendMail2() {
        Vertx vertx = Vertx.vertx();
        MailConfig config = new MailConfig();
        config.setHostname(hostname);
        config.setPort(port);
        config.setStarttls(StartTLSOptions.REQUIRED);
        config.setUsername(username);
        config.setPassword(password);
//        config.setAllowRcptErrors(true);
        MailClient mailClient = MailClient.createNonShared(vertx, config);

        MailMessage message = new MailMessage();
        message.setFrom(username);
        message.setTo("sunc@openscanner.cn");
        message.setText("this is the plain message text");

        mailClient.sendMail(message, res -> {
            if (res.succeeded()) {
                System.out.println("ok");
            } else {
                res.cause().printStackTrace();
                vertx.close();
            }
        });
    }

}
