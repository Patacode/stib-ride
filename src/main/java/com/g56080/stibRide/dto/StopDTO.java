package com.g56080.stibRide.dto;

import com.g56080.stibRide.util.IntegerPair;

public record StopDTO(int line_id, int station_id, int order) implements DTO<IntegerPair>{

    @Override
    public IntegerPair key(){
        return new IntegerPair(line_id, station_id);
    }

    @Override
    public int hashCode(){
        return key().hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof StopDTO sdto){
            return sdto.key().equals(key());
        }

        return false;
    }
}

