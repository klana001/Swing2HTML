package com.moogiesoft.stockmanager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class StockImageDialog extends JDialog 
{
	private JPanel entriesPanel;
	private StockItem item;

	public StockImageDialog(StockItem item)
	{
		this.item = item;
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		entriesPanel = new JPanel();
		entriesPanel.setLayout(new BoxLayout(entriesPanel, BoxLayout.Y_AXIS));
		
		mainPanel.add(new JScrollPane(entriesPanel),BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				add();
			}
		});
		
		
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		bottomPanel.add(addButton,BorderLayout.WEST);
		bottomPanel.add(doneButton,BorderLayout.EAST);
		mainPanel.add(bottomPanel,BorderLayout.SOUTH);
				
		populateEntries();
		
		add(mainPanel,BorderLayout.CENTER);
	}
	
	private void populateEntries()
	{
		entriesPanel.removeAll();
		int i=0;
		for (StockImage stockImage : item.getImages())
		{
			if (stockImage.getImagePath()==null || stockImage.getLargeImage()==null || stockImage.getThumbnailPath() == null || stockImage.getThumbnailImage()==null)
			{
				stockImage.construct();
			}
			
			final StockImage finalStockImage = stockImage;
			
			JPanel panel = new JPanel(new BorderLayout());

			JPanel imagePanel = new JPanel();
			imagePanel.add(new JLabel(new ImageIcon(stockImage.getThumbnailImage())));
			imagePanel.add(new JLabel(stockImage.getImagePath()));
//			JPanel imagePaneContainer = new JPanel(new BorderLayout());
//			
//			imagePaneContainer.add(new JLabel(""+i++),BorderLayout.WEST);
//			imagePaneContainer.add(imagePanel,BorderLayout.CENTER);
			
			JPanel imagePaneContainer = new JPanel();
			
			imagePaneContainer.add(new JLabel(""+i++));
			imagePaneContainer.add(imagePanel);
			panel.add(imagePaneContainer,BorderLayout.WEST);

			JPanel buttonPanel = new JPanel();
			JButton upButton = new JButton("Up");
			upButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					moveUp(finalStockImage);
				}
			});
			
			JButton downButton = new JButton("Down");
			downButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					moveDown(finalStockImage);
				}
			});
			
			JButton deleteButton = new JButton("Delete");
			deleteButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					delete(finalStockImage);
				}
			});
			
			buttonPanel.add(upButton);
			buttonPanel.add(downButton);
			buttonPanel.add(deleteButton);
			
			panel.add(buttonPanel,BorderLayout.EAST);
			
			panel.setBorder(new LineBorder(getForeground(), 1));
			
			entriesPanel.add(panel);
			
		}
		revalidate();
		repaint();
	}
	
	private void moveUp(StockImage image)
	{
		ArrayList<StockImage> adjustedItems = new ArrayList<>();
		for (int i=0;i<item.getImages().size();i++)
		{
			if (item.getImages().get(i)== image)
			{
				if (i>0)
				{
					item.getImages().remove(i);

					for (int j=0;j<item.getImages().size();j++)
					{
						if (j==i-1)
						{
							adjustedItems.add(image);
						}
						adjustedItems.add(item.getImages().get(j));
					}
					item.setImages(adjustedItems);
					populateEntries();
					return;
				}
			}
		}
	}
	
	private void moveDown(StockImage image)
	{
		ArrayList<StockImage> adjustedItems = new ArrayList<>();
		for (int i=0;i<item.getImages().size();i++)
		{
			if (item.getImages().get(i)== image)
			{
				if (i<item.getImages().size()-1)
				{
					item.getImages().remove(i);

					for (int j=0;j<item.getImages().size();j++)
					{
						adjustedItems.add(item.getImages().get(j));
						if (j==i)
						{
							adjustedItems.add(image);
						}
					}
					

					item.setImages(adjustedItems);
					populateEntries();
					return;
				}
			}
		}
	}
	
	private void delete(StockImage image)
	{
		item.getImages().remove(image);
		item.setImages(item.getImages());
		populateEntries();
	}
	
	private void add()
	{
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(this);
		out:
		if (result == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File inFile = fileChooser.getSelectedFile();
				File destFile = new File("data/pics/stock/"+inFile.getName());
				if (destFile.exists())
				{
					int reply = JOptionPane.showConfirmDialog(null, "Image already exists in database... do you want to replace it?", "Image Collision", JOptionPane.YES_NO_OPTION);
				    if (reply !=JOptionPane.YES_OPTION) break out;  
				}
				if (!destFile.getParentFile().exists())
				{
					if (!destFile.getParentFile().mkdirs())
					{
						throw new IOException("Unable to create directory: "+destFile.getParentFile().getAbsolutePath());
					}
				}
				
				Files.copy(inFile.toPath(),destFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
				
				StockImage image = new StockImage();
				image.setImagePath(destFile.getPath());
				image.setThumbnailPath(destFile.getPath()+".thumb.png");
				item.getImages().add(image);
				populateEntries();
//				stockItem.setImages(Arrays.asList(new StockImage[]{image}) );

			} catch (IOException e)
			{
				e.printStackTrace();
//				cancelCellEditing();
//				return new StockItemImageTableCellRenderer().getTableCellRendererComponent(table, value, isSelected, isSelected, rowIndex, vColIndex);
			}
		}
//		else
//		{
//			cancelCellEditing();
//			return new StockItemImageTableCellRenderer().getTableCellRendererComponent(table, value, isSelected, isSelected, rowIndex, vColIndex);
//		}
	}
}
