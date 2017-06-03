package com.cc.pes.system.examList.service;


import java.util.List;
import javax.servlet.http.HttpSession;

import com.cc.pes.system.exam.entity.Exam;
import com.cc.pes.system.examList.entity.ExamList;

public interface ExamListService {
	
	public List<ExamList> findAllExamList(int id) throws Exception;

	public List<ExamList> findAllExamForUaer(int userId);
	
}
