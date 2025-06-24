package hotel_management;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class BookRoom extends JFrame {
	JLabel label, label2, label3;
    JTextField field, field2, field3;
    JButton button;

    public BookRoom() {
        setTitle("Book a Room");
        setSize(400, 400);
        setLayout(null);

        label = new JLabel("Room Number:");
        label.setBounds(50, 30, 120, 25);
        add(label);

        field = new JTextField();
        field.setBounds(180, 30, 150, 25);
        add(field);

        label2 = new JLabel("Check-in (yyyy-mm-dd):");
        label2.setBounds(50, 80, 150, 25);
        add(label2);

        field2 = new JTextField();
        field2.setBounds(210, 80, 120, 25);
        add(field2);

        label3 = new JLabel("Check-out (yyyy-mm-dd):");
        label3.setBounds(50, 120, 160, 25);
        add(label3);

        field3 = new JTextField();
        field3.setBounds(210, 120, 120, 25);
        add(field3);

        button = new JButton("Book Now");
        button.setBounds(130, 180, 120, 30);
        add(button);

        button.addActionListener(e -> bookRoom());

        setVisible(true);
    }

    private void bookRoom() {
        String roomNo = field.getText();
        String checkin = field2.getText();
        String checkout = field3.getText();
        String username = Session.getUsername();

        if (roomNo.isEmpty() || checkin.isEmpty() || checkout.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        if (!isValidDate(checkin) || !isValidDate(checkout)) {
            JOptionPane.showMessageDialog(this, "Invalid date format.");
            return;
        }

        try {
        	Connection conn = Conn.getConnection();
            String checkSql = """
                select * from bookings
                where room_number = ?
                and (
                    (? between checkin_date and checkout_date)
                    OR (? between checkin_date and checkout_date)
                    OR (checkin_date between ? and ?)
                )
            """;

            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, roomNo);
            checkStmt.setString(2, checkin);
            checkStmt.setString(3, checkout);
            checkStmt.setString(4, checkin);
            checkStmt.setString(5, checkout);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Room is not available for selected dates.");
                return;
            }

            String insertSql = "insert into bookings (username, room_number, checkin_date, checkout_date, status) VALUES (?, ?, ?, ?, 'booked')";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, username);
            insertStmt.setString(2, roomNo);
            insertStmt.setString(3, checkin);
            insertStmt.setString(4, checkout);

            int rows = insertStmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Room booked successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Booking failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.");
        }
    }

    private boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        new BookRoom();
    }
}
