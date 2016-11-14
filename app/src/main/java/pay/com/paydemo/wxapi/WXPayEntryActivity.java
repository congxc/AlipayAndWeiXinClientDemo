package pay.com.paydemo.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import pay.com.paydemo.Constants;
import pay.com.paydemo.model.PaymentType;


public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler{

	private static final String TAG = "WXPayEntryActivity";


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, WXConfig.APP_ID);
        api.handleIntent(getIntent(), this);
    }


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}


	@Override
	public void onResp(final BaseResp resp) {
		Log.e(TAG, "errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode == BaseResp.ErrCode.ERR_OK){
				Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();

			}else if(resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
				Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "支付失敗", Toast.LENGTH_SHORT).show();
			}
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if(!isFinishing()) {
						finish();
					}
					if(resp.errCode == BaseResp.ErrCode.ERR_OK){
						setPaySuccessBroadcast();
					}
				}
			});
		}
	}

	private void setPaySuccessBroadcast(){
		Intent intent = new Intent(Constants.PAY_RESULT_ACTION);
		intent.setAction(Constants.PAY_RESULT_ACTION);
		intent.putExtra(Constants.ERR_CODE,Constants.PAY_SUCCESS);
		intent.putExtra(Constants.PAYMENT_TYPE, PaymentType.Weixin_App.getValue());
//		JBApplication.getAppContext().sendBroadcast(intent);
		sendBroadcast(intent);
	}
}