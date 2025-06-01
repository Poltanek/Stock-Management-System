package main.summative.scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.summative.util.StockStore;
import javafx.scene.control.ComboBox;

public class StockManagementScene {
    public Scene create(Runnable onBack) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        TextField productId = new TextField();
        TextField bookName = new TextField();
        TextField sellingPrice = new TextField();
        TextField costPrice = new TextField();

        // New ComboBox for Book Type
        ComboBox<String> bookType = new ComboBox<>();
        bookType.getItems().addAll("eBook", "Paper Back", "Hard Back");
        bookType.setPromptText("Select Book Type");

        Button invoiceBtn = new Button("Invoice Book Item");
        Button sellBtn = new Button("Sell Stock");
        Button restockBtn = new Button("Restock");
        Button backBtn = new Button("Back");

        ListView<String> stockList = new ListView<>();
        Label statusLabel = new Label();

        // Add controls to grid
        grid.add(new Label("Product ID"), 0, 0);
        grid.add(productId, 1, 0);
        grid.add(new Label("Book Name"), 0, 1);
        grid.add(bookName, 1, 1);
        grid.add(new Label("Selling Price"), 0, 2);
        grid.add(sellingPrice, 1, 2);
        grid.add(new Label("Cost Price"), 0, 3);
        grid.add(costPrice, 1, 3);
        grid.add(new Label("Book Type"), 0, 4);
        grid.add(bookType, 1, 4);

        grid.add(invoiceBtn, 0, 5);
        grid.add(sellBtn, 1, 5);
        grid.add(restockBtn, 1, 6);
        grid.add(backBtn, 0, 6);

        // Button event handlers
        invoiceBtn.setOnAction(e -> handleStockAction(productId, bookName, sellingPrice, costPrice, bookType, "Invoiced", stockList, statusLabel));
        sellBtn.setOnAction(e -> handleStockAction(productId, bookName, sellingPrice, costPrice, bookType, "Sold", stockList, statusLabel));
        restockBtn.setOnAction(e -> handleStockAction(productId, bookName, sellingPrice, costPrice, bookType, "Restocked", stockList, statusLabel));

        backBtn.setOnAction(e -> onBack.run());

        root.getChildren().addAll(grid, statusLabel, new Label("Current Stock History:"), stockList);
        updateStockList(stockList);

        return new Scene(root, 700, 550);
    }

    private void handleStockAction(TextField productId, TextField bookName, TextField sellingPrice, TextField costPrice,
                                   ComboBox<String> bookType, String action, ListView<String> stockList, Label statusLabel) {
        String id = productId.getText().trim();
        String name = bookName.getText().trim();
        String sellText = sellingPrice.getText().trim();
        String costText = costPrice.getText().trim();
        String type = bookType.getValue();

        if (id.isEmpty() || name.isEmpty() || sellText.isEmpty() || costText.isEmpty() || type == null) {
            statusLabel.setText("Error: All fields including book type must be filled.");
            return;
        }

        double sellPrice, costPriceVal;
        try {
            sellPrice = Double.parseDouble(sellText);
            costPriceVal = Double.parseDouble(costText);
        } catch (NumberFormatException ex) {
            statusLabel.setText("Error: Selling and Cost Price must be valid numbers.");
            return;
        }

        if (sellPrice < 0 || costPriceVal < 0) {
            statusLabel.setText("Error: Prices cannot be negative.");
            return;
        }

        if (sellPrice < costPriceVal) {
            statusLabel.setText("Warning: Selling Price is less than Cost Price.");
        }

        String item = String.format("%s: %s [%s] (Sell: $%.2f, Cost: $%.2f) - [%s]",
                id, name, type, sellPrice, costPriceVal, action);
        StockStore.addStock(item);

        statusLabel.setText("Item " + action.toLowerCase() + " successfully.");
        updateStockList(stockList);
        clearFields(productId, bookName, sellingPrice, costPrice);
        bookType.setValue(null);
    }

    private void updateStockList(ListView<String> listView) {
        listView.getItems().setAll(StockStore.getAllStock());
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
}
