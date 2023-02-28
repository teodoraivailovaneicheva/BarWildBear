package models;

import java.util.ArrayList;

public class Order {
    private String uid;
    private int tableNumber;
    private ArrayList<Product> products;
    private User waiter;
    private int percentDiscount;

    public Order(String uid, int tableNumber, User waiter) {
        this.uid = uid;
        this.tableNumber = tableNumber;
        this.products = new ArrayList<>();
        this.waiter = waiter;
    }

    public String getTotalPrice(boolean withDiscount) {

        double sum = 0;

        for (Product product : products) {
            sum += (product.getPrice() * product.getQuantity());
        }

        if(withDiscount) {
            double discount = sum * (Double.valueOf(percentDiscount) / 100);
            sum = sum - discount;
        }

        return String.format("%.2f .", sum);
    }

    public String getProductsCount() {
        int count = 0;
        for (Product product : products) {
            count += product.getQuantity();
        }
        return Integer.toString(count);
    }
    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public User getWaiter() {
        return waiter;
    }

    public void setWaiter(User waitress) {
        this.waiter = waitress;
    }

    public int getPercentDiscount() {

        return percentDiscount;
    }

    public void setPercentDiscount(int percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "uid='" + uid + '\'' +
                ", tableNumber=" + tableNumber +
                ", products=" + products +
                ", waitress=" + waiter +
                ", percentDiscount=" + percentDiscount +
                '}';
    }
}
