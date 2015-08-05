import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;

public class HTMLFlowLayout {
	
	static String htmlTemplate;
	static String htmlInnerTemplate;
	static CSS cssTemplate;
	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			Files.readAllLines(Paths.get("data/HTMLFlowLayout.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/HTMLFlowLayoutInner.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlInnerTemplate=sb.toString();
			
			sb.setLength(0);
			Files.readAllLines(Paths.get("data/HTMLBorderLayoutCSS.template")).stream().forEach(line->sb.append(line+"\n"));
			cssTemplate = new CSS();
			cssTemplate.className="HTMLBorderLayoutCSS.template";
			cssTemplate.raw = sb.toString();

		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	static String toHtml(Container container,CSS style, HashMap<String, CSS> cssEntries,String prefixWhiteSpace,HashMap<String, List<String>> scripts)
	{
		cssEntries.put("HTMLBorderLayoutCSS.template",cssTemplate);
		prefixWhiteSpace+="  ";
		
		String html = htmlTemplate.replace("*START*", prefixWhiteSpace);
		html = html.replace("*CONTAINER_CLASSES*", style!=null?style.className:"");
		html = html.replace("*STYLE*", "");
		
		Component[] components = container.getComponents();
		
		StringBuilder sb= new StringBuilder();
		for (Component component : components)
		{
			String innerhtml = htmlInnerTemplate.replace("*START*", prefixWhiteSpace+"  ");
			
			CSS innerStyle = Swing2HTML.getStyle(component,cssEntries);
			innerhtml = innerhtml.replace("*CLASSES*", innerStyle!=null?innerStyle.className:"");
			
			innerhtml = innerhtml.replace("*ID*", Swing2HTML.getID(component) );
			innerhtml = innerhtml.replace("*HTML*", Swing2HTML.toHtml(component,cssEntries,prefixWhiteSpace,scripts));
			sb.append(innerhtml);
		}
		
		html = html.replace("*INNER_PANELS*", sb.toString());
		
		return html;
	}
}
