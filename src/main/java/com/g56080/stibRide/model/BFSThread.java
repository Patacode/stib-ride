package com.g56080.stibRide.model;

import java.util.ArrayDeque;
import java.util.Deque;

import com.g56080.stibRide.util.ObservableAdapter;

public class BFSThread extends ObservableAdapter implements Runnable{

    private final Thread thread;
    private final int start, end;
    private final StibGraph graph;

    public BFSThread(StibGraph graph, int start, int end, String name){
        thread = new Thread(this, name);
        this.start = start;
        this.end = end;
        this.graph = graph;
    }

    public Thread thread(){
        return thread;
    }

    @Override
    public void run(){
        
        DirectedNode<Integer> res = graph.bfs(start, end);
        Deque<DirectedNode<Integer>> path = new ArrayDeque<>();
        DirectedNode<Integer> unode = null;
        if(res != null){ // then change node orientation (from start to finish)
            path.offerFirst(res);
            while((res = res.next()) != null && path.offerFirst(res));
            DirectedNode<Integer> dnode = path.pollFirst();
            dnode.setNext(path.peekFirst()); 
            unode = dnode;
            while(!path.isEmpty()){
                dnode = path.pollFirst();
                dnode.setNext(path.peekFirst()); 
            }
        }
        
        notifyObservers(this, unode); 
    }
}
