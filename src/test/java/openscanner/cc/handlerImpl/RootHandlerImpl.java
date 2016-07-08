package openscanner.cc.handlerImpl;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import openscanner.cc.handler.RootHandler;

public class RootHandlerImpl implements RootHandler {

    @Override
    public void handle(RoutingContext ctx) {
        Session session = ctx.session();
        session.put("test","test");
        System.out.println(session.lastAccessed());
        System.out.println(session.isDestroyed());
        System.out.println(session.timeout());
        ctx.vertx().setTimer(5000, handle->{

        });
        ctx.next();
    }

}