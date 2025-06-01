// RegisterScene.java
package main.summative.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.summative.util.UserStore;

public class RegisterScene {
    public Scene create(Runnable onBack) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email Address");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        Label statusLabel = new Label();

        Button registerBtn = new Button("Register");
        Button backBtn = new Button("Back");

        registerBtn.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            if (UserStore.registerUser(email, password)) {
                statusLabel.setText("Registration submitted. Awaiting admin approval.");
            } else {
                statusLabel.setText("Email already in use or pending.");
            }
        });

        backBtn.setOnAction(e -> onBack.run());

        root.getChildren().addAll(emailField, passwordField, registerBtn, backBtn, statusLabel);
        return new Scene(root, 350, 250);
    }
}
