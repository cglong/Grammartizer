package com.github.cglong.Grammartizer;

import java.util.Map;
import java.util.HashMap;

public class TwoKeyHashMap<K1, K2, V> extends HashMap<K1, Map<K2, V>> {
	private static final long serialVersionUID = -3289395417116167017L;

	public V get(K1 key1, K2 key2) {
		return this.get(key1).get(key2);
	}
	
	public V put(K1 key1, K2 key2, V value) {
		if (!this.containsKey(key1))
			this.put(key1, new HashMap<K2, V>());
		return this.get(key1).put(key2, value);
	}
}
