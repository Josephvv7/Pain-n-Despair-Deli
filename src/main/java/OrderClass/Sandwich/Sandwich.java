package OrderClass.Sandwich;

import OrderClass.Sandwich.Toppings.Cheese;
import OrderClass.Sandwich.Toppings.Meats;
import OrderClass.Sandwich.Toppings.ToppingsA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sandwich {
    private int size;
    private String breadType;
    private boolean isToasted;
    private List<ToppingsA> toppings;
    private boolean hasMeat = false;
    private boolean hasCheese = false;
    private boolean extraMeat;
    private boolean extraCheese;
    private Map<String, Integer> extraMeats = new HashMap<>();
    private Map<String, Integer> extraCheeses = new HashMap<>();

    public Sandwich(int size, String breadType) {
        this.size = size;
        this.breadType = breadType;
        this.toppings = new ArrayList<>();
    }

    public void setToasted(boolean toasted) {
        this.isToasted = toasted;
    }

    public boolean isToasted() {
        return isToasted;
    }

    public void setHasMeat(boolean hasMeat) {
        this.hasMeat = hasMeat;
    }

    public void setHasCheese(boolean hasCheese) {
        this.hasCheese = hasCheese;
    }

    public String getBreadType() {
        return breadType;
    }

    public void setBreadType(String breadType) {
        this.breadType = breadType;
    }

    public int getSize() {
        return size;
    }

    public List<ToppingsA> getToppings() {
        return toppings;
    }

    public void addTopping(ToppingsA topping) {
        toppings.add(topping);
    }

    public void addExtraMeat(String name, int size) {
        addTopping(new Meats(name, size));
        extraMeats.put(name, extraMeats.getOrDefault(name, 0) + 1);
    }

    public void addExtraCheese(String name, int size) {
        addTopping(new Cheese(name, size));
        extraCheeses.put(name, extraCheeses.getOrDefault(name, 0) + 1);
    }

    public double calculatePrice() {
        double total = 0.0;

        // Base price
        if (size == 4) total += 5.50;
        else if (size == 8) total += 7.00;
        else total += 8.50;

        // One time charge for initial meat selection
        if (hasMeat) {
            if (size == 4) total += 1.00;
            else if (size == 8) total += 2.00;
            else total += 3.00;
        }

        // One time charge for initial cheese selection
        if (hasCheese) {
            if (size == 4) total += 1.00;
            else if (size == 8) total += 1.50;
            else total += 2.25;
        }

        for (int count : extraMeats.values()) {
            if (size == 4) total += count * 0.50;
            else if (size == 8) total += count * 1.00;
            else total += count * 1.50;
        }

        for (int count :extraCheeses.values()) {
            if (size == 4) total += count * 0.30;
            else if (size == 8) total += count * 0.60;
            else total += count * 0.90;
        }

        return total;
    }

    public String getReceiptLine() {
        StringBuilder receipt = new StringBuilder();

        receipt.append(getSize()).append("\" ").append(breadType);
        if (isToasted()) {
            receipt.append(" (Toasted)");
        }
        receipt.append("\n");

        // This Counts all toppings (regular + extra)
        Map<String, Integer> totalCounts = new HashMap<>();
        for (ToppingsA topping : toppings) {
            String name = topping.getName();
            totalCounts.put(name, totalCounts.getOrDefault(name, 0) + 1);
        }

        Map<String, Integer> baseCounts = new HashMap<>(totalCounts);
        for (Map.Entry<String, Integer> entry : extraMeats.entrySet()) {
            String name = entry.getKey();
            int base = baseCounts.getOrDefault(name, 0);
            baseCounts.put(name, Math.max(0, base - entry.getValue()));
        }

        for (Map.Entry<String, Integer> entry : extraCheeses.entrySet()) {
            String name = entry.getKey();
            int base = baseCounts.getOrDefault(name, 0);
            baseCounts.put(name, Math.max(0, base - entry.getValue()));
        }

        for (Map.Entry<String, Integer> entry : baseCounts.entrySet()) {
            if (entry.getValue() > 0) {
                receipt.append("  - ").append(entry.getKey());
                if (entry.getValue() > 1) {
                    receipt.append(" x").append(entry.getValue());
                }
                receipt.append("\n");
            }
        }

        if (!extraMeats.isEmpty()) {
            receipt.append(" * Extra meats:\n");
            for (Map.Entry<String, Integer> entry : extraMeats.entrySet()) {
                receipt.append("    - ").append(entry.getKey()).append(" x").append(entry.getValue()).append("\n");
            }
        }

        if (!extraCheeses.isEmpty()) {
            receipt.append(" * Extra Cheeses:\n");
            for (Map.Entry<String, Integer> entry : extraCheeses.entrySet()) {
                receipt.append("    - ").append(entry.getKey()).append(" x").append(entry.getValue()).append("\n");
            }
        }

        receipt.append(" Sandwich Total: $").append(String.format("%.2f", calculatePrice())).append("\n");

        return receipt.toString();
    }
}

