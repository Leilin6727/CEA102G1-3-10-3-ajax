package com.art_rpt.model;

import java.util.List;
import java.util.Map;

public class ArtRptService {
	private ArtRptDAO_interface dao;
	
	public ArtRptService() {
		dao = new ArtRptDAO();
	}
	
	public ArtRptVO insertArtRpt(Integer artNo, Integer memNo, String artRptReson) {
		ArtRptVO artRptVO = new ArtRptVO();
		
		artRptVO.setArtNo(artNo);
		artRptVO.setMemNo(memNo);
		artRptVO.setArtRptReson(artRptReson);
		dao.insert(artRptVO);
		
		return artRptVO;
	}
	
	public ArtRptVO updateArtRpt(Integer artRptStatus, Integer artRptNo) {
		ArtRptVO artRptVO = new ArtRptVO();
		
		artRptVO.setArtRptStatus(artRptStatus);
		artRptVO.setArtRptNo(artRptNo);
		dao.update(artRptVO);
		
		return artRptVO;
	}
	
	public ArtRptVO getOneArtRpt(Integer artRptNo) {
		return dao.findByPrimaryKey(artRptNo);
	}
	
	public List<ArtRptVO> getAll(){
		return dao.getAll();
	}
	
	public List<ArtRptVO> getAll(Map<String, String[]> map){
		System.out.println("artRptSvc_map:" + map);
		return dao.getAll(map);
	}
}
