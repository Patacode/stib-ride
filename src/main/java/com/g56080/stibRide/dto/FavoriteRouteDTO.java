package com.g56080.stibRide.dto;

public record FavoriteRouteDTO(String name, int start, int end) implements DTO<String>{
    
    @Override
    public String key(){
        return name;
    }

    @Override
    public int hashCode(){
        return key().hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof FavoriteRouteDTO fvdto){
            return fvdto.key().equals(key());
        }

        return false;
    }
}

