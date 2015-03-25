package com.gdufs.gd.relation.model;

import java.util.HashSet;
import java.util.Set;

// 人脉转换对象
public class Rela {
	Set<String> followers = new HashSet<String>();

	Set<String> beFollowers = new HashSet<String>();

	public Set<String> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<String> followers) {
		this.followers = followers;
	}

	public Set<String> getBeFollowers() {
		return beFollowers;
	}

	public void setBeFollowers(Set<String> beFollowers) {
		this.beFollowers = beFollowers;
	}

	@Override
	public String toString() {
		return "Rela [followers=" + followers + ", beFollowers=" + beFollowers
				+ "]";
	}

}
