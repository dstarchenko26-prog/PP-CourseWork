module ua.nulp.trainmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires java.mail;

    requires javafx.graphics;

    requires org.junit.jupiter.api;
    requires org.junit.jupiter.engine;
    requires org.junit.platform.commons;
    requires org.junit.platform.launcher;

    opens ua.nulp.trainmanager to javafx.fxml, org.testfx.junit5, org.junit.platform.commons;
    exports ua.nulp.trainmanager;

    opens ua.nulp.trainmanager.DPL.wagons to org.junit.platform.commons;
    opens ua.nulp.trainmanager.DPL.train to org.junit.platform.commons;
    opens ua.nulp.trainmanager.DPL.params to org.junit.platform.commons;
    opens ua.nulp.trainmanager.DPL.database to org.junit.platform.commons;
    opens ua.nulp.trainmanager.DPL to org.junit.platform.commons;

    exports ua.nulp.trainmanager.UI.controllers;
    opens ua.nulp.trainmanager.UI.controllers to javafx.fxml, org.testfx.junit5, org.junit.platform.commons;
}
