package com.cc.pes.system.publicView.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.pes.system.topic.entity.Top;
import com.cc.pes.system.topic.service.TopService;
import com.cc.pes.system.user.service.UserService;

@Controller
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	@Qualifier("topService")
	private TopService topService;
	
	
	@RequestMapping(value="index", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> publicIndex(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", true);
		return map;
	}
	
	//	获取公共练习题目
	@RequestMapping(value="getTopicByType", method = RequestMethod.GET)
	public @ResponseBody List<Map> getTopicByType(@RequestParam(value = "type") String type, @RequestParam(value = "begin") Integer begin, @RequestParam(value = "length") Integer length) throws Exception{
		List<Map> list = new ArrayList<Map>();
		List<Top> topList = topService.findTopByType(type, begin, length);
		for (int i = 0; i < topList.size(); i++) {
			Top top = topList.get(i);
			Map<String,Object> infor = new HashMap<String,Object>();
			infor.put("topId", top.getId());
			infor.put("title", top.getTitle());
			infor.put("infor", top.getInfor());
			infor.put("codeTemp", top.getCodeTemp());
			list.add(infor);
		}
		return list;
	}
}
