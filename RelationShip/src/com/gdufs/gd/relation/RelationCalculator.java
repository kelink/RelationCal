package com.gdufs.gd.relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.gdufs.gd.relation.model.Degree2Friend;
import com.gdufs.gd.relation.model.Rela;
import com.gdufs.gd.relation.model.TextPair;

public class RelationCalculator {
	public static Set<Degree2Friend> cal(
			ArrayList<TextPair<String, String>> contacts) {
		Set<Degree2Friend> degree2Friends = new HashSet<Degree2Friend>();
		HashMap<String, Rela> relasList = new HashMap<String, Rela>();

		for (TextPair<String, String> item : contacts) {
			// �ж��Ƿ���ڣ������ڴ���
			if (!relasList.containsKey(item.getKey())) {
				Rela rela = new Rela();
				relasList.put(item.getKey(), rela);
			}
			if (!relasList.containsKey(item.getValue())) {
				Rela rela = new Rela();
				relasList.put(item.getValue(), rela);
			}
			// ���ڵ�ʱ��
			relasList.get(item.getKey()).getFollowers().add(item.getValue());
			relasList.get(item.getValue()).getBeFollowers().add(item.getKey());
		}
		// ��ȡ�����������м���
		for (String middle : relasList.keySet()) {
			Rela rela = relasList.get(middle);
			for (String hostNum : rela.getBeFollowers()) {
				for (String degree2Num : rela.getFollowers()) {
					// ȥ������
					if (!hostNum.equals(degree2Num)) {
						degree2Friends.add(new Degree2Friend(hostNum,
								degree2Num, middle));
						System.out.println(hostNum + "��" + degree2Num + ",�м���"
								+ middle);
					}
				}
			}
		}

		return degree2Friends;
	}
}