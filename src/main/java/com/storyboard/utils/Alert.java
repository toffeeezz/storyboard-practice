package com.storyboard.utils;

import java.util.Objects;

public class Alert {

    public static void showWarning(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(Alert.class.getResource("/css/style.css")).toExternalForm()
        );
        alert.getDialogPane().getStyleClass().add("alert");
        alert.showAndWait();
    }
}
