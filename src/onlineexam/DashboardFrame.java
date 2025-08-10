package onlineexam;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private User loggedUser;

    public DashboardFrame(User user) {
        this.loggedUser = user;
        setTitle("Online Exam - Dashboard");
        setSize(450, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel();
        p.setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome, " + loggedUser.getName());
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 16));
        lblWelcome.setBounds(20, 10, 350, 30);
        p.add(lblWelcome);

        JButton btnStart = new JButton("Start Exam");
        btnStart.setBounds(30, 60, 140, 40);
        p.add(btnStart);

        JButton btnProfile = new JButton("Profile / Update");
        btnProfile.setBounds(190, 60, 140, 40);
        p.add(btnProfile);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(350, 60, 70, 40);
        p.add(btnLogout);

        JLabel hint = new JLabel("<html><i>Note: Timer runs during exam and auto-submits when time is up.</i></html>");
        hint.setBounds(20, 120, 400, 40);
        p.add(hint);

        btnStart.addActionListener(e -> {
            new ExamFrame(loggedUser);
            dispose();
        });

        btnProfile.addActionListener(e -> new ProfileFrame(loggedUser));

        btnLogout.addActionListener(e -> {
            // go back to login
            new LoginFrame();
            dispose();
        });

        add(p);
        setVisible(true);
    }
}
