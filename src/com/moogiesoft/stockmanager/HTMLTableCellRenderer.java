package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class HTMLTableCellRenderer extends DefaultTableCellRenderer {
	
//	private JLabel jLabel = new JLabel();  
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		String txt = String.valueOf(value);
		txt = String.format("<html>%s</html>", txt);

//		jLabel.setText(txt);
//		if (isSelected)
//		{
//			jLabel.setBackground(table.getSelectionBackground());
//			jLabel.setForeground(table.getSelectionForeground());
//		}
//		else
//		{
//			jLabel.setBackground(table.getBackground());
//			jLabel.setForeground(table.getForeground());
//		}
		// Delegate to superclass which will set the label text, background,
		// etc.
		return super.getTableCellRendererComponent(table, txt, isSelected,
				hasFocus, row, column);
//		return jLabel;

	}
}
