package com.art_rpt.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.art_rpt.model.ArtRptDAO;
import com.art_rpt.model.ArtRptDAO_interface;
import com.art_rpt.model.ArtRptVO;

@WebServlet("/ArtRptTest")
public class ArtRptTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArtRptTest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ArtRptDAO_interface  dao = new ArtRptDAO();
	
		//新增
		ArtRptVO artRptVO = new ArtRptVO();
		artRptVO.setArtNo(1);
		artRptVO.setMemNo(3);
		artRptVO.setArtRptReson("ccccc");
		dao.insert(artRptVO);
		
		//更新
		artRptVO.setArtRptStatus(0);
		artRptVO.setArtRptNo(1);
		dao.update(artRptVO);
		
		//查全部
		List<ArtRptVO> list = dao.getAll();
		for(ArtRptVO artRptVO2 : list) {
			System.out.println(artRptVO2.getArtNo());
			System.out.println(artRptVO2.getMemNo());
			System.out.println(artRptVO2.getArtRptReson());
			System.out.println(artRptVO2.getArtRptStatus());
			System.out.println(artRptVO2.getArtRptTime());
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
