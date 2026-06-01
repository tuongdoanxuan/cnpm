# Business Requirement Document (BRD) - Dự án Flappy Bird

## 1. Giới thiệu
Tài liệu này mô tả chi tiết các yêu cầu nghiệp vụ cho việc phát triển trò chơi **Flappy Bird Clone**. Dự án tập trung vào việc tái hiện cơ chế cốt lõi của trò chơi gốc, cung cấp trải nghiệm giải trí nhẹ nhàng, mượt mà và dễ tiếp cận cho người chơi trên nền tảng kỹ thuật số.

---

## 2. Mục tiêu nghiệp vụ
* **Hoàn thiện Gameplay:** Xây dựng một sản phẩm hoàn chỉnh với logic trò chơi ổn định.
* **Tối ưu trải nghiệm:** Đảm bảo phản hồi điều khiển (input latency) thấp nhất để tạo cảm giác mượt mà.
* **Khả năng giữ chân người dùng:** Thiết kế cơ chế tính điểm và lưu kỷ lục để khuyến khích người chơi quay lại.

---

## 3. Phạm vi dự án (Scope)

### 3.1 Trong phạm vi
* Phát triển logic di chuyển của nhân vật (Gravity & Flap).
* Hệ thống chướng ngại vật (Pipes) sinh ra ngẫu nhiên.
* Xử lý va chạm (Collision Detection) và quản lý trạng thái trò chơi.
* Giao diện người dùng (UI): Màn hình chờ, màn hình chơi, màn hình kết thúc.
* Hệ thống lưu trữ điểm cao nhất (High Score) tại chỗ (Local Storage).

### 3.2 Ngoài phạm vi
* Hệ thống tài khoản và đăng nhập.
* Bảng xếp hạng trực tuyến (Global Leaderboard).
* Chế độ chơi nhiều người (Multiplayer).
* Tích hợp thanh toán hoặc mua vật phẩm trong game (In-app Purchase).

---

## 4. Các bên liên quan (Stakeholders)
* **Nhóm phát triển:** Chịu trách nhiệm thiết kế, lập trình và kiểm thử.
* **Người chơi:** Đối tượng trải nghiệm trực tiếp sản phẩm.
* **Giảng viên/Người đánh giá:** Theo dõi tiến độ và chất lượng kỹ thuật của dự án.

---

## 5. Mô tả người dùng (User Persona)
* **Đối tượng:** Người chơi phổ thông (Casual Gamers).
* **Đặc điểm:** Ưu tiên tính đơn giản, thao tác nhanh (thường là một chạm), thời gian chơi mỗi hiệp ngắn (dưới 2 phút).

---

## 6. Yêu cầu chức năng (Functional Requirements)

### 6.1 Cơ chế Gameplay
* **Điều khiển:** Chạm/Click/Nhấn phím sẽ tạo một lực đẩy (impulse) giúp nhân vật bay lên.
* **Vật lý:** Trọng lực luôn tác động kéo nhân vật rơi xuống tự do.
* **Chướng ngại vật:** Các cặp ống cống di chuyển từ phải sang trái với khoảng cách và độ cao ngẫu nhiên.
* **Kết thúc:** Trò chơi dừng ngay lập tức khi nhân vật va chạm với ống hoặc mặt đất.

### 6.2 Hệ thống tính điểm
* Ghi 01 điểm khi nhân vật đi qua hoàn toàn một cặp ống cống.
* Hiển thị điểm số hiện tại ngay trên màn hình trong lúc chơi.

### 6.3 Trạng thái trò chơi (Game States)
* **Start:** Chờ tương tác đầu tiên để bắt đầu.
* **Playing:** Giai đoạn điều khiển và tính điểm.
* **Game Over:** Hiển thị kết quả, so sánh với kỷ lục và cung cấp nút "Chơi lại".

### 6.4 Lưu trữ dữ liệu
* Tự động lưu điểm cao nhất vào bộ nhớ của thiết bị (hoặc trình duyệt) ngay sau khi hiệp đấu kết thúc.

---

## 7. Yêu cầu phi chức năng (Non-functional Requirements)
* **Hiệu năng:** Duy trì tốc độ khung hình ổn định (tối thiểu 60 FPS).
* **Tính khả dụng:** Giao diện đơn giản, người dùng không cần hướng dẫn vẫn có thể hiểu cách chơi.
* **Độ tin cậy:** Không xảy ra hiện tượng "văng" game (crash) hoặc lỗi logic va chạm sai lệch.
* **Khả năng bảo trì:** Mã nguồn được tổ chức theo module, có chú thích rõ ràng.

---

## 8. Quy tắc nghiệp vụ (Business Rules)
* Mỗi lần tương tác chỉ tạo ra một lực đẩy cố định (không cộng dồn lực nếu nhấn quá nhanh).
* Khoảng trống giữa hai ống cống phải đủ rộng để nhân vật có thể đi qua về mặt lý thuyết.
* Va chạm với mặt đất luôn dẫn đến trạng thái Game Over dù điểm số là bao nhiêu.

---

## 9. Giả định và Ràng buộc
* **Nền tảng:** Ưu tiên chạy tốt trên nền tảng Web (Trình duyệt) hoặc ứng dụng di động đơn lẻ.
* **Thời gian:** Dự án cần hoàn thiện trong khung thời gian học phần/kỳ học.
* **Nguồn lực:** Thực hiện bởi nhóm phát triển nội bộ (5 thành viên).

---

## 10. Tiêu chí thành công (Success Criteria)
* Sản phẩm vận hành đúng logic Flappy Bird gốc.
* Không có lỗi nghiêm trọng ảnh hưởng đến trải nghiệm (Bug-free core logic).
* Tốc độ phản hồi từ lúc nhấn đến khi nhân vật bay lên không có cảm giác trễ.
