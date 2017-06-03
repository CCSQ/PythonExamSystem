package com.cc.pes.system.topic.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.pes.system.topic.entity.Top;
import com.cc.pes.system.topic.service.TopService;
import com.cc.pes.system.user.entity.User;
import com.cc.pes.system.user.service.UserService;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

@Controller
@RequestMapping("/topic")
public class TopicController {
	
	@Autowired
	@Qualifier("topService")
	private TopService topService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	
	//	运行python代码并返回结果
	@RequestMapping(value="getCodeResult", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> publicIndex(@RequestBody List<Object> value, HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		System.out.println("run "+value.get(0));
		
		int topId = (int) value.get(0);
		String code = (String) value.get(1);
		
		map.put("code", topService.commitPythonCode(code, topId, request.getSession(false),request.getServletContext().getRealPath("/")));
		System.err.println(map);
		return map;
	}
	
	//	获取所有练习题目
	@RequestMapping(value="getAllTopic", method = RequestMethod.GET)
	public @ResponseBody List<Map> getTopicByType(@RequestParam(value = "begin") Integer begin, @RequestParam(value = "length") Integer length, HttpServletRequest request) throws Exception{
		List<Map> list = new ArrayList<Map>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"teacher".equals(userType)) {
			Map<String,Object> infor = new HashMap<String,Object>();
			infor.put("msg", false);
			list.add(infor);
			return list;
		}
		
		List<Top> topList = topService.findSomeTop(begin, length);
		System.out.println(topList);
		for (int i = 0; i < topList.size(); i++) {
			Top top = topList.get(i);
			Map<String,Object> infor = new HashMap<String,Object>();
			infor.put("id", top.getId());
			infor.put("title", top.getTitle());
			infor.put("infor", top.getInfor());
			infor.put("codeTemp", top.getCodeTemp());
			infor.put("topType", top.getTopType());
			infor.put("authorId", top.getAuthorId());
			infor.put("authorName", userService.getUserNameById(top.getAuthorId()));
			infor.put("creaTime", top.getCreaTime());
			
			infor.put("input", top.getInput());
			infor.put("output", top.getOutput());
			infor.put("keyCode", top.getKeyCode());
			infor.put("keyScore", top.getKeyScore());
			list.add(infor);
		}
		return list;
	}
	
	
	//	保存题目
	@RequestMapping(value="saveTop", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> saveTop(@RequestBody Top top, HttpServletRequest request) throws Exception{
		System.err.println(top);
		Map<String,Object> map = new HashMap<String,Object>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"teacher".equals(userType)) {
			Map<String,Object> infor = new HashMap<String,Object>();
			map.put("msg", false);
			return map;
		}
		if (top.getId() == 0) {
			top.setAuthorId((int) session.getAttribute("UserId"));
		}
		
		map.put("msg", topService.save(top));
		
		return map;
	}
	
	
	//	删除题目
	@RequestMapping(value="delTop", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> delTop(@RequestParam(value = "id") int topId, HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"teacher".equals(userType)) {
			Map<String,Object> infor = new HashMap<String,Object>();
			map.put("msg", false);
			return map;
		}
		
		map.put("msg", topService.deleteTop(topId));
		return map;
	}
	
	
	
}
