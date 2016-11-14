package pay.com.paydemo;


import pay.com.paydemo.model.TBDReceivingBill;

/**
 * @author dengyuanming
 * @ClassName: IPay
 * @Description:支付接口
 * @date 2016年8月22日 上午9:31
 */
public interface IPay {

    void pay(TBDReceivingBill tbdReceivingBillBean);

}
