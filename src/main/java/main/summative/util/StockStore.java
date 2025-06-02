package main.summative.util;

import java.util.ArrayList;
import java.util.List;

public class StockStore {
    private static final List<String> stockHistory = new ArrayList<>();

    public static void addStock(String stockInfo) {
        stockHistory.add(stockInfo);
    }

    public static List<String> getAllStock() {
        return new ArrayList<>(stockHistory);
    }

    public static boolean hasProductId(String productId) {
        return stockHistory.stream()
                .anyMatch(item -> item.startsWith(productId + ":"));
    }
}
