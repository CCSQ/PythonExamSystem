package com.cc.pes.system.papers.service;


import java.util.List;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.cc.pes.system.exam.entity.Exam;
import com.cc.pes.system.papers.entity.Papers;

public interface PapersService {
	
	public List<Papers> findExamPapers(int examId, int studentId) throws Exception;

	public List<Papers> createExam(int examId, int userId, HttpSession session);

	public int setSubmitExam(HttpSession session, List<Object> examList, String path);

	
}
