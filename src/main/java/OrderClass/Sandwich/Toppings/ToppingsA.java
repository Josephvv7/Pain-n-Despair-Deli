package OrderClass.Sandwich.Toppings;

public abstract class ToppingsA {
    private String name;

    public ToppingsA(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public abstract String getType();



}
