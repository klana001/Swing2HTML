package com.moogiesoft.swing;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.moogiesoft.stockmanager.StockImage;
import com.moogiesoft.stockmanager.StockItem;
import com.moogiesoft.swing.CustomerFavouriatesPanel.CustomerFavoriate;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Dimension;

public class StockItemPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public StockItemPanel(StockItem item) {
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setSize(new Dimension(200, 200));
		panel_3.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		CardPanel cardPanel = new CardPanel();
		cardPanel.setPreferredSize(new Dimension(200, 200));
		cardPanel.setSize(new Dimension(200, 200));
		
		panel_2.add(cardPanel, BorderLayout.NORTH);
		
//		JPanel panel_4 = new JPanel();
//		panel_4.setBorder(new EmptyBorder(20, 0, 0, 0));
//		panel_3.add(panel_4);
//		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane,BorderLayout.SOUTH);
		
		ImagePanel imagePanel = new ImagePanel();
		scrollPane.setViewportView(imagePanel);
		
		
		for (StockImage image : item.getImages())
		{
			String id = ""+new Random().nextInt(Integer.MAX_VALUE);
			JLabel label = new StretchJLabel();
			ImageIcon imageIcon = new ImageIcon(WebsiteTemplate.class.getResource("/"+image.getImagePath()));
			label.setIcon(imageIcon);
			label.setPreferredSize(cardPanel.getPreferredSize());
			cardPanel.add(label,id);
			LinkLabel linkLabel = cardPanel.createLinkLabel(id, WebsiteTemplate.class.getResource("/"+image.getThumbnailPath()));
			imagePanel.add(linkLabel);
		}


		
//		JLabel lblThumbnail = new JLabel("thumbnail1");
//		panel_4.add(lblThumbnail);
//		
//		JLabel lblThumbnail_1 = new JLabel("Thumbnail2");
//		panel_4.add(lblThumbnail_1);
//		
//		JLabel lblThumbnail_2 = new JLabel("Thumbnail3");
//		panel_4.add(lblThumbnail_2);
//		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EmptyBorder(0, 20, 20, 20));
		panel.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 20));
		
		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7, BorderLayout.NORTH);
		panel_7.setLayout(new BorderLayout(0, 20));
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel_7.add(lblNewLabel_1, BorderLayout.WEST);
		
		JPanel panel_8 = new JPanel();
		panel_6.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 20));
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		panel_8.add(lblNewLabel_2, BorderLayout.NORTH);
		
		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_10 = new JPanel();
		panel_9.add(panel_10, BorderLayout.NORTH);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("New button");
		panel_10.add(btnNewButton, BorderLayout.EAST);

	}

}
