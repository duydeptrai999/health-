# Lên Lịch Trình và Nhắc Nhở

## Tổng Quan

Tính năng này giúp người dùng tạo và quản lý lịch trình đo chỉ số sức khỏe, thói quen hàng ngày, uống nước, tập thể dục và chế độ dinh dưỡng. Hệ thống thông báo thông minh sẽ nhắc nhở người dùng thực hiện các hoạt động đã lên lịch, ngay cả khi ứng dụng đã đóng.

## Nguyên Lý Hoạt Động

1. **Lập lịch**: Người dùng tạo lịch trình cho các hoạt động sức khỏe.
2. **Nhắc nhở**: Hệ thống gửi thông báo vào thời điểm đã lên lịch.
3. **Theo dõi**: Ghi lại việc hoàn thành các nhiệm vụ và điều chỉnh lịch trình.

## Thành Phần Cần Triển Khai

### 1. ScheduleManager

**Mô tả**: Core class quản lý việc tạo, sửa, xóa và truy vấn lịch trình.

**Trách nhiệm**:
- Quản lý lịch trình trong cơ sở dữ liệu
- Xử lý logic lặp lại (hàng ngày, hàng tuần, hàng tháng)
- Tính toán lịch trình tiếp theo
- Đồng bộ với lịch hệ thống nếu cần

**Chi tiết triển khai**:
- Sử dụng Room Database để lưu trữ lịch trình
- Triển khai các pattern lặp lại phức tạp (VD: thứ 2, 4, 6 hàng tuần)
- Xử lý múi giờ và chế độ tiết kiệm ánh sáng
- Áp dụng thuật toán tối ưu hóa thông báo

### 2. NotificationService

**Mô tả**: Service quản lý việc gửi và xử lý thông báo.

**Trách nhiệm**:
- Lên lịch và hủy thông báo
- Xử lý tương tác người dùng với thông báo
- Hoạt động cả khi ứng dụng đóng
- Tối ưu hóa sử dụng pin

**Chi tiết triển khai**:
- Sử dụng AlarmManager cho thông báo chính xác
- Triển khai Foreground Service cho thông báo theo dõi liên tục
- Áp dụng NotificationCompat cho tương thích đa nền tảng
- Xử lý các cấp độ ưu tiên thông báo khác nhau
- Hỗ trợ thông báo có thể tương tác (quick actions)

### 3. DietPlanGenerator

**Mô tả**: Thành phần tạo và quản lý kế hoạch dinh dưỡng.

**Trách nhiệm**:
- Tạo kế hoạch dinh dưỡng dựa trên dữ liệu sức khỏe
- Điều chỉnh theo mục tiêu và sở thích người dùng
- Tạo gợi ý bữa ăn và công thức nấu ăn
- Theo dõi dinh dưỡng và calo

**Chi tiết triển khai**:
- Sử dụng cơ sở dữ liệu thực phẩm và dinh dưỡng
- Áp dụng thuật toán gợi ý dựa trên các chỉ số sức khỏe
- Tạo lịch ăn uống trong tuần với nhiều lựa chọn
- Hỗ trợ các chế độ ăn đặc biệt (chay, keto, ít muối, v.v.)

### 4. ReminderRepository

**Mô tả**: Repository cho việc lưu trữ và truy xuất dữ liệu nhắc nhở.

**Trách nhiệm**:
- Lưu trữ các loại nhắc nhở khác nhau
- Cung cấp API thống nhất cho các thành phần khác
- Xử lý việc đánh dấu hoàn thành
- Phân tích xu hướng tuân thủ lịch trình

**Chi tiết triển khai**:
- Sử dụng Room Database với các entity phù hợp
- Hỗ trợ truy vấn phức tạp (lịch trình theo ngày, theo loại)
- Tích hợp với HistoryRepository để lưu kết quả
- Cung cấp LiveData/Flow cho việc quan sát thay đổi

## Yêu Cầu Triển Khai Chi Tiết

### Lược Đồ Cơ Sở Dữ Liệu

1. **Bảng `schedules`**:
   ```sql
   CREATE TABLE schedules (
       id INTEGER PRIMARY KEY,
       title TEXT NOT NULL,
       description TEXT,
       type TEXT NOT NULL,
       start_time INTEGER NOT NULL,
       end_time INTEGER,
       repeat_type TEXT,
       repeat_interval INTEGER,
       repeat_days TEXT,
       reminder_time INTEGER,
       user_id INTEGER,
       created_at INTEGER,
       updated_at INTEGER,
       FOREIGN KEY (user_id) REFERENCES users(id)
   )
   ```

2. **Bảng `schedule_instances`**:
   ```sql
   CREATE TABLE schedule_instances (
       id INTEGER PRIMARY KEY,
       schedule_id INTEGER NOT NULL,
       scheduled_time INTEGER NOT NULL,
       completed INTEGER DEFAULT 0,
       completed_time INTEGER,
       notes TEXT,
       FOREIGN KEY (schedule_id) REFERENCES schedules(id)
   )
   ```

3. **Bảng `diet_plans`**:
   ```sql
   CREATE TABLE diet_plans (
       id INTEGER PRIMARY KEY,
       user_id INTEGER,
       start_date INTEGER,
       end_date INTEGER,
       total_calories INTEGER,
       protein_grams INTEGER,
       carbs_grams INTEGER,
       fat_grams INTEGER,
       special_requirements TEXT,
       created_at INTEGER,
       FOREIGN KEY (user_id) REFERENCES users(id)
   )
   ```

4. **Bảng `diet_plan_meals`**:
   ```sql
   CREATE TABLE diet_plan_meals (
       id INTEGER PRIMARY KEY,
       diet_plan_id INTEGER,
       day_of_week INTEGER,
       meal_type TEXT,
       meal_name TEXT,
       calories INTEGER,
       recipe_id INTEGER,
       recipe_url TEXT,
       FOREIGN KEY (diet_plan_id) REFERENCES diet_plans(id)
   )
   ```

### Loại Lịch Trình

1. **Đo Chỉ Số**:
   - Nhịp tim (định kỳ/hàng ngày)
   - SpO2 (định kỳ/khi cần)
   - Mức độ căng thẳng (hàng ngày/tuần)

2. **Thói Quen Sức Khỏe**:
   - Uống nước (nhiều lần trong ngày)
   - Tập thể dục (theo lịch/loại bài tập)
   - Thiền/thư giãn (khung giờ cố định)
   - Ngủ đủ giấc (nhắc đi ngủ/dậy)

3. **Dinh Dưỡng**:
   - Bữa ăn chính (3 bữa/ngày)
   - Bữa phụ/Snack (theo kế hoạch)
   - Vitamin/thuốc bổ (theo đơn)

### Cấu Trúc Thông Báo

1. **Thông Báo Đơn Giản**:
   - Tiêu đề: tên hoạt động
   - Nội dung: mô tả ngắn gọn
   - Thời gian: thời điểm/khoảng thời gian thực hiện
   - Actions: Đánh dấu hoàn thành, Hoãn, Bỏ qua

2. **Thông Báo Chi Tiết**:
   - Bổ sung hình ảnh/icon
   - Thêm nút tắt dẫn đến chức năng liên quan
   - Hỗ trợ mở rộng hiển thị thông tin chi tiết
   - Tiến trình hoàn thành (nếu có)

3. **Thông Báo Liên Tục**:
   - Hiển thị trong thanh trạng thái
   - Cập nhật tiến trình thời gian thực
   - Tùy chọn tắt/tạm dừng

### Thuật Toán Tạo Lịch Thông Minh

1. **Tối ưu hóa thời gian**:
   - Phân tích thói quen người dùng
   - Tránh các khung giờ bận/không khả dụng
   - Nhóm các hoạt động tương tự vào khung giờ gần nhau

2. **Điều chỉnh dựa trên kết quả**:
   - Tăng tần suất khi chỉ số không ổn định
   - Giảm tần suất khi chỉ số ổn định
   - Áp dụng ML để dự đoán thời điểm tốt nhất

3. **Cá nhân hóa nhắc nhở**:
   - Điều chỉnh theo mức độ tuân thủ
   - Thay đổi nội dung/cách thức nhắc nhở
   - Sử dụng ngôn ngữ khích lệ/động viên phù hợp

### Xử Lý Tối Ưu Pin

1. **Phân bổ thông báo**:
   - Sử dụng AlarmManager cho thông báo quan trọng/chính xác
   - Sử dụng FCM cho thông báo ít quan trọng
   - Nhóm thông báo trong cùng khung giờ

2. **Tối ưu hóa service**:
   - Sử dụng JobScheduler/WorkManager thay vì service liên tục
   - Đặt lịch batch thay vì riêng lẻ
   - Tuân thủ Doze mode và App Standby

3. **Cảm biến thông minh**:
   - Sử dụng Activity Recognition để phát hiện ngữ cảnh
   - Chỉ nhắc khi người dùng có thể tương tác
   - Tận dụng lúc điện thoại đang được sử dụng

### Đồng Bộ Hóa Đa Thiết Bị

1. **Đồng bộ lịch trình**:
   - Đồng bộ qua cloud nếu đăng nhập nhiều thiết bị
   - Ưu tiên gửi thông báo trên thiết bị đang sử dụng
   - Đánh dấu hoàn thành đồng bộ trên tất cả thiết bị

2. **Xử lý xung đột**:
   - Ưu tiên phiên bản mới nhất
   - Giải quyết xung đột dựa trên quy tắc merge
   - Thông báo người dùng nếu có xung đột nghiêm trọng

## Giao Diện Người Dùng

1. **Màn hình lịch chính**:
   - Hiển thị theo ngày/tuần/tháng
   - Màu sắc phân loại các loại hoạt động
   - Chỉ báo hoàn thành rõ ràng
   - Nút tạo nhanh lịch mới

2. **Màn hình tạo/chỉnh sửa lịch**:
   - Form đầy đủ với các loại lịch
   - Tùy chọn lặp lại linh hoạt
   - Cài đặt thông báo (thời gian trước, loại)
   - Gắn kết với hoạt động cụ thể

3. **Màn hình kế hoạch dinh dưỡng**:
   - Lịch thực đơn theo tuần
   - Chi tiết món ăn (công thức, dinh dưỡng)
   - Tùy chỉnh theo sở thích
   - Tích hợp với nhắc nhở bữa ăn

4. **Widget và Quick Settings**:
   - Widget hiển thị lịch trình hôm nay
   - Tile nhanh cho các hoạt động thường xuyên
   - Shortcut trên màn hình chính

## Tích Hợp Với Các Module Khác

- **HeartRateAnalyzer**: Lên lịch đo và gửi kết quả
- **SpO2Analyzer**: Lên lịch đo và gửi kết quả
- **StressAnalyzer**: Lên lịch đo và theo dõi xu hướng
- **HistoryRepository**: Lưu lịch sử hoạt động đã hoàn thành
- **AIService**: Nhận gợi ý dựa trên dữ liệu và mục tiêu

## Quy Trình Kiểm Thử

1. **Unit Tests**:
   - Kiểm tra logic lặp lại lịch trình
   - Kiểm tra tạo thông báo và xử lý tương tác
   - Kiểm tra thuật toán tạo kế hoạch dinh dưỡng

2. **Instrumentation Tests**:
   - Kiểm tra hoạt động AlarmManager/JobScheduler
   - Kiểm tra tương tác thông báo
   - Kiểm tra hiệu suất và sử dụng pin

3. **Beta Testing**:
   - Kiểm tra hoạt động trên nhiều thiết bị khác nhau
   - Theo dõi tính ổn định dài hạn
   - Đánh giá hiệu quả của thuật toán thông minh

## Tiêu Chí Hoàn Thành

1. Thông báo xuất hiện đúng giờ đã hẹn, ngay cả khi ứng dụng đã đóng
2. Khả năng tạo và quản lý các loại lịch trình khác nhau
3. Tạo được kế hoạch dinh dưỡng phù hợp với dữ liệu sức khỏe
4. Tiêu thụ pin tối ưu (<5% cho hoạt động nền)
5. Giao diện dễ sử dụng và linh hoạt

## Giới Hạn Kỹ Thuật

- Các nhà sản xuất điện thoại khác nhau có thể hạn chế hoạt động nền
- Android 12+ có các hạn chế về quyền thông báo và dịch vụ nền
- Độ chính xác của thông báo có thể bị ảnh hưởng bởi chế độ tiết kiệm pin
- Tương thích giữa các phiên bản Android khác nhau

## Đường Dẫn Tài Nguyên

- [API.md](../../API.md) - Format thực đơn và hoạt động
- [Android Notification Guide](https://developer.android.com/develop/ui/views/notifications)
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
- [AlarmManager](https://developer.android.com/reference/android/app/AlarmManager) 