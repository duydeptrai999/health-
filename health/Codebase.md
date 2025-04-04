# Codebase Structure

Tài liệu này mô tả cấu trúc codebase của ứng dụng StudyAI Health, giúp hiểu rõ các thành phần và mối liên hệ giữa chúng.

## SpO2 Measurement Feature

### Module: com.studyai.health.ui.screens.spo2

#### SpO2MeasurementScreen (Màn hình đo SpO2)
- Composable chính hiển thị giao diện đo SpO2
- Quản lý các trạng thái: disclaimer, sẵn sàng đo, đang đo, kết quả, lỗi
- Xử lý quyền camera và hiển thị thông báo liên quan
- DisclaimerContent, ReadyContent, MeasuringContent, ResultContent, ErrorContent

#### CameraPreview (Hiển thị camera)
- Composable quản lý camera preview cho đo SpO2
- Cấu hình camera, độ phân giải và hiệu năng
- Quản lý đèn flash để cải thiện độ chính xác
- Hiển thị camera trong layout tròn
- Xử lý lifecycle của camera và giải phóng tài nguyên

#### OxygenSaturationAnalyzer (Phân tích SpO2)
- Phân tích frame từ camera để tính toán SpO2
- Xử lý dữ liệu từ kênh màu đỏ và xanh lá
- Lọc nhiễu và tính toán tỷ lệ hấp thụ ánh sáng
- Chuyển đổi tỷ lệ thành giá trị SpO2 (phần trăm)
- Tính toán độ tin cậy của kết quả đo

### Module: com.studyai.health.ui.viewmodels.spo2

#### SpO2ViewModel (Quản lý trạng thái và dữ liệu)
- Quản lý toàn bộ trạng thái UI (MeasurementState)
- Điều khiển luồng đo: bắt đầu, tiến trình, kết thúc
- Xử lý đọc dữ liệu từ analyzer
- Lưu trữ kết quả vào cơ sở dữ liệu thông qua repository
- Xác định mức độ SpO2 (bình thường, thấp, nguy hiểm)

### Data Models

#### HealthMetric (Enum các loại đo sức khỏe)
- Định nghĩa các loại chỉ số sức khỏe bao gồm SPO2
- Cung cấp phương thức liên quan: getDisplayName, getUnit
- Xác định phạm vi bình thường của từng chỉ số
- Đánh giá kết quả đo (isNormal)

#### HealthRecord (Model lưu trữ dữ liệu sức khỏe)
- Lưu trữ kết quả đo SpO2 với timestamp
- Lưu thông tin về độ tin cậy của phép đo
- Hỗ trợ formatting dữ liệu cho hiển thị
- Đánh giá mức độ bình thường của kết quả

### Repositories

#### HealthRecordRepository
- Quản lý lưu trữ dữ liệu SpO2 vào cơ sở dữ liệu
- Truy vấn lịch sử đo
- Lấy kết quả đo gần nhất
- Xóa/cập nhật dữ liệu đo
