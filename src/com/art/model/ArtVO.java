package com.art.model;
//import java.sql.*;
import java.util.Date;

public class ArtVO implements java.io.Serializable{
	private Integer artNo;
	private Integer memNo;
	private String artTitle;
	private String artContent;
	private Integer artReplyno;
	private Date artTime;
	private Integer artStatus;
	private String movType;
			
	public ArtVO() {
		super();
	}
	
	public Integer getArtNo() {
		return artNo;
	}
	
	public void setArtNo(Integer artNo) {
		this.artNo = artNo;
	}
	
	public Integer getMemNo() {
		return memNo;
	}
	
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	
	public String getArtTitle() {
		return artTitle;
	}
	
	public void setArtTitle(String artTitle) {
		this.artTitle = artTitle;
	}
	
	public String getArtContent() {
		return artContent;
	}
	
	public void setArtContent(String artContent) {
		this.artContent = artContent;
	}
	
	public Integer getArtReplyno() {
		return artReplyno;
	}
	
	public void setArtReplyno(Integer artReplyno) {
		this.artReplyno = artReplyno;
	}
	
	public Date getArtTime() {
		return artTime;
	}
	
	public void setArtTime(Date artTime) {
		this.artTime = artTime;
	}
	
	public Integer getArtStatus() {
		return artStatus;
	}
	
	public void setArtStatus(Integer artStatus) {
		this.artStatus = artStatus;
	}

	public String getMovType() {
		return movType;
	}

	public void setMovType(String movType) {
		this.movType = movType;
	}
	
}
