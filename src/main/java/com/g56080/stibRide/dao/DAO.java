package com.g56080.stibRide.dao;

import java.util.List;

import com.g56080.stibRide.dto.DTO;

public interface DAO<K, T extends DTO<K>>{
    
    T select(K key);

    default T selectFull(K key){
        return select(key);
    }

    List<T> selectAll();

    default K update(T value){
        throw new UnsupportedOperationException("Operation not supported");
    }

    default K insert(T value){
        throw new UnsupportedOperationException("Operation not supported");
    }

    default T delete(K key){
        throw new UnsupportedOperationException("Operation not supported");
    }
}

