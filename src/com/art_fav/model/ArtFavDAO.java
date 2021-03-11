package com.art_fav.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ArtFavDAO implements ArtFavDAO_interface{
	//使用JNDI建立連線池
	private static DataSource ds = null;
	static{
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	//SQL指令
	private static final String INSERT_STMT = "INSERT INTO ARTICLE_FAVORITE (ART_NO, MEM_NO) VALUES (?,?)";
	private static final String DELETE_STMT= "DELETE FROM ARTICLE_FAVORITE WHERE ART_NO=? AND MEM_NO=?";
	private static final String FINDBYPK_STMT = "SELECT * FROM ARTICLE_FAVORITE WHERE ART_NO=? AND MEM_NO=? ORDER BY ART_NO";
	private static final String FIND_BY_MENNO_STMT = "SELECT * FROM ARTICLE_FAVORITE WHERE MEM_NO=? ORDER BY ART_NO";
	
	@Override
	public void insert(ArtFavVO artFavVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, artFavVO.getArtNo());
			pstmt.setInt(2, artFavVO.getMemNo());
			
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
	public void delete(Integer artNo, Integer memNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		 try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setInt(1, artNo);
			pstmt.setInt(2, memNo);
			
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
	public ArtFavVO findByPrimaryKey(Integer artNo, Integer memNo) {
		ArtFavVO artFavVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDBYPK_STMT);
			pstmt.setInt(1, artNo);
			pstmt.setInt(2, memNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				artFavVO = new ArtFavVO();
				artFavVO.setArtNo(rs.getInt("ART_NO"));
				artFavVO.setMemNo(rs.getInt("MEM_NO"));
				artFavVO.setArtFavTime(rs.getTimestamp("ARTFAV_TIME"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
		return artFavVO;
	}

	@Override
	public List<ArtFavVO> findByMenNo(Integer memNo) {
		List<ArtFavVO> list = new ArrayList<ArtFavVO>();
		ArtFavVO artFavVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_MENNO_STMT);
			pstmt.setInt(1, memNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				artFavVO = new ArtFavVO();
				artFavVO.setArtNo(rs.getInt("ART_NO"));
				artFavVO.setMemNo(rs.getInt("MEM_NO"));
				artFavVO.setArtFavTime(rs.getTimestamp("ARTFAV_TIME"));	
				list.add(artFavVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
