package com.moogiesoft.swing;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.moogiesoft.html.CSS;
import com.moogiesoft.html.Swing2HTML;
import com.moogiesoft.html.ToHTML;

public class LinkLabel extends JButton implements ToHTML
{
//	static String htmlTemplate;
//	static String htmlImageTemplate;
	static CSS cssTemplate;
	private static String script;
	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
//			Files.readAllLines(Paths.get("data/templates/HTMLLabel.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
//			
//			htmlTemplate=sb.toString();
//			
//			sb.setLength(0);
//			
			Files.readAllLines(Paths.get("data/templates/HTMLLinkLabelScript.template")).stream().forEach(line->sb.append(line+"\n"));//,
			
			script=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLLinkLabelCSS.template")).stream().forEach(line->sb.append(line+"\n"));
			cssTemplate = new CSS();
			cssTemplate.className="HTMLLinkLabelCSS.template";
			cssTemplate.raw = sb.toString();

		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private CardPanel cardPanel;
	private String cardName;

	private LinkLabel(CardPanel cardPanel, String cardName, String displayText, URL imageURL)
	{
		setBorder(null);
		setMargin(null);
		this.cardPanel=cardPanel;
		this.cardName=cardName;
		if (displayText!=null) setText(displayText);
		if (imageURL!=null) setIcon(new ImageIcon(imageURL));
		
		
		
		System.out.println("cardName: "+cardName+" imageURL: "+imageURL);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {

			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				setFont(getFont().deriveFont(getFont().getStyle() ^ Font.BOLD));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
				setFont(getFont().deriveFont(getFont().getStyle() ^ Font.BOLD));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				cardPanel.showCard(cardName);			}
		});
		
	}

	public LinkLabel(CardPanel cardPanel, String cardName, URL imageURL) {
		this(cardPanel,cardName,null,imageURL);
	}
	
	public LinkLabel(CardPanel cardPanel, String cardName, String displayText)
	{
		this(cardPanel,cardName,displayText,null);
	}

	@Override
	public String toHtml(HashMap<String, CSS> cssEntries, String prefixWhiteSpace,
			HashMap<String, List<String>> scripts) {
		prefixWhiteSpace+="  ";
		
			cssEntries.put("HTMLLinkLabelCSS.template",cssTemplate);
			String id= Swing2HTML.getID(this);
			String html = "<span class=\"mouseOver mouseOverImg\" id=\""+id+"\">"+HTMLLabel.toHtml(this, cssEntries, prefixWhiteSpace, scripts,false,true)+"</span>\n";
		
			
			String script = LinkLabel.script;
			script=script.replace("*ID*", id);
			script=script.replace("*CARD_PANEL_ID*", Swing2HTML.getID(cardPanel));
			script=script.replace("*CARD_NAME*", cardName);

			
			List<String> loadedScripts =  scripts.get(HTMLMain.LOADED);
			if (loadedScripts==null)
			{
				loadedScripts= new ArrayList<String>();
				scripts.put(HTMLMain.LOADED,loadedScripts);
			}
			loadedScripts.add(script);

			
		return html;
	}

}
