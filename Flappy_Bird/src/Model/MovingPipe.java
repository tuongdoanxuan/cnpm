package Model;

import java.awt.Graphics;
import java.awt.Image;

public class MovingPipe extends Pipe {
    private int dealtY = 0;
    private int direction = DIR_DOWN;
    private static final int MAX_DELTA = 50;
    private static final int DIR_UP = 0;
    private static final int DIR_DOWN = 1;

    public MovingPipe(int x, int y, int width, int height, Image img, int type) {
        super(x, y, width, height, img);
        this.type = type;
        this.direction = (type == TYPE_TOP_HARD) ? DIR_UP : DIR_DOWN;
    }

    @Override
    public void update() {
        super.update(); // dịch trái
        if (!visible) return;

        // chuyển động lên xuống
        if (direction == DIR_DOWN) {
            dealtY++;
            if (dealtY > MAX_DELTA) direction = DIR_UP;
        } else {
            dealtY--;
            if (dealtY <= 0) direction = DIR_DOWN;
        }

        y += (direction == DIR_DOWN) ? 1 : -1;
    }

    @Override
    public void draw(Graphics g) {
        if (visible && img != null) {
            g.drawImage(img, x, y + dealtY, width, height, null);
        }
    }

    @Override
    public void reset() {
        super.reset();
        dealtY = 0;
        direction = (type == TYPE_TOP_HARD) ? DIR_UP : DIR_DOWN;
    }
}
