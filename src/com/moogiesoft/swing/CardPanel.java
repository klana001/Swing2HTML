package com.moogiesoft.swing;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import com.moogiesoft.html.CSS;
import com.moogiesoft.html.Swing2HTML;
import com.moogiesoft.html.ToHTML;

public class CardPanel extends JPanel implements ToHTML  
{
	private static final String CARD_PANEL = "CARD_PANEL";
	CardLayout cardLayout;
	private HashMap<String, Component> cardMap = new HashMap<>();
	private HashMap<Component, String> cardMapRevese = new HashMap<>();
	
	private static String script;
	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			Files.readAllLines(Paths.get("data/templates/HTMLCardPanelScript.template")).stream().forEach(line->sb.append(line+"\n"));//,
			
			script=sb.toString();
			
			sb.setLength(0);


		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public CardPanel()
	{
		cardLayout=new CardLayout();
		super.setLayout(cardLayout);
	}
	
	@Override
	public void setLayout(LayoutManager layout)
	{
		System.out.println("oh no you dont!");
	}
	
	@Override
	public String toHtml(HashMap<String, CSS> cssEntries, String prefixWhiteSpace,
			HashMap<String, List<String>> scripts) {
		
		List<String> cardPanelMainScripts =  scripts.get(CARD_PANEL);
		if (cardPanelMainScripts==null)
		{
			cardPanelMainScripts= new ArrayList<String>();
			cardPanelMainScripts.add(script);
			scripts.put(CARD_PANEL,cardPanelMainScripts);
		}
		
		List<String> cardPanelScripts = new ArrayList<String>();
		for (Component component : getComponents())
		{
			HashMap<String, List<String>> localScripts = new HashMap<>();
			String script = "cardHtml"+Swing2HTML.getID(this)+cardMapRevese.get(component)+" = \""+escapeHTMLforJavaScript(Swing2HTML.toHtml(component, cssEntries, "", localScripts))+"\";\n";
			
			// ensure that non-loaded scripts are added to main script collection
			for (String scriptCategory : localScripts.keySet())
			{
				if (!scriptCategory.equals(HTMLMain.LOADED))
				{
					List<String> masterCategoryScriptList = scripts.get(scriptCategory);
					
					if (masterCategoryScriptList!=null)
					{
						masterCategoryScriptList = new ArrayList<String>();
						scripts.put(scriptCategory, masterCategoryScriptList);
					}
					masterCategoryScriptList.addAll(localScripts.get(scriptCategory));
				}
			}
			
			// rename the local script "LOADED" scripts to become a constructor...
			List<String> constructor = localScripts.get(HTMLMain.LOADED);
			
			if (constructor!=null)
			{
				StringBuilder sb = new StringBuilder();
				
				sb.append("function cardHtml"+Swing2HTML.getID(this)+cardMapRevese.get(component)+"Constructor()\n{\n");
				constructor.forEach(s-> sb.append(s));
				sb.append("}\n");
				ArrayList<String> constructorScriptList = new ArrayList<String>();
				constructorScriptList.add(sb.toString());
				scripts.put(sb.toString(), constructorScriptList);
			}

			
			cardPanelScripts.add(script);
		}
		scripts.put("CardPanelCardHtml"+Swing2HTML.getID(this),cardPanelScripts);
		
		
		// generate scripts for each card.
		String html = "<div id = "+Swing2HTML.getID(this)+"></div>";
		
		
		
		
		
		return html;
//		return Swing2HTML.toHtml(cardMap.g, cssEntries, prefixWhiteSpace, scripts);
	}
	
	@Override
	public void add(Component card,Object object)
	{
		cardMap.put((String) object,card);
		cardMapRevese.put(card, (String) object);
		super.add(card,object);
	}
	
	public LinkLabel createLinkLabel(String namedCard,String displayText)
	{
		return new LinkLabel(this,namedCard,displayText);
	}
	
	public Component getCardForName(String name)
	{
		return cardMap.get(name);
	}

	public void showCard(String cardName) {
		cardLayout.show(this, cardName);
		
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
		html = html.replace("\"", "\\\"");
		return html;
	}
//	static public String escapeHTMLforJavaScript(String html)
//	{
//		html = html.replace("\n", "");
//		html = html.replace("&", "&amp;");
//		html = html.replace("<", "&lt;");
//		html = html.replace(">", "&gt;");
//		html = html.replace("\"", "&quot;");
//		html = html.replace("'", "&#39;");
//		html = html.replace("/", "&#x2F;");
//		return html;
//	}
}
