package OrderClass.Sandwich;

import OrderClass.Sandwich.Toppings.ToppingsA;

import java.util.ArrayList;
import java.util.List;

public class Sandwich {
    private int size;
    private String breadType;
    private boolean toasted;
    private List<ToppingsA> toppings;
    private boolean extraMeat;
    private boolean extraCheese;

    public Sandwich(int size, String breadType, boolean toasted) {
        this.size = size;
        this.breadType = breadType;
        this.toasted = toasted;
        this.toppings = new ArrayList<>();
    }

    public void addTopping(ToppingsA topping) {
        toppings.add(topping);
    }

    public void setExtraMeat(boolean extraMeat) {
        this.extraMeat = extraMeat;
    }

    public void setExtraCheese(boolean extraCheese) {
        this.extraCheese = extraCheese;
    }

    public double calculatePrice() {
        double total = 0.0;

        if (size == 4) total += 5.50;
        else if (size == 8) total += 7.00;
        else total += 8.50;

        for (ToppingsA topping : toppings) {
            total += topping.getPrice();
        }

        if (extraMeat) {
            if (size == 4) total += 1.00;
            else if (size == 8) total += 2.00;
            else total += 3.00;
        }

        if (extraCheese) {
            if (size == 4) total += 0.75;
            else if (size == 8) total += 1.50;
            else total += 2.25;
        }

        return total;
    }

    public String getReceiptLine() {
        return null;
    }


}
