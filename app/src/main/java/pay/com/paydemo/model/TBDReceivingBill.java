package pay.com.paydemo.model;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TBDReceivingBill implements Serializable{
    private String FID;// 主键,根据系统的GUID生成
    private String FControlUnitID;//公司
    private String FContractNo;//合同号
    private double FAmount; //金额
    private String FBizDate;// 业务日期
    private String FCreatorID;//创建人
    private String FCreateTime;//创建时间
    private String FLastUpdateTime;//最后修改时间
    private String FLastUpdateUserID;//最后修改人
    private String FDescription;
    private String FEquipmentNo;
    private int FStatus;//1保存,2已收款
    private String FMobile;//手机
    private String FEffectDate;//有效期
    private int FPayType;//1.现金,2.支付宝,3.微信
    private String FAccountNo;//对方账号
    private String FPayOrderNo;//付款单号
    private String FAuditorID;//收款人
    private String FAuditTime;//收款时间
    private String FDelStatus;//
    private String FNumber;

    public String getFid() {
        return FID;
    }

    public void setFid(String FID) {
        this.FID = FID;
    }

    public String getFcontrolunitid() {
        return FControlUnitID;
    }

    public void setFcontrolunitid(String FControlUnitID) {
        this.FControlUnitID = FControlUnitID;
    }

    public String getFcontractno() {
        return FContractNo;
    }

    public void setFcontractno(String FContractNo) {
        this.FContractNo = FContractNo;
    }

    public double getFamount() {
        return FAmount;
    }

    public void setFamount(double FAmount) {
        this.FAmount = FAmount;
    }

    public String getFbizdate() {
        return FBizDate;
    }

    public void setFbizdate(String FBizDate) {
        this.FBizDate = FBizDate;
    }

    public String getFcreatorid() {
        return FCreatorID;
    }

    public void setFcreatorid(String FCreatorID) {
        this.FCreatorID = FCreatorID;
    }

    public String getFcreatetime() {
        return FCreateTime;
    }

    public void setFcreatetime(String FCreateTime) {
        this.FCreateTime = FCreateTime;
    }

    public String getFlastupdatetime() {
        return FLastUpdateTime;
    }

    public void setFlastupdatetime(String FLastUpdateTime) {
        this.FLastUpdateTime = FLastUpdateTime;
    }

    public String getFlastupdateuserid() {
        return FLastUpdateUserID;
    }

    public void setFlastupdateuserid(String FLastUpdateUserID) {
        this.FLastUpdateUserID = FLastUpdateUserID;
    }

    public String getFdescription() {
        return FDescription;
    }

    public void setFdescription(String FDescription) {
        this.FDescription = FDescription;
    }

    public String getFequipmentno() {
        return FEquipmentNo;
    }

    public void setFequipmentno(String FEquipmentNo) {
        this.FEquipmentNo = FEquipmentNo;
    }

    public int getFstatus() {
        return FStatus;
    }

    public void setFstatus(int FStatus) {
        this.FStatus = FStatus;
    }

    public String getFmobile() {
        return FMobile;
    }

    public void setFmobile(String FMobile) {
        this.FMobile = FMobile;
    }

    public String getFeffectdate() {
        return FEffectDate;
    }

    public long getFeffectTime(){
        if(FEffectDate == null)
            return 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(FEffectDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setFeffectdate(String FEffectDate) {
        this.FEffectDate = FEffectDate;
    }

    public int getFpaytype() {
        return FPayType;
    }

    public void setFpaytype(int FPayType) {
        this.FPayType = FPayType;
    }

    public String getFAccountNo() {
        return FAccountNo;
    }

    public void setFAccountNo(String FAccountNo) {
        this.FAccountNo = FAccountNo;
    }

    public String getFPayOrderNo() {
        return FPayOrderNo;
    }

    public void setFPayOrderNo(String FPayOrderNo) {
        this.FPayOrderNo = FPayOrderNo;
    }

    public String getFAuditorID() {
        return FAuditorID;
    }

    public void setFAuditorID(String FAuditorID) {
        this.FAuditorID = FAuditorID;
    }

    public String getFAuditTime() {
        if (FAuditTime == null || FAuditTime.equals("")) {
            return "";
        }
        try {
            SimpleDateFormat mFormatDay = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = mFormatDay.parse(FAuditTime);
            return mFormatDay.format(date);
        } catch (Exception e) {
        }

        return "";
    }

    public long getFAuditMissTime(){
        if(FAuditTime == null)
            return 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(FAuditTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setFAuditTime(String FAuditTime) {
        this.FAuditTime = FAuditTime;
    }

    public static TBDReceivingBill convert(OrderQueryRsp orderRsp){
        TBDReceivingBill tbdReceivingBill = new TBDReceivingBill();
        tbdReceivingBill.setFPayOrderNo(orderRsp.getTradeNo());
        tbdReceivingBill.setFAuditTime(orderRsp.getPayTime());
        tbdReceivingBill.setFpaytype(orderRsp.getPayType());
        tbdReceivingBill.setFdescription(orderRsp.getFDescription());
        tbdReceivingBill.setFamount(orderRsp.getTotalFee());
        return tbdReceivingBill;
    }
}
