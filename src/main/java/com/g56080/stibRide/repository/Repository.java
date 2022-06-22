package com.g56080.stibRide.repository;

import java.util.List;

import com.g56080.stibRide.dto.DTO;

public interface Repository<K, V extends DTO<K>>{

    V get(K key);

    default V getFull(K key){
        return get(key);
    }

    List<V> getAll();

    default K add(V value){
        throw new UnsupportedOperationException("Operation not supported");
    }

    default V remove(K key){
        throw new UnsupportedOperationException("Operation not supported");
    }
}

