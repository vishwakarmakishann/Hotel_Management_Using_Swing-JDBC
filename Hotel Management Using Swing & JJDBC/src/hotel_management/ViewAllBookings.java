package hotel_management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewAllBookings extends JFrame {
    JTable table;
    DefaultTableModel model;

    public ViewAllBookings() {
        setTitle("All Bookings (Admin)");
        setSize(400, 400);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("All Bookings", JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{
            "Booking ID", "Username", "Room Number", "Check-In", "Check-Out", "Status", "Booked At"
        }, 0);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadBookingData();

        setVisible(true);
    }

    private void loadBookingData() {

        String sql = "select * from bookings";

        try {
        	Connection conn = Conn.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("room_number"),
                    rs.getDate("checkin_date"),
                    rs.getDate("checkout_date"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading booking data.");
        }
    }

    public static void main(String[] args) {
        new ViewAllBookings();
    }
}
