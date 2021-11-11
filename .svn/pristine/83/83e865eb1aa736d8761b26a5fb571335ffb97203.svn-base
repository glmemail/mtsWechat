/**   
 *  Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
* @Title: CommonUtil.java 
* @Package com.bis.mtsRcverWechat.common 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Administrator  文雪
* @date 2016年4月6日 下午1:56:30 
* @version V1.0   
*/
package com.nrib.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyManagementException;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.nrib.wechat.MyX509TrustManager;
import com.nrib.wechat.Ticket;
import com.nrib.wechat.Token;
import com.nrib.wechat.UserDetail;
import com.nrib.wechat.UserTicket;
import com.nrib.wechat.WorkDetail;
import com.nrib.wechat.WorkList;

public class CommonUtil {
    // 凭证获取（GET）
    public final static String token_url = null;

    public static Logger logger = Logger.getLogger(CommonUtil.class);

    public static ConfUtil getConfigMsg = ConfUtil.getInstance();

    static HostnameVerifier hv = new HostnameVerifier() {
        @Override
        public boolean verify(String urlHostName, SSLSession session) {
            return true;
        }

    };
    
    /**
     * 发送https请求
     * 
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = new JSONObject(buffer.toString());
        } catch (ConnectException e1) {
            logger.error("Exception=" + e1);
            logger.error(getConfigMsg.getMsgvalueAndPar("E0029"));
        } catch (IOException e1) {
            logger.error("Exception=" + e1);
            logger.error(getConfigMsg.getMsgvalueAndPar("E0030"));
        } catch (KeyManagementException e1) {
            logger.error("Exception=" + e1);
            logger.error(getConfigMsg.getMsgvalueAndPar("E0031"));
        } catch (Exception e) {
            logger.error("Exception=" + e);
            logger.error(getConfigMsg.getMsgvalueAndPar("E0032"));
        }
        return jsonObject;
    }

    /**
     * 获取接口访问凭证
     * 
     * @param appid
     *            凭证
     * @param appsecret
     *            密钥
     * @return
     */
    public static Token getToken(String id, String secret, String tokenUrl) {

        Token token = null;
        String requestUrl = tokenUrl.replace("ID", id).replace("SECRECT", secret);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken((String) getJsonObject(jsonObject, "access_token"));
                token.setExpiresIn((Integer) getJsonObject(jsonObject, "expires_in"));

                Date date = new Date();
                Long expiration = date.getTime() + 7200000;

                token.setExpiration(expiration);

                // Date dead = new Date(expiration);
                // System.out.println("获取到token的时刻为"+date);
                // System.out.println("获取到token的到期时刻为" + dead);

            } catch (JSONException e) {

                token = null;
                // 获取token失败
                logger.error("Exception=" + e);
                logger.error(getConfigMsg.getMsgvalueAndPar("E0025"));
                try {
                    // Integer errCode = (Integer) jsonObject.get("errcode");
                    String errmsg = (String) jsonObject.get("errmsg");
                    logger.error(getConfigMsg.getMsgvalueAndPar("E0026") + errmsg);
                } catch (JSONException e1) {
                    logger.error("Exception=" + e1);
                    logger.error(getConfigMsg.getMsgvalueAndPar("E0027"));
                }

            }
        }
        return token;
    }

    public static Ticket getTicket(String ticketurl, String token) {
        Ticket ticket = null;
        String requestUrl = ticketurl.replace("ACCESS_TOKEN", token);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        ticket = new Ticket();
        try {
            ticket.setTicket((String) getJsonObject(jsonObject, "ticket"));
            ticket.setExpiresIn((Integer) getJsonObject(jsonObject, "expires_in"));
            Date date = new Date();
            Long expiration = date.getTime() + 7200000;
            ticket.setExpiration(expiration);
        } catch (JSONException e) {
            ticket = null;
            logger.error("Exception=" + e);
            logger.error(getConfigMsg.getMsgvalueAndPar("E0033"));
            try {
                String errmsg = jsonObject.getString("errmsg");
                logger.error(getConfigMsg.getMsgvalueAndPar("E0034") + errmsg);
            } catch (Exception e1) {
                logger.error(getConfigMsg.getMsgvalueAndPar("E0035"));
            }

        }
        return ticket;
    }

    /**
     * 获取成员信息
     * 
     * @param userTicketurl
     * @param access_token
     * @param code
     * @return
     */
    public static UserTicket getUserTicket(String userTicketurl, String access_token, String code) {
        UserTicket userTicket = null;
        String requestUrl = userTicketurl.replace("ACCESS_TOKEN", access_token).replace("CODE", code);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        userTicket = new UserTicket();

        try {
            if (jsonObject.isNull("OpenId")) {
                // 返回码
                userTicket.setErrcode((Integer) getJsonObject(jsonObject, "errcode"));
                // 对返回码的文本描述内容
                userTicket.setErrmsg((String) getJsonObject(jsonObject, "errmsg"));
                if (userTicket.getErrcode() == 0) {
                    // 成员UserID
                    userTicket.setUserId((String) getJsonObject(jsonObject, "UserId"));
                    // 手机设备号
                    userTicket.setDeviceId((String) getJsonObject(jsonObject, "DeviceId"));
                    // 成员票据
                    userTicket.setUserTicket((String) getJsonObject(jsonObject, "user_ticket"));
                    // 有效时间
                    userTicket.setExpiresIn((Integer) getJsonObject(jsonObject, "expires_in"));
                    Date date = new Date();
                    // 过期时间
                    Long expiration = date.getTime() + 7200000;
                    userTicket.setExpiration(expiration);
                }
            } else {
                // 返回码
                userTicket.setErrcode((Integer) getJsonObject(jsonObject, "errcode"));
                // 对返回码的文本描述内容
                userTicket.setErrmsg((String) getJsonObject(jsonObject, "errmsg"));
                if (userTicket.getErrcode() == 0) {
                    // 非企业成员的标识，对当前企业唯一
                    userTicket.setOpenId((String) getJsonObject(jsonObject, "OpenId"));
                    // 手机设备号
                    userTicket.setDeviceId((String) getJsonObject(jsonObject, "DeviceId"));
                }
            }
        } catch (JSONException e) {
            userTicket = null;
            logger.error("Exception=" + e);
            logger.error(getConfigMsg.getMsgvalueAndPar("E0039"));
            try {
                String errmsg = jsonObject.getString("errmsg");
                logger.error(getConfigMsg.getMsgvalueAndPar("E0040") + errmsg);
            } catch (Exception e1) {
                logger.error(getConfigMsg.getMsgvalueAndPar("E0041"));
            }
        }
        return userTicket;
    }

    /**
     * 获取成员详细信息
     * 
     * @param userTicketurl
     * @param access_token
     * @param code
     * @return
     */
    public static UserDetail getUserDetail(String userDetailurl, String access_token, String user_ticket) {
        UserDetail userDetail = null;
        String requestUrl = userDetailurl.replace("ACCESS_TOKEN", access_token);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "POST", user_ticket);
        userDetail = new UserDetail();
        try {
            // 成员UserID
            userDetail.setUserid((String) getJsonObject(jsonObject, "userid"));
            // 成员姓名
            userDetail.setName((String) getJsonObject(jsonObject, "name"));
            // 成员所属部门
            userDetail.setDepartment((org.json.JSONArray) getJsonObject(jsonObject, "department"));
            // 职位信息
            userDetail.setPosition((String) getJsonObject(jsonObject, "position"));
            // 成员手机号
            userDetail.setMobile((String) getJsonObject(jsonObject, "mobile"));
            // 性别
            userDetail.setGender((String) getJsonObject(jsonObject, "gender"));
            // 成员邮箱
            userDetail.setEmail((String) getJsonObject(jsonObject, "email"));
            // 头像url
            userDetail.setAvatar((String) getJsonObject(jsonObject, "avatar"));
        } catch (JSONException e) {
            userDetail = null;
            logger.error("Exception=" + e);
            logger.error(getConfigMsg.getMsgvalueAndPar("E0042"));
            try {
                String errmsg = jsonObject.getString("errmsg");
                logger.error(getConfigMsg.getMsgvalueAndPar("E0043") + errmsg);
            } catch (Exception e1) {
                logger.error(getConfigMsg.getMsgvalueAndPar("E0044"));
            }
        }
        return userDetail;
    }

    /**
     * 取得工单列表
     * @param url
     * @param workList_ticket
     * @return
     * @throws JSONException 
     */
    public static WorkList getWorkList(String url, String workList_ticket) {
        WorkList workList = null;
        try {
            // 发起POST请求获取凭证
            JSONObject jsonObject = httpsRequest(url, "POST", workList_ticket);
            
            if (jsonObject != null) {
                String jsonObjectstr = jsonObject.toString();
                jsonObjectstr = jsonObjectstr.replaceAll("DATA", "workList");
                jsonObject = new JSONObject(jsonObjectstr);
            }
            workList = JSONUtil.fromJson(jsonObject.toString(), WorkList.class);
        } catch (Exception e) {
            logger.error("Exception=" + e);
            logger.error(getConfigMsg.getMsgvalueAndPar("E0032"));
        }
        return workList;
    }

    /**
     * 取得工单详细
     * @param url
     * @param workList_ticket
     * @return
     */
    public static WorkDetail getWorkDetail(String url, String workDetail_ticket) {
        WorkDetail workDetail = new WorkDetail();
        // 发起POST请求获取凭证
         JSONObject jsonObject = httpsRequest(url, "POST", workDetail_ticket);
        try {

            if (jsonObject != null) {
                JSONObject jsonobject= (JSONObject) jsonObject.get("DATA");
                workDetail.setTITLE((String) getJsonObject(jsonobject, "TITLE"));
                workDetail.setCONTENT((String) getJsonObject(jsonobject, "CONTENT"));
                workDetail.setRESULT((String) getJsonObject(jsonobject, "RESULT"));
                workDetail.setUSER_NAME((String) getJsonObject(jsonobject, "USER_NAME"));
                workDetail.setTEL((String) getJsonObject(jsonobject, "TEL"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workDetail;
    }

    public static Object getJsonFile(String fileName) {
        Object rtn = null;
        String MTSC_HOME = ConfUtil.getMTSC_HOME();
        // WechatAccesser/dat/db.json 为固定涉及路径，不能随意修改
        StringBuilder sb = new StringBuilder();
        sb.append("/WechatAccesser/dat/");
        sb.append(fileName);
        String path = MTSC_HOME + sb.toString();
        File fileInfo = new File(path);
        InputStreamReader fr = null;
        try {
            fr = new InputStreamReader(new FileInputStream(fileInfo), "UTF-8");

            BufferedReader reader = new BufferedReader(fr);

            String tempString = null;
            StringBuilder strBuilder = new StringBuilder();

            // 将文件内容读入strBuilder
            while ((tempString = reader.readLine()) != null) {
                strBuilder.append(tempString);
            }

            fr.close();
            reader.close();
            rtn = strBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            logger.error("JsonFile取得失败" + e.toString());
        } catch (FileNotFoundException e) {
            logger.error("JsonFile取得失败" + e.toString());
        } catch (IOException e) {
            logger.error("JsonFile取得失败" + e.toString());
        } catch (Exception e) {
            logger.error("JsonFile取得失败" + e.toString());
        }
        return rtn;
    }

    private static Object getJsonObject(JSONObject jsonObject, String key) throws JSONException {
        Object obj = null;
        if (!jsonObject.isNull(key)) {
            obj = jsonObject.get(key);
        }
        return obj;
    }
}
