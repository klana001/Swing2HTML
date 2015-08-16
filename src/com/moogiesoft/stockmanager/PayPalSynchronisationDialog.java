package com.moogiesoft.stockmanager;

import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;




import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PayPalSynchronisationDialog extends JDialog implements Runnable
{
	PayPalSynchronisationDialog instance;
	private JTextArea textArea;
	private JButton btnDone;
	private StockDatabase stockDatabase;
	PayPalSynchronisationDialog()
	{
		instance = this;
		setPreferredSize(new Dimension(400, 400));
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Synchronising with PayPal...");
		setModal(true);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instance.dispose();
			}
		});
		btnDone.setEnabled(false);
		panel_2.add(btnDone, BorderLayout.EAST);
		
	}
	
	public void setStockDatabase(StockDatabase stockDatabase)
	{
		this.stockDatabase=stockDatabase;
	}
	
	public void synchronise()
	{
		new Thread(this,"PayPalSynch").start();
	}
	
	@Override
	public void run()
	{
		textArea.append("Downloading buttons from PayPal...\n");
		PayPalConnector palConnector=new PayPalConnector();
		
		List<StockItem> allStock = stockDatabase.getAllStock();
		allStock.removeIf(s->s.isNew());
		
		try
		{
			List<PayPalButton> buttons = palConnector.getPayPalButtons(textArea,stockDatabase);
			textArea.append("Success!\n");
			textArea.append("Buttons:\n");
			buttons.forEach(b->textArea.append(b.toString()+"\n"));
			textArea.append("\n");
			
			textArea.append("Downloading buttons from PayPal...Done.\n");
			
			

			
			ArrayList<PayPalButton> deletedButtons = new ArrayList<PayPalButton>();
			HashMap<StockItem, PayPalButton> map = new HashMap<StockItem, PayPalButton>();
			
			textArea.append("Removing orphaned buttons...\n");
			out:
			for (PayPalButton button : buttons)
			{
				for (StockItem item : allStock)
				{
					if (button.getPayPalId().equals(item.getPayPalId()))
					{
						map.put(item, button);
						continue out;
					}
				}
				palConnector.deleteButton(button);
				textArea.append("Orphan button ("+button+") removed from PayPal.\n");
				deletedButtons.add(button);
			}
			buttons.removeAll(deletedButtons);
			textArea.append("Removing orphaned buttons...done.\n");

			textArea.append("Removing buttons for deleted stock...\n");
			for (StockItem item : map.keySet())
			{
				if (item.isDeleted())
				{
					PayPalButton button = map.get(item);
					palConnector.deleteButton(button);
					deletedButtons.add(button);
					textArea.append("Deleted item's button ("+button+") removed from PayPal.\n");
				}
			}
			textArea.append("Removing buttons for deleted stock...done.\n");
			
			textArea.append("Adding buttons for new stock...\n");
			for (StockItem item : stockDatabase.getNonDeletedStock())
			{
				PayPalButton button = map.get(item);
				if (button==null)
				{
					button = new PayPalButton(item);
					palConnector.createButton(button);
					item.setPayPalId(button.getPayPalId());
					textArea.append("Created item button ("+button+") in PayPal.\n");
				}
			}
			textArea.append("Adding buttons for new stock...Done.\n");
			
			textArea.append("Updating Stock levels...\n");
			for (StockItem item : stockDatabase.getNonDeletedStock())
			{
				PayPalButton button = map.get(item);
				if (button!=null)
				{
//					if (button.getQuantity()!=item.getQuantity())
//					{
						if (button.getModifiedDate().compareTo(item.getModifiedDate())>0)
						{
							item.setQuantity(button.getQuantity());
							textArea.append("Updating database with more recent PayPal stock level for button ("+button+")...");
							textArea.append("Success!\n");
						}
						else if (button.getModifiedDate().compareTo(item.getModifiedDate())<0)
						{
							button.setQuantity(item.getQuantity());
							textArea.append("Updating PayPal with more recent database stock level for button ("+button+")...");
							palConnector.updateButton(button);
							textArea.append("Success!\n");
						}
//					}
				}
			}
			textArea.append("Updating Stock levels...Done.\n");
			

			// update stock item timestamps
			textArea.append("Synchronising last modified Timestamps...");
			buttons = palConnector.getPayPalButtons(textArea,stockDatabase);
			for (PayPalButton button : buttons)
			{
				for (StockItem item : allStock)
				{
					if (button.getPayPalId().equals(item.getPayPalId()))
					{
						item.setModifiedDate(button.getModifiedDate());
						break;
					}
				}
			}
			textArea.append("done.\n");
			
			
			// save any updates
			stockDatabase.save();
			textArea.append("COMPLETE.\n");
			
		} catch (Exception e)
		{
			textArea.append("Error! "+e.getMessage());
			e.printStackTrace();
			
		}
		
		
		btnDone.setEnabled(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		
//		btnDone.setEnabled(true);
//		buttons.forEach(System.out::println);
//		
//		for(PayPalButton button:buttons)
//		{
//			System.out.println("Attempting to delete "+button.itemName+"("+button.getPayPalId()+")...");
//			
//			if (palConnector.deleteButton(button))
//			{
//				System.out.println(button.itemName+"("+button.getPayPalId()+") successfully deleted.");
//			}
//			else
//			{
//				System.out.println(button.itemName+"("+button.getPayPalId()+") FAILED to be deleted.");
//			}
//		}
//		
//		PayPalButton button = palConnector.new PayPalButton();
//		button.setItemName("Test123");
//		button.setPrice("74.22");
//		button.setQuantity(100);
//		button.setStockId("ZZ001");
//		palConnector.createButton(button);
//		
//		System.out.println("Attempting to create "+button.itemName+"...");
//		
//		if (palConnector.createButton(button))
//		{
//			System.out.println(button.itemName+"("+button.getPayPalId()+") successfully created.");
//		}
//		else
//		{
//			System.out.println(button.itemName+" FAILED to be created.");
//		}
		
	}
	
	
}
