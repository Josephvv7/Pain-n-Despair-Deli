package OrderClass.Sandwich.Toppings;

public class Meats extends ToppingsA {
    private int size;

    public Meats(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public String getType() {
        return "Premium";
    }

    public static String[] getAvailableMeats() {
        return new String[] { "Steak", "Ham", "Salami", "Roast Beef", "Chicken", "Bacon" };
    }

}
