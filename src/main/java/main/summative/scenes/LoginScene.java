package main.summative.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.summative.util.AuditLog;
import main.summative.util.UserStore;

import java.util.function.Consumer;

public class LoginScene {

    // Pass in a Consumer<String> to get email on successful login
    public Scene create(Consumer<String> onLoginSuccess) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Stock Management System, Login Screen");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email Address");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (UserStore.validate(email, password)) {
                UserStore.User user = UserStore.getUser(email);
                AuditLog.recordLogin(email, user.getRole());
                onLoginSuccess.accept(email);
            } else {
                errorLabel.setText("Invalid email or password.");
            }
        });

        root.getChildren().addAll(titleLabel, emailField, passwordField, loginButton, errorLabel);

        return new Scene(root, 300, 220);
    }
}
