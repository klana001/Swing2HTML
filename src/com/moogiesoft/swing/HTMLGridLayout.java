package com.moogiesoft.swing;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;

import com.moogiesoft.html.CSS;
import com.moogiesoft.html.Swing2HTML;

public class HTMLGridLayout {

	static CSS cssTemplate;
	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			
			Files.readAllLines(Paths.get("data/templates/HTMLBorderLayoutCSS.template")).stream().forEach(line->sb.append(line+"\n"));
			cssTemplate = new CSS();
			cssTemplate.className="HTMLBorderLayoutCSS.template";
			cssTemplate.raw = sb.toString();

		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static String toHtml(Container container,CSS style, HashMap<String, CSS> cssEntries,String prefixWhiteSpace,HashMap<String, List<String>> scripts)
	{
		GridLayout gridLayout = (GridLayout) container.getLayout();
		cssEntries.put("HTMLBorderLayoutCSS.template",cssTemplate);
		prefixWhiteSpace+="  ";
		
		String html = "<div class=\"container cell-expandToFill\">\n";
		int cols = gridLayout.getColumns();
		cols = cols==0?1:cols;
		int rows = container.getComponentCount()/cols;
		
		for (int y=0;y<rows;y++)
		{
			html+= "<div class=\"row\">\n";
			
			for (int x=0;x<cols;x++)
			{
//				html+="<div class=\"container cell-expandToFill\">\n";
				html+= "<div class=\"cell\">\n";
				
				Component cellComponent = container.getComponent(y*cols+x);
				if (cellComponent!=null)
				{
					html+= Swing2HTML.toHtml(cellComponent,cssEntries,prefixWhiteSpace,scripts);
				}
				html+= "</div>";
			}
			html+= "</div>";
		}
		html+= "</div>";
		
		return html;
	}
}
