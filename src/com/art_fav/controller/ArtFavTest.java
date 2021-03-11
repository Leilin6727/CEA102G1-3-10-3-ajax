package com.art_fav.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.art_fav.model.ArtFavDAO;
import com.art_fav.model.ArtFavDAO_interface;
import com.art_fav.model.ArtFavVO;

@WebServlet("/ArtFavTest")
public class ArtFavTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArtFavTest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ArtFavDAO_interface dao = new ArtFavDAO();
//		//新增
//		ArtFavVO artFavVO = new ArtFavVO();
//		artFavVO.setArtNo(1);
//		artFavVO.setMemNo(2);
//		
//		dao.insert(artFavVO);
//		System.out.println("artFavVO:"+artFavVO);
		
//		//刪除
//		dao.delete(1, 2);
//
//		System.out.println("delete over");
	
		//findByPrimaryKey
		ArtFavVO artFavVO = dao.findByPrimaryKey(1, 2);
//		System.out.println(artFavVO.getArtNo());
//		System.out.println(artFavVO.getMemNo());
//		System.out.println(artFavVO.getArtFavTime());
		System.out.println(artFavVO);
		
//		//全部列表
//		List<ArtFavVO> list = dao.findByMenNo(1);
//		for(ArtFavVO artFavVO : list) {
//			System.out.println(artFavVO.getArtNo());
//			System.out.println(artFavVO.getMemNo());
//			System.out.println(artFavVO.getArtFavTime());			
//		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
