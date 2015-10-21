package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;


//public class StockItemDeletedTableCellEditior extends AbstractCellEditor implements TableCellEditor
//{
//	StockItem stockItem;
//	private JCheckBox checkBox = new JCheckBox();
//	
//	@Override
//    public Component getTableCellEditorComponent(JTable table, Object value,
//            boolean isSelected, int rowIndex, int vColIndex) {
//    	stockItem=(StockItem)value;
//
//    	checkBox .setSelected(stockItem.isDeleted());
//        return checkBox ;
//    }
//    
//    @Override
//    public Object getCellEditorValue() {
//    	stockItem.setDeleted(checkBox.isSelected());
//        return stockItem;
//    }
//
//}

public class StockItemTypeTableCellEditior extends JComboBoxTableCellEditior
{
	public StockItemTypeTableCellEditior()
	{
		super(Arrays.asList(new String[]{"Jewelery","Ornament"}));
	}
	StockItem stockItem;
	private JTable table;
	
	@Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
    	this.table=table;
    	stockItem=(StockItem)value;
    	stockItem.setNew(false);
        return super.getTableCellEditorComponent(table, ""+stockItem.getType(), isSelected, rowIndex, vColIndex);
    }
    public Object getCellEditorValue() {
    	stockItem.setType((String) super.getCellEditorValue());
        return stockItem;
    }

}
