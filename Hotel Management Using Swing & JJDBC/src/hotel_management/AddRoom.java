package hotel_management;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddRoom extends JFrame {
    JLabel label1, label2, label3, label4;
    JTextField field, field2;
    JComboBox<String> typeBox, statusBox;
    JButton button, button2;

    public AddRoom() {
        setTitle("Add Room");
        setSize(400, 400);
        setLayout(null);

        label1 = new JLabel("Room Number:");
        label2 = new JLabel("Room Type:");
        label3 = new JLabel("Price:");
        label4 = new JLabel("Status:");

        field = new JTextField();
        field2 = new JTextField();

        String[] types = {"AC", "Non-AC", "Deluxe"};
        String[] statuses = {"available", "booked", "maintenance"};

        typeBox = new JComboBox<>(types);
        statusBox = new JComboBox<>(statuses);
        button = new JButton("Add Room");
        button2 = new JButton("Cancel");

        label1.setBounds(50, 40, 100, 25);
        field.setBounds(180, 40, 150, 25);

        label2.setBounds(50, 80, 100, 25);
        typeBox.setBounds(180, 80, 150, 25);

        label3.setBounds(50, 120, 100, 25);
        field2.setBounds(180, 120, 150, 25);

        label4.setBounds(50, 160, 100, 25);
        statusBox.setBounds(180, 160, 150, 25);

        button.setBounds(80, 220, 100, 30);
        button2.setBounds(200, 220, 100, 30);

        add(label1); 
        add(field);
        add(label2); 
        add(typeBox);
        add(label3); 
        add(field2);
        add(label4); 
        add(statusBox);
        add(button);
        add(button2);

        button.addActionListener(e -> insertRoom());
        button2.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void insertRoom() {
        String roomNo = field.getText();
        String price = field2.getText();
        String type = (String) typeBox.getSelectedItem();
        String status = (String) statusBox.getSelectedItem();

        if (roomNo.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            

            Connection conn = Conn.getConnection();

            String sql = "CALL add_room(?, ?, ?, ?)";
            CallableStatement statement = conn.prepareCall(sql);
            statement.setString(1, roomNo);
            statement.setString(2, type);
            statement.setString(3, price);
            statement.setString(4, status);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Room added successfully!");
                dispose(); // close the form
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add room.");
            }

        } catch (SQLException e1) {
        	// TODO Auto-generated catch block
        	e1.printStackTrace();
        } 
    }
    public static void main(String[] args) {
		new AddRoom();
	}
}
