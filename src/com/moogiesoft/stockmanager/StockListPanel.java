package com.moogiesoft.stockmanager;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import de.javasoft.swing.JYTable;
import de.javasoft.swing.JYTableScrollPane;


public class StockListPanel extends JPanel
{
	private static final int IMAGE_COLUMN_INDEX = 0;
	private static final int NAME_COLUMN_INDEX = 1 ;
	private static final int DESCRIPTION_COLUMN_INDEX = 2;
	private static final int CREATED_DATE_COLUMN_INDEX = 3;
	private static final int SOLD_DATE_COLUMN_INDEX = 4;
	private static final int COST_COLUMN_INDEX= 5;
	private static final int SALE_PRICE_COLUMN_INDEX= 6;
	private static final int PROFIT_COLUMN_INDEX= 7;

	
	
	private static final int COLUMN_COUNT = PROFIT_COLUMN_INDEX +1;
	private JYTable stockTable;
	
	StockListPanel()
	{
		stockTable=new JYTable(0, COLUMN_COUNT);
		
		stockTable.getColumn(IMAGE_COLUMN_INDEX).setCellRenderer(new StockItemImageTableCellRenderer());
		stockTable.getColumn(NAME_COLUMN_INDEX).setCellRenderer(new StockItemNameTableCellRenderer());
		stockTable.getColumn(DESCRIPTION_COLUMN_INDEX).setCellRenderer(new StockItemDescriptionTableCellRenderer());
		stockTable.getColumn(CREATED_DATE_COLUMN_INDEX).setCellRenderer(new StockItemCreatedDateTableCellRenderer());
		stockTable.getColumn(SOLD_DATE_COLUMN_INDEX).setCellRenderer(new StockItemSoldDateTableCellRenderer());
//		stockTable.getColumn(COST_COLUMN_INDEX).setCellRenderer(new StockItemCostPriceTableCellRenderer());
//		stockTable.getColumn(SALE_PRICE_COLUMN_INDEX).setCellRenderer(new StockItemSalePriceTableCellRenderer());
//		stockTable.getColumn(PROFIT_COLUMN_INDEX).setCellRenderer(new StockItemProfitTableCellRenderer());
		
		stockTable.getColumn(IMAGE_COLUMN_INDEX).setCellEditor(new StockItemImageTableCellEditor());
		stockTable.getColumn(NAME_COLUMN_INDEX).setCellEditor(new StockItemNameTableCellEditior());
		stockTable.getColumn(DESCRIPTION_COLUMN_INDEX).setCellEditor(new StockItemDescriptionTableCellEditior());
//		stockTable.getColumn(CREATED_DATE_COLUMN_INDEX).setCellEditor(new StockItemCreatedDateTableCellEditior());
//		stockTable.getColumn(SOLD_DATE_COLUMN_INDEX).setCellEditor(new StockItemSoldDateTableCellEditior());
//		stockTable.getColumn(COST_COLUMN_INDEX).setCellEditor(new StockItemCostPriceTableCellEditior());
//		stockTable.getColumn(SALE_PRICE_COLUMN_INDEX).setCellEditor(new StockItemSalePriceTableCellEditior());
		stockTable.getColumn(PROFIT_COLUMN_INDEX).setCellRenderer(null);
		
		stockTable.getColumn(IMAGE_COLUMN_INDEX).setHeaderValue("Image");
		stockTable.getColumn(NAME_COLUMN_INDEX).setHeaderValue("Name");
		stockTable.getColumn(DESCRIPTION_COLUMN_INDEX).setHeaderValue("Description");
		stockTable.getColumn(CREATED_DATE_COLUMN_INDEX).setHeaderValue("Created on");
		stockTable.getColumn(SOLD_DATE_COLUMN_INDEX).setHeaderValue("Sold on");
		stockTable.getColumn(COST_COLUMN_INDEX).setHeaderValue("Cost Price");
		stockTable.getColumn(SALE_PRICE_COLUMN_INDEX).setHeaderValue("Sale Price");
		stockTable.getColumn(PROFIT_COLUMN_INDEX).setHeaderValue("Profit");

		
		StockItem stockItem= new StockItem();
		
		stockItem.setDescription("Welcome to Michelle Klaebe range of quality doll clothes. Here you can buy the most gorgeous dolls clothes, dolls shoes and dolls accessories. Our family business has been sewing gorgeous doll clothes since 2011, our dolls clothes fit the baby born to the cabbage patch dolls.\n"+
"Just measure your doll as per our measurement guide (coming soon) to see if our doll clothes will fit your dolls. If you are unsure, Just send us an email at michelle_klaebe@yahoo.com.au with your doll measurements and we will help you determine which doll clothes will fit your doll.\n"+
"\n"+
"We add new styles each month, so check back often.\n"+
"\n"+
"No minimum orders. Fast shipping. Finished inside seams.\n"+
"\n"+
"Got questions? email us at michelle_klaebe@yahoo.com.au\n");
		stockItem.setName("roll");
		
		stockItem.setLargeImage(new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB));
		
		stockItem.isDescriptionContainsSimilarSentence("clothes of roll seems");
			
		stockItem.isNameContainsSimilarSentence("roll robot");

		
		
		addStockItem(stockItem);
		setLayout(new BorderLayout());
		add(new JYTableScrollPane(stockTable),BorderLayout.CENTER);
	}
	
	private void addStockItem(StockItem stockItem)
	{
		Object[] row = new Object[COLUMN_COUNT];
		TableColumn imageColumn = stockTable.getColumn(IMAGE_COLUMN_INDEX);
		row[0] = stockItem;
		row[1] = stockItem;
		row[2] = stockItem;
		
//		if (stockItem.getSmallImage().getWidth()> imageColumn.getWidth());
//		{
			imageColumn.setWidth(stockItem.getSmallImage().getWidth());
			imageColumn.setPreferredWidth(stockItem.getSmallImage().getWidth());
			
//			
//		}
		
		((DefaultTableModel)stockTable.getModel()).addRow(row);
		
		TableCellListener tcl = new TableCellListener(stockTable, new AbstractAction()
		{
			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		        TableCellListener tcl = (TableCellListener)e.getSource();
//		        ((StockItem) tcl.getNewValue()).apply
		    }
		});
		
		
	}
	
	public static void main(String args[])
	{
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new StockListPanel(),BorderLayout.CENTER);
		frame.setVisible(true);
	}

}
