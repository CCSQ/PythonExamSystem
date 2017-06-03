package com.cc.pes.system.topic.service.impl;

import java.awt.print.Printable;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.validator.Var;

import com.cc.pes.system.papers.entity.Papers;
import com.cc.pes.system.topic.entity.Top;

public class ActionPubPyhton extends Thread implements Callable<String> {
	private String code;
	private Top top;
	private String path;
	private HttpSession session;
	private String output = "";
	
	public ActionPubPyhton(String code,Top top, HttpSession session, String path) {
		
		this.code = code;
		this.top = top;
		this.path =path;
		this.session = session;
	}
	
	public void run() {
		String filename = path + new Properties(System.getProperties()).getProperty("file.separator") +Thread.currentThread().getName()+".py";
		System.err.println(filename);
		File f = new File(filename);
		int score = 0;
		try {
			FileOutputStream fop = new FileOutputStream(f);
			OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
			writer.append("#!/usr/bin/python3\r\n");
			writer.append("import sys\r\n");
			String[] codes = code.split("~");
			Map<String, Boolean> scoreMap = new HashMap<String, Boolean>();
			String[] keyCode = top.getKeyCode().split("-");
			String[] keyScore = top.getKeyScore().split("-");
			for(int j = 0; j < keyCode.length - 1; j++) {
				scoreMap.put(keyCode[j], false);
			}
			
			for (int i = 0; i < codes.length; i++) {
				if(codes[i].replaceAll(" ", "").indexOf("#") == -1){
					writer.append(codes[i] + "\r\n");
				}
				
				for(int j = 0; j < keyCode.length - 1; j++) {
					if (codes[i].replaceAll(" ", "").indexOf(keyCode[j]) != -1 && !scoreMap.get(keyCode[j])) {
						score += new Integer(keyScore[j]);
					}
				}
			}
			
			
			
			String codeTemp = top.getCodeTemp();
			int start = codeTemp.indexOf("def") + 4;
			int end = codeTemp.indexOf("):");
			codeTemp = codeTemp.substring(start,end);
			end = codeTemp.indexOf("(");
			codeTemp = codeTemp.substring(0,end);
			writer.append("\r\n\r\n\r\n");
			
			int length = top.getInput().split("-")[0].split(",").length;
			int index = 1;
			for (int i = 0; i < top.getInput().split("-").length  ; i++) {
				writer.append(codeTemp+"(");
				for (int j = 0; j < length; j++) {
					if (j == length - 1) {
						writer.append("sys.argv[" + index + "]");
					} else {
						writer.append("sys.argv[" + index + "],");
					}
					
					index ++;
				}
				writer.append(")");
				writer.append("\r\n\r\n\r\n");
			}
			writer.close();
			fop.close();
			Runtime run = Runtime.getRuntime();
			
			String []cmd={"python",filename,};
			int leng = cmd.length;
			
			String[] inp = top.getInput().split("-");
			for(int i = 0; i < inp.length  ; i++){
				String[] inpTemp = inp[i].split(",");
				leng = cmd.length;
				int lengTemp = inpTemp.length;
				cmd = Arrays.copyOf(cmd, leng + lengTemp);
				System.arraycopy(inpTemp, 0, cmd, leng, lengTemp);
			}
			
			Process process = run.exec(cmd);
			System.out.println("执行的命令");
			for (int i = 0; i < cmd.length; i++) {
				System.out.print(cmd[i] + "   ");
			}
			System.out.println();
			InputStream in = process.getInputStream();
			InputStream err = process.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(in)));
			BufferedReader errBuff = new BufferedReader(new InputStreamReader(new BufferedInputStream(err)));
			String lineStr = "";
			boolean flag = false;
			while ((lineStr = errBuff.readLine()) != null) {
				this.output +=("/运行失败");
				flag = true;
			}
			if (!flag) {
				String[] out = top.getOutput().split("-");
				while ((lineStr = br.readLine()) != null) {
					for(int i = 0; i < out.length; i ++){
						if (out[i].equals(lineStr)) {
							score+=1;
						}
					}

					this.output +=("/"+ lineStr);
				}
			}
			if (score > 10) {
				score = 10;
			}
			
			br.close();
			session.setAttribute("msg",score);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String call() throws Exception {
		this.run();
		return this.output;
	}
}
