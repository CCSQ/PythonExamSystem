package com.cc.pes.system.topic.entity;



import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.pes.system.simple.entity.BaseEntity;
// 用户实体
@Entity
@Table(name="SYS_TOP")
public class Top extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private String infor;
	private String topType;
	private String codeTemp;
	private int authorId;
	private Date creaTime;
	private String input;
	private String output;
	private String keyCode;
	private String keyScore;
	
	
	@Id 
	@GeneratedValue
	@Column(name="ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="INFOR")
	public String getInfor() {
		return infor;
	}
	public void setInfor(String infor) {
		this.infor = infor;
	}
	
	@Column(name="TOPTYPE")
	public String getTopType() {
		return topType;
	}
	public void setTopType(String topType) {
		this.topType = topType;
	}
	
	@Column(name="CODETEMP")
	public String getCodeTemp() {
		return codeTemp;
	}
	public void setCodeTemp(String codeTemp) {
		this.codeTemp = codeTemp;
	}
	
	@Column(name="AUTHORID")
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	@Column(name="CREATIME")
	public Date getCreaTime() {
		return creaTime;
	}
	public void setCreaTime(Date creaTime) {
		this.creaTime = creaTime;
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
	
	@Column(name="KEYCODE")
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	
	@Column(name="KEYSCORE")
	public String getKeyScore() {
		return keyScore;
	}
	public void setKeyScore(String keyScore) {
		this.keyScore = keyScore;
	}
	
	
}
