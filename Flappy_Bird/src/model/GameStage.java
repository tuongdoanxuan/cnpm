package model;

import java.util.ArrayList;
import java.util.List;

public abstract class GameStage {
	private Bird bird;
	private GameStatus gameStatus;
	private List<GameObject> gameObjects;
	private Enviroment enviroment;

	public GameStage(Bird bird, GameStatus gameStatus, Enviroment enviroment) {
		this.bird = bird;
		this.gameStatus = gameStatus;
		this.gameObjects = new ArrayList<>();
		this.enviroment = enviroment;
	}

	public void update() {
		if (gameStatus.getState() == GameStatus.GameState.WAITING_TO_START) {
			// Chim dao động nhẹ khi chờ bắt đầu
			bird.idleMotion();
			return; // Không cập nhật gameObjects khi đang chờ
		}

		if (gameStatus.isGameOver())
			return;

		/*
		 * UC-11.1.3: Lấy/cập nhật vị trí hiện tại của chim trong quá trình chơi.
		 */
		bird.update();

		/*
		 * UC-11.1.4: Cập nhật và lấy thông tin các ống/chướng ngại vật đang hoạt động.
		 */
		for (GameObject obj : gameObjects) {
			obj.update();
			obj.setX(obj.getX() + (int) enviroment.getGroundSpeed());
		}

		// Kiểm tra va chạm và tính điểm
		for (GameObject obj : gameObjects) {
			if (obj.collidesWith(bird)) {
				gameStatus.setGameOver(true);
				return;
			}
			
			/*
			 * UC-11.1.5: Kiểm tra chim đã vượt qua ống hay chưa.
			 * Điều kiện bird.getX() > obj.getX() + obj.getWidth() cho biết chim đã đi qua khỏi ống.
			 * 
			 * UC-11.1.6: Kiểm tra ống đã được tính điểm chưa.
			 * Điều kiện !obj.getPassed() đảm bảo mỗi ống chỉ được cộng điểm một lần.
			 */

			if (!obj.getPassed() && bird.getX() > obj.getX() + obj.getWidth()) {
				
				/*
				 * UC-11.1.7: Tăng điểm cho người chơi khi chim vượt qua ống hợp lệ.
				 */
				gameStatus.incrementScore(0.5);
				
				/*
				 * UC-11.1.8: Đánh dấu ống đã được tính điểm để tránh cộng điểm trùng.
				 */
				obj.setPassed(true);
			}
			
			/*
			 * UC-11.2: Nếu chim chưa vượt qua ống, điều kiện bird.getX() > obj.getX() + obj.getWidth() sai,
			 * hệ thống không tăng điểm và điểm hiện tại được giữ nguyên.
			 * 
			 * UC-11.3: Nếu ống đã được tính điểm trước đó, obj.getPassed() == true,
			 * hệ thống bỏ qua thao tác tăng điểm để tránh cộng điểm trùng.
			 */
		}
		if (shouldAddObstacle()) {
			createObstacles();
		}

		if (bird.getY() + bird.getHeight() >= GameConfig.BOARD_HEIGHT) {
			gameStatus.setGameOver(true);
		}

		gameObjects.removeIf(obj -> obj.getX() + obj.getWidth() < 0);
	}

	// Getter và Setter
	public Bird getBird() {
		return bird;
	}

	public void setBird(Bird bird) {
		this.bird = bird;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(List<GameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}

	public Enviroment getEnviroment() {
		return enviroment;
	}

	public void setEnviroment(Enviroment enviroment) {
		this.enviroment = enviroment;
	}

	public void birdJump() {
		bird.jump();
	}

	public void resetGame(int boardHeight) {
		bird.reset(boardHeight);
		gameObjects.clear(); // Xóa tất cả đối tượng
		gameStatus.reset();

		// Thiết lập lại màn chơi sau khi reset
		setupStage();
	}

	public final void setupStage() {
		setupEnvironment();
		createObstacles();
		setupBird();
	}

	// Phương thức trừu tượng cho các lớp con
	public abstract void setupEnvironment();

	public abstract void createObstacles();

	public abstract void setupBird();

	// Phương thức hỗ trợ tạo cặp ống
	public void addPipePair(int x, boolean isMoving) {
		int openingSpace = getOpeningSpace(); // Các lớp con xác định khoảng trống
		int pipeWidth = 64;
		int pipeHeight = 512;
		int randomPipeY = (int) (-pipeHeight / 4 - Math.random() * (pipeHeight / 2));

		GameObject topPipe, bottomPipe;

		if (isMoving) {
			// Cặp ống di chuyển
			topPipe = new MovingPipe(x, randomPipeY, pipeWidth, pipeHeight, ImageAssets.topPipeImg, Pipe.TYPE_TOP_HARD);
			bottomPipe = new MovingPipe(x, randomPipeY + pipeHeight + openingSpace, pipeWidth, pipeHeight,
					ImageAssets.bottomPipeImg, Pipe.TYPE_BOTTOM_HARD);
		} else {
			// Cặp ống tĩnh
			topPipe = new Pipe(x, randomPipeY, pipeWidth, pipeHeight, ImageAssets.topPipeImg);
			((Pipe) topPipe).setType(Pipe.TYPE_TOP);

			bottomPipe = new Pipe(x, randomPipeY + pipeHeight + openingSpace, pipeWidth, pipeHeight,
					ImageAssets.bottomPipeImg);
			((Pipe) bottomPipe).setType(Pipe.TYPE_BOTTOM);
		}

		gameObjects.add(topPipe);
		gameObjects.add(bottomPipe);
	}

	// Phương thức trừu tượng xác định khoảng trống - các lớp con định nghĩa độ khó
	protected abstract int getOpeningSpace();

	// Phương thức thêm đạn (dùng trong chế độ Asian)
	protected void addBullet(int x, int y) {
		if (ImageAssets.bulletImg != null) {
			Bullet bullet = new Bullet(x, y, 80, 30, ImageAssets.bulletImg, -6);
			gameObjects.add(bullet);
		}
	}
	

	// Kiểm tra có cần thêm chướng ngại vật không
	protected boolean shouldAddObstacle() {
	    if (gameObjects.isEmpty()) {
	        return true;
	    }

	    // Tìm đối tượng ngoài cùng bên phải bằng vòng lặp truyền thống
	    int rightmostX = 0;
	    for (GameObject obj : gameObjects) {
	        if (obj.getX() > rightmostX) {
	            rightmostX = obj.getX();
	        }
	    }

	    return rightmostX < GameConfig.BOARD_WIDTH - getObstacleSpacing();
	}

	// Các lớp con định nghĩa khoảng cách giữa chướng ngại vật
	protected abstract int getObstacleSpacing();
}