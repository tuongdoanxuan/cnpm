package controller;

import model.GameStatus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FacadeController implements ActionListener, KeyListener {

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
        //UC_16.1.4
        if (gameStatus.isGameOver())
            return;

        // Cập nhật trạng thái chim
        bird.update();

        // Cập nhật tất cả đối tượng game
        for (GameObject obj : gameObjects) {
            obj.update();
            obj.setX(obj.getX() + (int) enviroment.getGroundSpeed());
        }

        // Kiểm tra va chạm và tính điểm
        for (GameObject obj : gameObjects) {
            // UC_16.1.4
            if (obj.collidesWith(bird)) {
                gameStatus.setGameOver(true);
                return;
            }

            if (!obj.getPassed() && bird.getX() > obj.getX() + obj.getWidth()) {
                gameStatus.incrementScore(0.5);
                obj.setPassed(true);
            }
        }
        if (shouldAddObstacle()) {
            createObstacles();
        }

        if (bird.getY() + bird.getHeight() >= GameConfig.BOARD_HEIGHT) {
            gameStatus.setGameOver(true);
        }

        gameObjects.removeIf(obj -> obj.getX() + obj.getWidth() < 0);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
