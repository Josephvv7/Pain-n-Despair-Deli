package OrderClass.Sandwich.Toppings;

public class RegularTopping extends ToppingsA {
    public RegularTopping (String name) {
        super(name);
    }

    @Override
    public double getPrice() {
        return 0.0;
    }

    @Override
    public String getType() {
        return "Regular";
    }
}
