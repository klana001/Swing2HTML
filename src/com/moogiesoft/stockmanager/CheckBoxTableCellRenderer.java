package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CheckBoxTableCellRenderer  extends DefaultTableCellRenderer {
	
	JCheckBox checkBox = new JCheckBox();
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

				checkBox.setSelected((Boolean) value);
//				checkBox.setBackground(bg);

		return checkBox;
		

	}
}
