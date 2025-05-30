package OrderClass;

import OrderClass.Sandwich.Sandwich;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Sandwich> sandwiches = new ArrayList<>();
    private List<Drinks> drinks = new ArrayList<>();
    private List<Chips> chips = new ArrayList<>();

    public double calculateTotal() {
        double total = 0.0;

        for (Sandwich sandwich : sandwiches) {
            total += sandwich.calculatePrice();
        }

        for (Drinks drink : drinks) {
            total += drink.getPrice();
        }

        for (Chips chip : chips) {
            total += chip.getPrice();
        }

        return total;
    }

    public void addSandwich(Sandwich sandwich) {
        sandwiches.add(sandwich);
    }

    public void addDrink(Drinks drink) {
        drinks.add(drink);
    }

    public void addChips(Chips chip) {
        chips.add(chip);
    }


    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("------- Order Receipt -------\n");

        int sandwichCount = 1;
        for (Sandwich sandwich : sandwiches) {
            receipt.append("Sandwich #").append(sandwichCount++).append(":\n");
            receipt.append(sandwich.getReceiptLine()).append("\n");
        }

        for (Drinks drink : drinks) {
            receipt.append(drink.getReceiptLine()).append("\n");
        }

        for (Chips chip : chips) {
            receipt.append(chip.getReceiptLine()).append("\n");
        }

        receipt.append("\nTotal: $").append(String.format("%.2f", calculateTotal()));
        return receipt.toString();

    }
}
