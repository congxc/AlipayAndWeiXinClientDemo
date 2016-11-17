package pay.com.paydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.UUID;

import pay.com.paydemo.alipay.AliPay;
import pay.com.paydemo.model.ClientOrder;
import pay.com.paydemo.model.PayType;
import pay.com.paydemo.wxapi.WXPay;

public class PaymentSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private ClientOrder mClientOrder;
    private String JDid = "jd888888888id";//京东公司ID
    private String KingID = "king9999999id";//金士顿公司id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_select);
        Intent intent = getIntent();
        int material = intent.getIntExtra("material", 0);

        inidatas(material);////首先客户端生成订单
        findViewById(R.id.login_alipay_pay).setOnClickListener(this);
        findViewById(R.id.login_weixin_pay).setOnClickListener(this);
        findViewById(R.id.scan_code_alipay).setOnClickListener(this);
        findViewById(R.id.scan_code_weixin).setOnClickListener(this);

    }

    private void inidatas(int material) {
        mClientOrder = new ClientOrder();
        mClientOrder.setFID(UUID.randomUUID().toString().replace("-",""));
        mClientOrder.setFcontrolUnitID(KingID);
        mClientOrder.setFnumber("S00123456");
        mClientOrder.setFUserID("888888");
        mClientOrder.setFUserName("zhangsan");
        if(material == 1){
            mClientOrder.setMaterailID("000001");
            mClientOrder.setMaterailName("屠龙刀");
        }else{
            mClientOrder.setMaterailID("000002");
            mClientOrder.setMaterailName("倚天剑");
        }
        mClientOrder.setFstatus(1);
        mClientOrder.setAddress("天安门");
        mClientOrder.setFmobil("155123456");
        mClientOrder.setFAmount(0.01);
        mClientOrder.setCount(1);
        mClientOrder.setFToalAmount(mClientOrder.getCount()*mClientOrder.getFAmount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_weixin_pay:
                WXPay wxPay = new WXPay(this);
                mClientOrder.setFpaymentType(PayType.WeiXIn.getValue());
                wxPay.pay(mClientOrder);
                break;
            case R.id.scan_code_weixin:
                Bundle data = new Bundle();
                mClientOrder.setFpaymentType(PayType.WeiXIn.getValue());
                data.putSerializable(ClientOrder.class.getName(),mClientOrder);
                Intent intent = new Intent(this,ScanCodePayActivity.class);
                intent.putExtras(data);
                startActivity(intent);
                break;
            case R.id.login_alipay_pay:
                IPay iPay = new AliPay(this);
                mClientOrder.setFpaymentType(PayType.Alipay.getValue());
                iPay.pay(mClientOrder);
                break;
            case R.id.scan_code_alipay:
                data = new Bundle();
                mClientOrder.setFpaymentType(PayType.Alipay.getValue());
                data.putSerializable(ClientOrder.class.getName(),mClientOrder);
                intent = new Intent(this,ScanCodePayActivity.class);
                intent.putExtras(data);
                startActivity(intent);
                break;
        }
    }
}
