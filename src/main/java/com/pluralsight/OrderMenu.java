package com.pluralsight;

import OrderClass.Chips;
import OrderClass.Drinks;
import OrderClass.Order;
import OrderClass.Sandwich.Sandwich;
import OrderClass.Sandwich.Toppings.Meats;
import OrderClass.Sandwich.Toppings.RegularTopping;
import OrderFileManager.OrderFileManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;

public class OrderMenu {
    public Order currentOrder;
    private final Scanner scanner = new Scanner(System.in);

    public int getInput () {
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    public String getStringInput () {
        return scanner.nextLine().trim();
    }

    public void display() {
        boolean running = true;
        while (running) {
            System.out.println("\n===== Welcome to Pain 'n' Despair Deli or whatever =====");
            System.out.println("\n--- Select whatever you want to do ---");
            System.out.println("\n1: Start New Order (yay...)");
            System.out.println("2: Exit Menu (Please...)\n");
            System.out.print("Your choice: ");

            switch (getInput()) {
                case 1 -> startNewOrder();
                case 0 -> running = false;
                default -> System.out.println("Where does it say to input whatever you just inputted?");
            }
        }
    }

        public void startNewOrder() {
            currentOrder = new Order();
            showOrderScreen();
        }

        public void showOrderScreen () {
            boolean startOrder = true;
            while (startOrder) {
                System.out.println("\n===== Order Menu =====");
                System.out.println("1: Add a Sandwich (Bozo lol)");
                System.out.println("2: Add a Drink (Thirsty huh?)");
                System.out.println("3: Add Chips (We do NOT need the chips ma boy)");
                System.out.println("4: Let us Checkout (So you can leave faster!)");
                System.out.println("0: Cancel Order (Save us both the time!)");
                System.out.print("\nSelect what you want to do (And lets make it snappy): ");

                switch (getInput()) {
                    case 1 -> buildSandwich();
                    case 2 -> addDrink();
                    case 3 -> addChips();
                    case 4 -> startOrder = checkout();
                    case 0 -> {
                        currentOrder = null;
                        startOrder = false;
                        System.out.println("Thank you for cancelling the order. We are both free...");
                    }
                    default -> System.out.println("Invalid selection. Picking 1-4 is hard huh?");
                }
            }
        }

        public void buildSandwich() {
            System.out.println("\n===== Build Your Sandwich =====");

            System.out.println("Bread Options:");
            System.out.println("1: White \n2: Wheat \n3: Rye \n4: Wrap");
            String bread = switch (getInput()) {
                case 1 -> "White";
                case 2 -> "Wheat";
                case 3 -> "Rye";
                case 4 -> "Wrap";
                default -> {
                    System.out.println("So I am picking? White bread it is, less time spent on earth, perfect for the both of us!");
                    yield "White";
                }
            };

            System.out.print("\nSize Selection (we have 4, 8, or 12 inches): ");
            int size = getInput();

            Sandwich sandwich = new Sandwich(size, bread);
            System.out.print("\nToast the sandwich? (Yes or No?): ");
            sandwich.setToasted(getStringInput().equalsIgnoreCase("Yes"));
            System.out.println("Sandwich will be toasted");

            pickToppings(sandwich, size);

            currentOrder.addSandwich(sandwich);

            System.out.println("Yay...sandwich added to order...(You get to pay soon congrats!)");
    }

        public void pickToppings (Sandwich sandwich, int size) {
            System.out.println("\n--- Premium Toppings ---");
            System.out.print("Add meat? (Yes or No): ");
            if (getStringInput().equalsIgnoreCase("Yes")) {

                String[] meats = Meats.getAvailableMeats();
                Map<String, Integer> initialMeats = new HashMap<>();

                int meatAmount = 0;
                while (meatAmount < 10) {
                    System.out.println("\nChoose what meat to add...You get to go crazy! (Up to 10, I am not made of money!)\n Select 0 to stop: ");
                    for (int i = 0; i < meats.length; i++) {
                        System.out.printf("%d. %s (%d selected)\n", i + 1, meats[i], initialMeats.getOrDefault(meats[i], 0));
                    }

                    int choice = getInput();
                    if (choice == 0) break;

                    if (choice >= 1 && choice <= meats.length) {
                        String selected = meats[choice - 1];
                        sandwich.addTopping(new Meats(selected, size));
                        initialMeats.put(selected, initialMeats.getOrDefault(selected, 0) + 1);
                        meatAmount++;
                        System.out.println(selected + " added.");
                    } else {
                        System.out.println("ERR can't let you do that!");
                    }
                }

                System.out.print("\nExtra meat? (It costs more, good for me, not so much for you...Thanks in advanced!): ");
                if (getStringInput().equalsIgnoreCase("Yes")) {
                    Map<String, Integer> meatAmountExtra = new HashMap<>();

                    while (true) {
                        System.out.println("\nChoose the extra meat you want (Like you need more...)\nFor legal reasons I have to tell you only up to 2 per type, although I would not try to stop you personally.\nPress 0 when satisfied: ");
                        for (int i = 0; i < meats.length; i++) {
                            String meat = meats[i];
                            System.out.printf("%d. %s (%d/2 extra)\n", i + 1, meat, meatAmountExtra.getOrDefault(meat, 0));
                        }
                        int choice = getInput();
                        if (choice == 0) break;

                        if (choice >= 1 && choice <+ meats.length) {
                            String selected = meats[choice - 1];
                            int currentCount = meatAmountExtra.getOrDefault(selected, 0);

                            if (currentCount >= 2) {
                                System.out.println("You already chose 2 of that, on top of what you have! This is the greed they spoke about in the Bible!" + selected);
                                continue;
                            }

                            sandwich.addTopping(new Meats(selected, size));
                            meatAmountExtra.put(selected, currentCount + 1);
                            System.out.println("Extra " + selected + " added.");
                        } else {
                            System.out.println("And I am back to saying you did something wrong, surprised? No.");
                        }
                    }
                }
            }

            System.out.println("\n--- The Free Stuff (Where you should be looking)");
            System.out.println("Press 'x' when you're done being so picky");

            while (true) {
                System.out.println("Add toppings/sauces: ");
                String topping = getStringInput();
                if (topping.equalsIgnoreCase("x")) break;
                sandwich.addTopping(new RegularTopping(topping));
            }
        }

        public void addDrink() {
            System.out.println("\n===== Add Drink =====");
            System.out.print("Size (small, medium, large): ");
            String size = getStringInput();

            System.out.print("Flavor (We do not have many or all day): ");
            String flavor = getStringInput();

            currentOrder.addDrink(new Drinks(size, flavor));
            System.out.println("Drink added yay! More money for me and more sugar for you!");
        }

        public void addChips() {
            System.out.println("\n===== Add Chips =====");
            System.out.print("Chip brand (All of them are stale): ");
            String brand = getStringInput();

            currentOrder.addChips(new Chips(brand));
            System.out.println("Chips added...");
        }

        public boolean checkout () {
            System.out.println("\n===== Checking Out Finally! =====");
            System.out.println(currentOrder.generateReceipt());

            System.out.println("\n1: Confirm (We know it's yours...)");
            System.out.println("0: Cancel (Wasted all that time...Lovely...");

            if (getInput() == 1) {
                OrderFileManager fileManager = new OrderFileManager();
                fileManager.saveReceipt(currentOrder);

                currentOrder = null;
                System.out.println("Order complete! We followed the prompts and you lost money! Enjoy your meal!");
                return true;
            }
            return false;
        }
    }

