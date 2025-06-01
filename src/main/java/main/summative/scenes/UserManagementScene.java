package main.summative.scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.summative.util.UserStore;

public class UserManagementScene {
    public Scene create(Runnable onBack) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        TextField userField = new TextField();
        userField.setPromptText("Enter Username");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter Password");

        Button addUser = new Button("Add User");
        Button removeUser = new Button("Remove User");
        Button backBtn = new Button("Back");

        ListView<String> userList = new ListView<>();
        updateUserList(userList);

        Label statusLabel = new Label();

        addUser.setOnAction(e -> {
            String username = userField.getText();
            String password = passwordField.getText();

            if (UserStore.addUser(username, password)) {
                statusLabel.setText("User added!");
                updateUserList(userList);
            } else {
                statusLabel.setText("User already exists.");
            }
        });

        removeUser.setOnAction(e -> {
            String username = userField.getText();
            if (UserStore.removeUser(username)) {
                statusLabel.setText("User removed.");
                updateUserList(userList);
            } else {
                statusLabel.setText("User not found.");
            }
        });

        // PENDING USERS SECTION
        Label pendingLabel = new Label("Pending Users:");
        ListView<String> pendingList = new ListView<>();
        updatePendingList(pendingList);

        Button approveBtn = new Button("Approve");
        Button rejectBtn = new Button("Reject");

        approveBtn.setOnAction(e -> {
            String selected = pendingList.getSelectionModel().getSelectedItem();
            if (selected != null && UserStore.approveUser(selected)) {
                statusLabel.setText("User approved.");
                updatePendingList(pendingList);
                updateUserList(userList);
            }
        });

        rejectBtn.setOnAction(e -> {
            String selected = pendingList.getSelectionModel().getSelectedItem();
            if (selected != null && UserStore.rejectUser(selected)) {
                statusLabel.setText("User rejected.");
                updatePendingList(pendingList);
            }
        });

        backBtn.setOnAction(e -> onBack.run());

        root.getChildren().addAll(
                userField, passwordField,
                addUser, removeUser,
                statusLabel, new Text("Users:"), userList,
                pendingLabel, pendingList, approveBtn, rejectBtn,
                backBtn
        );

        return new Scene(root, 600, 600);
    }

    private void updateUserList(ListView<String> listView) {
        listView.getItems().setAll(UserStore.getAllUsers().keySet());
    }

    private void updatePendingList(ListView<String> listView) {
        listView.getItems().setAll(UserStore.getPendingUsers().keySet());
    }
}
