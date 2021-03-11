package com.art_rep_rpt.model;

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



public class ArtRepRptDAO implements ArtRepRptDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Seenema");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	//SQL指令
	private static final String INSERT_STMT="INSERT INTO ARTICLE_REPLY_REPORT (ARTREP_NO, MEM_NO, ARTREPRPT_RESON) VALUES (?, ?, ?)";
	private static final String UPDATE_STMT="UPDATE ARTICLE_REPLY_REPORT SET ARTREPRPT_STATUS=? WHERE ARTREPRPT_NO=?";
	private static final String FINDBYPK_STMT="SELECT * FROM ARTICLE_REPLY_REPORT WHERE ARTREPRPT_NO=?";
	private static final String GETALL_STMT="SELECT * FROM ARTICLE_REPLY_REPORT ORDER BY ARTREPRPT_NO";
	
	
	@Override
	public void insert(ArtRepRptVO artRepRptVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, artRepRptVO.getArtRepNo());
			pstmt.setInt(2, artRepRptVO.getMemNo());
			pstmt.setString(3, artRepRptVO.getArtRepRptReson());
			
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
	public void update(ArtRepRptVO artRepRptVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, artRepRptVO.getArtRepRptStatus());
			pstmt.setInt(2, artRepRptVO.getArtRepRptNo());
			
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
	public ArtRepRptVO findByPrimaryKey(Integer artRepRptNo) {
		ArtRepRptVO artRepRptVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDBYPK_STMT);
			pstmt.setInt(1, artRepRptNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				artRepRptVO = new ArtRepRptVO();
				artRepRptVO.setArtRepRptNo(rs.getInt("ARTREPRPT_NO"));
				artRepRptVO.setArtRepNo(rs.getInt("ARTREP_NO"));
				artRepRptVO.setMemNo(rs.getInt("MEM_NO"));
				artRepRptVO.setArtRepRptReson(rs.getString("ARTREPRPT_RESON"));
				artRepRptVO.setArtRepRptStatus(rs.getInt("ARTREPRPT_STATUS"));
				artRepRptVO.setArtRepRptTime(rs.getTimestamp("ARTREPRPT_TIME"));
				
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
		return artRepRptVO;
	}

	@Override
	public List<ArtRepRptVO> getAll() {
		List<ArtRepRptVO> list = new ArrayList<ArtRepRptVO>();
		ArtRepRptVO artRepRptVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				artRepRptVO = new ArtRepRptVO();
				artRepRptVO.setArtRepRptNo(rs.getInt("ARTREPRPT_NO"));
				artRepRptVO.setArtRepNo(rs.getInt("ARTREP_NO"));
				artRepRptVO.setMemNo(rs.getInt("MEM_NO"));
				artRepRptVO.setArtRepRptReson(rs.getString("ARTREPRPT_RESON"));
				artRepRptVO.setArtRepRptStatus(rs.getInt("ARTREPRPT_STATUS"));
				artRepRptVO.setArtRepRptTime(rs.getTimestamp("ARTREPRPT_TIME"));
				list.add(artRepRptVO);
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
	public List<ArtRepRptVO> getAll(Map<String, String[]> map) {
		List<ArtRepRptVO> list = new ArrayList<ArtRepRptVO>();
		ArtRepRptVO artRepRptVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			//SQL尚未修改
			String compositeQuerySQL = "SELECT * FROM ARTICLE_REPLY_REPORT ORDER BY ARTREPRPT_NO"; 
			pstmt = con.prepareStatement(compositeQuerySQL);
			System.out.println("ArtRepRptDAO_compositeQuerySQL:"+compositeQuerySQL); //最後SQL結果
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				artRepRptVO = new ArtRepRptVO();
				artRepRptVO.setArtRepRptNo(rs.getInt("ARTREPRPT_NO"));
				artRepRptVO.setArtRepNo(rs.getInt("ARTREP_NO"));
				artRepRptVO.setMemNo(rs.getInt("MEM_NO"));
				artRepRptVO.setArtRepRptReson(rs.getString("ARTREPRPT_RESON"));
				artRepRptVO.setArtRepRptStatus(rs.getInt("ARTREPRPT_STATUS"));
				artRepRptVO.setArtRepRptTime(rs.getTimestamp("ARTREPRPT_TIME"));				
				list.add(artRepRptVO);
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
