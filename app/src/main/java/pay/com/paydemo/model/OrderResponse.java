package pay.com.paydemo.model;

public class OrderResponse {
	private String result_code;
	private String err_Msg;

	private String prepay_id;
	private String code_url;
	private String out_trade_no;

	private String appid; // 微信分配的ID
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getPrepay_id() {
		return prepay_id;
	}
	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	public String getCode_url() {
		return code_url;
	}
	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getErr_Msg() {
		return err_Msg;
	}
	public void setErr_Msg(String err_Msg) {
		this.err_Msg = err_Msg;
	}

}

