package main.summative.scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import main.summative.util.StockStore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ReportScene {
    public Scene create(Runnable onBack) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Button printSingle = new Button("Print Stock Info");
        Button printAll = new Button("Print All Stock Report");
        Button backBtn = new Button("Back");
        Label statusLabel = new Label();

        printSingle.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Print Stock Info");
            dialog.setHeaderText("Enter Product ID:");
            dialog.setContentText("Product ID:");

            dialog.showAndWait().ifPresent(productId -> {
                List<String> matching = StockStore.getAllStock().stream()
                        .filter(item -> item.startsWith(productId + ":"))
                        .collect(Collectors.toList());

                if (matching.isEmpty()) {
                    statusLabel.setText("No stock found with ID: " + productId);
                    return;
                }

                File file = chooseSaveFile(root, "Save Single Stock Report");
                if (file != null) {
                    try (FileWriter writer = new FileWriter(file)) {
                        for (String line : matching) {
                            writer.write(line + System.lineSeparator());
                        }
                        statusLabel.setText("Single stock report saved to " + file.getAbsolutePath());
                    } catch (IOException ex) {
                        statusLabel.setText("Error writing file: " + ex.getMessage());
                    }
                }
            });
        });

        printAll.setOnAction(e -> {
            List<String> allStock = StockStore.getAllStock();
            if (allStock.isEmpty()) {
                statusLabel.setText("No stock history available.");
                return;
            }
            File file = chooseSaveFile(root, "Save All Stock Report");
            if (file != null) {
                try (FileWriter writer = new FileWriter(file)) {
                    for (String line : allStock) {
                        writer.write(line + System.lineSeparator());
                    }
                    statusLabel.setText("All stock report saved to " + file.getAbsolutePath());
                } catch (IOException ex) {
                    statusLabel.setText("Error writing file: " + ex.getMessage());
                }
            }
        });

        backBtn.setOnAction(e -> onBack.run());

        root.getChildren().addAll(printSingle, printAll, backBtn, statusLabel);
        return new Scene(root, 500, 400);
    }

    private File chooseSaveFile(VBox parent, String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        return fileChooser.showSaveDialog(parent.getScene().getWindow());
    }
}
