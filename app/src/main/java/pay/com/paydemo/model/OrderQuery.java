package pay.com.paydemo.model;

public class OrderQuery {
    private int paymentType;
    private String out_trade_no;
    //外部订单号
    public String trade_no;


    public OrderQuery() {

    }

    public OrderQuery(int paymentType,String out_trade_no,String trade_no) {
        this.out_trade_no = out_trade_no;
        this.paymentType = paymentType;
        this.trade_no = trade_no;
    }

    public String getOutOrderNo() {
        return out_trade_no;
    }
    public void setOutOrderNo(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
    public int getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }


    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }
}
