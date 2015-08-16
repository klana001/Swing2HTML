package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;


public class StockItemPriceTableCellEditior extends HTMLTableCellEditor
{
	StockItem stockItem;
	JTable table;
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
    	this.table=table;
    	stockItem=(StockItem)value;
    	stockItem.setNew(false);
        return super.getTableCellEditorComponent(table, ""+stockItem.getPrice(), isSelected, rowIndex, vColIndex);
    }
    public Object getCellEditorValue() {
    	try
    	{
    		double price = Double.parseDouble((String) super.getCellEditorValue());
    		
    		if (price<0)
    		{
    			JOptionPane.showMessageDialog(table, "Only positve amounts please.","Invalid Input",JOptionPane.ERROR_MESSAGE );
    		}
    		else
    		{
    			stockItem.setPrice(price);
    		}
    		
    	} catch (Exception e) 
    	{
    		JOptionPane.showMessageDialog(table, "Not a valid number.","Invalid Input",JOptionPane.ERROR_MESSAGE );
    	}
        return stockItem;
    }

}
