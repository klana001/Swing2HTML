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

public class HTMLBorderLayout {
	
	static String htmlTemplate;
	static CSS cssTemplate;
	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			Files.readAllLines(Paths.get("data/HTMLBorderLayout.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlTemplate=sb.toString();
			
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

	static String toHtml(BorderLayout layout,CSS style, HashMap<String, CSS> cssEntries,String prefixWhiteSpace)
	{
		cssEntries.put("HTMLBorderLayoutCSS.template",cssTemplate);
		prefixWhiteSpace+="  ";
		Component northComponent = layout.getLayoutComponent(BorderLayout.NORTH);
		Component eastComponent = layout.getLayoutComponent(BorderLayout.EAST);
		Component westComponent = layout.getLayoutComponent(BorderLayout.WEST);
		Component southComponent = layout.getLayoutComponent(BorderLayout.SOUTH);
		Component centerComponent = layout.getLayoutComponent(BorderLayout.CENTER);
		
		String html = htmlTemplate.replace("*START*", prefixWhiteSpace);
		html = html.replace("*CONTAINER_CLASSES*", style!=null?style.className:"");
		html = html.replace("*STYLE*", "");
		
		html = html.replace("*TOP_CLASSES*", "");
		if (northComponent==null)
		{
			html = html.replace("*TOP_ID*", "" );
			html = html.replace("*TOP_HTML*", "");
		}
		else
		{
			html = html.replace("*TOP_ID*", Swing2HTML.getID(northComponent) );
			html = html.replace("*TOP_HTML*", Swing2HTML.toHtml(northComponent,cssEntries,prefixWhiteSpace));
		}

		html = html.replace("*LEFT_CLASSES*", "");
		if (westComponent==null)
		{
			html = html.replace("*LEFT_ID*", "" );
			html = html.replace("*LEFT_HTML*", "");
		}
		else
		{
			html = html.replace("*LEFT_ID*", Swing2HTML.getID(westComponent) );
			html = html.replace("*LEFT_HTML*", Swing2HTML.toHtml(westComponent,cssEntries,prefixWhiteSpace));
		}
		html = html.replace("*MID_CLASSES*", "cell-expandToFill");
		if (centerComponent==null)
		{
			html = html.replace("*MID_ID*", "" );
			html = html.replace("*MID_HTML*", "");
		}
		else
		{
			html = html.replace("*MID_ID*", Swing2HTML.getID(centerComponent) );
			html = html.replace("*MID_HTML*", Swing2HTML.toHtml(centerComponent,cssEntries,prefixWhiteSpace));
		}
		
		html = html.replace("*RIGHT_CLASSES*", "");
		if (eastComponent==null)
		{
			html = html.replace("*RIGHT_ID*", "" );
			html = html.replace("*RIGHT_HTML*", "");
		}
		else
		{
			html = html.replace("*RIGHT_ID*", Swing2HTML.getID(eastComponent) );
			html = html.replace("*RIGHT_HTML*", Swing2HTML.toHtml(eastComponent,cssEntries,prefixWhiteSpace));
		}
		
		html = html.replace("*BOTTOM_CLASSES*", "");
		if (southComponent==null)
		{
			html = html.replace("*BOTTOM_ID*", "" );
			html = html.replace("*BOTTOM_HTML*", "");
		}
		else
		{
			html = html.replace("*BOTTOM_ID*", Swing2HTML.getID(southComponent) );
			html = html.replace("*BOTTOM_HTML*", Swing2HTML.toHtml(southComponent,cssEntries,prefixWhiteSpace));
		}
		
		return html;
	}
}
