package pay.com.paydemo.alipay;


import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import pay.com.paydemo.Constants;
import pay.com.paydemo.IPay;
import pay.com.paydemo.model.AlipayAppTradeOrder;
import pay.com.paydemo.model.ClientOrder;
import pay.com.paydemo.model.OrderQuery;
import pay.com.paydemo.model.OrderQueryRsp;
import pay.com.paydemo.model.PaymentType;
import pay.com.paydemo.model.WebResult;
import pay.com.paydemo.utils.HttpUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static pay.com.paydemo.utils.GsonFactory.getGson;

public class AliPay implements IPay {

    private Activity context;
    private String orderNo;

    public AliPay(Activity context) {
        this.context = context;
    }

    @Override
    public void pay(final ClientOrder clientOrder) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String requestData = getGson().toJson(clientOrder);
                String responseData = HttpUtils.httpPost(Constants.ALIPAY_CHARGE_ORDER, requestData);

                if (TextUtils.isEmpty(responseData)) {
                    subscriber.onError(new Exception("获取订单失败"));
                    return;
                } else {
                    WebResult data = getGson().fromJson(responseData, new TypeToken<WebResult>() {}.getType());
                    if(data != null && data.isSuccess()){
                        AlipayAppTradeOrder alipayAppTradeOrder = getGson().fromJson(getGson().toJson(data.getResult()), AlipayAppTradeOrder.class);
                        if(alipayAppTradeOrder != null){
                            orderNo = alipayAppTradeOrder.getOrderNo();
                            String orderData = alipayAppTradeOrder.getContent();
                            subscriber.onNext(orderData);
                            return;
                        }
                    }
                    subscriber.onError(new Exception("获取订单失败"));
                    return;
                }
            }
        }).flatMap(new Func1<String, Observable<Map<String, String>>>() {
            @Override
            public Observable<Map<String, String>> call(final String orderInfo) {
                return Observable.create(new Observable.OnSubscribe<Map<String, String>>() {
                    @Override
                    public void call(Subscriber<? super Map<String, String>> subscriber) {
                        PayTask alipay = new PayTask(context);// 构造PayTask 对象
                        Map<String, String> result = alipay.payV2(orderInfo, true);// 调用支付接口，获取支付结果
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
//                ViewUtils.showWaitingDialog(context);
            }
        }).subscribe(new Subscriber<Map<String, String>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                ViewUtils.cancelWaitingDialog(context);
                Toast.makeText(context, "获取订单失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Map<String, String> result) {
//                ViewUtils.cancelWaitingDialog(context);
                PayResult payResult = new PayResult(result);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                    Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                    if(!TextUtils.isEmpty(orderNo)){
                        checkOrderStatus(PaymentType.Alipay_App.getValue(),orderNo,null);
                    }
                }else if(TextUtils.equals(resultStatus, "6001")){
                    Toast.makeText(context, "支付取消", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 调用app支付支付完成的时候  向服务器查询确认订单的状态
     */
    private void checkOrderStatus(final int payType,final String outTradeNo,final String tradeNo){
        if(payType != PaymentType.Alipay_App.getValue() && payType != PaymentType.Weixin_App.getValue())
            return;
        Log.e("PayBroadcastReceiver","checkOrderStatus");
        Observable.create(new Observable.OnSubscribe<OrderQueryRsp>() {
            @Override
            public void call(Subscriber<? super OrderQueryRsp> subscriber) {
                String queryUrl = "";
                if (payType == PaymentType.Alipay_App.getValue()) {
                    queryUrl = Constants.ALIPAY_TRADE_QUERY;
                } else if (payType == PaymentType.Weixin_App.getValue()) {
                    queryUrl = Constants.WEIXIN_TRADE_QUERY;
                }
                OrderQuery orderQuery = new OrderQuery(payType, outTradeNo,tradeNo);
                String queryJson = getGson().toJson(orderQuery);
                String responseData = HttpUtils.httpPost(queryUrl, queryJson);
                if (TextUtils.isEmpty(responseData)) {
                    subscriber.onError(new Exception());
                    return;
                }
                WebResult data = getGson().fromJson(responseData, WebResult.class);
                if (data == null || !data.getCode().equals("1") || data.getResult() == null) {
                    subscriber.onError(new Exception());
                    return;
                } else {
                    OrderQueryRsp orderRsp = getGson().fromJson(getGson().toJson(data.getResult()), OrderQueryRsp.class);
                    subscriber.onNext(orderRsp);
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
//                        showWaitingDialog(UserInfoEditActivity.this,getString(R.string.waitting));
                    }
                })
                .subscribe(new Subscriber<OrderQueryRsp>() {
                    @Override
                    public void onCompleted() {
//                        cancelWaitingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        cancelWaitingDialog();
                    }

                    @Override
                    public void onNext(OrderQueryRsp orderQueryRsp) {
                        if(orderQueryRsp != null){
                            //真正支付成功
                            Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}