package com.moogiesoft.stockmanager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;


public class StockDatabase
{
	private List<StockItem> stock = new ArrayList<StockItem>();
	Map<String,StockItem> map = new HashMap<String, StockItem>();
	public void reload() throws Exception
	{
		InputStream is = this.getClass().getResourceAsStream("/data/data.dat");
		BufferedInputStream bis = new BufferedInputStream(is);
		ObjectInputStream ois = new ObjectInputStream(bis);
		stock = (List<StockItem>) ois.readObject();
		map = new HashMap<String, StockItem>();
		for (StockItem item : stock)
		{
			if (item.getPayPalId()!=null)
			{
				map.put(item.getPayPalId(), item);
			}
		}
		ois.close();
	}
	
	public void add(StockItem stockItem) throws Exception
	{
		stock.add(stockItem);
		map.put(stockItem.getPayPalId(), stockItem);
		save();
	}
	
	public void save() throws Exception
	{

		URL url = this.getClass().getResource("data/data.dat");
		if (url == null)
		{
			url = new File("data/data.dat").toURL();
		}
		OutputStream os = new FileOutputStream(new File(url.toURI()));
		BufferedOutputStream bos = new BufferedOutputStream(os);
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		
		oos.writeObject(stock);
		oos.close();
	}
	
	public List<StockItem> getAllStock()
	{
		return stock;
	}
	
	public List<StockItem> getNonDeletedStock()
	{
		return stock.stream().filter(si->!si.isDeleted()).collect(Collectors.toList());
	}
	
	public List<StockItem> search(String searchString)
	{
		return null;
	}
	
	public List<PayPalButton> getAsPayPalButtons()
	{
		List<PayPalButton> buttons = new ArrayList<PayPalButton>();
		for (StockItem item : getNonDeletedStock())
		{
			PayPalButton button = new PayPalButton();
			button.setItemName(item.getName());
			button.setPayPalId(item.getPayPalId());
			button.setQuantity(item.getQuantity());
			button.setStockId("unknown");
			buttons.add(button);
		}
		return buttons;
	}

	public StockItem getItem(String hostedButtonID)
	{
		return map.get(hostedButtonID);
	}
}
