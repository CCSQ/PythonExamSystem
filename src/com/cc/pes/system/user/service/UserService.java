package com.cc.pes.system.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.cc.pes.system.topic.entity.Top;
import com.cc.pes.system.user.entity.User;

public interface UserService {
	public User findUserById(int id) throws Exception;
	
	public Boolean login(User user, HttpSession session) throws Exception;
	
	public void loginOut(HttpSession session) throws Exception;
	
	public List<User> getTeacherPage(int begin, int length) throws Exception;

	public boolean save(User user)  throws Exception;

	public boolean setPwd(Integer id);

	public boolean deleteUser(Integer id);

	public int addUser(User user);

	public List<User> getStudentPage(Integer begin, Integer length, String clazz) throws Exception;
	
	public String getUserNameById(Integer id) throws Exception;

	public int updateUserPwd(Map<String, String> pwd, int userid);

	
}
