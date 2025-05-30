package OrderClass.Sandwich.Toppings;

public class Sauces extends ToppingsA {
    public Sauces (String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Sauce";
    }

    public static String[] getAvailableSauces() {
        return new String[] { "Mayo", "Mustard", "Oil", "Vinegar", "Ranch", "Chipotle Southwest" };
    }

}
