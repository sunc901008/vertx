package openscanner.cc.handlerImpl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.sstore.impl.SessionImpl;
import openscanner.cc.handler.LocalSessionStoreHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created : sunc
 * Date : 16-6-17
 * Description :
 */
public class LocalSessionStoreHandlerImpl implements LocalSessionStoreHandler, Handler<Long> {

    protected final Vertx vertx;
    protected final LocalMap<String, Session> localMap;
    private final long reaperInterval;
    private long ticker;
    private long timerID = -1;
    private long timer = -1;
    private boolean closed;

    public LocalSessionStoreHandlerImpl(Vertx vertx, String sessionMapName, long reaperInterval, long ticker) {
        this.vertx = vertx;
        this.reaperInterval = reaperInterval;
        this.ticker = ticker;
        localMap = vertx.sharedData().getLocalMap(sessionMapName);
        setTimer();
        setTicker();
    }

    @Override
    public Session createSession(long timeout) {
        return new SessionImpl(timeout);
    }

    @Override
    public long retryTimeout() {
        return 0;
    }

    @Override
    public void get(String id, Handler<AsyncResult<Session>> resultHandler) {
        resultHandler.handle(Future.succeededFuture(localMap.get(id)));
    }

    @Override
    public void delete(String id, Handler<AsyncResult<Boolean>> resultHandler) {
        resultHandler.handle(Future.succeededFuture(localMap.remove(id) != null));
    }

    @Override
    public void put(Session session, Handler<AsyncResult<Boolean>> resultHandler) {
        localMap.put(session.id(), session);
        resultHandler.handle(Future.succeededFuture(true));
    }

    @Override
    public void clear(Handler<AsyncResult<Boolean>> resultHandler) {
        localMap.clear();
        resultHandler.handle(Future.succeededFuture(true));
    }

    @Override
    public void size(Handler<AsyncResult<Integer>> resultHandler) {
        resultHandler.handle(Future.succeededFuture(localMap.size()));
    }

    @Override
    public synchronized void close() {
        localMap.close();
        if (timerID != -1) {
            vertx.cancelTimer(timerID);
            if(ticker != 0) vertx.cancelTimer(timer);
        }
        closed = true;
    }

    @Override
    public synchronized void handle(Long tid) {
        long now = System.currentTimeMillis();
        Set<String> toRemove = new HashSet<>();
        for (Session session : localMap.values()) {
            if (now - session.lastAccessed() > session.timeout()) {
                toRemove.add(session.id());
            }
        }
        for (String id : toRemove) {
            localMap.remove(id);
        }
        if (!closed) {
            setTimer();
        }
    }

    private void setTimer() {
        if (reaperInterval != 0) {
            timerID = vertx.setTimer(reaperInterval, this);
        }
    }

    private void setTicker() {
        if(ticker != 0)
            timer = vertx.setTimer(ticker, handler -> {
                for (Session session : localMap.values()) {
                    String id = session.id();
                    System.out.println(id);
                }
            });
    }

    @Override
    public LocalSessionStoreHandler setSessionTicker(long ticker) {
        this.ticker = ticker;
        return this;
    }
}