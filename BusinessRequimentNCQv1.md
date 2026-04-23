BusinessRequiment
## 1. Overview

### 1.1 Project Description
Dự án nhằm phát triển một trò chơi mobile/web đơn giản dựa trên ý tưởng của game Flappy Bird. Người chơi điều khiển một chú chim vượt qua các chướng ngại vật (ống) bằng cách chạm vào màn hình.

### 1.2 Objectives
- Xây dựng một game đơn giản, dễ chơi.
- Triển khai nhanh để thử nghiệm thị trường (MVP).
- Có thể mở rộng thêm tính năng trong tương lai (leaderboard, skins, ads).

## 2.  Stakeholders

| Role              | Description                          |
|-------------------|--------------------------------------|
| Product Owner     | Định hướng sản phẩm                  |
| Developer Team    | Xây dựng game                        |
| Designer          | Thiết kế UI/UX                       |
| Tester (QA)       | Kiểm thử và đảm bảo chất lượng       |
| End Users         | Người chơi                           |

---

## 3.  Scope

### 3.1 In Scope (Phạm vi chính)
- Gameplay cơ bản (tap để bay)
- Sinh ống ngẫu nhiên
- Tính điểm
- Game over và restart
- Âm thanh cơ bản

## 4.  Functional Requirements

### 4.1 Gameplay
- Người chơi chạm (tap) để làm chim bay lên.
- Chim bị ảnh hưởng bởi trọng lực (rơi xuống).
- Nếu chim chạm ống hoặc đất → Game Over.

### 4.2 Scoring System
- +1 điểm khi vượt qua mỗi cặp ống.
- Hiển thị điểm hiện tại.

### 4.3 Obstacles (Ống)
- Ống xuất hiện liên tục từ bên phải.
- Khoảng cách giữa các ống là ngẫu nhiên trong giới hạn.

### 4.4 Game State
- Start Game
- Playing
- Game Over
- Restart

### 4.5 Sound
- Âm thanh khi:
  - Bay
  - Va chạm
  - Ghi điểm

## 5.  Non-Functional Requirements

### 5.1 Performance
- Game phải chạy mượt (≥ 60 FPS).
- Load nhanh (< 3 giây).

### 5.2 Compatibility
- Web browser.
- Hỗ trợ nhiều kích thước màn hình.

### 5.3 Usability
- Giao diện đơn giản.
- Dễ hiểu, không cần hướng dẫn.



## 6. System Architecture (Simple)

- Frontend: Game Engine (Unity / Android Native)
- Backend (optional): Firebase (leaderboard, analytics)


## 7.  UI/UX Requirements

### 7.1 Screens
- Splash Screen
- Main Menu
- Game Screen
- Game Over Screen

### 7.2 Controls
- Tap / Click để điều khiển
- 
## 8.  KPIs (Key Performance Indicators)

- Số lượt chơi
- Thời gian chơi trung bình
- Tỷ lệ quay lại (retention)
- Điểm trung bình


## 9.  Future Enhancements

- Leaderboard online
- Ads (AdMob)
- Achievement system



## 10.  Timeline (Dự kiến)

| Phase            | Duration       |
|------------------|----------------|
| Design           | 3 ngày         |
| Development      | 1-2 tuần       |
| Testing          | 3-5 ngày       |
| Deployment       | 1 ngày         |

---

## 11.  Risks

- Gameplay quá đơn giản → dễ chán
- Performance trên thiết bị yếu
- Bug vật lý (physics)

---

## 12.  Acceptance Criteria

- Game chạy mượt, không crash
- Gameplay đúng như mô tả
- Tính điểm chính xác
- Có thể restart game

---

## 13. Notes

- Ưu tiên làm MVP trước
- Code dễ maintain để mở rộng sau này

**Author:** Quyet  
**Version:** 1.0  
**Date:** 2026-04-23
