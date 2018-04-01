package org.xxz.stattools.utils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bidirectional HashMap
 * @param <K> key type
 * @param <V> value type
 * @author Denis Kostin
 */
public class BiHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    private Map<K,V> map = new HashMap<K, V>();
    private Map<V,K> inversedMap = new HashMap<V, K>();

    @Override
    public V put(K k, V v) {
        map.put(k, v);
        inversedMap.put(v, k);
        return v;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public V get(Object k) {
        return map.get(k);
    }

    public K getKey(V v) {
        return inversedMap.get(v);
    }

}