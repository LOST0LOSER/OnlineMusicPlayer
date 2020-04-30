package dao;

import entity.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    /**
     * 依据用户名查询，如果找不到，返回null,
     * 否则返回一个User对象（包含了用户的所有信息）
     */
    public static int userCounter = 0;

    @Nullable
    public User selectUser(String username) {
        User user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConn();
            ps = conn.prepareStatement("select*from user where username=?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setAge(rs.getInt("age"));
                    user.setGender(rs.getString("gender"));
                    user.setEmail(rs.getString("email"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtils.getClose(conn, ps, rs);
        }
        return user;
    }


    /**
     * 注册
     */
    public void insertUser(@NotNull User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtils.getConn();
            ps = conn.prepareStatement("insert into user values(?,?,?,?,?,?)");
            ps.setInt(1, userCounter);
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getGender());
            ps.setInt(5, user.getAge());
            ps.setString(6, user.getEmail());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            userCounter++;
            DBUtils.getClose(conn, ps, null);
        }
    }


	public void toggleFavorite(int userID, int musicID) {
		Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtils.getConn();
            ps = conn.prepareStatement("insert into favorites values(?,?)");
            ps.setInt(1, userID);
            ps.setInt(2, musicID);
            ps.executeUpdate();
        }catch(java.sql.SQLIntegrityConstraintViolationException e) {
        	String deleteSql = "delete from favorites where userID =" + userID + " and musicID =" + musicID;
        	try {
				ps = conn.prepareStatement(deleteSql);
				ps.executeUpdate();
			} catch (SQLException eDelete) {
				eDelete.printStackTrace();
			}
        }catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            DBUtils.getClose(conn, ps, null);
        }
	}
}
