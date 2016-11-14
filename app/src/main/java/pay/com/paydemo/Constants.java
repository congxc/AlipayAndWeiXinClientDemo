package pay.com.paydemo;

/**
 * Created by xc on 2016/10/12.
 */

public class Constants {
    //    public final static String BASE_CHARGE_URL = "http://10.10.10.98:8080";
    public final static String BASE_CHARGE_URL = "http://160c729k13.51mypc.cn";
    public final static String ALIPAY_CHARGE_ORDER = BASE_CHARGE_URL+"/pay/OrderAlipay";
    public final static String ALIPAY_GET_QRCODE = BASE_CHARGE_URL+"/pay/ScanAlipay";
    public final static String ALIPAY_TRADE_QUERY = BASE_CHARGE_URL+"/pay/QueryAlipayOrder";

    public final static String WEIXIN_CHARGE_ORDER = BASE_CHARGE_URL+"/pay/OrderWeiXin";
    public final static String WEIXIN_GET_QRCODE = BASE_CHARGE_URL+"/pay/ScanWeiXin";
    public final static String WEIXIN_TRADE_QUERY = BASE_CHARGE_URL+"/pay/QueryWeiXinOrder";

    public final static String TOTAL_AMOUNT = "total_amount";
    public final static String SUBJECT = "subject";
    public final static String BODY = "body";


    public final static String FPAYTYPE = "fpaytype";
    public final static String FCONTRACT_NO = "fcontractno";

    public final static String FCONTROLUNITID = "FControlUnitID";
    public final static String FEFFECTDATE = "feffectdate";
    public final static String FACCOUNTNO = "FAccountNo";

    public final static String PAYMENT_TYPE = "payment_type";
    public final static String ERR_CODE = "errCode";

    public static final String PAY_RESULT_ACTION = "pay_result_action";

    public final static int PAY_SUCCESS = 0;

    public final static String TRADE_NO = "order_no";
}
