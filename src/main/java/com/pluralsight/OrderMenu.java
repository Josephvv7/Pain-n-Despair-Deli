package com.pluralsight;

import OrderClass.Chips;
import OrderClass.Drinks;
import OrderClass.Order;
import OrderClass.Sandwich.Sandwich;
import OrderClass.Sandwich.Toppings.Cheese;
import OrderClass.Sandwich.Toppings.Meats;
import OrderClass.Sandwich.Toppings.RegularTopping;
import OrderClass.Sandwich.Toppings.Sauces;
import OrderFileManager.OrderFileManager;

import java.util.*;
import java.util.stream.IntStream;

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

        private void buildSandwich() {
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
            int size = Integer.parseInt(scanner.nextLine());

            Sandwich sandwich = new Sandwich(size, bread);
            System.out.print("\nToast the sandwich? (Y/N?): ");
            sandwich.setToasted(getStringInput().equalsIgnoreCase("Y"));
            System.out.println("Sandwich will be toasted");

            pickToppings(sandwich, size);

            currentOrder.addSandwich(sandwich);

            System.out.println("Yay...sandwich added to order...(You get to pay soon congrats!)");
    }

        private void pickToppings (Sandwich sandwich, int size) {
            System.out.println("\n--- Premium Toppings ---");
            System.out.print("Add meat? (Y/N): ");

            if (getStringInput().equalsIgnoreCase("Y")) {
                sandwich.setHasMeat(true);
                String[] meats = Meats.getAvailableMeats();

                Map<String, Integer> initialMeats = new HashMap<>();
                int meatAmount = 0;

                System.out.println("\nChoose what meat to add...You get to go crazy! (Up to 10, I am not made of money!)\n Select 0 to stop: ");
                //Previous for-loop
                /*for (int i = 0; i < meats.length; i++) {
                    System.out.printf("%d. %s\n", i + 1, meats[i]);
                    }*/

                //Implementing Streams
                IntStream.range(0, meats.length)
                        .forEach(i -> System.out.printf("%d. %s\n", i + 1, meats[i]));

                while (meatAmount < 10) {
                    System.out.printf("You have selected %d/10. Keep selecting or press 0 to finish: ", meatAmount);
                    int choice = getInput();

                    if (choice == 0) break;

                    if (choice >= 1 && choice <= meats.length) {
                        String selected = meats[choice - 1];
                        sandwich.addTopping(new Meats(selected, size));
                        initialMeats.put(selected, initialMeats.getOrDefault(selected, 0) + 1);
                        meatAmount++;
                        System.out.println(selected + " added.");

                    } else {
                        System.out.println("ERR can't let you do that! Enter 1 to " + meats.length + " or 0 to stop.");
                    }
                }

                if (!initialMeats.isEmpty()) {
                    System.out.println("\nYou have selected:");
                    for (Map.Entry<String, Integer> entry : initialMeats.entrySet()) {
                        System.out.printf(" - %s x%d\n", entry.getKey(), entry.getValue());
                    }
                }

                System.out.print("\nExtra meat? (It costs more, good for me, not so much for you...Thanks in advanced!)\nY/N: ");
                if (getStringInput().equalsIgnoreCase("Y")) {
                    Map<String, Integer> meatAmountExtra = new HashMap<>();

                    System.out.println("\nChoose the extra meat you want (Like you need more...)\nFor legal reasons I have to tell you only up to 2 per type, although I would not try to stop you personally.\nPress 0 when satisfied: ");
                    for (int i = 0; i < meats.length; i++) {
                        System.out.printf("%d. %s\n", i + 1, meats[i]);
                    }

                    while (true) {
                        System.out.print("Extra meat selection (0 to stop): ");
                        int choice = getInput();
                        if (choice == 0) break;

                        if (choice >= 1 && choice <= meats.length) {
                            String selected = meats[choice - 1];
                            int currentCount = meatAmountExtra.getOrDefault(selected, 0);

                            if (currentCount >= 2) {
                                System.out.println("You already chose 2 of " + selected + "\nThis is the greed they spoke about in the Bible!");
                                continue;
                            }

                            sandwich.addExtraMeat(selected, size);
                            meatAmountExtra.put(selected, currentCount + 1);
                            System.out.println("Extra " + selected + " added.");
                        } else {
                            System.out.println("And I am back to saying you did something wrong, surprised? No.");
                        }
                    }
                    if (!meatAmountExtra.isEmpty()) {
                        System.out.print("\nYou added extra:");
                        for (Map.Entry<String, Integer> entry : meatAmountExtra.entrySet()) {
                            System.out.printf("\n - %s x%d (extra)\n", entry.getKey(), entry.getValue());
                        }
                    }
                }
            }

            System.out.println("\n--- Cheese Selection ---");
            System.out.print("Add cheese? (Y/N): ");
            if (getStringInput().equalsIgnoreCase("Y")) {
                sandwich.setHasCheese(true);
                String[] cheeses = Cheese.getAvailableCheese();
                Map<String, Integer> initialCheeses = new HashMap<>();

                System.out.println("\nChoose what cheese to add...You get to go crazy! (Up to 10, I am not made of money!)\n Select 0 to stop: ");
                //Previous for-loop before using Streams
                /*for (int i = 0; i < cheeses.length; i++) {
                    System.out.printf("%d. %s\n", i + 1, cheeses[i]);
                }*/

                //Using Streams for Cheese
                IntStream.range(0, cheeses.length)
                        .forEach(i -> System.out.printf("%d. %s\n", i + 1, cheeses[i]));

                while (true) {
                    System.out.print("Select Cheese or press 0 to finish: ");
                    int choice = getInput();

                    if (choice == 0) break;

                    if (choice >= 1 && choice <= cheeses.length) {
                        String selected = cheeses[choice - 1];
                        sandwich.addTopping(new Cheese(selected, size));
                        initialCheeses.put(selected, initialCheeses.getOrDefault(selected, 0) + 1);
                        System.out.println(selected + " added.");

                    } else {
                        System.out.println("ERR can't let you do that! Enter 1 to " + cheeses.length + " or 0 to stop.");
                    }
                }

                if (!initialCheeses.isEmpty()) {
                    System.out.println("\nYou have selected:");
                    for (Map.Entry<String, Integer> entry : initialCheeses.entrySet()) {
                        System.out.printf(" - %s x%d\n", entry.getKey(), entry.getValue());
                    }
                }

                System.out.print("\nExtra cheese? (Remember it costs more!)\nY/N: ");
                if (getStringInput().equalsIgnoreCase("Y")) {
                    Map<String, Integer> extraCheese = new HashMap<>();

                    System.out.println("\nChoose the extra cheese you want\nAgain for legal reasons I have to tell you only up to 2 per type\nPress 0 when satisfied: ");
                    for (int i = 0; i < cheeses.length; i++) {
                        System.out.printf("%d. %s\n", i + 1, cheeses[i]);
                    }

                    while (true) {
                        System.out.print("Extra cheese selection (0 to stop): ");
                        int choice = getInput();
                        if (choice == 0) break;

                        if (choice >= 1 && choice <= cheeses.length) {
                            String selected = cheeses[choice - 1];
                            int currentCount = extraCheese.getOrDefault(selected, 0);

                            if (currentCount >= 2) {
                                System.out.println("Hold your greedy paws, remember only 2 of " + selected + " allowed.");
                                continue;
                            }

                            sandwich.addExtraCheese(selected, size);
                            extraCheese.put(selected, currentCount + 1);
                            System.out.println("Extra " + selected + " added.");
                        } else {
                            System.out.println("Must have been an miss input! ");
                        }
                    }

                    if (!extraCheese.isEmpty()) {
                        System.out.println("\nYou added extra:");
                        for (Map.Entry<String, Integer> entry : extraCheese.entrySet()) {
                            System.out.printf("\n - %s x%d (extra)\n", entry.getKey(), entry.getValue());
                        }
                    }
                }
            }

            System.out.println("\n");

            String[] regularToppings = RegularTopping.getAvailableToppings();

            System.out.println("\n---- The Free Stuff (Where you should be looking) ----");
            for (int i = 0; i < regularToppings.length; i++) {
                System.out.printf("%d. %s\n", i + 1, regularToppings[i]);
            }
            System.out.println("Press 0 when you're done being so picky");

            while (true) {
                System.out.println("Add toppings: ");
                int choice = getInput();

                if (choice == 0) break;

                if (choice >= 1 && choice <= regularToppings.length) {
                    String selected = regularToppings[choice - 1];
                    sandwich.addTopping(new RegularTopping(selected));
                    System.out.println(selected + " added.");
                } else {
                    System.out.println("Bro...Can we not count? Select 1 to " + regularToppings.length + ", or 0 to stop.");
                }
            }

            String[] sauceOptions = Sauces.getAvailableSauces();

            System.out.println("\n---- Sauces ----");
            for (int i = 0; i < sauceOptions.length; i++) {
                System.out.printf("%d. %s\n", i + 1, sauceOptions[i]);
            }
            System.out.println("Press 0 when you are done.");

            while (true) {
                System.out.println("Select Sauces: ");
                int choice = getInput();

                if (choice == 0) break;

                if (choice >= 1 && choice <= sauceOptions.length) {
                    String selected = sauceOptions[choice - 1];
                    sandwich.addTopping(new Sauces(selected));
                    System.out.println(selected + " added.");
                } else {
                    System.out.println("Bro...Can we not count? Select 1 to " + sauceOptions.length + ", or 0 to stop.");
                }
            }
        }

        private void addDrink() {
            System.out.println("\n===== Add Drink =====");
            System.out.print("Size (small, medium, large): ");
            String size = getStringInput();

            System.out.print("Flavor (We do not have many or all day): ");
            System.out.println("\n1: Water \n2: Coke \n3: Pepsi \n4: Sprite");

            int choice = getInput();
            String flavor = switch (choice) {
                case 1 -> "Water";
                case 2 -> "Coke";
                case 3 -> "Pepsi";
                case 4 -> "Sprite";
                default -> {
                    System.out.println("Invalid choice! Choosing my favorite for you!");
                    yield "Sprite";
                }
            };

            currentOrder.addDrink(new Drinks(size, flavor));
            System.out.println("Drink added yay! More money for me and more sugar for you!");

    }

        private void addChips() {
            System.out.println("\n===== Add Chips =====");
            System.out.print("Chip brands (All of them are stale by the way!): ");
            System.out.println("\n1: Lays \n2: Ruffles \n3: Cheetos");

            int choice = getInput();
            String brand = switch (choice) {
                case 1 -> "Lays";
                case 2 -> "Ruffles";
                case 3 -> "Cheetos";
                default -> {
                    System.out.println("Invalid choice! We are going with the most bland brand1 HA!: ");
                    yield "Lays";
                }
            };

            currentOrder.addChips(new Chips(brand));
            System.out.println(  "Bag of " + brand  + " added. Enjoy the disappointment");

    }

        private boolean checkout () {
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

