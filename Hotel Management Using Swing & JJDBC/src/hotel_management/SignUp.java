package hotel_management;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

class SignUp extends JFrame{
	JLabel label, label2, label3, label4;
	JTextField field, field2, field3, field4;
	JButton button, button2;
	JComboBox<String> box;
	String s[] = { "admin", "user" };
	public SignUp() {
		setTitle("Sign Up");
		setLayout(null);
		
		label = new JLabel("Name");
		label2 = new JLabel("Username");
		label3 = new JLabel("Password");
		label4 = new JLabel("Role");
		field = new JTextField(20);
		field2 = new JTextField(20);
		field3 = new JTextField(20);
		button = new JButton("Sign Up");
		button2 = new JButton("Login");
		box = new JComboBox<String>(s);
		add(box);
		add(label);
		add(label2);
		add(label3);
		add(label4);
		add(field);
		add(field2);
		add(field3);
		add(button);
		add(button2);

		label.setBounds(50,50,100,25);
		label2.setBounds(50,100,100,25);
		label3.setBounds(50,150,100,25);
		label4.setBounds(50,200,100,25);
		
		field.setBounds(150,50,100,25);
		field2.setBounds(150,100,100,25);
		field3.setBounds(150,150,100,25);
		box.setBounds(150,200,100,25);
		
		button.setBounds(50,250,100,25);
		button2.setBounds(170,250,100,25);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = field.getText();
				String username = field2.getText();
				String password = field3.getText();
				String role = (String) box.getSelectedItem();
				String sql = "insert into users(name, username, password, role) values(?,?,?,?)";
				try {
					PreparedStatement p = Conn.getConnection().prepareStatement(sql);
					p.setString(1, name);
					p.setString(2, username);
					p.setString(3, password);
					p.setString(4, role);
					p.execute();
					JOptionPane.showMessageDialog(button, "Successful");
					new Login();
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Login();
				dispose();
			}
		});
		
		setVisible(true);
		setSize(400,400);
	}
	public static void main(String[] args) {
		new SignUp();
	}
}


