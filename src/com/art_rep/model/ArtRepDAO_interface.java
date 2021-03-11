package com.art_rep.model;

import java.util.List;
import java.util.Map;

public interface ArtRepDAO_interface {
	public void insert(ArtRepVO artRepVO);
	public void update(ArtRepVO artRepVO);
	public ArtRepVO findByPrimaryKey(Integer artRepNo);
	public List<ArtRepVO> findByArtNo(Integer artNo);
	public List<ArtRepVO> getAll();
	public List<ArtRepVO> getAll(Map<String, String[]> map);
	
	
}
