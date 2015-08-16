package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;


public class StockItemQuantityTableCellEditior extends HTMLTableCellEditor
{
	StockItem stockItem;
	JTable table;
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
    	this.table=table;
    	stockItem=(StockItem)value;
    	stockItem.setNew(false);
        return super.getTableCellEditorComponent(table, ""+stockItem.getQuantity(), isSelected, rowIndex, vColIndex);
    }
    public Object getCellEditorValue() {
    	try
    	{
    		int quantity = Integer.parseInt((String) super.getCellEditorValue());
    		
    		if (quantity<0)
    		{
    			JOptionPane.showMessageDialog(table, "Only positve amounts please.","Invalid Input",JOptionPane.ERROR_MESSAGE );
    		}
    		else
    		{
    			stockItem.setQuantity(Integer.parseInt((String) super.getCellEditorValue()));
    		}
    		
    	} catch (Exception e) 
    	{
    		JOptionPane.showMessageDialog(table, "Not a valid integer.","Invalid Input",JOptionPane.ERROR_MESSAGE );
    	}
        return stockItem;
    }

}
