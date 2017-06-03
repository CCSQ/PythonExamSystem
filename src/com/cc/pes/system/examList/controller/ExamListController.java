package com.cc.pes.system.examList.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.pes.system.exam.entity.Exam;
import com.cc.pes.system.exam.service.ExamService;
import com.cc.pes.system.examList.entity.ExamList;
import com.cc.pes.system.examList.service.ExamListService;
import com.cc.pes.system.topic.entity.Top;
import com.cc.pes.system.user.entity.User;
import com.cc.pes.system.user.service.UserService;

@Controller
@RequestMapping("/examList")
public class ExamListController {
	@Autowired
	@Qualifier("examListService")
	private ExamListService examListService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	//	获取所有考试成绩
	@RequestMapping(value="getAllExamList", method = RequestMethod.GET)
	public @ResponseBody List<Map> getAllExam(@RequestParam(value = "id") int id,HttpServletRequest request) throws Exception{
		List<Map> list = new ArrayList<Map>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"teacher".equals(userType)) {
			Map<String,Object> infor = new HashMap<String,Object>();
			infor.put("msg", false);
			list.add(infor);
			return list;
		}

		List<ExamList> examList = examListService.findAllExamList(id);

		for (int i = 0; i < examList.size(); i++) {
			ExamList examl = examList.get(i);
			Map<String,Object> infor = new HashMap<String,Object>();

			infor.put("studentId",examl.getStudentId());
			User student = userService.findUserById(examl.getStudentId());
			infor.put("studentName",student.getName());
			infor.put("spenTime",examl.getSpenTime());
			infor.put("examTime",examl.getExamTime());
			infor.put("soce",examl.getSoce());
			
			list.add(infor);
		}
		return list;
	}
}
	
