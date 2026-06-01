package View;

import Controller.FacadeController;
import Model.*;

import javax.swing.*;
import java.awt.*;

public class FlappyBird extends JPanel {
	int boardHeight = GameConfig.BOARD_HEIGHT;
	int boardWidth = GameConfig.BOARD_WIDTH;

	Bird bird;
	GameStatus gameStatus;
	Enviroment environment;
	ScorePanel scorePanel;
	FacadeController controller;

	public FlappyBird() {
		setPreferredSize(new Dimension(boardWidth, boardHeight));

		bird = new Bird(boardWidth / 8, boardHeight / 2, 51, 36, ImageAssets.birdImg);
		gameStatus = new GameStatus();
		gameStatus.setState(GameStatus.GameState.WAITING_TO_START);
		environment = new Enviroment(1.0, -4.0, true, 12.0);

		scorePanel = new ScorePanel(e -> controller.restartGame(), gameStatus, e -> {
			if (controller != null)
				controller.getBackToStartCallback().run();
		});

		gameStatus.addObserver(scorePanel);
		add(scorePanel);

		controller = new FacadeController(this, bird, gameStatus, environment, scorePanel);
		addKeyListener(controller);
		setFocusable(true);
		requestFocusInWindow();
	}

	public void setGameMode(String mode) {
		controller.setGameMode(mode);
	}

	public void setBackToStartCallback(Runnable callback) {
		controller.setBackToStartCallback(callback);
	}

	public void restartGame() {
		controller.restartGame();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (environment.isDay()) {
			g.drawImage(ImageAssets.backgroundImg, 0, 0, boardWidth, boardHeight, null);
		} else {
			g.drawImage(ImageAssets.backgroundImg, 0, 0, boardWidth, boardHeight, null);
			g.setColor(new Color(0, 0, 50, 100));
			g.fillRect(0, 0, boardWidth, boardHeight);
		}

		if (gameStatus.getState() == GameStatus.GameState.WAITING_TO_START) {
			int floatingY = bird.getY() + (int) (5 * Math.sin(System.currentTimeMillis() * 0.005));
			g.drawImage(bird.getImg(), bird.getX(), floatingY, bird.getWidth(), bird.getHeight(), null);
			g.setFont(new Font("Arial", Font.BOLD, 26));
			g.setColor(Color.WHITE);
			g.drawString("CÁCH CHƠI", boardWidth / 2 - 70, 100);
			g.setFont(new Font("Arial", Font.PLAIN, 18));
			g.drawString("- Nhấn SPACE để làm chim bay lên", boardWidth / 2 - 150, 150);
			g.drawString("- Tránh các ống để không bị game over", boardWidth / 2 - 150, 180);
			g.drawString("- Mỗi lần vượt ống được +1 điểm", boardWidth / 2 - 150, 210);
			g.setFont(new Font("Arial", Font.BOLD, 22));
			int alpha = (int) (128 + 127 * Math.sin(System.currentTimeMillis() * 0.005));
			g.setColor(new Color(255, 255, 0, alpha));
			g.drawString(">>> Nhấn phím bất kỳ để bắt đầu <<<", boardWidth / 2 - 200, boardHeight - 80);
		} else {
			drawGame(g);
		}
	}

	public void drawGame(Graphics g) {
		bird.render(g);

		for (GameObject obj : controller.getCurrentStage().getGameObjects()) {
			g.drawImage(obj.getImg(), obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight(), null);
		}

		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.PLAIN, 32));
		String scoreText = gameStatus.getScoreText();
		FontMetrics fontMetrics = g.getFontMetrics();
		int textWidth = fontMetrics.stringWidth(scoreText);
		g.drawString(scoreText, (boardWidth - textWidth) / 2, 90);

		g.setFont(new Font("Arial", Font.PLAIN, 16));
		if ("Asian".equals(controller.getGameMode())) {
			g.setColor(Color.RED);
		}
		g.drawString("Chế độ: " + controller.getGameMode(), boardWidth - 140, 25);
	}

}
