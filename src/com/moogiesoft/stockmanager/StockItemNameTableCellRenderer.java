package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;


public class StockItemNameTableCellRenderer extends HTMLTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		return value==null?new JLabel("NULL"):super.getTableCellRendererComponent(table, String.valueOf(((StockItem) value).getHighLightedName()), isSelected,
				hasFocus, row, column);
	}
}
