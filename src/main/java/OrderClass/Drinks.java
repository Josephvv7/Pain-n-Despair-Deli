package OrderClass;

public class Drinks {
    private String size;
    private String flavor;

    public Drinks(String size, String flavor) {
        this.size = size;
        this.flavor = flavor;
    }

    public String getSize() {
        return size;
    }

    public String getFlavor() {
        return flavor;
    }

    public double getPrice() {
        switch (size.toLowerCase()) {
            case "small":
                return 1.50;
            case "medium":
                return 2.00;
            case "large":
                return 2.50;
            default:
                return 0.00;
        }
    }

    public String getReceiptLine() {
        return String.format("Drink: %s %s - $%.2f", size, flavor, getPrice());
    }
}
