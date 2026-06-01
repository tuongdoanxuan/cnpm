package model;

import java.awt.Graphics;
import java.awt.Image;

public class Pipe extends GameObject {

    protected int speed = 2;
    protected int type;

    public static final int TYPE_TOP = 0;
    public static final int TYPE_BOTTOM = 1;
    public static final int TYPE_TOP_HARD = 2;
    public static final int TYPE_BOTTOM_HARD = 3;

    public Pipe(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public void update() {
        // Di chuyển pipe sang trái
        x -= speed;
        if (x + width < 0) {
            visible = false;
        }
    }

    @Override
    public void reset() {
        visible = true;
    }

    public void draw(Graphics g) {
        if (visible && img != null) {
            g.drawImage(img, x, y, width, height, null);
        }
    }
}
