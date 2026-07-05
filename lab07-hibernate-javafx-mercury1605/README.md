[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/juGwSZB0)

# Lab 07 – Library Management (JavaFX + Hibernate)

Ứng dụng desktop quản lý **Student** và **Book** cho phòng Thư viện, dùng **JavaFX** (giao diện) + **Hibernate** (ORM, SQL Server) theo kiến trúc 3 tầng Repository Pattern (DAO → Repository → Service).

## Tính năng

- Đăng nhập (BCrypt) + đăng xuất, hiển thị người dùng đang đăng nhập.
- CRUD Student: thêm / sửa / xóa, tìm kiếm real-time, sắp xếp theo cột, phân trang.
- CRUD Book và gán/gỡ sách cho từng Student.
- Validate dữ liệu nhập, xác nhận trước khi xóa.
- Mật khẩu hash bằng BCrypt; unit test tầng Service (Mockito).

## 🚀 Chạy nhanh bằng file `.jar` (không cần Maven)

File đóng gói sẵn đặt ở thư mục gốc repo: **`JavaFXDemo-1.0-SNAPSHOT.jar`** (fat jar, đã gộp JavaFX + Hibernate + driver SQL Server).

**Yêu cầu:**
- **JDK 21** đã cài (`java -version` phải ra 21 trở lên).
- **Windows** (jar nhúng sẵn native của JavaFX cho Windows — không chạy trên macOS/Linux).
- **SQL Server** đang chạy và khớp đúng cấu hình đã nhúng trong jar (xem bên dưới).

**Bước 1 — Chuẩn bị SQL Server** (config đã nhúng cố định trong jar, không sửa được sau khi build nên máy chấm phải khớp):

| Thông số | Giá trị bắt buộc |
|---|---|
| Server | `localhost:1433` |
| Database | `LibraryDB` |
| User | `sa` |
| Password | `lab07_hibernate_javafx` |

```sql
-- Tạo DB rỗng (bảng sẽ tự sinh khi chạy lần đầu nhờ hbm2ddl.auto=update)
CREATE DATABASE LibraryDB;

-- Đảm bảo login sa bật và đúng mật khẩu jar mong đợi
ALTER LOGIN sa ENABLE;
ALTER LOGIN sa WITH PASSWORD = 'lab07_hibernate_javafx';
```

**Bước 2 — Chạy:**
```bash
java -jar JavaFXDemo-1.0-SNAPSHOT.jar
```

> ✅ App khởi động (hiện màn Login) chỉ với JDK 21 — không cần Maven.
> ⚠️ Nếu SQL Server không khớp cấu hình trên, app vẫn mở màn Login nhưng sẽ lỗi khi đăng nhập/thao tác dữ liệu. Muốn trỏ sang SQL Server khác thì phải sửa `hibernate.cfg.xml` rồi build lại (xem mục *Build* bên dưới).
> ℹ️ Cảnh báo `Unsupported JavaFX configuration: classes were loaded from 'unnamed module'` là bình thường, bỏ qua được.

**Tài khoản đăng nhập:** `admin@library.com` / `admin123` (hoặc email/mật khẩu của một Student đã tạo trong app).

---

## Yêu cầu môi trường (khi build từ source)

| Thành phần | Phiên bản |
|---|---|
| JDK | 21 |
| Maven | 3.9+ |
| SQL Server | 2019+ (bật TCP/IP, port 1433) |
| JavaFX | 21 (Maven tự tải) |

Driver SQL Server (`mssql-jdbc`) và JavaFX được Maven tải tự động qua `pom.xml`, không cần cài tay.

## Cấu hình database

1. Tạo database rỗng trong SQL Server:
   ```sql
   CREATE DATABASE LibraryDB;
   ```
   > Bảng `Students` / `Books` sẽ được Hibernate tự tạo (`hbm2ddl.auto=update`) khi chạy lần đầu.

2. Sửa `SBA301_Hibernate_Project/src/main/resources/hibernate.cfg.xml` cho khớp SQL Server của bạn:
   ```xml
   <property name="connection.url">jdbc:sqlserver://localhost:1433;DatabaseName=LibraryDB;encrypt=true;trustServerCertificate=true</property>
   <property name="connection.username">sa</property>
   <property name="connection.password">YOUR_PASSWORD</property>
   ```

## Build

Cài module backend vào local Maven repo trước, rồi build frontend:

```bash
# 1. Backend (Hibernate)
cd SBA301_Hibernate_Project
mvn clean install

# 2. Frontend (JavaFX) -> tạo fat jar chạy được
cd ../JavaFXDemo
mvn clean package
```

Kết quả: `JavaFXDemo/target/JavaFXDemo-1.0-SNAPSHOT.jar` (fat jar, đã gộp JavaFX + Hibernate + driver).

## Chạy ứng dụng

```bash
# Cách 1: chạy fat jar
java -jar JavaFXDemo/target/JavaFXDemo-1.0-SNAPSHOT.jar

# Cách 2: chạy trực tiếp bằng Maven (dev)
cd JavaFXDemo
mvn javafx:run
```

> Cảnh báo `Unsupported JavaFX configuration: classes were loaded from 'unnamed module'` khi chạy fat jar là bình thường, không ảnh hưởng chức năng.

### Tài khoản đăng nhập

- **Admin mặc định:** `admin@library.com` / `admin123`
- Hoặc dùng email/mật khẩu của một Student đã tạo trong app (mật khẩu được hash BCrypt).

## Test

```bash
cd SBA301_Hibernate_Project
mvn test
```
