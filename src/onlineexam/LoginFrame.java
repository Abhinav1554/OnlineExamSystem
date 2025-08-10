package onlineexam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblStatus;

    public LoginFrame() {
        setTitle("Online Exam - Login");
        setSize(400, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel p = new JPanel();
        p.setLayout(null);

        JLabel lbl = new JLabel("Login");
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        lbl.setBounds(160, 10, 200, 30);
        p.add(lbl);

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(40, 60, 100, 25);
        p.add(l1);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 60, 180, 25);
        p.add(txtUsername);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(40, 100, 100, 25);
        p.add(l2);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 100, 180, 25);
        p.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 140, 80, 30);
        p.add(btnLogin);

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(250, 140, 80, 30);
        p.add(btnExit);

        lblStatus = new JLabel("");
        lblStatus.setBounds(40, 180, 320, 25);
        p.add(lblStatus);

        btnLogin.addActionListener(e -> doLogin());
        btnExit.addActionListener(e -> System.exit(0));

        add(p);
        setVisible(true);

        // prefill username for convenience (optional)
        txtUsername.setText("student");

        // on close, also save data
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                DataManager.saveUsers();
                DataManager.saveQuestions();
            }
        });
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblStatus.setText("Enter username and password.");
            return;
        }

        User user = DataManager.findUser(username, password);
        if (user != null) {
            lblStatus.setText("Login successful. Opening dashboard...");
            // open dashboard and close login frame
            SwingUtilities.invokeLater(() -> {
                new DashboardFrame(user);
                dispose();
            });
        } else {
            lblStatus.setText("Invalid username or password.");
        }
    }
}