package com.art_rpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.art.model.ArtDAO;
import com.art.model.ArtService;
import com.art_rpt.model.ArtRptService;
import com.art_rpt.model.ArtRptVO;
import com.mem.model.MemDAO;


public class ArtRptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArtRptServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		System.out.println("action---"+action);
		
		//ArtIndex.jsp呼叫，新增文章檢舉
		if("addArtRpt".equals(action)) {
			JSONArray array = new JSONArray();
			HttpSession session = request.getSession();
			
			/*====================請求參數===================*/
			Integer artNo = Integer.parseInt(request.getParameter("artNo"));
			Integer memNo = Integer.parseInt(String.valueOf(session.getAttribute("memNo")));
			String artRptReson = request.getParameter("artRptReson");
			
			/*====================新增資料===================*/
			ArtRptService artRptSvc = new ArtRptService();
			artRptSvc.insertArtRpt(artNo, memNo, artRptReson);
					
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}
		
		//articlereport.jsp呼叫
		if("listAllArticleReport".equals(action)) {
			JSONArray array = new JSONArray();
			
			ArtDAO artDAO = new ArtDAO();
			ArtRptService artRptSvc = new ArtRptService();
			MemDAO memDAO = new MemDAO();
			List<ArtRptVO> list = artRptSvc.getAll();
			
			/*==============放入JSONObject==============*/
			for(ArtRptVO artRptVO : list) {
				
				JSONObject obj = new JSONObject();
				try { 
					obj.put("artNo", artRptVO.getArtNo());
					obj.put("memName", memDAO.findByPrimaryKey(artDAO.findByPrimaryKey(artRptVO.getArtNo()).getMemNo()).getMemName());
					obj.put("artRptNo", artRptVO.getArtRptNo());
					obj.put("artRptContent", artRptVO.getArtRptReson());
					obj.put("artRptTime", artRptVO.getArtRptTime());
					
					if(artRptVO.getArtRptStatus() == 0) {
						obj.put("artRptStatusButton", "確認檢舉");
					}else {
						obj.put("artRptStatusButton", "已檢舉");
					}
					obj.put("artRptStatus", artRptVO.getArtRptStatus());
					obj.put("reportMemName", memDAO.findByPrimaryKey(artRptVO.getMemNo()).getMemName());
					obj.put("artTitle", artDAO.findByPrimaryKey(artRptVO.getArtNo()).getArtTitle());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				array.put(obj);
			}

			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();			
		}
		
		//
		if("changeArticleReport".equals(action)) {
			JSONArray array = new JSONArray();
			
			/*====================請求參數===================*/
			Integer artNo = Integer.parseInt(request.getParameter("artNo"));
			Integer artRptNo = Integer.parseInt(request.getParameter("artRptNo"));
			
			/*====================修改資料===================*/
			ArtService artSvc = new ArtService();
			Integer artStatus = artSvc.getOneArt(artNo).getArtStatus();
			if(artStatus == 0) {
				artSvc.updateStatus(artNo, 1);
			}else {
				artSvc.updateStatus(artNo, 0);
			}
			System.out.println("新的ArtStatus:"+artSvc.getOneArt(artNo).getArtStatus());
			
			ArtRptService artRpeSvc = new ArtRptService();
			Integer artRptStatus = artRpeSvc.getOneArtRpt(artRptNo).getArtRptStatus();
			if(artRptStatus == 0) {
				artRpeSvc.updateArtRpt(1, artRptNo);
			}else {
				artRpeSvc.updateArtRpt(0, artRptNo);
			}
			System.out.println("新的ArtRptStatus"+artSvc.getOneArt(artNo).getArtStatus());
			
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
