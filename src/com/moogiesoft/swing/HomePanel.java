package com.moogiesoft.swing;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.GridLayout;

import javax.swing.JSplitPane;
import javax.swing.ImageIcon;

public class HomePanel extends JPanel
{
	public HomePanel(CardPanel cardPanel) {
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblWelcomeToMichelles = new JLabel("Welcome to Michelle's awesome-ish website!");
		add(lblWelcomeToMichelles, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(HomePanel.class.getResource("/data/pics/main1.jpg")));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(HomePanel.class.getResource("/data/pics/main2.jpg")));
		panel.add(lblNewLabel_1);
		
		CustomerFavouriatesPanel customerFavoriatesPanel = new CustomerFavouriatesPanel(cardPanel);
		for (int i=0;i<20;i++)
		{
			customerFavoriatesPanel.addCustomerFavoriate(WebsiteTemplate.class.getResource("/data/pics/boutique.png"),"");
		}
		add(customerFavoriatesPanel, BorderLayout.SOUTH);
	}
	
}
