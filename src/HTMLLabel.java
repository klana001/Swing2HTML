import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class HTMLLabel {
	
	static String htmlTemplate;
	static CSS cssTemplate;
	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			Files.readAllLines(Paths.get("data/HTMLLabel.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlTemplate=sb.toString();
			
			sb.setLength(0);
			
			Files.readAllLines(Paths.get("data/HTMLLabelCSS.template")).stream().forEach(line->sb.append(line+"\n"));
			cssTemplate = new CSS();
			cssTemplate.className="HTMLLabelCSS.template";
			cssTemplate.raw = sb.toString();

		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	static String toHtml(JLabel label,HashMap<String, CSS> cssEntries,String prefixWhiteSpace)
	{
		cssEntries.put("HTMLLabelCSS.template",cssTemplate);
		prefixWhiteSpace+="  ";
		
		CSS style = Swing2HTML.getStyle(label,cssEntries);
		
		String html = htmlTemplate.replace("*START*", prefixWhiteSpace);
		html = html.replace("*CLASSES*", style!=null?style.className:"");
		html = html.replace("*TEXT*", label.getText());
		html = html.replace("*ID*", Swing2HTML.getID(label) );
		
		return html;
	}
	
	
	
}
