package openscanner.cc.stripe;

import io.vertx.ext.web.RoutingContext;

/**
 * Created : sunc
 * Date : 16-7-12
 * Description :
 */
public class StripeOrders {

    public Integer amount;
    public String name;
    public String description;

    public StripeOrders(RoutingContext ctx) {
        this.amount = ctx.get("amount");
        this.name = ctx.get("name");
        this.description = ctx.get("description");
    }

    public Integer getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
