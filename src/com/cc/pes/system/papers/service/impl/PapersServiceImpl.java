package com.cc.pes.system.papers.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cc.pes.system.exam.dao.ExamDao;
import com.cc.pes.system.exam.entity.Exam;
import com.cc.pes.system.exam.service.ExamService;
import com.cc.pes.system.examList.dao.ExamListDao;
import com.cc.pes.system.examList.entity.ExamList;
import com.cc.pes.system.papers.dao.PapersDao;
import com.cc.pes.system.papers.entity.Papers;
import com.cc.pes.system.papers.service.PapersService;
import com.cc.pes.system.topic.dao.TopDao;
import com.cc.pes.system.topic.entity.Top;
import com.cc.pes.system.topic.service.TopService;
import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@Service("papersService")
public class PapersServiceImpl implements PapersService {

	@Autowired
	private PapersDao<Papers, Integer> papersDao;
	
	@Autowired
	private TopDao<Top, Integer> topDao;
	
	@Autowired
	@Qualifier("topService")
	private TopService topService;
	
	@Autowired
	private ExamDao<Exam, Integer> examDao;
	
	@Autowired
	private ExamListDao<ExamList, Integer> examListDao;

	@Override
	public List<Papers> findExamPapers(int examId, int studentId) throws Exception {
		return papersDao.findByHQL("from Papers where examId = ? and studentId = ?", examId, studentId);
	}



	@Override
	public List<Papers> createExam(int examId, int userId, HttpSession session) {
		// 新建完考试信息了
		ExamList newExamList = new ExamList();
		newExamList.setExamId(examId);
		newExamList.setStudentId(userId);
		newExamList.setExamTime(new Date());
		newExamList.setSoce(-1);
		newExamList.setSpenTime(-1);
		int id = newExamList.save();
		session.setAttribute("ExamListId", id);
		int count = topDao.getCount("Top");
		// 新建题目信息
		Random random = new Random();
		if (count < 10) {
			return null;
		}
		List<Integer> a = new ArrayList<Integer>();
		while (a.size() <= 10) {
			Integer num = random.nextInt(count);
			boolean flag = false;
			for (Integer integer : a) {
				if (integer.intValue() == num) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				a.add(num);
			}
		}
		
		
		List<Papers> list = new ArrayList<Papers>();
		for (int i : a) {
			Top top = topDao.get(i);
			if (null != top) {
				Papers papers = new Papers();
				papers.setCode(top.getCodeTemp());
				papers.setExamId(examId);
				papers.setNumber(i);
				papers.setScoe(-1);
				papers.setStudentId(userId);
				papers.setTopId(top.getId());
				papers.save();
				list.add(papers);
			}
		}

		return list;
	}


	@Override
	public int setSubmitExam(HttpSession session, List<Object> examList, String path) {
		int sco = 0;
		for (Object object : examList) {
			Map<String, Object> infor = (Map<String, Object>) object;
			Papers papers = papersDao.get((Integer) infor.get("id"));
			papers.setCode(((String) infor.get("code")).replaceAll("~", "\n"));
			Top top = topDao.get((Integer) infor.get("topId"));
			try {
				papers.setScoe(topService.commitPythonCode(top,(String) infor.get("code"),path,papers));
				Thread.sleep(10);
				sco += papersDao.get((Integer) infor.get("id")).getScoe();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int exId = (int) session.getAttribute("ExamId");
		int examId = (int) session.getAttribute("ExamListId");
		ExamList examL = examListDao.get(examId);
		Exam exam = examDao.get(exId);
		examL.setSoce(sco);
		int spentTime = (int) ((new Date().getTime() - examL.getExamTime().getTime())/60000);
		if (spentTime == 0) {
			spentTime = 1;
		}
		if (spentTime > exam.getTimeLong()) {
			spentTime = exam.getTimeLong();
		}
		examL.setSpenTime(spentTime);
		return sco;
	}
	
	

}
