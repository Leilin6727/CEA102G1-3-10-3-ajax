package com.art.model;

import java.util.List;
import java.util.Map;

public class ArtService {
	
	private ArtDAO_interface dao;
	
	public ArtService() {
		dao = new ArtDAO();
//		dao = new ArtJDBCDAO();
	}
	
	public ArtVO insertArt(Integer memNo, String artTitle, String artContent, String artMovTypeSelect) {
		ArtVO artVO = new ArtVO();
		
		artVO.setMemNo(memNo);
		artVO.setArtTitle(artTitle);
		artVO.setArtContent(artContent);
		artVO.setMovType(artMovTypeSelect);
		Integer artNo = Integer.parseInt(dao.insert(artVO));
		artVO.setArtNo(artNo);
		
		return artVO;
	}
	
	public ArtVO updateArt(Integer artNo, String artTitle, String artContent, String artMovTypeSelect) {
		ArtVO artVO = new ArtVO();
		
		artVO.setArtNo(artNo);
		artVO.setArtTitle(artTitle);
		artVO.setArtContent(artContent);
		artVO.setMovType(artMovTypeSelect);
		dao.update(artVO);
		artVO = dao.findByPrimaryKey(artNo);
		
		return artVO;
	}
	
	public void deleteArt(Integer artNo) {
		dao.delete(artNo);
	}
	
	public List<ArtVO> findByTitle(String artTitle){
		return dao.findByTitle(artTitle);
	}
	
	public ArtVO getOneArt(Integer artNo) {
		return dao.findByPrimaryKey(artNo);
	}
	
	public List<ArtVO> getAll(){
		return dao.getAll();
	}
	
	public List<ArtVO> getAll(Map<String, String[]> map){
		System.out.println("artSvc_map:" + map);
		return dao.getAll(map);
	}
	public ArtVO updateStatus(Integer artNo, Integer artStatus) {
		ArtVO artVO = new ArtVO();
		artVO.setArtNo(artNo);
		artVO.setArtStatus(artStatus);
		dao.updateStatus(artVO);
		artVO = dao.findByPrimaryKey(artNo);
		
		return artVO;
	}
	public List<String> getAllMoveType(){
		return dao.getAllMoveType();
	}
}
