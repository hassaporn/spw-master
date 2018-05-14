package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceShip extends Sprite{

	int step = 8;
	int colors;
	
	public SpaceShip(int x, int y, int width, int height, int colors) {
		super(x, y, width, height);
		this.colors = colors;
		
	}

	@Override
	public void draw(Graphics2D g) {
		if (colors == 4)
			g.setColor(Color.GREEN);
		else if(colors == 3)
			g.setColor(Color.BLUE);
		else if(colors == 5)
			g.setColor(Color.YELLOW);
		g.fillRect(x, y, width, height);
		
	}

	public void move(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}

}
