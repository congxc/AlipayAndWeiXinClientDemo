package pay.com.paydemo.model;

/*
 * 扫描订单对象
 */
public class QrTradeOrder {
    private String orderNo;
    private String twoDimCode;
    private String ownerId;
    private Double totalFee;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getTwoDimCode() {
        return twoDimCode;
    }

    public void setTwoDimCode(String twoDimCode) {
        twoDimCode = twoDimCode;
    }
}
