package com.cc.pes.system.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.pes.system.simple.context.spring.SpringDispatcherContextHolder;
import com.cc.pes.system.simple.util.Util;
import com.cc.pes.system.user.dao.UserDao;
import com.cc.pes.system.user.entity.User;
import com.cc.pes.system.user.service.UserService;

//告诉spring，当Spring要创建UserServiceImpl的的实例时，bean的名字必须叫做"userService"，这样当Action需要使用UserServiceImpl的的实例时,就可以由Spring创建好的"userService"，然后注入给Action。
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao<User, Integer> userDao;
	
	@Override
	public User findUserById(int id) throws Exception {
		List<User> userList = userDao.findByHQL("from User where id = ?", id);
		if (userDao.getCount("User") == 0) {
			User userAdmin = new User();
			userAdmin.setName("admin");
			userAdmin.setPwd(Util.string2MD5("admin"));
			userAdmin.setType("admin");
			userAdmin.save();
		}
		if (userList.size() != 1){
			return null;
		}
		return (User)userList.get(0);
	}

	@Override
	public Boolean login(User user, HttpSession session) throws Exception {
		User userInfo =  this.findUserById(user.getId());
		if (null == userInfo) {
			return false;
		}
		if (Util.string2MD5(user.getPwd()).equals(userInfo.getPwd())) {
			session.setAttribute("UserId", userInfo.getId());
			session.setAttribute("UserName", userInfo.getName());
			session.setAttribute("UserType", userInfo.getType());
			session.setAttribute("UserClazz", userInfo.getClazz());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void loginOut(HttpSession session) throws Exception {
		session.invalidate();
	}

	@Override
	public List<User> getTeacherPage(int begin, int length) throws Exception {
		return userDao.findSomeByHql("from User where type = ?", begin, length, "teacher");
	}

	@Override
	public boolean save(User user) throws Exception {
		User userinfor = userDao.get(user.getId());
		userinfor.setName(user.getName());
		userinfor.setClazz(user.getClazz());
		userDao.update(userinfor);
		return true;
	}

	@Override
	public boolean setPwd(Integer id) {
		User userinfor = userDao.get(id);

		userinfor.setPwd(Util.string2MD5("0000"));
		userDao.update(userinfor);
//		userDao.commitByHQL("update User set pwd = '" + Util.string2MD5("0000") + "' where id=?", userinfor.getId());
		
		return true;
	}

	@Override
	public boolean deleteUser(Integer id) {
		userDao.delete(userDao.get(id));
		return true;
	}

	@Override
	public int addUser(User user) {
		user.setPwd(Util.string2MD5("0000"));
		return userDao.save(user);
	}
	
	@Override
	public List<User> getStudentPage(Integer begin, Integer length, String clazz) throws Exception {

		return userDao.findSomeByHql("from User where type = ? and clazz = ?", begin, length, "student", clazz);
	}

	@Override
	public String getUserNameById(Integer id) throws Exception {
		return userDao.get(id).getName();
	}

	@Override
	public int updateUserPwd(Map<String, String> pwd, int userid) {
		 User user = userDao.get(userid);
		 if (!user.getPwd().equals(Util.string2MD5(pwd.get("oldPwd")))) {
			return 1;
		}
		 user.setPwd(Util.string2MD5(pwd.get("newPwd")));
		 return 2;
	}
	

	
}
