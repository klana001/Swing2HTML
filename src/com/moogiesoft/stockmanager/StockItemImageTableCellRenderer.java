package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;



public class StockItemImageTableCellRenderer extends ImageTableCellRenderer 
{
//
//	public StockItemImageTableCellRenderer(DefaultTableModel model) {
//		super(model);
//	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
//		
//		Component result = new JLabel("Add...");
//		if (value!=null && ((StockItem) value).getSmallImage() !=null)
//		{
//			StockItem stockItem= ((StockItem) value);
//			result = super.getTableCellRendererComponent(table,stockItem.getSmallImage(), isSelected, hasFocus, row, column);
//		}
//		return result;
//		
		
		Component result = new JLabel("Add...");
		if (value!=null)
		{
			StockItem stockItem= ((StockItem) value);
			
			if (stockItem.getSmallImage() !=null)
			{
				 result = super.getTableCellRendererComponent(table,stockItem.getSmallImage(), isSelected,
						hasFocus, row, column);
			}
			result.setEnabled(!stockItem.isDeleted());
		}
		return result;
	}

}
