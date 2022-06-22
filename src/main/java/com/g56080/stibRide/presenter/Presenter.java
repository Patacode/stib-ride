package com.g56080.stibRide.presenter;

import java.util.List;

import com.g56080.stibRide.dto.FavoriteRouteDTO;
import com.g56080.stibRide.dto.StationDTO;
import com.g56080.stibRide.model.DirectedNode;
import com.g56080.stibRide.model.Message;
import com.g56080.stibRide.model.Model;
import com.g56080.stibRide.repository.FavoriteRouteRepository;
import com.g56080.stibRide.repository.StationRepository;
import com.g56080.stibRide.util.Observable;
import com.g56080.stibRide.util.Observer;
import com.g56080.stibRide.view.View;

import javafx.application.Platform;

public class Presenter implements Observer{

    private final View view;
    private final Model model;
    private final StationRepository sr;
    private final FavoriteRouteRepository fvr;

    public Presenter(View view, Model model){
        this.model = model;
        this.view = view;
        sr = new StationRepository();
        fvr = new FavoriteRouteRepository();

        this.model.addObserver(this);
    }

    public void start(){
        view.populateStations(sr.getAll());
        view.populateFavorites(fvr.getAll());
        view.addButtonHandler(this);
    }


    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable source, Object arg){
        Platform.runLater(() -> {
            if(arg instanceof DirectedNode<?>){
                DirectedNode<Integer> dnode = (DirectedNode<Integer>) arg;
                while(dnode != null){
                    StationDTO fullstation = sr.getFull(dnode.value());
                    view.addRecord(fullstation);
                    dnode = dnode.next();
                }

                view.setSuccess("Route successfully loaded");
            } else if(arg instanceof Message msg){
                if(msg.error()){
                    view.setError(msg.message());    
                } else{
                    view.setSuccess(msg.message());
                }

                switch(msg.type()){
                    case UPDATE_SUCCESS, DELETE_SUCCESS:
                        view.populateFavorites(fvr.getAll());
                }

            } else if(arg instanceof FavoriteRouteDTO fvdto){
                view.addFavorite(fvdto);
                view.setSuccess("Favorite successfully recorded");
            }
        });
    }

    public void findShortestPath(StationDTO start, StationDTO end){
        if(start == null && end == null) view.setError("Invalid start and end");
        else if(start == null) view.setError("Invalid start");
        else if(end == null) view.setError("Invalid end");
        else model.shortestPath(start.key(), end.key());
    }

    public void addFavoriteRoute(String name, StationDTO start, StationDTO end){
        if(start == null && end == null) view.setError("Invalid start and end");
        else if(start == null) view.setError("Invalid start");
        else if(end == null) view.setError("Invalid end");
        else if(name.isEmpty()) view.setError("Invalid favorite name");
        else model.addFavoriteRoute(name, start.key(), end.key());
    }

    public void findShortestPath(FavoriteRouteDTO fvdto){
        if(fvdto == null) view.setError("Invalid favorite choice");
        else model.shortestPath(fvdto.start(), fvdto.end());
    }

    public void updateFavoriteRouteDialog(FavoriteRouteDTO fvdto){
        if(fvdto == null) view.setError("Invalid favorite choice");
        else{
            StationDTO sdto1 = sr.get(fvdto.start());
            StationDTO sdto2 = sr.get(fvdto.end());

            view.setDialogFavoriteName(fvdto.name());
            view.setDialogComboBoxValues(sdto1, sdto2);
            openDialog();
        }
    }

    public void openDialog(){
        view.showDialog();
    }

    public void closeDialog(){
        view.hideDialog();
    }

    public void updateFavorite(String oldFav, String newFav, StationDTO start, StationDTO end){
        if(start == null && end == null) view.setError("Invalid start and end");
        else if(start == null) view.setError("Invalid start");
        else if(end == null) view.setError("Invalid end");
        else{ 
            closeDialog();
            model.updateFavoriteRoute(oldFav, newFav, start.key(), end.key());
        }
    }

    public void deleteFavorite(FavoriteRouteDTO fvdto){
        if(fvdto == null) view.setError("Invalid favorite choice");
        else model.deleteFavoriteRoute(fvdto.key());
    }
}

