import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

public class RoundedBackgroundBoxPanel extends JPanel implements NonInhieratibleBackground , ToHTML
{
	private static final String ROUNDED_RECT = "ROUNDED_RECT";
	static String htmlTemplate;
	static String scriptTemplate;
	static String loadedScriptTemplate;

	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			Files.readAllLines(Paths.get("data/HTMLRoundedBackgroundBoxPanel.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlTemplate=sb.toString();
			
			sb.setLength(0);
			Files.readAllLines(Paths.get("data/HTMLRoundedBackgroundBoxPanelScript.template")).stream().forEach(line->sb.append(line+"\n"));//,
			
			scriptTemplate=sb.toString();
			
			sb.setLength(0);
			Files.readAllLines(Paths.get("data/HTMLRoundedBackgroundBoxPanelLoadedScript.template")).stream().forEach(line->sb.append(line+"\n"));//,
			
			loadedScriptTemplate=sb.toString();

		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	private int offsetX;
	private int offsetY;
	private int widthOffset;
	private int heightOffset;
	private double radius;




	public RoundedBackgroundBoxPanel()
	{

	}
	
	public void setBackgroundBox(int offsetX, int offsetY, double radius)
	{
		this.offsetX=offsetX;
		this.offsetY=offsetY;
		this.radius=radius;
	}
	
	
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Shape shape = new RoundRectangle2D.Double(offsetX,offsetY,getWidth(),getHeight(),radius,radius);
        g2d.setColor(getBackground());
        g2d.fill(shape);
        super.paint(g);
    }
	

//    @Override
//    protected void paintComponent(Graphics g) {
////        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        Shape shape = new RoundRectangle2D.Double(offsetX,offsetY,getWidth(),getHeight(),radius,radius);
//        g2d.setColor(getBackground());
//        g2d.fill(shape);
//    }

	@Override
	public String toHtml(HashMap<String, CSS> cssEntries, String prefixWhiteSpace, HashMap<String, List<String>> scripts) {
		
		List<String> roundScripts =  scripts.get(ROUNDED_RECT);
		if (roundScripts==null)
		{
			roundScripts= new ArrayList<String>();
			roundScripts.add(scriptTemplate);
			scripts.put(ROUNDED_RECT,roundScripts);
		}
		
		List<String> loadedScripts =  scripts.get(HTMLMain.LOADED);
		if (loadedScripts==null)
		{
			loadedScripts= new ArrayList<String>();
			scripts.put(HTMLMain.LOADED,loadedScripts);
		}
		
		CSS css= new CSS();

//		String colourString= "rgba("+getBackground().getRed()+","+getBackground().getGreen()+","+getBackground().getBlue()+",1)";
//		String shadowString= "inset "+offsetX+"px "+offsetY+"px "+colourString+";\n";
//		css.style=	"box-shadow: "+shadowString+
////					"-moz-box-shadow: "+shadowString+
////					"-webkit-box-shadow: "+shadowString+
////					"-o-box-shadow: "+shadowString+
//					"border-radius:"+radius+"px;\n";
////					"background: initial;\n";
		
		String id = "roundedBackGroundBoxPanel"+this.hashCode();
		
		css.raw = "#"+id+"-wrap { position:relative; width:"+getWidth()+"px; height:"+getHeight()+"px   }\n"+
				  "#"+id+"-wrap canvas { position:absolute; top:0; left:0; z-index:-1; }\n";
//				  "#"+id+"-wrap div { z-index:1 }\n";
		css.style = css.raw; 
		
//		css.style=	"border-radius: 25px;\n"+
//					"background-position: "+offsetX+"px "+offsetY+"px;\n" +
//					"background: #"+String.format("%06X", getBackground().getRGB() & 0xFFFFFF)+"\n";
//		
		
		
		css.className="roundedBackGroundBoxPanel"+css.style.hashCode();
		cssEntries.put(css.style,css);
//	    background-image: url("paper.gif");
//	    background-repeat: repeat-y;
		prefixWhiteSpace+="  ";
		
		String html = htmlTemplate.replace("*START*", prefixWhiteSpace);
//		html = html.replace("*CLASSES*", css.className);
		
		html = html.replace("*ID*",id);
		html = html.replace("*WIDTH*",""+getWidth()+"px");
		html = html.replace("*HEIGHT*",""+getHeight()+"px");
		
		String loadedScript = loadedScriptTemplate;
		loadedScript = loadedScript.replace("*ID*",id);
		loadedScript = loadedScript.replace("*WIDTH*",""+(getWidth()));
		loadedScript = loadedScript.replace("*HEIGHT*",""+getHeight());
		loadedScript = loadedScript.replace("*X_OFFSET*",""+(offsetX));
		loadedScript = loadedScript.replace("*Y_OFFSET*",""+offsetY);
		loadedScript = loadedScript.replace("*RADIUS*",""+radius);
		loadedScript = loadedScript.replace("*BACK_GROUND*",String.format("%06X", getBackground().getRGB() & 0xFFFFFF));
		loadedScripts.add(loadedScript);
		

		String  innerHtml = Swing2HTML.containerToHtml(this, cssEntries, prefixWhiteSpace,scripts, true);
		
		html = html.replace("*INNER_PANELS*", innerHtml);

		return html;
	}
}
