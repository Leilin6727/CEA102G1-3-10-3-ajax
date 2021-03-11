package com.art_rep_rpt.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.art_rep_rpt.model.ArtRepRptDAO;
import com.art_rep_rpt.model.ArtRepRptDAO_interface;
import com.art_rep_rpt.model.ArtRepRptVO;

/**
 * Servlet implementation class ArtRepRptTest
 */
@WebServlet("/ArtRepRptTest")
public class ArtRepRptTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArtRepRptTest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ArtRepRptDAO_interface dao = new ArtRepRptDAO();	
//		//新增
//		ArtRepRptVO artRepRptVO = new ArtRepRptVO();
//		artRepRptVO.setArtRepNo(1);
//		artRepRptVO.setMemNo(1);
//		artRepRptVO.setArtRepRptReson("test");
//		dao.insert(artRepRptVO);
		
		//更新
		ArtRepRptVO artRepRptVO2 = new ArtRepRptVO();
		artRepRptVO2.setArtRepRptNo(1);
		artRepRptVO2.setArtRepRptStatus(0);
		dao.update(artRepRptVO2);
		
//		//查詢
//		ArtRepRptVO artRepRptVO3 = dao.findByPrimaryKey(1);
//		System.out.println(artRepRptVO3.getArtRepNo());
//		System.out.println(artRepRptVO3.getMemNo());
//		System.out.println(artRepRptVO3.getArtRepRptReson());
//		System.out.println(artRepRptVO3.getArtRepRptTime());
//		
//		//查全部
//		List<ArtRepRptVO> list  = dao.getAll();
//		for(ArtRepRptVO artRepRptVO4 : list) {
//			System.out.println(artRepRptVO3.getArtRepNo());
//			System.out.println(artRepRptVO3.getMemNo());
//			System.out.println(artRepRptVO3.getArtRepRptReson());
//			System.out.println(artRepRptVO3.getArtRepRptTime());			
//		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
