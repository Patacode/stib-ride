package com.g56080.stibRide.repository;

import java.util.List;

import com.g56080.stibRide.dao.StationDAO;
import com.g56080.stibRide.dto.StationDTO;

public class StationRepository implements Repository<Integer, StationDTO>{
    
    private final StationDAO sdao;

    public StationRepository(){
        sdao = StationDAO.instance();
    }

    @Override
    public StationDTO get(Integer key){
        return sdao.select(key);
    }

    public StationDTO getByName(String name){
        return sdao.selectByName(name);
    }
    
    @Override
    public StationDTO getFull(Integer key){
        return sdao.selectFull(key);
    }

    public List<StationDTO> getNeighbors(Integer key){
        return sdao.selectNeighbors(key);    
    }

    @Override
    public List<StationDTO> getAll(){
        return sdao.selectAll();
    }
}

