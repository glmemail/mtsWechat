/**
 * 
 */
package com.nrib.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.nrib.common.CommonUtil;
import com.nrib.common.ConfUtil;
import com.nrib.common.Constant;
import com.nrib.wechat.MediaFile;
import com.nrib.wechat.Token;



/**
 * @author zhaoyubo
 *
 * 2017/07/25
 */
@WebServlet("/UploadServlet")
public class UploadFile extends HttpServlet {
    
    /**
     * 工单上传
     */
    private static final long serialVersionUID = -3601518694748811834L;

    private static Logger logger = Logger.getLogger(UploadFile.class);
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 10;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 10; // 4MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 10; // 5MB
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    
    /**
     * 上传数据及保存文件
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String sid = session.getId();
        String userId = (String) session.getAttribute(sid);
        session.removeAttribute("message");
        // 读取属性文件
        ConfUtil cfUtl = ConfUtil.getInstance();
        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 设置附件最大值
        upload.setFileSizeMax(MAX_FILE_SIZE);
        // 设置最大请求值（表单数据包括file）
        upload.setSizeMax(MAX_REQUEST_SIZE);
        // 设置编码
        upload.setHeaderEncoding("UTF-8"); 
        try {
            // 解析请求的内容
            List<FileItem> formItems = upload.parseRequest(request);
            Map<String,String> map = new HashMap<String,String>();
            //拼接多个附件名用
            StringBuffer attachment = new StringBuffer();
            //拼接分隔符
            String Delimiter = "|";
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 不在表单中的字段
                    if (item.isFormField()) {
                        String value = new String(item.getString().getBytes("ISO-8859-1"), "UTF-8");
                        String key = item.getFieldName();
                        map.put(key, value);
                    }
                }
                Token token = (Token) session.getAttribute("token");
                if (token == null) {
                    String redirect = request.getContextPath() + "/error/406.jsp";
                    // 跳转到页面
                    request.setAttribute("message", "token为空！");
                    logger.error(cfUtl.getMsgvalueAndPar("E0032", "token为空"));
                    response.sendRedirect(redirect);
                    return;
                }
                String serverIds = map.get("serverIds");
                logger.debug("token : " + token);
                String[] media_ids = serverIds.split(",");
                String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
                //创建path
                String uploadPath = Constant.UPLOAD_PATH + File.separator;
                //上传用
                uploadPath = uploadPath + time;
                String ssmpath = "\\";
                //给SSM用
                ssmpath = ssmpath + time;
                for (int i = 0; i < media_ids.length; i++) {
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }
                    MediaFile t = downloadMedia(Constant.GET_MEDIA_URL, token.getAccessToken(), media_ids[i]);
                    String name = t.getFilename();
                    name = name.replace("\\", "");
                    name = name.replace("/", "");
                    name = name.replace(":", "");
                    name = name.replace("?", "");
                    name = name.replace("\"", "");
                    name = name.replace(">", "");
                    name = name.replace("<", "");
                    name = name.replace("|", "");
                    String filePath = uploadPath + File.separator;
                    // 文件上传
                    byte2File(t.getFiledata(), filePath, name);
                    // 给ssm用的
                    attachment.append("\\").append(ssmpath).append("\\").append("\\").append(name).append(Delimiter);
                    // 保存文件到硬盘
                    logger.info(cfUtl.getMsgvalueAndPar("I0010", filePath + name));
                }
                if (attachment.length() != 0) {
                    attachment = attachment.deleteCharAt(attachment.lastIndexOf(Delimiter));
                }
                map.put("attachment", attachment.toString());
                //添加创建者
                map.put("CreateUser", userId);
                map.put("FROM", cfUtl.getConfigvalueAndPar("FROM"));
                String systemname = cfUtl.getConfigvalueAndPar("SYSTEMNAME");
                String title = map.get("Title");
                String content = map.get("Content");
                StringBuilder createworkurl = new StringBuilder();
                createworkurl.append(cfUtl.getConfigvalueAndPar("SSMIP"));
                createworkurl.append(Constant.CREATE_WORK_URL);
                boolean upload_flg = this.uploadAddEvent(createworkurl.toString(),
                        systemname, title, content, attachment.toString(), userId);
                logger.info(cfUtl.getMsgvalueAndPar("I0009", map.toString()));
                if (upload_flg) {
                    logger.info(cfUtl.getMsgvalueAndPar("I0019"));
                    request.setAttribute("message", "工单起票成功!");
                } else {
                    logger.info(cfUtl.getMsgvalueAndPar("I0020"));
                    request.setAttribute("message", "工单起票失败!");
                }
            } else {
            	request.setAttribute("message", "表单数据异常");
            }
        } catch (Exception ex) {
            request.setAttribute("message", "上传工单发生错误!");
            logger.error(cfUtl.getMsgvalueAndPar("E0003", ex.toString()));
        }
        // 跳转到 message.jsp
        getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
    }

    /**
     * 调用SSM接口上传工单
     * @param url
     * @param systemname
     * @param title
     * @param content
     * @param attachment
     * @param weChatId
     * @return
     * @throws JSONException
     */
    private boolean uploadAddEvent(String url, String systemname,
            String title, String content, String attachment, String weChatId) throws JSONException {
        boolean rtn = false;
        ConfUtil cfUtl = ConfUtil.getInstance();
        // 取得参数
        StringBuilder pam = new StringBuilder();
        pam.append("{");
        pam.append("\"").append("ESBREQ").append("\"").append(":");
        pam.append("{");
        pam.append("\"").append("DATA").append("\"").append(":");
        pam.append("{");
        pam.append("\"").append("SystemName").append("\"").append(":");
        pam.append("\"").append(systemname).append("\"").append(",");
        pam.append("\"").append("Title").append("\"").append(":");
        pam.append("\"").append(title).append("\"").append(",");
        pam.append("\"").append("CONTENT").append("\"").append(":");
        pam.append("\"").append(content).append("\"").append(",");
        pam.append("\"").append("Attachment").append("\"").append(":");
        pam.append("\"").append(attachment).append("\"").append(",");
        pam.append("\"").append("WeChatId").append("\"").append(":");
        pam.append("\"").append(weChatId).append("\"");
        pam.append("}");
        pam.append("}");
        pam.append("}");
        // 发送https请求
        JSONObject jsonObject = CommonUtil.httpsRequest(url.toString(), "POST", pam.toString());
        logger.info(cfUtl.getMsgvalueAndPar("I0028", jsonObject.toString()));
        if (jsonObject != null) {
            if (!jsonObject.isNull("ESBRESP")) {
                JSONObject esbresp = (JSONObject) jsonObject.get("ESBRESP");
                if (!esbresp.isNull("HEADER")) {
                    JSONObject header = (JSONObject) esbresp.get("HEADER");
                    String msg = (String) header.get("MSG");
                    if ("OK".equals(msg)) {
                        rtn = true;
                    }
                }
            }
        }
        return rtn;
    }

    /**  
     * 获取媒体文件  
     *   
     * @param accessToken  
     *            接口访问凭证  
     * @param media_id  
     *            媒体文件id  
     * @throws IOException 
     * */  
    private MediaFile downloadMedia(String url, String token, String media_id) throws IOException {  
        MediaFile t = new MediaFile();
        url = url.replace("ACCESS_TOKEN", token);
        url = url.replace("MEDIA_ID", media_id);
        
        HttpURLConnection conn = null;  
        try {  
            URL requestUrl = new URL(url);  
            conn = (HttpURLConnection) requestUrl.openConnection();  
            conn.setDoInput(true);  
            conn.setRequestMethod("GET");  
            conn.setConnectTimeout(30000);  
            conn.setReadTimeout(30000);  
            BufferedInputStream bis = new BufferedInputStream(  
                    conn.getInputStream());  
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();    
            byte[] buff = new byte[100];    
            int rc = 0;    
            while ((rc = bis.read(buff, 0, 100)) > 0) {    
                swapStream.write(buff, 0, rc);    
            }    
            byte[] filebyte = swapStream.toByteArray();
            String disposition = conn.getHeaderField("Content-disposition");
            String[] strs =disposition.split("\"");
            String filename = "";
            for (int i = 0;i < strs.length;i++){
                if (strs[i].indexOf("filename") != -1) {
                    filename = strs[i+1];
                    break;
                }
            }
            t.setFilename(filename);
            t.setFiledata(filebyte);
            return t;
        } finally {  
            if(conn != null){
                conn.disconnect();
            }
        }  
    }  
    
    
    private void byte2File(byte[] buf, String filePath, String fileName) throws Exception {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } finally {
            if (bos != null) {
                bos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
}
