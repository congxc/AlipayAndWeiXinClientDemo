package pay.com.paydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import pay.com.paydemo.alipay.AliPay;
import pay.com.paydemo.model.PayType;
import pay.com.paydemo.model.TBDReceivingBill;
import pay.com.paydemo.wxapi.WXPay;

public class PaymentSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private TBDReceivingBill tbdReceivingBillBean;//商品订单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_select);
        inidatas();
        findViewById(R.id.login_alipay_pay).setOnClickListener(this);
        findViewById(R.id.login_weixin_pay).setOnClickListener(this);
        findViewById(R.id.scan_code_alipay).setOnClickListener(this);
        findViewById(R.id.scan_code_weixin).setOnClickListener(this);

    }

    private void inidatas() {
        tbdReceivingBillBean = new TBDReceivingBill();
        tbdReceivingBillBean.setFAccountNo("1055123456");
        tbdReceivingBillBean.setFamount(0.1);
        tbdReceivingBillBean.setFmobile("18798006850");
        tbdReceivingBillBean.setFPayOrderNo("123456");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_weixin_pay:
                WXPay wxPay = new WXPay(this);
                wxPay.pay(tbdReceivingBillBean);
                break;
            case R.id.scan_code_weixin:
                Bundle data = new Bundle();
                tbdReceivingBillBean.setFpaytype(PayType.WeiXIn.getValue());
                data.putSerializable(TBDReceivingBill.class.getName(),tbdReceivingBillBean);
                Intent intent = new Intent(this,ScanCodePayActivity.class);
                intent.putExtras(data);
                startActivity(intent);
                break;
            case R.id.login_alipay_pay:
                IPay iPay = new AliPay(this);
                iPay.pay(tbdReceivingBillBean);
                break;
            case R.id.scan_code_alipay:
                data = new Bundle();
                tbdReceivingBillBean.setFpaytype(PayType.Alipay.getValue());
                data.putSerializable(TBDReceivingBill.class.getName(),tbdReceivingBillBean);
                intent = new Intent(this,ScanCodePayActivity.class);
                intent.putExtras(data);
                startActivity(intent);
                break;
        }
    }
}
