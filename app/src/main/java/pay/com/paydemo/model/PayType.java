package pay.com.paydemo.model;


public enum PayType {
    Cash(1, "现金"),
    Alipay(2,"支付宝"),
    WeiXIn(3,"微信");

    private int value;
    private String type;

    PayType(int value,String type){
        this.value = value;
        this.type = type;
    }

    public int getValue(){
        return value;
    }

    public String getType(){
        return type;
    }

    public static PayType type(int value){
        PayType[] payTypes = values();
        for(PayType paytype : payTypes){
            if(paytype.getValue() == value)
                return paytype;

        }
        return null;
    }

}
