package com.moogiesoft.stockmanager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class StockImageMaker extends JFrame
{
	static int OUTPUT_SIZE = 100;
	ImagePane imagePane;
	protected class ImagePane extends JPanel
	{
		
		BufferedImage image;
		Point startPoint;
		Point endPoint;
		int counter =0;
		boolean shift = false;
		
		public ImagePane() {
			
			addMouseMotionListener(new MouseMotionListener() {
				
				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					
					endPoint=e.getPoint();
					
					if (shift)
					{
						int sx = startPoint.x;
						int sy = startPoint.y;
						
						int ex = endPoint.x;
						int ey = endPoint.y;

						
						if (endPoint.x-startPoint.x<0)
						{
							
							sx = endPoint.x;
							ex = startPoint.x;
						}
						
						if (endPoint.y-startPoint.y<0)
						{
							sy = endPoint.y;
							ey = startPoint.y;
						}
						
						int size = ex-sx;
						if (ey-sy > ex-sx)
						{
							size = ey-sy;
						}
						
						endPoint.x = startPoint.x + ((ex == endPoint.x)? size:-size);
						endPoint.y = startPoint.y + ((ey == endPoint.y)? size:-size);
					}
					
					repaint();
					
				}
			});
			

			
			addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					if (startPoint!=null)
					{

						int sx = startPoint.x;
						int sy = startPoint.y;
						
						int ex = endPoint.x;
						int ey = endPoint.y;

						
						if (endPoint.x-startPoint.x<0)
						{
							sx = endPoint.x;
							ex = startPoint.x;
						}
						
						if (endPoint.y-startPoint.y<0)
						{
							sy = endPoint.y;
							ey = startPoint.y;
						}

						
						try
						{
							int size = Math.max(ex-sx,ey-sy);
							
							if (size>0)
							{
								BufferedImage newImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
								
								newImage.getGraphics().drawImage(image.getSubimage(sx, sy, ex-sx,ey-sy ), (size-(ex-sx))/2,(size-(ey-sy))/2,null);
								
								BufferedImage newImage1 = new BufferedImage(OUTPUT_SIZE, OUTPUT_SIZE, BufferedImage.TYPE_INT_RGB);
								newImage1.getGraphics().drawImage(newImage.getScaledInstance(OUTPUT_SIZE, OUTPUT_SIZE,BufferedImage.SCALE_SMOOTH), 0,0,null);
								
								ImageIO.write(newImage1,"PNG" ,new File("C:\\output\\"+(counter++)+".png") );
							}
						} catch(Exception e)
						{
							e.printStackTrace();
						}
						repaint();
					}
					startPoint=null;
					shift=false;
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
						startPoint = arg0.getPoint();
						endPoint=startPoint;
						if (arg0.getButton()!=MouseEvent.BUTTON1)
						{
							shift=true;
						}
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		public void paint(Graphics g)
		{
			g.drawImage(image,0,0,null);
			g.setColor(Color.yellow);
			if (startPoint!=null)
			{
				int sx = startPoint.x;
				int sy = startPoint.y;
				
				int ex = endPoint.x;
				int ey = endPoint.y;

				
				if (endPoint.x-startPoint.x<0)
				{
					sx = endPoint.x;
					ex = startPoint.x;
				}
				
				if (endPoint.y-startPoint.y<0)
				{
					sy = endPoint.y;
					ey = startPoint.y;
				}

				g.drawRect(sx, sy, ex-sx, ey-sy);
			}
				
		}
		
		public void setImage(BufferedImage image)
		{
			this.image = image;
			this.setSize(new Dimension(image.getWidth(), image.getHeight()));
			this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		}
		
	}
	public StockImageMaker()
	{
		final DefaultListModel listModel = new DefaultListModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JList list = new JList(listModel);
		add(list,BorderLayout.WEST);
		imagePane = new ImagePane();
		add(new JScrollPane(imagePane),BorderLayout.CENTER);
		
		File dirFile = new File("c:\\pics");
		for (File file : dirFile.listFiles())
		{
			listModel.addElement(file.getName());
		}
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				try
				{
					imagePane.setImage(ImageIO.read(new File("c:\\pics\\"+list.getSelectedValue())));
				} catch(IOException e)
				{
					e.printStackTrace();
				}
				imagePane.invalidate();
				imagePane.repaint();
			}
		});
		
		setSize(1024,1024);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String args[])
	{
		// TODO Auto-generated method stub
		try	
		{
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");

		} catch (Exception e)
		{
//			do nothing
//			e.printStackTrace();
		}
		
		new StockImageMaker();
	}
}
