package com.moogiesoft.html;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.moogiesoft.swing.HTMLBorderLayout;
import com.moogiesoft.swing.HTMLFlowLayout;
import com.moogiesoft.swing.HTMLGridLayout;
import com.moogiesoft.swing.HTMLLabel;
import com.moogiesoft.swing.HTMLMain;
import com.moogiesoft.swing.MainPage;
import com.moogiesoft.swing.NonInhieratibleBackground;


public class Swing2HTML
{

	
	public static final String RESOURCE_PATH = "data";
	public static int defaultBackground;
	public static int defaultForeground;
	public static FontUIResource defaultFont; 
	
	static
	{
		System.out.println((Color) UIManager.get("Panel.background"));
		defaultBackground=((Color) UIManager.get("Panel.background")).getRGB();
		defaultForeground=((Color) UIManager.getColor("Label.foreground")).getRGB();
		defaultFont=((FontUIResource) UIManager.get("Label.font"));
	}
	
	
	public static void addFudges(Component component)
	{
		if (component instanceof JLabel)
		{
			component.setSize(component.getWidth()+component.getFont().getSize(),component.getHeight());
		}
		else if (component instanceof Container)
		{
			for (Component child : ((Container) component).getComponents())
			{
				addFudges(child);
			}
		}
			
	}
	
	public static void main(String[] args) throws IOException
	{
		// TODO Auto-generated method stub
		MainPage mainPage = new MainPage();
		mainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPage.setVisible(true);
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				addFudges(mainPage);
				String html=HTMLMain.toHtml(mainPage);
				System.out.println(html);
				
				PrintStream ps;
				try
				{
					ps = new PrintStream("data/main2.html");
					ps.print(html);
					ps.close();
				}
				catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
			
		
//		String html=HTMLMain.toHtml(mainPage);
//		System.out.println(html);
//		
//		PrintStream ps = new PrintStream("data/main2.html");
//		ps.print(html);
//		ps.close();
//		
//		JFrame frame = new JFrame();
//		frame.setBackground(Color.DARK_GRAY);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.add(template);
//		frame.setVisible(true);
		
		
		
	}

	public static String toHtml(Component component, HashMap<String, CSS> cssEntries,String prefixWhiteSpace,HashMap<String, List<String>> scripts)
	{
		String html= "UNKNOWN Component: "+component.getClass().getSimpleName();
		
		if (component instanceof ToHTML)
		{
			html = ((ToHTML) component).toHtml( cssEntries, prefixWhiteSpace,scripts);
		}
		else if (component instanceof JFrame )
		{
			html=toHtml(((JFrame) component).getContentPane(),cssEntries,prefixWhiteSpace,scripts);
		}
		else if (component instanceof JLabel)
		{
			JLabel label = (JLabel) component;
			
			html= HTMLLabel.toHtml(label,cssEntries,prefixWhiteSpace,scripts);
		}
		else if (component instanceof Container)
		{
			Container container = (Container) component;
			
			html = containerToHtml(container,cssEntries,prefixWhiteSpace,scripts,true);
				
		}


		
		return html;
	}
	
	public static CSS getStyle(Component component, HashMap<String, CSS> cssEntries)
	{
		return getStyle(component,cssEntries,null);
	}
	
	public static CSS getStyle(Component component, HashMap<String, CSS> cssEntries, HashMap<Style, String> extraStyles)
	{
		String style = "";

		Component curr = component;
		
		if (extraStyles==null || extraStyles.get(Style.BACKGROUND)==null)
		{
			int background=defaultBackground;
			while (curr!=null && !(curr instanceof NonInhieratibleBackground) && background==defaultBackground)
			{
				background=curr.getBackground().getRGB();
				curr=curr.getParent();
			}
			
			if (background!=defaultBackground)
			{
				style+="background: #"+String.format("%06X", background & 0xFFFFFF)+";\n";
			}
		}
		else
		{
			style+=	extraStyles.get(Style.BACKGROUND)+";\n";
		}
		
		curr = component;
		int foreground=defaultForeground;
		while (curr!=null && foreground==defaultForeground)
		{
			foreground=curr.getForeground().getRGB();
			curr=curr.getParent();
		}
		
		curr = component;
		int fontSize=defaultFont.getSize();
		while (curr!=null && fontSize==defaultFont.getSize())
		{
			fontSize=curr.getFont().getSize();
			curr=curr.getParent();
		}
 
		if (foreground!=defaultForeground)
		{
			style+="color: #"+String.format("%06X", foreground & 0xFFFFFF)+";\n";
		}
		
		if (fontSize!=defaultFont.getSize())
		{
			style+="font-size:"+fontSize+"px;\n";
		}
		
//		if (!(component instanceof JPanel))
//		{
		
		if (extraStyles==null || extraStyles.get(Style.WIDTH)==null)
		{
			style+="width: "+component.getSize().getWidth()+"px;\n";
		}
		else
		{
			style+=	extraStyles.get(Style.WIDTH)+";\n";
		}
		
		if (extraStyles==null || extraStyles.get(Style.HEIGHT)==null)
		{
			style+="height: "+component.getSize().getHeight()+"px;\n";
		}
		else
		{
			style+=	extraStyles.get(Style.HEIGHT)+";\n";
		}
//		}
		
		CSS entry = null;
		if (style.length()>0)
		{
			entry = cssEntries.get(style);
			if (entry ==null)
			{
				entry = new CSS();
				entry.className="C"+cssEntries.size();
				entry.style=style;
				cssEntries.put(entry.style,entry);
			}
		}
		return entry;
	}
	
//	static CSS getStyle(Component component, HashMap<String, CSS> cssEntries, Integer ... fudgeFactors)
//	{
//		String style = "";
//
//		Component curr = component;
//		int background=defaultBackground;
//		while (curr!=null && !(curr instanceof NonInhieratibleBackground) && background==defaultBackground)
//		{
//			background=curr.getBackground().getRGB();
//			curr=curr.getParent();
//		}
//		
//		curr = component;
//		int foreground=defaultForeground;
//		while (curr!=null && foreground==defaultForeground)
//		{
//			foreground=curr.getForeground().getRGB();
//			curr=curr.getParent();
//		}
//		
//		curr = component;
//		int fontSize=defaultFont.getSize();
//		while (curr!=null && fontSize==defaultFont.getSize())
//		{
//			fontSize=curr.getFont().getSize();
//			curr=curr.getParent();
//		}
// 
//		if (background!=defaultBackground)
//		{
//			style+="background: #"+String.format("%06X", background & 0xFFFFFF)+";\n";
//		}
//		
//		if (foreground!=defaultForeground)
//		{
//			style+="color: #"+String.format("%06X", foreground & 0xFFFFFF)+";\n";
//		}
//		
//		if (fontSize!=defaultFont.getSize())
//		{
//			style+="font-size:"+fontSize+"px;\n";
//		}
//		
////		if (!(component instanceof JPanel))
////		{
//			style+="width: "+(component.getSize().getWidth()+ (fudgeFactors.length>0?fudgeFactors[0]:0))+"px;\n";
//			style+="height: "+(component.getSize().getHeight()+ (fudgeFactors.length>1?fudgeFactors[1]:0))+"px;\n";
////		}
//		
//		CSS entry = null;
//		if (style.length()>0)
//		{
//			entry = cssEntries.get(style);
//			if (entry ==null)
//			{
//				entry = new CSS();
//				entry.className="C"+cssEntries.size();
//				entry.style=style;
//				cssEntries.put(entry.style,entry);
//			}
//		}
//		return entry;
//	}

	public static String getID(Component component)
	{
		return component.getName()!=null?component.getName():""+component.hashCode();
	}
	

	public static String containerToHtml(Container container, HashMap<String, CSS> cssEntries,String prefixWhiteSpace,HashMap<String, List<String>> scripts, boolean computeStyle)
	{
		String html;
		
		CSS style = null;
		
		if (computeStyle) style=getStyle(container, cssEntries);
		
		if (container.getLayout() instanceof BorderLayout)
		{
			html=HTMLBorderLayout.toHtml((BorderLayout) container.getLayout(),style,cssEntries,prefixWhiteSpace,scripts);
		}
		else if (container.getLayout() instanceof FlowLayout )
		{
			html=HTMLFlowLayout.toHtml(container,style,cssEntries,prefixWhiteSpace,scripts);
		}
		else if (container.getLayout() instanceof GridLayout )
		{
			html=HTMLGridLayout.toHtml(container,style,cssEntries,prefixWhiteSpace,scripts);
		}
		else
		{
			System.out.println("UNKNOWN Layout: "+container.getLayout().getClass().getSimpleName());
			html= toHtml(container.getComponent(0),cssEntries,prefixWhiteSpace,scripts);
			
		}
		return html;
	}
	
	public static String stringToHTMLString(String string) {
	    StringBuffer sb = new StringBuffer(string.length());
	    // true if last char was blank
	    boolean lastWasBlankChar = false;
	    int len = string.length();
	    char c;

	    for (int i = 0; i < len; i++)
	        {
	        c = string.charAt(i);
	        if (c == ' ') {
	            // blank gets extra work,
	            // this solves the problem you get if you replace all
	            // blanks with &nbsp;, if you do that you loss 
	            // word breaking
	            if (lastWasBlankChar) {
	                lastWasBlankChar = false;
	                sb.append("&nbsp;");
	                }
	            else {
	                lastWasBlankChar = true;
	                sb.append(' ');
	                }
	            }
	        else {
	            lastWasBlankChar = false;
	            //
	            // HTML Special Chars
	            if (c == '"')
	                sb.append("&quot;");
	            else if (c == '&')
	                sb.append("&amp;");
	            else if (c == '<')
	                sb.append("&lt;");
	            else if (c == '>')
	                sb.append("&gt;");
	            else if (c == '\n')
	                // Handle Newline
	                sb.append("&lt;br/&gt;");
	            else {
	                int ci = 0xffff & c;
	                if (ci < 160 )
	                    // nothing special only 7 Bit
	                    sb.append(c);
	                else {
	                    // Not 7 Bit use the unicode system
	                    sb.append("&#");
	                    sb.append(new Integer(ci).toString());
	                    sb.append(';');
	                    }
	                }
	            }
	        }
	    return sb.toString();
	}
}
