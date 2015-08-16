package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;


public class StockItemDescriptionTableCellEditior extends HTMLTableCellEditor
{
	StockItem stockItem;
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
    	stockItem=(StockItem)value;
    	stockItem.setNew(false);
        return new JScrollPane(super.getTableCellEditorComponent(table, stockItem.getDescription(), isSelected, rowIndex, vColIndex));
    }
    public Object getCellEditorValue() {
    	stockItem.setDescription((String ) super.getCellEditorValue());
    	stockItem.removeDescriptionHighLighting();
        return stockItem;
    }

}
