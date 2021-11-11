package com.nrib.common;

/**
 * @author gaoliming
 *
 */
public class Constant {

    /** 应用授权作用域 静默授权，可获取成员的基础信息 */
    public static final String SCOPE1 = "snsapi_base";

    /** 应用授权作用域 静默授权，可获取成员的详细信息，但不包含手机、邮箱 */
    public static final String SCOPE2 = "snsapi_userinfo";

    /** 应用授权作用域 手动授权，可获取成员的详细信息，包含手机、邮箱 */
    public static final String SCOPE3 = "snsapi_privateinfo";

    /** 获取code的URL 请求方式：GET（HTTPS） 必须参数一：access_token 调用接口凭证 必须参数二：code */
    public static final String GET_CROP_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=CORPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&agentid=AGENTID&state=STATE#wechat_redirect";

    /** #获取access_token 请求方式：GET（HTTPS） 必须参数一：corpid 企业ID 必须参数二：corpsecret 应用的凭证密钥 */
    public static final String GET_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRECT";

    /** #获取成员信息的URL */
    public static final String GET_USERTICKET_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";

    /** #获取成员详情的URL */
    public static final String GET_USERDETAIL_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail?access_token=ACCESS_TOKEN";

    /** 共用路径，文件上传的路径 */
    public static final String UPLOAD_PATH = "/opt/mtsClient/WechatAccesser/upload";
    
    /** 做成JSON文件的路径 */
    public static final String JSON_PATH = "/opt/mtsClient/MtsSender/dat";
    
    /** 创建工单URL */
    public static final String CREATE_WORK_URL = "/ccfsp/Sv/OA/AddEvent/oa_add_event.asp";

    /** 工单列表取得URL */
    public static final String WORK_LIST_URL = "/ccfsp/Sv/OA/QueryEvent/oa_query_event.asp";

    /** 工单详细取得URL */
    public static final String WORK_DETAIL_URL = "/ccfsp/Sv/OA/WeChatDetail/wechatdetail.asp";

    /** #获取jsapi_ticket的URL */
    public static final String GET_JSAPI_TICKET_URL = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN";

    /** #获取临时素材的URL */
    public static final String GET_MEDIA_URL = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
    
    
}
