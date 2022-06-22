package com.g56080.stibRide.view;

import com.g56080.stibRide.dto.StationDTO;
import com.g56080.stibRide.repository.StationRepository;

import javafx.util.StringConverter;

public class StationStringConverter extends StringConverter<StationDTO>{

    @Override
    public StationDTO fromString(String str){
        StationRepository sr = new StationRepository();
        return sr.getByName(str);
    }

    @Override
    public String toString(StationDTO sdto){
        return sdto == null ? "" : sdto.name();
    }
}

