package Model;

import java.awt.Image;

public abstract class GameObject {
	protected int x, y, width, height;
	protected Image img;
	protected boolean passed;
	protected boolean visible = true;


	public GameObject(int x, int y, int width, int height, Image img) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;
		this.passed = false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	public boolean getPassed() {
		return passed;
	}
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	public abstract void update(); // cập nhật mỗi frame

	public abstract void reset(); // reset trạng thái về ban đầu

	// [UC-04: Play Game] - Bước 3: checkCollision() - Hệ thống kiểm tra va chạm bằng so sánh diện tích (Bounding Box / Hitbox) của Bird với Pipe/mặt đất
	// [Sequence Diagram: Play Game] - Bước 3: getBounds() để lấy birdHitbox và pipeHitbox từ Bird/Pipe để CollisionDetector đối sánh
	public boolean collidesWith(Bird bird) {
	    // Hitbox của Bird có khoảng đệm 4px (ngang) và 3px (dọc) để tránh va chạm ảo ở rìa ảnh trong suốt
	    int birdPaddingX = 4;
	    int birdPaddingY = 3;
	    int birdHitboxX = bird.getX() + birdPaddingX;
	    int birdHitboxY = bird.getY() + birdPaddingY;
	    int birdHitboxW = bird.getWidth() - 2 * birdPaddingX;
	    int birdHitboxH = bird.getHeight() - 2 * birdPaddingY;

	    // Hitbox của vật cản (ống nước) có khoảng đệm 6px mỗi bên ngang để khớp với thân ống thực tế (hẹp hơn viền ngoài của miệng ống)
	    int objPaddingX = 6;
	    int objHitboxX = this.x + objPaddingX;
	    int objHitboxW = this.width - 2 * objPaddingX;

	    int objHitboxY = this.y;
	    int objHitboxH = this.height;

	    if (this instanceof Pipe) {
	        Pipe pipe = (Pipe) this;
	        int type = pipe.getType();
	        int pipePaddingY = 3; // Co nhẹ 3px ở phần miệng ống hở
	        if (type == Pipe.TYPE_TOP || type == Pipe.TYPE_TOP_HARD) {
	            // Ống trên: co biên dưới lên
	            objHitboxH = this.height - pipePaddingY;
	        } else if (type == Pipe.TYPE_BOTTOM || type == Pipe.TYPE_BOTTOM_HARD) {
	            // Ống dưới: co biên trên xuống
	            objHitboxY = this.y + pipePaddingY;
	            objHitboxH = this.height - pipePaddingY;
	        }
	    } else if (this instanceof Bullet) {
	        // Đối với đạn (Bullet): Co nhỏ cả 2 phương để khớp với hình viên đạn tròn nhỏ
	        int bulletPaddingY = 3;
	        objHitboxY = this.y + bulletPaddingY;
	        objHitboxH = this.height - 2 * bulletPaddingY;
	    }

	    return birdHitboxX + birdHitboxW > objHitboxX &&
	           birdHitboxX < objHitboxX + objHitboxW &&
	           birdHitboxY + birdHitboxH > objHitboxY &&
	           birdHitboxY < objHitboxY + objHitboxH;
	}

}
