package db;

import java.sql.*;
import java.util.*;
import java.io.InputStream;
import java.io.IOException;

/**
 * 
 * Title: 数据库连接 
 * Description: 数据库连接模块
 * 
 * @author 谢孟辉
 */

public class dbConn {
	public dbConn() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Statement conn() {
		try {
			// 加载配置文件
			Properties prop = new Properties();
			InputStream input = getClass().getClassLoader().getResourceAsStream("./db/config.properties");
			if (input == null) {
				throw new RuntimeException("找不到配置文件 config.properties");
			}
			prop.load(input);

			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 获取配置信息
			String url = "jdbc:mysql://localhost:3306/stuManagerDB?useSSL=false&serverTimezone=Asia/Shanghai";
			String user = "root";
			String pwd = prop.getProperty("db.password");

			// 建立连接
			Connection con = DriverManager.getConnection(url, user, pwd);
			return con.createStatement();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// 建议记录日志而不是返回null
			e.printStackTrace();
			throw new RuntimeException("数据库连接失败", (Throwable) e);
		}
	}


	// 查询数据库
	public ResultSet getRs(String sql) {
		try {
			Statement stat = conn();
			ResultSet rs = stat.executeQuery(sql);
			System.out.println(rs);
			return rs;
		} catch (SQLException ex) {
			System.err.println("------------" + ex.getMessage());
			return null;
		}
	}

	// 更新数据库
	public int getUpdate(String sql) {
		try {
			Statement stat = conn();
			int i = stat.executeUpdate(sql);
			return i;
		} catch (Exception ex) {
			System.out.println(">>>>>>>>" + ex.getMessage());
			return -1;
		}
	}

	private void jbInit() throws Exception {
		conn();
	}

}
