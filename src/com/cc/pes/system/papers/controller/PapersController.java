package com.cc.pes.system.papers.controller;



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
import com.cc.pes.system.papers.entity.Papers;
import com.cc.pes.system.papers.service.PapersService;
import com.cc.pes.system.topic.entity.Top;
import com.cc.pes.system.topic.service.TopService;
import com.cc.pes.system.user.service.UserService;

@Controller
@RequestMapping("/papers")
public class PapersController {
	@Autowired
	@Qualifier("topService")
	private TopService topService;
	
	@Autowired
	@Qualifier("papersService")
	private PapersService papersService;
	
	//	获取试卷
	@RequestMapping(value="getPapersByExamId", method = RequestMethod.GET)
	public @ResponseBody List<Map> papers(@RequestParam(value = "examId") int examId, HttpServletRequest request) throws Exception{
		List<Map> list = new ArrayList<Map>();
		
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"student".equals(userType)) {
			Map<String,Object> infor = new HashMap<String,Object>();
			infor.put("msg", false);
			list.add(infor);
			return list;
		}
		
		int userId = (int) session.getAttribute("UserId");
		List<Papers> papersList = papersService.findExamPapers(examId, userId);

		for (int i = 0; i < papersList.size(); i++) {
			Papers papers = papersList.get(i);
			Map<String,Object> infor = new HashMap<String,Object>();

			infor.put("topId",papers.getTopId());
			
			Top top = topService.getTopById(papers.getTopId());
			infor.put("topTitle",top.getTitle());
			infor.put("topInfor",top.getInfor());
			
			infor.put("number",papers.getNumber());
			infor.put("code",papers.getCode());
			infor.put("scoe", papers.getScoe());
			
			infor.put("input", papers.getInput());
			infor.put("output", papers.getOutput());
			
			list.add(infor);
		}
		
		return list;
	}
	
	
	//	初始化试卷
	@RequestMapping(value="initExam", method = RequestMethod.GET)
	public @ResponseBody List<Map> initExam(@RequestParam(value = "examId") int examId, HttpServletRequest request) throws Exception{
		List<Map> list = new ArrayList<Map>();
		
		HttpSession session = request.getSession(false);
		
		String userType = (String) session.getAttribute("UserType");
		if (userType == null || !"student".equals(userType)) {
			Map<String,Object> infor = new HashMap<String,Object>();
			infor.put("msg", false);
			list.add(infor);
			return list;
		}

		// 开始新建考试
		int userId = (int) session.getAttribute("UserId");
		List<Papers> papersList = papersService.findExamPapers(examId, userId);
		if (papersList.size() <= 0) {
			papersList = papersService.createExam(examId, userId, session);
		}
		
		session.setAttribute("ExamId", examId);

		for (int i = 0; i < papersList.size(); i++) {
			Papers papers = papersList.get(i);
			Map<String,Object> infor = new HashMap<String,Object>();

			infor.put("id",papers.getId());
			infor.put("topId",papers.getTopId());

			Top top = topService.getTopById(papers.getTopId());
			infor.put("topTitle",top.getTitle());
			infor.put("topInfor",top.getInfor());

			infor.put("number",papers.getNumber());
			infor.put("code",papers.getCode());
	
			list.add(infor);
		}
		
		return list;
	}
	
	//	提交试卷
	@RequestMapping(value="submitExam", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> initExam(@RequestBody List<Object> examList, HttpServletRequest request) throws Exception{
		Map<String, Object> infor = new HashMap<String, Object>();
		
		infor.put("msg", papersService.setSubmitExam(request.getSession(false), examList, request.getServletContext().getRealPath("/")));
		return infor;
	}
}
