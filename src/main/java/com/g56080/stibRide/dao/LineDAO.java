package com.g56080.stibRide.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import com.g56080.stibRide.dto.LineDTO;
import com.g56080.stibRide.dto.StationDTO;
import com.g56080.stibRide.exception.RepositoryException;
import com.g56080.stibRide.jdbc.DBManager;

public class LineDAO implements DAO<Integer, LineDTO>{
    
    private final DBManager db;

    private LineDAO(){
        db = DBManager.instance();
    }

    @Override
    public LineDTO select(Integer key){
        String query = "select * from lines where id = ?"; 
        LineDTO sdto = null;
        try(PreparedStatement pstmt = db.prepareStatement(query)){
            pstmt.setInt(1, key);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                int line_id = res.getInt(1);
                sdto = new LineDTO(line_id);
            }
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return sdto;
    }

    @Override
    public LineDTO selectFull(Integer key){
        String query = """
            select lines.id, stations.id, name from stops
                join lines on id_line = lines.id
                join stations on id_station = stations.id
                    where id_line = ?;
        """;
        LineDTO ldto = null;
        try(PreparedStatement pstmt = db.prepareStatement(query)){
            pstmt.setInt(1, key);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                int line_id = res.getInt(1);
                ldto = new LineDTO(line_id);

                do{
                    int station_id = res.getInt(2);
                    String station_name = res.getString(3);
                    ldto.stations().add(new StationDTO(station_id, station_name));
                } while(res.next());
            }
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return ldto;
    }

    @Override
    public List<LineDTO> selectAll(){
        String query = "select * from lines";
        List<LineDTO> sdtos = new ArrayList<>();
        try(Statement stmt = db.createStatement()){
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                int line_id = res.getInt(1);
                sdtos.add(new LineDTO(line_id));

            }
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return sdtos;
    }

    public static LineDAO instance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        
        public final static LineDAO INSTANCE;

        static{
            INSTANCE = new LineDAO();
        }
    }
}
