package com.g56080.stibRide.repository;

import java.util.List;

import com.g56080.stibRide.dao.LineDAO;
import com.g56080.stibRide.dto.LineDTO;

public class LineRepository implements Repository<Integer, LineDTO>{
    
    private final LineDAO sdao;

    public LineRepository(){
        sdao = LineDAO.instance();
    }

    @Override
    public LineDTO get(Integer key){
        return sdao.select(key);
    }
    
    @Override
    public LineDTO getFull(Integer key){
        return sdao.selectFull(key);
    }

    @Override
    public List<LineDTO> getAll(){
        return sdao.selectAll();
    }
}

