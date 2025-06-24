package hotel_management;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EditUser extends JFrame {
	JLabel label, label2, label3;
    JTextField field, field2;
    JComboBox<String> box;
    JButton button, button2;

    public EditUser() {
        setTitle("Edit User");
        setSize(400, 400);
        setLayout(null);

        label = new JLabel("Username:");
        label.setBounds(50, 30, 100, 25);
        add(label);

        field = new JTextField();
        field.setBounds(150, 30, 150, 25);
        add(field);

        button = new JButton("Load");
        button.setBounds(150, 65, 100, 25);
        add(button);

        label2 = new JLabel("Password:");
        label2.setBounds(50, 110, 100, 25);
        add(label2);

        field2 = new JTextField();
        field2.setBounds(150, 110, 150, 25);
        add(field2);

        label3 = new JLabel("Role:");
        label3.setBounds(50, 150, 100, 25);
        add(label3);

        box = new JComboBox<>(new String[]{"admin", "user"});
        box.setBounds(150, 150, 150, 25);
        add(box);

        button2 = new JButton("Update");
        button2.setBounds(150, 200, 100, 30);
        add(button2);

        button.addActionListener(e -> loadUser());
        button2.addActionListener(e -> updateUser());

        setVisible(true);
    }

    private void loadUser() {
        String username = field.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a username.");
            return;
        }

        String sql = "select password, role from users where username = ?";
        try {
        	Connection conn = Conn.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                field2.setText(rs.getString("password"));
                box.setSelectedItem(rs.getString("role"));
                field2.setEnabled(true);
                box.setEnabled(true);
                button2.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading user.");
        }
    }

    private void updateUser() {
        String username = field.getText();
        String password = field2.getText();
        String role = (String) box.getSelectedItem();

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty.");
            return;
        }

        String sql = "update users set password = ?, role = ? where username = ?";
        try {
        	Connection conn = Conn.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setString(2, role);
            stmt.setString(3, username);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "User updated successfully.");
                new ManageUsers();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.");
        }
    }

    public static void main(String[] args) {
        new EditUser();
    }
}
