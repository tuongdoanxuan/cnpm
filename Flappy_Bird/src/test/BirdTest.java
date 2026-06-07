package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Bird;

public class BirdTest {

	private Bird bird;

	@BeforeEach
	public void setUp() {
		// Tạo một đối tượng Bird (không cần hình ảnh để test logic)
		bird = new Bird(100, 200, 34, 24, null);
		bird.setGravity(1.5);
		bird.setJumpStrength(-10.0);
	}

	@Test
	public void testBirdInitialPosition() {
		assertEquals(100, bird.getX(), "Vị trí X ban đầu chưa đúng");
		assertEquals(200, bird.getY(), "Vị trí Y ban đầu chưa đúng");
		assertEquals(34, bird.getWidth(), "Chiều rộng chưa đúng");
		assertEquals(24, bird.getHeight(), "Chiều cao chưa đúng");
	}

	@Test
	public void testBirdJump() {
		bird.jump();
		bird.update();
		assertTrue(bird.getY() < 200, "Vị trí Y của chim phải giảm (bay lên) sau khi nhảy");
	}

	@Test
	public void testBirdUpdateFalling() {
		bird.update();
		assertTrue(bird.getY() > 200, "Vị trí Y của chim phải tăng (rơi xuống) theo trọng lực");
	}

	@Test
	public void testReset() {
		bird.jump();
		bird.update();
		bird.reset(600);
		assertEquals(300, bird.getY(), "Vị trí Y phải được reset về giữa màn hình");
	}

	@Test
	public void testUpperBoundLimit() {
		bird.setY(-10);
		bird.update();
		assertEquals(0, bird.getY(), "Vị trí Y không được nhỏ hơn 0 (vượt trần)");
	}
}
