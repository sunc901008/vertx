package openscanner.cc.stripe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created : sunc
 * Date : 16-7-12
 * Description :
 */
public class OrderMap {
    private final Map<String, Integer> OrderMap = new HashMap<>();

    public OrderMap() {
        OrderMap.put("GOODS-A", 1000);
        OrderMap.put("GOODS-B", 2000);
        OrderMap.put("GOODS-C", 3000);
    }

    public Integer getAmount(String purcharse) {
        return OrderMap.getOrDefault(purcharse, 0);
    }

}
