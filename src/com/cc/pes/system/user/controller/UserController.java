package com.cc.pes.system.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.pes.system.simple.context.spring.SpringDispatcherContextHolder;
import com.cc.pes.system.simple.controller.SimpleController;
import com.cc.pes.system.simple.util.Util;
import com.cc.pes.system.topic.entity.Top;
import com.cc.pes.system.user.entity.User;
import com.cc.pes.system.user.service.UserService;

import sun.security.provider.MD5;

@Controller
@RequestMapping("/user")
public class UserController {
	protected static final Log logger = LogFactory.getLog(SimpleController.class);
	
//	@Autowired 自动装配 @Qualifier("UserService") 可能存在多个
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	//	用户登陆
	@RequestMapping(value="login", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> userLogin(@RequestBody User user, HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("login", userService.login(user,request.getSession()));
		return map;
	}
	
	//	用户退出
	@RequestMapping(value="loginout", method = RequestMethod.GET)
	public String userLoginOut(HttpServletRequest request) throws Exception{
		userService.loginOut(request.getSession(false));
		return "index";
	}
	
	
	//	检测是否存在该用户
	@RequestMapping(value="userIsExist", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> userIsExist(@RequestParam(value = "id") Integer id, @RequestParam(value = "type") String type) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		User user = userService.findUserById(id);
		if (null == user || !user.getType().equals(type)) map.put("isExist", false); else map.put("isExist", true);
		return map;
	}

	// 获取所有教师
	@RequestMapping(value="getAllTeacherData", method = RequestMethod.GET)
	public @ResponseBody List<Map> getAllTeacherData(@RequestParam(value = "begin") Integer begin, @RequestParam(value = "length") Integer length, HttpServletRequest request) throws Exception{
		List<Map> list = new ArrayList<Map>();

		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"admin".equals(userType)) {
			Map<String,Object> infor = new HashMap<String,Object>();
			infor.put("msg", false);
			list.add(infor);
			return list;
		}
		
		List<User> teacherList = userService.getTeacherPage(begin, length);
		
		for (int i = 0; i < teacherList.size(); i++) {
			User user = teacherList.get(i);
			Map<String,Object> infor = new HashMap<String,Object>();
			infor.put("id", user.getId());
			infor.put("name", user.getName());
			infor.put("clazz", user.getClazz());
			list.add(infor);
		}
		return list;
	}
	
	//	用户修改
	@RequestMapping(value="upDateUser", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> upDateUser(@RequestBody User user, HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"admin".equals(userType)) {
			map.put("msg", false);
			return map;
		}
		
		
		map.put("msg", userService.save(user));
		return map;
	}
	
	//	用户重置密码 待改进
	@RequestMapping(value="setUserPwd", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> resetUserPwd(@RequestParam(value = "id") Integer id, HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"admin".equals(userType)) {
			map.put("msg", false);
			return map;
		}
		
		map.put("msg", userService.setPwd(id));
		return map;
	}
	
	//	用户删除
	@RequestMapping(value="deleteUser", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> deleteUser(@RequestParam(value = "id") Integer id, HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"admin".equals(userType)) {
			map.put("msg", false);
			return map;
		}
		
		map.put("msg", userService.deleteUser(id));
		return map;
	}
	
	//	用户增加
	@RequestMapping(value="addUser", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> addUser(@RequestBody User user, HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"admin".equals(userType)) {
			map.put("msg", false);
			return map;
		}
		
		map.put("msg", userService.addUser(user));
		return map;
	}
	
	// 获取所有学生
	@RequestMapping(value="getAllStudentData", method = RequestMethod.GET)
	public @ResponseBody List<Map> getAllStudentData(@RequestParam(value = "begin") Integer begin, @RequestParam(value = "length") Integer length, HttpServletRequest request) throws Exception{
		List<Map> list = new ArrayList<Map>();

		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"teacher".equals(userType)) {
			Map<String,Object> infor = new HashMap<String,Object>();
			infor.put("msg", false);
			list.add(infor);
			return list;
		}
		
		String clazz = (String) session.getAttribute("UserClazz");
		
		List<User> studentList = userService.getStudentPage(begin, length, clazz);
		
		for (int i = 0; i < studentList.size(); i++) {
			User user = studentList.get(i);
			Map<String,Object> infor = new HashMap<String,Object>();
			if (user.getClazz().equals(clazz)) {
				infor.put("id", user.getId());
				infor.put("name", user.getName());
				infor.put("clazz", user.getClazz());
				list.add(infor);
			}
		}
		return list;
	}

	//	用户修改
	@RequestMapping(value="setStuPwd", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> resetStuPwd(@RequestParam(value = "id") Integer id, HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"teacher".equals(userType)) {
			map.put("msg", false);
			return map;
		}
		
		map.put("msg", userService.setPwd(id));
		return map;
	}

	//	用户删除 学生
	@RequestMapping(value="deleteStudent", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> deleteStudent(@RequestParam(value = "id") Integer id, HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"teacher".equals(userType)) {
			map.put("msg", false);
			return map;
		}
		
		map.put("msg", userService.deleteUser(id));
		return map;
	}
	
	
	//	用户增加学生
	@RequestMapping(value="addStudentUser", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> addStudentUser(@RequestBody User user, HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"teacher".equals(userType)) {
			map.put("msg", false);
			return map;
		}
		user.setClazz((String) request.getSession(false).getAttribute("UserClazz"));
		map.put("msg", userService.addUser(user));
		return map;
	}
	
	
	//	获取用户信息
	@RequestMapping(value="getUserInfor", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> getUserInfor(HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null){
			map.put("msg", false);
			return map;
		}

		map.put("userId", session.getAttribute("UserId"));
		map.put("userName", session.getAttribute("UserName"));
		map.put("userType", session.getAttribute("UserType"));
		map.put("userClazz", session.getAttribute("UserClazz"));
		
		return map;
	}
	
	//	用户修改密码
	@RequestMapping(value="setPWD", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> setPWD(@RequestBody Map<String, String> pwd, HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null) {
			map.put("msg", 0);
			return map;
		}
		int userid = (int) session.getAttribute("UserId");
		map.put("msg", userService.updateUserPwd(pwd, userid));
		return map;
	}
}
