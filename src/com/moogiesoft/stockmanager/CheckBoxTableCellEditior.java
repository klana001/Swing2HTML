package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;


public class CheckBoxTableCellEditior extends AbstractCellEditor implements TableCellEditor
{
	StockItem stockItem;
	private JCheckBox checkBox = new JCheckBox();
	
	@Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
    	checkBox .setSelected((Boolean)value);
        return checkBox ;
    }
    
    @Override
    public Object getCellEditorValue() {
        return checkBox.isSelected();
    }

}
