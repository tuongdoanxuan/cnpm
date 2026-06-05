package Model;

public interface StageFactory {
	public GameStage createStage(String nameStage);
	public void setDependencies(Bird bird, GameStatus gameStatus, Enviroment environment);
}
