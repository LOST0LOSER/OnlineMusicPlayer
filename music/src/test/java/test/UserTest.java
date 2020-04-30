package test;

import org.junit.Test;

import dao.UserDao;
import entity.User;

public class UserTest {
	@Test
	public void insert() {
		UserDao dao = new UserDao();
		User user = new User();
		user.setUsername("我是神麼");
		user.setPassword("775881089");
		user.setGender("男");
		user.setAge(24);
		user.setEmail("13476054425@163.com");
		dao.insertUser(user);
	}
	
	
	@Test
	public void select() {
		UserDao dao = new UserDao();
		User user = dao.selectUser("我是神麼");
		System.out.println(user);
	}
}
