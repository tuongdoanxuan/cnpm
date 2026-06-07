package test;

import Model.Bird;
import Model.Enviroment;
import Model.GameConfig;
import Model.GameStage;
import Model.GameStatus;
import Model.Pipe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOverTest {

    // Tạo GameStage giả lập để kiểm soát môi trường test
    static class TestStage extends GameStage {

        public TestStage(Bird bird, GameStatus gameStatus, Enviroment enviroment) {
            super(bird, gameStatus, enviroment);
        }

        // Tắt trọng lực, tốc độ nền và lực nhảy để vị trí Bird/Pipe không bị thay đổi khi test
        @Override
        public void setupEnvironment() {
            getEnviroment().setGravity(0);
            getEnviroment().setGroundSpeed(0);
            getEnviroment().setJumpStrength(0);
        }

        // Không tự tạo chướng ngại vật, từng test sẽ tự thêm Pipe theo tình huống cần kiểm tra
        @Override
        public void createObstacles() {
        }

        // Gán thông số môi trường cho Bird
        @Override
        public void setupBird() {
            getBird().setGravity(getEnviroment().getGravity());
            getBird().setJumpStrength(getEnviroment().getJumpStrength());
        }

        @Override
        protected int getOpeningSpace() {
            return 150;
        }

        @Override
        protected int getObstacleSpacing() {
            return 250;
        }
    }

    // Hàm hỗ trợ tạo stage ở trạng thái PLAYING trước khi chạy từng test
    private TestStage createStage(Bird bird, GameStatus gameStatus) {
        Enviroment enviroment = new Enviroment();
        TestStage stage = new TestStage(bird, gameStatus, enviroment);
        stage.setupStage();
        gameStatus.setState(GameStatus.GameState.PLAYING);
        return stage;
    }

    @Test
    void gameOverWhenBirdCollidesWithPipe() {
        // Kiểm tra trường hợp Bird va chạm với Pipe thì game phải chuyển sang Game Over
        Bird bird = new Bird(100, 200, 34, 24, null);
        GameStatus gameStatus = new GameStatus();
        TestStage stage = createStage(bird, gameStatus);

        // Đặt Pipe trùng vị trí với Bird để tạo va chạm
        Pipe pipe = new Pipe(100, 200, 64, 512, null);
        stage.getGameObjects().add(pipe);

        stage.update();

        assertTrue(gameStatus.isGameOver());
        assertEquals("Va chạm chướng ngại vật", gameStatus.getGameOverReason());
    }

    @Test
    void gameOverWhenBirdTouchesGround() {
        // Kiểm tra trường hợp Bird chạm mặt đất thì game phải chuyển sang Game Over
        Bird bird = new Bird(100, GameConfig.BOARD_HEIGHT - 24, 34, 24, null);
        GameStatus gameStatus = new GameStatus();
        TestStage stage = createStage(bird, gameStatus);

        stage.update();

        assertTrue(gameStatus.isGameOver());
        assertEquals("Bird chạm mặt đất", gameStatus.getGameOverReason());
    }

    @Test
    void gameContinuesWhenNoCollision() {
        // Kiểm tra trường hợp Bird không va chạm và chưa chạm đất thì game vẫn tiếp tục
        Bird bird = new Bird(100, 200, 34, 24, null);
        GameStatus gameStatus = new GameStatus();
        TestStage stage = createStage(bird, gameStatus);

        // Đặt Pipe cách xa Bird để không xảy ra va chạm
        Pipe pipe = new Pipe(300, 200, 64, 512, null);
        stage.getGameObjects().add(pipe);

        stage.update();

        assertFalse(gameStatus.isGameOver());
        assertEquals("", gameStatus.getGameOverReason());
    }
}