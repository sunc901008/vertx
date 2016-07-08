package openscanner.cc.handlerImpl;

import io.vertx.ext.web.RoutingContext;
import openscanner.cc.handler.APIRootHandler;

public class APIRootHandlerImpl implements APIRootHandler {

    @Override
    public void handle(RoutingContext ctx) {

        // 判断请求来源
        String remote_address = ctx.request().remoteAddress().host();
        ctx.put("remote_address", remote_address);
        ctx.next();

    }


}