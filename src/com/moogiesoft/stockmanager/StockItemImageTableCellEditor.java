package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class StockItemImageTableCellEditor extends ImageTableCellEditor
{
	StockItem stockItem;
    public Component getTableCellEditorComponent(final JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
    	
    	stockItem=(StockItem)value;
    	stockItem.setNew(false);
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(table);
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
				
				stockItem.setLargeImage(ImageIO.read(inFile));
				Files.copy(inFile.toPath(),destFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
				stockItem.setImagePath(destFile.getPath());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			cancelCellEditing();
			return new StockItemImageTableCellRenderer().getTableCellRendererComponent(table, value, isSelected, isSelected, rowIndex, vColIndex);
		}
					
        return new JScrollPane(super.getTableCellEditorComponent(table, stockItem.getSmallImage(), isSelected, rowIndex, vColIndex));
    }
    public Object getCellEditorValue() {
    	if (super.getCellEditorValue()!=null)
    	{
    		stockItem.setLargeImage((BufferedImage) super.getCellEditorValue());
    	}
        return stockItem;
    }

}
