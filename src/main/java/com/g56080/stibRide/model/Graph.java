package com.g56080.stibRide.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Graph<T>{

    private final Map<T, List<T>> graph;

    public Graph(){
        graph = new HashMap<>();
    }

    protected final Map<T, List<T>> graph(){
        return graph;
    }

    public abstract void load();
    public abstract DirectedNode<T> bfs(T start, T end);

    public List<T> neighborsOf(T value){
        List<T> neighbors = graph.get(value);
        return Collections.unmodifiableList(neighbors == null ? List.of() : neighbors);
    }
}

