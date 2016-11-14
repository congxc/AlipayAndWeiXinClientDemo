package pay.com.paydemo.model;

/*
 * 支付宝App订单对象
 */
public class AlipayAppTradeOrder {
    private String orderNo;
    private String content;
    private String ownerId;
    private Double totalFee;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderId(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
