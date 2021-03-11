package com.art.model;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Art;



public class ArtDAO implements ArtDAO_interface{
	//使用JNDI建立連線
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
	private static final String INSERT_STMT=
			"INSERT INTO ARTICLE (MEM_NO, ART_TITLE, ART_CONTENT, MOV_TYPE) VALUES (?,?,?,?)";
	private static final String UPDATE_STMT=
			"UPDATE ARTICLE SET ART_TITLE=?, ART_CONTENT=?, MOV_TYPE=? WHERE ART_NO=?";
	private static final String UPDATE_STATUS_STMT=
			"UPDATE ARTICLE SET ART_STATUS=? WHERE ART_NO=?";
	private static final String DELETE_STMT="DELETE FROM ARTICLE WHERE ART_NO=?";
	private static final String FINDBYPK_STMT="SELECT ART_NO, MEM_NO, ART_TITLE, ART_CONTENT, ART_REPLYNO, ART_TIME, ART_STATUS, MOV_TYPE FROM ARTICLE WHERE ART_NO=?";	
	private static final String GETALL_STMT="SELECT ART_NO, MEM_NO, ART_TITLE, ART_CONTENT, ART_REPLYNO, ART_TIME, ART_STATUS, MOV_TYPE FROM ARTICLE ORDER BY ART_NO";
	private static final String GETALL_MOVETYPE_STMT=
			"select CONCAT('MOV_TYPE', @s:=@s+1) movTypeIndex , aa.* from (select MOV_TYPE,min(ART_TIME) FROM Seenema.ARTICLE group by MOV_TYPE) aa, (SELECT @s:= 0) AS s";
	
	@Override
	public String insert(ArtVO artVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String artNo = null;
		try {
			con = ds.getConnection();
			String[] cols = {"artNo"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);
					
			pstmt.setInt(1, artVO.getMemNo());
			pstmt.setString(2, artVO.getArtTitle());			
			pstmt.setString(3, artVO.getArtContent());
			pstmt.setString(4, artVO.getMovType());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				artNo = rs.getString(1);
			}
			
			
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
		return artNo;
	}

	@Override
	public void update(ArtVO artVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			

			pstmt.setString(1, artVO.getArtTitle());
			pstmt.setString(2, artVO.getArtContent());
			pstmt.setString(3, artVO.getMovType());
			pstmt.setInt(4, artVO.getArtNo());			
			
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
	public void delete(Integer artNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setInt(1, artNo);
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
	public ArtVO findByPrimaryKey(Integer artNo) {
		ArtVO artVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
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
				artVO.setMovType(rs.getString("MOV_TYPE"));
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
			con = ds.getConnection();
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
				artVO.setMovType(rs.getString("MOV_TYPE"));
				list.add(artVO);
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
	public List<ArtVO> findByTitle(String artTitle) {
		List<ArtVO> list = new ArrayList<ArtVO>();
		ArtVO artVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			String FINDBYTITLE_STMT ="SELECT ART_NO, MEM_NO, ART_TITLE, ART_CONTENT, ART_REPLYNO, ART_TIME, ART_STATUS, MOV_TYPE FROM ARTICLE ";
			
			if(artTitle.trim() != null) {
				//若artTitle有值
				FINDBYTITLE_STMT += "WHERE ART_TITLE LIKE ?";
				
				pstmt = con.prepareStatement(FINDBYTITLE_STMT);
				pstmt.setString(1, "%"+artTitle+"%");
			}else {
				//若artTitle為空，則查全部
				pstmt = con.prepareStatement(FINDBYTITLE_STMT);				
			}
			
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
				artVO.setMovType(rs.getString("MOV_TYPE"));
				list.add(artVO);
				System.out.println("findByTitle_add_list_ok:"+list);
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
	public List<ArtVO> getAll(Map<String, String[]> map) {
		List<ArtVO> list = new ArrayList<ArtVO>();
		ArtVO artVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			String compositeQuerySQL = "select * from article, MEMBER where ARTICLE.MEM_NO = MEMBER.MEM_NO " + 
										jdbcUtil_CompositeQuery_Art.get_WhereCondition(map) +
										"order by ART_NO ";
			pstmt = con.prepareStatement(compositeQuerySQL);
			System.out.println("ArtDAO_compositeQuerySQL:" + compositeQuerySQL ); //印出最後的SQL
			rs = pstmt.executeQuery();
			System.out.println("map size:"+ map.size());
			while (rs.next()) {
				artVO = new ArtVO();
				artVO.setArtNo(rs.getInt("ART_NO"));
				artVO.setMemNo(rs.getInt("MEM_NO"));
				artVO.setArtTitle(rs.getString("ART_TITLE"));
				artVO.setArtContent(rs.getString("ART_CONTENT"));
				artVO.setArtReplyno(rs.getInt("ART_REPLYNO"));
				artVO.setArtTime(rs.getTimestamp("ART_TIME"));
				artVO.setArtStatus(rs.getInt("ART_STATUS"));
				artVO.setMovType(rs.getString("MOV_TYPE"));
				list.add(artVO);
			}
			System.out.println("ArtDAO_List:" + list);
			System.out.println("ArtDAO_List_size:" + list.size());
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
	public void updateStatus(ArtVO artVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS_STMT);
			
			
			pstmt.setInt(1, artVO.getArtStatus());
			pstmt.setInt(2, artVO.getArtNo());			
			
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
	public List<String> getAllMoveType() {
		List<String> moveTypeList = new ArrayList<String>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL_MOVETYPE_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {			
				moveTypeList.add(rs.getString("MOV_TYPE"));
				moveTypeList.add(rs.getString("movTypeIndex"));
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
		return moveTypeList;		
		
	}

}
