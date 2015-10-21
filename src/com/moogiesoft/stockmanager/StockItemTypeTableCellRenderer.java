package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class StockItemTypeTableCellRenderer extends
JComboBoxTableCellRenderer {
//	public StockItemDescriptionTableCellRenderer(DefaultTableModel model) {
//		super(model);
//		// TODO Auto-generated constructor stub
//	}
	
	

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
//		System.out.println(table.getRowHeight(row)+" "+value);

		Component result = new JLabel("Add...");
		if (value!=null)
		{
			StockItem stockItem= ((StockItem) value);
			if (stockItem.getType() !=null)
			{
				result = super.getTableCellRendererComponent(table, stockItem.getType(), isSelected, hasFocus, row, column);
			}
			result.setEnabled(!stockItem.isDeleted());
		}

		
		
		return result;
		
//		return value==null?new JLabel("Add..."):super.getTableCellRendererComponent(table,((StockItem) value).isNew()?"Add...":
//				String.valueOf(((StockItem) value).getHighLightedDescription()),
//				isSelected, hasFocus, row, column);
	}
}
