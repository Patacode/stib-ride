package com.g56080.stibRide.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import org.sqlite.SQLiteConfig;

import com.g56080.stibRide.config.ConfigManager;

public class DBManager{

    private Connection con;
    private final String db_name;

    private DBManager(){
        //Class.forName("org.sqlite.JDBC");
        ConfigManager cm = ConfigManager.instance();
        db_name = cm.propertyOf("db.driver") + cm.propertyOf("db.url");
    }

    public void connect(){
        if(con != null)
            throw new IllegalStateException("Connection already established");

        SQLiteConfig config = null;
        try{
            config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            con = DriverManager.getConnection(db_name, config.toProperties());
        } catch(SQLException exc){
            System.err.println("Error: " + exc.getMessage());
        }
    }

    public PreparedStatement prepareStatement(String query){
        if(con == null)
            throw new IllegalStateException("Connection not yet established");

        PreparedStatement pstmt = null;
        try{
            pstmt = con.prepareStatement(query);
        } catch(SQLException exc){
            System.err.println("Error: " + exc.getMessage());
        }

        return pstmt;
    }

    public Statement createStatement(){
        if(con == null)
            throw new IllegalStateException("Connection not yet established");

        Statement stmt = null;
        try{
            stmt = con.createStatement();
        } catch(SQLException exc){
            System.err.println("Error: " + exc.getMessage());
        }

        return stmt;
    }

    public void startTransact(){
        if(con == null)
            throw new IllegalStateException("Connection not yet established");

        try{
            con.setAutoCommit(false); 
        } catch(SQLException exc){
            System.err.println("Error: " + exc.getMessage());
        } 
    }

    public void endTransact(){
        if(con == null)
            throw new IllegalStateException("Connection not yet established");
        
        try{
            con.commit();
            con.setAutoCommit(false);
        } catch(SQLException exc){
            System.err.println("Error: " + exc.getMessage());
        }
    }

    public void cancelTransact(){
        if(con == null)
            throw new IllegalStateException("Connection not yet established");

        try{
            con.rollback();
            con.setAutoCommit(false);
        } catch(SQLException exc){
            System.err.println("Error: " + exc.getMessage());
        }
    }

    public void close(){
        if(con == null)
            throw new IllegalStateException("Connection not yet established");

        try{
            con.close();
            con = null;
        } catch(SQLException exc){
            System.err.println("Error: " + exc.getMessage());
        }
    }

    public static DBManager instance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        
        public static final DBManager INSTANCE;

        static{
            INSTANCE = new DBManager();
        }
    }
}
