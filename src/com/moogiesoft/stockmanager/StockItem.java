package com.moogiesoft.stockmanager;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;


public class StockItem implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5475801832215315346L;
	
	private transient BufferedImage smallImage;
	private transient BufferedImage largeImage;
	private int[] imagedata;
	private int imageWidth;
	private int imageHeight;
	
	private long dateOfPurchase;
	private long dateOfSale;
	private long purchaseCost;
	private long salePrice;
	
	private String name;
	private String description;
	
	private transient String highLightedDescription;
	private transient String highLightedName;
	
	private transient HashMap<String, List<String>> soundExMapping;
	
	public StockItem()
	{
		if (description !=null)
		{
			soundExMapping = SoundEx.convertSentenceToSoundExMap(description,null);
		}
		
		if (name !=null)
		{
			soundExMapping = SoundEx.convertSentenceToSoundExMap(name,soundExMapping);
		}
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
		if (highLightedDescription ==null)
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
		if (highLightedName ==null)
		{
			highLightedName = new String(name);
		}
		return highLightedName;
	}
	
	public void setName(String name)
	{
		soundExMapping = SoundEx.convertSentenceToSoundExMap(name,soundExMapping);
		this.name=name;
	}
	
	public void setDescription(String description)
	{
		soundExMapping = SoundEx.convertSentenceToSoundExMap(description,soundExMapping);
		this.description=description;
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
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(largeImage,0,0,null);
		imagedata=((DataBufferInt)image.getRaster().getDataBuffer()).getData();

		contructImages();
	}

	public Date getDateOfPurchase() {
		return new Date(dateOfPurchase);
	}

	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase.getTime();
	}

	public Date getDateOfSale() {
		return new Date(dateOfSale);
	}

	public void setDateOfSale(Date dateOfSale) {
		this.dateOfSale = dateOfSale.getTime();
	}

	public long getPurchaseCost() {
		return purchaseCost;
	}

	public void setPurchaseCost(long purchaseCost) {
		this.purchaseCost = purchaseCost;
	}

	public long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(long salePrice) {
		this.salePrice = salePrice;
	}
	
	private void contructImages()
	{
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		System.arraycopy(imagedata, 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(),0,imagedata.length);
		
		GraphicsEnvironment environment = 
		      GraphicsEnvironment.getLocalGraphicsEnvironment();
		    
		    GraphicsDevice device = 
		      environment.getDefaultScreenDevice();
		      
		    GraphicsConfiguration config = device.getDefaultConfiguration();
		    
		    // Create an image that does not support transparency (Opaque)
		    largeImage = config.createCompatibleImage(imageWidth, imageHeight, 
		      Transparency.OPAQUE);
		    
		    largeImage.getGraphics().drawImage(image, 0,0,null);
		    smallImage=config.createCompatibleImage(48, 48, 
				      Transparency.OPAQUE);
		    smallImage.getGraphics().drawImage(largeImage.getScaledInstance(48, 48, Image.SCALE_AREA_AVERAGING),0,0,null);
	}
	
	public static void main(String[] args)
	{
		StockItem stockItem= new StockItem();
		
		stockItem.setDescription("Welcome to Michelle Klaebe range of quality doll clothes. Here you can buy the most gorgeous dolls clothes, dolls shoes and dolls accessories. Our family business has been sewing gorgeous doll clothes since 2011, our dolls clothes fit the baby born to the cabbage patch dolls.\n"+
"Just measure your doll as per our measurement guide (coming soon) to see if our doll clothes will fit your dolls. If you are unsure, Just send us an email at michelle_klaebe@yahoo.com.au with your doll measurements and we will help you determine which doll clothes will fit your doll.\n"+
"\n"+
"We add new styles each month, so check back often.\n"+
"\n"+
"No minimum orders. Fast shipping. Finished inside seams.\n"+
"\n"+
"Got questions? email us at michelle_klaebe@yahoo.com.au\n");
		stockItem.setName("roll");
		
		stockItem.setLargeImage(new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB));
		
		stockItem.isDescriptionContainsSimilarSentence("clothes of roll seems");
		System.out.println(stockItem.getHighLightedDescription());
		
		stockItem.isNameContainsSimilarSentence("roll robot");
		System.out.println(stockItem.getHighLightedName());
		System.out.println(SoundEx.soundex("seams"));
		System.out.println(SoundEx.soundex("seems"));
	}
	
	
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
}
