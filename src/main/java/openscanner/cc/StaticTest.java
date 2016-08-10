package openscanner.cc;

import openscanner.cc.Verticles.Property;

import java.util.Properties;

/**
 * Created : sunc
 * Date : 16-7-18
 * Description :
 */
public class StaticTest {

    public static Properties p;

    static {
        p = new Property().getProperties("conf/jdbc.properties");
    }

}
