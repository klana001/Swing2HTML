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

public class HTMLBorderLayout {
	
	static String htmlTemplate;
	static CSS cssTemplate;
	private static String htmlBottomTemplate;
	private static String htmlMidRightTemplate;
	private static String htmlMidMidTemplate;
	private static String htmlMidLeftTemplate;
	private static String htmlMidTemplate;
	private static String htmlTopTemplate;
	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			Files.readAllLines(Paths.get("data/templates/HTMLBorderLayout.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLBorderLayout.top.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlTopTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLBorderLayout.mid.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlMidTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLBorderLayout.mid.left.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlMidLeftTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLBorderLayout.mid.mid.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlMidMidTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLBorderLayout.mid.right.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlMidRightTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLBorderLayout.bottom.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlBottomTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLBorderLayoutCSS.template")).stream().forEach(line->sb.append(line+"\n"));
			cssTemplate = new CSS();
			cssTemplate.className="HTMLBorderLayoutCSS.template";
			cssTemplate.raw = sb.toString();

		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static String toHtml(BorderLayout layout,CSS style, HashMap<String, CSS> cssEntries,String prefixWhiteSpace,HashMap<String, List<String>> scripts)
	{
		cssEntries.put("HTMLBorderLayoutCSS.template",cssTemplate);
		prefixWhiteSpace+="  ";
		Component northComponent = layout.getLayoutComponent(BorderLayout.NORTH);
		Component eastComponent = layout.getLayoutComponent(BorderLayout.EAST);
		Component westComponent = layout.getLayoutComponent(BorderLayout.WEST);
		Component southComponent = layout.getLayoutComponent(BorderLayout.SOUTH);
		Component centerComponent = layout.getLayoutComponent(BorderLayout.CENTER);
		
		String html = htmlTemplate;
		String rowsHtml = "";
		String alignment="alignMiddle";
		String topHtml="";
		if (northComponent!=null)
		{
			topHtml=htmlTopTemplate;
			topHtml = topHtml.replace("*TOP_CLASSES*", "");
			topHtml = topHtml.replace("*TOP_ID*", Swing2HTML.getID(northComponent) );
			topHtml = topHtml.replace("*TOP_HTML*", Swing2HTML.toHtml(northComponent,cssEntries,prefixWhiteSpace,scripts));
			alignment="alignTop";
		}
		rowsHtml+=topHtml;

		String midHTML=htmlMidTemplate;
//		if (westComponent!=null || eastComponent!=null || centerComponent!=null)
//		{
			String midInnerHTML="";
			
			if (westComponent!=null)
			{
				midInnerHTML+=htmlMidLeftTemplate;
				midInnerHTML = midInnerHTML.replace("*LEFT_CLASSES*", "");
				midInnerHTML = midInnerHTML.replace("*LEFT_ID*", Swing2HTML.getID(westComponent) );
				midInnerHTML = midInnerHTML.replace("*LEFT_HTML*", Swing2HTML.toHtml(westComponent,cssEntries,prefixWhiteSpace,scripts));
				alignment="alignLeft";
			}
			
//			if (centerComponent!=null)
//			{
				midInnerHTML+=htmlMidMidTemplate;
				midInnerHTML = midInnerHTML.replace("*MID_CLASSES*", "cell-expandToFill");
				
				if (centerComponent==null)
				{
					midInnerHTML = midInnerHTML.replace("*MID_ID*", "" );
					midInnerHTML = midInnerHTML.replace("*MID_HTML*", "");
				}
				else
				{
					midInnerHTML = midInnerHTML.replace("*MID_ID*", Swing2HTML.getID(centerComponent) );
					midInnerHTML = midInnerHTML.replace("*MID_HTML*", Swing2HTML.toHtml(centerComponent,cssEntries,prefixWhiteSpace,scripts));
				}
//			}
			
			if (eastComponent!=null)
			{
				midInnerHTML+=htmlMidRightTemplate;
				midInnerHTML = midInnerHTML.replace("*RIGHT_CLASSES*", "");
				midInnerHTML = midInnerHTML.replace("*RIGHT_ID*", Swing2HTML.getID(eastComponent) );
				midInnerHTML = midInnerHTML.replace("*RIGHT_HTML*", Swing2HTML.toHtml(eastComponent,cssEntries,prefixWhiteSpace,scripts));
				alignment="alignRight";
			}
			
			midHTML = midHTML.replace("*CELLS*", midInnerHTML);
//		}
		rowsHtml+=midHTML;
		
		String bottomHtml="";
		if (southComponent!=null)
		{
			bottomHtml=htmlBottomTemplate;
			bottomHtml = bottomHtml.replace("*BOTTOM_CLASSES*", "");
			bottomHtml = bottomHtml.replace("*BOTTOM_ID*", Swing2HTML.getID(southComponent) );
			bottomHtml = bottomHtml.replace("*BOTTOM_HTML*", Swing2HTML.toHtml(southComponent,cssEntries,prefixWhiteSpace,scripts));
			alignment="alignBottom";
		}
		rowsHtml+=bottomHtml;
		
		html=html.replace("*ROWS*", rowsHtml);

		html=html.replace("*START*", prefixWhiteSpace);
		html = html.replace("*CONTAINER_CLASSES*", style!=null?style.className:"");
		html = html.replace("*STYLE*", "");
		
		return html;
	}
}
