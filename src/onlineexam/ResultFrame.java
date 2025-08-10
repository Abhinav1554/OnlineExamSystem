package onlineexam;

import javax.swing.*;
import java.awt.*;

public class ResultFrame extends JFrame {
    public ResultFrame(User user, int score, int total, String detail) {
        setTitle("Result - " + user.getName());
        setSize(700, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel(null);

        JLabel lbl = new JLabel("Exam Result");
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setBounds(280, 10, 200, 30);
        p.add(lbl);

        JLabel lscore = new JLabel("Score: " + score + " / " + total);
        lscore.setFont(new Font("Arial", Font.PLAIN, 16));
        lscore.setBounds(50, 60, 300, 30);
        p.add(lscore);

        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setText(detail);
        JScrollPane sp = new JScrollPane(ta);
        sp.setBounds(50, 110, 580, 300);
        p.add(sp);

        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.setBounds(260, 430, 160, 35);
        p.add(btnBack);

        btnBack.addActionListener(e -> {
            new DashboardFrame(user);
            dispose();
        });

        add(p);
        setVisible(true);
    }
}