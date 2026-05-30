package model;

import java.util.ArrayList;
import java.util.List;

public class GameStatus implements Subject {
	private double score;
	private double highScore;
	private boolean gameOver;

//	private String difficulty; // "Easy", "Medium", "Hard"
//	private int pipeSpawnRate; // Thời gian spawn pipe (ms)
//	private int pipeSpeed; // Tốc độ di chuyển pipe (px/frame)
//	private int gravity; // Trọng lực (ảnh hưởng đến rơi tự do của bird)
//	private int jumpStrength; // Độ mạnh khi nhấn space (độ cao nhảy lên)

	private List<Observer> observers = new ArrayList<>();

	public GameStatus() {
		this.score = 0;
		this.highScore = 0;
		this.gameOver = false;
	}
	public enum GameState {
	    START_MENU,
	    WAITING_TO_START,
	    PLAYING,
	    GAME_OVER
	}
	private GameState currentState = GameState.START_MENU;

	public void setState(GameState state) {
	    currentState = state;
	}

	public GameState getState() {
	    return currentState;
	}


	public void incrementScore(double amount) {
		this.score += amount;
		notifyScoreChanged(this.score);
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
		if (gameOver) {
			// Cập nhật điểm cao nhất nếu điểm hiện tại cao hơn
			if (this.score > this.highScore) {
				this.highScore = this.score;
			}
			notifyGameOver();
		}
	}

	// --- Observer methods ---
	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyScoreChanged(double newScore) {
		for (Observer o : observers) {
			o.onScoreChanged(newScore);
		}
	}

	@Override
	public void notifyGameOver() {
		for (Observer o : observers) {
			o.onGameOver();
		}
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
		notifyScoreChanged(this.score);
	}

	public double getHighScore() {
		return highScore;
	}

	public void setHighScore(double highScore) {
		this.highScore = highScore;
		notifyScoreChanged(this.highScore);

	}

	public void reset() {
		this.score = 0;
		this.gameOver = false;
	}

	// reset lại điểm cao nhất
	public void resetHighScore() {
		this.highScore = 0;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public String getScoreText() {
		return gameOver ? "" + (int) score : String.valueOf((int) score);
	}
	

}
