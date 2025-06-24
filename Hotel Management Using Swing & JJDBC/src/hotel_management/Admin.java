package hotel_management;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Admin extends JFrame {
	JLabel label;
	JButton button, button2, button3, button4;
    public Admin() {
        setTitle("Admin Dashboard");
        setSize(400, 400);
        setLayout(null);

        label = new JLabel("Welcom, "+Session.getUsername());
        label.setBounds(120, 20, 200, 30);
        add(label);

        button = new JButton("Manage Rooms");
        button.setBounds(100, 80, 200, 30);
        add(button);

        button2 = new JButton("View All Bookings");
        button2.setBounds(100, 130, 200, 30);
        add(button2);

        button3 = new JButton("Manage Users");
        button3.setBounds(100, 180, 200, 30);
        add(button3);

        button4 = new JButton("Logout");
        button4.setBounds(100, 230, 200, 30);
        add(button4);

        button.addActionListener(e -> {
        	new ManageRoom();
        	dispose();
        });

        button2.addActionListener(e -> new ViewAllBookings());

        button3.addActionListener(e -> new ManageUsers());

        button4.addActionListener(e -> {
            dispose(); 
            new Login();
        });

        setVisible(true);
    }
}
