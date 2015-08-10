package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;


public class HTMLTableCellEditor extends AbstractCellEditor implements
        TableCellEditor {
	JTextArea component = new JTextArea();
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
//        if (isSelected) {
//            ((JTextField)component).selectAll();
//        }
        component.setText((String)value);
        
        if (table.getRowHeight(rowIndex)<component.getPreferredSize().height);
        {
        	table.setRowHeight(rowIndex,component.getPreferredSize().height);
        }
        return component;
    }
    public Object getCellEditorValue() {
        return component.getText();
    }

}
