package main.summative.scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class LogsScene {

    // You will probably want to fetch logs from somewhere; for now, dummy data:
    private List<String> getLogs() {
        return List.of(
                "User 'alice' logged in at 2025-06-01 10:00",
                "User 'bob' added a new book at 2025-06-01 11:15",
                "User 'alice' logged out at 2025-06-01 12:00"
        );
    }

    public Scene create(Runnable onBack) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Audit Logs");
        ListView<String> logListView = new ListView<>();
        logListView.getItems().addAll(getLogs());

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> onBack.run());

        root.getChildren().addAll(title, logListView, backBtn);

        return new Scene(root, 600, 400);
    }
}
