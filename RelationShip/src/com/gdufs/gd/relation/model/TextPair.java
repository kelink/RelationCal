package com.gdufs.gd.relation.model;

/**
 * ��ֵ��
 * 
 * @author Administrator
 *
 * @param <K>
 * @param <V>
 */
public class TextPair<K, V> // ���ͣ���д����Ҫ����������
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
}