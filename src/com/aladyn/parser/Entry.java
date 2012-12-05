package com.aladyn.parser;

import java.util.Map;

/**
 * MyEntry est un couple clé valeur
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 * 
 * @see Map.Entry
 * @param <K> la clé
 * @param <V> la valeur
 */
final class MyEntry<K, V> implements Map.Entry<K, V> {
	
    private final K key;
    private V value;

    public MyEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
}
