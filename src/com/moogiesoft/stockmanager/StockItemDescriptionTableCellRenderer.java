package com.moogiesoft.stockmanager;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class StockItemDescriptionTableCellRenderer extends
		HTMLTableCellRenderer {
//	public StockItemDescriptionTableCellRenderer(DefaultTableModel model) {
//		super(model);
//		// TODO Auto-generated constructor stub
//	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
//		System.out.println(table.getRowHeight(row)+" "+value);

		Component result = new JLabel("Add...");
		if (value!=null)
		{
			StockItem stockItem= ((StockItem) value);
			
			if (stockItem.getHighLightedDescription() !=null)
			{
				 result = super.getTableCellRendererComponent(table,stockItem.getHighLightedDescription(), isSelected,
						hasFocus, row, column);
//				 final int preferredArea = result.getPreferredSize().height*result.getPreferredSize().width+1*result.getPreferredSize().width;
//				   	SwingUtilities.invokeLater(new Runnable() {
//						
//						@Override
//						public void run() {
//							if (Math.abs(preferredArea-table.getRowHeight(row)*table.getColumnModel().getColumn(column).getWidth())>1*table.getColumnModel().getColumn(column).getWidth())
//							{
//								table.setRowHeight(row, 1+preferredArea/table.getColumnModel().getColumn(column).getWidth());
//								System.out.println("preferredArea: "+preferredArea+" actual:" +table.getRowHeight(row)*table.getColumnModel().getColumn(column).getWidth());
//							}
//						}
//					});
			}
			result.setEnabled(!stockItem.isDeleted());
			JScrollPane scrollPane = new JScrollPane(result,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBorder(null);
			scrollPane.setAutoscrolls(false);
			scrollPane.getVerticalScrollBar().setValue(0);
			result = scrollPane;

		}

		return result;
		
//		return value==null?new JLabel("Add..."):super.getTableCellRendererComponent(table,((StockItem) value).isNew()?"Add...":
//				String.valueOf(((StockItem) value).getHighLightedDescription()),
//				isSelected, hasFocus, row, column);
	}
}
