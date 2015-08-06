package com.moogiesoft.swing;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.moogiesoft.html.CSS;
import com.moogiesoft.html.Swing2HTML;



public class HTMLLabel {
	
	static String htmlTemplate;
	static String htmlImageTemplate;
	static CSS cssTemplate;
	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			Files.readAllLines(Paths.get("data/templates/HTMLLabel.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLLabelImage.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlImageTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLLabelCSS.template")).stream().forEach(line->sb.append(line+"\n"));
			cssTemplate = new CSS();
			cssTemplate.className="HTMLLabelCSS.template";
			cssTemplate.raw = sb.toString();

		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static String toHtml(JLabel label,HashMap<String, CSS> cssEntries,String prefixWhiteSpace,HashMap<String, List<String>> scripts)
	{
		
		prefixWhiteSpace+="  ";
		String html ="";
		
		CSS style;
		if (label.getIcon()!=null)
		{
			try
			{
				Class<? extends Icon> clazz = label.getIcon().getClass();
				Field urlField = clazz.getDeclaredField("location");
				urlField.setAccessible(true);
	
				URL location = (URL) urlField.get(label.getIcon());
				String relativeLocation = location.toString().substring(location.toString().indexOf(Swing2HTML.RESOURCE_PATH)+Swing2HTML.RESOURCE_PATH.length()+1, location.toString().length());
				
//				HashMap<Style, String> extraStyling = new HashMap();
//				extraStyling.put(Style.BACKGROUND, "background-image: url(\""+relativeLocation+"\");");
//				extraStyling.put(Style.WIDTH, "width: "+label.getWidth()+"px;");
//				extraStyling.put(Style.HEIGHT, "height: "+label.getWidth()+"px;");
//				style = Swing2HTML.getStyle(label,cssEntries,extraStyling);
//				cssEntries.put("HTMLLabelCSS.template",cssTemplate);
				
				html = htmlImageTemplate.replace("*START*", prefixWhiteSpace);
				html = html.replace("*URL*", relativeLocation);
//				html = html.replace("*WIDTH*", ""+label.getWidth());
//				html = html.replace("*HEIGHT*", ""+label.getHeight());
				
			} catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		else
		{
			style = Swing2HTML.getStyle(label,cssEntries);
			cssEntries.put("HTMLLabelCSS.template",cssTemplate);
			
			html = htmlTemplate.replace("*START*", prefixWhiteSpace);
			html = html.replace("*CLASSES*", style!=null?style.className:"");
			html = html.replace("*TEXT*", label.getText());
			html = html.replace("*ID*", Swing2HTML.getID(label) );
		}


		
		
		

		
		return html;
	}
	
	
	
}
