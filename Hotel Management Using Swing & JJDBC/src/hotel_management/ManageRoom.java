package hotel_management;

import javax.swing.*;
import java.awt.event.*;

public class ManageRoom extends JFrame {
    JButton addButton, viewButton, editButton, deleteButton, backButton;

    public ManageRoom() {
        setTitle("Manage Rooms");
        setSize(400, 400);
        setLayout(null);
        
        JLabel label = new JLabel("Room Management");
        label.setBounds(130, 20, 200, 30);
        add(label);

        addButton = new JButton("Add Room");
        addButton.setBounds(100, 70, 200, 30);
        add(addButton);

        viewButton = new JButton("View Rooms");
        viewButton.setBounds(100, 120, 200, 30);
        add(viewButton);

        editButton = new JButton("Edit Room");
        editButton.setBounds(100, 170, 200, 30);
        add(editButton);

        deleteButton = new JButton("Delete Room");
        deleteButton.setBounds(100, 220, 200, 30);
        add(deleteButton);

        backButton = new JButton("Back to Admin");
        backButton.setBounds(100, 270, 200, 30);
        add(backButton);

        addButton.addActionListener(e -> new AddRoom());
        viewButton.addActionListener(e -> new ViewRoom());
        editButton.addActionListener(e -> new EditRoom());
        deleteButton.addActionListener(e -> new DeleteRoom());
        backButton.addActionListener(e -> {
            dispose();
            new hotel_management.Admin();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ManageRoom();
    }
}
