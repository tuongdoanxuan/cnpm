package model;

import java.awt.Color;
import java.awt.Graphics;

public class Bird {
    private int x;
    private int y;
    private int width;
    private int height;
    private double velocityY;
    private double gravity;
    private double jumpStrength;
    private boolean alive;
    private int boardHeight;
    private int idleTick;

    public Bird() {
        this(120, 240, 48, 36);
    }

    public Bird(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gravity = 0.8;
        this.jumpStrength = 12.0;
        this.alive = true;
        this.idleTick = 0;
    }

    public void update() {
        if (!alive) {
            return;
        }
        velocityY += gravity;
        y += (int) velocityY;

        if (y < 0) {
            y = 0;
            velocityY = 0;
        }

        if (boardHeight > 0 && y + height >= boardHeight) {
            y = boardHeight - height;
            velocityY = 0;
            alive = false;
        }
    }

    public void jump() {
        if (!alive) {
            return;
        }
        velocityY = -jumpStrength;
    }

    public void idleMotion() {
        idleTick++;
        y += (int) (Math.sin(idleTick * 0.1) * 1.5);
    }

    public void reset(int boardHeight) {
        this.boardHeight = boardHeight;
        this.x = 120;
        this.y = boardHeight / 2;
        this.velocityY = 0;
        this.alive = true;
        this.idleTick = 0;
    }

    public void render(Graphics g) {
        if (g == null) {
            return;
        }
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, width, height);
    }

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

    public double getVelocityY() {
        return velocityY;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setJumpStrength(double jumpStrength) {
        this.jumpStrength = jumpStrength;
    }

    public boolean isAlive() {
        return alive;
    }
}

