# BUSINESS REQUIREMENT DOCUMENT  
## Dự án: Flappy Bird Game

---

## 1. Giới thiệu  
Tài liệu này mô tả các yêu cầu nghiệp vụ cho việc phát triển một trò chơi đơn giản dựa trên Flappy Bird. Hệ thống nhằm cung cấp trải nghiệm giải trí nhẹ, dễ tiếp cận cho người chơi phổ thông.

---

## 2. Mục tiêu nghiệp vụ  
- Phát triển một trò chơi có thể chơi hoàn chỉnh với cơ chế đơn giản.  
- Đảm bảo trải nghiệm mượt mà và phản hồi nhanh.  
- Tăng khả năng người chơi quay lại nhiều lần.  

---

## 3. Phạm vi  

### 3.1 Trong phạm vi  
- Gameplay cơ bản (di chuyển chim, chướng ngại vật, tính điểm).  
- Quản lý trạng thái trò chơi (bắt đầu, đang chơi, kết thúc, chơi lại).  
- Lưu và hiển thị điểm cao nhất.  

### 3.2 Ngoài phạm vi  
- Chế độ nhiều người chơi.  
- Bảng xếp hạng trực tuyến.  
- Mua bán trong game hoặc tùy chỉnh nâng cao.  

---

## 4. Các bên liên quan  
- Nhóm phát triển.  
- Người chơi.  
- Giảng viên hoặc người đánh giá.  

---

## 5. Mô tả người dùng  
Người dùng là người chơi phổ thông, ưu tiên trò chơi đơn giản, dễ hiểu, thời gian chơi ngắn và thao tác nhanh.

---

## 6. Yêu cầu chức năng  

### 6.1 Gameplay  
- Người chơi điều khiển chim bay lên khi có thao tác.  
- Chim tự động rơi xuống do trọng lực.  
- Chướng ngại vật di chuyển từ phải sang trái.  
- Trò chơi kết thúc khi xảy ra va chạm.  

### 6.2 Tính điểm  
- Hệ thống tăng điểm khi chim vượt qua một chướng ngại vật.  
- Điểm được hiển thị trong quá trình chơi.  

### 6.3 Trạng thái trò chơi  
- Hệ thống bao gồm các trạng thái:  
  - Bắt đầu  
  - Đang chơi  
  - Kết thúc  
- Người chơi có thể chơi lại sau khi kết thúc.  

### 6.4 Điều khiển  
- Hệ thống nhận thao tác từ người dùng qua chạm, click hoặc bàn phím.  

### 6.5 Lưu trữ dữ liệu  
- Hệ thống lưu điểm cao nhất trên thiết bị người dùng.  

---

## 7. Yêu cầu phi chức năng  
- Trò chơi phải chạy mượt với tốc độ khung hình ổn định.  
- Phản hồi nhanh khi người dùng thao tác.  
- Không xảy ra lỗi trong quá trình chơi bình thường.  
- Mã nguồn rõ ràng, dễ bảo trì.  

---

## 8. Quy tắc nghiệp vụ  
- Trọng lực luôn tác động làm chim rơi xuống.  
- Mỗi thao tác tạo ra một lực đẩy lên cố định.  
- Chướng ngại vật được tạo với độ cao ngẫu nhiên và khoảng cách hợp lý.  
- Va chạm là điều kiện kết thúc trò chơi.  

---

## 9. Giả định và ràng buộc  
- Hệ thống phát triển cho một nền tảng (web hoặc mobile).  
- Thời gian thực hiện dự án có giới hạn.  
- Nguồn lực và quy mô nhóm hạn chế.  

---

## 10. Tiêu chí thành công  
- Trò chơi hoạt động ổn định, không lỗi nghiêm trọng.  
- Người chơi hiểu cách chơi ngay khi bắt đầu.  
- Người chơi có xu hướng chơi lại nhiều lần.  

---
