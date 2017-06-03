package com.cc.pes.system.examList.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.pes.system.exam.dao.ExamDao;
import com.cc.pes.system.exam.entity.Exam;
import com.cc.pes.system.exam.service.ExamService;
import com.cc.pes.system.examList.dao.ExamListDao;
import com.cc.pes.system.examList.entity.ExamList;
import com.cc.pes.system.examList.service.ExamListService;

import javax.servlet.http.HttpSession;

@Service("examListService")
public class ExamListServiceImpl implements ExamListService {

	@Autowired
	private ExamListDao<ExamList, Integer> examListDao;

	
	@Override
	public List<ExamList> findAllExamList(int id) throws Exception {
		return examListDao.findByHQL("from ExamList where examId = ?", id);
	}


	@Override
	public List<ExamList> findAllExamForUaer(int userId) {
		return examListDao.findByHQL("from ExamList where studentId = ?", userId);
	}
	
	

}
