# PlanbookAI Backend - Spring Boot Monolithic

Dự án Backend cho hệ thống **PlanbookAI (PBA)** được thiết kế theo cấu trúc **Domain-Driven Design (DDD)** Monolithic sạch sẽ và tối ưu hóa hiệu năng, tương thích hoàn toàn với MySQL 8.x và sẵn sàng tích hợp các thư viện thông minh (OpenCV, Gemini AI).

## 🚀 Hướng Dẫn Cài Đặt và Khởi Chạy

### 1. Yêu cầu hệ thống
- Java Development Kit (JDK) 17 hoặc trở lên.
- Maven 3.8 trở lên.
- MySQL Server 8.0 trở lên.

### 2. Cấu hình Cơ sở dữ liệu MySQL
- Khởi chạy MySQL Server và chạy tệp script SQL `planbookai_schema.sql` đã được cung cấp để tạo cấu trúc 7 bảng.
- Cấu hình lại thông số đăng nhập (username, password) của cơ sở dữ liệu của bạn trong tệp tin:
  `src/main/resources/application.properties`

### 3. Build & Chạy dự án
Mở terminal tại thư mục gốc chứa tệp `pom.xml` và chạy:
```bash
# Biên dịch dự án
mvn clean install

# Khởi chạy dự án Spring Boot
mvn spring-boot:run
```
Hệ thống sẽ khởi chạy ở cổng mặc định `8080` (hoặc cổng cấu hình trong `application.properties`).

## 📁 Cấu Trúc Dự Án (Domain-Driven Design - DDD)

- `domain/`: Chứa các thực thể miền (Domain Entities/Aggregate Roots), Value Objects, Domain Events và Interfaces của Repositories. Đây là trái tim của hệ thống và độc lập với các công nghệ bên ngoài.
- `application/`: Chứa các dịch vụ điều phối nghiệp vụ (Use Cases, DTOs, Mappers).
- `presentation/`: Chứa các API Controllers nhận yêu cầu từ ReactJS và phản hồi dữ liệu.
- `infrastructure/`: Chứa các cấu hình kỹ thuật (Security, JWT, Database Connections, Adapters của OpenCV và Gemini AI).