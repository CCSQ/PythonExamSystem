package com.cc.pes.system.papers.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.antlr.gunit.swingui.parsers.StGUnitParser.output_return;

import com.cc.pes.system.simple.entity.BaseEntity;
// 用户实体
@Entity
@Table(name="SYS_PAPERS")
public class Papers extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int topId;
	private int studentId;
	private int examId;
	private int number; // 第几道题目
	private String code;
	private int scoe;
	private String input;
	private String output;

	
	
	@Id 
	@GeneratedValue
	@Column(name="ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="TOPID")
	public int getTopId() {
		return topId;
	}
	public void setTopId(int topId) {
		this.topId = topId;
	}
	
	@Column(name="STUDENTID")
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	@Column(name="NUMBER")
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Column(name="CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="SCOE")
	public int getScoe() {
		return scoe;
	}
	public void setScoe(int scoe) {
		this.scoe = scoe;
	}
	
	@Column(name="EXAMID")
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}
	
	@Column(name="INPUT")
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	
	@Column(name="OUTPUT")
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}

	
	
}
