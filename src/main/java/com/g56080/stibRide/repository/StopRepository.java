package com.g56080.stibRide.repository;

import java.util.List;

import com.g56080.stibRide.dao.StopDAO;
import com.g56080.stibRide.dto.StopDTO;
import com.g56080.stibRide.util.IntegerPair;

public class StopRepository implements Repository<IntegerPair, StopDTO>{
    
    private final StopDAO sdao;

    public StopRepository(){
        sdao = StopDAO.instance();
    }

    @Override
    public StopDTO get(IntegerPair key){
        return sdao.select(key);
    }

    @Override
    public List<StopDTO> getAll(){
        return sdao.selectAll();
    }

}

