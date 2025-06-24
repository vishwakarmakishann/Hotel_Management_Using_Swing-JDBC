package hotel_management;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame {
    JLabel label, label2;
    JTextField field1;
    JPasswordField field2;
    JButton button, button2;

    public Login() {
        setTitle("Login");
        setLayout(null);
        label = new JLabel("Username");
        label2 = new JLabel("Password");
        field1 = new JTextField(20);
        field2 = new JPasswordField(20);
        button = new JButton("Login");
        button2 = new JButton("New? Signup!");

        label.setBounds(50, 50, 100, 25);
        field1.setBounds(150, 50, 150, 25);
        label2.setBounds(50, 100, 100, 25);
        field2.setBounds(150, 100, 150, 25);
        button.setBounds(60, 160, 100, 25);
        button2.setBounds(180, 160, 120, 25);

        add(label);
        add(field1);
        add(label2);
        add(field2);
        add(button);
        add(button2);

        button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String username = field1.getText();
                String password = String.valueOf(field2.getPassword());

                String sql = "select id, username, role from users where username = ? and password = ?";

                try (Connection conn = Conn.getConnection();
                     PreparedStatement statement = conn.prepareStatement(sql)) {

                    statement.setString(1, username);
                    statement.setString(2, password);

                    ResultSet rs = statement.executeQuery();

                    if (rs.next()) {
                    	int id=rs.getInt("id");
    	            	String uname=rs.getString("username");
                        String role = rs.getString("role");
                        Session.setSession(id, uname, role);
                        JOptionPane.showMessageDialog(button, "Login Successful as " + role);
                        dispose(); 
                        if (role.equals("admin")) {
                            new Admin();
                        } else if (role.equals("user")) {
                            new User();
                        }
                    } else {
                        JOptionPane.showMessageDialog(button, "Invalid username or password");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(button, "Database error occurred.");
                }

			}
		});
        button2.addActionListener(e -> {
        	new SignUp();
        	dispose();
        });
       
        setVisible(true);
        setSize(400, 400);
    }
}
