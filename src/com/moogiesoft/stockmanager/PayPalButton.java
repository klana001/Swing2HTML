package com.moogiesoft.stockmanager;

import java.util.Date;
import java.util.Map;


class PayPalButton
{
	public PayPalButton(Map<String, String> map)
	{
		itemName = map.get("item_name");
		price = map.get("amount");
		payPalId = map.get("payPalId");
		stockId = map.get("item_number");
	}
	
	public PayPalButton(PayPalButton button)
	{
		itemName = button.itemName;
		price = button.price;
		payPalId = button.payPalId;
		stockId = button.stockId;
		quantity = button.quantity;
		modifiedDate = new Date(button.modifiedDate.getTime());
	}
	
	public PayPalButton(StockItem item)
	{
		itemName = item.getName();
		price = ""+item.getPrice();
		payPalId = item.getPayPalId();
		stockId = item.getStockId();
		quantity = item.getQuantity();
		modifiedDate = new Date(item.getModifiedDate().getTime());
	}
	
	public PayPalButton()
	{
	}
	
	private Date modifiedDate;	
	String itemName;
	String stockId;
	String price;
	int quantity =-1;
	String payPalId;
	public String getItemName()
	{
		return itemName;
	}
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
	public String getStockId()
	{
		return stockId;
	}
	public void setStockId(String stockId)
	{
		this.stockId = stockId;
	}
	public String getPrice()
	{
		return price;
	}
	public void setPrice(String price)
	{
		this.price = price;
	}
	public int getQuantity()
	{
		return quantity;
	}
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	public String getPayPalId()
	{
		return payPalId;
	}
	public void setPayPalId(String payPalId)
	{
		this.payPalId = payPalId;
	}
	
	public Date getModifiedDate()
	{
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate)
	{
		this.modifiedDate = modifiedDate;
	}

	public String toString()
	{
		return "itemName: "+itemName+" stockId: "+stockId+" price: "+price+" quantity: "+quantity+" payPalId: "+payPalId;
	}
}
