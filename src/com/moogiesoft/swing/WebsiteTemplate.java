package com.moogiesoft.swing;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import com.moogiesoft.stockmanager.StockDatabase;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.CardLayout;


public class WebsiteTemplate extends JPanel
{

	/**
	 * Create the panel.
	 */
	public WebsiteTemplate()
	{
		setFont(new Font("Tahoma", Font.PLAIN, 11));
		setBackground(Color.DARK_GRAY);
		setLayout(new BorderLayout(0, 0));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.DARK_GRAY);
		add(leftPanel, BorderLayout.WEST);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.DARK_GRAY);
		add(rightPanel, BorderLayout.EAST);
		
		JPanel mainCentrePanel = new JPanel();
		mainCentrePanel.setBackground(Color.DARK_GRAY);
		add(mainCentrePanel, BorderLayout.CENTER);
		mainCentrePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.DARK_GRAY);
		mainCentrePanel.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));
		
		RoundedBackgroundBoxPanel topRoundedBackgroundBoxPanel = new RoundedBackgroundBoxPanel();
		topRoundedBackgroundBoxPanel.setBackground(Color.WHITE);
		
		topRoundedBackgroundBoxPanel.setLayout(new BorderLayout(0,0));
		
		
		TiledBackGroundJPanel tiledBackGroundJPanel = new TiledBackGroundJPanel(WebsiteTemplate.class.getResource("/data/pics/tree.png"),true);
		topRoundedBackgroundBoxPanel.setBackgroundBox(0, 50, 20);
		
		
		topRoundedBackgroundBoxPanel.add(tiledBackGroundJPanel,BorderLayout.CENTER);
		topPanel.add(topRoundedBackgroundBoxPanel, BorderLayout.SOUTH);
		
		JPanel Padding = new JPanel();
		Padding.setPreferredSize(new Dimension(10, 15));
		Padding.setSize(new Dimension(10, 15));
		topPanel.add(Padding, BorderLayout.NORTH);
		Padding.setLayout(new BorderLayout(0, 0));
		
		JPanel topNavButtonPanel = new JPanel();
		topPanel.add(topNavButtonPanel, BorderLayout.CENTER);
		topNavButtonPanel.setBackground(Color.DARK_GRAY);
		topNavButtonPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		topNavButtonPanel.add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblH = new JLabel("Home");
		lblH.setForeground(Color.WHITE);
		lblH.setBackground(Color.DARK_GRAY);
		panel_1.add(lblH);
		
//		JLabel lblNewLabel_1 = new JLabel("Shopping cart");
//		lblNewLabel_1.setForeground(Color.WHITE);
		PayPalViewCartButton cart2 = new PayPalViewCartButton();
		panel_1.add(cart2);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.DARK_GRAY);
		mainCentrePanel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel bottomCopyRightTextPanel = new JPanel();
		bottomCopyRightTextPanel.setBackground(Color.DARK_GRAY);
		bottomPanel.add(bottomCopyRightTextPanel, BorderLayout.SOUTH);
		
		JLabel lblCopyrightYourSite = new JLabel("Copyright Your Site Name :: All Rights reserved :: Design by blah blah");
		lblCopyrightYourSite.setForeground(Color.WHITE);
		bottomCopyRightTextPanel.add(lblCopyrightYourSite);
		
//		JPanel bottomTilePanel = new JPanel();
//		bottomTilePanel.setBackground(Color.DARK_GRAY);
//		bottomPanel.add(bottomTilePanel, BorderLayout.NORTH);
		
		
		RoundedBackgroundBoxPanel bottomRoundedBackgroundBoxPanel = new RoundedBackgroundBoxPanel();
		bottomRoundedBackgroundBoxPanel.setBackground(Color.WHITE);
		bottomRoundedBackgroundBoxPanel.setLayout(new BorderLayout(0,0));
		
		
		TiledBackGroundJPanel bottomtiledBackGroundJPanel = new TiledBackGroundJPanel(WebsiteTemplate.class.getResource("/data/pics/tree.png"),true);
		bottomRoundedBackgroundBoxPanel.setBackgroundBox(0, -50, 20);

		
		bottomRoundedBackgroundBoxPanel.add(bottomtiledBackGroundJPanel,BorderLayout.CENTER);
		bottomPanel.add(bottomRoundedBackgroundBoxPanel, BorderLayout.NORTH);
		
		JPanel mainCentreCentrePanel = new JPanel();
		mainCentreCentrePanel.setBackground(Color.WHITE);
		mainCentreCentrePanel.setSize(new Dimension(700, 500));
		mainCentrePanel.add(mainCentreCentrePanel, BorderLayout.CENTER);
		mainCentreCentrePanel.setLayout(new BorderLayout(0, 0));
		
		CardPanel cardPanel = new CardPanel();
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.WHITE);
		mainCentreCentrePanel.add(titlePanel, BorderLayout.NORTH);
		titlePanel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_2 = new JLabel();
		
		lblNewLabel_2.setIcon(new ImageIcon(WebsiteTemplate.class.getResource("/data/pics/title.png")));
		titlePanel.add(lblNewLabel_2, BorderLayout.WEST);
		
		JPanel socialisePanel = new JPanel();
		titlePanel.add(socialisePanel, BorderLayout.EAST);
		socialisePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel socialButtonPanel = new JPanel();
		socialButtonPanel.setBackground(Color.WHITE);
		socialisePanel.add(socialButtonPanel, BorderLayout.EAST);
		socialButtonPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("facebook");
		lblNewLabel_3.setBackground(Color.WHITE);
		socialButtonPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("twitter");
		lblNewLabel_4.setBackground(Color.WHITE);
		socialButtonPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("pinterest");
		lblNewLabel_5.setBackground(Color.WHITE);
		socialButtonPanel.add(lblNewLabel_5);
		
		JPanel socialRequestPanel = new JPanel();
		socialRequestPanel.setBackground(Color.WHITE);
		socialisePanel.add(socialRequestPanel, BorderLayout.WEST);
		socialRequestPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_6 = new JLabel("Socialise with us --> ");
		socialRequestPanel.add(lblNewLabel_6, BorderLayout.SOUTH);
		
		JPanel navPanel = new JPanel();
		mainCentreCentrePanel.add(navPanel, BorderLayout.WEST);
		navPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel navPanel1 = new JPanel();
		navPanel.add(navPanel1, BorderLayout.NORTH);
		navPanel1.setLayout(new BorderLayout(0, 0));
		
		JPanel boutiquePanel = new JPanel();
		navPanel1.add(boutiquePanel, BorderLayout.NORTH);
		boutiquePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel boutiqueLabel = new JLabel("");
		boutiqueLabel.setIcon(new ImageIcon(WebsiteTemplate.class.getResource("/data/pics/boutique.png")));
		boutiquePanel.add(boutiqueLabel, BorderLayout.NORTH);
		
		JPanel categoryPanel = new JPanel();
		boutiquePanel.add(categoryPanel, BorderLayout.SOUTH);
		categoryPanel.setLayout(new GridLayout(5, 0, 0, 0));
		
		JPanel categoryPanel1 = new JPanel();
		categoryPanel1.setBackground(Color.CYAN);
//		cardPanel.add(categoryPanel1, "categoryPanel1");
		StockDatabase stockDatabase = new StockDatabase();
		try
		{
			stockDatabase.reload();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		StockItemPanel stockItemPanel = new StockItemPanel(stockDatabase.getNonDeletedStock().get(0));
		cardPanel.add(stockItemPanel,"categoryPanel1");
		JButton lblCategory= cardPanel.createLinkLabel("categoryPanel1","Category");
		categoryPanel.add(lblCategory);
		
		JPanel categoryPanel2 = new JPanel();
		categoryPanel2.setBackground(Color.ORANGE);
		cardPanel.add(categoryPanel2, "categoryPanel2");
		JButton lblCategory2= cardPanel.createLinkLabel("categoryPanel2","Category");
		categoryPanel.add(lblCategory2);
		
		
		
		RoundedBackgroundBoxPanel roundedBackgroundBoxPanel1 = new RoundedBackgroundBoxPanel();
		roundedBackgroundBoxPanel1.setBackground(Color.PINK);
		roundedBackgroundBoxPanel1.setBackgroundBox(0, 0, 35);
		cardPanel.add(roundedBackgroundBoxPanel1, "categoryPanel3");
		JButton lblCategory3= cardPanel.createLinkLabel("categoryPanel3","Category");
		categoryPanel.add(lblCategory3);
		
//		JLabel label_2 = new JLabel("Category");
//		categoryPanel.add(label_2);
		
	
		
//		JLabel label_3 = new JLabel("Category");
//		categoryPanel.add(label_3);
		
		JPanel navPanel2 = new JPanel();
		navPanel1.add(navPanel2, BorderLayout.SOUTH);
		navPanel2.setLayout(new BorderLayout(0, 0));
		
		JPanel infoPanel = new JPanel();
		navPanel2.add(infoPanel, BorderLayout.NORTH);
		infoPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel infoOptionsPanel = new JPanel();
		infoPanel.add(infoOptionsPanel, BorderLayout.SOUTH);
		infoOptionsPanel.setLayout(new GridLayout(5, 0, 0, 0));
		
//		JLabel lblHome = new JLabel("Home");
//		infoOptionsPanel.add(lblHome);

		HomePanel homePanel = new HomePanel(cardPanel);
		cardPanel.add(homePanel, "categoryPanel4");
		JButton lblHome= cardPanel.createLinkLabel("categoryPanel4","Home");
		infoOptionsPanel.add(lblHome);
		
		JLabel lblCatalogue = new JLabel("Catalogue");
		infoOptionsPanel.add(lblCatalogue);
		
		JLabel lblTerms = new JLabel("Terms");
		infoOptionsPanel.add(lblTerms);
		
		JLabel lblLinks = new JLabel("Links");
		infoOptionsPanel.add(lblLinks);
		
		JLabel lblContact = new JLabel("Contact");
		infoOptionsPanel.add(lblContact);
		
		JLabel infoLabel = new JLabel("");
		infoLabel.setIcon(new ImageIcon(WebsiteTemplate.class.getResource("/data/pics/info.png")));
		infoPanel.add(infoLabel, BorderLayout.NORTH);
		
		JPanel navPanel3 = new JPanel();
		navPanel2.add(navPanel3, BorderLayout.SOUTH);
		navPanel3.setLayout(new BorderLayout(0, 0));
		
		JPanel cartPanel = new JPanel();
		navPanel3.add(cartPanel, BorderLayout.NORTH);
		cartPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel cartOptionsPanel = new JPanel();
		cartPanel.add(cartOptionsPanel, BorderLayout.SOUTH);
		cartOptionsPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		PayPalViewCartButton cart1 = new PayPalViewCartButton();
//		JLabel lblNewLabel = new JLabel("View Cart");
		cartOptionsPanel.add(cart1);
		
		JLabel cartLabel = new JLabel("");
		cartPanel.add(cartLabel, BorderLayout.NORTH);
		cartLabel.setIcon(new ImageIcon(WebsiteTemplate.class.getResource("/data/pics/cart.png")));
		
		JPanel navPanel4 = new JPanel();
		navPanel3.add(navPanel4, BorderLayout.SOUTH);
		navPanel4.setLayout(new BorderLayout(0, 0));
		
		JPanel searchPanel = new JPanel();
		navPanel4.add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel searchLabel = new JLabel("");
		searchLabel.setIcon(new ImageIcon(WebsiteTemplate.class.getResource("/data/pics/searchSite.png")));
		searchPanel.add(searchLabel, BorderLayout.NORTH);
		
		mainCentreCentrePanel.add(cardPanel, BorderLayout.CENTER);
		cardPanel.setLayout(new CardLayout(0, 0));
		
		

	}

}
