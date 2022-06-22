package com.g56080.stibRide.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import com.g56080.stibRide.dto.StationDTO;
import com.g56080.stibRide.dto.LineDTO;
import com.g56080.stibRide.exception.RepositoryException;
import com.g56080.stibRide.jdbc.DBManager;

public class StationDAO implements DAO<Integer, StationDTO>{

    private final DBManager db;

    private StationDAO(){
        db = DBManager.instance();
    }

    @Override
    public StationDTO select(Integer key){
        String query = "select * from stations where id = ?"; 
        StationDTO sdto = null;
        try(PreparedStatement pstmt = db.prepareStatement(query)){
            pstmt.setInt(1, key);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                int station_id = res.getInt(1);
                String station_name = res.getString(2);
                sdto = new StationDTO(station_id, station_name);
            }
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return sdto;
    }

    public StationDTO selectByName(String name){
        String query = "select * from stations where name = ?"; 
        StationDTO sdto = null;
        try(PreparedStatement pstmt = db.prepareStatement(query)){
            pstmt.setString(1, name);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                int station_id = res.getInt(1);
                String station_name = res.getString(2);
                sdto = new StationDTO(station_id, station_name);
            }
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return sdto;
    }

    @Override
    public StationDTO selectFull(Integer key){
        String query = """
            select stations.id, name, lines.id from stops
                join lines on id_line = lines.id
                join stations on id_station = stations.id
                    where id_station = ?; 
        """;
        StationDTO sdto = null;
        try(PreparedStatement pstmt = db.prepareStatement(query)){
            pstmt.setInt(1, key);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                int station_id = res.getInt(1);
                String station_name = res.getString(2);
                sdto = new StationDTO(station_id, station_name);
                
                do{
                    int line_id = res.getInt(3);
                    sdto.lines().add(new LineDTO(line_id));
                } while(res.next());
            }
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return sdto;
    }

    public List<StationDTO> selectNeighbors(Integer key){
        String query = """
            select distinct ts.id, ts.name from stations as r
                join stops as fst on fst.id_station = r.id
                join stops as sst on sst.id_line = fst.id_line and sst.id_order in (fst.id_order - 1, fst.id_order + 1)
                join stations ts on ts.id = sst.id_station
                    where r.id = ?;
        """;
        List<StationDTO> neighbors = new ArrayList<>();
        try(PreparedStatement pstmt = db.prepareStatement(query)){
            pstmt.setInt(1, key);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                int station_id = res.getInt(1);
                String station_name = res.getString(2);
                neighbors.add(new StationDTO(station_id, station_name));
            }
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return neighbors;
    }

    @Override
    public List<StationDTO> selectAll(){
        String query = "select * from stations";
        List<StationDTO> sdtos = new ArrayList<>();
        try(Statement stmt = db.createStatement()){
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                int station_id = res.getInt(1);
                String station_name = res.getString(2);
                sdtos.add(new StationDTO(station_id, station_name));

            }
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return sdtos;
    }

    public static StationDAO instance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        
        private final static StationDAO INSTANCE;

        static{
            INSTANCE = new StationDAO();
        }
    }
}

