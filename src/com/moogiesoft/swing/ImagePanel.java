package com.moogiesoft.swing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import com.moogiesoft.html.CSS;
import com.moogiesoft.html.ToHTML;
import com.moogiesoft.swing.CustomerFavouriatesPanel.CustomerFavoriate;

public class ImagePanel extends JPanel implements ToHTML
{
	static String htmlTemplate;
	static CSS cssTemplate;
	private static String htmlImageTemplate;
	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			Files.readAllLines(Paths.get("data/templates/HTMLCustomerFavouriatesPanel.ImagePanel.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLCustomerFavouriatesPanel.ImagePanel.Image.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlImageTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLCustomerFavouriatesPanelCSS.ImagePanel.template")).stream().forEach(line->sb.append(line+"\n"));
			cssTemplate = new CSS();
			cssTemplate.className="horizontalScroll";
			cssTemplate.raw = sb.toString();
			
			

		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	List<LinkLabel> linklabels = new ArrayList<LinkLabel>();

	public void add(LinkLabel linkLabel)
	{
		linklabels.add(linkLabel);
		super.add(linkLabel);
	}
	
	@Override
	public String toHtml(HashMap<String, CSS> cssEntries, String prefixWhiteSpace, HashMap<String, List<String>> scripts)
	{
		StringBuilder sb = new StringBuilder();
		cssEntries.put("horizontalScroll",cssTemplate);
		for (LinkLabel linkLabel : linklabels)
		{
			String imagePanelhtml=htmlImageTemplate;

			imagePanelhtml = imagePanelhtml.replace("*IMAGE_HTML*",linkLabel.toHtml(cssEntries, prefixWhiteSpace, scripts));
			imagePanelhtml = imagePanelhtml.replace("*START*", prefixWhiteSpace);
			sb.append(imagePanelhtml);
		}

		
		String html=htmlTemplate;
		html=html.replace("*IMAGE_HTML*", sb.toString());

		return html;
	}
	
}
