import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Dimension;


public class WebsiteTemplate extends JPanel
{

	/**
	 * Create the panel.
	 */
	public WebsiteTemplate()
	{
		setFont(new Font("Tahoma", Font.PLAIN, 11));
		setBackground(Color.DARK_GRAY);
		setLayout(new BorderLayout(0, 0));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.DARK_GRAY);
		add(leftPanel, BorderLayout.WEST);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.DARK_GRAY);
		add(rightPanel, BorderLayout.EAST);
		
		JPanel mainCentrePanel = new JPanel();
		mainCentrePanel.setBackground(Color.DARK_GRAY);
		add(mainCentrePanel, BorderLayout.CENTER);
		mainCentrePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.DARK_GRAY);
		mainCentrePanel.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));
		
		RoundedBackgroundBoxPanel topRoundedBackgroundBoxPanel = new RoundedBackgroundBoxPanel();
		topRoundedBackgroundBoxPanel.setBackground(Color.WHITE);
		topRoundedBackgroundBoxPanel.setBackgroundBox(0, 20, 40);
		topRoundedBackgroundBoxPanel.setLayout(new BorderLayout(0,0));
		
		
		TiledBackGroundJPanel tiledBackGroundJPanel = new TiledBackGroundJPanel();
		tiledBackGroundJPanel.setTileImage("pics/tree.png");
		
		topRoundedBackgroundBoxPanel.add(tiledBackGroundJPanel,BorderLayout.CENTER);
		topPanel.add(topRoundedBackgroundBoxPanel, BorderLayout.SOUTH);
		
		JLabel label = new JLabel("TILED IMAGES GO HERE!");
		tiledBackGroundJPanel.add(label);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Tahoma", Font.PLAIN, 29));
		
		JPanel topNavButtonPanel = new JPanel();
		topNavButtonPanel.setBackground(Color.DARK_GRAY);
		topPanel.add(topNavButtonPanel, BorderLayout.NORTH);
		topNavButtonPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		topNavButtonPanel.add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblH = new JLabel("Home");
		lblH.setForeground(Color.WHITE);
		lblH.setBackground(Color.DARK_GRAY);
		panel_1.add(lblH);
		
		JLabel lblNewLabel_1 = new JLabel("Shopping cart");
		lblNewLabel_1.setForeground(Color.WHITE);
		panel_1.add(lblNewLabel_1);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.DARK_GRAY);
		mainCentrePanel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel bottomCopyRightTextPanel = new JPanel();
		bottomCopyRightTextPanel.setBackground(Color.DARK_GRAY);
		bottomPanel.add(bottomCopyRightTextPanel, BorderLayout.SOUTH);
		
		JLabel lblCopyrightYourSite = new JLabel("Copyright Your Site Name :: All Rights reserved :: Design by blah blah");
		lblCopyrightYourSite.setForeground(Color.WHITE);
		bottomCopyRightTextPanel.add(lblCopyrightYourSite);
		
//		JPanel bottomTilePanel = new JPanel();
//		bottomTilePanel.setBackground(Color.DARK_GRAY);
//		bottomPanel.add(bottomTilePanel, BorderLayout.NORTH);
		
		
		RoundedBackgroundBoxPanel bottomRoundedBackgroundBoxPanel = new RoundedBackgroundBoxPanel();
		bottomRoundedBackgroundBoxPanel.setBackground(Color.WHITE);
		bottomRoundedBackgroundBoxPanel.setBackgroundBox(0, -20, 40);
		bottomRoundedBackgroundBoxPanel.setLayout(new BorderLayout(0,0));
		
		
		TiledBackGroundJPanel bottomtiledBackGroundJPanel = new TiledBackGroundJPanel();
		bottomtiledBackGroundJPanel.setTileImage("pics/tree.png");
		
		bottomRoundedBackgroundBoxPanel.add(bottomtiledBackGroundJPanel,BorderLayout.CENTER);
		bottomPanel.add(bottomRoundedBackgroundBoxPanel, BorderLayout.NORTH);		
		
		
		JLabel lblNewLabel = new JLabel("TILED IMAGES GO HERE!");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 29));
		bottomtiledBackGroundJPanel.add(lblNewLabel);
		
		JPanel mainCentreCentrePanel = new JPanel();
		mainCentreCentrePanel.setBackground(Color.WHITE);
		mainCentreCentrePanel.setSize(new Dimension(700, 500));
		mainCentrePanel.add(mainCentreCentrePanel, BorderLayout.CENTER);
		mainCentreCentrePanel.setLayout(new BorderLayout(0, 0));

	}

}
