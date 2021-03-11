package com.art.model;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
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

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Art;



public class ArtJDBCDAO implements ArtDAO_interface{
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/seenema?useSSL=false&serverTimezone=Asia/Taipei&";
	public static final String USER = "root";
	private static final String PASSWORD = "123456";
	
	//使用JDBC
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	
	//SQL指令
	private static final String INSERT_STMT=
			"INSERT INTO ARTICLE (MEM_NO, ART_TITLE, ART_CONTENT) VALUES (?,?,?)";
	private static final String UPDATE_STMT=
			"UPDATE ARTICLE SET ART_TITLE=?, ART_CONTENT=? WHERE ART_NO=?";
	private static final String DELETE_STMT="DELETE FROM ARTICLE WHERE ART_NO=?";
	private static final String FINDBYPK_STMT="SELECT ART_NO, MEM_NO, ART_TITLE, ART_CONTENT, ART_REPLYNO, ART_TIME, ART_STATUS FROM ARTICLE WHERE ART_NO=?";	
	private static final String GETALL_STMT="SELECT ART_NO, MEM_NO, ART_TITLE, ART_CONTENT, ART_REPLYNO, ART_TIME, ART_STATUS FROM ARTICLE ORDER BY ART_NO";
	private static final String FINDBYTITLE_STMT="SELECT ART_NO, MEM_NO, ART_TITLE, ART_CONTENT, ART_REPLYNO, ART_TIME, ART_STATUS FROM ARTICLE WHERE ART_TITLE LIKE ?";
	
	@Override
	public String insert(ArtVO artVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String artNo = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT, PreparedStatement.RETURN_GENERATED_KEYS);
					
			pstmt.setInt(1, artVO.getMemNo());
			pstmt.setString(2, artVO.getArtTitle());			
			pstmt.setString(3, artVO.getArtContent());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				artNo = rs.getString(1);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();		
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
		return artNo;
	}

	@Override
	public void update(ArtVO artVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			

			pstmt.setString(1, artVO.getArtTitle());
			pstmt.setString(2, artVO.getArtContent());
			pstmt.setInt(3, artVO.getArtNo());			
			
			pstmt.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
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
	public void delete(Integer artNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setInt(1, artNo);
			pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
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
	public ArtVO findByPrimaryKey(Integer artNo) {
		ArtVO artVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FINDBYPK_STMT);
			
			pstmt.setInt(1, artNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				artVO = new ArtVO();
				artVO.setArtNo(rs.getInt("ART_NO"));
				artVO.setMemNo(rs.getInt("MEM_NO"));
				artVO.setArtTitle(rs.getString("ART_TITLE"));
				artVO.setArtContent(rs.getString("ART_CONTENT"));
				artVO.setArtReplyno(rs.getInt("ART_REPLYNO"));
				artVO.setArtTime(rs.getTimestamp("ART_TIME"));
				artVO.setArtStatus(rs.getInt("ART_STATUS"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return artVO;
		
	}	
	
	@Override
	public List<ArtVO> getAll() {
		List<ArtVO> list = new ArrayList<ArtVO>();
		ArtVO artVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GETALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				artVO = new ArtVO();
				artVO.setArtNo(rs.getInt("ART_NO"));
				artVO.setMemNo(rs.getInt("MEM_NO"));
				artVO.setArtTitle(rs.getString("ART_TITLE"));
				artVO.setArtContent(rs.getString("ART_CONTENT"));				
				artVO.setArtReplyno(rs.getInt("ART_REPLYNO"));
				artVO.setArtTime(rs.getTimestamp("ART_TIME"));
				artVO.setArtStatus(rs.getInt("ART_STATUS"));
				list.add(artVO);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
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
	public List<ArtVO> findByTitle(String artTitle) {
		List<ArtVO> list = new ArrayList<ArtVO>();
		ArtVO artVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FINDBYTITLE_STMT);
			pstmt.setString(1, "%"+artTitle+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				artVO = new ArtVO();
				artVO.setArtNo(rs.getInt("ART_NO"));
				artVO.setMemNo(rs.getInt("MEM_NO"));
				artVO.setArtTitle(rs.getString("ART_TITLE"));
				artVO.setArtContent(rs.getString("ART_CONTENT"));
				artVO.setArtReplyno(rs.getInt("ART_REPLYNO"));
				artVO.setArtTime(rs.getTimestamp("ART_TIME"));
				artVO.setArtStatus(rs.getInt("ART_STATUS"));
				list.add(artVO);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
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
	public List<ArtVO> getAll(Map<String, String[]> map) {
		List<ArtVO> list = new ArrayList<ArtVO>();
		ArtVO artVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String compositeQuerySQL = "select * from article " + 
										jdbcUtil_CompositeQuery_Art.get_WhereCondition(map) +
										"order by ART_NO ";
			pstmt = con.prepareStatement(compositeQuerySQL);
			System.out.println("ArtDAO_compositeQuerySQL:" + compositeQuerySQL ); //印出最後的SQL
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				artVO = new ArtVO();
				artVO.setArtNo(rs.getInt("ART_NO"));
				artVO.setMemNo(rs.getInt("MEM_NO"));
				artVO.setArtTitle(rs.getString("ART_TITLE"));
				artVO.setArtContent(rs.getString("ART_CONTENT"));
				artVO.setArtReplyno(rs.getInt("ART_REPLYNO"));
				artVO.setArtTime(rs.getTimestamp("ART_TIME"));
				artVO.setArtStatus(rs.getInt("ART_STATUS"));
				list.add(artVO);
			}
			System.out.println("ArtDAO_List:" + list);
		} catch (SQLException e) {
			
			e.printStackTrace();
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
	public void updateStatus(ArtVO artVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getAllMoveType() {
		// TODO Auto-generated method stub
		return null;
	}

}
