package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class StockItemDescriptionTableCellEditior extends HTMLTableCellEditor
{
	StockItem stockItem;
	private int rowHeight;
	private JTable table;
	private int row;
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
    	stockItem=(StockItem)value;
    	stockItem.setNew(false);
    	rowHeight = table.getRowHeight(rowIndex);
    	this.table=table;
    	row = rowIndex;
    	SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
		    	table.setRowHeight(rowIndex, 100);
			}
		});

        return new JScrollPane(super.getTableCellEditorComponent(table, stockItem.getDescription(), isSelected, rowIndex, vColIndex));
    }
    public Object getCellEditorValue() {
    	table.setRowHeight(row, rowHeight);
    	stockItem.setDescription((String ) super.getCellEditorValue());
    	stockItem.removeDescriptionHighLighting();
        return stockItem;
    }

}
