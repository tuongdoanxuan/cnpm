package model;

import java.util.Random;

public class AsianStage extends GameStage {

	private Random random;
	private int bulletThresholdScore;

	public AsianStage(Bird bird, GameStatus gameStatus, Enviroment enviroment) {
		super(bird, gameStatus, enviroment);
		this.random = new Random();
		this.bulletThresholdScore = 2;
	}

	@Override
	public void setupEnvironment() {
		// Chế độ châu Á
		getEnviroment().setGravity(1.1);
		getEnviroment().setGroundSpeed(-5.0); // Tốc độ ống di chuyển nhanh hơn
		getEnviroment().setJumpStrength(12.0); // Lực nhảy yếu hơn một chút
		getEnviroment().setDay(true); // Sẽ thay đổi động
	}

	@Override
	public void setupBird() {
		getBird().setGravity(getEnviroment().getGravity());
		getBird().setJumpStrength(getEnviroment().getJumpStrength());
	}

	@Override
	public void createObstacles() {
		// Tạo chướng ngại vật cho chế độ châu Á
		int initialX = GameConfig.BOARD_WIDTH;

		// 50% cơ hội cho ống di chuyển trong chế độ châu Á
		boolean useMovingPipe = random.nextBoolean();
		addPipePair(initialX, useMovingPipe);

		// Thêm đạn nếu đạt ngưỡng điểm
		if (getGameStatus().getScore() >= bulletThresholdScore && random.nextDouble() < 0.5) {
			int bulletY = getBird().getY() + getBird().getHeight() / 2;
			addBullet(GameConfig.BOARD_WIDTH, bulletY);
		}
	}

	@Override
	protected int getOpeningSpace() {
		// TODO Auto-generated method stub
		return GameConfig.BOARD_HEIGHT / 4;

	}

	@Override
	protected int getObstacleSpacing() {
		// TODO Auto-generated method stub
		return 250;
	}

	public void update() {
		super.update();
		// Thêm chướng ngại vật mới khi cần (thường xuyên hơn cổ điển)
		if (shouldAddObstacle()) {
			createObstacles();
		}

		// Chu kỳ ngày/đêm động dựa trên điểm số
		if (getGameStatus().getScore() > 0 && (int) getGameStatus().getScore() % 10 == 0) {
			// Chuyển đổi ngày/đêm mỗi 10 điểm
			if ((int) getGameStatus().getScore() % 20 == 0) {
				getEnviroment().setDay(true);
			} else {
				getEnviroment().setDay(false);
			}
		}
		checkBulletCollisions();
	}

	private void checkBulletCollisions() {
		for (GameObject obj : getGameObjects()) {
			if (obj instanceof Bullet && obj.collidesWith(getBird())) {
				// Va chạm đạn - kết thúc game ngay lập tức
				getGameStatus().setGameOver(true);
				break;
			}
		}
	}

	// Ghi đè để xử lý ống di chuyển khác đi nếu cần
	public void addPipePair(int x, boolean isMoving) {
		super.addPipePair(x, isMoving);

		// Trong chế độ châu Á, thỉnh thoảng thêm thử thách phụ
		if (random.nextDouble() < 0.1) { // 10% cơ hội
			// Thêm chướng ngại vật phụ hoặc sửa đổi những cái hiện có
			// Có thể được triển khai dựa trên thiết kế game của bạn
		}
	}
}
