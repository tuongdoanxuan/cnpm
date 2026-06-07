package Controller;

import Model.*;
import View.FlappyBird;
import View.ScorePanel;

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
        // [UC-02: Start Game] - Bước 2: startGame() / restartGame() - Hệ thống ghi nhận sự kiện đầu vào và bắt đầu khởi tạo
        // [Sequence Diagram: Start Game] - Bước 2: startGame() gửi đến GamePanel (FacadeController)
        // [UC-02: Start Game] - Bước 3: Hệ thống khởi tạo môi trường (reset vị trí Bird, xóa list Pipe cũ, set Điểm số = 0)
        // [Sequence Diagram: Start Game] - Bước 3a, 3b, 3c: resetPosition(), clearAndInitPipes(), setScore(0)
        currentStage.resetGame(view.getHeight());
        scorePanel.reset();
        setGameMode(gameMode);
        // Đặt trạng thái về WAITING_TO_START để chờ người chơi nhấn phím bắt đầu
        gameStatus.setState(GameStatus.GameState.WAITING_TO_START);
        // [UC-02: Start Game] - Bước 5: Giao diện chuyển từ Main Menu sang Gameplay Screen, bắt đầu Game Loop và gọi repaint()/render()
        // [Sequence Diagram: Start Game] - Bước 5: Khởi tạo xong, yêu cầu render và hiển thị trò chơi bắt đầu (displayGameplayScreen)
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStatus.getState() != GameStatus.GameState.PLAYING) {
            view.repaint();
            return;
        }

        // [UC-04: Play Game] - Bước 1: Trong mỗi Game Loop, hệ thống tự động cập nhật vị trí của Bird (trọng lực) và Pipe (di chuyển sang trái)
        // [Sequence Diagram: Play Game] - Bước 1a & 1b: Vòng lặp game (Game Loop - Timer) chạy và cập nhật updatePosition() cho các đối tượng
        currentStage.update();
        // [UC-04: Play Game] - Bước 5: Hệ thống gọi repaint() để vẽ lại toàn bộ các đối tượng với tọa độ và điểm số mới nhất
        // [Sequence Diagram: Play Game] - Bước 5: repaint() (Vẽ lại khung hình)
        view.repaint();

        // [UC-04: Play Game] - Bước 4.1: Nếu có va chạm, CollisionDetector báo hiệu setGameOver(true), dừng Game Loop và hiển thị màn hình Game Over
        // [Sequence Diagram: Play Game] - Bước 4.1: return true (collision = true) -> gọi setGameOver(true) -> stopGameLoop() & displayGameOver()
        if (gameStatus.isGameOver()) {
            gameLoop.stop();
            audio.stopBackgroundMusic();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameStatus.getState() == GameStatus.GameState.WAITING_TO_START) {
            // [UC-02: Start Game] - Bước 1: Người chơi thực hiện thao tác bắt đầu (nhấn phím)
            // [UC-02: Start Game] - Bước 4: updateState(PLAYING) - Cập nhật biến GameStatus từ trạng thái WAITING_TO_START sang trạng thái PLAYING
            // [Sequence Diagram: Start Game] - Bước 1 & 2 & 4: Nhấn Phím Space -> Gọi startGame() -> updateState(PLAYING)
            gameStatus.setState(GameStatus.GameState.PLAYING);
            audio.playBackgroundMusic(); 
            return;
        }

        if (gameStatus.getState() == GameStatus.GameState.PLAYING) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                // [UC-04: Play Game] - Bước 2: Người chơi nhấn phím Space, hệ thống nhận sự kiện và gọi jump() cấp lực nâng Bird lên
                // [Sequence Diagram: Play Game] - Bước 2: Input (Nhấn phím) -> gọi jump() nâng chim bay lên (cập nhật vận tốc velocityUpdated)
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
