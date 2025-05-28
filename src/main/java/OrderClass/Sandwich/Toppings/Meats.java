package OrderClass.Sandwich.Toppings;

public class Meats extends ToppingsA {
    private int size;

    public Meats(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public double getPrice() {
        if (size == 4) return 1.00;
        if (size == 8) return 2.00;
        return 3.00;
    }

    @Override
    public String getType() {
        return "Premium";
    }

    public static String[] getAvailableMeats() {
        return new String[] { "Steak", "Ham", "Salami", "Roast Beef", "Chicken", "Bacon" };
    }

}
