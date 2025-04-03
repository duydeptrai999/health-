# Lưu Trữ và Phân Tích Lịch Sử Sức Khỏe

## Tổng Quan

Tính năng này quản lý việc lưu trữ, hiển thị và phân tích dữ liệu sức khỏe theo thời gian, giúp người dùng theo dõi sự thay đổi các chỉ số sức khỏe, phát hiện xu hướng và nhận cảnh báo khi có bất thường.

## Nguyên Lý Hoạt Động

1. **Thu thập dữ liệu**: Lưu trữ tất cả kết quả đo từ các tính năng khác (nhịp tim, SpO2, mức độ căng thẳng).
2. **Phân tích xu hướng**: Áp dụng các thuật toán thống kê để phát hiện xu hướng và bất thường.
3. **Trực quan hoá**: Hiển thị dữ liệu theo thời gian dưới dạng biểu đồ và báo cáo.

## Thành Phần Cần Triển Khai

### 1. HealthDatabase

**Mô tả**: Core class quản lý cơ sở dữ liệu sức khỏe, sử dụng Room Database.

**Trách nhiệm**:
- Định nghĩa các entity (bảng dữ liệu)
- Cung cấp DAO (Data Access Object) cho mỗi loại dữ liệu
- Quản lý migration cơ sở dữ liệu
- Mã hóa dữ liệu nhạy cảm

**Chi tiết triển khai**:
- Sử dụng Room Database với các entity: HeartRate, SpO2, StressLevel, UserProfile
- Đảm bảo hỗ trợ query phức tạp với tối ưu hiệu suất
- Thiết kế lược đồ cơ sở dữ liệu có khả năng mở rộng
- Triển khai mã hóa SQLCipher cho dữ liệu nhạy cảm

### 2. HistoryRepository

**Mô tả**: Repository pattern triển khai để tương tác với cơ sở dữ liệu và các nguồn dữ liệu khác.

**Trách nhiệm**:
- Cung cấp API thống nhất cho việc lưu trữ/truy xuất dữ liệu
- Đồng bộ hóa dữ liệu với đám mây (nếu có)
- Xử lý lỗi và xung đột dữ liệu
- Tối ưu hóa hoạt động truy vấn

**Chi tiết triển khai**:
- Sử dụng Kotlin Coroutines cho các thao tác bất đồng bộ
- Triển khai caching để tối ưu hiệu suất
- Áp dụng pattern Single Source of Truth
- Hỗ trợ pagination cho tập dữ liệu lớn

### 3. ChartViewModel

**Mô tả**: ViewModel cung cấp dữ liệu được xử lý cho việc hiển thị biểu đồ.

**Trách nhiệm**:
- Lấy dữ liệu từ Repository
- Xử lý và chuyển đổi dữ liệu cho biểu đồ
- Tính toán thống kê (trung bình, min, max, xu hướng)
- Cung cấp dữ liệu theo các khung thời gian khác nhau

**Chi tiết triển khai**:
- LiveData hoặc StateFlow để cập nhật UI khi dữ liệu thay đổi
- Xử lý các trường hợp dữ liệu không đầy đủ
- Tính toán các chỉ số thống kê theo thời gian thực
- Áp dụng các thuật toán phát hiện xu hướng

### 4. HistoryFragment

**Mô tả**: Fragment hiển thị dữ liệu lịch sử dưới dạng biểu đồ và danh sách.

**Trách nhiệm**:
- Hiển thị biểu đồ dữ liệu theo thời gian
- Cung cấp bộ lọc và tùy chọn xem
- Hiển thị thống kê và phân tích
- Cho phép xuất dữ liệu

**Chi tiết triển khai**:
- Sử dụng thư viện MPAndroidChart cho biểu đồ
- Triển khai các chế độ xem: ngày/tuần/tháng/năm
- Hỗ trợ cử chỉ zoom, pan trên biểu đồ
- Tối ưu hiệu suất render với dataset lớn

## Yêu Cầu Triển Khai Chi Tiết

### Lược Đồ Cơ Sở Dữ Liệu

1. **Bảng `users`**:
   ```sql
   CREATE TABLE users (
       id INTEGER PRIMARY KEY,
       name TEXT,
       birth_date TEXT,
       gender TEXT,
       height REAL,
       weight REAL,
       created_at INTEGER,
       updated_at INTEGER
   )
   ```

2. **Bảng `heart_rate_records`**:
   ```sql
   CREATE TABLE heart_rate_records (
       id INTEGER PRIMARY KEY,
       user_id INTEGER,
       bpm INTEGER NOT NULL,
       confidence INTEGER,
       recorded_at INTEGER NOT NULL,
       note TEXT,
       FOREIGN KEY (user_id) REFERENCES users(id)
   )
   ```

3. **Bảng `spo2_records`**:
   ```sql
   CREATE TABLE spo2_records (
       id INTEGER PRIMARY KEY,
       user_id INTEGER,
       spo2_value REAL NOT NULL,
       confidence INTEGER,
       recorded_at INTEGER NOT NULL,
       note TEXT,
       FOREIGN KEY (user_id) REFERENCES users(id)
   )
   ```

4. **Bảng `stress_records`**:
   ```sql
   CREATE TABLE stress_records (
       id INTEGER PRIMARY KEY,
       user_id INTEGER,
       stress_level INTEGER NOT NULL,
       hrv_sdnn REAL,
       hrv_rmssd REAL,
       lf_hf_ratio REAL,
       recorded_at INTEGER NOT NULL,
       note TEXT,
       FOREIGN KEY (user_id) REFERENCES users(id)
   )
   ```

### Xuất Dữ Liệu

1. **Định dạng CSV**:
   - Header row với tên các cột
   - Mỗi hàng là một bản ghi
   - Giá trị được phân cách bởi dấu phẩy
   - Bao gồm timestamp, giá trị đo và các metadata liên quan

2. **Định dạng PDF**:
   - Trang bìa với thông tin người dùng và khoảng thời gian
   - Biểu đồ tổng quan cho mỗi loại dữ liệu
   - Bảng thống kê với giá trị trung bình, min, max
   - Phân tích xu hướng và ghi chú

3. **Bảo mật dữ liệu**:
   - Mã hóa file xuất
   - Yêu cầu xác thực trước khi xuất
   - Xóa file xuất sau một khoảng thời gian nhất định
   - Không bao gồm thông tin định danh cá nhân khi không cần thiết

### Thuật Toán Phân Tích Xu Hướng

1. **Moving Average (MA)**:
   - Tính trung bình di động trong cửa sổ N điểm
   - Làm mịn dữ liệu để dễ quan sát xu hướng
   - Áp dụng với cửa sổ 7 ngày cho dữ liệu hàng ngày

2. **Exponential Moving Average (EMA)**:
   - Đặt trọng số cao hơn cho dữ liệu gần đây
   - Phản ứng nhanh hơn với thay đổi mới nhất
   - Hệ số alpha = 0.3 (điều chỉnh theo độ nhạy mong muốn)

3. **Linear Regression**:
   - Tính đường xu hướng tuyến tính
   - Phát hiện xu hướng tăng/giảm dài hạn
   - Tính độ dốc để xác định tốc độ thay đổi

4. **Phát hiện bất thường**:
   - Kiểm tra giá trị nằm ngoài khoảng ±2 độ lệch chuẩn
   - Phát hiện thay đổi đột ngột (>20% so với trung bình 7 ngày)
   - Cảnh báo khi chỉ số nằm ngoài ngưỡng an toàn

### Mã Hóa Dữ Liệu

1. **SQLCipher**:
   - Mã hóa toàn bộ cơ sở dữ liệu SQLite
   - Sử dụng khóa mã hóa dựa trên mật khẩu người dùng
   - AES-256 với CBC mode

2. **Bảo vệ dữ liệu trong bộ nhớ**:
   - Xóa dữ liệu nhạy cảm khỏi bộ nhớ sau khi sử dụng
   - Không lưu dữ liệu nhạy cảm trong log
   - Sử dụng biến không thể thay đổi (immutable) khi có thể

3. **Xác thực truy cập**:
   - Yêu cầu xác thực lại khi truy cập dữ liệu lịch sử nhạy cảm
   - Sử dụng BiometricPrompt khi có thể
   - Timeout tự động sau thời gian không hoạt động

### Tối Ưu Hiệu Suất Truy Vấn

1. **Indexing**:
   - Đánh index cho cột `recorded_at` trên tất cả bảng dữ liệu
   - Đánh index cho `user_id` nếu hỗ trợ nhiều người dùng
   - Sử dụng composite index khi cần

2. **Query Optimization**:
   - Sử dụng LIMIT và OFFSET cho pagination
   - Tận dụng query với GROUP BY cho tính toán thống kê
   - Sử dụng view cho các truy vấn phức tạp, thường xuyên

3. **Caching**:
   - Cache kết quả truy vấn thường dùng
   - Invalidate cache khi dữ liệu thay đổi
   - LRU cache cho dữ liệu được truy cập gần đây

## Giao Diện Người Dùng

1. **Màn hình tổng quan**:
   - Thẻ tóm tắt cho mỗi loại dữ liệu với giá trị mới nhất
   - Biểu đồ mini hiển thị xu hướng 7 ngày gần nhất
   - Cảnh báo khi có bất thường

2. **Màn hình chi tiết**:
   - Biểu đồ đường đầy đủ với tùy chọn khung thời gian
   - Hiển thị điểm dữ liệu khi nhấn vào biểu đồ
   - Bảng thống kê bên dưới biểu đồ
   - Nút xuất dữ liệu (PDF/CSV)

3. **Bộ lọc và tùy chỉnh**:
   - Lọc theo loại dữ liệu (nhịp tim, SpO2, stress)
   - Chọn khung thời gian (ngày, tuần, tháng, tùy chỉnh)
   - Tùy chọn hiển thị (đường xu hướng, điểm dữ liệu, vùng bình thường)
   - So sánh các khoảng thời gian (tuần này vs tuần trước)

## Xử Lý Giới Hạn Dữ Liệu

1. **Lưu trữ offline**:
   - Giữ dữ liệu chi tiết trong 6 tháng gần nhất
   - Lưu dữ liệu tổng hợp (daily/weekly) trong 2 năm
   - Tự động nén/lưu trữ dữ liệu cũ

2. **Sao lưu và phục hồi**:
   - Tự động sao lưu cơ sở dữ liệu định kỳ
   - Lưu bản sao lưu trong bộ nhớ ngoài hoặc đám mây
   - Cung cấp tùy chọn phục hồi từ bản sao lưu

3. **Xử lý dữ liệu không đầy đủ**:
   - Hiển thị điểm ngắt khi có khoảng trống trong dữ liệu
   - Cảnh báo khi dữ liệu không đủ cho phân tích đáng tin cậy
   - Tùy chọn nội suy dữ liệu cho biểu đồ (hiển thị rõ phần nội suy)

## Tích Hợp Với Các Module Khác

- **HeartRateAnalyzer**: Nhận và lưu kết quả đo nhịp tim
- **OxygenSaturationAnalyzer**: Nhận và lưu kết quả đo SpO2
- **StressAnalyzer**: Nhận và lưu kết quả phân tích căng thẳng
- **AIService**: Cung cấp dữ liệu lịch sử cho phân tích AI
- **NotificationService**: Tạo thông báo cho xu hướng bất thường

## Quy Trình Kiểm Thử

1. **Unit Tests**:
   - Kiểm tra DAO và Repository
   - Kiểm tra các thuật toán phân tích dữ liệu
   - Kiểm tra tính toán thống kê

2. **Instrumentation Tests**:
   - Kiểm tra migration cơ sở dữ liệu
   - Kiểm tra hiệu suất với dataset lớn
   - Kiểm tra xuất dữ liệu

3. **Thực Nghiệm**:
   - Kiểm tra tải và hiển thị >1000 điểm dữ liệu
   - Đánh giá thời gian phản hồi của truy vấn phức tạp
   - Kiểm tra sử dụng bộ nhớ ở các thiết bị cấu hình thấp

## Tiêu Chí Hoàn Thành

1. Lưu trữ thành công dữ liệu từ tất cả các module đo lường
2. Hiển thị biểu đồ mượt mà với dataset >1000 điểm
3. Thời gian tải dữ liệu <1 giây cho truy vấn thông thường
4. Xuất báo cáo PDF/CSV thành công
5. Phát hiện và cảnh báo xu hướng bất thường một cách chính xác

## Giới Hạn Kỹ Thuật

- Hiệu suất có thể giảm trên thiết bị cấu hình thấp khi dữ liệu lớn
- Độ chính xác phân tích xu hướng phụ thuộc vào tần suất và chất lượng dữ liệu
- Lưu trữ offline giới hạn ở 6 tháng để tối ưu không gian và hiệu suất
- Định dạng báo cáo PDF có giới hạn về khả năng tùy chỉnh

## Đường Dẫn Tài Nguyên

- [API.md](../../API.md) - Cấu trúc database và API
- [Room Database](https://developer.android.com/training/data-storage/room)
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
- [Phân tích xu hướng dữ liệu](https://en.wikipedia.org/wiki/Trend_analysis)