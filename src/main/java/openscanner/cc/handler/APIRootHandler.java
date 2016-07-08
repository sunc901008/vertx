package openscanner.cc.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import openscanner.cc.handlerImpl.APIRootHandlerImpl;

public interface APIRootHandler extends Handler<RoutingContext> {
    static APIRootHandler create() {
        return new APIRootHandlerImpl();
      }
}