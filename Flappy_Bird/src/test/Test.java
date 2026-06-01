package Test;

import javax.swing.JFrame;

import Model.GameConfig;
import View.FlappyBird;
import View.StartPanel;

public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Khởi tạo view FlappyBird (đã chứa Model và Controller bên trong)
        FlappyBird flappyBird = new FlappyBird();
 
        // Panel bắt đầu
        StartPanel startPanel = new StartPanel(
            () -> {
                frame.setContentPane(flappyBird);
                frame.revalidate();
                flappyBird.requestFocus();
                flappyBird.restartGame(); // Gọi qua view để tái khởi động game
            },
            gameMode -> {
                flappyBird.setGameMode(gameMode); // View gọi controller.setGameMode()
            }
        );

        // Gán callback quay lại màn hình bắt đầu
        flappyBird.setBackToStartCallback(() -> {
            frame.setContentPane(startPanel);
            frame.revalidate();
            startPanel.showPanel();
        });

        frame.setContentPane(startPanel); // Hiển thị start panel ban đầu
        frame.setVisible(true);

    }

}
