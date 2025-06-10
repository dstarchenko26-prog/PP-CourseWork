package ua.nulp.trainmanager.UI.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.DPL.wagons.Wagon;
import ua.nulp.trainmanager.Main;
import ua.nulp.trainmanager.util.Logger;

import java.io.IOException;

public class TrainRedactorUIController {
    @FXML
    private Button addBTN;

    @FXML
    private Button sortBTN;

    @FXML
    private Button prevBTN;

    @FXML
    private GridPane gridPaneC;

    @FXML
    private Label lblError;

    @FXML
    private Label lName;

    @FXML
    private Label lSpeed;

    @FXML
    private Label lWeight;

    @FXML
    private Label lTraction;

    @FXML
    private Label lConsumption;

    @FXML
    private Label lCargo;

    @FXML
    private Label lPass;

    @FXML
    private Label lLuggage;

    @FXML
    public void initialize() {
        Image image1 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/addICO.png"));
        ImageView icon1 = new ImageView(image1);
        icon1.setFitWidth(128);
        icon1.setFitHeight(128);
        addBTN.setGraphic(icon1);
        Image image2 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/sortICO.png"));
        ImageView icon2 = new ImageView(image2);
        icon2.setFitWidth(128);
        icon2.setFitHeight(128);
        sortBTN.setGraphic(icon2);
        Image image3 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/prevICO.png"));
        ImageView icon3 = new ImageView(image3);
        icon3.setFitWidth(128);
        icon3.setFitHeight(128);
        prevBTN.setGraphic(icon3);
        printWagons();
        addBTN.setOnAction(event -> {
            try {
                Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/AddWagonToTrainUI.fxml"));
                Scene newScene = new Scene(newRoot, 1240, 630);
                Logger.info("Додавання нового вагона");
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                Logger.error("Помилка при переході до вікна вибору вагона", "");
            }
        });
        prevBTN.setOnAction(event -> {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Logger.info("Повернення до меню поїздів");
            stage.close();
        });
        sortBTN.setOnAction(event -> {
            DPL.trains[DPL.id].sort();
            Logger.info("Вагони посортовано");
            printWagons();
        });
    }

    private void printInfo() {
        String name = DPL.trains[DPL.id].getName();
        lName.setText(name);
        int speed = DPL.trains[DPL.id].getSpeed();
        lSpeed.setText("Швидкість: " + speed + " км/год");
        int weight = DPL.trains[DPL.id].getWeight();
        lWeight.setText("Вага: " + weight + " т.");
        int traction = DPL.trains[DPL.id].getTraction();
        lTraction.setText("Тяга: " + traction + " т.");
        int consumption = DPL.trains[DPL.id].getConsumption();
        lConsumption.setText("Розхід: " + consumption + " л/год");
        int pass = DPL.trains[DPL.id].getPass();
        lPass.setText("Пасажирів: " + pass);
        int cargo = DPL.trains[DPL.id].getCargo();
        lCargo.setText("Вантажів: " + cargo + " т.");
        int lug = DPL.trains[DPL.id].getLuggage();
        lLuggage.setText("Багажу: " + lug);
    }

    private void printWagons() {
        printInfo();
        gridPaneC.getChildren().clear();
        Wagon[] wagons = DPL.trains[DPL.id].getWagons();
        for (int i = 0; i < wagons.length; i++) {
            GridPane wagonInfo = new GridPane();
            wagonInfo.setMaxWidth(Double.MAX_VALUE);
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100/6);
            rc.setVgrow(Priority.ALWAYS);
            ColumnConstraints cc1 = new ColumnConstraints();
            cc1.setPercentWidth(100);
            cc1.setHgrow(Priority.ALWAYS);
            wagonInfo.getColumnConstraints().add(cc1);
            wagonInfo.getRowConstraints().add(rc);
            wagonInfo.getRowConstraints().add(rc);
            wagonInfo.getRowConstraints().add(rc);
            wagonInfo.getStyleClass().add("gridPaneWI");

            String name = wagons[i].getName();
            Label lbl1 = new Label("Назва: " + name);
            lbl1.setMaxWidth(Double.MAX_VALUE);
            wagonInfo.add(lbl1, 0, 0);

            int speed = wagons[i].getSpeed();
            Label lbl2 = new Label("Швидкість: " + speed + " км/год");
            lbl2.setMaxWidth(Double.MAX_VALUE);
            wagonInfo.add(lbl2, 0, 1);

            int weight = wagons[i].getWeight();
            Label lbl3 = new Label("Вага: " + weight + " т.");
            lbl3.setMaxWidth(Double.MAX_VALUE);
            wagonInfo.add(lbl3, 0, 2);

            int param1 = 0;
            int param2 = 0;
            int param3 = 0;
            Label lbl4;
            Label lbl5;
            Label lbl6;

            if (wagons[i].getClass().getName().equals("ua.nulp.trainmanager.DPL.wagons.Loc")) {
                param1 = wagons[i].getTraction();
                lbl4 = new Label("Тяга: " + param1 + " т.");
                lbl4.setMaxWidth(Double.MAX_VALUE);
                wagonInfo.add(lbl4, 0, 3);

                param2 = wagons[i].getConsumption();
                lbl5 = new Label("Розхід: " + param2 + " л/(т * год)");
                lbl5.setMaxWidth(Double.MAX_VALUE);
                wagonInfo.add(lbl5, 0, 4);

                wagonInfo.getRowConstraints().add(rc);
                wagonInfo.getRowConstraints().add(rc);
            } else {
                param1 = wagons[i].getCapacity();
                wagonInfo.getRowConstraints().add(rc);
                if (wagons[i].getClass().getName().equals("ua.nulp.trainmanager.DPL.wagons.Passengers")) {
                    wagonInfo.getRowConstraints().add(rc);
                    wagonInfo.getRowConstraints().add(rc);
                    param2 = wagons[i].getComfort();
                    param3 = wagons[i].getAmountOfLuggage();
                    lbl4 = new Label("Пасажирських місць: " + param1);
                    lbl4.setMaxWidth(Double.MAX_VALUE);
                    wagonInfo.add(lbl4, 0, 3);
                    lbl5 = new Label("Комфорт:" + param2);
                    lbl5.setMaxWidth(Double.MAX_VALUE);
                    wagonInfo.add(lbl5, 0, 4);
                    lbl6 = new Label("К-сть багажу: " + param3);
                    lbl6.setMaxWidth(Double.MAX_VALUE);
                    wagonInfo.add(lbl6, 0, 5);
                } else {
                    lbl4 = new Label("Ємкість: " + param1 + " т.");
                    lbl4.setMaxWidth(Double.MAX_VALUE);
                    wagonInfo.add(lbl4, 0, 3);
                }
            }

            Button delButton = new Button();
            Image image = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/crossICO.png"));
            ImageView icon = new ImageView(image);
            icon.setFitWidth(128);
            icon.setFitHeight(128);
            delButton.setGraphic(icon);
            delButton.setId("wagBTNdel" + i);
            delButton.setMaxWidth(Double.MAX_VALUE);
            delButton.setOnAction(event -> {
                String sId = ((Button) event.getSource()).getId();
                int c = 0;
                for (int j = sId.length() - 1; j > 0; j--) {
                    if (!Character.isDigit(sId.charAt(j))) {
                        continue;
                    }
                    c = j;
                }
                String cut = sId.substring(c, sId.length());
                int id = Integer.parseInt(cut);

                if (!DPL.trains[DPL.id].preDel(id)) {
                    lblError.setText("Вага перевищить тягу");
                    return;
                }
                lblError.setText("");
                Logger.info("У поїзда №" + DPL.id + " видалено вагон " + id);
                DPL.trains[DPL.id].del(id);

                printWagons();
            });

            GridPane wagonCard = new GridPane();
            ColumnConstraints cc2 = new ColumnConstraints();
            cc2.setPercentWidth(70);
            cc2.setHgrow(Priority.ALWAYS);
            ColumnConstraints cc3 = new ColumnConstraints();
            cc3.setPercentWidth(30);
            cc3.setHgrow(Priority.ALWAYS);
            wagonCard.getColumnConstraints().add(cc2);
            wagonCard.getColumnConstraints().add(cc3);
            wagonCard.getStyleClass().add("gridPaneWC");

            wagonCard.add(wagonInfo, 0, 0);
            wagonCard.add(delButton, 1, 0);

            int c = i % 2;
            int r = i / 2;

            gridPaneC.add(wagonCard, c, r);
        }
    }
}
