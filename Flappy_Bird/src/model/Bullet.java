package Model;

import java.awt.Graphics;
import java.awt.Image;

public class Bullet extends GameObject {
    private int speedX;

    public Bullet(int x, int y, int width, int height, Image img, int speedX) {
        super(x, y, width, height, img);
        this.speedX = speedX;
    }

    @Override
    public void update() {
        x += speedX;
    }

    public void draw(Graphics g) {
        if (visible && img != null) {
            g.drawImage(img, x, y, width, height, null);
        }
    }

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
