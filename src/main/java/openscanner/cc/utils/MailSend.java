package openscanner.cc.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * Created : sunc
 * Date : 16-7-1
 * Description :
 */
public class MailSend {

    static int port = 587;

    static String server = "smtp.exmail.qq.com";//邮件服务器mail.cpip.net.cn

    static String from = "service";//发送者,显示的发件人名字

    static String user = "service@appinner.com";//发送者邮箱地址

    static String password = "Sunc1123.";//密码

    public static void sendEmail(String email, String subject, String body) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.port", String.valueOf(port));
            props.put("mail.smtp.auth", "true");
            Transport transport = null;
            Session session = Session.getDefaultInstance(props, null);
            transport = session.getTransport("smtp");
            transport.connect(server, user, password);
            MimeMessage msg = new MimeMessage(session);
            msg.setSentDate(new Date());
            InternetAddress fromAddress = null;
            fromAddress = new InternetAddress(user, from, "UTF-8");
            msg.setFrom(fromAddress);
            InternetAddress[] toAddress = new InternetAddress[1];
            toAddress[0] = new InternetAddress(email);
            msg.setRecipients(Message.RecipientType.TO, toAddress);
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.saveChanges();
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
