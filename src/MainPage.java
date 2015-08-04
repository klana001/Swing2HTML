import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;


public class MainPage extends JFrame
{
	public MainPage() {
		setSize(new Dimension(800, 600));
		setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		WebsiteTemplate websiteTemplate = new WebsiteTemplate();
		getContentPane().add(websiteTemplate, BorderLayout.CENTER);
	}

}
