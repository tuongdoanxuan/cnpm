package Model;

public class BasicStageFactory implements StageFactory {

	private Bird bird;
	private GameStatus gameStatus;
	private Enviroment environment;

	@Override
	public void setDependencies(Bird bird, GameStatus gameStatus, Enviroment environment) {
		this.bird = bird;
		this.gameStatus = gameStatus;
		this.environment = environment;
	}

	@Override
	public GameStage createStage(String nameStage) {
		if (bird == null || gameStatus == null || environment == null) {
			throw new IllegalStateException("Phải thiết lập các phụ thuộc trước khi tạo các giai đoạn");
		}

		switch (nameStage.toLowerCase()) {
		case "dễ":
			return new ClassicStage(bird, gameStatus, environment);
		default:
			throw new IllegalArgumentException("BasicStageFactory không thể tạo giai đoạn: " + " " + nameStage);
		}
	}

}
