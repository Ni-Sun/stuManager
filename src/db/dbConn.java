package db;

import java.sql.*;
import java.util.*;
import java.io.InputStream;
import java.io.IOException;

/**
 * 
 * Title: ���ݿ����� 
 * Description: ���ݿ�����ģ��
 * 
 * @author л�ϻ�
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
			// ���������ļ�
			Properties prop = new Properties();
			InputStream input = getClass().getClassLoader().getResourceAsStream("./db/config.properties");
			if (input == null) {
				throw new RuntimeException("�Ҳ��������ļ� config.properties");
			}
			prop.load(input);

			// ��������
			Class.forName("com.mysql.cj.jdbc.Driver");

			// ��ȡ������Ϣ
			String url = "jdbc:mysql://localhost:3306/stuManagerDB?useSSL=false&serverTimezone=Asia/Shanghai";
			String user = "root";
			String pwd = prop.getProperty("db.password");

			// ��������
			Connection con = DriverManager.getConnection(url, user, pwd);
			return con.createStatement();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// �����¼��־�����Ƿ���null
			e.printStackTrace();
			throw new RuntimeException("���ݿ�����ʧ��", (Throwable) e);
		}
	}


	// ��ѯ���ݿ�
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

	// �������ݿ�
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
