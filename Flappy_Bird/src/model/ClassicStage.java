package Model;

public class ClassicStage extends GameStage {

	public ClassicStage(Bird bird, GameStatus gameStatus, Enviroment enviroment) {
		super(bird, gameStatus, enviroment);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setupEnvironment() {
		// TODO Auto-generated method stub
		// Chế độ cổ điển - cài đặt dễ hơn
		getEnviroment().setGravity(1.0);
		getEnviroment().setGroundSpeed(-4.0); // Ống di chuyển sang trái với tốc độ vừa phải
		getEnviroment().setJumpStrength(11.0);
		getEnviroment().setDay(true); // Luôn là ban ngày trong chế độ cổ điển
	}

	@Override
	public void createObstacles() {
		// Tạo cặp ống ban đầu cho chế độ cổ điển
		int initialX = GameConfig.BOARD_WIDTH;
		addPipePair(initialX, false); // Không có ống di chuyển trong chế độ cổ điển
	}

	@Override
	public void setupBird() {
		// TODO Auto-generated method stub
		// Thiết lập chim tiêu chuẩn cho chế độ cổ điển
		getBird().setGravity(getEnviroment().getGravity());
		getBird().setJumpStrength(getEnviroment().getJumpStrength());
	}

	@Override
	protected int getOpeningSpace() {
		// Khoảng trống lớn hơn để chơi dễ hơn
		return GameConfig.BOARD_HEIGHT / 5;
	}

	@Override
	protected int getObstacleSpacing() {
		// Khoảng cách giữa các chướng ngại vật lớn hơn trong chế độ cổ điển
		return 250;
	}

	public void update() {
		super.update(); // Gọi logic cập nhật từ lớp cha

		// Thêm chướng ngại vật mới khi cần
		if (shouldAddObstacle()) {
			createObstacles();
		}
	}

}
