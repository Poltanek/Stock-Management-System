package main.summative;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.summative.scenes.*;


public class HelloApplication extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // Initial scene: Login
        showLoginScene();

        primaryStage.setTitle("Book Stock Management System");
        primaryStage.show();
    }

    private void showLoginScene() {
        LoginScene loginScene = new LoginScene();
        Scene scene = loginScene.create(email -> showMainDashboardScene());
        primaryStage.setScene(scene);
    }

    private void showMainDashboardScene() {
        MainDashboardScene dashboard = new MainDashboardScene();
        Scene scene = dashboard.create(
                this::showStockScene,
                this::showUserManagementScene,
                this::showReportScene,
                this::showLogsScene,
                this::showLoginScene // logout
        );
        primaryStage.setScene(scene);
    }

    private void showStockScene() {
        StockManagementScene stockScene = new StockManagementScene();
        primaryStage.setScene(stockScene.create(this::showMainDashboardScene));
    }

    private void showUserManagementScene() {
        UserManagementScene userScene = new UserManagementScene();
        primaryStage.setScene(userScene.create(this::showMainDashboardScene));
    }

    private void showReportScene() {
        ReportScene reportScene = new ReportScene();
        primaryStage.setScene(reportScene.create(this::showMainDashboardScene));
    }

    private void showLogsScene() {
        LogsScene logsScene = new LogsScene();
        primaryStage.setScene(logsScene.create(this::showMainDashboardScene));
    }

    public static void main(String[] args) {
        launch();
    }
}
