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
 * 数据库操作工具类，封装数据库操作的常用方法
 * 
 * @author cgw
 * 
 */
public class DBUtil {

    /* 连接驱动 */
    private static final String DRIVERCLASS;
    
    /* 数据库地址 */
    private static final String URL;
    
    /* 数据库用户 */
    private static final String USER;
    
    /* 用户密码 */
    private static final String PASSWORD;

    // 对常量进行初始化
    static {
	// 读取配置文件
	Properties p = new Properties();
	InputStream inStream = null;
	inStream = DBUtil.class.getClassLoader().getResourceAsStream(
		"config.properties");
	try {
	    p.load(inStream);// 将配置文件中的数据加载到p中
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

    // 关闭数据库连接资源的方法
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
     * 执行insert,update,delete等Sql语句 考虑到事务，把Connection对象有业务层来管理
     */
    public static int carriedOut(Connection conn, String sql, Object... params) {
	int a = 0;

	PreparedStatement pst = null;
	try {
	    pst = conn.prepareStatement(sql);
	    System.out.println(sql);
	    // 给sql语句绑定参数
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
     * 查询： 返回一行数据
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
		// 将rs以列名称为map键，值为map值
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
     * 查询： 返回多行数据，以List封装多行
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
	    // 将所有列名称存储到数组中
	    String[] columnLabels = new String[columnCount];
	    for (int i = 1; i <= columnCount; i++) {
		columnLabels[i - 1] = rsmd.getColumnLabel(i);
	    }

	    while (rs.next()) {
		row = new HashMap<String, Object>();
		// 将rs以列名称为map键，值为map值
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

    // 只适合使用于单表查询
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
		// 将rs以列名称为map键，值为map值
		try {
		    bean = clz.newInstance();// 使用反射创建对象，会调用clz类的默认的构造方法
		    ResultSetMetaData rsmd = rs.getMetaData();
		    int columnCount = rsmd.getColumnCount();
		    Field field = null;
		    for (int i = 0; i < columnCount; i++) {
			field = clz
				.getDeclaredField(rsmd.getColumnLabel(i + 1));
			field.setAccessible(true);// 沒有這句話，private屬性不能直接訪問
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

    // 只适合使用于单表查询
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
		// 将rs以列名称为map键，值为map值
		try {
		    bean = clz.newInstance();// 使用反射创建对象，会调用clz类的默认的构造方法
		    ResultSetMetaData rsmd = rs.getMetaData();
		    int columnCount = rsmd.getColumnCount();
		    Field field = null;
		    for (int i = 0; i < columnCount; i++) {
			field = clz
				.getDeclaredField(rsmd.getColumnLabel(i + 1));
			field.setAccessible(true);// 沒有這句話，private屬性不能直接訪問
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
