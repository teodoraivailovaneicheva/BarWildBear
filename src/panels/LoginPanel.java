package panels;

import frames.BarFrame;
import models.UserType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class LoginPanel extends BasePanel{
    private JLabel welcomeLabel;
    private JLabel logoLabel;

    public LoginPanel(BarFrame frame) {
        super(frame);

        welcomeLabel = new JLabel("Bar Wild Bear");
        welcomeLabel.setBounds(frame.getWidth()/2-150,100, 300, 50);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD,20));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        //Create logo image
        createLogo();

        JLabel enterPassLabel = new JLabel("Enter password");
        enterPassLabel.setBounds(frame.getWidth()/2-60, logoLabel.getY() + 60, 120, 40);
        enterPassLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterPassLabel);

        JPasswordField pinField = new JPasswordField();
        pinField.setBounds(frame.getWidth()/2-60, enterPassLabel.getY() + 200, 120, 40);
        pinField.setHorizontalAlignment(SwingConstants.CENTER);
        add(pinField);

        JButton loginButton = new JButton("Entrance");
        loginButton.setBounds(frame.getWidth()/2-50, pinField.getY() + 50, 100, 40);
        loginButton.addActionListener(e -> {
            if(frame.dataProvider.isCorrectLogin(pinField.getText())) {
                if(frame.dataProvider.loggedUser.getType() == UserType.MANAGER) {
                    frame.router.showManagerPanel();
                }else {
                    frame.router.showTables();
                }
            } else {
                showError("Wrong password. Please enter your password again!");
            }
        });
        add(loginButton);
    }

    private void createLogo() {
        logoLabel = new JLabel();
        logoLabel.setBounds(frame.getWidth()/2-125,welcomeLabel.getY() + 50, 250, 240);

        BufferedImage logo = null;
        try{
            logo = ImageIO.read(new File("src/bear.png"));
        }
        catch (Exception e){
            System.out.println("picture" + e.getMessage());
        }
        ImageIcon imageIcon = new ImageIcon(fitImage(logo, logoLabel.getWidth(), logoLabel.getHeight()));
        logoLabel.setIcon(imageIcon);
        add(logoLabel);
    }

    private Image fitImage(Image img , int w , int h){
        BufferedImage resizedImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0,w,h,null);
        g2.dispose();
        return resizedImage;
    }
}
