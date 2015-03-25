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
 * 获取所有的Contact记录
 * 
 * @author Administrator
 *
 */
public class RelationDaol {
	public static ArrayList<TextPair<String, String>> getAllContact(
			Connection conn) throws SQLException {
		String sqlString = "select hostNum,friendNum from YContact";
		// 流式读取mysql数据集
		PreparedStatement ps = conn.prepareStatement(sqlString,
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		ps.setFetchSize(Integer.MIN_VALUE);
		ResultSet resultSet = ps.executeQuery();
		ArrayList<TextPair<String, String>> result = new ArrayList<TextPair<String, String>>();
		int i = 0;
		while (resultSet.next()) {
			String hostNum = resultSet.getString("hostNum");
			String friendNum = resultSet.getString("friendNum");
			if (hostNum != null && friendNum != null) {
				TextPair<String, String> textPair = new TextPair<String, String>(
						hostNum, friendNum);
				result.add(textPair);
			}
			++i;
		}
		return result;
	}

	// 查询关系表，判断是否增加，删除还是更新

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

	// 比较
	public static void compare(Set<Degree2Friend> oldList,
			Set<Degree2Friend> newList) {
		Set<Degree2Friend> temp = new HashSet<Degree2Friend>();
		for (Degree2Friend newItem : newList) {
			// 新添加的item
			if (oldList.contains(newItem)) {
				temp.add(newItem);
			}
		}
		oldList.removeAll(temp);// 存在则移去，剩下的就是要旧表删除的
		newList.removeAll(temp);// 存在则移去，剩下的就是要新表要更新的
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
				pstmt.addBatch(); // 批量执行
			}
			// 执行批量更新
			pstmt.executeBatch();
			conn.commit();// 提交事务
			System.out.println("删除数据表成功");
			flag = true;
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				conn.rollback(); // 进行事务回滚
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return flag;
	}

	// 批量插入
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
			// 执行批量更新
			pstmt.executeBatch();
			conn.commit();
			System.out.println("插入数据表成功");
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
