package openscanner.cc.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import openscanner.cc.handlerImpl.RootHandlerImpl;

/**
 * Created : sunc
 * Date : 16-6-17
 * Description :
 */
public interface RootHandler extends Handler<RoutingContext> {

    static RootHandler create(){
        return new RootHandlerImpl();
    }
}
