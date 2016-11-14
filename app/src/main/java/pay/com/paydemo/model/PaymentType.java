package pay.com.paydemo.model;



public enum PaymentType {
    Alipay_Scan(1),
    Alipay_App(2),
    Weixin_Scan(3),
    Weixin_App(4);

    private int value;

    PaymentType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
