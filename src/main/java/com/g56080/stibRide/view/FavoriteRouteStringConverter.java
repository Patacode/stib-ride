package com.g56080.stibRide.view;

import com.g56080.stibRide.repository.FavoriteRouteRepository;
import com.g56080.stibRide.dto.FavoriteRouteDTO;

import javafx.util.StringConverter;

public class FavoriteRouteStringConverter extends StringConverter<FavoriteRouteDTO>{

    @Override
    public FavoriteRouteDTO fromString(String str){
        FavoriteRouteRepository fvr = new FavoriteRouteRepository();
        return fvr.get(str);
    }

    @Override
    public String toString(FavoriteRouteDTO fvdto){
        return fvdto == null ? "" : fvdto.name();
    }
}

