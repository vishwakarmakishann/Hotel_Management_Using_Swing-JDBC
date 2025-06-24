package hotel_management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewRoom extends JFrame {
    JTable table;
    DefaultTableModel model;

    public ViewRoom() {
        setTitle("View Rooms");
        setSize(400, 400);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Room List", JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Room Number", "Type", "Price", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadRoomData();

        setVisible(true);
    }

    private void loadRoomData() {
        model.setRowCount(0);
        try {
        	Connection conn = Conn.getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from rooms");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getString("status")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load room data.");
        }
    }

    public static void main(String[] args) {
        new ViewRoom();
    }
}
