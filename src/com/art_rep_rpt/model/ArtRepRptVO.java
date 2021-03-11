package com.art_rep_rpt.model;

import java.sql.Timestamp;


public class ArtRepRptVO implements java.io.Serializable{
	private Integer ArtRepRptNo;
	private Integer ArtRepNo;
	private Integer memNo;
	private String ArtRepRptReson;
	private Integer ArtRepRptStatus;
	private Timestamp ArtRepRptTime;
	
	public ArtRepRptVO() {
		super();
	}

	public Integer getArtRepRptNo() {
		return ArtRepRptNo;
	}

	public void setArtRepRptNo(Integer artRepRptNo) {
		ArtRepRptNo = artRepRptNo;
	}

	public Integer getArtRepNo() {
		return ArtRepNo;
	}

	public void setArtRepNo(Integer artRepNo) {
		ArtRepNo = artRepNo;
	}

	public Integer getMemNo() {
		return memNo;
	}

	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}

	public String getArtRepRptReson() {
		return ArtRepRptReson;
	}

	public void setArtRepRptReson(String artRepRptReson) {
		ArtRepRptReson = artRepRptReson;
	}

	public Integer getArtRepRptStatus() {
		return ArtRepRptStatus;
	}

	public void setArtRepRptStatus(Integer artRepRptStatus) {
		ArtRepRptStatus = artRepRptStatus;
	}

	public Timestamp getArtRepRptTime() {
		return ArtRepRptTime;
	}

	public void setArtRepRptTime(Timestamp artRepRptTime) {
		ArtRepRptTime = artRepRptTime;
	}
	
	
}
