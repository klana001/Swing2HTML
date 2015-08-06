package com.moogiesoft.swing;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


public class MainPage extends JFrame
{
	public MainPage() {
		setSize(new Dimension(960, 900));
		setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		WebsiteTemplate websiteTemplate = new WebsiteTemplate();
		getContentPane().add(websiteTemplate, BorderLayout.CENTER);
		
		addComponentListener(new ComponentListener()
		{
			
			@Override
			public void componentShown(ComponentEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e)
			{
				setTitle("W: "+getWidth()+" H: "+getHeight());
				
			}
			
			@Override
			public void componentMoved(ComponentEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}

}
