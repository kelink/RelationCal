package com.gdufs.gd.relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import com.gdufs.gd.relation.model.Degree2Friend;
import com.gdufs.gd.relation.model.Rela;
import com.gdufs.gd.relation.model.TextPair;

public class RelationCalculator {
	public static Set<Degree2Friend> cal(
			ArrayList<TextPair<String, String>> contacts) {
		Set<Degree2Friend> degree2Friends = new HashSet<Degree2Friend>();
		HashMap<String, Rela> relasList = new HashMap<String, Rela>();
		System.out.println(contacts);
		for (TextPair<String, String> item : contacts) {		
			// 判断是否存在，不存在创建
			if (!relasList.containsKey(item.getKey())) {
				Rela rela = new Rela();
				relasList.put(item.getKey(), rela);
			}
			if (!relasList.containsKey(item.getValue())) {
				Rela rela = new Rela();
				relasList.put(item.getValue(), rela);
			}
			// 存在的时候
			relasList.get(item.getKey()).getFollowers().add(item.getValue());
			relasList.get(item.getValue()).getBeFollowers().add(item.getKey());
		}
		System.out.println("关注者和被关注者列表："+relasList.toString());
		// 获取二度人脉和中间人
		for (String middle : relasList.keySet()) {
			System.out.println(middle);
			Rela rela = relasList.get(middle);
			for (String hostNum : rela.getBeFollowers()) {
				for (String degree2Num : rela.getFollowers()) {
					//去掉本来contact就有的
					TextPair<String, String> tempPair=new TextPair<String, String>(hostNum,degree2Num);
					if (!contacts.contains(tempPair)) {
						// 去掉自身(delete)
						if (!hostNum.equals(degree2Num)) {
							degree2Friends.add(new Degree2Friend(hostNum,
									degree2Num, middle));
							System.out.println(hostNum + "和" + degree2Num + ",中间人"
									+ middle);
						}
					}
					
				}
			}
		}

		return degree2Friends;
	}
}