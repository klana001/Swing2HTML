package com.moogiesoft.stockmanager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class StockManager extends JFrame
{
	private JTextField searchTextField;
	final StockListPanel stockListPanel;
	StockManager()
	{
	    try 
	    {
	      UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
	    } 
	    catch (Exception e) 
	    {
	      e.printStackTrace();
	    }

		
//		WebLookAndFeel.install ();
//		WebLookAndFeel.setDecorateFrames(true);
		setTitle("Stock Manager");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(0, 0));
		stockListPanel = new StockListPanel();
		mainPanel.add(stockListPanel);
		getContentPane().add(mainPanel,BorderLayout.CENTER);
		
		JPanel topPanel = new JPanel(new BorderLayout(0,0));
		
		mainPanel.add(topPanel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		topPanel.add(panel,BorderLayout.WEST);
		
		JCheckBox showDeletedItemsCheckBox = new JCheckBox("Show Deleted Items");
		showDeletedItemsCheckBox.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				stockListPanel.setShowDeleted(showDeletedItemsCheckBox.isSelected());
				
			}
		});
		panel.add(showDeletedItemsCheckBox);
		
		searchTextField = new JTextField();
		panel.add(searchTextField);
		searchTextField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		panel.add(btnSearch);
		
		JPanel panel_1 = new JPanel();
		
		JButton btnSynchronize = new JButton("Synchronize with PayPal");
		panel_1.add(btnSynchronize);
		btnSynchronize.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PayPalSynchronisationDialog payPalSynchronisationDialog = new PayPalSynchronisationDialog();
				payPalSynchronisationDialog.setStockDatabase(stockListPanel.getStockDatabase());
				payPalSynchronisationDialog.pack();
				payPalSynchronisationDialog.setLocationRelativeTo(null);
				payPalSynchronisationDialog.synchronise();
				payPalSynchronisationDialog.setVisible(true);

			}
		});
		
		JButton btnPublishToWebsite = new JButton("Publish to Website");
		panel_1.add(btnPublishToWebsite);
		
		
		topPanel.add(panel_1,BorderLayout.EAST);
		setVisible(true);
	}
	
	public static void main(String args[]) throws Exception
	{
//		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		
//		UIManager.installLookAndFeel("AdvancedNimbus", AdvancedNimbusLookAndFeel.class.getName());
//		UIManager.setLookAndFeel(AdvancedNimbusLookAndFeel.class.getName());
//		JFrame.setDefaultLookAndFeelDecorated(true);
		
	    try 
	    {
	      UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
	    } 
	    catch (Exception e) 
	    {
	      e.printStackTrace();
	    }

		
//		WebLookAndFeel.install ();
//		WebLookAndFeel.setDecorateFrames(true);
		new StockManager();
	}
}
