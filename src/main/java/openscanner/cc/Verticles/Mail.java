package openscanner.cc.Verticles;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.mail.MailMessage;
import io.vertx.ext.mail.StartTLSOptions;

/**
 * @author sunc
 * @date 2016年3月10日
 * @description : 邮件发送
 */
public class Mail {

    private static final Logger logger = LoggerFactory.getLogger(Mail.class);

    public static void handler(String sendTo, Handler<Boolean> handler) {
        Vertx vertx = Vertx.vertx();
        String username = "service@openscanner.cn";
        MailConfig config = new MailConfig();
        config.setHostname("smtp.exmail.qq.com");
        config.setPort(587);
        config.setStarttls(StartTLSOptions.REQUIRED);
        config.setUsername(username);
        config.setPassword("123Liu.");
        config.setAllowRcptErrors(true);
        MailClient mailClient = MailClient.createNonShared(vertx, config);

        MailMessage mailMessage = sendEmail(username, sendTo);
        mailClient.sendMail(mailMessage, res -> {
            if (res.succeeded()) {
                logger.info("Send email success! sendTo : {0}", sendTo);
                handler.handle(true);
            } else {
                System.out.println("send fail");
                res.cause().printStackTrace();
                logger.error("Send email Failed! sendTo : {0} . Reason : {1}", sendTo, res.cause().getMessage());
                handler.handle(false);
            }
        });
    }

    private static MailMessage sendEmail(String from, String to) {
        MailMessage message = new MailMessage();
        message.setFrom(from);
        message.setTo(to);
        String subject = "activate account";
        String test = "abcd";
        message.setSubject(subject);
        message.setText(test);
        return message;
    }


}
