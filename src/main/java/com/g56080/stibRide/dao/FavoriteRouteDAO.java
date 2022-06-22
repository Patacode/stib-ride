package com.g56080.stibRide.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import com.g56080.stibRide.dto.FavoriteRouteDTO;
import com.g56080.stibRide.exception.RepositoryException;
import com.g56080.stibRide.jdbc.DBManager;

public class FavoriteRouteDAO implements DAO<String, FavoriteRouteDTO>{

    private final DBManager db;

    private FavoriteRouteDAO(){
        db = DBManager.instance();
    }

    @Override
    public FavoriteRouteDTO select(String key){
        String query = "select * from FAVORITE_ROUTE where name = ?";
        FavoriteRouteDTO fvdto = null;
        try(PreparedStatement pstmt = db.prepareStatement(query)){
            pstmt.setString(1, key);
            ResultSet res = pstmt.executeQuery();
            if(res.next()){
                String fav_name = res.getString(1);
                int start = res.getInt(2);
                int end = res.getInt(3);
                fvdto = new FavoriteRouteDTO(fav_name, start, end);
            }
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return fvdto;
    }

    @Override
    public List<FavoriteRouteDTO> selectAll(){
        String query = "select * from FAVORITE_ROUTE";
        List<FavoriteRouteDTO> fvdtos = new ArrayList<>();
        try(Statement stmt = db.createStatement()){
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                String fav_name = res.getString(1);
                int start = res.getInt(2);
                int end = res.getInt(3);
                fvdtos.add(new FavoriteRouteDTO(fav_name, start, end));
            }
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return fvdtos;
    }

    @Override
    public String insert(FavoriteRouteDTO fvdto){
        String query = "insert into FAVORITE_ROUTE values(?, ?, ?)";
        try(PreparedStatement pstmt = db.prepareStatement(query)){
            pstmt.setString(1, fvdto.name());
            pstmt.setInt(2, fvdto.start());
            pstmt.setInt(3, fvdto.end());
            pstmt.executeUpdate();
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return fvdto.key();
    }

    @Override
    public String update(FavoriteRouteDTO fvdto){
        String query = """
            update FAVORITE_ROUTE 
                set start_station = ?, end_station = ?
                where name = ?;
        """;
        try(PreparedStatement pstmt = db.prepareStatement(query)){
            pstmt.setInt(1, fvdto.start());
            pstmt.setInt(2, fvdto.end());
            pstmt.setString(3, fvdto.name());
            pstmt.executeUpdate();
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return fvdto.key();
    }

    @Override
    public FavoriteRouteDTO delete(String key){
        String query = "delete from FAVORITE_ROUTE where name = ?";
        FavoriteRouteDTO fvdto = null;
        try(PreparedStatement pstmt = db.prepareStatement(query)){
            pstmt.setString(1, key);
            pstmt.executeUpdate();
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }

        return fvdto;
    }

    public void updateName(String oldKey, String newKey){
        String query = "update FAVORITE_ROUTE set name = ? where name = ?";
        try(PreparedStatement pstmt = db.prepareStatement(query)){
            pstmt.setString(1, newKey);
            pstmt.setString(2, oldKey);
            pstmt.executeUpdate();
        } catch(SQLException exc){
            throw new RepositoryException(exc);
        }
    }

    public static FavoriteRouteDAO instance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        
        public final static FavoriteRouteDAO INSTANCE;

        static{
            INSTANCE = new FavoriteRouteDAO();
        }
    }
}

