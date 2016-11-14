package pay.com.paydemo.model;

import android.text.TextUtils;

/*
 * 和服务端的通用返回对象
 */
public class WebResult {
    private static final long serialVersionUID = 1L;
    public final static String SUCCESS = "1";
    public final static String FAIL = "0";
    private String code;//错误代码
    private String msg; //"错误消息的描述"
    private Object result;//返回的结果对象

    public WebResult(Object result, String code, String msg) {
        this.result = result;
        this.code = code;
        this.msg = msg;
    }

    public WebResult() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isSuccess(){
        if(!TextUtils.isEmpty(code) && code.equals(SUCCESS))
            return true;
        return false;
    }
}
