package com.art_rpt.model;

import java.sql.Timestamp;

public class ArtRptVO implements java.io.Serializable{
	private Integer artRptNo;
	private Integer artNo;
	private Integer memNo;
	private String artRptReson;
	private Integer artRptStatus;
	private Timestamp artRptTime;
	
	public ArtRptVO() {
		super();
	}

	public Integer getArtRptNo() {
		return artRptNo;
	}

	public void setArtRptNo(Integer artRptNo) {
		this.artRptNo = artRptNo;
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

	public String getArtRptReson() {
		return artRptReson;
	}

	public void setArtRptReson(String artRptReson) {
		this.artRptReson = artRptReson;
	}

	public Integer getArtRptStatus() {
		return artRptStatus;
	}

	public void setArtRptStatus(Integer artRptStatus) {
		this.artRptStatus = artRptStatus;
	}

	public Timestamp getArtRptTime() {
		return artRptTime;
	}

	public void setArtRptTime(Timestamp artRptTime) {
		this.artRptTime = artRptTime;
	}
	
}
