package com.g56080.stibRide.view;

import java.util.Arrays;
import java.util.List;

import org.controlsfx.control.SearchableComboBox;

import com.g56080.stibRide.dto.FavoriteRouteDTO;
import com.g56080.stibRide.dto.StationDTO;
import com.g56080.stibRide.presenter.Presenter;
import com.g56080.stibRide.repository.StationRepository;

import javafx.beans.property.SimpleStringProperty;

import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.util.Duration;
import javafx.util.StringConverter;

public class View{ // Fxml controller

    @FXML
    private DialogPane favUpdateDialog;

    @FXML
    private SearchableComboBox<StationDTO> startChoice;

    @FXML
    private SearchableComboBox<StationDTO> endChoice;

    @FXML
    private SearchableComboBox<FavoriteRouteDTO> favoriteChoice;

    @FXML
    private SearchableComboBox<StationDTO> favUpdate_startChoice;

    @FXML
    private SearchableComboBox<StationDTO> favUpdate_endChoice;

    @FXML
    private Label info;

    @FXML
    private Button searchBtn;

    @FXML
    private Button favoriteBtn;

    @FXML
    private Button loadBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Label deleteBtnWrapper;

    @FXML
    private TableView<StationDTO> resultTable;

    @FXML
    private TableColumn<StationDTO, String> stationsCol;

    @FXML
    private TableColumn<StationDTO, String> linesCol;

    @FXML
    private TextField textFieldFavName;

    @FXML
    private TextField favUpdate_tfname;

    public void initialize(){

        // Table view
        stationsCol.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().name()));
        linesCol.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().lines().stream().map(v -> v.key()).toList().toString()));

        // choice boxes
        StringConverter<StationDTO> converter = new StationStringConverter();
        startChoice.setConverter(converter);
        endChoice.setConverter(converter);
        favUpdate_startChoice.setConverter(converter);
        favUpdate_endChoice.setConverter(converter);
        favoriteChoice.setConverter(new FavoriteRouteStringConverter());

        // text field
        textFieldFavName.setPromptText("Name...");

        // delete button
        Tooltip tooltip = new Tooltip("Coming soon");
        tooltip.setShowDelay(new Duration(100));
        deleteBtnWrapper.setTooltip(tooltip);
        deleteBtn.setDisable(true);
    }

    public void populateStations(List<StationDTO> stations){
        stations.forEach(station -> {
            startChoice.getItems().add(station);
            endChoice.getItems().add(station);
            favUpdate_startChoice.getItems().add(station);
            favUpdate_endChoice.getItems().add(station);
        });

        startChoice.setValue(startChoice.getItems().get(0));
        endChoice.setValue(endChoice.getItems().get(0));
    }

    public void populateFavorites(List<FavoriteRouteDTO> favorites){
        favoriteChoice.getItems().setAll(favorites.toArray(size -> new FavoriteRouteDTO[size]));
    }

    public void addButtonHandler(Presenter presenter){
        searchBtn.setOnAction(ev -> {
            info.setText("");
            resultTable.getItems().clear();
            presenter.findShortestPath(startChoice.getValue(), endChoice.getValue());
        });

        favoriteBtn.setOnAction(ev -> {
            info.setText("");
            presenter.addFavoriteRoute(textFieldFavName.getText(), startChoice.getValue(), endChoice.getValue()); 
        });

        loadBtn.setOnAction(ev -> {
            info.setText("");
            resultTable.getItems().clear();
            presenter.findShortestPath(favoriteChoice.getValue());
        });

        updateBtn.setOnAction(ev -> {
            info.setText("");
            presenter.updateFavoriteRouteDialog(favoriteChoice.getValue());
        });

        deleteBtn.setOnAction(ev -> {
            info.setText("");
            presenter.deleteFavorite(favoriteChoice.getValue());
        });

        favUpdateDialog.getButtonTypes().forEach(btn -> {
            String btnText = btn.getText().toLowerCase();
            switch(btnText){
                case "apply":
                    favUpdateDialog.lookupButton(btn).setOnMouseClicked(ev -> {
                        presenter.updateFavorite(
                                favUpdate_tfname.getPromptText(), 
                                favUpdate_tfname.getText(), 
                                favUpdate_startChoice.getValue(), 
                                favUpdate_endChoice.getValue());
                    });
                    break;
                case "close": favUpdateDialog.lookupButton(btn).setOnMouseClicked(ev -> presenter.closeDialog());
            }
        });
    }

    public void addRecord(StationDTO station){
        resultTable.getItems().add(station);
    }

    public void addFavorite(FavoriteRouteDTO fvdto){
        favoriteChoice.getItems().add(fvdto);
    }

    public void setError(String str){
        info.setTextFill(Color.RED);
        info.setText(str);
    }

    public void setSuccess(String str){
        info.setTextFill(Color.GREEN);
        info.setText(str);
    }

    public void setDefaultValueComboBox(StationDTO origin, StationDTO dest){
        favUpdate_startChoice.setValue(origin);
        favUpdate_endChoice.setValue(dest);
    }

    public void setDialogFavoriteName(String name){
        favUpdate_tfname.setText("");
        favUpdate_tfname.setPromptText(name);
    }

    public void setDialogComboBoxValues(StationDTO origin, StationDTO dest){
        favUpdate_startChoice.setValue(origin);
        favUpdate_endChoice.setValue(dest);
    }
    
    public void showDialog(){
        favUpdateDialog.setOpacity(1);
        favUpdateDialog.setDisable(false);
    }

    public void hideDialog(){
        favUpdateDialog.setOpacity(0);
        favUpdateDialog.setDisable(true);
    }
}

