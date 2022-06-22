package com.g56080.stibRide;

import java.io.IOException;

import com.g56080.stibRide.config.ConfigManager;
import com.g56080.stibRide.jdbc.DBManager;
import com.g56080.stibRide.model.Model;
import com.g56080.stibRide.presenter.Presenter;
import com.g56080.stibRide.view.View;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void init(){
        ConfigManager.instance().load(false);
        DBManager.instance().connect();
    }

    @Override
    public void stop(){
        DBManager.instance().close();
    }

    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxml_loader = new FXMLLoader(getClass().getResource("/fxml/scene.fxml"));
        View view = new View();
        Model model = new Model();
        Presenter presenter = new Presenter(view, model);
        Parent root = null;
        Scene scene = null;

        fxml_loader.setController(view);
        root = fxml_loader.load();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);

        presenter.start();
        stage.show();
    }
}

