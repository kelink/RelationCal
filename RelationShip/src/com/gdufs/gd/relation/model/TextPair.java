package com.gdufs.gd.relation.model;

/**
 * 键值对
 * 
 * @author Administrator
 *
 * @param <K>
 * @param <V>
 */
public class TextPair<K, V> // 泛型，填写你想要的数据类型
{
	private K key;
	private V value;

	public TextPair(K k2, V v2) {
		key = k2;
		value = v2;
	} // 初始化key和value

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