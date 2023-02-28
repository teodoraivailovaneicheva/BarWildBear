package panels;

import frames.BarFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablesPanel extends BasePanel implements ActionListener {
    public TablesPanel(BarFrame frame) {
        super(frame);

        int buttonX = frame.getWidth() / 2 - 145;
        int buttonY = frame.getHeight() / 2 - 100;

        for(int i = 0; i < frame.dataProvider.tables.size(); i++) {
            Integer tableNumber = frame.dataProvider.tables.get(i);

            if(i == 5) {
                buttonX = frame.getWidth() / 2 - 145;
                buttonY = frame.getHeight() / 2 - 40;
            }


            JButton tableButton = new JButton(Integer.toString(tableNumber));
            tableButton.addActionListener(this);
            tableButton.setBounds(buttonX, buttonY, 50,50);
            add(tableButton);

            buttonX += 60;


            JButton exitButton = new JButton("Exit");
            exitButton.setBounds(frame.getWidth() - 158, getY() + 50,150,40);
            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.router.showLogin();
                }
            });
            add(exitButton);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String tableNumberString = ((JButton)e.getSource()).getText();
        int tableNumber = Integer.parseInt(tableNumberString);
        frame.router.showOrderPanel(tableNumber);
    }
}
