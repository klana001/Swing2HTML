package com.moogiesoft.stockmanager;
import java.awt.Component;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;


public class JComboBoxTableCellEditior extends AbstractCellEditor implements TableCellEditor
{
	StockItem stockItem;
	private JComboBox comboBox = new JComboBox();
	
	public JComboBoxTableCellEditior(List<String> values)
	{
		super();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		values.forEach(value->model.addElement(value));
		comboBox.setModel(model);
	}

	@Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBox.getModel();
		
		if (model.getSelectedItem().equals(value))
		{
			comboBox.setSelectedItem(value);
		}
        return comboBox ;
    }
    
    @Override
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }

}
