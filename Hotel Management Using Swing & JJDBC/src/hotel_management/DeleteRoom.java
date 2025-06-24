package hotel_management;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteRoom extends JFrame {
    JLabel label;
    JTextField field;
    JButton button;

    public DeleteRoom() {
        setTitle("Delete Room");
        setSize(400, 400);
        setLayout(null);

        label = new JLabel("Enter Room Number to Delete:");
        label.setBounds(50, 30, 250, 25);
        add(label);

        field = new JTextField();
        field.setBounds(50, 60, 200, 25);
        add(field);

        button = new JButton("Delete");
        button.setBounds(100, 100, 100, 30);
        add(button);

        button.addActionListener(e -> deleteRoom());

        setVisible(true);
    }

    private void deleteRoom() {
        String roomNo = field.getText();

        if (roomNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a room number.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete room " + roomNo + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
        	Connection conn = Conn.getConnection();
            PreparedStatement stmt = conn.prepareStatement("delete from rooms where room_number = ?");
            stmt.setString(1, roomNo);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Room deleted successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Room not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting room.");
        }
    }

    public static void main(String[] args) {
        new DeleteRoom();
    }
}
