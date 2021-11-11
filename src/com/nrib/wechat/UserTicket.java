/**
 * 
 */
package com.nrib.wechat;

/**
 * @author gaolm
 *
 *         2017/11/27
 */
public class UserTicket {
    /** 返回码 */
    private int errcode;
    /** 对返回码的文本描述内容 */
    private String errmsg;
    /** 成员UserID */
    private String userId;
    /** 成员票据 */
    private String userTicket;
    /** user_ticket的有效时间（秒），随user_ticket一起返回 */
    private Integer expiresIn;
    /** 非企业成员的标识，对当前企业唯一 */
    private String openId;
    /** 手机设备号(由企业微信在安装时随机生成，删除重装会改变，升级不受影响) */
    private String deviceId;
    /** 过期时间 */
    private Long expiration;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserTicket() {
        return userTicket;
    }

    public void setUserTicket(String userTicket) {
        this.userTicket = userTicket;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "UserTicket [errcode=" + errcode + ", errmsg=" + errmsg + ", userId=" + userId + ", userTicket="
                + userTicket + ", expiresIn=" + expiresIn + ", openId=" + openId + ", deviceId=" + deviceId
                + ", expiration=" + expiration + "]";
    }

}
