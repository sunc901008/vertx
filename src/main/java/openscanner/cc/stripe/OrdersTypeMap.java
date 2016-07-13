package openscanner.cc.stripe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created : sunc
 * Date : 16-7-12
 * Description :
 */
public class OrdersTypeMap {
    private final Map<String, String> ordersTypeMap = new HashMap<>();

    public OrdersTypeMap() {
        ordersTypeMap.put("TYPE-A", "10");
        ordersTypeMap.put("TYPE-B", "20");
        ordersTypeMap.put("TYPE-C", "30");

    }

    public String getType(String type) {
        return ordersTypeMap.getOrDefault(type, "0");
    }

}
