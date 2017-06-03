package com.cc.pes.system.examList.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.pes.system.simple.entity.BaseEntity;
// 用户实体
@Entity
@Table(name="SYS_EXAMLIST")
public class ExamList extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int examId;
	private int studentId;
	private int spenTime;
	private Date examTime;
	private int soce;
	
	
	@Id 
	@GeneratedValue
	@Column(name="ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="STUDENTID")
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	@Column(name="SPENTIME")
	public int getSpenTime() {
		return spenTime;
	}
	public void setSpenTime(int spenTime) {
		this.spenTime = spenTime;
	}
	
	@Column(name="EXAMTIME")
	public Date getExamTime() {
		return examTime;
	}
	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}
	
	@Column(name="SOCE")
	public int getSoce() {
		return soce;
	}
	public void setSoce(int soce) {
		this.soce = soce;
	}
	
	@Column(name="EXAMID")
	public int getExamId() {
		return examId;
	}
	public void setExamId(int examId) {
		this.examId = examId;
	}

}
