package OrderClass.Sandwich.Toppings;

public class Cheese extends ToppingsA {
    private int size;

    public Cheese(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public String getType() {
        return "Premium";
    }

    public static String[] getAvailableCheese() {
        return new String[] { "American", "Provolone", "Cheddar", "Swiss" };
    }

}
