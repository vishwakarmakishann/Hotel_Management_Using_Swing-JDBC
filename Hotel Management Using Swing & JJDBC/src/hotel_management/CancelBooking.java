package hotel_management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CancelBooking extends JFrame {
    JTable table;
    DefaultTableModel model;
    JTextField field;
    JButton button;

    public CancelBooking() {
        setTitle("Cancel Booking");
        setSize(400, 400);
        setLayout(null);

        JLabel label = new JLabel("Your Active Bookings", JLabel.CENTER);
        label.setBounds(120, 10, 200, 25);
        add(label);

        model = new DefaultTableModel(new String[]{
            "Booking ID", "Room No.", "Check-in", "Check-out", "Status"
        }, 0);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 40, 350, 180);
        add(scrollPane);

        JLabel label2 = new JLabel("Enter Booking ID:");
        label2.setBounds(20, 240, 100, 25);
        add(label2);

        field = new JTextField();
        field.setBounds(130, 240, 80, 25);
        add(field);

        button = new JButton("Cancel Booking");
        button.setBounds(220, 240, 150, 25);
        add(button);

        button.addActionListener(e -> cancelBooking());

        loadActiveBookings();
        setVisible(true);
    }

    private void loadActiveBookings() {
        model.setRowCount(0);
        String username = Session.getUsername();

        String sql = "select * from bookings where username = ? and status = 'booked'";

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
                    rs.getString("status")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading bookings.");
        }
    }

    private void cancelBooking() {
        String bookingId = field.getText();
        if (bookingId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a booking ID.");
            return;
        }

        
        String username = Session.getUsername();
        String checkSql = "select * from bookings where id = ? and username = ? and status = 'booked'";

        try {
        	Connection conn = Conn.getConnection();
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, bookingId);
            checkStmt.setString(2, username);

            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Booking not found or already cancelled.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to cancel this booking?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            String cancelSql = "update bookings set status = 'cancelled' where id = ?";
            PreparedStatement cancelStmt = conn.prepareStatement(cancelSql);
            cancelStmt.setString(1, bookingId);
            int rows = cancelStmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully.");
                loadActiveBookings();
            } else {
                JOptionPane.showMessageDialog(this, "Error cancelling booking.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.");
        }
    }

    public static void main(String[] args) {
        new CancelBooking();
    }
}
