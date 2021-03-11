package com.art_rep.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.art_rep.model.ArtRepDAO;
import com.art_rep.model.ArtRepDAO_interface;
import com.art_rep.model.ArtRepVO;

@WebServlet("/ArtRepTest")
public class ArtRepTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArtRepTest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ArtRepDAO_interface dao = new ArtRepDAO();
		
//		//新增
//		ArtRepVO artRepVO = new ArtRepVO();
//		artRepVO.setArtNo(2);
//		artRepVO.setMemNo(1);
//		artRepVO.setArtRepContent("DAO測試回覆");
//		dao.insert(artRepVO);
//		
//		//更新
//		ArtRepVO artRepVO2 = new ArtRepVO();
//		artRepVO2.setArtRepNo(1);
//		artRepVO2.setArtRepContent("DAO測試回覆3");
//		artRepVO2.setArtRepStatus(1);
//		dao.update(artRepVO2);
//		
//		//查詢
//		ArtRepVO artRepVO3 = dao.findByPrimaryKey(1);
//		System.out.println(artRepVO3.getArtNo());
//		System.out.println(artRepVO3.getMemNo());
//		System.out.println(artRepVO3.getArtRepContent());
//		System.out.println(artRepVO3.getArtRepTime());
//		
		//查全部
		List<ArtRepVO> list = dao.getAll();
		for(ArtRepVO artRepVO4 : list) {
			System.out.println(artRepVO4.getArtNo());
			System.out.println(artRepVO4.getMemNo());
			System.out.println(artRepVO4.getArtRepContent());
			System.out.println(artRepVO4.getArtRepTime());			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
