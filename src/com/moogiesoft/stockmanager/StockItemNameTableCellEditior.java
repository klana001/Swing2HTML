package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;


public class StockItemNameTableCellEditior extends HTMLTableCellEditor
{
	StockItem stockItem;
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
    	
    	stockItem=(StockItem)value;
    	stockItem.setNew(false);
        return super.getTableCellEditorComponent(table, stockItem.getName(), isSelected, rowIndex, vColIndex);
    }
    public Object getCellEditorValue() {
    	stockItem.setName((String ) super.getCellEditorValue());
    	stockItem.removeNameHighLighting();
        return stockItem;
    }

}
