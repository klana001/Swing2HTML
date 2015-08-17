package com.moogiesoft.stockmanager;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.moogiesoft.stockmanager.StockItem.StockItemListener;

import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;

//import com.alee.laf.WebLookAndFeel;

import de.javasoft.swing.JYTable;
import de.javasoft.swing.JYTableScrollPane;

public class StockListPanel extends JPanel implements StockItemListener
{
	private StockDatabase stockDatabase = new StockDatabase();
	
	public StockDatabase getStockDatabase()
	{
		return stockDatabase;
	}

	private static final int IMAGE_COLUMN_INDEX = 0;
	private static final int NAME_COLUMN_INDEX = 1 ;
	private static final int DESCRIPTION_COLUMN_INDEX = 2;
	private static final int CREATED_DATE_COLUMN_INDEX = 3;
	private static final int PRICE_COLUMN_INDEX = 4;
	private static final int QUANTITY_COLUMN_INDEX = 5;
	private static final int DELETED_COLUMN_INDEX=6;
//	private static final int SOLD_DATE_COLUMN_INDEX = 4;
//	private static final int COST_COLUMN_INDEX= 5;
//	private static final int SALE_PRICE_COLUMN_INDEX= 6;
//	private static final int PROFIT_COLUMN_INDEX= 7;
	
	private DefaultTableModel tableModel;

	
	
	private static final int COLUMN_COUNT = DELETED_COLUMN_INDEX +1;
	private JYTable stockTable;
	private boolean showDeleted;
	
//	final class DeletedRowFilter extends  RowFilter
//	{
//		RowFilter parent;
//		public DeletedRowFilter(RowFilter parent)
//		{
//			this.parent=parent;
//		}
//		@SuppressWarnings("unchecked")
//		@Override
//		public boolean include(javax.swing.RowFilter.Entry entry)
//		{
////			
////			if (entry == null || entry.getValue(0)== null)
////			{
////				return true;
////			}
//			return false;//(!((StockItem) entry.getValue(0)).isDeleted()) && (parent==null || parent.include(entry));
//		}
//	}
	
	StockListPanel()
	{
		tableModel = new DefaultTableModel(0, COLUMN_COUNT) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		    	
		       StockItem stockItem = (StockItem)getValueAt(row, 0);
		       if (column == CREATED_DATE_COLUMN_INDEX) return false;
		       else if (column == DELETED_COLUMN_INDEX) return !stockItem.isNew();
		       else if (stockItem.isDeleted()) return false;
		       return true;
		    }
		};
		stockTable=new JYTable();
		stockTable.setModel(tableModel);
		stockTable.putClientProperty("terminateEditOnFocusLost", true);

		setLayout(new BorderLayout());
		
		
		add(new JYTableScrollPane(stockTable),BorderLayout.CENTER);
		reload();
	}
	
	private void addNewStockItemRow()
	{
		StockItem newStockItem = new StockItem(true);
		addStockItem(newStockItem);
	}
	
	private void addStockItem(StockItem stockItem)
	{
		stockItem.addStockItemListener(this);
		
		try
		{
			stockDatabase.add(stockItem);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		Object[] row = new Object[COLUMN_COUNT];
		TableColumn imageColumn = stockTable.getColumn(IMAGE_COLUMN_INDEX);
		
		for (int i=0;i<COLUMN_COUNT;i++)
		{
			row[i] = stockItem;
		}
		
		if (stockItem.getSmallImage()!=null)
		{
//		if (stockItem.getSmallImage().getWidth()> imageColumn.getWidth());
//		{
			imageColumn.setWidth(stockItem.getSmallImage().getWidth());
			imageColumn.setPreferredWidth(stockItem.getSmallImage().getWidth());
			
//			
//		}
		}
		
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

	@Override
	public void stockAdded(StockItem stockItem) {
		addNewStockItemRow();
	}

	@Override
	public void updated(StockItem stockItem) {
		try
		{
//			URL url = this.getClass().getResource("data/data.dat");
//			if (url == null)
//			{
//				url = new File("data/data.dat").toURL();
//			}
//			OutputStream os = new FileOutputStream(new File(url.toURI()));
//			BufferedOutputStream bos = new BufferedOutputStream(os);
//			ObjectOutputStream oos = new ObjectOutputStream(bos);
//			Vector<String> columnNames = new Vector<>();
//			for (int i=0;i<tableModel.getColumnCount();i++)
//			{
//				columnNames.add(tableModel.getColumnName(i));
//			}
//			
//			oos.writeObject(columnNames);
//			oos.writeObject(tableModel.getDataVector());
//			oos.close();
			stockDatabase.save();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void reload()
	{
		try
		{
			stockDatabase.reload();

			Vector<String> columnIds = new Vector<String>();
			for (int i=0;i<COLUMN_COUNT;i++)
			{
				columnIds.add(""+i);
			}
			
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
			
			stockDatabase.getAllStock().forEach(s->s.addStockItemListener(this));
			if (stockDatabase.getAllStock().stream().filter(s->s.isNew()).count()==0)
			{
				addNewStockItemRow();
			}
			List<StockItem> stock = showDeleted?stockDatabase.getAllStock():stockDatabase.getNonDeletedStock();
			
			for (StockItem stockItem : stock)
			{
				Vector<Object> row = new Vector<Object>();
				rows.add(row);
				
				for (int i=0;i<COLUMN_COUNT;i++)
				{
					row.add(stockItem);
				}
			}
			tableModel.setDataVector(rows,columnIds);
		} catch (Exception e)
		{
			e.printStackTrace();
			addNewStockItemRow();
		}
		
		stockTable.getColumn(IMAGE_COLUMN_INDEX).setCellRenderer(new StockItemImageTableCellRenderer());
		stockTable.getColumn(NAME_COLUMN_INDEX).setCellRenderer(new StockItemNameTableCellRenderer());
		stockTable.getColumn(DESCRIPTION_COLUMN_INDEX).setCellRenderer(new StockItemDescriptionTableCellRenderer());
		stockTable.getColumn(CREATED_DATE_COLUMN_INDEX).setCellRenderer(new StockItemCreatedDateTableCellRenderer());
		stockTable.getColumn(DELETED_COLUMN_INDEX).setCellRenderer(new StockItemDeletedTableCellRenderer());
		stockTable.getColumn(QUANTITY_COLUMN_INDEX).setCellRenderer(new StockItemQuantityTableCellRenderer());
		stockTable.getColumn(PRICE_COLUMN_INDEX).setCellRenderer(new StockItemPriceTableCellRenderer());
		
		stockTable.getColumn(IMAGE_COLUMN_INDEX).setCellEditor(new StockItemImageTableCellEditor());
		stockTable.getColumn(NAME_COLUMN_INDEX).setCellEditor(new StockItemNameTableCellEditior());
		stockTable.getColumn(DESCRIPTION_COLUMN_INDEX).setCellEditor(new StockItemDescriptionTableCellEditior());
		stockTable.getColumn(DELETED_COLUMN_INDEX).setCellEditor(new StockItemDeletedTableCellEditior());
		stockTable.getColumn(QUANTITY_COLUMN_INDEX).setCellEditor(new StockItemQuantityTableCellEditior());
		stockTable.getColumn(PRICE_COLUMN_INDEX).setCellEditor(new StockItemPriceTableCellEditior());
		
		stockTable.getColumn(IMAGE_COLUMN_INDEX).setHeaderValue("Image");
		stockTable.getColumn(NAME_COLUMN_INDEX).setHeaderValue("Name");
		stockTable.getColumn(DESCRIPTION_COLUMN_INDEX).setHeaderValue("Description");
		stockTable.getColumn(CREATED_DATE_COLUMN_INDEX).setHeaderValue("Created on");
		stockTable.getColumn(DELETED_COLUMN_INDEX).setHeaderValue("Del");
		stockTable.getColumn(QUANTITY_COLUMN_INDEX).setHeaderValue("Qty");
		stockTable.getColumn(PRICE_COLUMN_INDEX).setHeaderValue("Price");
		
		stockTable.getColumn(IMAGE_COLUMN_INDEX).setMaxWidth(StockImage.SMALL_IMAGE_SIZE+4);
		stockTable.getColumn(NAME_COLUMN_INDEX).setPreferredWidth(60);
		stockTable.getColumn(DELETED_COLUMN_INDEX).setMaxWidth(40);
		stockTable.getColumn(QUANTITY_COLUMN_INDEX).setMaxWidth(40);
		stockTable.getColumn(PRICE_COLUMN_INDEX).setMaxWidth(50);
		
		invalidate();
		repaint();
	}

	public boolean isShowDeleted()
	{
		return showDeleted;
	}

	public void setShowDeleted(boolean showDeleted)
	{
		this.showDeleted = showDeleted;
		reload();
	}

}
