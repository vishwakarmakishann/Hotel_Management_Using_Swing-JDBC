package hotel_management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditRoom extends JFrame {
    JLabel label, label2, label3, label4;
    JTextField field, field2;
    JComboBox<String> box, box2;
    JButton button, button2;

    public EditRoom() {
        setTitle("Edit Room");
        setSize(400, 400);
        setLayout(null);

        label = new JLabel("Room Number:");
        label2 = new JLabel("Type:");
        label3 = new JLabel("Price:");
        label4 = new JLabel("Status:");

        field = new JTextField();
        field2 = new JTextField();

        box = new JComboBox<>(new String[]{"AC", "Non-AC", "Deluxe"});
        box2 = new JComboBox<>(new String[]{"available", "booked", "maintenance"});

        button = new JButton("Load Room");
        button2 = new JButton("Update Room");

        label.setBounds(50, 30, 100, 25);
        field.setBounds(160, 30, 150, 25);
        button.setBounds(120, 65, 140, 30);

        label2.setBounds(50, 110, 100, 25);
        box.setBounds(160, 110, 150, 25);

        label3.setBounds(50, 150, 100, 25);
        field2.setBounds(160, 150, 150, 25);

        label4.setBounds(50, 190, 100, 25);
        box2.setBounds(160, 190, 150, 25);

        button2.setBounds(120, 240, 140, 30);

        add(label); add(field); add(button);
        add(label2); add(box);
        add(label3); add(field2);
        add(label4); add(box2);
        add(button2);

        button.addActionListener(e -> loadRoom());
        button2.addActionListener(e -> updateRoom());

        setVisible(true);
    }

    private void loadRoom() {
        String roomNo = field.getText();

        if (roomNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a room number.");
            return;
        }

        String sql = "select type, price, status from rooms where room_number = ?";
        try {
        	Connection conn = Conn.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, roomNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                box.setSelectedItem(rs.getString("type"));
                field2.setText(rs.getString("price"));
                box2.setSelectedItem(rs.getString("status"));
            } else {
                JOptionPane.showMessageDialog(this, "Room not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading room.");
        }
    }

    private void updateRoom() {
        String roomNo = field.getText();
        String type = (String) box.getSelectedItem();
        String price = field2.getText();
        String status = (String) box2.getSelectedItem();

        if (roomNo.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            
            String sql = "update rooms set type = ?, price = ?, status = ? where room_number = ?";
            
            	Connection conn = Conn.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, type);
                stmt.setString(2, price);
                stmt.setString(3, status);
                stmt.setString(4, roomNo);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Room updated successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed.");
                }
            

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price format.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.");
        }
    }

    public static void main(String[] args) {
        new EditRoom();
    }
}
