package com.moogiesoft.swing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.moogiesoft.html.CSS;
import com.moogiesoft.html.Swing2HTML;
import com.moogiesoft.html.ToHTML;

import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CustomerFavouriatesPanel extends JComponent  
{
	static String htmlTemplate;
	static CSS cssTemplate;
	private static String htmlImageTemplate;
	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			Files.readAllLines(Paths.get("data/templates/HTMLCustomerFavouriatesPanel.ImagePanel.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLCustomerFavouriatesPanel.ImagePanel.Image.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlImageTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/templates/HTMLCustomerFavouriatesPanelCSS.ImagePanel.template")).stream().forEach(line->sb.append(line+"\n"));
			cssTemplate = new CSS();
			cssTemplate.className="horizontalScroll";
			cssTemplate.raw = sb.toString();
			
			

		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public final class ImagePanel extends JPanel implements ToHTML
	{
		@Override
		public String toHtml(HashMap<String, CSS> cssEntries, String prefixWhiteSpace, HashMap<String, List<String>> scripts)
		{
			StringBuilder sb = new StringBuilder();
			cssEntries.put("horizontalScroll",cssTemplate);
			for (CustomerFavoriate favoriate : favoriates)
			{
				String imagePanelhtml=htmlImageTemplate;
				String id = ""+Math.random();
				cardPanel.add(new JLabel("Text to go here...."+id),id);
				LinkLabel linkLabel = cardPanel.createLinkLabel(id, favoriate.image.url);
				imagePanelhtml = imagePanelhtml.replace("*IMAGE_HTML*",linkLabel.toHtml(cssEntries, prefixWhiteSpace, scripts));
				imagePanelhtml = imagePanelhtml.replace("*START*", prefixWhiteSpace);
				sb.append(imagePanelhtml);
			}

			
			String html=htmlTemplate;
			html=html.replace("*IMAGE_HTML*", sb.toString());

			return html;
		}
		
	}
	
	final class CustomerFavoriate
	{
		String text;
		URLImage image;
		public CustomerFavoriate(String text, URLImage image) {
			super();
			this.text = text;
			this.image = image;
		}
	}
	
	final class URLImage
	{
		String relativePath;
		BufferedImage image;
		URL url;
		
		public URLImage(URL url)
		{
			this.url= url;
			try
			{
				relativePath= url.toString().substring(url.toString().indexOf(Swing2HTML.RESOURCE_PATH)+Swing2HTML.RESOURCE_PATH.length()+1,url.toString().length());
				image=ImageIO.read(url);
			} catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	private CardPanel cardPanel;
	
	public CustomerFavouriatesPanel(CardPanel cardPanel) {
		this.cardPanel=cardPanel;
		setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel(new BorderLayout());
		
		TiledBackGroundJPanel tilePanel = new TiledBackGroundJPanel();
		tilePanel.setTileImage(WebsiteTemplate.class.getResource("/data/pics/dots.png"));
		topPanel.add(tilePanel,BorderLayout.NORTH);
		
		RoundedBackgroundBoxPanel textLabelPanel = new RoundedBackgroundBoxPanel();
		textLabelPanel.setBackgroundBox(0, 0, 8);
		textLabelPanel.setBackground(Color.WHITE);
		JLabel textLabel = new JLabel("Check out our customer's favoriates!");

		textLabelPanel.add(textLabel);
		tilePanel.add(textLabelPanel);
		
		add(topPanel,BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane,BorderLayout.SOUTH);
		
		imagePanel = new ImagePanel();
		scrollPane.setViewportView(imagePanel);
	}
	
	List<CustomerFavoriate> favoriates = new ArrayList<CustomerFavouriatesPanel.CustomerFavoriate>();
	private ImagePanel imagePanel;


//	@Override
//	public String toHtml(HashMap<String, CSS> cssEntries, String prefixWhiteSpace,
//			HashMap<String, List<String>> scripts) {
//		String html = Swing2HTML.toHtml(this, cssEntries, prefixWhiteSpace, scripts)
//	}

	public void addCustomerFavoriate(URL image, String text)
	{
		CustomerFavoriate favoriate = new CustomerFavoriate(text, new URLImage(image));
		favoriates.add(favoriate);
		
		JLabel imageIcon = new JLabel(new ImageIcon(image)); 
		imagePanel.add(imageIcon);
		
		
	}
	
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//    	backingPanel.paintComponents(g);
//    }
}
