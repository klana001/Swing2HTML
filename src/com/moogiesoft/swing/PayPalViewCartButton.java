package com.moogiesoft.swing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.moogiesoft.html.CSS;
import com.moogiesoft.html.Swing2HTML;
import com.moogiesoft.html.ToHTML;

public class PayPalViewCartButton extends JButton implements ToHTML
{
	static CSS cssTemplate;
	private static String script;
	private static String htmlTemplate;
	
	PayPalViewCartButton()
	{
		ImageIcon imageIcon = new ImageIcon(WebsiteTemplate.class.getResource("/data/pics/btn_viewcart_SM.gif"));
		setIcon(imageIcon);
	}
	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("data/templates/HTMLPayPalViewCart_sandbox.template")));
			String line=br.readLine();
			while (line!=null)
			{
				sb.append(line);
				sb.append("\n");
				line=br.readLine();
			}
			
			htmlTemplate=sb.toString();
			
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toHtml(HashMap<String, CSS> cssEntries, String prefixWhiteSpace,
			HashMap<String, List<String>> scripts) {

//			String html = htmlTemplate;
//			html.replace("*START*", "");
//			htm
//			script=script.replace("*CARD_PANEL_ID*", Swing2HTML.getID(cardPanel));
//			script=script.replace("*CARD_NAME*", cardName);
//
//			
//			List<String> loadedScripts =  scripts.get(HTMLMain.LOADED);
//			if (loadedScripts==null)
//			{
//				loadedScripts= new ArrayList<String>();
//				scripts.put(HTMLMain.LOADED,loadedScripts);
//			}
//			loadedScripts.add(script);

			
		return htmlTemplate;
	}
}
