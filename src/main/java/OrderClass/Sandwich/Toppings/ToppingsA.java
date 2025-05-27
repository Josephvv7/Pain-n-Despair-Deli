package OrderClass.Sandwich.Toppings;

public abstract class ToppingsA {
    private String name;

    public ToppingsA(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract double getPrice();

    public abstract String getType();

}
