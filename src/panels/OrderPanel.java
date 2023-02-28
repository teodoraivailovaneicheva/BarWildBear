package panels;

import frames.BarFrame;
import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderPanel extends BasePanel{

    public int selectedTableNumber;
    public JTable ordersTable;
    public DefaultTableModel ordersTableModel;
    public JTable productsTable;
    public DefaultTableModel productsTableModel;
    public ArrayList<JButton> categoryButtons;
    public ArrayList<JButton> productButtons;
    public Product selectedProduct;

    public OrderPanel(BarFrame frame, int selectedTableNumber) {
        super(frame);
        this.selectedTableNumber = selectedTableNumber;
        initializeHeader();
        initializeOrderTable();
        initializeProductsTable();
        initializeCategories();
        initializeFooter();
        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);

    }

    public void initializeHeader(){

        JButton createButton = new JButton("Create");
        createButton.setBounds(0,15,frame.getWidth()/3,40);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOrder();
            }
        });
        add(createButton);

        JButton finishButton = new JButton("Finish");
        finishButton.setBounds(frame.getWidth()-(frame.getWidth()/3),15,frame.getWidth()/3,40);
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
        add(finishButton);

        String tableText = "Table: " + selectedTableNumber;
        JLabel tableLabel = new JLabel(tableText);
        tableLabel.setBounds(frame.getWidth()/2 - 50, 30,100,30);
        tableLabel.setFont(new Font("Arial", Font.BOLD,14));
        tableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(tableLabel);

        JLabel waiterLabel = new JLabel(frame.dataProvider.loggedUser.getName());
        waiterLabel.setBounds(frame.getWidth()/2 - 50, 50,100,30 );
        waiterLabel.setFont(new Font("Arial", Font.BOLD,14));
        waiterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(waiterLabel);

    }

    public void initializeOrderTable(){

        String cols[] = {"Number", "Product", "Price"};
        ordersTableModel = new DefaultTableModel();
        ordersTableModel.setColumnIdentifiers(cols);
        ordersTable = new JTable(ordersTableModel);
        JScrollPane ordersPane = new JScrollPane(ordersTable);
        ordersPane.setBounds(0,60,frame.getWidth() / 3, frame.getHeight() - 150);
        add(ordersPane);
    }
    public void initializeProductsTable(){
        String cols[] = {"Product", "Quantity", "Price"};
        productsTableModel = new DefaultTableModel();
        productsTableModel.setColumnIdentifiers(cols);
        productsTable = new JTable(productsTableModel);
        JScrollPane productPane = new JScrollPane(productsTable);
        productPane.setBounds(frame.getWidth() - (frame.getWidth()/3),60,frame.getWidth() / 3, frame.getHeight() - 150);
        add(productPane);
    }

    public void initializeFooter() {

        JButton exitButton = new JButton("Reject");
        exitButton.setBounds(0, frame.getHeight() - 80, frame.getWidth() / 3, 40);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.router.showLogin();
            }
        });
        add(exitButton);
    }

    public void increaseQuantity(Product product) {
        if (ordersTable.getSelectedRow() < 0) {
            showError("There is no order selected");
            return;
        }
        int currentlySelectedRow = ordersTable.getSelectedRow();
        Order order = frame.dataProvider.orders.get(ordersTable.getSelectedRow());
        boolean isFound = false;
        for (Product prd : order.getProducts()) {
            if (prd.getBrand().equals(product.getBrand())) {
                prd.setQuantity(prd.getQuantity() + 1);
                isFound = true;
                break;
            }
        }if (isFound == false) {
            order.getProducts().add(product);
        }
            frame.dataProvider.fetchProducts(productsTableModel, order);
            frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
            ordersTable.setRowSelectionInterval(currentlySelectedRow, currentlySelectedRow);
        }

    public void createOrder(){
        boolean isYes = showQuestionPopup("Would you like to create a new order?");
        if (isYes) {
            Order order = new Order("1", selectedTableNumber, frame.dataProvider.loggedUser);
            frame.dataProvider.orders.add(order);
            frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
        }
    }

    public void deleteOrder() {

        Order selectedOrder = frame.dataProvider.orders.get(ordersTable.getSelectedRow());

        if(ordersTable.getSelectedRow() < 0) {
            showError("You do not have an order selected!");
            return;
        }
        boolean isDiscounted = showQuestionPopup("Would you like to include a discount?");
        if (isDiscounted){
            selectedOrder.setPercentDiscount(20);
            showQuestionPopup("The amount after the applied discount is: " +  selectedOrder.getTotalPrice(true));

        }

        boolean isYes = showQuestionPopup("Would you like to complete the order?");
        if (isYes) {
            frame.dataProvider.orders.remove(selectedOrder);
            frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
        }
    }

    public void initializeCategories(){
        categoryButtons = new ArrayList<>();
        int buttonX = frame.getWidth()/2 - (frame.getWidth()/3)/2;
        int buttonY = 80;
        for (Category category:frame.dataProvider.categories) {
            JButton button = new JButton(category.title);
            button.setBounds(buttonX, buttonY, frame.getWidth()/3, 40);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearButtons();
                    initializeProductButtons(category.type);
                }
            });
            add(button);
            categoryButtons.add(button);
            buttonY += 40;
        }
    }

    public void initializeProductButtons(ProductType type){
        productButtons = new ArrayList<>();
        int buttonX = frame.getWidth()/2 - (frame.getWidth()/3)/2;
        int buttonY = 80;

        for (Product product : frame.dataProvider.products){
            if (product.getType() == type){
                JButton button = new JButton(product.getBrand());
                button.setBounds(buttonX, buttonY, frame.getWidth()/3, 40);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        selectedProduct = product;
                        addProductToOrder(product);
                    }
                });
                add(button);
                productButtons.add(button);
                buttonY += 40;
            }
        }
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearButtons();
                initializeCategories();
            }
        });
        backButton.setBounds(buttonX, buttonY, frame.getWidth()/3, 40);
        add(backButton);
        productButtons.add(backButton);
    }

    public void addProductToOrder(Product product){
        if (ordersTable.getSelectedRow() < 0){
            showError("There is no order selected");
            return;
        }
        int currentlySelectedRow = ordersTable.getSelectedRow();
        Order order = frame.dataProvider.orders.get(ordersTable.getSelectedRow());
       boolean isFound = false;
       for (Product prd : order.getProducts()){
           if(prd.getBrand().equals(product.getBrand())){
               prd.setQuantity(prd.getQuantity() + 1);
               isFound = true;
               break;
           }
       }if (isFound == false) {
            order.getProducts().add(product);
        }
        frame.dataProvider.fetchProducts(productsTableModel,order);
        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
        ordersTable.setRowSelectionInterval(currentlySelectedRow, currentlySelectedRow);
    }

    public void clearButtons(){

        if (categoryButtons != null){
            for(JButton button : categoryButtons){
                remove(button);
            }
            repaint();
        }
        if (productButtons != null){
            for(JButton button : productButtons){
                remove(button);
            }
            repaint();
        }
    }
}

