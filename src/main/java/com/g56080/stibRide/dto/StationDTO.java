package com.g56080.stibRide.dto;

import java.util.ArrayList;
import java.util.List;

public record StationDTO(int id, String name, List<LineDTO> lines) implements DTO<Integer>{

    public StationDTO(int id, String name){
        this(id, name, new ArrayList<>());
    }
    
    @Override
    public Integer key(){
        return id;
    }

    @Override
    public int hashCode(){
        return key().hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof StationDTO sdto){
            return sdto.key().equals(key());
        }

        return false;
    }
}

