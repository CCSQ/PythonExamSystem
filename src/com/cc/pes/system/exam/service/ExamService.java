package com.cc.pes.system.exam.service;



import java.util.List;
import javax.servlet.http.HttpSession;

import com.cc.pes.system.exam.entity.Exam;

public interface ExamService {
	
	public List<Exam> findSomeExam(int begin, int length, String Clazz) throws Exception;

	public boolean setExamType(Exam exam, HttpSession session);

	public boolean deleteExamType(int id, HttpSession session);

	public boolean saveExamType(Exam exam, HttpSession session);

	public boolean updateExam(Exam exam);

	public List<Exam> findAllExam(String userClazz);
	
	
}
