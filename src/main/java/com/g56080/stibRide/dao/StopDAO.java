package com.g56080.stibRide.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import com.g56080.stibRide.dto.StopDTO;
import com.g56080.stibRide.exception.RepositoryException;
import com.g56080.stibRide.jdbc.DBManager;
import com.g56080.stibRide.util.IntegerPair;

public class StopDAO implements DAO<IntegerPair, StopDTO>{
    
    private final DBManager db;

    private StopDAO(){
        db = DBManager.instance();
    }

    @Override
    public StopDTO select(IntegerPair key){
        String query = "select * from stops where id_line = ? and id_station = ?"; 
        StopDTO sdto = null;
        try(PreparedStatement pstmt = db.prepareStatement(query)){
            pstmt.setInt(1, key.left());
            pstmt.setInt(2, key.right());
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                int line_id = res.getInt(1);
                int station_id = res.getInt(2);
                int order = res.getInt(3);
                sdto = new StopDTO(line_id, station_id, order);
            }
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return sdto;
    }

    @Override
    public List<StopDTO> selectAll(){
        String query = "select * from stops";
        List<StopDTO> sdtos = new ArrayList<>();
        try(Statement stmt = db.createStatement()){
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                int line_id = res.getInt(1);
                int station_id = res.getInt(2);
                int order = res.getInt(3);
                sdtos.add(new StopDTO(line_id, station_id, order));
            }
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return sdtos;
    }

    public static StopDAO instance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        
        private final static StopDAO INSTANCE;

        static{
            INSTANCE = new StopDAO();
        }
    }
    
}

