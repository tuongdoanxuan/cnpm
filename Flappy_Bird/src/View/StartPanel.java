package View;

package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class StartPanel extends JPanel {
    private JButton startButton; // Nút để bắt đầu game
    private JButton modeButton; // Nút để chọn chế độ game
    private Image backgroundImage; // Hình nền cho panel
    private JPanel titlePanel; // Panel chứa tiêu đề có hiệu ứng động
    private Timer colorTimer; // Timer cho hiệu ứng màu
    private float hue = 0; // Giá trị hue hiện tại cho hiệu ứng cầu vồng
    private JDialog modeDialog; // Hộp thoại chọn chế độ
    private JPanel centerPanel; // Panel nội dung chính
    private String gameMode = "Dễ"; // Chế độ game hiện tại (mặc định: Dễ)
    private Image birdIcon; // Biểu tượng chim để trang trí tiêu đề

    // Font tùy chỉnh cho các thành phần UI khác nhau
    private Font titleFont; // Font cho tiêu đề chính "Flappy"
    private Font subtitleFont; // Font cho phụ đề "Bird"
    private Font buttonFont; // Font cho các nút
    private Font labelFont; // Font cho nhãn

    // Hàm callback để truyền chế độ game vào game chính
    private Consumer<String> gameModeConsumer;

    private Color currentModeColor1 = new Color(76, 175, 80);
    private Color currentModeColor2 = new Color(139, 195, 74);

    public StartPanel(Runnable onStartGame, Consumer<String> gameModeConsumer) {
        this.gameModeConsumer = gameModeConsumer;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(360, 640));

        // Khởi tạo tất cả font tùy chỉnh
        initializeFonts();

        // Tải hình nền từ thư mục resources
        backgroundImage = new ImageIcon("picture/flappybirdbg.png").getImage();

        // Tải biểu tượng chim với xử lý lỗi
        try {
            birdIcon = new ImageIcon("picture/flappybird.png").getImage();
        } catch (Exception e) {
            birdIcon = null; // Đặt thành null nếu không tải được ảnh
        }

        // Tạo panel tiêu đề có hiệu ứng động
        createTitlePanel();

        // Khởi tạo timer cho hiệu ứng màu (cập nhật mỗi 80ms)
        colorTimer = new Timer(80, e -> {
            hue += 0.015f; // Tăng giá trị hue cho hiệu ứng cầu vồng
            if (hue > 1f)
                hue = 0f; // Đặt lại hue khi vượt quá 1
            titlePanel.repaint(); // Kích hoạt vẽ lại để tạo hiệu ứng
        });
        colorTimer.start();

        // Tạo nút START với gradient màu cam
        startButton = createStyledButton("START", new Color(255, 87, 34), new Color(255, 152, 0));
        startButton.setFont(buttonFont);

        // Thêm trình xử lý sự kiện cho nút bắt đầu
        startButton.addActionListener(e -> {
            colorTimer.stop(); // Dừng hiệu ứng
            this.setVisible(false); // Ẩn panel bắt đầu

            // Truyền chế độ game đã chọn vào game chính
            if (gameModeConsumer != null) {
                gameModeConsumer.accept(gameMode);
            }

            // Thực thi hàm callback bắt đầu game
            if (onStartGame != null) {
                onStartGame.run();
            }
        });

        // Tạo nút chọn chế độ với màu sắc động
        modeButton = createModeButton("Chế độ: " + gameMode);
        modeButton.setFont(buttonFont);

        // Thêm trình xử lý sự kiện cho nút chế độ
        modeButton.addActionListener(e -> showModeSelection());

        // Tạo panel nội dung chính với bố cục dọc
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(Box.createVerticalGlue()); // Khoảng cách phía trên
        centerPanel.add(titlePanel);

        // Tạo panel chứa nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thêm nút bắt đầu với căn giữa
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(startButton);

        // Thêm khoảng cách giữa các nút
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Thêm nút chế độ với căn giữa
        modeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(modeButton);

        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalGlue()); // Khoảng cách phía dưới

        add(centerPanel, BorderLayout.CENTER);
    }

    private void initializeFonts() {
        try {
            // Font tiêu đề chính - đậm và lớn
            titleFont = new Font("Impact", Font.BOLD, 85);

            // Font phụ đề - kiểu vui nhộn
            subtitleFont = new Font("Comic Sans MS", Font.BOLD, 45);

            // Font nút - hiện đại và sạch sẽ
            buttonFont = new Font("Segoe UI", Font.BOLD, 22);

            // Font nhãn - thanh lịch và dễ đọc
            labelFont = new Font("Segoe UI", Font.BOLD, 24);

        } catch (Exception e) {
            // Dùng Arial làm font dự phòng nếu có lỗi xảy ra
            titleFont = new Font("Arial", Font.BOLD, 80);
            subtitleFont = new Font("Arial", Font.BOLD, 36);
            buttonFont = new Font("Arial", Font.BOLD, 22);
            labelFont = new Font("Arial", Font.BOLD, 24);
        }
    }

    // hiệu ứng nút
    private JButton createStyledButton(String text, Color color1, Color color2) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Tạo nền gradient dọc
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Thêm bóng bên trong nhẹ để tạo chiều sâu
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 18, 18);

                // Tính toán vị trí văn bản để căn giữa
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 2;

                // Vẽ bóng của văn bản
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.drawString(getText(), x + 2, y + 2);

                // Vẽ văn bản chính màu trắng
                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(), x, y);

                g2d.dispose();
            }
        };

        // Đặt thuộc tính cho nút
        button.setPreferredSize(new Dimension(220, 65));
        button.setMaximumSize(new Dimension(220, 65));
        button.setContentAreaFilled(false); // Tắt nền mặc định
        button.setBorderPainted(false); // Tắt viền mặc định
        button.setFocusPainted(false); // Tắt viền focus
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Con trỏ hình bàn tay khi di chuột qua

        return button;
    }

    // tạo nút
    private JButton createModeButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Tạo gradient bằng màu chế độ hiện tại
                GradientPaint gradient = new GradientPaint(0, 0, currentModeColor1, 0, getHeight(), currentModeColor2);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Thêm bóng bên trong nhẹ
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 18, 18);

                // Căn giữa và vẽ văn bản với bóng
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 2;

                // Bóng văn bản
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.drawString(getText(), x + 2, y + 2);

                // Văn bản chính
                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(), x, y);

                g2d.dispose();
            }
        };

        // Đặt thuộc tính cho nút
        button.setPreferredSize(new Dimension(220, 65));
        button.setMaximumSize(new Dimension(220, 65));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    // cập nhật màu nút khi chọn chế độ
    private void updateModeButtonColors(String mode) {
        if (mode.equals("Dễ")) {
            currentModeColor1 = new Color(76, 175, 80);
            currentModeColor2 = new Color(139, 195, 74);
        } else if (mode.equals("Asian")) {
            currentModeColor1 = new Color(244, 67, 54);
            currentModeColor2 = new Color(255, 87, 34);
        }
        modeButton.repaint(); // Kích hoạt vẽ lại để hiển thị màu mới
    }

    // hiệu ứng cho tiêu đề
    private void createTitlePanel() {
        titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Tính toán vị trí giữa để căn chỉnh văn bản
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;

                // Chuẩn bị đo đạc văn bản "Flappy"
                g2d.setFont(titleFont);
                FontMetrics flappyMetrics = g2d.getFontMetrics();
                String flappyText = "Flappy";
                int flappyWidth = flappyMetrics.stringWidth(flappyText);

                // Chuẩn bị đo đạc văn bản "Bird"
                g2d.setFont(subtitleFont);
                FontMetrics birdMetrics = g2d.getFontMetrics();
                String birdText = "BIRD";
                int birdWidth = birdMetrics.stringWidth(birdText);

                // Vẽ "Flappy" với hiệu ứng bóng nhiều lớp
                int flappyX = centerX - flappyWidth / 2;
                int flappyY = centerY - 25;

                g2d.setFont(titleFont);

                // Tạo chiều sâu với nhiều lớp bóng
                for (int i = 6; i >= 0; i--) {
                    int alpha = 20 + (i * 10); // Tăng độ mờ cho mỗi lớp
                    g2d.setColor(new Color(0, 0, 0, alpha));
                    g2d.drawString(flappyText, flappyX + i, flappyY + i);
                }

                // Vẽ văn bản "Flappy" chính màu trắng
                g2d.setColor(Color.WHITE);
                g2d.drawString(flappyText, flappyX, flappyY);

                // Thêm viền xám nhạt để làm nổi bật
                g2d.setStroke(new BasicStroke(2f));
                g2d.setColor(new Color(200, 200, 200, 150));
                g2d.drawString(flappyText, flappyX, flappyY);

                // Tính toán vị trí cho phần chim (biểu tượng + văn bản)
                int birdIconWidth = (birdIcon != null ? 40 : 40);
                int birdSectionWidth = birdIconWidth + birdWidth + 20;
                int birdStartX = centerX - birdSectionWidth / 2;
                int birdY = centerY + 40;

                // Vẽ biểu tượng chim với hiệu ứng tỏa sáng
                if (birdIcon != null) {
                    // Thêm ánh sáng vàng xung quanh biểu tượng chim
                    g2d.setColor(new Color(255, 255, 0, 50));
                    g2d.fillOval(birdStartX + 35, birdY - 35, 60, 45);

                    // Vẽ biểu tượng chim thực tế
                    g2d.drawImage(birdIcon, birdStartX + 45, birdY - 30, 51, 36, null);
                }

                // Vẽ văn bản "Bird" với hiệu ứng gradient cầu vồng
                int birdTextX = birdStartX + birdIconWidth + 60;
                g2d.setFont(subtitleFont);

                // Tạo màu cầu vồng dựa trên hue hiện tại
                Color rainbowColor = Color.getHSBColor(hue, 0.9f, 1f);
                Color rainbowColor2 = Color.getHSBColor((hue + 0.1f) % 1f, 0.9f, 0.9f);

                // Bóng nhiều lớp cho văn bản "Bird"
                for (int i = 4; i >= 0; i--) {
                    int alpha = 30 + (i * 15);
                    g2d.setColor(new Color(0, 0, 0, alpha));
                    g2d.drawString(birdText, birdTextX + i, birdY + i);
                }

                // Áp dụng gradient cầu vồng cho văn bản "Bird"
                GradientPaint rainbowGradient = new GradientPaint(birdTextX, birdY - 20, rainbowColor,
                        birdTextX + birdWidth, birdY + 10, rainbowColor2);
                g2d.setPaint(rainbowGradient);
                g2d.drawString(birdText, birdTextX, birdY);

                // Thêm hiệu ứng tia lấp lánh động
                g2d.setColor(new Color(255, 255, 255, 180));
                g2d.setStroke(new BasicStroke(1.5f));
                long time = System.currentTimeMillis() / 100; // Hiệu ứng dựa trên thời gian

                // Vẽ ba tia lấp lánh với chuyển động sine/cosine
                for (int i = 0; i < 3; i++) {
                    int sparkleX = birdTextX + (int) (Math.sin(time * 0.1 + i) * 20) + 20 + i * 30;
                    int sparkleY = birdY - 10 + (int) (Math.cos(time * 0.1 + i) * 10);
                    drawSparkle(g2d, sparkleX, sparkleY, 8);
                }

                g2d.dispose();
            }

            private void drawSparkle(Graphics2D g2d, int x, int y, int size) {
                // Vẽ các đường chéo chính
                g2d.drawLine(x - size / 2, y, x + size / 2, y);
                g2d.drawLine(x, y - size / 2, x, y + size / 2);
                // Vẽ các đường chéo để tạo hiệu ứng ngôi sao
                g2d.drawLine(x - size / 3, y - size / 3, x + size / 3, y + size / 3);
                g2d.drawLine(x - size / 3, y + size / 3, x + size / 3, y - size / 3);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(360, 120);
            }
        };

        // Cấu hình thuộc tính panel tiêu đề
        titlePanel.setOpaque(false); // Nền trong suốt
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0)); // Thêm padding
    }

    // Hiển thị để chọn chế độ
    private void showModeSelection() {
        // Tạo hộp thoại modal để chọn chế độ
        modeDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chọn chế độ", true);
        modeDialog.setUndecorated(true); // Bỏ trang trí cửa sổ
        modeDialog.setSize(getWidth(), getHeight());
        modeDialog.setLocationRelativeTo(this);

        // Tạo panel hộp thoại với nền động
        JPanel dialogPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // Tạo nền tối bán trong suốt
                g2d.setColor(new Color(0, 0, 0, 200));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

        // Chế độ
        JLabel selectModeLabel = new JLabel("Chọn độ khó") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Căn giữa văn bản
                FontMetrics fm = g2d.getFontMetrics(getFont());
                int textWidth = fm.stringWidth(getText());
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + fm.getAscent()) / 2;

                // Vẽ bóng văn bản
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.drawString(getText(), x + 3, y + 3);

                // Vẽ văn bản chính
                g2d.setColor(getForeground());
                g2d.drawString(getText(), x, y);

                g2d.dispose();
            }
        };
        selectModeLabel.setFont(labelFont.deriveFont(32f));
        selectModeLabel.setForeground(Color.WHITE);
        selectModeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectModeLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 40, 0));

        // Tạo nút chế độ Dễ
        JButton easyModeButton = createModeButton("Dễ", new Color(76, 175, 80));
        easyModeButton.addActionListener(e -> {
            gameMode = "Dễ";
            modeButton.setText("Chế độ: " + gameMode);
            updateModeButtonColors(gameMode);
            modeDialog.dispose();
        });

        // Tạo nút chế độ Khó
        JButton hardModeButton = createModeButton("Asian", new Color(244, 67, 54));
        hardModeButton.addActionListener(e -> {
            gameMode = "Asian";
            modeButton.setText("Chế độ: " + gameMode);
            updateModeButtonColors(gameMode);
            modeDialog.dispose();
        });

        // Tạo panel chứa các nút chế độ
        JPanel modesPanel = new JPanel();
        modesPanel.setLayout(new BoxLayout(modesPanel, BoxLayout.Y_AXIS));
        modesPanel.setOpaque(false);
        modesPanel.add(easyModeButton);
        modesPanel.add(Box.createRigidArea(new Dimension(0, 25))); // Khoảng cách giữa các nút
        modesPanel.add(hardModeButton);

        // Lắp ráp các thành phần hộp thoại
        dialogPanel.add(Box.createVerticalGlue()); // Khoảng cách phía trên
        dialogPanel.add(selectModeLabel);
        dialogPanel.add(modesPanel);
        dialogPanel.add(Box.createVerticalGlue()); // Khoảng cách phía dưới

        modeDialog.add(dialogPanel);
        modeDialog.setVisible(true);
    }

    private JButton createModeButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Tô màu nền đồng nhất
                g2d.setColor(color);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // Thêm highlight nhẹ ở phía trên (tùy chọn cho thiết kế phẳng)
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() / 3, 20, 20);

                // Vẽ viền với màu tối hơn
                g2d.setColor(color.darker());
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 23, 23);

                // Căn giữa và hiển thị văn bản
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 2;

                // Bóng văn bản
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.drawString(getText(), x + 2, y + 2);

                // Văn bản chính màu trắng
                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(), x, y);

                g2d.dispose();
            }
        };

        // Cấu hình thuộc tính nút
        button.setFont(buttonFont.deriveFont(26f));
        button.setPreferredSize(new Dimension(280, 70));
        button.setMaximumSize(new Dimension(280, 70));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setContentAreaFilled(false); // Tắt nền mặc định
        button.setBorderPainted(false); // Tắt viền mặc định
        button.setFocusPainted(false); // Tắt chỉ báo focus
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Con trỏ hình bàn tay khi di chuột qua

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Vẽ hình nền được co giãn theo kích thước panel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void showPanel() {
        this.setVisible(true);
        colorTimer.start(); // Tiếp tục hiệu ứng màu
    }

    public void hidePanel() {
        this.setVisible(false);
        colorTimer.stop(); // Dừng hiệu ứng màu để tiết kiệm tài nguyên
    }

    // lấy chế độ
    public String getGameMode() {
        return gameMode;
    }
}
