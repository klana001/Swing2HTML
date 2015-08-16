package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class StockItemCreatedDateTableCellRenderer extends HTMLTableCellRenderer {
//	public StockItemCreatedDateTableCellRenderer(DefaultTableModel model) {
//		super(model);
//		// TODO Auto-generated constructor stub
//	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component result = new JLabel("Add...");
		if (value!=null)
		{
			StockItem stockItem= ((StockItem) value);
			
			if (stockItem.getCreationDate() !=null)
			{
				 result = super.getTableCellRendererComponent(table,stockItem.getCreationDate(), isSelected,
						hasFocus, row, column);
			}
			result.setEnabled(!stockItem.isDeleted());
		}
		return result;
		
//		return value==null?new JLabel("Add..."):super.getTableCellRendererComponent(table,((StockItem) value).isNew()?"Add...": ((StockItem) value).getCreationDate(), isSelected,
//				hasFocus, row, column);
	}
}
