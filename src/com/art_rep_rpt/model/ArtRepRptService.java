package com.art_rep_rpt.model;

import java.util.List;
import java.util.Map;

public class ArtRepRptService {
	private ArtRepRptDAO_interface dao;
	
	public ArtRepRptService() {
		dao = new ArtRepRptDAO();
	}
	
	public ArtRepRptVO insertArtRepRpt(Integer artRepNo, Integer memNo, String artRepRptReson) {
		ArtRepRptVO artRepRptVO = new ArtRepRptVO();
		
		artRepRptVO.setArtRepNo(artRepNo);
		artRepRptVO.setMemNo(memNo);
		artRepRptVO.setArtRepRptReson(artRepRptReson);
		dao.insert(artRepRptVO);
		
		return artRepRptVO;
	}
	
	public ArtRepRptVO updateArtRepRpt(Integer artRepRptNo, Integer artRepRptStatus) {
		ArtRepRptVO artRepRptVO = new ArtRepRptVO();
		
		artRepRptVO.setArtRepRptNo(artRepRptNo);
		artRepRptVO.setArtRepRptStatus(artRepRptStatus);
		dao.update(artRepRptVO);
		
		return artRepRptVO;
	}
	
	public ArtRepRptVO getOneArtRepRpt(Integer artRepRptNo) {
		return dao.findByPrimaryKey(artRepRptNo);
	}
	
	public List<ArtRepRptVO> getAll(){
		return dao.getAll();
	}
	
	public List<ArtRepRptVO> getAll(Map<String, String[]> map){
		System.out.println("artRepRptSvc_map:" + map);
		return dao.getAll(map);
	}
}
