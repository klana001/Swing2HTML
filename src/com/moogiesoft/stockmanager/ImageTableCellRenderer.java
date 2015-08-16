package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;



public class ImageTableCellRenderer implements TableCellRenderer 
{
	
//	private DefaultTableModel model;
//
//	public ImageTableCellRenderer(DefaultTableModel model) {
//		this.model = model;
//	}
	
	JLabel imagePanel = new JLabel();
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

//		StockItem item = (StockItem) model.getValueAt(row, 0);
		
		if (value!=null)
		{
			Image image = (Image) value;
			imagePanel.setIcon(new ImageIcon(image));
						
			if (table.getRowHeight(row) < image.getHeight(null))
			{
				table.setRowHeight(row, image.getHeight(null));
			}
		}
		else
		{
			imagePanel.setIcon(null);
		}
		
			if (isSelected)
			{
				imagePanel.setBackground(table.getSelectionBackground());
			}
			else
			{
				imagePanel.setBackground(table.getBackground());
			}
//			imagePanel.setEnabled(!item.isDeleted());
		return imagePanel;

	}


}
