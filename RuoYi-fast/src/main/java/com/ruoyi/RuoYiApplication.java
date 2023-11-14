package com.ruoyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.**.mapper")//加上你项目的dao或service所在文件位置即可
public class RuoYiApplication{
	
    public static void main(String[] args){
    	System.setProperty("spring.devtools.restart.enabled", "false");
    	SpringApplication.run(RuoYiApplication.class, args);
    	System.out.println("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ  \n" );
    }
}