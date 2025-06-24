package hotel_management;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DeleteUser extends JFrame {
    JLabel label;
    JTextField field;
    JButton button;

    public DeleteUser() {
        setTitle("Delete User");
        setSize(400, 400);
        setLayout(null);

        label = new JLabel("Enter Username to Delete:");
        label.setBounds(50, 30, 200, 25);
        add(label);

        field = new JTextField();
        field.setBounds(50, 60, 200, 25);
        add(field);

        button = new JButton("Delete");
        button.setBounds(100, 100, 100, 30);
        add(button);

        button.addActionListener(e -> deleteUser());

        setVisible(true);
    }

    private void deleteUser() {
        String username = field.getText();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete user: " + username + "?",
                "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
        	Connection conn = Conn.getConnection();
            PreparedStatement stmt = conn.prepareStatement("delete from users where username = ?");
            stmt.setString(1, username);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "User deleted successfully.");
                new ManageUsers();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting user.");
        }
    }

    public static void main(String[] args) {
        new DeleteUser();
    }
}
