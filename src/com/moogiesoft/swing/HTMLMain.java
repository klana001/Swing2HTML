package com.moogiesoft.swing;
import java.awt.BorderLayout;
import java.awt.Component;
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

public class HTMLMain {
	static public final String LOADED="LOADED";
	static String htmlTemplate;
//	static CSS cssTemplate;
//	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			Files.readAllLines(Paths.get("data/templates/HTMLMain.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlTemplate=sb.toString();
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static String toHtml(Component component)
	{
		
		
		HashMap<String, CSS> cssEntries = new HashMap<>();
		HashMap<String, List<String>> scripts = new HashMap<>();
		
		
		String defaultStyle="";
		

//		defaultStyle+="background: #"+String.format("%06X", Swing2HTML.defaultBackground & 0xFFFFFF)+";\n";
		defaultStyle+="color: #"+String.format("%06X", Swing2HTML.defaultForeground & 0xFFFFFF)+";\n";
		defaultStyle+="font-size:"+Swing2HTML.defaultFont.getSize()+"px;\n";
		defaultStyle+="font-family: \""+component.getFont().getFamily()+"\";\n";
		
//		defaultStyle+="width: "+component.getWidth()+"px;\n";
//		defaultStyle+="height: "+component.getWidth()+"px;\n";

		CSS defaults = new CSS();
		defaults.style=defaultStyle;
		defaults.className="defaults";
		
		cssEntries.put(defaults.style,defaults);

		
		String componentHTML= Swing2HTML.toHtml(component, cssEntries, "      ",scripts);
		String html = htmlTemplate.replace("*START*", "");
		
		StringBuilder sb= new StringBuilder();
		cssEntries.values().stream().forEach(css->sb.append(css.raw!=null?css.raw:("."+css.className+"\n{"+css.style+"}\n\n")));
		html = html.replace("*CSS*", sb.toString());
		
		sb.setLength(0);
		scripts.entrySet().stream().filter(entry->!entry.getKey().equals(LOADED)).forEach(entry->entry.getValue().stream().forEach(script->sb.append(script+"\n")));
		html = html.replace("*SCRIPT*", sb.toString());
		
		sb.setLength(0);
		scripts.entrySet().stream().filter(entry->entry.getKey().equals(LOADED)).forEach(entry->entry.getValue().stream().forEach(script->sb.append(script+"\n")));
		html = html.replace("*LOADED*", sb.toString());

		html = html.replace("*HTML*", componentHTML);

//		html= escapeHTMLforJavaScript(html);
		
		return html;
	}
	
	static public String escapeHTMLforJavaScript(String html)
	{
		int startLength;
		do
		{
			startLength=html.length();
			html = html.replace("  ","");
		} while (html.length()!=startLength);
		
		html = html.replace("\t","");
		html = html.replace("\n", "");
		return html;
	}
}
