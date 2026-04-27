# TÀI LIỆU YÊU CẦU NGHIỆP VỤ (BUSINESS REQUIREMENT DOCUMENT - BRD)

## Dự án: Game Flappy Bird Mobile

---

## 1. Giới thiệu

### 1.1 Mục đích

Tài liệu này mô tả các yêu cầu nghiệp vụ cho việc phát triển game Flappy Bird trên nền tảng di động. Mục tiêu là xây dựng một trò chơi đơn giản, dễ chơi nhưng gây nghiện, mang lại trải nghiệm giải trí cho người dùng.

### 1.2 Phạm vi

Hệ thống là một ứng dụng game chạy trên Android, cho phép người chơi điều khiển một chú chim vượt qua các chướng ngại vật để đạt điểm số cao nhất.

### 1.3 Định nghĩa

* **Người chơi (Player)**: Người sử dụng ứng dụng để chơi game
* **Chướng ngại vật (Obstacle)**: Các ống nước mà người chơi phải tránh
* **Điểm số (Score)**: Số điểm đạt được khi vượt qua chướng ngại vật
* **Phiên chơi (Game Session)**: Một lần chơi từ khi bắt đầu đến khi kết thúc

---

## 2. Mục tiêu nghiệp vụ

* Xây dựng game đơn giản, dễ tiếp cận
* Tăng khả năng giữ chân người chơi (retention)
* Tạo trải nghiệm chơi mượt mà và phản hồi nhanh
* Cho phép người chơi cạnh tranh điểm số

---

## 3. Các bên liên quan (Stakeholders)

| Đối tượng      | Mô tả                          |
| -------------- | ------------------------------ |
| Người chơi     | Sử dụng game để giải trí       |
| Lập trình viên | Phát triển và bảo trì hệ thống |
| Chủ dự án      | Quản lý và định hướng sản phẩm |

---

## 4. Mô tả tổng quan hệ thống

### 4.1 Góc nhìn sản phẩm

Game là một ứng dụng độc lập trên thiết bị di động, không phụ thuộc hệ thống bên ngoài (ngoại trừ leaderboard nếu có).

### 4.2 Đặc điểm người dùng

* Người chơi phổ thông
* Mọi độ tuổi
* Có kiến thức cơ bản về sử dụng smartphone

### 4.3 Giả định

* Người dùng có thiết bị Android
* Người dùng quen với thao tác chạm màn hình
* Kết nối internet không bắt buộc

---

## 5. Yêu cầu chức năng (Functional Requirements)

### 5.1 Điều khiển game

* Người chơi chạm vào màn hình để làm chim bay lên
* Chim bị ảnh hưởng bởi trọng lực và rơi xuống liên tục

### 5.2 Cơ chế gameplay

* Chim tự động bay về phía trước
* Chướng ngại vật xuất hiện liên tục
* Phát hiện va chạm:

  * Va vào ống → kết thúc game
  * Rơi xuống đất → kết thúc game

### 5.3 Hệ thống tính điểm

* Mỗi lần vượt qua một ống → +1 điểm
* Hiển thị điểm theo thời gian thực

### 5.4 Trạng thái game

* Màn hình bắt đầu
* Đang chơi
* Game Over
* Cho phép chơi lại

### 5.5 Âm thanh & hiệu ứng

* Âm thanh khi nhảy
* Âm thanh khi ghi điểm
* Âm thanh khi thua

### 5.6 Bảng xếp hạng (tùy chọn)

* Lưu điểm cao nhất trên máy
* Hiển thị kỷ lục
* Leaderboard online (nâng cao)

---

## 6. Yêu cầu phi chức năng (Non-Functional Requirements)

### 6.1 Hiệu năng

* Game chạy mượt ~60 FPS
* Thời gian tải < 3 giây

### 6.2 Khả dụng (Usability)

* Điều khiển 1 chạm đơn giản
* Giao diện tối giản, dễ hiểu

### 6.3 Độ tin cậy

* Không crash trong quá trình chơi
* Xử lý va chạm chính xác

### 6.4 Tương thích

* Android 8.0 trở lên
* Hỗ trợ nhiều kích thước màn hình

---

## 7. Kiến trúc hệ thống (Tổng quan)

* Frontend: Android (Kotlin/Java)
* Game Engine: Canvas / Unity (tùy chọn)
* Lưu trữ: SharedPreferences (lưu điểm)

---

## 8. Ràng buộc

* Thời gian phát triển hạn chế
* Phụ thuộc hiệu năng thiết bị
* Yêu cầu đồ họa đơn giản

---

## 9. Rủi ro

| Rủi ro                | Giải pháp                  |
| --------------------- | -------------------------- |
| Game quá khó          | Điều chỉnh khoảng cách ống |
| Lag, giật             | Tối ưu rendering           |
| Người chơi nhanh chán | Thêm hiệu ứng, âm thanh    |

---

## 10. Định hướng phát triển (Future Enhancements)

* Thêm skin cho nhân vật
* Hệ thống thành tích (achievement)
* Chế độ chơi nhiều người
* Tăng độ khó theo thời gian

---

## 11. Tiêu chí thành công

* Game hoạt động ổn định
* Trải nghiệm mượt mà
* Người chơi quay lại nhiều lần
* Không có lỗi nghiêm trọng

---

## 12. Kết luận

Game Flappy Bird hướng đến việc mang lại trải nghiệm giải trí đơn giản nhưng gây nghiện. Với cơ chế điều khiển dễ tiếp cận và gameplay hấp dẫn, hệ thống có khả năng thu hút và giữ chân người chơi hiệu quả.
