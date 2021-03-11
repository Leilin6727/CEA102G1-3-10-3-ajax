package com.art.model;
import java.util.List;
import java.util.Map;

public interface ArtDAO_interface {
	public String insert(ArtVO artVO);
	public void update(ArtVO artVO);
	public void updateStatus(ArtVO artVO);
	public void delete(Integer artNo);
	public ArtVO findByPrimaryKey(Integer artNo);
	public List<ArtVO> findByTitle(String artTitle);
	public List<String> getAllMoveType();
	public List<ArtVO> getAll();
	public List<ArtVO> getAll(Map<String, String[]> map);

}
