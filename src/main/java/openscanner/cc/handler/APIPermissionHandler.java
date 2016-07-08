package openscanner.cc.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import openscanner.cc.handlerImpl.APIPermissionHandlerImpl;

public interface APIPermissionHandler extends Handler<RoutingContext>{

    static APIPermissionHandler create(){
        return new APIPermissionHandlerImpl();
    }
}
