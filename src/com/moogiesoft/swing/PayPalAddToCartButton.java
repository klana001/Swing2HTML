package com.moogiesoft.swing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.moogiesoft.html.CSS;
import com.moogiesoft.html.Swing2HTML;
import com.moogiesoft.html.ToHTML;
import com.moogiesoft.stockmanager.StockItem;

public class PayPalAddToCartButton extends JButton implements ToHTML
{
	static CSS cssTemplate;
	private static String htmlTemplate;
	private StockItem item;
	
	PayPalAddToCartButton(StockItem item)
	{
		this.item=item;
		ImageIcon imageIcon = new ImageIcon(WebsiteTemplate.class.getResource("/data/pics/btn_cart_LG.gif"));
		setIcon(imageIcon);
	}
	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("data/templates/HTMLPayPalAddToCart_sandbox.template")));
			String line=br.readLine();
			while (line!=null)
			{
				sb.append(line);
				sb.append("\n");
				line=br.readLine();
			}
			
			htmlTemplate=sb.toString();
			
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toHtml(HashMap<String, CSS> cssEntries, String prefixWhiteSpace,
			HashMap<String, List<String>> scripts) {

			String html = htmlTemplate;
			
			if (item.getPayPalId()==null)
			{
				throw new RuntimeException("Need to sync with paypal!");
			}
			html=html.replace("*PAY_PAL_ID*", item.getPayPalId());
			
		return html;
	}
}
