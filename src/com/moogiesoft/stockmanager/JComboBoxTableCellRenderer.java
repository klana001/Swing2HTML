package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class JComboBoxTableCellRenderer extends DefaultTableCellRenderer {
	
//	private DefaultTableModel model;
//	public HTMLTableCellRenderer(DefaultTableModel model) {
//		this.model=model;
//	}
//	private JLabel jLabel = new JLabel();  
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		String txt = String.valueOf(value);


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
		Component component = super.getTableCellRendererComponent(table, txt, isSelected,
				hasFocus, row, column);
		
//		StockItem item = (StockItem) model.getValueAt(row, 0);
//		component.setEnabled(!item.isDeleted());
		return component;
//		return jLabel;

	}
}
