package com.g56080.stibRide.model;

import java.util.ArrayDeque;
import java.util.Deque;

import com.g56080.stibRide.dto.FavoriteRouteDTO;
import com.g56080.stibRide.repository.FavoriteRouteRepository;
import com.g56080.stibRide.util.Observable;
import com.g56080.stibRide.util.ObservableAdapter;
import com.g56080.stibRide.util.Observer;

public class Model extends ObservableAdapter implements Observer{

    private final StibGraph graph;

    public Model(){
        graph = new StibGraph();
        graph.load();
    }

    @Override
    public void update(Observable source, Object arg){
        notifyObservers(source, arg);
    }

    public void shortestPath(int start, int end){
        BFSThread bfs_thread = new BFSThread(graph, start, end, "BFS Thread");
        bfs_thread.addObserver(this);
        bfs_thread.thread().start();
    }

    public void addFavoriteRoute(String name, int start, int end){
        FavoriteRouteRepository frr = new FavoriteRouteRepository();
        FavoriteRouteDTO fvdto = null;
        if((fvdto = frr.get(name)) != null){ // existing favorite name
            notifyObservers(this, new Message("The given favorite name is already in used", true));
        } else{
            fvdto = new FavoriteRouteDTO(name, start, end);
            frr.add(fvdto);
            notifyObservers(this, fvdto);
        }
    }

    public void updateFavoriteRoute(String oldFav, String newFav, int start, int end){
        FavoriteRouteRepository frr = new FavoriteRouteRepository();
        if(newFav != null && !newFav.isEmpty()){ // update favorite using new name
            if(!frr.canUpdateName(newFav)){
                notifyObservers(this, new Message("New favorite name already in used", true));
            } else{
                frr.updateName(oldFav, newFav);
                frr.add(new FavoriteRouteDTO(newFav, start, end));
                notifyObservers(this, new Message("Favorite successfully updated", MessageType.UPDATE_SUCCESS));
            }
        } else{ // update favorite using old name
            frr.add(new FavoriteRouteDTO(oldFav, start, end));
            notifyObservers(this, new Message("Favorite successfully updated", MessageType.UPDATE_SUCCESS));
        }  
    }

    public void deleteFavoriteRoute(String fav){
        FavoriteRouteRepository frr = new FavoriteRouteRepository();
        frr.remove(fav);
        notifyObservers(this, new Message("Favorite successfully removed", MessageType.DELETE_SUCCESS));
    }
}

