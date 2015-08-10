package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXImagePanel;


public class ImageTableCellRenderer implements TableCellRenderer 
{
	
	JXImagePanel imagePanel = new JXImagePanel();
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value!=null)
		{
			Image image = (Image) value;
			imagePanel.setImage(image);
						
			if (table.getRowHeight(row) < image.getHeight(null))
			{
				table.setRowHeight(row, image.getHeight(null));
			}
			
		
			if (isSelected)
			{
				imagePanel.setBackground(table.getSelectionBackground());
			}
			else
			{
				imagePanel.setBackground(table.getBackground());
			}
		}
		return imagePanel;

	}

}
