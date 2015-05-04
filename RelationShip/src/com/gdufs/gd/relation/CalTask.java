package com.gdufs.gd.relation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import com.gdufs.gd.relation.db.ConnectionPool;
import com.gdufs.gd.relation.model.Degree2Friend;
import com.gdufs.gd.relation.model.TextPair;

public class CalTask implements Runnable {

	@Override
	public void run() {
		execute();
	}

	public void execute() {
		// get All contact
		long time = System.currentTimeMillis();
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		ArrayList<TextPair<String, String>> contacts = null;
		System.out.println("开始更新二度人脉关系...");
		if (connection != null) {
			try {
				contacts = RelationDaol.getAllContact(connection);

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("获取Contact 失败");
			}
			try {
				// calculate relation
				System.out.println("relation 计算所有关系中....");
				Set<Degree2Friend> newList = RelationCalculator.cal(contacts);
				System.out.println("计算结果："+newList);
				System.out.println("relation 新旧关系比较开始...");
				// get old relation
				Set<Degree2Friend> oldList = RelationDaol
						.getAllSecondRelation(connection);
				System.out.println("relation 比较前");
				System.out.println("oldList:---->"+oldList);
				System.out.println("newList:---->"+oldList);
				
				// compare relation
				RelationDaol.compare(oldList, newList);
				System.out.println("relation 比较结束");

				System.out.println("oldList:---->"+oldList);
				System.out.println("newList:---->"+oldList);
				
				// restore Relation
				if (!oldList.isEmpty()) {
					System.out.println("relation 删除旧的关系"
							+ RelationDaol.batchDel(connection, oldList));
				}
				if (!newList.isEmpty()) {
					RelationDaol.batchInsert(connection, newList);
					System.out.println("relation 添加新的关系"
							+ RelationDaol.batchDel(connection, oldList));
				}
				// 清理，为下次做准备
				contacts.clear();
				oldList.clear();
				newList.clear();

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Exception 发生");
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}
		System.out.println("relation 更新结束");
		System.out.println("消耗时间" + (System.currentTimeMillis() - time));
	}
}
