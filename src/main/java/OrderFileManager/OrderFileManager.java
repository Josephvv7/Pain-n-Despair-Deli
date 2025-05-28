package OrderFileManager;

import OrderClass.Order;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderFileManager {

    public void saveReceipt(Order order) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

        java.io.File receiptsDir = new java.io.File("receipts");
        receiptsDir.mkdirs();

        String filename = "receipts/" + now.format(formatter) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(order.generateReceipt());
            System.out.println("Receipt was saved to: " + filename);
        } catch (IOException e) {
            System.out.println("Failed to save receipt");
            e.printStackTrace();
        }
    }
}
