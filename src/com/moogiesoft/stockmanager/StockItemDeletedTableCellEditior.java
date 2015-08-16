package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
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

public class StockItemDeletedTableCellEditior extends CheckBoxTableCellEditior
{
	StockItem stockItem;
	
	@Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
    	stockItem=(StockItem)value;

        return super.getTableCellEditorComponent( table, stockItem.isDeleted(),
                 isSelected,  rowIndex,  vColIndex) ;
    }
    
    @Override
    public Object getCellEditorValue() {
    	stockItem.setDeleted((Boolean) super.getCellEditorValue());
        return stockItem;
    }

}
