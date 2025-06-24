package hotel_management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ManageUsers extends JFrame {
    JTable table;
    DefaultTableModel model;
    JButton addBtn, editBtn, deleteBtn, backBtn;

    public ManageUsers() {
        setTitle("Manage Users");
        setSize(400, 400);
        setLayout(null);

        JLabel label = new JLabel("User Management");
        label.setBounds(130, 10, 150, 30);
        add(label);

        model = new DefaultTableModel(new String[]{"ID", "Username", "Role"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 50, 350, 180);
        add(scrollPane);

        addBtn = new JButton("Add User");
        editBtn = new JButton("Edit User");
        deleteBtn = new JButton("Delete User");
        backBtn = new JButton("Back");

        addBtn.setBounds(30, 250, 150, 30);
        editBtn.setBounds(200, 250, 150, 30);
        deleteBtn.setBounds(30, 300, 150, 30);
        backBtn.setBounds(200, 300, 150, 30);

        add(addBtn);
        add(editBtn);
        add(deleteBtn);
        add(backBtn);

        loadUsers();

        addBtn.addActionListener(e -> new SignUp());
        editBtn.addActionListener(e -> {
        	new EditUser();
        	dispose();
        });
        deleteBtn.addActionListener(e -> {
        	new DeleteUser();
        	dispose();
        });
        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void loadUsers() {
        model.setRowCount(0);
        try {
        	Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select id, username, role from users");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("role")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading user data.");
        }
    }

    public static void main(String[] args) {
        new ManageUsers();
    }
}
