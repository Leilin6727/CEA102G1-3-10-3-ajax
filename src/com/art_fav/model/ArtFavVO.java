package com.art_fav.model;

import java.sql.Timestamp;


public class ArtFavVO implements java.io.Serializable{
	private Integer artNo;
	private Integer memNo;
	private Timestamp artFavTime;	
	
	public ArtFavVO() {
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
	
	public Timestamp getArtFavTime() {
		return artFavTime;
	}
	
	public void setArtFavTime(Timestamp artFavTime) {
		this.artFavTime = artFavTime;
	}
		
}
