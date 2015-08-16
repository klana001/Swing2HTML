package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.awt.Image;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;


public class ImageTableCellEditor extends AbstractCellEditor implements
        TableCellEditor {
	
private Image image;
//	JXImagePanel imagePanel = new JXImagePanel();
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
        
		image = (Image) value;
		JLabel label = new JLabel();
		if (image!=null)
		{
			label.setIcon(new ImageIcon(image));
		}

        
//        if (table.getRowHeight(rowIndex)<component.getPreferredSize().height);
//        {
//        	table.setRowHeight(rowIndex,component.getPreferredSize().height);
//        }
        return label;
    }
    public Object getCellEditorValue() {
        return image;
    }

}
