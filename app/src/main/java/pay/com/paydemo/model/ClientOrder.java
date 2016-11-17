package pay.com.paydemo.model;

import java.io.Serializable;

/**
 * author: xiecong
 * create time: 2016/11/17 10:48
 * lastUpdate time: 2016/11/17 10:48
 */

public class ClientOrder implements Serializable{
    private String FID;
    private String Fnumber;//订单号
    private String FcontrolUnitID;//公司ID（子商户id）如：在京东平台 购买 金士顿的U盘 这里表示金士顿公司
    private String FUserID;//用户ID 如京东
    private String FUserName;//用户名称
    private double FAmount;//单价
    private int count;//购买数量
    private double FToalAmount; //总金额
    private String materailID;//购买商品ID
    private String materailName;//商品名称
    private String createTime;//下单时间
    private int Fstatus;  //订单状态  1保存 2已付款
    private int FpaymentType;//支付方式ash(1, "现金"),Alipay(2,"支付宝"),  WeiXIn(3,"微信";
    private String address;//收货地址
    private String Fmobil;//联系电话
    private String FDescription;//备注
    private String subject;

    public String getFID() {
        return FID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFnumber() {
        return Fnumber;
    }

    public void setFnumber(String fnumber) {
        Fnumber = fnumber;
    }

    public String getFcontrolUnitID() {
        return FcontrolUnitID;
    }

    public void setFcontrolUnitID(String fcontrolUnitID) {
        FcontrolUnitID = fcontrolUnitID;
    }

    public String getFUserID() {
        return FUserID;
    }

    public void setFUserID(String FUserID) {
        this.FUserID = FUserID;
    }

    public String getFUserName() {
        return FUserName;
    }

    public void setFUserName(String FUserName) {
        this.FUserName = FUserName;
    }

    public double getFAmount() {
        return FAmount;
    }

    public void setFAmount(double FAmount) {
        this.FAmount = FAmount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getFToalAmount() {
        return FToalAmount;
    }

    public void setFToalAmount(double FToalAmount) {
        this.FToalAmount = FToalAmount;
    }

    public String getMaterailID() {
        return materailID;
    }

    public void setMaterailID(String materailID) {
        this.materailID = materailID;
    }

    public String getMaterailName() {
        return materailName;
    }

    public void setMaterailName(String materailName) {
        this.materailName = materailName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getFstatus() {
        return Fstatus;
    }

    public void setFstatus(int fstatus) {
        Fstatus = fstatus;
    }

    public int getFpaymentType() {
        return FpaymentType;
    }

    public void setFpaymentType(int fpaymentType) {
        FpaymentType = fpaymentType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFmobil() {
        return Fmobil;
    }

    public void setFmobil(String fmobil) {
        Fmobil = fmobil;
    }

    public String getFDescription() {
        return FDescription;
    }

    public void setFDescription(String FDescription) {
        this.FDescription = FDescription;
    }
}
