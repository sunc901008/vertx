package openscanner.cc;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import openscanner.cc.Verticles.JdbcController;
import openscanner.cc.Verticles.Property;

import java.util.Properties;

/**
 * Created : sunc
 * Date : 16-7-14
 * Description :
 */
public class Test {
    public static void main(String[] args) {
//        String string = "qw1";
//        long l = -1;
//        try {
//            l = Long.parseLong(string);
//        }catch (Exception e){
//        }
//        System.out.println("start");
//
//        Vertx vertx = Vertx.vertx();
//        new JdbcController(vertx).excute((bool, res) -> {
//            if (bool) {
//                System.out.println(res);
//            } else {
//                System.out.println("---");
//            }
//        });
//        new JdbcController(vertx).excute((bool, res) -> {
//            if (bool) {
//                System.out.println(res);
//            } else {
//                System.out.println("---");
//            }
//        });

//        Properties p1 = new Property().getProperties("conf/test.properties");
//        System.out.println(p1.getProperty("username"));
//        Properties p2 = new Property().getProperties("conf/jdbc.properties");
//        System.out.println(p2.getProperty("username"));

        String open_id = "f59f4c848f3645cba927bf7c0ddc50d3";

        String sql = "select a.count as count,unpaid.count as unpaid_count from (select count(id) as count from orders " +
                "where created_at between date_format(now(),'%Y-%m-%d') and date_format(date_add(now(), interval 1 day),'%Y-%m-%d')" +
                " and open_id = '" + open_id + "') a,(select count(id) as count from orders " +
                "where created_at between date_format(now(),'%Y-%m-%d') and date_format(date_add(now(), interval 1 day),'%Y-%m-%d')" +
                " and open_id = '" + open_id + "' and is_paid = 0) unpaid";

        System.out.print(sql);


    }
}
