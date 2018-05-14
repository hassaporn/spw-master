package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	Graphics2D big2;
	Graphics2D big3;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big2 = (Graphics2D) bi.getGraphics();
		big3 = (Graphics2D) bi.getGraphics();
		big.setBackground(Color.BLACK);
		big2.setBackground(Color.BLACK);
		big3.setBackground(Color.BLACK);
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		big2.clearRect(0, 0, 400, 600);
		big3.clearRect(0, 0, 400, 600);
		big.setColor(Color.WHITE);
		big2.setColor(Color.WHITE);
		big3.setColor(Color.PINK);
		big.drawString(String.format("%05d", reporter.getScore()), 300, 20);
		big2.drawString(String.format("%05d", reporter.getScore2()), 50, 20);
		big3.drawString(String.format("%05d", reporter.getTotalScore()), 175, 20);
		for(Sprite s : sprites){
			s.draw(big);
			s.draw(big2);
		repaint();
		}
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
