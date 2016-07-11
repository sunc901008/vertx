package openscanner.cc.stripe;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created : sunc
 * Date : 16-7-8
 * Description :
 */
public class Html {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.route("/").handler(ctx -> ctx.response().sendFile("templates/pay.html"));



        router.route("/webhook").handler(ctx -> {
            MultiMap mapP = ctx.request().params();
            for(String key : mapP.names())
                System.out.println(key + ":" + mapP.get(key));

            MultiMap maps = ctx.request().headers();
            for(String key : maps.names())
                System.out.println(key + ":" + maps.get(key));

            ctx.response().end("hehe");
        });



        router.route("/self").handler(ctx -> ctx.response().sendFile("templates/paySelf.html"));

        router.route("/pay").handler(ctx -> {
            ctx.response().setChunked(true);
            ctx.response().putHeader("Content-type","text/html");

            // Set your secret key: remember to change this to your live secret key in production
// See your keys here https://dashboard.stripe.com/account/apikeys
            Stripe.apiKey = "sk_test_kAFmLlc0L9fymGNvVDi3L1Et";

// Get the credit card details submitted by the form
            String token = ctx.request().getParam("stripeToken");

            try {
                Map<String, Object> customerParams = new HashMap<>();
                customerParams.put("source", token);
                customerParams.put("description", "Example charge");

                Customer customer = Customer.create(customerParams);

                Map<String, Object> chargeParams = new HashMap<>();
                chargeParams.put("amount", 2000); // amount in cents, again
                chargeParams.put("currency", "usd");
                chargeParams.put("customer", customer.getId());

                Charge charge = Charge.create(chargeParams);

                ctx.response().write(customer.getId());

                ctx.response().end("<br/><a href='/'>back</a>");

            } catch (CardException | APIException | InvalidRequestException | AuthenticationException | APIConnectionException e) {
                e.printStackTrace();
            }



        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8000, res -> {
            if (res.succeeded())
                System.out.println("start");
            else
                res.cause().printStackTrace();
        });

    }
}
