import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;

public class HTMLMain {
	
	static String htmlTemplate;
//	static CSS cssTemplate;
//	
	static
	{
		try
		{
			StringBuilder sb= new StringBuilder();
			Files.readAllLines(Paths.get("data/HTMLMain.template")).stream().forEach(line->sb.append("*START*"+line+"\n"));//,
			
			htmlTemplate=sb.toString();
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	static String toHtml(Component component)
	{
		
		
		HashMap<String, CSS> cssEntries = new HashMap<>();
		
		
		String defaultStyle="";
		

//		defaultStyle+="background: #"+Integer.toHexString(Swing2HTML.defaultBackground&0xFFFFFF)+";\n";
		defaultStyle+="color: #"+Integer.toHexString(Swing2HTML.defaultForeground&0xFFFFFF)+";\n";
		defaultStyle+="font-size:"+Swing2HTML.defaultFont.getSize()+"px;\n";
		defaultStyle+="font-family: \""+component.getFont().getFamily()+"\";\n";
		
//		defaultStyle+="width: "+component.getWidth()+"px;\n";
//		defaultStyle+="height: "+component.getWidth()+"px;\n";

		CSS defaults = new CSS();
		defaults.style=defaultStyle;
		defaults.className="defaults";
		
		cssEntries.put(defaults.style,defaults);

		
		String componentHTML= Swing2HTML.toHtml(component, cssEntries, "      ");
		
		StringBuilder sb= new StringBuilder();
		cssEntries.values().stream().forEach(css->sb.append(css.raw!=null?css.raw:("."+css.className+"\n{"+css.style+"}\n\n")));

		String html = htmlTemplate.replace("*START*", "");
		html = html.replace("*CSS*", sb.toString());
		html = html.replace("*HTML*", componentHTML);
	
		return html;
	}
}
