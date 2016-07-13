package openscanner.cc.stripe;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created : sunc
 * Date : 16-7-12
 * Description :
 */

/*
*参考方案：2位业务号+4位年月日+5位时分秒+4位随机数
* 两位业务号可以区分业务类型，比如01代表虚拟商品，02代表实物商品等等；
* 年月日本来是8位，如20160101，不要开头两位也有6位，但是别人很容易看出来规律，
* 可以设定一个参考日期，然后用下订单的日期减去参考日期，得到最终天数，一年是365天，四位表示年月日可以支撑20年，
* 时分秒正常有6位，010203，可以将其转化为秒数，既能隐藏真实下单时间，又可以减少位数。
* 最后四位随机数预先生成好，考虑到订单量不大，10000个订单足够用了，
* 可以设置一个自增序列，里面存10000个随机数，要用的时候按顺序取，用完了再从第一条记录开始取。
*
* 最终确定的方案是：2位子应用号(或者支付类型号)+5位时分秒+4位年月日+4位随机数
*
* */

public class BillNumberBuilder {

    private static final String beginDateStr = "2014-12-01";

    public static long getDaySub(String bill) {//bill 2位类型号

        long billNumber = 0;
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DecimalFormat decimalYMD = new DecimalFormat("0000");
        DecimalFormat decimalHMS = new DecimalFormat("00000");
        try {
            Date beginDate = formatDate.parse(beginDateStr);
            Date date = Calendar.getInstance().getTime();
            Date endDate = formatDate.parse(formatDate.format(date));

            long secend = (formatTime.parse(formatTime.format(date)).getTime() - formatDate.parse(formatDate.format(date)).getTime())/1000;
            bill = bill + decimalHMS.format(secend);//bill +5位时分秒转化的秒数

            long day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
            bill = bill + decimalYMD.format(day);//bill +4位年月日的天数

            Random random = new Random();
            int r = random.nextInt(9999) + 1;
            bill = bill + decimalYMD.format(r);//bill +4位随机数

            billNumber = Long.parseLong(bill);//15位订单号
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return billNumber;
    }

    public static void main(String [] args){

        OrdersTypeMap otm = new OrdersTypeMap();
        System.out.println(getDaySub(otm.getType("TYPE-A")));
    }

}
