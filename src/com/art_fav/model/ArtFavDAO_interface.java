package com.art_fav.model;

import java.util.List;


public interface ArtFavDAO_interface {
	public void insert(ArtFavVO artFavVO);
	public void delete(Integer artNo, Integer memNo);
	public ArtFavVO findByPrimaryKey(Integer artNo, Integer memNo);
	public List<ArtFavVO> findByMenNo(Integer memNo);
}
