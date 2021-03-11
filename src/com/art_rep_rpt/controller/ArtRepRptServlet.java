package com.art_rep_rpt.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import com.art_rep_rpt.model.ArtRepRptService;


public class ArtRepRptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArtRepRptServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		System.out.println("action---"+action);
		
		if("addRepRpt".equals(action)) {
			JSONArray array = new JSONArray();
			HttpSession session = request.getSession();
			
			/*====================請求參數===================*/
			Integer artRepNo = Integer.parseInt(request.getParameter("artRepNo"));
			Integer memNo = Integer.parseInt(String.valueOf(session.getAttribute("memNo")));
			String artRepRptReson = request.getParameter("artRepRptReson");
			System.out.println("artRepRptReson:"+request.getParameter("artRepRptReson"));
			
			/*====================新增資料===================*/
			ArtRepRptService artRepRptSvc = new ArtRepRptService();
			artRepRptSvc.insertArtRepRpt(artRepNo, memNo, artRepRptReson);
			System.out.println("insertArtRepRpt ok!");
			
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
			
			
		}
	}

}
