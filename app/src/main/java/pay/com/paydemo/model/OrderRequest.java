package pay.com.paydemo.model;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * 下单请求
 */
public class OrderRequest {

    private String FControlUnitID;//公司
    private String FEquipmentNo;
    private String FUserId;
    private String Fcontractno;
    private String subject;
    private double total_fee;
    private String body;
    private int paymentType;
    private String create_ip;
    public String getFControlUnitID() {
        return FControlUnitID;
    }

    public void setFControlUnitID(String fControlUnitID) {
        FControlUnitID = fControlUnitID;
    }

    public String getFEquipmentNo() {
        return FEquipmentNo;
    }

    public void setFEquipmentNo(String fEquipmentNo) {
        FEquipmentNo = fEquipmentNo;
    }

    public String getFUserId() {
        return FUserId;
    }

    public void setFUserId(String fUserId) {
        FUserId = fUserId;
    }

    public String getFcontractno() {
        return Fcontractno;
    }

    public void setFcontractno(String fcontractno) {
        Fcontractno = fcontractno;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(double total_fee) {
        this.total_fee = total_fee;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }



    public static OrderRequest build(PaymentType paymentType,TBDReceivingBill receivingBillBean,Context context){
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setPaymentType(paymentType.getValue());
//        orderRequest.setSubject("待付产品费用");
        orderRequest.setTotal_fee(receivingBillBean.getFamount());
        orderRequest.setFcontractno(receivingBillBean.getFcontractno());
//        orderRequest.setFUserId(LoginUserInfoMannager.getInstance().getTbdUser().getFID());
        orderRequest.setFControlUnitID(receivingBillBean.getFcontrolunitid());
//        orderRequest.setFEquipmentNo(JBApplication.getDeviceId());
        orderRequest.create_ip = getDeviceIp(context);
        return orderRequest;
    }
    /**
     * 获取设备ip
     *
     * @return
     */
    public static String getDeviceIp(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return ((ipAddress >> 24) & 0xFF) + "." +
                ((ipAddress >> 16) & 0xFF) + "." +
                ((ipAddress >> 8) & 0xFF) + "." +
                (ipAddress & 0xFF);
    }
}
