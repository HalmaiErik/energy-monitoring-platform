package ui.login;

import rpc.SmartApplianceService;
import rpc.SmartApplianceServiceImpl;
import ui.main.MainUi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

public class LoginUi extends JFrame {

    private JTextField usernameText;
    private JPanel panel;
    private JButton loginButton;
    private JLabel label;

    private final SmartApplianceService smartApplianceService;

    public LoginUi() throws MalformedURLException {
        this.setTitle("Login");
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(200, 200));
        this.setVisible(true);

        smartApplianceService = new SmartApplianceServiceImpl();
        addListeners();
    }

    private void addListeners() {
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameText.getText();
                try {
                    MainUi mainUi = new MainUi(username);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });
    }
}
