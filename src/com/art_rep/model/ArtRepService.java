package com.art_rep.model;

import java.util.List;
import java.util.Map;

public class ArtRepService {
	
	private ArtRepDAO_interface dao;
	
	public ArtRepService() {
		dao = new ArtRepDAO();
	}
	
	public ArtRepVO insertArtRep(Integer artNo, Integer memNo, String artRepContent) {
		ArtRepVO artRepVO = new ArtRepVO();
		
		artRepVO.setArtNo(artNo);
		artRepVO.setMemNo(memNo);
		artRepVO.setArtRepContent(artRepContent);
		dao.insert(artRepVO);
		
		return artRepVO;
	}
	
	public ArtRepVO updateArtRep(String artRepContent, Integer artRepStatus, Integer artRepNo) {
		ArtRepVO artRepVO = new ArtRepVO();
		
		artRepVO.setArtRepContent(artRepContent);
		artRepVO.setArtRepStatus(artRepStatus);
		artRepVO.setArtRepNo(artRepNo);
		dao.update(artRepVO);
		
		return artRepVO;
	}
	
	public ArtRepVO getOneArtRep(Integer artRepNo) {
		return dao.findByPrimaryKey(artRepNo);
	}
	
	public List<ArtRepVO> getAll(){
		return dao.getAll();
	}
	
	public List<ArtRepVO> getAll(Map<String, String[]> map){
		return dao.getAll(map);
	}
	
	public List<ArtRepVO> findByArtNo(Integer artNo){
		return dao.findByArtNo(artNo);
	}
}
