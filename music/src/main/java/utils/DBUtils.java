package utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBUtils {
    @NotNull
    private static String driver = "com.mysql.cj.jdbc.Driver";
    @NotNull
    private static String url = "jdbc:mysql://127.0.0.1:3306/music?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    @NotNull
    private static String username = "root";
    @NotNull
    private static String password = "root";

    static {
//		Properties pro = new Properties();
//		ResourceBundle bundle=ResourceBundle.getBundle("jdbc");
//		InputStream is = DBUtils.class.getClassLoader().getResourceAsStream("resources/jdbc.properties");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//		try {
//			assert is != null;
//			pro.load(is);
//			driver = pro.getProperty("driver");
//			url = pro.getProperty("url");
//			username = pro.getProperty("username");
//			password = pro.getProperty("password");
//			initial = pro.getProperty("initial");
//			max = pro.getProperty("max");

//			driver = "com.mysql.jdbc.Driver";
//			driver = bundle.getString("driver");
//			url = bundle.getString("url");
//			username = bundle.getString("username");
//			password = bundle.getString("password");
//			initial = bundle.getString("initial");
//			max = bundle.getString("max");

        /*连接池*/
//			bds = new BasicDataSource();
//			//设置数据库连接信息
//			bds.setDriverClassName(driver);
//			bds.setUrl(url);
//			bds.setUsername(username);
//			bds.setPassword(password);
//			//管理参数
//			bds.setInitialSize(Integer.parseInt(initial));//初始化连接数量
//			bds.setMaxActive(Integer.parseInt(max));//最大连接数量

//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	Class.forName(driver);

        //获取连接
        public static Connection getConn () throws SQLException {
            return DriverManager.getConnection(url, username, password);//获取连接对象
        }

        //关闭资源
        public static void getClose (Connection conn, Statement stat, ResultSet rs){
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stat != null) {
                    stat.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);//归还之前打开自动提交
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
