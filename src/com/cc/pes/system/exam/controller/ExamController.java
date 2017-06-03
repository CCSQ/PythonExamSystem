package com.cc.pes.system.exam.controller;

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
import com.cc.pes.system.user.service.UserService;

@Controller
@RequestMapping("/exam")
public class ExamController {
	@Autowired
	@Qualifier("examService")
	private ExamService examService;
	
	@Autowired
	@Qualifier("examListService")
	private ExamListService examListService;
	
	//	获取所有考试
	@RequestMapping(value="getAllExam", method = RequestMethod.GET)
	public @ResponseBody List<Map> getAllExam(@RequestParam(value = "begin") int begin, @RequestParam(value = "length") int length, HttpServletRequest request) throws Exception{
		List<Map> list = new ArrayList<Map>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"teacher".equals(userType)) {
			Map<String,Object> infor = new HashMap<String,Object>();
			infor.put("msg", false);
			list.add(infor);
			return list;
		}
		
		String userClazz = (String) session.getAttribute("UserClazz");
		List<Exam> examList = examService.findSomeExam(begin, length, userClazz);

		for (int i = 0; i < examList.size(); i++) {
			Exam exam = examList.get(i);
			Map<String,Object> infor = new HashMap<String,Object>();

			infor.put("id",exam.getId());
			infor.put("beginTime",exam.getBeginTime());
			infor.put("endTime",exam.getEndTime());
			infor.put("number",exam.getNumber());
			infor.put("timeLong",exam.getTimeLong());
			infor.put("type",exam.getType());
			
			list.add(infor);
		}
		return list;
	}
	
	//	修改考试
	@RequestMapping(value="setType", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> setType(@RequestBody Exam exam,  HttpServletRequest request) throws Exception{
		Map<String,Object> infor = new HashMap<String,Object>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"teacher".equals(userType)) {
			infor.put("msg", false);
			return infor;
		}
		
		infor.put("msg",examService.setExamType(exam,session));
		return infor;
	}
	
	//	删除考试
	@RequestMapping(value="deleteExam", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> deleteExam(@RequestParam(value = "id") int id,  HttpServletRequest request) throws Exception{
		Map<String,Object> infor = new HashMap<String,Object>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"teacher".equals(userType)) {
			infor.put("msg", false);
			return infor;
		}
		infor.put("msg",examService.deleteExamType(id,session));
		return infor;
	}
	
	//	增加考试
	@RequestMapping(value="addExam", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> addExam(@RequestBody Exam exam,  HttpServletRequest request) throws Exception{
		Map<String,Object> infor = new HashMap<String,Object>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"teacher".equals(userType)) {
			infor.put("msg", false);
			return infor;
		}
		
		if (exam.getId() != 0) {
			infor.put("msg",examService.updateExam(exam));
		} else {
			infor.put("msg",examService.saveExamType(exam,session));
		}
		
		return infor;
	}
	

	//	获取所有考生考试
	@RequestMapping(value="getStudentExam", method = RequestMethod.GET)
	public @ResponseBody List<Map> getStudentExam(HttpServletRequest request) throws Exception{
		List<Map> list = new ArrayList<Map>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"student".equals(userType)) {
			Map<String,Object> infor = new HashMap<String,Object>();
			infor.put("msg", false);
			list.add(infor);
			return list;
		}
		
		String userClazz = (String) session.getAttribute("UserClazz");
		List<Exam> examList = examService.findAllExam(userClazz);
		
		int userId = (int) session.getAttribute("UserId");
		List<ExamList> examInfor = examListService.findAllExamForUaer(userId);

		for (int i = 0; i < examList.size(); i++) {
			Exam exam = examList.get(i);
			Map<String,Object> infor = new HashMap<String,Object>();
			// 未激活考试
			if (exam.getType() == 0) {
				continue;
			}

			infor.put("id",exam.getId());
			infor.put("beginTime",exam.getBeginTime());
			infor.put("endTime",exam.getEndTime());
			infor.put("timeLong",exam.getTimeLong());
			
			for (int j = 0; j < examInfor.size(); j++) {
				ExamList newExam = examInfor.get(j);
				if (exam.getId() == newExam.getExamId()) {
					infor.put("examTime",newExam.getExamTime());
					infor.put("soce",newExam.getSoce());
					infor.put("spenTime",newExam.getSpenTime());
					break;
				}
			}
			
			list.add(infor);
		}
		return list;
	}
}
