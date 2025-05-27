package OrderClass.Sandwich.Toppings;

public class Sauces extends ToppingsA {
    public Sauces (String name) {
        super(name);
    }

    @Override
    public double getPrice() {
        return 0.0;
    }

    @Override
    public String getType() {
        return "Sauce";
    }
}
