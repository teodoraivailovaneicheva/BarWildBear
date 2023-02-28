package frames;

import database.Database;
import models.*;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class BarDataProvider {
    public ArrayList<User> users;
    public ArrayList<User> searchedUsers;
    public ArrayList<Integer> tables;
    public ArrayList<Order> orders;
    public ArrayList<Category> categories;
    public ArrayList<Product> products;
    public User loggedUser;
    public boolean isSearchingUsers;

    public BarDataProvider() {
        orders = new ArrayList<>();
        categories = Database.getCategories();
        products = Database.getProducts();

        User user1 = new User("John", "0101", "123456", UserType.MANAGER);
        User user2 = new User("Anna", "0000", "654321", UserType.WAITER);
        User user3 = new User("Penny", "0202", "456789", UserType.WAITER);

        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        tables = new ArrayList<>();
        tables.add(11);
        tables.add(12);
        tables.add(13);
        tables.add(14);
        tables.add(15);
        tables.add(16);
        tables.add(17);
        tables.add(18);
        tables.add(19);
        tables.add(20);
    }

    public boolean isCorrectLogin(String pin) {
        for (User user : users) {
            if(user.getPinCode().equals(pin)) {
                loggedUser = user;
                return true;
            }
        }

        return false;
    }

    public void fetchUsers(DefaultTableModel model) {
        model.setRowCount(0);
        ArrayList<User> activeUserList;
        if(isSearchingUsers) {
            activeUserList = new ArrayList<>(searchedUsers);
        } else {
            activeUserList = new ArrayList<>(users);
        }
        for(User user : activeUserList) {
            String row[] = new String[4];
            row[0] = user.getName();
            row[1] = user.getPinCode();
            row[2] = user.getPhoneNumber();
            row[3] = user.getUserRole();
            model.addRow(row);
        }
    }
    public void searchUsers(String searchedText) {
        searchedUsers = new ArrayList<>();
        for(User user : users) {
            if(user.getName().toLowerCase().contains(searchedText.toLowerCase())) {
                searchedUsers.add(user);
            }
        }
    }

    public void fetchOrders(DefaultTableModel model, int tableNumber){
        model.setRowCount(0);
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getTableNumber() == tableNumber){
                String row[] = new String[3];
                row[0] = Integer.toString(i+1);
                row[1] = order.getProductsCount();
                row[2] = order.getTotalPrice(false);
                model.addRow(row);
            }
        }
    }

    public void fetchProducts(DefaultTableModel model, Order order){
        model.setRowCount(0);
        for (int i = 0; i < order.getProducts().size(); i++) {
            Product product = order.getProducts().get(i);
            String row[] = new String[3];
            row[0] = product.getBrand();
            row[1] = Integer.toString(product.getQuantity());
            row[2] = product.getTotalPrice();
            model.addRow(row);
        }
    }
}

