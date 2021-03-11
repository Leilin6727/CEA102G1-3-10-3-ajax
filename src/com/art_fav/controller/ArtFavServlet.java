package com.art_fav.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.art_fav.model.ArtFavService;
import com.art_fav.model.ArtFavVO;


public class ArtFavServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
           
    public ArtFavServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		System.out.println("action---"+action);

		//ArtIndex.jsp呼叫，新增收藏
		if("addArtFav".equals(action)) {
			JSONArray array = new JSONArray();
			HttpSession session = request.getSession();
			
			/*====================請求參數===================*/
			System.out.println(request.getParameter("artNo")); 
			Integer artNo = Integer.parseInt(request.getParameter("artNo"));
			Integer memNo = Integer.parseInt(String.valueOf(session.getAttribute("memNo")));
			
			/*====================新增資料===================*/
			ArtFavService artFavSvc = new ArtFavService();
			artFavSvc.insertArtFav(artNo, memNo);
//			JSONObject obj = new JSONObject();
			
//			try {
//				obj.put("addArtFav", true);
//				obj.put("artNo", artNo);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			array.put(obj);
			
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();			
			
		}
		
		//ArtIndex.jsp呼叫，刪除收藏
		if("deleteArtFav".equals(action)) {
			JSONArray array = new JSONArray();
			HttpSession session = request.getSession();
			
			/*====================請求參數===================*/
			System.out.println("request.getParameter(\"artNo\")"+request.getParameter("artNo")); 
			Integer artNo = Integer.parseInt(request.getParameter("artNo"));
			System.out.println("session.getAttribute(\"memNo\")"+session.getAttribute("memNo"));
			Integer memNo = Integer.parseInt(String.valueOf(session.getAttribute("memNo")));
			
			/*====================新增資料===================*/
			ArtFavService artFavSvc = new ArtFavService();
			artFavSvc.deleteArtFav(artNo, memNo);
//			JSONObject obj = new JSONObject();
			
			/*====================回傳新增成功===================*/
//			try {
//				obj.put("addArtFav", true);
//				obj.put("artNo", artNo);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			array.put(obj);
			
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();			
			
		}
		
		//ArtIndex.jsp呼叫，檢查是否為收藏
		if("isArtFav".equals(action)) {
			JSONArray array = new JSONArray();
			HttpSession session = request.getSession();
			
			/*====================請求參數===================*/
			Integer artNo = Integer.parseInt(request.getParameter("artNo"));
			System.out.println(session.getAttribute("memNo"));
			if(session.getAttribute("memNo") != null) {
				Integer memNo = Integer.parseInt(String.valueOf(session.getAttribute("memNo")));				
			
			/*====================查詢資料===================*/
			ArtFavService artFavSvc = new ArtFavService();
			JSONObject obj = new JSONObject();
			
			/*==============放入JSONObject==============*/
			if(artFavSvc.getOneArtFav(artNo, memNo) == null) {
				try {
					obj.put("isArtFav", false);
					obj.put("artNo", artNo);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else {
				try {
					obj.put("isArtFav", true);
					obj.put("artNo", artNo);
				} catch (JSONException e) {
					e.printStackTrace();
				}
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
	}

}
