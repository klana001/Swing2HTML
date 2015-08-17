package com.moogiesoft.stockmanager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class StockImage implements Serializable{
	private transient BufferedImage smallImage;
	private transient BufferedImage largeImage;
	private transient int imageWidth;
	private transient int imageHeight;
	private String imagePath;
	private String thumbnailPath;
	
	public static final int SMALL_IMAGE_SIZE = 48;
	
	public BufferedImage getThumbnailImage()
	{
		if (smallImage==null)
		{
			construct();
		}
		return smallImage;
	}
	
	public String getImagePath()
	{
		return imagePath;
	}
	
	public String getThumbnailPath()
	{
		return thumbnailPath;
	}
	
	public void construct()
	{
		if (largeImage== null && imagePath!=null) 
		{
			try
			{
				File file = new File(imagePath);
				if (file.exists())
				{
					largeImage = ImageIO.read(file);
					imageWidth=largeImage.getWidth();
					imageHeight=largeImage.getHeight();
				}
				else
				{
					imageWidth=SMALL_IMAGE_SIZE;
					imageHeight=SMALL_IMAGE_SIZE;
					largeImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
					Graphics2D g = ((Graphics2D) largeImage.getGraphics());
					g.setColor(Color.white);
					g.drawString("Not", 0, 20);
					g.drawString("Found", 0, 40);
				}

			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if (imageWidth>0)
		{
			if (thumbnailPath!=null)
			{
				
				File file = new File(thumbnailPath);
				if (file.exists())
				{
				    try
				    {
				    	smallImage = ImageIO.read(file);
				    	return;

					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
				
			
			BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
			img.getGraphics().drawImage(largeImage, 0, 0, null);
//			System.arraycopy(imagedata, 0, ((DataBufferInt)getRaster().getDataBuffer()).getData(),0,imagedata.length);
			
			GraphicsEnvironment environment = 
			      GraphicsEnvironment.getLocalGraphicsEnvironment();
			    
		    GraphicsDevice device = 
		      environment.getDefaultScreenDevice();
		      
		    GraphicsConfiguration config = device.getDefaultConfiguration();
		    
		    // Create an image that does not support transparency (Opaque)
		    largeImage = config.createCompatibleImage(imageWidth, imageHeight, 
		      Transparency.OPAQUE);
		    
		    largeImage.getGraphics().drawImage(img, 0,0,null);
		    smallImage=config.createCompatibleImage(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, 
				      Transparency.OPAQUE);
		    smallImage.getGraphics().drawImage(largeImage.getScaledInstance(SMALL_IMAGE_SIZE, SMALL_IMAGE_SIZE, Image.SCALE_AREA_AVERAGING),0,0,null);
		    
			if (thumbnailPath!=null)
			{
			    try
			    {
			    	ImageIO.write(smallImage,"png",new File(thumbnailPath));

				} catch (IOException e)
				{
					e.printStackTrace();
				
				}
			}
//			}
//			else
//			{
//				smallImage = new BufferedImage(40, 40, BufferedImage.TYPE_INT_RGB);
//				Graphics2D g = ((Graphics2D) largeImage.getGraphics());
//				g.setColor(Color.white);
//				g.drawString("Not", 0, 20);
//				g.drawString("Found", 0, 40);
//			}
		}
	}

	public BufferedImage getLargeImage()
	{
		return largeImage;
	}

	public void setImagePath(String path)
	{
		imagePath=path.replace("\"", "/");
		
	}

	public void setThumbnailPath(String path)
	{
		thumbnailPath=path.replace("\"", "/");
		
	}
}
