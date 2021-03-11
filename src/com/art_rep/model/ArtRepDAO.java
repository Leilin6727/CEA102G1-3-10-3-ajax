package com.art_rep.model;

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

public class ArtRepDAO implements ArtRepDAO_interface{
	
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
	private static final String INSERT_STMT="INSERT INTO ARTICLE_REPLY (ART_NO, MEM_NO, ARTREP_CONTENT) VALUES (?, ?, ?)";
	private static final String UPDATE_STMT="UPDATE ARTICLE_REPLY SET ARTREP_CONTENT=?, ARTREP_STATUS=? WHERE ARTREP_NO=?";
	private static final String FINDBYPK_STMT="SELECT * FROM ARTICLE_REPLY WHERE ARTREP_NO=?";
	private static final String GETALL_STMT="SELECT * FROM ARTICLE_REPLY ORDER BY ARTREP_NO DESC";
	private static final String FINDBYARTNO_STMT="SELECT * FROM ARTICLE_REPLY WHERE ART_NO=?";
	
	@Override
	public void insert(ArtRepVO artRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, artRepVO.getArtNo());
			pstmt.setInt(2, artRepVO.getMemNo());
			pstmt.setString(3, artRepVO.getArtRepContent());
			
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
	public void update(ArtRepVO artRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, artRepVO.getArtRepContent());
			pstmt.setInt(2, artRepVO.getArtRepStatus());
			pstmt.setInt(3, artRepVO.getArtRepNo());
			
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
	public ArtRepVO findByPrimaryKey(Integer artRepNo) {
		ArtRepVO artRepVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDBYPK_STMT);
			pstmt.setInt(1, artRepNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				artRepVO = new ArtRepVO();
				artRepVO.setArtRepNo(rs.getInt("ARTREP_NO"));
				artRepVO.setArtNo(rs.getInt("ART_NO"));
				artRepVO.setMemNo(rs.getInt("MEM_NO"));
				artRepVO.setArtRepContent(rs.getString("ARTREP_CONTENT"));
				artRepVO.setArtRepTime(rs.getTimestamp("ARTREP_TIME"));
				artRepVO.setArtRepStatus(rs.getInt("ARTREP_STATUS"));
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
		return artRepVO;
	}

	@Override
	public List<ArtRepVO> getAll() {
		List<ArtRepVO> list = new ArrayList<ArtRepVO>();
		ArtRepVO artRepVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				artRepVO = new ArtRepVO();
				artRepVO.setArtRepNo(rs.getInt("ARTREP_NO"));
				artRepVO.setArtNo(rs.getInt("ART_NO"));
				artRepVO.setMemNo(rs.getInt("MEM_NO"));
				artRepVO.setArtRepContent(rs.getString("ARTREP_CONTENT"));
				artRepVO.setArtRepTime(rs.getTimestamp("ARTREP_TIME"));
				artRepVO.setArtRepStatus(rs.getInt("ARTREP_STATUS"));
				list.add(artRepVO);
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
	public List<ArtRepVO> getAll(Map<String, String[]> map) {
		List<ArtRepVO> list = new ArrayList<ArtRepVO>();
		ArtRepVO artRepVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			String compositeQuerySQL = "SELECT * FROM ARTICLE_REPLY ORDER BY ARTREP_NO";
			pstmt = con.prepareStatement(compositeQuerySQL);
			System.out.println("ArtRepDAO_compositeQuerySQL:" + compositeQuerySQL ); //印出最後的SQL
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				artRepVO = new ArtRepVO();
				artRepVO.setArtRepNo(rs.getInt("ARTREP_NO"));
				artRepVO.setArtNo(rs.getInt("ART_NO"));
				artRepVO.setMemNo(rs.getInt("MEM_NO"));
				artRepVO.setArtRepContent(rs.getString("ARTREP_CONTENT"));
				artRepVO.setArtRepTime(rs.getTimestamp("ARTREP_TIME"));
				artRepVO.setArtRepStatus(rs.getInt("ARTREP_STATUS"));
				list.add(artRepVO);
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
	public List<ArtRepVO> findByArtNo(Integer artNo) {
		List<ArtRepVO> list = new ArrayList<ArtRepVO>();
		ArtRepVO artRepVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDBYARTNO_STMT);
			pstmt.setInt(1, artNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				artRepVO = new ArtRepVO();
				artRepVO.setArtRepNo(rs.getInt("ARTREP_NO"));
				artRepVO.setArtNo(rs.getInt("ART_NO"));
				artRepVO.setMemNo(rs.getInt("MEM_NO"));
				artRepVO.setArtRepContent(rs.getString("ARTREP_CONTENT"));
				artRepVO.setArtRepTime(rs.getTimestamp("ARTREP_TIME"));
				artRepVO.setArtRepStatus(rs.getInt("ARTREP_STATUS"));
				list.add(artRepVO);
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
