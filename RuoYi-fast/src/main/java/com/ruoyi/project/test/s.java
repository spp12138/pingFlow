package com.ruoyi.project.test;

import com.ruoyi.common.utils.JDBCUtil;

public class s {
	public static void main(String[] args) throws Exception {
		
		JDBCUtil db = new JDBCUtil("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@3.1.11.35/almdb", "ERMS", "ERMS");
		Object [] a = {};
//		List<Object> excuteQuery = db.excuteQuery("CALL ETL_LDMS_REPORT_DAILY('?')",a);
//		for (Object object : excuteQuery) {
//			System.out.println(object);
//		}
		Object excuteQuery2 = db.proc2("{CALL proc_erms_dat_dcf('20191105',?)}", a, 1, java.sql.Types.INTEGER);
		if(excuteQuery2.equals(0)){
			System.out.println("执行成功" + "返回值" + excuteQuery2);
		}
		System.out.println(excuteQuery2);
		
	}
	
	
	/**
	 * 
DB	driverClass/jdbcUrl/jdbc download	Maven dependency

*********************  Oracle  *******************

oracle.jdbc.driver.OracleDriver	

<dependency>
<groupId>com.Oracle</groupId>
<artifactId>ojdbc14</artifactId>
<version>10.2.0.4.0</version>
</dependency>

thin模式：
jdbc:oracle:thin:@host:port1521default:dbName
*************************************************** 
*
*********************  MySQL  *********************  
com.mysql.jdbc.Driver
or
org.gjt.mm.mysql.Driver	
<dependency>
<groupId>mysql</groupId>
<artifactId>mysql-connector-java</artifactId>
<version>5.1.13</version>
</dependency>
jdbc:mysql://host:port3306default/dbName?user=userName&password= password&useUnicode=true&characterEncoding=gb2312
*************************************************** 
*
*********************  Sybase  *********************  
com.sybase.jdbc3.jdbc.SybDriver
or
com.sysbase.jdbc.SybDriver
jdbc:sybase:Tds:host:port5007default/dbName
*************************************************** 
*
*********************  DB2  	
com.ibm.db2.jdbc.NET.DB2Driver （db2java.zip）
or
com.ibm.db2.jcc.DB2Driver （db2jcc.jar）
or
com.ibm.db2.jdbc.app.DB2Driver
使用db2jcc.jar：
<dependency>
<groupId>db2jcc</groupId>
<artifactId>db2jcc</artifactId>
<version>9.0</version>
</dependency>
<dependency>
<groupId>db2jcc_license_cu</groupId>
<artifactId>db2jcc_license_cu</artifactId>
<version>9.0</version>
</dependency>
注：
Maven仓库中是没有该驱动的，需先手动添加jar到仓库中。
 	jdbc:db2://host:port5000default/dbName
 	关于DB2的JDBC驱动
Informix  	com.informix.jdbc.IfxDriver	 
 	jdbc:informix-sqli://host:port1533default/
dbName:informixserver=server;user=username
;password=password
*************************************************** 
*
*********************  SQL Server	
2005版本及以后：
com.microsoft.sqlserver.jdbc.SQLServerDriver
or
2000版本：
com.microsoft.jdbc.sqlserver.SQLServerDriver
<dependency>
<groupId>net.sourceforge.jtds</groupId>
<artifactId>jtds</artifactId>
<version>1.2.4</version>
</dependency>
 	jdbc:sqlserver://host:port1433default;
databasename=dbName
Access  	sun.jdbc.odbc.JdbcOdbcDriver	 
 	jdbc:odbc:dataSourceName
PostgreSQL	org.postgresql.Driver	
<dependency>
<groupId>postgresql</groupId>
<artifactId>postgresql</artifactId>
<version>9.1-901.jdbc3</version>
</dependency>
or
<dependency>
<groupId>postgresql</groupId>
<artifactId>postgresql</artifactId>
<version>9.1-901.jdbc4</version>
</dependency>
version可选值:
<version>8.4-702.jdbc3</version>
注：
jdbc3的适用于JDK1.5及以下；
jdbc4的适用于JDK1.6及以上。
 	jdbc:postgresql://host:port5432default/dbName
 	http://jdbc.postgresql.org/download.html
SQLite	org.sqlite.JDBC	
<dependency>
<groupId>org.xerial</groupId>
<artifactId>sqlite-jdbc</artifactId>
<version>3.7.2</version>
</dependency> 
 	jdbc:sqlite:dbPath.db
 	http://www.xerial.org/maven/repository/
artifact/org/xerial/sqlite-jdbc/3.7.2/

*************************************************** 
*
*********************  Derby	
org.apache.derby.jdbc.ClientDriver
or
org.apache.derby.jdbc.EmbeddedDriver
<dependency>
<groupId>org.apache.derby</groupId>
<artifactId>derby</artifactId>
<version>10.9.1.0</version>
</dependency>
Client模式：
jdbc:derby://localhost:port1527default/dbName
*************************************************** 
*
*********************  HSQLDB	
org.hsqldb.jdbcDriver
or
org.hsqldb.jdbc.JDBCDriver
2.0以前版本：
<dependency>
<groupId>hsqldb</groupId>
<artifactId>hsqldb</artifactId>
<version>1.8.0.7</version>
</dependency>
2.0及以后版本：
<dependency>
<groupId>org.hsqldb</groupId>
<artifactId>hsqldb</artifactId>
<version>2.2.8</version>
<!– JDK1.6环境下无需指定classifier –>
<classifier>jdk5</classifier>
</dependency>
mem模式：
jdbc:hsqldb:mem:dbName
file模式：
jdbc:hsqldb:file:dbPath
res模式：
jdbc:hsqldb:res:org.my.path.resdb
连接到local hsql Server：
jdbc:hsqldb:hsql://localhost/dbName
连接到local http Server：
jdbc:hsqldb:http://localhost/dbName
*************************************************** 

*********************  H2	org.h2.Driver	
<dependency>
<groupId>com.h2database</groupId>
<artifactId>h2</artifactId>
<version>1.3.168</version>
</dependency>
mem模式：
jdbc:h2:mem:dbName
连接到local tcp Server：
jdbc:h2:tcp://localhost/~/dbName




	 */
	
	
}
