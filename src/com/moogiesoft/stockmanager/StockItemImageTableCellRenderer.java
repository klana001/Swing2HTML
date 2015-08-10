package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXImagePanel;


public class StockItemImageTableCellRenderer extends ImageTableCellRenderer 
{

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		return value==null?new JLabel("NULL"):super.getTableCellRendererComponent(table, ((StockItem) value).getSmallImage(), isSelected, hasFocus, row, column);


	}

}
