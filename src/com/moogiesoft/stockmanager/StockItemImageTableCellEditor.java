package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class StockItemImageTableCellEditor extends ImageTableCellEditor
{
	StockItem stockItem;
    public Component getTableCellEditorComponent(final JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
    	
    	stockItem=(StockItem)value;
    	SwingUtilities.invokeLater(new Runnable()
		{
			
			@Override
			public void run()
			{
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(table);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					stockItem.setImage(ImageIO.read(fileChooser.getSelectedFile()));
				}
				
			}
		});
        return new JScrollPane(super.getTableCellEditorComponent(table, stockItem.getSmallImage(), isSelected, rowIndex, vColIndex));
    }
    public Object getCellEditorValue() {
    	stockItem.setLargeImage((BufferedImage) super.getCellEditorValue());
        return stockItem;
    }

}
