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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.moogiesoft.html.CSS;
import com.moogiesoft.html.Swing2HTML;



public class HTMLLabel {
	
	static String htmlTemplate;
	static String htmlImageTemplate;
	static CSS cssTemplate;
	private static String imageLabelScriptTemplate;
	
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
			
//			Files.readAllLines(Paths.get("data/templates/HTMLLabelImageScript.template")).stream().forEach(line->sb.append(line+"\n"));//,
//			imageLabelScriptTemplate=sb.toString();
//		
//			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLLabelCSS.template")).stream().forEach(line->sb.append(line+"\n"));
			cssTemplate = new CSS();
			cssTemplate.className="HTMLLabelCSS.template";
			cssTemplate.raw = sb.toString();

		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static String toHtml(Component label,HashMap<String, CSS> cssEntries,String prefixWhiteSpace,HashMap<String, List<String>> scripts, boolean fitToContainer)
	{
		Icon icon;
		String text;
		if (label instanceof JButton)
		{
			icon = ((JButton) label).getIcon();
			text = ((JButton) label).getText();
		}
		else if (label instanceof JLabel)
		{
			icon = ((JLabel) label).getIcon();
			text = ((JLabel) label).getText();
		}
		else
		{
			throw new RuntimeException("label not Jbutton or JLabel");
		}
		prefixWhiteSpace+="  ";
		String html ="";
		
		CSS style;
		if (icon!=null)
		{
			try
			{
				List<String> loadedScripts =  scripts.get(HTMLMain.LOADED);
				if (loadedScripts==null)
				{
					loadedScripts= new ArrayList<String>();
					scripts.put(HTMLMain.LOADED,loadedScripts);
				}
				
				
				
				Class<? extends Icon> clazz = icon.getClass();
				Field urlField = clazz.getDeclaredField("location");
				urlField.setAccessible(true);
	
				URL location = (URL) urlField.get(icon);
				String relativeLocation = location.toString().substring(location.toString().indexOf(Swing2HTML.RESOURCE_PATH)+Swing2HTML.RESOURCE_PATH.length()+1, location.toString().length());
				
//				HashMap<Style, String> extraStyling = new HashMap();
//				extraStyling.put(Style.BACKGROUND, "background-image: url(\""+relativeLocation+"\");");
//				extraStyling.put(Style.WIDTH, "width: "+label.getWidth()+"px;");
//				extraStyling.put(Style.HEIGHT, "height: "+label.getWidth()+"px;");
//				style = Swing2HTML.getStyle(label,cssEntries,extraStyling);
//				cssEntries.put("HTMLLabelCSS.template",cssTemplate);
				
				html = htmlImageTemplate.replace("*START*", prefixWhiteSpace);
				html = html.replace("*URL*", relativeLocation);
//				html = html.replace("*CLASSES*", fitToContainer?"scaleImage":"");
				html = html.replace("*CLASSES*", "");

//				String script=imageLabelScriptTemplate;
//				script = script.replace("*ID*", Swing2HTML.getID(label));
//				script = script.replace("*IMG_ID*", Swing2HTML.getID(label));

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
			html = html.replace("*TEXT*", Swing2HTML.stringToHTMLString(text));
			html = html.replace("*ID*", Swing2HTML.getID(label) );
		}


		
		
		

		
		return html;
	}
	
	
	
}
