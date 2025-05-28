package OrderClass.Sandwich.Toppings;

public class Cheese extends ToppingsA {
    private int size;

    public Cheese(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public double getPrice() {
        if (size == 4) return 0.75;
        if (size == 8) return 1.50;
        return 2.25;
    }

    @Override
    public String getType() {
        return "Premium";
    }
}
