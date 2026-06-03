package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionListener;

import Model.*;

public class ScorePanel extends JPanel implements Observer {
	private JLabel currentScoreLabel;
	private JLabel highScoreLabel;
	private JButton restartButton;
	private JButton backToStartButton;
	private GameStatus gameStatus;
	private int finalScore;

	public ScorePanel(ActionListener restartAction, GameStatus gameStatus) {
		this(restartAction, gameStatus, null);
	}

	public ScorePanel(ActionListener restartAction, GameStatus gameStatus, ActionListener backToStartAction) {
		this.gameStatus = gameStatus;

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setOpaque(false);

		JPanel scorePanel = createScorePanel();

		// === Button Panel ===
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setOpaque(false);
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// === Restart Button ===
		restartButton = new JButton("PLAY AGAIN");
		restartButton.setFont(new Font("Arial", Font.BOLD, 20));
		restartButton.setBackground(new Color(232,97,1));
		restartButton.setForeground(Color.WHITE);
		restartButton.setFocusPainted(false);
		restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		restartButton.setMaximumSize(new Dimension(200, 60));
		restartButton.addActionListener(restartAction);

		// === Back to Start Button ===
		backToStartButton = new JButton("HOME");
		backToStartButton.setFont(new Font("Arial", Font.BOLD, 20));
		backToStartButton.setBackground(new Color(232,97,1)); 
		backToStartButton.setForeground(Color.WHITE);
		backToStartButton.setFocusPainted(false);
		backToStartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backToStartButton.setMaximumSize(new Dimension(200, 50));
		if (backToStartAction != null) {
			backToStartButton.addActionListener(backToStartAction);
		}

		// Add buttons to button panel
		buttonPanel.add(restartButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		buttonPanel.add(backToStartButton);

		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(scorePanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		mainPanel.add(buttonPanel);
		mainPanel.add(Box.createVerticalGlue());

		JPanel containerPanel = new JPanel(new BorderLayout());
		containerPanel.setOpaque(false);
		containerPanel.add(mainPanel, BorderLayout.CENTER);
		containerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		add(containerPanel, BorderLayout.CENTER);

		setVisible(false);
		setOpaque(false);
	}

	private JPanel createScorePanel() {
		JPanel scoreContainer = new JPanel();
		scoreContainer.setLayout(new BoxLayout(scoreContainer, BoxLayout.Y_AXIS));
		scoreContainer.setBackground(new Color(255, 248, 220));
		scoreContainer.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(50, 50, 50), 2),
				new EmptyBorder(20, 30, 20, 30)));
		scoreContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreContainer.setMaximumSize(new Dimension(300, 150));

		JLabel scoreTitle = new JLabel("GAME OVER");
		scoreTitle.setFont(new Font("Arial", Font.BOLD, 30));
		scoreTitle.setForeground(new Color(255, 69, 0));
		scoreTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Panel cho điểm hiện tại
		JPanel currentScorePanel = new JPanel(new BorderLayout());
		currentScorePanel.setOpaque(false);
		JLabel currentScoreText = new JLabel("Score:");
		currentScoreText.setFont(new Font("Arial", Font.PLAIN, 20));
		currentScoreLabel = new JLabel("0");
		currentScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
		currentScorePanel.add(currentScoreText, BorderLayout.WEST);
		currentScorePanel.add(currentScoreLabel, BorderLayout.EAST);

		// Separator line
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(255, 215, 0));
		separator.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Panel cho điểm cao nhất
		JPanel highScorePanel = new JPanel(new BorderLayout());
		highScorePanel.setOpaque(false);
		JLabel highScoreText = new JLabel("Best:");
		highScoreText.setFont(new Font("Arial", Font.PLAIN, 20));
		highScoreLabel = new JLabel("0");
		highScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
		highScoreLabel.setForeground(new Color(255, 165, 0));
		highScorePanel.add(highScoreText, BorderLayout.WEST);
		highScorePanel.add(highScoreLabel, BorderLayout.EAST);

		scoreContainer.add(scoreTitle);
		scoreContainer.add(Box.createRigidArea(new Dimension(0, 15)));
		scoreContainer.add(currentScorePanel);
		scoreContainer.add(Box.createRigidArea(new Dimension(0, 10)));
		scoreContainer.add(separator);
		scoreContainer.add(Box.createRigidArea(new Dimension(0, 10)));
		scoreContainer.add(highScorePanel);

		return scoreContainer;
	}

	@Override
	public void onScoreChanged(double newScore) {
		finalScore = (int) newScore;
	}

	@Override
	public void onGameOver() {
		currentScoreLabel.setText(String.valueOf(finalScore));
		highScoreLabel.setText(String.valueOf((int) gameStatus.getHighScore()));
		setVisible(true);
	}

	public void reset() {
		setVisible(false);
	}
}