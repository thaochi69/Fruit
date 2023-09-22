/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.ArrayList;
import java.util.Hashtable;
import model.Fruit;
import model.Order;

/**
 *
 * @author Thao Chi
 */
public class Manager {
    public static int menu() {
        System.out.println("==========MENU=========");
        System.out.println("1. Create Fruit");
        System.out.println("2. View orders");
        System.out.println("3. Shopping (for buyer)");
        System.out.println("4. Exit");
        System.out.println("=======================");
        System.out.println("Enter your choice: ");
        int choice = Validation.checkInputIntLimit(1, 4);
        return choice;
    }

    public static void createFruit(ArrayList<Fruit> lf) {
        while (true) {
            System.out.println("Enter fruit id: ");
            String fruitId = Validation.checkInputString();
            if (!Validation.checkIdExist(lf, fruitId)) {
                System.out.println("Id exist");
                return;
            }
            System.out.println("Enter fruit name: ");
            String fruitName = Validation.checkInputString();
            System.out.println("Enter price: ");
            double price = Validation.checkInputDouble();
            System.out.println("Enter quantity: ");
            int quantity = Validation.checkInputInt();
            System.out.println("Enter origin: ");
            String origin = Validation.checkInputString();
            lf.add(new Fruit(fruitId, fruitName, price, quantity, origin));
            if (!Validation.checkInputYN()) {
                return;
            }
        }
    }

    static void viewOrder(Hashtable<String, ArrayList<Order>> ht) {
        for (String name : ht.keySet()) {
            System.out.println("Customer: " + name);
            ArrayList<Order> lo = ht.get(name);
            displayListOrder(lo);
        }
        
    }

    public static void shopping(ArrayList<Fruit> lf, Hashtable<String, ArrayList<Order>> ht) {
        if (lf.isEmpty()) {
            System.out.println("No have item.");
            return;
        }
        
        ArrayList<Order> lo = new ArrayList<>();
        while (true) {
            displayListFruit(lf);
            System.out.println("Enter item: ");
            int item = Validation.checkInputIntLimit(1, lf.size());
            Fruit fruit = getFruitByItem(lf, item);
            System.out.println("Enter quantity: ");
            int quantity = Validation.checkInputIntLimit(1, fruit.getQuantity());
            fruit.setQuantity(fruit.getQuantity() - quantity);
            
            if (!Validation.checkItemExist(lo, fruit.getFruitId())) {
                updateOrder(lo, fruit.getFruitId(), quantity);
            } else {
                lo.add(new Order(fruit.getFruitId(), fruit.getFruitName(),
                        quantity, fruit.getPrice()));
            }

            if (!Validation.checkInputYN()) {
                break;
            }
        }
        displayListOrder(lo);
        System.out.println("Enter name: ");
        String name = Validation.checkInputString();
        ht.put(name, lo);
        System.out.println("Add successfull");
    }

   
    public static void displayListFruit(ArrayList<Fruit> lf) {
        int countItem = 1;
        System.out.printf("%-10s%-20s%-20s%-15s\n", "Item", "Fruit name", "Origin", "Price");
        for (Fruit fruit : lf) {
            if (fruit.getQuantity() != 0) {
                System.out.printf("%-10d%-20s%-20s%-15.0f$\n", countItem++,
                        fruit.getFruitName(), fruit.getOrigin(), fruit.getPrice());
            }
        }
       
    }
    
    public static Fruit getFruitByItem(ArrayList<Fruit> lf, int item) {
        int countItem = 1;
        for (Fruit fruit : lf) {
            if (fruit.getQuantity() != 0) {
                countItem++;
            }
            if (countItem - 1 == item) {
                return fruit;
            }
        }
        return null;
    }

    public static void displayListOrder(ArrayList<Order> lo) {
        double total = 0;
        System.out.printf("%15s%15s%15s%15s\n", "Product", "Quantity", "Price", "Amount");
        for (Order order : lo) {
            System.out.printf("%15s%15d%15.0f$%15.0f$\n", order.getFruitName(),
                    order.getQuantity(), order.getPrice(),
                    order.getPrice() * order.getQuantity());
            total += order.getPrice() * order.getQuantity();
        }
        System.out.println("Total: " + total);
    }

    
    public static void updateOrder(ArrayList<Order> lo, String id, int quantity) {
        for (Order order : lo) {
            if (order.getFruitId().equalsIgnoreCase(id)) {
                order.setQuantity(order.getQuantity() + quantity);
                return;
            }
        }
    }
}
