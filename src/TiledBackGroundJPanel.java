import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TiledBackGroundJPanel extends JPanel implements ToHTML,NonInhieratibleBackground
{
	static String htmlTemplate;

	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			Files.readAllLines(Paths.get("data/HTMLTiledBackGroundJPanel.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlTemplate=sb.toString();

		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	private Paint paint;
	private BufferedImage tileImage;
	private String tileImagePath;
	
	public TiledBackGroundJPanel()
	{
		paint = getBackground();
		tileImage=null;
	}
	
	public TiledBackGroundJPanel(String image)
	{
		setTileImage(image);
	}
	
	public void setTileImage(String image)
	{
		this.tileImagePath=image;
		try
		{
			tileImage=ImageIO.read(this.getClass().getResourceAsStream(Swing2HTML.RESOURCE_PATH+"/"+image));
			paint = new TexturePaint(tileImage, new Rectangle(tileImage.getWidth(), tileImage.getHeight()));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getTileImagePath()
	{
		return tileImagePath;
	}

    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

	@Override
	public String toHtml(HashMap<String, CSS> cssEntries, String prefixWhiteSpace) {
		CSS css= new CSS();
		css.style="background: url(\""+getTileImagePath()+"\") repeat;\n";
		css.className="repeatBackground"+getTileImagePath().replace("/", "_").replace(".", "_");
		cssEntries.put(css.style,css);
//	    background-image: url("paper.gif");
//	    background-repeat: repeat-y;
		prefixWhiteSpace+="  ";
		
		String html = htmlTemplate.replace("*START*", prefixWhiteSpace);
		html = html.replace("*CLASSES*", css.className);
		

		String  innerHtml = Swing2HTML.containerToHtml(this, cssEntries, prefixWhiteSpace, false);
		
		html = html.replace("*INNER_PANELS*", innerHtml);

		return html;
	}

}
