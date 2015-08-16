package com.moogiesoft.stockmanager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


public class StockItem implements Serializable
{
	public static final int SMALL_IMAGE_SIZE = 48;


	public interface StockItemListener
	{
		public void stockAdded(StockItem stockItem);
		
		public void updated(StockItem stockItem);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -5475801832215315346L;
	
	private transient BufferedImage smallImage;
	private transient BufferedImage largeImage;
	private transient List<StockItemListener> stockItemListeners;
	private boolean isNew;
//	private transient int[] imagedata;
	private String path;
	private int imageWidth;
	private int imageHeight;
	private Date creationDate;
	private boolean deleted;

	private String payPalID;
	private int quantity;
	private double price;
	private boolean visible;
	private boolean promote;
	
	private String name;
	private String description;
	
	private transient String highLightedDescription;
	private transient String highLightedName;
	
	private transient HashMap<String, List<String>> soundExMapping;

	private String stockId;

	private Date modifiedDate;
//	private Date lastPayPalSynchModifiedDate;
//	
//	public Date getLastPayPalSynchModifiedDate()
//	{
//		return lastPayPalSynchModifiedDate;
//	}
//
//	public void setLastPayPalSynchModifiedDate(Date lastPayPalSynchModifiedDate)
//	{
//		this.lastPayPalSynchModifiedDate = lastPayPalSynchModifiedDate;
//	}

	public StockItem()
	{
		modifiedDate=new Date();
		if (description !=null)
		{
			soundExMapping = SoundEx.convertSentenceToSoundExMap(description,null);
		}
		
		if (name !=null)
		{
			soundExMapping = SoundEx.convertSentenceToSoundExMap(name,soundExMapping);
		}
	}
	
	public void addStockItemListener(StockItemListener listener)
	{
		if (stockItemListeners == null)
		{
			stockItemListeners = new ArrayList<StockItem.StockItemListener>(1); 
		}
		stockItemListeners.add(listener);
	}
	
	public StockItem(boolean isNew)
	{
		this();
		this.isNew=isNew;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getHighLightedDescription()
	{
		if (highLightedDescription ==null && description !=null)
		{
			highLightedDescription = new String(description);
		}
		return highLightedDescription;
	}
	
	public void removeNameHighLighting()
	{
		highLightedName=null;
	}
	
	public void removeDescriptionHighLighting()
	{
		highLightedDescription=null;
	}

	
	
	public String getHighLightedName()
	{
		if (highLightedName ==null && name !=null)
		{
			highLightedName = new String(name);
		}
		return highLightedName;
	}
	
	public void setName(String name)
	{
		soundExMapping = SoundEx.convertSentenceToSoundExMap(name,soundExMapping);
		this.name=name;
		updated();
	}
	
	public void setDescription(String description)
	{
		soundExMapping = SoundEx.convertSentenceToSoundExMap(description,soundExMapping);
		this.description=description;
		updated();
	}
	
	public boolean isDescriptionContainsSimilarSentence(String sentence)
	{
		highLightedDescription = hightlightSentence(sentence,description);
		
		return (highLightedDescription != null);
	}
	
	public boolean isNameContainsSimilarSentence(String sentence)
	{
		highLightedName = hightlightSentence(sentence,name);
		
		return (highLightedName != null);
	}
	
	private String hightlightSentence(String querySentence, String inputSentence)
	{
		  String cleansentence = querySentence.replaceAll("[^A-Za-z0-9 ]", "");
		  String highlighting = new String(inputSentence);
		  boolean found=false;
		  StringTokenizer st = new StringTokenizer(cleansentence," ");
		  Set<String> highLightedWordSet = new HashSet<String>();
		  
		  while (st.hasMoreTokens())
		  {
			  List<String> hitWords = soundExMapping.get(SoundEx.soundex(st.nextToken()));
			  if (hitWords !=null)
			  {
				  found=true;
				  
				  for (String hitWord : hitWords)
				  {
					  if (!highLightedWordSet.contains(hitWord))
					  {
						  highlighting = highlighting.replace(hitWord, "<b><i>"+hitWord+"</i></b>");
						  highLightedWordSet.add(hitWord);
					  }
				  }
			  }
		  }
		  return !found?null:highlighting;
	}

	public BufferedImage getSmallImage() {
		if (smallImage==null)
		{
			contructImages();
		}
	
		return smallImage;
	}


	public BufferedImage getLargeImage() {
		if (largeImage==null)
		{
			contructImages();
		}
		
		return largeImage;
	}

	public void setLargeImage(BufferedImage largeImage) {
		imageWidth=largeImage.getWidth();
		imageHeight=largeImage.getHeight();
		this.largeImage = largeImage;
//		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
//		image.getGraphics().drawImage(largeImage,0,0,null);
////		imagedata=((DataBufferInt)image.getRaster().getDataBuffer()).getData();

		contructImages();
		updated();
	}

private void updated() {
		modifiedDate=new Date();
//		if (stockItemListeners == null)
//		{
//			stockItemListeners = new ArrayList<StockItem.StockItemListener>(1); 
//		}
		stockItemListeners.stream().forEach(l->l.updated(this));
	}

//	public Date getDateOfPurchase() {
//		return new Date(dateOfPurchase);
//	}
//
//	public void setDateOfPurchase(Date dateOfPurchase) {
//		this.dateOfPurchase = dateOfPurchase.getTime();
//	}
//
//	public Date getDateOfSale() {
//		return new Date(dateOfSale);
//	}
//
//	public void setDateOfSale(Date dateOfSale) {
//		this.dateOfSale = dateOfSale.getTime();
//	}
//
//	public long getPurchaseCost() {
//		return purchaseCost;
//	}
//
//	public void setPurchaseCost(long purchaseCost) {
//		this.purchaseCost = purchaseCost;
//	}
//
//	public long getSalePrice() {
//		return salePrice;
//	}
//
//	public void setSalePrice(long salePrice) {
//		this.salePrice = salePrice;
//	}
	
	private void contructImages()
	{
		if (largeImage== null && path!=null) 
		{
			try
			{
				File file = new File(path);
				if (file.exists())
				{
					largeImage = ImageIO.read(file);
					imageWidth=largeImage.getWidth();
					imageHeight=largeImage.getHeight();
//					BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
//					image.getGraphics().drawImage(largeImage,0,0,null);
//					imagedata=((DataBufferInt)image.getRaster().getDataBuffer()).getData();
				}
				else
				{
					imageWidth=40;
					imageHeight=40;
					largeImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
					Graphics2D g = ((Graphics2D) largeImage.getGraphics());
					g.setColor(Color.white);
					g.drawString("Not", 0, 20);
					g.drawString("Found", 0, 40);
//
//					imagedata=((DataBufferInt)largeImage.getRaster().getDataBuffer()).getData();
				}

			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if (imageWidth>0)
		{
			BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(largeImage, 0, 0, null);
//			System.arraycopy(imagedata, 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(),0,imagedata.length);
			
			GraphicsEnvironment environment = 
			      GraphicsEnvironment.getLocalGraphicsEnvironment();
			    
			    GraphicsDevice device = 
			      environment.getDefaultScreenDevice();
			      
			    GraphicsConfiguration config = device.getDefaultConfiguration();
			    
			    // Create an image that does not support transparency (Opaque)
			    largeImage = config.createCompatibleImage(imageWidth, imageHeight, 
			      Transparency.OPAQUE);
			    
			    largeImage.getGraphics().drawImage(image, 0,0,null);
			    smallImage=config.createCompatibleImage(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, 
					      Transparency.OPAQUE);
			    smallImage.getGraphics().drawImage(largeImage.getScaledInstance(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, Image.SCALE_AREA_AVERAGING),0,0,null);
		}
	}
	
//	public static void main(String[] args)
//	{
//		StockItem stockItem= new StockItem();
//		
//		stockItem.setDescription("Welcome to Michelle Klaebe range of quality doll clothes. Here you can buy the most gorgeous dolls clothes, dolls shoes and dolls accessories. Our family business has been sewing gorgeous doll clothes since 2011, our dolls clothes fit the baby born to the cabbage patch dolls.\n"+
//"Just measure your doll as per our measurement guide (coming soon) to see if our doll clothes will fit your dolls. If you are unsure, Just send us an email at michelle_klaebe@yahoo.com.au with your doll measurements and we will help you determine which doll clothes will fit your doll.\n"+
//"\n"+
//"We add new styles each month, so check back often.\n"+
//"\n"+
//"No minimum orders. Fast shipping. Finished inside seams.\n"+
//"\n"+
//"Got questions? email us at michelle_klaebe@yahoo.com.au\n");
//		stockItem.setName("roll");
//		
//		stockItem.setLargeImage(new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB));
//		
//		stockItem.isDescriptionContainsSimilarSentence("clothes of roll seems");
//		System.out.println(stockItem.getHighLightedDescription());
//		
//		stockItem.isNameContainsSimilarSentence("roll robot");
//		System.out.println(stockItem.getHighLightedName());
//		System.out.println(SoundEx.soundex("seams"));
//		System.out.println(SoundEx.soundex("seems"));
//	}
	
	
	@Override
	public String toString()
	{
		return name+" "+description;
	}
//	@Override
//	public boolean equals(Object other)
//	{
//		boolean result=false;
//		if (other instanceof StockItem)
//		{
//			StockItem otherStockItem = (StockItem)other;
//			result = name.equals(otherStockItem.name) &&
//					description.equals(otherStockItem.description); 
//		}
//		return result;
//	}

	public void setImagePath(String path) {
		this.path=path;
		updated();
	}

	public Date getCreationDate() {
		return creationDate;
	}
	
	public boolean isNew()
	{
		return isNew;
	}

	public void setNew(boolean isNew) {
		if (isNew!=this.isNew)
		{
			this.isNew=isNew;
	
			if (stockItemListeners == null)
			{
				stockItemListeners = new ArrayList<StockItem.StockItemListener>(1); 
			}
			
			stockItemListeners.stream().forEach(l->l.stockAdded(this));
			creationDate = new Date();
			updated();
		}
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
		updated();
	}

	public int getQuantity()
	{
		return quantity;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity=quantity;
		updated();
	}

	public double getPrice()
	{
		return price;
	}
	
	
	public void setPrice(double price)
	{
		this.price=price;
		updated();
	}

	public String getPayPalId()
	{
		return payPalID;
	}

	public String getStockId()
	{
		return stockId;
	}

	public void setPayPalId(String payPalId)
	{
		this.payPalID=payPalId;
		updated();
	}

	public Date getModifiedDate()
	{
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate)
	{
		this.modifiedDate=modifiedDate;
		
	}
}
