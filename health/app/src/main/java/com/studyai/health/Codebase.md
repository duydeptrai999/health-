# Codebase Structure

## Package: data

### data.models
- **HealthMetric**: Enum đại diện cho các loại thông số sức khỏe (HEART_RATE, SPO2, vv) với các phương thức trợ giúp để lấy đơn vị, tên hiển thị và phạm vi bình thường.
- **HealthRecord**: Entity để lưu trữ các bản ghi sức khỏe trong cơ sở dữ liệu Room với các trường như loại đo, giá trị, thời gian và độ tin cậy.

### data.repositories
- **HealthRecordRepository**: Singleton quản lý tương tác với cơ sở dữ liệu để thêm, sửa, xóa và truy vấn các bản ghi sức khỏe.

### data.dao
- **HealthRecordDao**: Interface định nghĩa các phương thức truy cập dữ liệu như thêm, cập nhật, xóa và truy vấn các bản ghi sức khỏe.

### data.utils
- **DateConverter**: Lớp chuyển đổi Date để lưu trữ trong Room Database.
- **HealthMetricConverter**: Lớp chuyển đổi enum HealthMetric để lưu trữ trong Room Database.

### data.database
- **AppDatabase**: Lớp quản lý cơ sở dữ liệu Room của ứng dụng, cung cấp các DAO.

## Package: ui

### ui.screens.spo2
- **SpO2MeasurementScreen**: Composable hiển thị giao diện đo SpO2 với các trạng thái khác nhau như disclaimer, đo lường và kết quả.
- **MeasurementState**: Enum đại diện cho các trạng thái của quá trình đo SpO2 (DISCLAIMER, READY, MEASURING, RESULT, ERROR).
- **OxygenSaturationAnalyzer**: Lớp phân tích dữ liệu camera để tính toán giá trị SpO2 sử dụng nguyên lý quang phổ.
- **CameraPreview**: Composable hiển thị camera preview và xử lý các sự kiện camera.

### ui.viewmodels.spo2
- **SpO2ViewModel**: ViewModel quản lý trạng thái UI và logic nghiệp vụ cho màn hình đo SpO2.
- **SpO2UiState**: Data class lưu trữ trạng thái UI của màn hình đo SpO2.

### ui.components
- **TopAppBarWithBack**: Composable hiển thị thanh tiêu đề với nút quay lại.

### ui.components.buttons
- **PrimaryButton**: Composable hiển thị nút chính với kiểu dáng thương hiệu.

### ui.components.cards
- **StatusCard**: Composable hiển thị thẻ trạng thái sức khỏe với biểu tượng, tiêu đề và mô tả.
- **HealthStatus**: Enum đại diện cho các cấp độ sức khỏe khác nhau (EXCELLENT, GOOD, AVERAGE, POOR, BAD).

### ui.theme
- **AppIcons**: Object tập trung quản lý các biểu tượng sử dụng trong ứng dụng.
- **Color**: Định nghĩa bảng màu cho ứng dụng, bao gồm màu trạng thái sức khỏe.

## Package: di
- **AppModule**: Module Hilt cung cấp các dependencies cho ứng dụng như Database, DAO và Repository. 