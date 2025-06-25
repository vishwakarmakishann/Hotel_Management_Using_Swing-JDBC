
# 🏨 Hotel Management System (Java Swing + JDBC + MySQL)

A fully functional Hotel Management System built using **Java Swing**, **JDBC** and **MySQL**, supporting both **admin** and **user** roles. Includes login/signup, booking workflows, CRUD operations, session handling, triggers, and stored procedures.

---

## 📌 Key Features

### 👥 User Authentication
- User **Login** with role detection
- User **SignUp** with validation
- Session managed via `Session.java`

### 🧑‍💼 Admin Dashboard
- ➕ Add Room
- 🛏️ Manage Rooms (View, Edit, Delete)
- 📅 View All Bookings
- 👥 Manage Users (Add, Edit, Delete)
- 🔙 Logout

### 🙋 User Dashboard
- 🏨 Book a Room
- 📃 View Booking History
- ❌ Cancel Booking
- 🔙 Logout

---

## 🛠️ Technologies Used

| Layer       | Tech                |
|-------------|---------------------|
| UI          | Java Swing (AWT)    |
| Backend     | JDBC + MySQL        |
| Design      | MVC-style packages  |
| DB Logging  | SQL Triggers        |
| Business Logic | Stored Procedures |

---

## 🧾 Database 

### Tables
- `users (id, name, username, password, role)`
- `rooms (id, room_number, type, price, status)`
- `bookings (id, username, room_number, checkin_date, checkout_date, status, created_at)`
- `room_change_log` *(logs edits via trigger)*
- `room_deletion_log` *(logs cancellations via trigger)*

---

## ⚙️ SQL Triggers

### 🔄 `after_room_update`
Logs any update to `rooms` if `type`, `price`, or `status` changes.

```sql
CREATE TRIGGER after_room_update
AFTER UPDATE ON rooms
FOR EACH ROW
BEGIN
    INSERT INTO room_change_log (
        room_id, room_number,
        old_type, old_price, old_status,
        new_type, new_price, new_status
    )
    VALUES (
        OLD.id, OLD.room_number,
        OLD.type, OLD.price, OLD.status,
        NEW.type, NEW.price, NEW.status
    );
END;

```

### 🗑️ `after_room_delete`
Logs full room details before a room is deleted.

```sql
CREATE TRIGGER after_room_delete
AFTER DELETE ON rooms
FOR EACH ROW
BEGIN
    INSERT INTO room_deletion_log (
        room_id, room_number, type, price, status
    ) VALUES (
        OLD.id, OLD.room_number, OLD.type, OLD.price, OLD.status
    );
END;
```

---

## 🧩 Stored Procedures

### 🛏️ `add_room`
Adds a new room to the rooms table using the provided input parameters.

```sql
DELIMITER //
CREATE PROCEDURE add_room(
    IN room_num VARCHAR(20),
    IN room_type VARCHAR(20),
    IN room_price DOUBLE,
    IN room_status VARCHAR(20)
)
BEGIN
    INSERT INTO rooms (room_number, type, price, status)
    VALUES (room_num, room_type, room_price, room_status);
END;
//
DELIMITER ;

```

## 🚀 Getting Started

### Prerequisites
- Java JDK 8+
- MySQL Server / XAMPP
- JDBC Driver (MySQL Connector/J)
- Eclipse / IntelliJ / VS Code

### Setup

1. Create necessary tables in MySQL
2. Open the project in your IDE
3. Update your DB credentials in `Conn.java`:
   ```java
   DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_db", "root", "yourpassword");
   ```
4. Run `Login.java`

---

