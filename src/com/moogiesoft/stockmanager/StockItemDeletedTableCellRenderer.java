package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

//public class StockItemDeletedTableCellRenderer  extends DefaultTableCellRenderer {
//	
//	JCheckBox checkBox = new JCheckBox();
//	public Component getTableCellRendererComponent(JTable table, Object value,
//			boolean isSelected, boolean hasFocus, int row, int column) {
//
//		Component result = new JLabel("");
//		if (value!=null)
//		{
//			
//			StockItem stockItem= ((StockItem) value);
//			if (!stockItem.isNew())
//			{
//				result = checkBox;
//				checkBox.setSelected(stockItem.isDeleted());
//
//			}
////			result.setEnabled(!stockItem.isDeleted());
//		}
//
//		return result;
//		
//
//	}
//}


public class StockItemDeletedTableCellRenderer  extends CheckBoxTableCellRenderer {
	

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component result = new JLabel("");
		if (value!=null)
		{
			
			StockItem stockItem= ((StockItem) value);
			if (!stockItem.isNew())
			{
				result = super.getTableCellRendererComponent(table, stockItem.isDeleted(), isSelected, hasFocus, row, column);
			}
//			result.setEnabled(!stockItem.isDeleted());
		}

		return result;
		

	}
}