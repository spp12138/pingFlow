package com.ruoyi.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <p>文件名: HttpRequest.java</p>  
 * <p>描述: Http工具类</p>  
 * <p>版权: Copyright (c) 2018</p>  
 * <p>公司: 信雅达 Sunyard</p>  
 * @author 桑一平  
 * @date 2019年8月20日
 * @version 1.0  
 * 创建时间:  2019年8月20日14:43:40
 *   
 * 修改历史:   
 * 时    间                          作    者                版    本             描    述   
 * ----------- -------- -------- -------------------------------------- 
 * 2019年8月20日      桑一平                1.0     新建
 *
 */
public class HttpUtil {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
	@SuppressWarnings("unused") 
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url +(StringUtils.isBlank(param) ? "" : "?" + param);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
            return "error";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
	@SuppressWarnings("unused") 
    public static String sendPost(String url, String param,boolean ... isXml ) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("referer", "https://xdclass.net/");
            conn.setRequestProperty("origin", "https://xdclass.net");
            conn.setRequestProperty("host", "api-v2.xdclass.net");
            conn.setRequestProperty("Content-Length", "75");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            conn.setRequestProperty("Sec-Fetch-Dest", "empty");
            conn.setRequestProperty("Sec-Fetch-Mode", "cors");
            conn.setRequestProperty("Sec-Fetch-Site", "same-site");
            conn.setRequestProperty("DNT", "1");
            if(isXml != null){
            	conn.setRequestProperty("Content-Type", "application/xml");
            }else{
            	conn.setRequestProperty("Content-Type", "application/json");
            }
            conn.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:105.0) Gecko/20100101 Firefox/105.0");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }
            
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
            return "error";
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
	
	/**
	 * 是否为测试环境
	 * @return
	 */
	public static boolean isTest() {
		String value = PropsUtil.getString("PUBLIC.isTest");
		if(StringUtils.isEmpty(value)){
			return false;
		}
		if("true".equals(value)){
			return true;
		}
		return false;
	}
}