package gui;

import javax.swing.*;

import client_side.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SystemPage extends JFrame implements ActionListener {
	static String loggedUser;
	JButton addPurchase;
	JButton viewDetails;
	JPanel panel;
	JLabel detailsLabel;
	SystemPage(String un) {
		loggedUser = un;
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("System Page");
		setSize(400, 200);
		this.setVisible(true);
		JLabel HelloLabel = new JLabel("Hello " + un);
		detailsLabel = new JLabel();

		this.getContentPane().add(HelloLabel);
		this.getContentPane().add(detailsLabel);
		
		addPurchase = new JButton("Add purchase");
		viewDetails = new JButton("View my details");
		panel = new JPanel(new GridLayout(3, 1));
		panel.add(addPurchase);
		panel.add(viewDetails);
		panel.add(HelloLabel);
		panel.add(detailsLabel);
		add(panel, BorderLayout.CENTER);
		addPurchase.addActionListener(this);
		viewDetails.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae) {
		StringBuilder msg = new StringBuilder();
		if (ae.getSource() == addPurchase) {
			if (Client.addPurchase(msg)) { // TODO
				JOptionPane.showMessageDialog(this, msg);
				System.out.println("purchase added.");
			} else {
				System.out.println(msg);
				JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (ae.getSource() == viewDetails) {
			if (Client.getNumPurchases(msg)) {
				detailsLabel.setText("username: " + loggedUser + ". num purchases: " + msg);
				System.out.println("user details: " + "username = " + loggedUser + ". num purchases = " + msg);
			} else {
				System.out.println(msg);
				JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}