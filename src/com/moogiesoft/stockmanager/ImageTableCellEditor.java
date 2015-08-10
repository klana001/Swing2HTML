package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.awt.Image;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import org.jdesktop.swingx.JXImagePanel;


public class ImageTableCellEditor extends AbstractCellEditor implements
        TableCellEditor {
	
	JXImagePanel imagePanel = new JXImagePanel();
//	
//	@Override
//	public Component getTableCellRendererComponent(JTable table, Object value,
//			boolean isSelected, boolean hasFocus, int row, int column) {
//		if (value!=null)
//		{
//			Image image = (Image) value;
//			imagePanel.setImage(image);
//						
//			if (table.getRowHeight(row) < image.getHeight(null))
//			{
//				table.setRowHeight(row, image.getHeight(null));
//			}
//			
//		
//			if (isSelected)
//			{
//				imagePanel.setBackground(table.getSelectionBackground());
//			}
//			else
//			{
//				imagePanel.setBackground(table.getBackground());
//			}
//		}
//		return imagePanel;

	
//	if (value!=null)
//	{
//		Image image = (Image) value;
//		imagePanel.setImage(image);
//					
//		if (table.getRowHeight(row) < image.getHeight(null))
//		{
//			table.setRowHeight(row, image.getHeight(null));
//		}
//		
//	
//		if (isSelected)
//		{
//			imagePanel.setBackground(table.getSelectionBackground());
//		}
//		else
//		{
//			imagePanel.setBackground(table.getBackground());
//		}
//	}
//	return imagePanel;
	

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
//        if (isSelected) {
//            ((JTextField)component).selectAll();
//        }
//        component.setText((String)value);
        
		Image image = (Image) value;
		imagePanel.setImage(image);
        
//        if (table.getRowHeight(rowIndex)<component.getPreferredSize().height);
//        {
//        	table.setRowHeight(rowIndex,component.getPreferredSize().height);
//        }
        return imagePanel;
    }
    public Object getCellEditorValue() {
        return imagePanel.getImage();
    }

}
