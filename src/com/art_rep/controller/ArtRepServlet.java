package com.art_rep.controller;

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

import com.art_rep.model.ArtRepService;
import com.art_rep.model.ArtRepVO;
import com.mem.model.MemDAO;

public class ArtRepServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArtRepServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		System.out.println("action---"+action);
		
		//新增回覆文章
		if("addArtRep".equals(action)) {
			JSONArray array = new JSONArray();
			HttpSession session = request.getSession();
			
			/*====================請求參數===================*/
			Integer artNo = Integer.parseInt(request.getParameter("artNo"));
			Integer memNo = Integer.parseInt(String.valueOf(session.getAttribute("memNo")));
			System.out.println(request.getParameter("artRepContent"));
			String artRepContent = request.getParameter("artRepContent");
			
			/*====================新增資料===================*/
			ArtRepService artRepSvc = new ArtRepService();
			artRepSvc.insertArtRep(artNo, memNo, artRepContent);
			System.out.println("addArtRep成功！");
			
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();			
			
		}
		
		if("listAllArtRepByArtNo".equals(action)){
			JSONArray array = new JSONArray();
			HttpSession session = request.getSession();
			
			ArtRepService artRepSvc = new ArtRepService();
			MemDAO memDAO = new MemDAO();
			List<ArtRepVO> list = artRepSvc.findByArtNo(Integer.parseInt(request.getParameter("artNo")));
			
			/*==============放入JSONObject==============*/
			for(ArtRepVO artRepVO : list) {
				JSONObject obj = new JSONObject();
				try {
					obj.put("artNo", artRepVO.getArtNo());
					obj.put("memName", memDAO.findByPrimaryKey(artRepVO.getMemNo()).getMemName());
					obj.put("artRepNo", artRepVO.getArtRepNo());
					obj.put("artRepContent", artRepVO.getArtRepContent());
					obj.put("artRepTime", artRepVO.getArtRepTime());
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
		
	}

}
