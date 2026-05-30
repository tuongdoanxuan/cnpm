package model;

public class Enviroment {
	private double gravity, groundSpeed;
	private boolean isDay;
	private double jumpStrength;
	public Enviroment(double gravity, double groundSpeed, boolean isDay, double jumpStrength) {
		super();
		this.gravity = gravity; //Gia tốc trọng lực
		this.groundSpeed = groundSpeed; //tốc độ cuộn màn hình
		this.isDay = isDay; //để chuyển đổi giữa ngày/đêm
		this.jumpStrength = jumpStrength;//Độ mạnh của cú nhảy
	}
	
	public Enviroment() {
	}

	public void updateTimeOfDay() {
		isDay = !isDay; // Chuyển đổi ngày <-> đêm
	}

	public void reset() {
		gravity = 1.0;
		groundSpeed = 4.0;
		isDay = true;
		jumpStrength = 10.0;
	}

	// Getter & Setter
	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	public double getGroundSpeed() {
		return groundSpeed;
	}

	public void setGroundSpeed(double groundSpeed) {
		this.groundSpeed = groundSpeed;
	}

	public boolean isDay() {
		return isDay;
	}

	public void setDay(boolean isDay) {
		this.isDay = isDay;
	}

	public double getJumpStrength() {
		return jumpStrength;
	}

	public void setJumpStrength(double jumpStrength) {
		this.jumpStrength = jumpStrength;
	}
}
