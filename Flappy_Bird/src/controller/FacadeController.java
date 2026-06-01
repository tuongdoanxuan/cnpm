package controller;

import model.*;
import view.FlappyBird;
import view.ScorePanel;

import javax.swing.*;
import java.awt.event.*;

public class FacadeController implements ActionListener, KeyListener {

    private final FlappyBird view;
    private final Timer gameLoop;
    private final GameStatus gameStatus;
    private final Bird bird;
    private final Enviroment env;
    private final ScorePanel scorePanel;

    private GameStage currentStage;
    private StageFactory factory;
    private String gameMode = "Dễ"; // Default

    private final GameAudio audio; // Thêm vào thuộc tính
    
    private Runnable backToStartCallback;

    public FacadeController(FlappyBird view, Bird bird, GameStatus status,
                                Enviroment env, ScorePanel scorePanel) {
        this.view = view;
        this.bird = bird;
        this.gameStatus = status;
        this.env = env;
        this.scorePanel = scorePanel;

        this.factory = new BasicStageFactory(); // default
        factory.setDependencies(bird, gameStatus, env);
        this.currentStage = factory.createStage("Dễ");
        this.currentStage.setupStage();

        this.gameLoop = new Timer(1000 / 60, this);

     // Khởi tạo Audio Adapter
        audio = new AudioAdapter(new SoundEffectPlayer(), new BackgroundMusicPlayer());
        
        gameLoop.start();
    }
    
    public void setBackToStartCallback(Runnable backToStartCallback) {
		this.backToStartCallback = backToStartCallback;
	}

	public Runnable getBackToStartCallback() {
    	return backToStartCallback;
    }

    public void setGameMode(String mode) {
        if (!mode.equals(gameMode)) {
            gameStatus.resetHighScore();
            gameMode = mode;
        }

        this.gameMode = mode;

        if (mode.equals("Asian")) {
            factory = new ChallengeStageFactory();
        } else {
            factory = new BasicStageFactory();
        }

        factory.setDependencies(bird, gameStatus, env);
        currentStage = factory.createStage(mode);
        currentStage.setupStage();
    }

    public void restartGame() {
        currentStage.resetGame(view.getHeight());
        scorePanel.reset();
        setGameMode(gameMode);
        gameStatus.setState(GameStatus.GameState.WAITING_TO_START);
        gameLoop.start();
        view.requestFocusInWindow();
        scorePanel.onScoreChanged(gameStatus.getScore());
    }

    public GameStage getCurrentStage() {
        return currentStage;
    }

    public String getGameMode() {
        return gameMode;
    }
    /*
     * UC-11.1.2: Hệ thống bắt đầu xử lý cập nhật điểm trong game loop.
     * Phương thức này được Timer gọi liên tục trong quá trình game chạy.
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStatus.getState() != GameStatus.GameState.PLAYING) {
            view.repaint();
            return;
        }

        /*
         * UC-11.1.11: Sau khi xử lý cập nhật điểm, giao diện tiếp tục được vẽ lại
         */
        currentStage.update();
        view.repaint();

        if (gameStatus.isGameOver()) {
            gameLoop.stop();
            audio.stopBackgroundMusic();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameStatus.getState() == GameStatus.GameState.WAITING_TO_START) {
            gameStatus.setState(GameStatus.GameState.PLAYING);
            audio.playBackgroundMusic(); 
            return;
        }

        if (gameStatus.getState() == GameStatus.GameState.PLAYING) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                currentStage.birdJump();
                audio.playJumpSound();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
