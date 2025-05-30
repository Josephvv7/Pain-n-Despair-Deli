package OrderClass;

public class Chips {
    private String brand;

    public Chips(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return 1.50;
    }

    public String getReceiptLine() {
        return String.format("Chips: %s - $%.2f", brand, getPrice());
    }

}
