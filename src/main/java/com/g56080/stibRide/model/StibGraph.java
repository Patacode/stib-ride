package com.g56080.stibRide.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.g56080.stibRide.dto.StationDTO;
import com.g56080.stibRide.repository.StationRepository;

public class StibGraph extends Graph<Integer>{

    private final StationRepository sr;

    public StibGraph(){
        sr = new StationRepository();
    }

    @Override
    public void load(){ // load the graph
        List<StationDTO> stations = sr.getAll();
        stations.forEach(station -> {
            graph().put(station.key(), new ArrayList<>());
            List<StationDTO> neighbors = sr.getNeighbors(station.key());
            neighbors.forEach(nstation -> graph().get(station.key()).add(nstation.key()));
        });
    }

    @Override
    public DirectedNode<Integer> bfs(Integer start, Integer end){
        Map<Integer, Boolean> checkedMap = new HashMap<>(); // nodes marked as processed
        LinkedList<DirectedNode<Integer>> queue = new LinkedList<>(); // nodes to be processed
        DirectedNode<Integer> current = null; // current node

        // set up
        checkedMap.put(start, true);
        queue.add(new DirectedNode<>(start));
        queue.addAll(sr.getNeighbors(start)
            .stream()
            .collect(
                () -> new ArrayList<DirectedNode<Integer>>(), 
                (c, a) -> {
                    checkedMap.put(a.key(), true);
                    c.add(new DirectedNode<Integer>(a.key(), new DirectedNode<>(start)));
                }, 
                (c1, c2) -> c1.addAll(c2)));


        // search shortest path
        while(!queue.isEmpty() && !(current = queue.poll()).value().equals(end)){
            DirectedNode<Integer> tmp = current;
            queue.addAll(sr.getNeighbors(tmp.value())
                .stream()
                .filter(v -> !checkedMap.containsKey(v.key()))
                .map(v -> {
                    checkedMap.put(v.key(), true);
                    return new DirectedNode<>(v.key(), tmp);
                }).toList());
        }

        return current;
    } 
}

