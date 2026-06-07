package Model;

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

		// [UC-04: Play Game] - Bước 1: Trong mỗi Game Loop, tự động cập nhật vị trí Bird (áp dụng vận tốc/trọng lực làm Bird rơi xuống)
		// [Sequence Diagram: Play Game] - Bước 1a: updatePosition(gravity) gửi từ GamePanel đến Bird để cập nhật tọa độ Y
		bird.update();

		// Tường: Tăng độ khó theo điểm
		int pipeSpeed = 2;

		if (gameStatus.getScore() >= 40) {
		    pipeSpeed = 6;
		} else if (gameStatus.getScore() >= 30) {
		    pipeSpeed = 5;
		} else if (gameStatus.getScore() >= 20) {
		    pipeSpeed = 4;
		} else if (gameStatus.getScore() >= 10) {
		    pipeSpeed = 3;
		}

		// [UC-04: Play Game] - Bước 1: Trong mỗi Game Loop, tự động di chuyển các Pipe sang trái để tạo hiệu ứng bay tới
		// [Sequence Diagram: Play Game] - Bước 1b: updatePosition() gửi từ GamePanel đến PipeManager (gameObjects) để giảm tọa độ X
		for (GameObject obj : gameObjects) {

		    if (obj instanceof Pipe) {
		        ((Pipe) obj).setSpeed(pipeSpeed);
		    }

		    obj.update();
		    obj.setX(obj.getX() + (int) enviroment.getGroundSpeed());
		}

		// Duyệt danh sách kiểm tra va chạm và tính điểm
		for (GameObject obj : gameObjects) {
			// [UC-04: Play Game] - Bước 3: Hệ thống kiểm tra va chạm (checkCollision) giữa Bird với Pipe
			// [Sequence Diagram: Play Game] - Bước 3: checkCollision(Bird, Pipe) gửi từ GamePanel đến CollisionDetector để so sánh hitbox
			if (obj.collidesWith(bird)) {
				// Cân chỉnh vị trí tiếp xúc biên (Collision Resolution) để tránh tình trạng chim đè lên ống khi trò chơi dừng lại
				resolveCollision(bird, obj);
				// [UC-04: Play Game] - Bước 4.1: Nếu có va chạm, CollisionDetector báo hiệu setGameOver(true), dừng Game Loop và hiển thị Game Over
				// [Sequence Diagram: Play Game] - Bước 4.1: CollisionDetector trả về true -> Gọi setGameOver(true) đến GameStatus
				gameStatus.setGameOver(true);
				return;
			}

			// [UC-04: Play Game] - Bước 4.2: Nếu không va chạm và Bird vượt qua Pipe, gọi updateScore() tăng điểm số
			// [Sequence Diagram: Play Game] - Bước 4.2: updateScore() gửi từ GamePanel đến ScoreManager/GameStatus
			if (!obj.getPassed() && bird.getX() > obj.getX() + obj.getWidth()) {
				//Tường: Combo-> cộng điểm
				gameStatus.increaseCombo();

				double scoreAmount = 0.5;

				// Thưởng điểm mỗi 10 combo
				if (gameStatus.getCombo() % 10 == 0) {
				    scoreAmount = 1.0;
				}

				gameStatus.incrementScore(scoreAmount);
				obj.setPassed(true);
			}
		}
		if (shouldAddObstacle()) {
			createObstacles();
		}

		// [UC-04: Play Game] - Bước 3: Hệ thống kiểm tra va chạm (checkCollision) giữa Bird với mặt đất
		if (bird.getY() + bird.getHeight() >= GameConfig.BOARD_HEIGHT) {
			// Cân chỉnh vị trí chim đứng yên chính xác trên mặt đất, không bị chìm xuống dưới
			bird.setY(GameConfig.BOARD_HEIGHT - bird.getHeight());
			// [UC-04: Play Game] - Bước 4.1: Chạm đất có va chạm -> Gọi setGameOver(true) để báo hiệu kết thúc game
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

	// [UC-02: Start Game] - Bước 3: Hệ thống khởi tạo môi trường (reset vị trí Bird, xóa list Pipe cũ, set Điểm số = 0)
	public void resetGame(int boardHeight) {
		// [UC-02: Start Game] - Bước 3a: resetPosition() - Thiết lập lại vị trí mặc định cho đối tượng Bird
		// [Sequence Diagram: Start Game] - Bước 3a: resetPosition() gửi đến Bird
		bird.reset(boardHeight);
		// [UC-02: Start Game] - Bước 3b: clearAndInitPipes() - Xóa danh sách Pipe cũ và tạo Pipe mới
		// [Sequence Diagram: Start Game] - Bước 3b: clearAndInitPipes() gửi đến PipeManager (gameObjects)
		gameObjects.clear(); 
		// [UC-02: Start Game] - Bước 3c: setScore(0) - Gán điểm số hiện tại về 0
		// [Sequence Diagram: Start Game] - Bước 3c: setScore(0) gửi đến GameStatus
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

	// Giải quyết va chạm: Cân chỉnh lại tọa độ chim/vật cản tại thời điểm tiếp xúc biên (0 pixel overlap)
	private void resolveCollision(Bird bird, GameObject obj) {
		int overlapX1 = (bird.getX() + bird.getWidth()) - obj.getX(); // Chim va chạm từ phía bên trái ống
		int overlapX2 = (obj.getX() + obj.getWidth()) - bird.getX();  // Chim va chạm từ phía bên phải ống
		
		int overlapY1 = (bird.getY() + bird.getHeight()) - obj.getY(); // Chim va chạm từ phía trên vật cản (đầu ống dưới)
		int overlapY2 = (obj.getY() + obj.getHeight()) - bird.getY();  // Chim va chạm từ phía dưới vật cản (đầu ống trên)
		
		int overlapX = -1;
		boolean birdOnLeft = false;
		if (overlapX1 > 0 && overlapX2 > 0) {
			if (overlapX1 < overlapX2) {
				overlapX = overlapX1;
				birdOnLeft = true;
			} else {
				overlapX = overlapX2;
				birdOnLeft = false;
			}
		}
		
		int overlapY = -1;
		boolean birdAbove = false;
		if (overlapY1 > 0 && overlapY2 > 0) {
			if (overlapY1 < overlapY2) {
				overlapY = overlapY1;
				birdAbove = true;
			} else {
				overlapY = overlapY2;
				birdAbove = false;
			}
		}
		
		// Đẩy vật thể lùi lại theo trục có độ lún (penetration) nhỏ nhất
		if (overlapX > 0 && overlapY > 0) {
			if (overlapX < overlapY) {
				// Giải quyết theo phương ngang (đẩy ống lùi lại để khớp với rìa chim)
				if (birdOnLeft) {
					obj.setX(obj.getX() + overlapX);
				} else {
					obj.setX(obj.getX() - overlapX);
				}
			} else {
				// Giải quyết theo phương dọc (đẩy chim lùi lại lên/xuống)
				if (birdAbove) {
					bird.setY(bird.getY() - overlapY);
				} else {
					bird.setY(bird.getY() + overlapY);
				}
			}
		}
	}
}