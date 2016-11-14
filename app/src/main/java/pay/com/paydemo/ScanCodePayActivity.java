package pay.com.paydemo;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pay.com.paydemo.model.OrderQuery;
import pay.com.paydemo.model.OrderQueryRsp;
import pay.com.paydemo.model.OrderRequest;
import pay.com.paydemo.model.PayType;
import pay.com.paydemo.model.PaymentType;
import pay.com.paydemo.model.QrTradeOrder;
import pay.com.paydemo.model.TBDReceivingBill;
import pay.com.paydemo.model.WebResult;
import pay.com.paydemo.utils.GsonFactory;
import pay.com.paydemo.utils.HttpUtils;
import pay.com.paydemo.utils.ZxingUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class ScanCodePayActivity extends AppCompatActivity {

    private ImageView qr_code_img;

    private TextView contract_no;

    private TextView payable_money;

    private TextView qr_code_txt;

    private TBDReceivingBill tbdReceivingBillBean;

    //查询的时间间隔
    private static int INTERVAL = 5;
    private Executor executor = Executors.newCachedThreadPool();
    private Subscription subscribe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_pay);
        qr_code_img = (ImageView)findViewById(R.id.qr_code_img);
        contract_no = (TextView)findViewById(R.id.contract_no);
        payable_money = (TextView)findViewById(R.id.payable_money);
        qr_code_txt = (TextView)findViewById(R.id.qr_code_txt);
        initViews();
        loadData();
    }
    protected void initViews() {
        Bundle data = getIntent().getExtras();
        if (data != null) {
            tbdReceivingBillBean = (TBDReceivingBill) data.getSerializable(TBDReceivingBill.class.getName());
            if (tbdReceivingBillBean != null) {
                contract_no.setText("合同号 :" + tbdReceivingBillBean.getFcontractno());
                payable_money.setText(String.valueOf(tbdReceivingBillBean.getFamount()));
            }
        }
    }


//    private Map<String, String> buildOrderParam() {
//        Map<String, String> orderInfo = new HashMap<>();
//        orderInfo.put(Constants.FPAYTYPE, String.valueOf(tbdReceivingBillBean.getFpaytype()));
//        orderInfo.put(Constants.TOTAL_AMOUNT, String.valueOf(tbdReceivingBillBean.getFamount()));
//        orderInfo.put(Constants.SUBJECT, JBApplication.getResStr(R.string.no_pay_money));
//        orderInfo.put(Constants.FCONTRACT_NO, tbdReceivingBillBean.getFcontractno());
//        orderInfo.put(Constants.FCONTROLUNITID, tbdReceivingBillBean.getFcontrolunitid());
//        orderInfo.put(Constants.FEFFECTDATE, tbdReceivingBillBean.getFeffectdate());
//        orderInfo.put(Constants.FACCOUNTNO, tbdReceivingBillBean.getFAccountNo());
//        if(tbdReceivingBillBean.getFpaytype() == PayType.Alipay.getValue()) {
//            orderInfo.put(Constants.PAYMENT_TYPE, String.valueOf(PaymentType.Alipay_Scan.getValue()));
//        }else if(tbdReceivingBillBean.getFpaytype() == PayType.WeiXIn.getValue()){
//            orderInfo.put(Constants.PAYMENT_TYPE, String.valueOf(PaymentType.Weixin_Scan.getValue()));
//        }
//        return orderInfo;
//    }

    protected void loadData() {
        Observable.create(new Observable.OnSubscribe<WebResult>() {
            @Override
            public void call(Subscriber<? super WebResult> subscriber) {
                PaymentType paymentType = null;
                if(tbdReceivingBillBean.getFpaytype() == PayType.Alipay.getValue()) {
                    paymentType = PaymentType.Alipay_Scan;
                }else if(tbdReceivingBillBean.getFpaytype() == PayType.WeiXIn.getValue()){
                    paymentType = PaymentType.Weixin_Scan;
                }
                OrderRequest orderRequest =  OrderRequest.build(paymentType,tbdReceivingBillBean,ScanCodePayActivity.this);
                String requestData = GsonFactory.getGson().toJson(orderRequest);
                String responseData = null;
                if (tbdReceivingBillBean.getFpaytype() == PayType.Alipay.getValue())
                    responseData = HttpUtils.httpPost(Constants.ALIPAY_GET_QRCODE, requestData);
                else if (tbdReceivingBillBean.getFpaytype() == PayType.WeiXIn.getValue()) {
                    responseData = HttpUtils.httpPost(Constants.WEIXIN_GET_QRCODE, requestData);
                }
                if (TextUtils.isEmpty(responseData)) {
                    subscriber.onError(new Exception("获取订单失败"));
                    return;
                } else {
                    WebResult data = GsonFactory.getGson().fromJson(responseData,WebResult.class);
                    if(data != null){
                        subscriber.onNext(data);
                        subscriber.onCompleted();
                    } else{
                        subscriber.onError(new Exception("获取二维码失败"));
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribe(new Subscriber<WebResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ScanCodePayActivity.this, "获取二维码失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(WebResult result) {
                        if(result.isSuccess()){
                            QrTradeOrder qrTradeOrder = GsonFactory.getGson().fromJson(GsonFactory.getGson().toJson(result.getResult()), QrTradeOrder.class);
                            if (qrTradeOrder != null) {
                                Bitmap bitmap = ZxingUtils.getQRCodeImge(qrTradeOrder.getTwoDimCode(), dp2px(256), dp2px(256), "");
                                qr_code_img.setImageBitmap(bitmap);
                                if (tbdReceivingBillBean != null) {
                                    if (tbdReceivingBillBean.getFpaytype() == PayType.Alipay.getValue())
                                        qr_code_txt.setText(String.format("请用%s扫描二维码", "支付宝"));
                                    else if (tbdReceivingBillBean.getFpaytype() == PayType.WeiXIn.getValue()) {
                                        qr_code_txt.setText(String.format("请用%s扫描二维码", "微信"));
                                    }
                                }
                                queryRollOrderStatus(qrTradeOrder);
                            }} else if(result.getCode().equals("2")){
//                            showToast(JBApplication.getResStr(R.string.tip_order_has_paied));
                            Toast.makeText(ScanCodePayActivity.this, "订单已经支付", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ScanCodePayActivity.this, "获取二维码失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private  int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    /*
    * 每隔5秒轮询一次订单
    * */
    private void queryRollOrderStatus(final QrTradeOrder qrTradeOrder) {
        subscribe = Observable.create(new Observable.OnSubscribe<OrderQueryRsp>() {
                                          @Override
                                          public void call(Subscriber<? super OrderQueryRsp> subscriber) {
                                              OrderQuery orderQuery = new OrderQuery();
                                              orderQuery.setOutOrderNo(qrTradeOrder.getOrderNo());
                                              PaymentType paymentType = null;
                                              if(tbdReceivingBillBean.getFpaytype() == PayType.Alipay.getValue()) {
                                                  paymentType = PaymentType.Alipay_Scan;
                                              }else if(tbdReceivingBillBean.getFpaytype() == PayType.WeiXIn.getValue()){
                                                  paymentType = PaymentType.Weixin_Scan;
                                              }
                                              orderQuery.setPaymentType(paymentType.getValue());
                                              String requestData = GsonFactory.getGson().toJson(orderQuery);
                                              String responseData = null;
                                              if (tbdReceivingBillBean.getFpaytype() == PayType.Alipay.getValue())
                                                  responseData = HttpUtils.httpPost(Constants.ALIPAY_TRADE_QUERY, requestData);
                                              else if (tbdReceivingBillBean.getFpaytype() == PayType.WeiXIn.getValue()) {
                                                  responseData = HttpUtils.httpPost(Constants.WEIXIN_TRADE_QUERY, requestData);
                                              }
                                              if (TextUtils.isEmpty(responseData)) {
                                                  subscriber.onError(new Exception("获取订单失败"));
                                                  return;
                                              } else {
                                                  WebResult data = GsonFactory.getGson().fromJson(responseData, new TypeToken<WebResult>() {
                                                  }.getType());
                                                  if(data != null && data.isSuccess()){
                                                      OrderQueryRsp orderRsp = GsonFactory.getGson().fromJson(GsonFactory.getGson().toJson(data.getResult()),OrderQueryRsp.class);
                                                      subscriber.onNext(orderRsp);
                                                      subscriber.onCompleted();
                                                      return;
                                                  }else{
                                                      subscriber.onError(new Exception("获取订单失败"));
                                                      return;
                                                      //subscriber.onNext(false);
                                                  }
                                              }
                                          }
                                      }

        ).subscribeOn(Schedulers.from(executor)).
                repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                               @Override
                               public Observable<?> call(Observable<? extends Void> observable) {

                                   return Observable.interval(INTERVAL, TimeUnit.SECONDS, Schedulers.io());
                               }
                           }
                ).
                observeOn(Schedulers.immediate()).
                subscribe(new Subscriber<OrderQueryRsp>() {
                              @Override
                              public void onCompleted() {

                              }

                              @Override
                              public void onError(Throwable e) {

                              }

                              @Override
                              public void onNext(final OrderQueryRsp result) {
                                  if (result != null) {
                                      runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Toast.makeText(ScanCodePayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                                              if (subscribe != null)
                                                  subscribe.unsubscribe();
                                              if(!isFinishing()) {
                                                  finish();
                                              }
                                              setPaySuccessBroadcast(result);
                                          }
                                      });
                                  }
                              }
                          }
                );
    }

    private void setPaySuccessBroadcast(OrderQueryRsp result){
        Intent intent = new Intent(Constants.PAY_RESULT_ACTION);
        intent.setAction(Constants.PAY_RESULT_ACTION);
        intent.putExtra(Constants.ERR_CODE,Constants.PAY_SUCCESS);
        TBDReceivingBill receivingBill = TBDReceivingBill.convert(result);
        intent.putExtra(TBDReceivingBill.class.getName(),receivingBill);
//        JBApplication.getAppContext().sendBroadcast(intent);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null)
            subscribe.unsubscribe();
    }
}