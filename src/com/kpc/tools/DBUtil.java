package com.kpc.tools;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * ���ݿ���������࣬��װ���ݿ�����ĳ��÷���
 * 
 * @author cgw
 * 
 */
public class DBUtil {

    /* �������� */
    private static final String DRIVERCLASS;
    
    /* ���ݿ��ַ */
    private static final String URL;
    
    /* ���ݿ��û� */
    private static final String USER;
    
    /* �û����� */
    private static final String PASSWORD;

    // �Գ������г�ʼ��
    static {
	// ��ȡ�����ļ�
	Properties p = new Properties();
	InputStream inStream = null;
	inStream = DBUtil.class.getClassLoader().getResourceAsStream(
		"config.properties");
	try {
	    p.load(inStream);// �������ļ��е����ݼ��ص�p��
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (inStream != null) {
		try {
		    inStream.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	DRIVERCLASS = p.getProperty("driverClass");
	URL = p.getProperty("url");
	USER = p.getProperty("user");
	PASSWORD = p.getProperty("pwd");
    }

    public static Connection getConnection() {
	Connection conn = null;

	try {
	    Class.forName(DRIVERCLASS);
	    conn = DriverManager.getConnection(URL, USER, PASSWORD);
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	}

	return conn;
    }

    // �ر����ݿ�������Դ�ķ���
    public static void closeConnection(Connection conn) {
	if (conn != null) {
	    try {
		conn.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void closeStatement(Statement st) {
	if (st != null) {
	    try {
		st.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void closeResultset(ResultSet rs) {
	if (rs != null) {
	    try {
		rs.close();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * ִ��insert,update,delete��Sql��� ���ǵ����񣬰�Connection������ҵ���������
     */
    public static int carriedOut(Connection conn, String sql, Object... params) {
	int a = 0;

	PreparedStatement pst = null;
	try {
	    pst = conn.prepareStatement(sql);
	    System.out.println(sql);
	    // ��sql���󶨲���
	    for (int i = 0; i < params.length; i++) {
		pst.setObject(i + 1, params[i]);
		System.out.print(params[i] + "\r");
	    }

	    a = pst.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    closeStatement(pst);
	}

	return a;
    }

    /**
     * ��ѯ�� ����һ������
     */
    public static Map<String, Object> queryForRow(Connection conn, String sql,
	    Object... params) {
	Map<String, Object> row = new HashMap<String, Object>();

	PreparedStatement pst = null;
	ResultSet rs = null;
	try {
	    pst = conn.prepareStatement(sql);
	    System.out.println(sql);
	    for (int i = 0; i < params.length; i++) {
		pst.setObject(i + 1, params[i]);
		System.out.print(params[i] + ", ");
	    }
	    System.out.println();
	    rs = pst.executeQuery();
	    if (rs.next()) {
		// ��rs��������Ϊmap����ֵΪmapֵ
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
		    row.put(rsmd.getColumnLabel(i), rs.getObject(i));
		}
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    closeResultset(rs);
	    closeStatement(pst);
	}

	return row;
    }

    /**
     * ��ѯ�� ���ض������ݣ���List��װ����
     */
    public static List<Map<String, Object>> queryForRows(Connection conn,
	    String sql, Object... params) {
	List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

	PreparedStatement pst = null;
	ResultSet rs = null;
	try {
	    pst = conn.prepareStatement(sql);
	    System.out.println(sql);
	    for (int i = 0; i < params.length; i++) {
		pst.setObject(i + 1, params[i]);
		System.out.print(params[i] + ", ");
	    }
	    System.out.println();
	    rs = pst.executeQuery();
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columnCount = rsmd.getColumnCount();
	    Map<String, Object> row = null;
	    // �����������ƴ洢��������
	    String[] columnLabels = new String[columnCount];
	    for (int i = 1; i <= columnCount; i++) {
		columnLabels[i - 1] = rsmd.getColumnLabel(i);
	    }

	    while (rs.next()) {
		row = new HashMap<String, Object>();
		// ��rs��������Ϊmap����ֵΪmapֵ
		for (int i = 1; i <= columnCount; i++) {
		    row.put(columnLabels[i - 1], rs.getObject(i));
		}

		rows.add(row);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    closeResultset(rs);
	    closeStatement(pst);
	}

	return rows;
    }

    // ֻ�ʺ�ʹ���ڵ����ѯ
    public static <T> T queryForBean(Connection conn, Class<T> clz, String sql,
	    Object... params) {
	T bean = null;

	PreparedStatement pst = null;
	ResultSet rs = null;
	try {
	    pst = conn.prepareStatement(sql);
	    for (int i = 0; i < params.length; i++) {
		pst.setObject(i + 1, params[i]);
	    }
	    rs = pst.executeQuery();
	    if (rs.next()) {
		// ��rs��������Ϊmap����ֵΪmapֵ
		try {
		    bean = clz.newInstance();// ʹ�÷��䴴�����󣬻����clz���Ĭ�ϵĹ��췽��
		    ResultSetMetaData rsmd = rs.getMetaData();
		    int columnCount = rsmd.getColumnCount();
		    Field field = null;
		    for (int i = 0; i < columnCount; i++) {
			field = clz
				.getDeclaredField(rsmd.getColumnLabel(i + 1));
			field.setAccessible(true);// �]���@��Ԓ��private���Բ���ֱ���L��
			System.out.println(field.getName());
			field.set(bean, rs.getObject(i + 1));
		    }
		} catch (InstantiationException e) {
		    e.printStackTrace();
		} catch (IllegalAccessException e) {
		    e.printStackTrace();
		} catch (NoSuchFieldException e) {
		    e.printStackTrace();
		} catch (SecurityException e) {
		    e.printStackTrace();
		} catch (IllegalArgumentException e) {
		    e.printStackTrace();
		}

	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    closeResultset(rs);
	    closeStatement(pst);
	}

	return bean;
    }

    // ֻ�ʺ�ʹ���ڵ����ѯ
    public static <T> List<T> queryForBeans(Connection conn, Class<T> clz,
	    String sql, Object... params) {
	List<T> beans = new ArrayList<T>();

	PreparedStatement pst = null;
	ResultSet rs = null;
	try {
	    pst = conn.prepareStatement(sql);
	    for (int i = 0; i < params.length; i++) {
		pst.setObject(i + 1, params[i]);
	    }
	    rs = pst.executeQuery();
	    T bean = null;
	    while (rs.next()) {
		// ��rs��������Ϊmap����ֵΪmapֵ
		try {
		    bean = clz.newInstance();// ʹ�÷��䴴�����󣬻����clz���Ĭ�ϵĹ��췽��
		    ResultSetMetaData rsmd = rs.getMetaData();
		    int columnCount = rsmd.getColumnCount();
		    Field field = null;
		    for (int i = 0; i < columnCount; i++) {
			field = clz
				.getDeclaredField(rsmd.getColumnLabel(i + 1));
			field.setAccessible(true);// �]���@��Ԓ��private���Բ���ֱ���L��
			field.set(bean, rs.getObject(i + 1));
		    }

		    beans.add(bean);
		} catch (InstantiationException e) {
		    e.printStackTrace();
		} catch (IllegalAccessException e) {
		    e.printStackTrace();
		} catch (NoSuchFieldException e) {
		    e.printStackTrace();
		} catch (SecurityException e) {
		    e.printStackTrace();
		} catch (IllegalArgumentException e) {
		    e.printStackTrace();
		}

	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    closeResultset(rs);
	    closeStatement(pst);
	}

	return beans;
    }
}
