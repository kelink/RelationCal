package com.gdufs.gd.relation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.gdufs.gd.relation.model.Degree2Friend;
import com.gdufs.gd.relation.model.TextPair;

/**
 * 关系查找
 * 
 * @author Administrator
 *
 */
public class RelationDaol {
	public static ArrayList<TextPair<String, String>> getAllContact(
			Connection conn) throws SQLException {
		String sqlString = "select hostNum,friendNum from YContact where isSysUser=1";
		PreparedStatement ps = conn.prepareStatement(sqlString,
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		ps.setFetchSize(Integer.MIN_VALUE);
		ResultSet resultSet = ps.executeQuery();
		ArrayList<TextPair<String, String>> result = new ArrayList<TextPair<String, String>>();
		while (resultSet.next()) {
			String hostNum = resultSet.getString("hostNum");
			String friendNum = resultSet.getString("friendNum");
			if (hostNum != null && friendNum != null) {
				TextPair<String, String> textPair = new TextPair<String, String>(
						hostNum, friendNum);
				result.add(textPair);
			}
		}
		return result;
	}

	//获取所有的二度人脉关系
	public static Set<Degree2Friend> getAllSecondRelation(Connection conn)
			throws SQLException {
		Set<Degree2Friend> result = new HashSet<Degree2Friend>();
		String sqlString = "select hostNum,friendNum,middle from YRelationsecond";
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery(sqlString);
		while (resultSet.next()) {
			String hostNum = resultSet.getString("hostNum");
			String friendNum = resultSet.getString("friendNum");
			String middle = resultSet.getString("middle");
			if (hostNum != null && friendNum != null) {
				result.add(new Degree2Friend(hostNum, friendNum, middle));
			}
		}
		return result;
	}

	//比较
	public static void compare(Set<Degree2Friend> oldList,
			Set<Degree2Friend> newList) {
		Set<Degree2Friend> temp = new HashSet<Degree2Friend>();
		for (Degree2Friend newItem : newList) {
			// ����ӵ�item
			if (oldList.contains(newItem)) {
				temp.add(newItem);
			}
		}
		oldList.removeAll(temp);// ��������ȥ��ʣ�µľ���Ҫ�ɱ�ɾ���
		newList.removeAll(temp);// ��������ȥ��ʣ�µľ���Ҫ�±�Ҫ���µ�
		temp = null;
	}

	// 批量删除
	public static boolean batchDel(Connection conn, Set<Degree2Friend> oldList) {
		String sql = "delete From yrelationsecond where hostNum=? and friendNum=? and middle=?";
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			for (Degree2Friend item : oldList) {
				pstmt.setString(1, item.getHostNum());
				pstmt.setString(2, item.getDegree2Num());
				pstmt.setString(3, item.getMiddle());
				pstmt.addBatch(); 
			}
			pstmt.executeBatch();
			conn.commit();
			System.out.println("batchDel scuuess");
			flag = true;
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				conn.rollback(); 
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return flag;
	}

	// ��������
	public static boolean batchInsert(Connection conn, Set<Degree2Friend> list) {
		String sql = "INSERT INTO yrelationsecond (hostNum, friendNum, middle) VALUES (?,?,?);";
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			for (Degree2Friend degree2Friend : list) {
				pstmt.setString(1, degree2Friend.getHostNum());
				pstmt.setString(2, degree2Friend.getDegree2Num());
				pstmt.setString(3, degree2Friend.getMiddle());
				pstmt.addBatch();
			}
			// ִ����������
			pstmt.executeBatch();
			conn.commit();
			System.out.println("������ݱ�ɹ�");
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return flag;
	}
}
