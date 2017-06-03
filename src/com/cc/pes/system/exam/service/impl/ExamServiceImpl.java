package com.cc.pes.system.exam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.pes.system.exam.dao.ExamDao;
import com.cc.pes.system.exam.entity.Exam;
import com.cc.pes.system.exam.service.ExamService;
import javax.servlet.http.HttpSession;

@Service("examService")
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamDao<Exam, Integer> examDao;

	
	@Override
	public List<Exam> findSomeExam(int begin, int length, String Clazz) throws Exception {
		return examDao.findSomeByHql("from Exam where clazz = ?", begin, length, Clazz);
	}


	@Override
	public boolean setExamType(Exam exam, HttpSession session) {
		Exam examInfor = examDao.get(exam.getId());
		String userClazz = (String) session.getAttribute("UserClazz");
		if (!userClazz.equals(examInfor.getClazz())) {
			return false;
		}
		examInfor.setType(exam.getType());
		return true;
	}


	@Override
	public boolean deleteExamType(int id, HttpSession session) {
		Exam examInfor = examDao.get(id);
		String userClazz = (String) session.getAttribute("UserClazz");
		if (!userClazz.equals(examInfor.getClazz())) {
			return false;
		}
		examInfor.delete();
		return true;
	}


	@Override
	public boolean saveExamType(Exam exam, HttpSession session) {
		String userClazz = (String) session.getAttribute("UserClazz");
		exam.setClazz(userClazz);
		exam.save();
		return true;
	}


	@Override
	public boolean updateExam(Exam exam) {
		Exam examInfor = examDao.get(exam.getId());
		examInfor.setBeginTime(exam.getBeginTime());
		examInfor.setEndTime(exam.getEndTime());
		examInfor.setTimeLong(exam.getTimeLong());
		return true;
	}


	@Override
	public List<Exam> findAllExam(String userClazz) {
		return examDao.findByHQL("from Exam where clazz = ?", userClazz);
	}
	
	
	
	
}
