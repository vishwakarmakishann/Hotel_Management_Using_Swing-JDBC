package hotel_management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewMyBookings extends JFrame {
    JTable table;
    DefaultTableModel model;

    public ViewMyBookings() {
        setTitle("My Booking History");
        setSize(400, 400);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Your Bookings", JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{
            "Booking ID", "Room Number", "Check-in", "Check-out", "Status", "Booked At"
        }, 0);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadBookings();

        setVisible(true);
    }

    private void loadBookings() {
        model.setRowCount(0);
        String username = Session.getUsername();

        String sql = "select * from bookings where username = ?";

        try {
        	Connection conn = Conn.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("room_number"),
                    rs.getDate("checkin_date"),
                    rs.getDate("checkout_date"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading your bookings.");
        }
    }

    public static void main(String[] args) {
        new ViewMyBookings();
    }
}
