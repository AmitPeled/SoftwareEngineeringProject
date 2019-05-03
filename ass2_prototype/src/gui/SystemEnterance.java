package gui;

import javax.swing.*;
import client_side.Client;
import java.awt.*;
import java.awt.event.*;

class SystemEnterance extends JFrame implements ActionListener {
	JButton Login;
	JButton Register;

	JPanel panel;
	JLabel unameLabel, passLabel;
	final JTextField unameText, passText;

	SystemEnterance() {
		unameLabel = new JLabel();
		unameLabel.setText("Username:");
		unameText = new JTextField(18);

		passLabel = new JLabel();
		passLabel.setText("Password:");
		passText = new JPasswordField(18);

		Login = new JButton("Login");
		Register = new JButton("Register");

		panel = new JPanel(new GridLayout(3, 1));
		panel.add(unameLabel);
		panel.add(unameText);
		panel.add(passLabel);
		panel.add(passText);
		panel.add(Login);
		panel.add(Register);

		add(panel, BorderLayout.CENTER);
		Login.addActionListener(this);
		Register.addActionListener(this);
		setTitle("System Entrance FORM");
	}

	public void actionPerformed(ActionEvent actionEvent) {
		String username = unameText.getText();
		String password = passText.getText();
		Client client = new Client(username, password);
		StringBuilder msgReceived = new StringBuilder();
		if (actionEvent.getSource() == Login) {
			if (client.verifyUser(msgReceived)) {
				new SystemPage(client);
			} else {
				System.out.println(msgReceived);
				JOptionPane.showMessageDialog(this, msgReceived, "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (actionEvent.getSource() == Register) {
			if (client.register(msgReceived)) {
				new SystemPage(client);
			} else {
				System.out.println(msgReceived);
				JOptionPane.showMessageDialog(this, msgReceived, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static void main(String arg[]) {
		SystemEntranceDemonstration.main(arg);
	}
}

class SystemEntranceDemonstration {
	public static void main(String arg[]) {
		try {
			SystemEnterance frame = new SystemEnterance();
			frame.setSize(300, 100);
			frame.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

}
