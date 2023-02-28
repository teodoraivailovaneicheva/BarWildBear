package database;

import models.Category;
import models.Product;
import models.ProductType;

import java.util.ArrayList;

public class Database {

    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Product> getProducts() {

        Product a = new Product("1", ProductType.ALCOHOLIC, "vodka", "Smirnoff", 2.20, 1);
        Product b = new Product("2", ProductType.NONALCOHOLIC, "soda", "Coca Cola", 2.00, 1);
        Product c = new Product("3", ProductType.FOOD, "nuts", "almonds", 5.00, 1);
        Product a1 = new Product("4", ProductType.ALCOHOLIC, "vodka", "Absolut", 2.20, 1);
        Product b1 = new Product("5", ProductType.NONALCOHOLIC, "soda", "Pepsi", 2.00, 1);
        Product c1 = new Product("6", ProductType.FOOD, "nuts", "peanuts", 5.00, 1);
        Product a2 = new Product("7", ProductType.ALCOHOLIC, "vodka", "Grey guss", 2.20, 1);
        Product b2 = new Product("8", ProductType.NONALCOHOLIC, "soda", "Fanta", 2.00, 1);
        Product c2 = new Product("9", ProductType.FOOD, "nuts", "hazelnut", 5.00, 1);

        products.add(a);
        products.add(b);
        products.add(c);
        products.add(a1);
        products.add(b1);
        products.add(c1);
        products.add(a2);
        products.add(b2);
        products.add(c2);

        return products;
    }

    public static ArrayList<Category> getCategories(){
        ArrayList<Category> categories = new ArrayList<>();
        Category a1 = new Category("Alcohol",ProductType.ALCOHOLIC);
        Category a2 = new Category("NonAlcohol",ProductType.NONALCOHOLIC);
        Category a3 = new Category("Nuts",ProductType.FOOD);

        categories.add(a1);
        categories.add(a2);
        categories.add(a3);

        return  categories;
    }
}
