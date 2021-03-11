package com.art.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.art.model.*;



@WebServlet("/ArticleTest")
public class ArticleTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ArticleTest() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArtDAO_interface dao = new ArtDAO();
		
		//�s�W
		ArtVO article1 = new ArtVO();
		article1.setMemNo(1);
		article1.setArtTitle("aaaaa");
		article1.setArtContent("C:/Users/CJ0201005/Pictures/pokemon.png");
		article1.setArtReplyno(0);
		article1.setArtTime(java.sql.Date.valueOf("2021-01-01"));
		article1.setArtStatus(0);
		
		dao.insert(article1);
		
//		//�ק�
//		ArtVO article2 = new ArtVO();
//		article2.setArtno(2);
//		article2.setMemno(1);
//		article2.setArttitle("ccc");
//		article2.setArtcontent("ddd");
//		article2.setArtreplyno(0);
//		article2.setArttime(java.sql.Date.valueOf("2021-01-03"));
//		article2.setArtStatus(true);
//		
//		dao.update(article2);
		
		//�R��
//		dao.delete(3);
		
//		//�d��
//		ArtVO article3 = dao.findByPrimaryKey(2);
//		System.out.println(article3.getArttitle());
//		System.out.println(article3.getArtcontent());
//		System.out.println(article3.getArttime());
		
//		//�d��2
//		ArtVO article4 = dao.findByTitle();
		
//		//�d��3
//		List<ArtVO> list = dao.getAll();
//		for(ArtVO article5 : list) {
//			System.out.println(article5.getArttitle());
//			System.out.println(article5.getArtcontent());
//			System.out.println(article5.getArttime());			
//		}
		
		//�d��4
//		List<ArtVO> list = dao.getAll();


		
				
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
