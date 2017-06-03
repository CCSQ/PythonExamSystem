package com.cc.pes.system.topic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.TransactionAwarePersistenceManagerFactoryProxy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.pes.system.papers.entity.Papers;
import com.cc.pes.system.topic.dao.TopDao;
import com.cc.pes.system.topic.entity.Top;
import com.cc.pes.system.topic.service.TopService;

import sun.print.resources.serviceui;

@Service("topService")
public class TopServiceImpl implements TopService {

	@Autowired
	private TopDao<Top, Integer> topDao;

	@Override
	public List<Top> findTopByType(String type, int begin, int length) throws Exception {
		return topDao.findSomeByHql("from Top where topType = ?", begin, length, type);
	}

	@Override
	public int commitPythonCode(Top top, String code, String path, Papers papers) throws Exception {
		path += "temp";
		Callable ap = new ActionPyhton(code, path, top, papers);
		ExecutorService pool = Executors.newFixedThreadPool(1);
		Future<String> future = pool.submit(ap);
		try {
			Thread.sleep(100);
			String output = future.get();
			
			System.err.println("最终输出："+output);
			topDao.commitByHQL("update Papers set scoe=? where ID = ?", papers.getId());
			papers.setScoe(new Integer(output));
			
		} catch (Exception e) {
			
		}
		return 0;
	}
	
	@Override
	public List<Top> findSomeTop(int begin, int length) throws Exception {
		return topDao.findSomeByHql("from Top", begin, length);
	}

	@Override
	public boolean save(Top top) {
		if (top.getId() == 0) {
			top.setCreaTime(new Date());
			topDao.save(top);
		} else {
			Top topInfor = topDao.get(top.getId());
			topInfor.setInfor(top.getInfor());
			topInfor.setTitle(top.getTitle());
			topInfor.setCodeTemp(top.getCodeTemp());
			topInfor.setInput(top.getInput());
			topInfor.setOutput(top.getOutput());
			topInfor.setKeyCode(top.getKeyCode());
			topInfor.setKeyScore(top.getKeyScore());
			topInfor.setTopType(top.getTopType());
		}
		return false;
	}

	@Override
	public Top getTopById(int id) throws Exception {
		return topDao.get(id);
	}

	@Override
	public String commitPythonCode(String code, int topId, HttpSession httpSession, String string) {
		Top top = topDao.get(topId);
		Callable app = new ActionPubPyhton(code,top, httpSession, string);
		int csore = 0;
		ExecutorService pool = Executors.newFixedThreadPool(1);
		Future<String> future = pool.submit(app);
		
//		Thread thread = new Thread(app);
//		thread.start();
		String output = "";
		try {
			Thread.sleep(100);
			output = future.get();
//			thread.get();
			csore = (int) httpSession.getAttribute("msg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return csore +"/"+ top.getInput()  + output ;
	}

	@Override
	public boolean deleteTop(int topId) {
		topDao.delete(topDao.get(topId));
		return true;
	}

	
}
