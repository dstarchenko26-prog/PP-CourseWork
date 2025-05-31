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
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.Main;
import ua.nulp.trainmanager.util.Logger;

import java.io.IOException;

public class TrainsMenuUIController {

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
        printTrains();
        addBTN.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("UI/AddTrainWindow.fxml"));
                Parent root = loader.load();
                Logger.info("Спроба додавання нового поїзда");
                Stage modalStage = new Stage();
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.setTitle("Новий поїзд");
                modalStage.setScene(new Scene(root, 700, 350));
                Image image = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/addICO.png"));
                modalStage.getIcons().add(image);
                modalStage.showAndWait();
                printTrains();
            } catch (IOException e) {
                Logger.error("Помилка при відкритті форми додавання нового поїзда", "");
            }
        });
        prevBTN.setOnAction(event -> {
            try {
                Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/MainUI.fxml"));
                Scene newScene = new Scene(newRoot, 1120, 630);
                Logger.info("Повернення до головного меню");
                DPL.FT = false;
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                Logger.error("Помилка при повернені до головного меню з управління поїздами", "");
            }
        });
        filtBTN.setOnAction(event -> {
            try {
                Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/TrainsFilterUI.fxml"));
                Scene newScene = new Scene(newRoot, 1280, 630);
                Logger.info("Зміна налаштування фільтрів");
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                Logger.error("Помилка при переході до фільтру вагонів", "");
            }
        });
    }

    private Train[] checkFilter() {

        if (DPL.FT) {
            Train[] trains = DPL.trains;
            Train[] fTrains = new Train[0];
            if (!DPL.usParams.name.isEmpty()) {
                for (int i = 0; i < trains.length; i++) {
                    if (DPL.usParams.name.equals((trains[i].getName()))) {
                        fTrains = addTrain(fTrains, trains[i]);
                    }
                }
            } else {
                fTrains = trains;
            }

            trains = fTrains;
            fTrains = new Train[0];
            if (DPL.usParams.maxSpeed == 0) {
                fTrains = trains;
            } else {
                for (int i = 0; i < trains.length; i++) {
                    if (DPL.usParams.maxSpeed >= trains[i].getSpeed()) {
                        fTrains = addTrain(fTrains, trains[i]);
                    }
                }
            }

            trains = fTrains;
            fTrains = new Train[0];
            if (DPL.usParams.minSpeed == 0) {
                fTrains = trains;
            } else {
                for (int i = 0; i < trains.length; i++) {
                    if (DPL.usParams.minSpeed <= trains[i].getSpeed()) {
                        fTrains = addTrain(fTrains, trains[i]);
                    }
                }
            }

            trains = fTrains;
            fTrains = new Train[0];
            if (DPL.usParams.maxWeight == 0) {
                fTrains = trains;
            } else {
                for (int i = 0; i < trains.length; i++) {
                    if (DPL.usParams.maxWeight >= trains[i].getWeight()) {
                        fTrains = addTrain(fTrains, trains[i]);
                    }
                }
            }

            trains = fTrains;
            fTrains = new Train[0];
            if (DPL.usParams.minTrac == 0) {
                fTrains = trains;
            } else {
                for (int i = 0; i < trains.length; i++) {
                    if (DPL.usParams.minTrac <= trains[i].getTraction()) {
                        fTrains = addTrain(fTrains, trains[i]);
                    }
                }
            }

            trains = fTrains;
            fTrains = new Train[0];
            if (DPL.usParams.maxCons == 0) {
                fTrains = trains;
            } else {
                for (int i = 0; i < trains.length; i++) {
                    if (DPL.usParams.maxCons >= trains[i].getConsumption()) {
                        fTrains = addTrain(fTrains, trains[i]);
                    }
                }
            }

            trains = fTrains;
            fTrains = new Train[0];
            if (DPL.usParams.minPass == 0) {
                fTrains = trains;
            } else {
                for (int i = 0; i < trains.length; i++) {
                    if (DPL.usParams.minPass <= trains[i].getPass()) {
                        fTrains = addTrain(fTrains, trains[i]);
                    }
                }
            }

            trains = fTrains;
            fTrains = new Train[0];
            if (DPL.usParams.minCargo == 0) {
                fTrains = trains;
            } else {
                for (int i = 0; i < trains.length; i++) {
                    if (DPL.usParams.minCargo <= trains[i].getCargo()) {
                        fTrains = addTrain(fTrains, trains[i]);
                    }
                }
            }
            return fTrains;
        } else {
            return DPL.trains;
        }
    }

    private Train[] addTrain(Train[] trains, Train train) {
        Train[] newTrains = new Train[trains.length + 1];
        for (int i = 0; i < trains.length; i++) {
            newTrains[i] = trains[i];
        }
        newTrains[trains.length] = train;
        return newTrains;
    }

    private void printTrains() {
        gridPaneC.getChildren().clear();
        Train[] trains = checkFilter();
        for (int i = 0; i < trains.length; i++) {
            GridPane trainInfo = new GridPane();
            trainInfo.setMaxWidth(Double.MAX_VALUE);
            ColumnConstraints cc1 = new ColumnConstraints();
            cc1.setPercentWidth(100/2);
            cc1.setHgrow(Priority.ALWAYS);
            RowConstraints rc1 = new RowConstraints();
            rc1.setPercentHeight(100/5);
            rc1.setVgrow(Priority.ALWAYS);
            trainInfo.getColumnConstraints().add(cc1);
            trainInfo.getColumnConstraints().add(cc1);
            trainInfo.getRowConstraints().add(rc1);
            trainInfo.getRowConstraints().add(rc1);
            trainInfo.getRowConstraints().add(rc1);
            trainInfo.getRowConstraints().add(rc1);
            trainInfo.getRowConstraints().add(rc1);
            trainInfo.getStyleClass().add("gridPaneTI");

            String name = trains[i].getName();
            Label lbl1 = new Label("Назва: " + name);
            lbl1.setMaxWidth(Double.MAX_VALUE);
            trainInfo.add(lbl1, 0, 0);

            int speed = trains[i].getSpeed();
            Label lbl2 = new Label("Швидкість: " + speed + " км/год");
            lbl2.setMaxWidth(Double.MAX_VALUE);
            trainInfo.add(lbl2, 0, 1);

            int weight = trains[i].getWeight();
            Label lbl3 = new Label("Вага: " + weight + " т.");
            lbl3.setMaxWidth(Double.MAX_VALUE);
            trainInfo.add(lbl3, 1, 1);

            int traction = trains[i].getTraction();
            Label lbl4 = new Label("Тяга: " + traction + " т.");
            lbl4.setMaxWidth(Double.MAX_VALUE);
            trainInfo.add(lbl4, 0, 2);

            int consumption = trains[i].getConsumption();
            Label lbl5 = new Label("Росхід: " + consumption + " л/год");
            lbl5.setMaxWidth(Double.MAX_VALUE);
            trainInfo.add(lbl5, 1, 2);

            int pass = trains[i].getPass();
            Label lbl6 = new Label("Пасажирів: " + pass);
            lbl6.setMaxWidth(Double.MAX_VALUE);
            trainInfo.add(lbl6, 0, 3);

            int cargo = trains[i].getCargo();
            Label lbl7 = new Label("Вантажів: " + cargo + " т.");
            lbl7.setMaxWidth(Double.MAX_VALUE);
            trainInfo.add(lbl7, 1, 3);

            int lug = trains[i].getLuggage();
            Label lbl8 = new Label("Багажу: " + lug);
            lbl8.setMaxWidth(Double.MAX_VALUE);
            trainInfo.add(lbl8, 0, 4);

            int count = trains[i].getWagons().length;
            Label lbl9 = new Label("Вагонів: " + count);
            lbl9.setMaxWidth(Double.MAX_VALUE);
            trainInfo.add(lbl9, 1, 4);

            Button delButton = new Button();
            Image image1 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/crossICO.png"));
            ImageView icon1 = new ImageView(image1);
            icon1.setFitWidth(128);
            icon1.setFitHeight(128);
            delButton.setGraphic(icon1);
            delButton.setId("trBTNdel" + i);
            delButton.setMaxWidth(Double.MAX_VALUE);
            Button redactButton = new Button();
            Image image2 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/redactICO.png"));
            ImageView icon2 = new ImageView(image2);
            icon2.setFitWidth(128);
            icon2.setFitHeight(128);
            redactButton.setGraphic(icon2);
            redactButton.setId("trBTNredact" + i);
            redactButton.setMaxWidth(Double.MAX_VALUE);

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
                DPL.delTrain(id);
                printTrains();
            });

            redactButton.setOnAction(event -> {
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
                DPL.id = id;
                try {
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("UI/TrainRedactorUI.fxml"));
                    Parent root = loader.load();
                    Logger.info("Відкрито редагування поїзда: " + DPL.trains[id].getString());
                    Stage modalStage = new Stage();
                    modalStage.initModality(Modality.APPLICATION_MODAL);
                    modalStage.setTitle("Редактор");
                    modalStage.setScene(new Scene(root, 1120, 630));
                    Image image = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/redactICO.png"));
                    modalStage.getIcons().add(image);
                    modalStage.showAndWait();
                    printTrains();
                } catch (IOException e) {
                    Logger.error("Помилка при відкритті вікна редагування поїзда", "");
                }
            });

            GridPane trainCard = new GridPane();
            trainCard.setHgap(10);
            trainCard.setVgap(10);
            ColumnConstraints cc2 = new ColumnConstraints();
            cc2.setPercentWidth(50);
            cc2.setHgrow(Priority.ALWAYS);
            ColumnConstraints cc3 = new ColumnConstraints();
            cc3.setPercentWidth(25);
            cc3.setHgrow(Priority.ALWAYS);
            trainCard.getColumnConstraints().add(cc2);
            trainCard.getColumnConstraints().add(cc3);
            trainCard.getColumnConstraints().add(cc3);
            trainCard.getStyleClass().add("gridPaneTC");

            trainCard.add(trainInfo, 0, 0);
            trainCard.add(redactButton, 1, 0);
            trainCard.add(delButton, 2, 0);

            gridPaneC.add(trainCard, 0, i);
        }
    }
}
