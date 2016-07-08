package openscanner.cc.handler;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;
import io.vertx.ext.web.sstore.SessionStore;
import openscanner.cc.handlerImpl.LocalSessionStoreHandlerImpl;

@VertxGen
public interface LocalSessionStoreHandler extends SessionStore {

    /**
     * Default of how often, in ms, to check for expired sessions
     */
    long DEFAULT_REAPER_INTERVAL = 1000;

    /**
     * Default name for map used to store sessions
     */
    String DEFAULT_SESSION_MAP_NAME = "vertx-web.sessions";

    long DEFAULT_TICKER = 0;

    /**
     * Create a session store
     *
     * @param vertx  the Vert.x instance
     * @return the session store
     */
    static LocalSessionStoreHandler create(Vertx vertx) {
        return new LocalSessionStoreHandlerImpl(vertx, DEFAULT_SESSION_MAP_NAME, DEFAULT_REAPER_INTERVAL, DEFAULT_TICKER);
    }
    /**
     * Create a session store
     *
     * @param vertx  the Vert.x instance
     * @param sessionMapName  name for map used to store sessions
     * @return the session store
     */
    static LocalSessionStoreHandler create(Vertx vertx, String sessionMapName) {
        return new LocalSessionStoreHandlerImpl(vertx, sessionMapName, DEFAULT_REAPER_INTERVAL, DEFAULT_TICKER);
    }
    /**
     * Create a session store
     *
     * @param vertx  the Vert.x instance
     * @param sessionMapName  name for map used to store sessions
     * @param reaperInterval  how often, in ms, to check for expired sessions
     * @return the session store
     */
    static LocalSessionStoreHandler create(Vertx vertx, String sessionMapName, long reaperInterval) {
        return new LocalSessionStoreHandlerImpl(vertx, sessionMapName, reaperInterval, DEFAULT_TICKER);
    }

    @Fluent
    LocalSessionStoreHandler setSessionTicker(long ticker);

}
