package openscanner.cc.stripe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created : sunc
 * Date : 16-7-12
 * Description :
 */
public class GoodsMap {
    private final Map<String, Integer> orderMap = new HashMap<>();

    public GoodsMap() {
        orderMap.put("GOODS-A", 1000);
        orderMap.put("GOODS-B", 2000);
        orderMap.put("GOODS-C", 3000);
    }

    public Integer getAmount(String goods) {
        return orderMap.getOrDefault(goods, 0);
    }
}