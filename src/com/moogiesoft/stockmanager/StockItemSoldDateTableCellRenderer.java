package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;


public class StockItemSoldDateTableCellRenderer extends HTMLTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		return value==null?new JLabel("NULL"):super.getTableCellRendererComponent(table, ((StockItem) value).getDateOfPurchase(), isSelected,
				hasFocus, row, column);
	}
}
