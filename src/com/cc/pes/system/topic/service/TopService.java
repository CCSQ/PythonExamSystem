package com.cc.pes.system.topic.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.cc.pes.system.papers.entity.Papers;
import com.cc.pes.system.topic.entity.Top;

public interface TopService {
	public List<Top> findTopByType(String type, int begin, int length) throws Exception;
	
	public List<Top> findSomeTop(int begin, int length) throws Exception;
	
	public int commitPythonCode(Top top, String code, String path, Papers papers) throws Exception;

	public boolean save(Top top);
	
	public Top getTopById(int id) throws Exception;

	public String commitPythonCode(String code, int topId, HttpSession httpSession, String string);

	public boolean deleteTop(int topId);
	
}
