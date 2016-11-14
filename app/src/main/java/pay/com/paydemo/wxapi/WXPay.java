package pay.com.paydemo.wxapi;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import pay.com.paydemo.Constants;
import pay.com.paydemo.IPay;
import pay.com.paydemo.model.OrderRequest;
import pay.com.paydemo.model.PaymentType;
import pay.com.paydemo.model.TBDReceivingBill;
import pay.com.paydemo.model.WebResult;
import pay.com.paydemo.model.WeixinAppTradeOrder;
import pay.com.paydemo.utils.GsonFactory;
import pay.com.paydemo.utils.HttpUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


public class WXPay implements IPay {

    private Activity context;
    private final IWXAPI msgApi;
    private PayReq req;
    private static final String TAG = "MicroMsg.SDKSample.PayActivity";
    private StringBuffer sb;

    public WXPay(Activity activity) {
        this.context = activity;
        req = new PayReq();
        sb = new StringBuffer();
        msgApi = WXAPIFactory.createWXAPI(activity, null);
        msgApi.registerApp(WXConfig.APP_ID);
    }

    @Override
    public void pay(final TBDReceivingBill receivingBillBean) {
        if(!msgApi.isWXAppInstalled()){
            Toast.makeText(context, "未安装微信", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!msgApi.isWXAppSupportAPI()){
            Toast.makeText(context, "当前微信版本不支持支付", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(receivingBillBean.getFcontractno())
                || TextUtils.isEmpty(receivingBillBean.getFeffectdate())
                || receivingBillBean.getFamount() <= 0
                || receivingBillBean.getFstatus() == 2) {
            Toast.makeText(context, "参数错误", Toast.LENGTH_SHORT).show();
            return;
        }
        Observable.create(new Observable.OnSubscribe<WeixinAppTradeOrder>() {
            @Override
            public void call(Subscriber<? super WeixinAppTradeOrder> subscriber) {
                OrderRequest orderRequest =  OrderRequest.build(PaymentType.Weixin_App,receivingBillBean,context);
                String requestData = GsonFactory.getGson().toJson(orderRequest);

                String responseData = HttpUtils.httpPost(Constants.WEIXIN_CHARGE_ORDER, requestData);
                if (TextUtils.isEmpty(responseData)) {
                    subscriber.onError(new Exception("获取订单失败"));
                    return;
                } else {
                    WebResult data = GsonFactory.getGson().fromJson(responseData, new TypeToken<WebResult>() {}.getType());
                    if(data != null && data.isSuccess()){
                        WeixinAppTradeOrder weixinAppTradeOrder = GsonFactory.getGson().fromJson(GsonFactory.getGson().toJson(data.getResult()), new TypeToken<WeixinAppTradeOrder>() {}.getType());
                        subscriber.onNext(weixinAppTradeOrder);
                        subscriber.onCompleted();
                    }else{
                        subscriber.onError(new Exception("获取订单失败"));
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
//                        ViewUtils.showWaitingDialog(context);
                    }
                })
                .subscribe(new Subscriber<WeixinAppTradeOrder>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        ViewUtils.cancelWaitingDialog(context);
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(WeixinAppTradeOrder result) {
//                        ViewUtils.cancelWaitingDialog(context);
                        if (result != null) {
//                            if(context instanceof UserInfoEditActivity){
//                                ((UserInfoEditActivity)context).setWxPayOrderId(result.getOutTradeNo());
//                            }
                            genPayReq(result);
                        } else {
                            Toast.makeText(context, "获取订单失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }


    private void genPayReq(WeixinAppTradeOrder weixinAppTradeOrder) {

        req.appId = weixinAppTradeOrder.getAppid();
        req.partnerId = weixinAppTradeOrder.getPartnerid();
        req.prepayId = weixinAppTradeOrder.getPrepayid();
        req.packageValue = weixinAppTradeOrder.getIpackage();
        req.timeStamp = weixinAppTradeOrder.getTimestamp();
        req.nonceStr = weixinAppTradeOrder.getNoncestr();
        req.sign = weixinAppTradeOrder.getSign();
        msgApi.registerApp(req.appId);
        msgApi.sendReq(req);
    }

}
