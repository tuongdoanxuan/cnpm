package model;

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

	public boolean collidesWith(Bird bird) {
	    return bird.getX() + bird.getWidth() > this.x &&
	           bird.getX() < this.x + this.width &&
	           bird.getY() + bird.getHeight() > this.y &&
	           bird.getY() < this.y + this.height;
	}

}
