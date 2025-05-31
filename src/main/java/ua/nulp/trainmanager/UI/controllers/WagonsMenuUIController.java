package ua.nulp.trainmanager.UI.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.DPL.wagons.Wagon;
import ua.nulp.trainmanager.Main;
import ua.nulp.trainmanager.util.Logger;

import java.io.IOException;

public class WagonsMenuUIController {

    @FXML
    private Button addBTN;

    @FXML
    private Button filtBTN;

    @FXML
    private Button prevBTN;

    @FXML
    private GridPane gridPaneC;

    @FXML
    public void initialize() {
        Image image1 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/addICO.png"));
        ImageView icon1 = new ImageView(image1);
        icon1.setFitWidth(128);
        icon1.setFitHeight(128);
        addBTN.setGraphic(icon1);
        Image image2 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/filterICO.png"));
        ImageView icon2 = new ImageView(image2);
        icon2.setFitWidth(128);
        icon2.setFitHeight(128);
        filtBTN.setGraphic(icon2);
        Image image3 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/prevICO.png"));
        ImageView icon3 = new ImageView(image3);
        icon3.setFitWidth(128);
        icon3.setFitHeight(128);
        prevBTN.setGraphic(icon3);
        printWagons();
        addBTN.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("UI/AddWagonWindow.fxml"));
                Parent root = loader.load();
                Logger.info("Спроба додавання нового вагона");
                Stage modalStage = new Stage();
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.setTitle("Новий вагон");
                modalStage.setScene(new Scene(root, 1120, 630));
                Image image = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/addICO.png"));
                modalStage.getIcons().add(image);
                modalStage.showAndWait();
                printWagons();
            } catch (IOException e) {
                Logger.error("Помилка при відкритті форми додавання нового вагона", "");
            }
        });
        prevBTN.setOnAction(event -> {
            try {
                Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/MainUI.fxml"));
                Scene newScene = new Scene(newRoot, 1120, 630);
                Logger.info("Повернення до головного меню");
                DPL.FW = false;
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                Logger.error("Помилка при повернені до головного меню з управління вагонами", "");
            }
        });
        filtBTN.setOnAction(event -> {
            try {
                Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/WagonsFilterUI.fxml"));
                Scene newScene = new Scene(newRoot, 1120, 630);
                Logger.info("Зміна налаштування фільтрів");
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                Logger.error("Помилка при переході до фільтру вагонів", "");
            }
        });
    }

    private Wagon[] checkFilter() {

        if (DPL.FW) {
            Wagon[] wagons = DPL.wagons;
            Wagon[] fWagons = new Wagon[0];
            if (!DPL.usParams.name.isEmpty()) {
                for (int i = 0; i < wagons.length; i++) {
                    if (DPL.usParams.name.equals((wagons[i].getName()))) {
                        fWagons = addWagon(fWagons, wagons[i]);
                    }
                }
            } else {
                fWagons = wagons;
            }

            wagons = fWagons;
            fWagons = new Wagon[0];
            if (DPL.usParams.type == 0) {
                fWagons = wagons;
            } else {
                for (int i = 0; i < wagons.length; i++) {
                    int t = 0;
                    switch (wagons[i].getClass().getName()) {
                        case "ua.nulp.trainmanager.DPL.wagons.Loc": t = 1; break;
                        case "ua.nulp.trainmanager.DPL.wagons.Cargo": t = 2; break;
                        case "ua.nulp.trainmanager.DPL.wagons.Passengers": t = 3; break;
                    }
                    if (t == DPL.usParams.type) {
                        fWagons = addWagon(fWagons, wagons[i]);
                    }
                }
            }

            wagons = fWagons;
            fWagons = new Wagon[0];
            if (DPL.usParams.maxSpeed == 0) {
                fWagons = wagons;
            } else {
                for (int i = 0; i < wagons.length; i++) {
                    if (DPL.usParams.maxSpeed >= wagons[i].getSpeed()) {
                        fWagons = addWagon(fWagons, wagons[i]);
                    }
                }
            }

            wagons = fWagons;
            fWagons = new Wagon[0];
            if (DPL.usParams.minSpeed == 0) {
                fWagons = wagons;
            } else {
                for (int i = 0; i < wagons.length; i++) {
                    if (DPL.usParams.minSpeed <= wagons[i].getSpeed()) {
                        fWagons = addWagon(fWagons, wagons[i]);
                    }
                }
            }

            wagons = fWagons;
            fWagons = new Wagon[0];
            if (DPL.usParams.maxWeight == 0) {
                fWagons = wagons;
            } else {
                for (int i = 0; i < wagons.length; i++) {
                    if (DPL.usParams.maxWeight >= wagons[i].getWeight()) {
                        fWagons = addWagon(fWagons, wagons[i]);
                    }
                }
            }

            wagons = fWagons;
            fWagons = new Wagon[0];
            if (DPL.usParams.minTrac == 0) {
                fWagons = wagons;
            } else {
                for (int i = 0; i < wagons.length; i++) {
                    if (DPL.usParams.minTrac <= wagons[i].getTraction() || wagons[i].getTraction() == 0) {
                        fWagons = addWagon(fWagons, wagons[i]);
                    }
                }
            }

            wagons = fWagons;
            fWagons = new Wagon[0];
            if (DPL.usParams.maxCons == 0) {
                fWagons = wagons;
            } else {
                for (int i = 0; i < wagons.length; i++) {
                    if (DPL.usParams.maxCons >= wagons[i].getConsumption()) {
                        fWagons = addWagon(fWagons, wagons[i]);
                    }
                }
            }

            wagons = fWagons;
            fWagons = new Wagon[0];
            if (DPL.usParams.minPass == 0) {
                fWagons = wagons;
            } else {
                for (int i = 0; i < wagons.length; i++) {
                    if (DPL.usParams.minPass <= wagons[i].getCapacity() || wagons[i].getComfort() == 0) {
                        fWagons = addWagon(fWagons, wagons[i]);
                    }
                }
            }

            wagons = fWagons;
            fWagons = new Wagon[0];
            if (DPL.usParams.minComf == 0) {
                fWagons = wagons;
            } else {
                for (int i = 0; i < wagons.length; i++) {
                    if (DPL.usParams.minComf <= wagons[i].getComfort() || wagons[i].getComfort() == 0) {
                        fWagons = addWagon(fWagons, wagons[i]);
                    }
                }
            }

            wagons = fWagons;
            fWagons = new Wagon[0];
            if (DPL.usParams.minCargo == 0) {
                fWagons = wagons;
            } else {
                for (int i = 0; i < wagons.length; i++) {
                    if (DPL.usParams.minCargo <= wagons[i].getCapacity() || wagons[i].getComfort() != 0 || wagons[i].getTraction() != 0) {
                        fWagons = addWagon(fWagons, wagons[i]);
                    }
                }
            }
            return fWagons;
        } else {
            return DPL.wagons;
        }
    }

    private Wagon[] addWagon(Wagon[] wagons, Wagon wagon) {
        Wagon[] newWagons = new Wagon[wagons.length + 1];
        for (int i = 0; i < wagons.length; i++) {
            newWagons[i] = wagons[i];
        }
        newWagons[wagons.length] = wagon;
        return newWagons;
    }

    private void printWagons() {
        gridPaneC.getChildren().clear();
        Wagon[] wagons = checkFilter();
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
                String cut = sId.substring(c, c + 1);
                int id = Integer.parseInt(cut);
                DPL.delWagon(id);
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
