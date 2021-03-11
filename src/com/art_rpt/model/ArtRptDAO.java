package com.art_rpt.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ArtRptDAO implements ArtRptDAO_interface{

		private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		//SQL
		private static final String INSERT_STMT = "INSERT INTO ARTICLE_REPORT (ART_NO, MEM_NO, ARTRPT_RESON) VALUES (?, ?, ?)";
		private static final String UPDATE_STMT = "UPDATE ARTICLE_REPORT SET ARTRPT_STATUS=? WHERE ARTRPT_NO=?";
		private static final String FINDBYPK_STMT = "SELECT * FROM ARTICLE_REPORT WHERE ARTRPT_NO=?";
		private static final String GETALL_STMT = "SELECT * FROM ARTICLE_REPORT ORDER BY ARTRPT_NO";
		
		
		@Override
		public void insert(ArtRptVO artRptVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);
				pstmt.setInt(1, artRptVO.getArtNo());
				pstmt.setInt(2, artRptVO.getMemNo());
				pstmt.setString(3, artRptVO.getArtRptReson());
				
				pstmt.executeUpdate();
				
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				if(con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		@Override
		public void update(ArtRptVO artRptVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_STMT);
				pstmt.setInt(1, artRptVO.getArtRptStatus());
				pstmt.setInt(2, artRptVO.getArtRptNo());
				
				pstmt.executeUpdate();
				
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			} finally {
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}
				
				if(con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		@Override
		public ArtRptVO findByPrimaryKey(Integer artRptNo) {
			ArtRptVO artRptVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(FINDBYPK_STMT);
				pstmt.setInt(1, artRptNo);
				pstmt.executeQuery();
				
				while (rs.next()) {
					artRptVO = new ArtRptVO();
					artRptVO.setArtRptNo(rs.getInt("ARTRPT_NO"));
					artRptVO.setArtNo(rs.getInt("ART_NO"));
					artRptVO.setMemNo(rs.getInt("MEM_NO"));
					artRptVO.setArtRptReson(rs.getString("ARTRPT_RESON"));
					artRptVO.setArtRptStatus(rs.getInt("ARTRPT_STATUS"));
					artRptVO.setArtRptTime(rs.getTimestamp("ARTRPT_TIME"));
				}
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				if(con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return artRptVO;
		}
		
		@Override
		public List<ArtRptVO> getAll() {
			List<ArtRptVO> list = new ArrayList<ArtRptVO>();
			ArtRptVO artRptVO = null;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GETALL_STMT);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					artRptVO = new ArtRptVO();
					artRptVO.setArtRptNo(rs.getInt("ARTRPT_NO"));
					artRptVO.setArtNo(rs.getInt("ART_NO"));
					artRptVO.setMemNo(rs.getInt("MEM_NO"));
					artRptVO.setArtRptReson(rs.getString("ARTRPT_RESON"));
					artRptVO.setArtRptStatus(rs.getInt("ARTRPT_STATUS"));
					artRptVO.setArtRptTime(rs.getTimestamp("ARTRPT_TIME"));
					list.add(artRptVO);
				}
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				if(con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return list;
		}
		
		@Override
		public List<ArtRptVO> getAll(Map<String, String[]> map) {
			List<ArtRptVO> list = new ArrayList<ArtRptVO>();
			ArtRptVO artRptVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = ds.getConnection();
				//SQL尚未修改
				String compositeQuerySQL = "SELECT * FROM ARTICLE_REPORT ORDER BY ARTRPT_NO";
				pstmt = con.prepareStatement(compositeQuerySQL);
				System.out.println("ArtRepRptDAO_compositeQuerySQL:"+compositeQuerySQL); //最後SQL結果
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					artRptVO = new ArtRptVO();
					artRptVO.setArtRptNo(rs.getInt("ARTRPT_NO"));
					artRptVO.setArtNo(rs.getInt("ART_NO"));
					artRptVO.setMemNo(rs.getInt("MEM_NO"));
					artRptVO.setArtRptReson(rs.getString("ARTRPT_RESON"));
					artRptVO.setArtRptStatus(rs.getInt("ARTRPT_STATUS"));
					artRptVO.setArtRptTime(rs.getTimestamp("ARTRPT_TIME"));
					list.add(artRptVO);
				}
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
			} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				if(con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return list;
		}
		
}
