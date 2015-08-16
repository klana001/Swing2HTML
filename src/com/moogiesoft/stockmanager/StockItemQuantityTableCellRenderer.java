package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class StockItemQuantityTableCellRenderer extends HTMLTableCellRenderer {
//	public StockItemNameTableCellRenderer(DefaultTableModel model) {
//		super(model);
//		// TODO Auto-generated constructor stub
//	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

//		Component result= new JLabel("Add...");
//		if (value!=null && ((StockItem) value).getHighLightedName()!=null)
//		{
//			StockItem stockItem= ((StockItem) value);
//			result = super.getTableCellRendererComponent(table, String.valueOf(stockItem.getHighLightedName()), isSelected,
//					hasFocus, row, column);
//		}
//		return result;
////		return value==null?new JLabel("Add..."):super.getTableCellRendererComponent(table, ((StockItem) value).isNew()?"Add...":String.valueOf(((StockItem) value).getHighLightedName()), isSelected,
////				hasFocus, row, column);
//		
		Component result = new JLabel("Add...");
		if (value!=null)
		{
			StockItem stockItem= ((StockItem) value);
			if (!stockItem.isNew())
			{
			
				result = super.getTableCellRendererComponent(table,""+stockItem.getQuantity(), isSelected,
							hasFocus, row, column);
				result.setEnabled(!stockItem.isDeleted());
			}
		}
		return result;
	}
}
