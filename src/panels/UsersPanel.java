package panels;

import frames.BarFrame;
import models.User;
import models.UserType;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsersPanel extends BasePanel{
    public DefaultTableModel usersTableModel;
    public JTable usersTable;

    public JTextField searchField;
    public JTextField nameField;
    public JTextField pinField;
    public JTextField phoneField;
    public JComboBox typeComboBox;


    public UsersPanel(BarFrame frame) {
        super(frame);

        String cols[] = {"Name", "PIN", "Phone", "Position"};
        usersTableModel = new DefaultTableModel();
        usersTableModel.setColumnIdentifiers(cols);

        usersTable = new JTable(usersTableModel);

        JScrollPane usersTablePane = new JScrollPane(usersTable);
        usersTablePane.setBounds(0,0,frame.getWidth(),200);
        add(usersTablePane);

        frame.dataProvider.fetchUsers(usersTableModel);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(frame.getWidth() - 158, usersTablePane.getHeight() + 30, 150, 40);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
        add(deleteButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(frame.getWidth() - 158, deleteButton.getY() + 50,150,40);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.router.showLogin();
            }
        });
        add(exitButton);
        initializeTextFields();
        initializeSearchField();
    }
    public void addUser() {
        UserType userType ;
        if (typeComboBox.getSelectedIndex() == 0){
            userType = UserType.MANAGER;
        }else {
            userType = UserType.WAITER;
   }
        User newUser = new User(nameField.getText(), pinField.getText(), phoneField.getText(), userType);
        frame.dataProvider.users.add(newUser);
        frame.dataProvider.fetchUsers(usersTableModel);
    }
    public void deleteUser() {
        if(usersTable.getSelectedRow() < 0) {
            showError("You have no selected user!");
            return;
        }
        
        boolean isYes = showQuestionPopup("Are you sure you want to delete this user?");
        if(isYes) {
            User selectedUser = frame.dataProvider.users.get(usersTable.getSelectedRow());
            if(selectedUser.getPhoneNumber().equals(frame.dataProvider.loggedUser.getPhoneNumber())) {
                showError("You cannot delete the current user!");
                return;
            }
            frame.dataProvider.users.remove(usersTable.getSelectedRow());
            frame.dataProvider.fetchUsers(usersTableModel);
        }
    }

    public void initializeTextFields() {
        nameField = new JTextField("Name");
        nameField.setBounds(8, 230, 150, 40);
        add(nameField);

        pinField = new JTextField("PIN");
        pinField.setBounds(8, nameField.getY() + 50, 150, 40);
        add(pinField);

        phoneField = new JTextField("Phone");
        phoneField.setBounds(8, pinField.getY() + 50, 150, 40);
        add(phoneField);

        String options[] = {"Manager", "Waiter", "Bartender"};
        typeComboBox = new JComboBox(options);
        typeComboBox.setBounds(8, phoneField.getY() + 50, 150, 40);
        add(typeComboBox);

        JButton addButton = new JButton("Add");
        addButton.setBounds(8, typeComboBox.getY() + 50, 150, 40);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        add(addButton);
    }

    public void initializeSearchField() {
        searchField = new JTextField();
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                frame.dataProvider.isSearchingUsers = true;
                frame.dataProvider.searchUsers(searchField.getText());
                frame.dataProvider.fetchUsers(usersTableModel);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(searchField.getText().isEmpty()) {
                    frame.dataProvider.isSearchingUsers = false;
                }
                frame.dataProvider.searchUsers(searchField.getText());
                frame.dataProvider.fetchUsers(usersTableModel);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        searchField.setBounds(frame.getWidth()/2-75, 230, 150, 40);
        add(searchField);

    }
}
