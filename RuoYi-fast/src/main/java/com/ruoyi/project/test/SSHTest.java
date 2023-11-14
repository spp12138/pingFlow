package com.ruoyi.project.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSHTest {
	public static void main(String[] args) throws Exception {
//		ssh();
//		Scanner scan = new Scanner(System.in);
//		while (true){
//			Process process =  Runtime.getRuntime().exec(scan.next());
//			BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
//			BufferedReader errorStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream(),"GBK"));
//			
//			String line = null;
//			while ((line = inputStreamReader.readLine()) != null) {
//				System.out.println(line);
//			}
//			while ((line = errorStreamReader.readLine()) != null) {
//				System.out.println(line);
////            errLog.append(line);
//			}
//		}
//        std.start();
//        err.start();
//        std.join();
//        err.join();
//        process.waitFor();

//        return errLog.toString();
		
		
	}

	@SuppressWarnings({ "resource", "unused" })
	private static void ssh() {
		String hostname = "3.1.11.34";
		String username = "weblogic";
		String password = "weblogic1";

		try {
			Connection conn = new Connection(hostname);
			conn.connect();
			// 进行身份认证
			boolean isAuthenticated = conn.authenticateWithPassword(username,password);
			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");
			// 开启一个Session
			Session sess = conn.openSession();
			// 执行具体命令
			sess.execCommand("cd SangYp;ls");
			// 获取返回输出
			InputStream stdout = new StreamGobbler(sess.getStdout());
			// 返回错误输出
			InputStream stderr = new StreamGobbler(sess.getStderr());
			BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout));
			BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stderr));

			System.out.println("Here is the output from stdout:");
			while (true) {
				String line = stdoutReader.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}

			System.out.println("Here is the output from stderr:");
			while (true) {
				String line = stderrReader.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}
			// 关闭Session
			sess.close();
			// 关闭Connection
			conn.close();
		} catch (IOException e) {
			e.printStackTrace(System.err);
			System.exit(2);
		}
	}
}
