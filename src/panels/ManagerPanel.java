package panels;

import frames.BarFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerPanel extends BasePanel{


    public ManagerPanel(BarFrame frame) {
        super(frame);

        JButton tablesButton = new JButton("Tables");
        tablesButton.setBounds(frame.getWidth()/2 - 75, frame.getHeight()/2 - 100,150,40);
        tablesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.router.showTables();
            }
        });
        add(tablesButton);

        JButton usersButton = new JButton("Employees");
        usersButton.setBounds(frame.getWidth()/2 - 75, tablesButton.getY() + 50, 150,40);
        usersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.router.showUsersPanel();
            }
        });
        add(usersButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(frame.getWidth()/2 - 75, usersButton.getY() + 50, 150,40);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.router.showLogin();
            }
        });
        add(exitButton);
    }
}
