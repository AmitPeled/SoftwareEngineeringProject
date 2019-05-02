package gui;

import javax.swing.*;

import client_side.Client;

import java.awt.*;
import java.awt.event.*;

class SystemEntrance extends JFrame implements ActionListener {
	JButton Login;
	JButton Register;

	JPanel panel;
	JLabel unameLabel, passLabel;
	final JTextField unameText, passText;

	SystemEntrance() {
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

	public void actionPerformed(ActionEvent ae) {
		String un = unameText.getText();
		String ps = passText.getText();
		StringBuilder msgReceived = new StringBuilder();
		if (ae.getSource() == Login) {
			if (Client.verifyUser(un, ps, msgReceived)) {
				new SystemPage(un);
			} else {
				System.out.println(msgReceived);
				JOptionPane.showMessageDialog(this, msgReceived, "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (ae.getSource() == Register) {
			if (Client.register(un, ps, msgReceived)) {
				new SystemPage(un);
			} else {
				System.out.println(msgReceived);
				JOptionPane.showMessageDialog(this, msgReceived, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static void main(String arg[]) {
		SystemEntranceDemo.main(arg);
	}
}

class SystemEntranceDemo {
	public static void main(String arg[]) {
		try {
			SystemEntrance frame = new SystemEntrance();
			frame.setSize(300, 100);
			frame.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}