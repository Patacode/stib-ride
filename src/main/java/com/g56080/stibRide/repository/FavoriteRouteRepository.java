package com.g56080.stibRide.repository;

import java.util.List;

import com.g56080.stibRide.dao.FavoriteRouteDAO;
import com.g56080.stibRide.dto.FavoriteRouteDTO;

public class FavoriteRouteRepository implements Repository<String, FavoriteRouteDTO>{

    private final FavoriteRouteDAO dao;

    public FavoriteRouteRepository(){
        dao = FavoriteRouteDAO.instance();
    }

    @Override
    public FavoriteRouteDTO get(String key){
        return dao.select(key);
    }

    @Override
    public List<FavoriteRouteDTO> getAll(){
        return dao.selectAll();
    }

    @Override
    public String add(FavoriteRouteDTO value){
        String key = null;
        if(dao.select(value.key()) != null){
            key = dao.update(value);
        } else{
            key = dao.insert(value);
        }

        return key;
    }

    @Override
    public FavoriteRouteDTO remove(String key){
        return dao.delete(key);
    }

    public void updateName(String oldKey, String newKey){
        dao.updateName(oldKey, newKey);
    }

    public boolean canUpdateName(String key){
        return dao.select(key) == null;
    }
}

