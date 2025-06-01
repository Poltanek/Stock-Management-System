package main.summative.scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainDashboardScene {
    public Scene create(Runnable onStocks, Runnable onUsers, Runnable onReports, Runnable onLogs, Runnable onLogout) {
        BorderPane root = new BorderPane();
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(10));

        Button stocksBtn = new Button("Stocks");
        Button usersBtn = new Button("Users");
        Button reportBtn = new Button("Report");
        Button logsBtn = new Button("Logs");
        Button logoutBtn = new Button("Logout");

        stocksBtn.setOnAction(e -> onStocks.run());
        usersBtn.setOnAction(e -> onUsers.run());
        reportBtn.setOnAction(e -> onReports.run());
        logsBtn.setOnAction(e -> onLogs.run());
        logoutBtn.setOnAction(e -> onLogout.run());

        menu.getChildren().addAll(stocksBtn, usersBtn, reportBtn, logsBtn, logoutBtn);
        root.setLeft(menu);
        return new Scene(root, 800, 600);
    }
}