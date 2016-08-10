package openscanner.cc.Verticles;

/**
 * Created : sunc
 * Date : 16-7-7
 * Description :
 */
@FunctionalInterface
public interface HandlerUC<E, Object> {

    void handle(E bool, Object object);
}