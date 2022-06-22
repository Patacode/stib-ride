package com.g56080.stibRide.dto;

import java.util.ArrayList;
import java.util.List;

public record LineDTO(int id, List<StationDTO> stations) implements DTO<Integer>{

    public LineDTO(int id){
        this(id, new ArrayList<>());
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
        if(obj instanceof LineDTO ldto){
            return ldto.key().equals(key());
        }

        return false;
    }
}

