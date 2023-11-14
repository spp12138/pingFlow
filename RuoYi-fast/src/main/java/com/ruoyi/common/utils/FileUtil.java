package com.ruoyi.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * 
 * <p>文件名: FileUtil.java</p>  
 * <p>描述: 文件操作工具类</p>  
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
public class FileUtil {
String newline = "\r\n";//windows
    
    /**
     * 写入文件,末尾自动添加\r\n
     * utf-8  追加
     * @param path
     * @param str
     */
    public static void writeLog(String path, String str)
    {
        try
        {
            File file = new File(path);
            if(!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file); 
            StringBuffer sb = new StringBuffer();
            sb.append(str + "\r\n");
            out.write(sb.toString().getBytes("utf-8")); 
            out.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
    }
    
    /**
     * 写入文件,末尾自动添加\r\n
     * @param path
     * @param str
     */
    public static void writeLog(String path, String str, boolean is_append, String encode)
    {
        try
        {
            File file = new File(path);
            if(!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, is_append); //true表示追加 
            StringBuffer sb = new StringBuffer();
            sb.append(str + "\r\n");
            out.write(sb.toString().getBytes(encode));//
            out.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
    }
    /**
     * 整个文件以string放回，添加\r\n换行
     * @param path
     * @return
     */
    public static String readLogByString(String path)
    {
        StringBuffer sb=new StringBuffer();
        String tempstr=null;
        try {
            File file=new File(path);
            if(!file.exists())
                throw new FileNotFoundException();
            FileInputStream fis=new FileInputStream(file);
            @SuppressWarnings("resource")
			BufferedReader br=new BufferedReader(new InputStreamReader(fis, "utf-8"));
            while((tempstr=br.readLine())!=null) {
                sb.append(tempstr + "\r\n");
            }
        } catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        return sb.toString();
    }
    
    /**
     * 加入编码
     * 整个文件以string放回，添加\r\n换行
     * @param path
     * @return
     */
    public static String readLogByStringAndEncode(String path, String encode)
    {
        StringBuffer sb=new StringBuffer();
        String tempstr=null;
        try {
            File file=new File(path);
            if(!file.exists())
                throw new FileNotFoundException();
            FileInputStream fis=new FileInputStream(file);
            @SuppressWarnings("resource")
			BufferedReader br=new BufferedReader(new InputStreamReader(fis, encode));
            while((tempstr=br.readLine())!=null) {
                sb.append(tempstr + "\r\n");
            }
        } catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        return sb.toString();
    }
    
    /**
     * 按行读取文件，以list<String>的形式返回
     * @param path
     * @return
     */
    public static List<String> readLogByList(String path) {
        List<String> lines = new ArrayList<String>();
        String tempstr = null;
        try {
            File file = new File(path);
            if(!file.exists()) {
                throw new FileNotFoundException();
            }
            FileInputStream fis = new FileInputStream(file);
            @SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
            while((tempstr = br.readLine()) != null) {
                lines.add(tempstr.toString());
            }
        } catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        return lines;
    }
    
    
    /**
     * 创建目录
     * @param dir_path
     */
    public static void mkDir(String dir_path) {
        File myFolderPath = new File(dir_path);   
        try {   
            if (!myFolderPath.exists()) {   
               myFolderPath.mkdir();   
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
    }
    
    /**
     * 创建文件
     * @param file_path
     */
    public static void createNewFile(String file_path) {  
        File myFilePath = new File(file_path);   
        try {   
            if (!myFilePath.exists()) {   
                myFilePath.createNewFile();   
            } 
        }   
        catch (Exception e) {   
            e.printStackTrace();   
        }  
    }
    
    /**
     * 递归删除文件或者目录
     * @param file_path
     */
    public static void deleteEveryThing(String file_path) {
	    try{
	        File file=new File(file_path);
	        if(!file.exists()){
	            return ;
	        }
	        if(file.isFile()){
	            file.delete();
	        }else{
	            File[] files = file.listFiles();
	            for(int i=0;i<files.length;i++){
	                String root=files[i].getAbsolutePath();//得到子文件或文件夹的绝对路径
	                deleteEveryThing(root);
	            }
	            file.delete();
	        }
	    } catch(Exception e) {
	    }
    }
    
    /*
     * 得到一个文件夹下所有文件
     */
    public static List<String> getAllFileNameInFold(String fold_path) {
        List<String> file_paths = new ArrayList<String>();
        
        LinkedList<String> folderList = new LinkedList<String>();   
        folderList.add(fold_path);   
        while (folderList.size() > 0) {   
            File file = new File(folderList.peekLast());   
            folderList.removeLast();   
            File[] files = file.listFiles();   
            ArrayList<File> fileList = new ArrayList<File>();   
            for (int i = 0; i < files.length; i++) {   
                if (files[i].isDirectory()) {   
                    folderList.add(files[i].getPath());   
                } else {   
                    fileList.add(files[i]);   
                }   
            }   
            for (File f : fileList) {   
                file_paths.add(f.getAbsoluteFile().getPath());   
            }   
        }   
        return file_paths;
    }
    /**
     * 按行分页读取文件
     * @param path
     * @param pageNo
     * @param pageSize
     * @return
     * @throws IOException
     */
    public static List<String> readForPage(String path, int pageNo,int pageSize) throws IOException {  
    	File file = new File(path);
        if(!file.exists()) {
            throw new FileNotFoundException();
        }
		FileReader in = new FileReader(file);  
		LineNumberReader reader = new LineNumberReader(in);  
		String s = "";  
		List<String> resList = new ArrayList<String>();
		int startRow = (pageNo - 1) * pageSize + 1;
		int endRow = pageNo * pageSize;
		int lines = 0;  
//		if (startRow <= 0 || startRow > getTotalLines(path)) {  
//			System.out.println("不在文件的行数范围(1至总行数)之内。");  
//			System.exit(0);  
//		}  
		while (s != null) {  
		    lines++;  
		    s = reader.readLine();  
		    if(lines >= startRow && lines <= endRow) {  
		        resList.add(s);
		    }  
		    if(lines > endRow){
		    	break;
		    }
		}  
		reader.close();  
		in.close();  
		return resList;
    }
    
    /**
     * 获取文件行数,下标从0开始
     * @param file
     * @return
     * @throws IOException
     */
	public static int getTotalLines(String path) throws IOException {
		File file = new File(path);
        if(!file.exists()) {
            throw new FileNotFoundException();
        }
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		reader.skip(Long.MAX_VALUE);
		int lines = reader.getLineNumber();
		reader.close();
		return lines;
	}

    
}