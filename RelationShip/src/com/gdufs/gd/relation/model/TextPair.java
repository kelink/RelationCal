package com.gdufs.gd.relation.model;

/**
 * ��ֵ��
 * 
 * @author Administrator
 *
 * @param <K>
 * @param <V>
 */
public class TextPair<K, V> // ���ͣ���д����Ҫ���������
{
	private K key;
	private V value;

	public TextPair(K k2, V v2) {
		key = k2;
		value = v2;
	} // ��ʼ��key��value

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public void setKey(K key2) {
		this.key = key2;
	}

	public void setValue(V value2) {
		this.value = value2;
	}

	@Override
	public String toString() {
		return key + "-" + value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		TextPair other = (TextPair) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}