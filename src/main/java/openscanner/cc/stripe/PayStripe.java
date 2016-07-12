package openscanner.cc.stripe;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.FaviconHandler;
import io.vertx.ext.web.handler.StaticHandler;
import openscanner.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created : sunc
 * Date : 16-7-8
 * Description :
 */
public class PayStripe extends AbstractVerticle {

    @Override
    public void start() {

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().handler(FaviconHandler.create("webroot/favicon.ico"));
        router.route("/static/*").handler(StaticHandler.create());

        router.route("/").handler(ctx -> ctx.response().sendFile("templates/order.hbs"));

        router.route("/order").handler(ctx -> {
            String goods = ctx.request().getParam("goods");
            if ("".equals(goods)) {
                ctx.response().end("error");
                return;
            }
            OrderMap order = new OrderMap();
            int amount = order.getAmount(goods);
            String name = goods;
            String description = "PAY FOR " + goods;

            ctx.put("amount", amount);
            ctx.put("name", name);
            ctx.put("description", description);

            HandlebarsTemplateEngine.create().render(ctx, "templates/pay", res -> {
                if (res.succeeded())
                    ctx.response().end(res.result());
                else
                    ctx.response().end(res.cause().getMessage());
            });

        });

//        router.route("/self").handler(ctx -> ctx.response().sendFile("templates/paySelf.html"));

        router.route("/pay").handler(ctx -> {
            ctx.response().setChunked(true);
            ctx.response().putHeader("Content-type", "text/html");

            Stripe.apiKey = "sk_test_kAFmLlc0L9fymGNvVDi3L1Et";
            String token = ctx.request().getParam("stripeToken");

            MultiMap map = ctx.request().params();
            for (String key : map.names())
                ctx.response().write(key + ":" + map.get(key) + "<br />");

            ctx.response().write("<br /><br /><br /><br />");

            try {
                Map<String, Object> customerParams = new HashMap<>();
                customerParams.put("source", token);
                customerParams.put("description", "");

                Customer customer = Customer.create(customerParams);

                ctx.response().write("getCurrency:" + customer.getCurrency() + "<br />");
                ctx.response().write("getDescription:" + customer.getDescription() + "<br />");
                ctx.response().write("getId:" + customer.getId() + "<br />");
                ctx.response().write("getDefaultSource:" + customer.getDefaultSource() + "<br />");
                ctx.response().write("getSources:" + customer.getSources() + "<br />");
                ctx.response().write("getSubscriptions:" + customer.getSubscriptions() + "<br />");


                Map<String, Object> chargeParams = new HashMap<>();
                chargeParams.put("customer", customer.getId());

                chargeParams.put("amount", 500); // amount in cents, again
                chargeParams.put("currency", "usd");

                Charge charge = Charge.create(chargeParams);
                ctx.response().write("token:<br />" + token + "<br />");
                ctx.response().write("charge:<br />");
                ctx.response().write("getAmount:" + charge.getAmount() + "<br/>");
                ctx.response().write("getCustomer:" + charge.getCustomer() + "<br/>");
                ctx.response().write("getApplicationFee:" + charge.getApplicationFee() + "<br/>");
                ctx.response().write("getOrder:" + charge.getOrder() + "<br/>");
                ctx.response().write("getSource:" + charge.getSource() + "<br/>");
                ctx.response().write("getShipping:" + charge.getShipping() + "<br/>");
                ctx.response().write("getPaid:" + charge.getPaid() + "<br/>");

                ctx.response().write("<br/>");
                ctx.response().end("<a href='/'>back</a>");

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

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(PayStripe.class.getName());
    }
}
