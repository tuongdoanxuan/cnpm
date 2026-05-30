package model;

package model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Bird {
    private int x, y; // tọa độ của chim
    private int width, height; // Kích thước chim (rộng & cao), dùng để vẽ và va chạm (collision)
    private int velocityY; // Tốc độ rơi theo trục Y (dương: rơi xuống, âm: bay lên)
    private double gravity;
    private double jumpStrength;

    private double angle = 0; // góc nghiêng
    private Image image;
    private double idleAngle = 0; // Góc dao động (góc chuyển động sin)

    // Gọi khi chim đang chờ bắt đầu
    public void idleMotion() {
        idleAngle += 0.1;
        this.setY(y + (int) (5 * Math.sin(idleAngle))); // chuyển động lặp lại (dao động).
    }

    public Bird(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setJumpStrength(double jumpStrength) {
        this.jumpStrength = -Math.abs(jumpStrength); // đảm bảo là số âm
    }

    // Đặt lại vị trí ban đầu và vận tốc khi reset game
    public void reset(int boardHeight) {
        y = boardHeight / 2; // hoặc vị trí y khởi đầu
        velocityY = 0;
        angle = 0;
    }

    public void jump() {
        velocityY = (int) jumpStrength;
    }

    public void update() {
        velocityY += gravity;
        y += velocityY;

        // Cập nhật góc nghiêng
        angle = Math.toDegrees(Math.atan2(velocityY, 10.0));
        if (angle < -30)
            angle = -5;
        if (angle > 90)
            angle = 65;

        if (y < 0) {
            y = 0;
            velocityY = 0;
        }
    }

    // Vẽ chim ra màn hình
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform original = g2d.getTransform();

        // Tính vị trí tâm để xoay quanh giữa hình con chim
        int centerX = x + width / 2;
        int centerY = y + height / 2;

        // Xoay và vẽ hình ảnh
        g2d.rotate(Math.toRadians(angle), centerX, centerY);
        g2d.drawImage(image, x, y, width, height, null);

        // Khôi phục lại transform ban đầu
        g2d.setTransform(original);
    }

    // Getter & Setter cần thiết
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImg() {
        return image;
    }

    public void setY(int y) {
        this.y = y;
    }
}
