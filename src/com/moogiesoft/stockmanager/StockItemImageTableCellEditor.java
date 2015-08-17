package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;


public class StockItemImageTableCellEditor extends ImageTableCellEditor
{
	StockItem stockItem;
//	private int row;
//	private JTable table;
    public Component getTableCellEditorComponent(final JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
//    	this.table =table;
//    	this.row=rowIndex;
    	stockItem=(StockItem)value;
    	stockItem.setNew(false);
    	
		StockImageDialog dialog = new StockImageDialog(stockItem);
		dialog.setSize(500, 500);
		dialog.setTitle("Images Ordering for Item");
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
    	
//		JFileChooser fileChooser = new JFileChooser();
//		int result = fileChooser.showOpenDialog(table);
//		out:
//		if (result == JFileChooser.APPROVE_OPTION)
//		{
//			try
//			{
//				File inFile = fileChooser.getSelectedFile();
//				File destFile = new File("data/pics/stock/"+inFile.getName());
//				if (destFile.exists())
//				{
//					int reply = JOptionPane.showConfirmDialog(null, "Image already exists in database... do you want to replace it?", "Image Collision", JOptionPane.YES_NO_OPTION);
//				    if (reply !=JOptionPane.YES_OPTION) break out;  
//				}
//				if (!destFile.getParentFile().exists())
//				{
//					if (!destFile.getParentFile().mkdirs())
//					{
//						throw new IOException("Unable to create directory: "+destFile.getParentFile().getAbsolutePath());
//					}
//				}
//				
//				Files.copy(inFile.toPath(),destFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
//				
//				StockImage image = new StockImage();
//				image.imagePath=destFile.getPath();
//				image.thumbnailPath=destFile.getPath()+".thumb.png";
//				stockItem.setImages(Arrays.asList(new StockImage[]{image}) );
//
//			} catch (IOException e)
//			{
//				cancelCellEditing();
//				return new StockItemImageTableCellRenderer().getTableCellRendererComponent(table, value, isSelected, isSelected, rowIndex, vColIndex);
//			}
//		}
//		else
//		{
//			cancelCellEditing();
//			return new StockItemImageTableCellRenderer().getTableCellRendererComponent(table, value, isSelected, isSelected, rowIndex, vColIndex);
//		}
		cancelCellEditing();
    	if (stockItem.getSmallImage()==null)
    	{
    		return new JLabel("Add...");
    	}
    	
    	if (table.getRowHeight(rowIndex)<stockItem.getSmallImage().getHeight())
    	{
    		SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					table.setRowHeight(rowIndex,stockItem.getSmallImage().getHeight());				
				}
			});
    	}
    	
    	return super.getTableCellEditorComponent(table, stockItem.getSmallImage(), isSelected, rowIndex, vColIndex);
    	
    }
    public Object getCellEditorValue() {
//    	if (super.getCellEditorValue()!=null)
//    	{
//    		stockItem.setLargeImage((BufferedImage) super.getCellEditorValue());
//    	}

        return stockItem;
    }

}
