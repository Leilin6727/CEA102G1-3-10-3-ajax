package com.art_rpt.model;

import java.util.List;
import java.util.Map;

public interface ArtRptDAO_interface {
	public void insert(ArtRptVO artRptVO);
	public void update(ArtRptVO artRptVO);
	public ArtRptVO findByPrimaryKey(Integer artRptNo);
	public List<ArtRptVO> getAll();
	public List<ArtRptVO> getAll(Map<String, String[]> map);
}
