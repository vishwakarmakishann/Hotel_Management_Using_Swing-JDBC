package hotel_management;

import javax.swing.*;
import java.awt.*;

public class User extends JFrame {
    JLabel label;
    JButton button2, button3, button4, button5;

    public User() {
        setTitle("User Dashboard");
        setSize(400, 400);
        setLayout(null);

        label = new JLabel("Welcome, " + Session.getUsername());
        label.setBounds(130, 20, 250, 30);
        add(label);

        button2 = new JButton("Book a Room");
        button2.setBounds(100, 80, 200, 30);
        add(button2);

        button3 = new JButton("View Booking History");
        button3.setBounds(100, 130, 200, 30);
        add(button3);

        button4 = new JButton("Cancel Booking");
        button4.setBounds(100, 180, 200, 30);
        add(button4);

        button5 = new JButton("Logout");
        button5.setBounds(100, 230, 200, 30);
        add(button5);

        button2.addActionListener(e -> new BookRoom());
        button3.addActionListener(e -> new ViewMyBookings());
        button4.addActionListener(e -> new CancelBooking());
        button5.addActionListener(e -> {
            dispose();
            new Login();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new User();
    }
}
