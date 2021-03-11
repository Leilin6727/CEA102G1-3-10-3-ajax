package com.art_rep.model;

import java.sql.Timestamp;

public class ArtRepVO implements java.io.Serializable{
	private Integer artRepNo;
	private Integer artNo;
	private Integer memNo;
	private Timestamp artRepTime;
	private String artRepContent;
	private Integer artRepStatus;

	public ArtRepVO() {
		super();
	}

	public Integer getArtRepNo() {
		return artRepNo;
	}

	public void setArtRepNo(Integer artRepNo) {
		this.artRepNo = artRepNo;
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

	public Timestamp getArtRepTime() {
		return artRepTime;
	}

	public void setArtRepTime(Timestamp artRepTime) {
		this.artRepTime = artRepTime;
	}

	public String getArtRepContent() {
		return artRepContent;
	}

	public void setArtRepContent(String artRepContent) {
		this.artRepContent = artRepContent;
	}

	public Integer getArtRepStatus() {
		return artRepStatus;
	}

	public void setArtRepStatus(Integer artRepStatus) {
		this.artRepStatus = artRepStatus;
	}
	
}
