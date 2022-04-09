package view;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 446472514249432753L;

	public ImagePanel(String filepath) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel label = new JLabel(scaledImage(filepath, 60, 60));
		add(label);
	}
	
	private static ImageIcon scaledImage(String filepath, int x, int y) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(filepath));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image dimg = img.getScaledInstance(x, y,
		        Image.SCALE_DEFAULT);
		
		return new ImageIcon(dimg);
	}
}
