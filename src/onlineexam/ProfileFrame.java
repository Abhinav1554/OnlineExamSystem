package onlineexam;

import javax.swing.*;
import java.awt.*;

public class ProfileFrame extends JFrame {
    private User user;
    private JTextField txtName, txtEmail;
    private JPasswordField txtOldPass, txtNewPass;

    public ProfileFrame(User user) {
        this.user = user;
        setTitle("Profile - " + user.getUsername());
        setSize(420, 320);
        setLocationRelativeTo(null);

        JPanel p = new JPanel(null);

        JLabel lblTitle = new JLabel("Update Profile");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setBounds(130, 10, 200, 30);
        p.add(lblTitle);

        JLabel l1 = new JLabel("Name:");
        l1.setBounds(30, 60, 100, 25);
        p.add(l1);
        txtName = new JTextField(user.getName());
        txtName.setBounds(140, 60, 220, 25);
        p.add(txtName);

        JLabel l2 = new JLabel("Email:");
        l2.setBounds(30, 100, 100, 25);
        p.add(l2);
        txtEmail = new JTextField(user.getEmail());
        txtEmail.setBounds(140, 100, 220, 25);
        p.add(txtEmail);

        JLabel l3 = new JLabel("Old Password:");
        l3.setBounds(30, 140, 100, 25);
        p.add(l3);
        txtOldPass = new JPasswordField();
        txtOldPass.setBounds(140, 140, 220, 25);
        p.add(txtOldPass);

        JLabel l4 = new JLabel("New Password:");
        l4.setBounds(30, 180, 100, 25);
        p.add(l4);
        txtNewPass = new JPasswordField();
        txtNewPass.setBounds(140, 180, 220, 25);
        p.add(txtNewPass);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(140, 220, 100, 30);
        p.add(btnSave);

        JLabel lblMsg = new JLabel("");
        lblMsg.setBounds(30, 255, 350, 25);
        p.add(lblMsg);

        btnSave.addActionListener(e -> {
            user.setName(txtName.getText().trim());
            user.setEmail(txtEmail.getText().trim());
            String oldPass = new String(txtOldPass.getPassword()).trim();
            String newPass = new String(txtNewPass.getPassword()).trim();

            if (!oldPass.isEmpty() || !newPass.isEmpty()) {
                // change password flow
                if (!user.getPassword().equals(oldPass)) {
                    lblMsg.setText("Old password is incorrect.");
                    return;
                }
                if (newPass.length() < 4) {
                    lblMsg.setText("New password must be at least 4 characters.");
                    return;
                }
                user.setPassword(newPass);
            }

            DataManager.updateUser(user);
            lblMsg.setText("Profile updated successfully.");
            txtOldPass.setText("");
            txtNewPass.setText("");
        });

        add(p);
        setVisible(true);
    }
}
