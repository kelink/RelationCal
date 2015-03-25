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
		System.out.println("��ʼ���¶���������ϵ...");
		if (connection != null) {
			try {
				contacts = RelationDaol.getAllContact(connection);

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("��ȡContact ʧ��");
			}
			try {
				// calculate relation
				System.out.println("relation �������й�ϵ��....");
				Set<Degree2Friend> newList = RelationCalculator.cal(contacts);

				System.out.println("relation �¾ɹ�ϵ�ȽϿ�ʼ...");
				// get old relation
				Set<Degree2Friend> oldList = RelationDaol
						.getAllSecondRelation(connection);
				// compare relation
				RelationDaol.compare(oldList, newList);
				System.out.println("relation �ȽϽ���");

				// restore Relation
				if (!oldList.isEmpty()) {
					System.out.println("relation ɾ���ɵĹ�ϵ"
							+ RelationDaol.batchDel(connection, oldList));
				}
				if (!newList.isEmpty()) {
					RelationDaol.batchInsert(connection, newList);
					System.out.println("relation ����µĹ�ϵ"
							+ RelationDaol.batchDel(connection, oldList));
				}
				// ����Ϊ�´���׼��
				contacts.clear();
				oldList.clear();
				newList.clear();

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Exception ����");
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		System.out.println("relation ���½���");
		System.out.println("����ʱ��" + (System.currentTimeMillis() - time));
	}
}
