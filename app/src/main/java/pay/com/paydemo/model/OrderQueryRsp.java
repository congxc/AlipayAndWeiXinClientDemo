package pay.com.paydemo.model;

public class OrderQueryRsp{
    //支付平台的订单号
    private String tradeNo;
    //内部的订单号
    private String outTradeNo;
    //金额
    private double totalFee;

    private String payTime;

    private String FDescription;

    private int payType;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getFDescription() {
        return FDescription;
    }

    public void setFDescription(String fDescription) {
        FDescription = fDescription;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
