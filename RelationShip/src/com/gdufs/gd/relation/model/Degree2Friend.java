package com.gdufs.gd.relation.model;

public class Degree2Friend {
	private String hostNum;
	private String degree2Num;// 二度朋友
	private String middle;// 中间人

	public Degree2Friend(String hostNum, String degree2Num, String middle) {
		this.hostNum = hostNum;
		this.degree2Num = degree2Num;
		this.middle = middle;
	}

	public String getHostNum() {
		return hostNum;
	}

	public void setHostNum(String hostNum) {
		this.hostNum = hostNum;
	}

	public String getDegree2Num() {
		return degree2Num;
	}

	public void setDegree2Num(String degree2Num) {
		this.degree2Num = degree2Num;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((degree2Num == null) ? 0 : degree2Num.hashCode());
		result = prime * result + ((hostNum == null) ? 0 : hostNum.hashCode());
		result = prime * result + ((middle == null) ? 0 : middle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Degree2Friend other = (Degree2Friend) obj;
		if (degree2Num == null) {
			if (other.degree2Num != null)
				return false;
		} else if (!degree2Num.equals(other.degree2Num))
			return false;
		if (hostNum == null) {
			if (other.hostNum != null)
				return false;
		} else if (!hostNum.equals(other.hostNum))
			return false;
		if (middle == null) {
			if (other.middle != null)
				return false;
		} else if (!middle.equals(other.middle))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Degree2Friend [hostNum=" + hostNum + ", degree2Num="
				+ degree2Num + ", middle=" + middle + "]";
	}

}
