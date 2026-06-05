package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import Model.GameStatus;
import Model.Leaderboard;

public class UpdateScoreTest {
//Tăng độ khó theo điểm số (Dynamic Difficulty)
	@Test
	public void testIncrementScore() {
		GameStatus status = new GameStatus();
		status.incrementScore(1);
		assertEquals(1.0, status.getScore(), 0.001);
	}

	@Test
	public void testMultipleIncrementScore() {
		GameStatus status = new GameStatus();
		status.incrementScore(0.5);
		status.incrementScore(0.5);
		assertEquals(1.0, status.getScore(), 0.001);
	}

	@Test
	public void testResetScore() {
		GameStatus status = new GameStatus();
		status.incrementScore(10);
		status.reset();
		assertEquals(0.0, status.getScore(), 0.001);
	}

	@Test
	public void testGameOver() {
		GameStatus status = new GameStatus();
		status.setGameOver(true);
		assertTrue(status.isGameOver());
	}

	@Test
	public void testHighScoreUpdate() {
		GameStatus status = new GameStatus();
		status.incrementScore(20);
		status.setGameOver(true);
		assertEquals(20.0, status.getHighScore(), 0.001);
	}
//Đánh giá trình độ theo số điểm
	@Test
	public void testRankBeginner() {
		GameStatus status = new GameStatus();
		status.setScore(5);
		assertEquals("Người mới", status.getRank());
	}

	@Test
	public void testRankIntermediate() {
		GameStatus status = new GameStatus();
		status.setScore(15);
		assertEquals("Trung bình", status.getRank());
	}

	@Test
	public void testRankExpert() {
		GameStatus status = new GameStatus();
		status.setScore(25);
		assertEquals("Cao thủ", status.getRank());
	}

	@Test
	public void testRankLegend() {
		GameStatus status = new GameStatus();
		status.setScore(50);
		assertEquals("Huyền thoại", status.getRank());
	}

	//Vượt nhiều ống liên tiếp không va chạm sẽ nhận điểm thưởng (Combo Bonus)
	@Test
	public void testComboIncrease() {
		GameStatus status = new GameStatus();

		status.increaseCombo();
		status.increaseCombo();

		assertEquals(2, status.getCombo());
	}
//Hiển thị bảng xếp hạng
	@Test
	public void testLeaderboardSaveScore() {
		Leaderboard.saveScore(100);
		List<Integer> scores = Leaderboard.loadScores();
		assertTrue(scores.contains(100));
	}
}