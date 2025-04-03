# Đo Nhịp Tim Qua Camera

## Tổng Quan

Tính năng này sử dụng camera điện thoại kết hợp với đèn flash để phân tích sự thay đổi màu sắc trên đầu ngón tay, từ đó tính toán nhịp tim của người dùng theo thời gian thực.

## Nguyên Lý Hoạt Động

1. **Photoplethysmography (PPG)**: Khi máu bơm qua các mạch máu, lượng ánh sáng phản xạ từ mô thay đổi.
2. **Chu kỳ nhịp tim**: Phân tích tín hiệu PPG để phát hiện chu kỳ và tính nhịp tim.
3. **Xử lý tín hiệu**: Áp dụng các thuật toán lọc nhiễu, FFT để tách tín hiệu nhịp tim.

## Thành Phần Cần Triển Khai

### 1. HeartRateActivity

**Mô tả**: Activity chính cho việc đo nhịp tim, quản lý UI và luồng người dùng.

**Trách nhiệm**:
- Hiển thị hướng dẫn đặt ngón tay lên camera
- Quản lý vòng đời camera và quyền truy cập
- Hiển thị kết quả đo và chỉ số theo thời gian thực
- Xử lý các nút bấm (bắt đầu, dừng, lưu kết quả)

**Chi tiết triển khai**:
- Layout: Khu vực hiển thị camera, vùng hiển thị kết quả, nút bấm chức năng
- Kiểm tra và yêu cầu quyền CAMERA và đèn flash
- Hiển thị sóng nhịp tim và đếm ngược thời gian

### 2. CameraProcessor

**Mô tả**: Class xử lý dữ liệu từ camera để cung cấp frames cho phân tích.

**Trách nhiệm**:
- Thiết lập và quản lý phiên camera
- Bật/tắt đèn flash khi cần
- Trích xuất dữ liệu màu sắc từ frames
- Cung cấp frames cho HeartRateAnalyzer

**Chi tiết triển khai**:
- Sử dụng CameraX API cho các thiết bị Android mới
- Fallback sang Camera API cũ nếu cần
- Tối ưu fps (20-30fps) cho phân tích chính xác
- Xử lý các trường hợp đặc biệt (ánh sáng yếu, camera không hỗ trợ)

### 3. HeartRateAnalyzer

**Mô tả**: Core class phân tích tín hiệu và tính toán nhịp tim.

**Trách nhiệm**:
- Phân tích frames để trích xuất tín hiệu PPG
- Lọc nhiễu và xử lý tín hiệu
- Áp dụng thuật toán phát hiện đỉnh (peak detection)
- Tính toán nhịp tim (BPM) và độ tin cậy

**Chi tiết triển khai**:
- Phân tích kênh màu đỏ (Red channel) từ mỗi frame
- Sử dụng cửa sổ trượt để tính toán trung bình động
- Lọc thông thấp để loại bỏ nhiễu cao tần
- Phát hiện đỉnh và tính khoảng cách giữa các đỉnh
- Chuyển đổi từ khoảng thời gian sang BPM

### 4. ResultView

**Mô tả**: Component hiển thị kết quả và trực quan hóa dữ liệu.

**Trách nhiệm**:
- Hiển thị số BPM hiện tại
- Vẽ biểu đồ sóng nhịp tim theo thời gian thực
- Hiển thị mức độ tin cậy của kết quả
- Cung cấp tùy chọn lưu và chia sẻ kết quả

**Chi tiết triển khai**:
- Custom View để vẽ đồ thị sóng nhịp tim
- Animate khi có kết quả mới
- Hiển thị màu sắc tương ứng với các dải nhịp tim
- Tùy chọn xuất kết quả sang HistoryRepository

## Yêu Cầu Triển Khai Chi Tiết

### Quyền Hạn Cần Thiết

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.FLASHLIGHT" />
```

### Thuật Toán Cốt Lõi

1. **Thu thập frames**:
   - Lấy giá trị trung bình của kênh đỏ trong vùng ROI (Region of Interest)
   - Tần suất lấy mẫu: 20-30fps

2. **Xử lý tín hiệu**:
   - Áp dụng bộ lọc thông dải (bandpass filter) 0.8-3.0Hz (48-180 BPM)
   - Loại bỏ nhiễu bằng bộ lọc trung vị
   - Chuẩn hóa tín hiệu

3. **Phát hiện nhịp tim**:
   - Áp dụng thuật toán phát hiện đỉnh (peak detection)
   - Tính khoảng cách trung bình giữa các đỉnh
   - Chuyển đổi thành BPM: 60 / (khoảng thời gian giữa các đỉnh)

4. **Đánh giá độ tin cậy**:
   - Phân tích biến thiên khoảng R-R
   - Loại bỏ kết quả bất thường (nằm ngoài ±20% của trung bình)
   - Hiển thị chỉ số tin cậy dựa trên độ ổn định của tín hiệu

### Xử Lý Lỗi và Phản Hồi

1. **Ánh sáng không đủ**:
   - Phát hiện độ sáng thấp từ camera
   - Tự động bật đèn flash khi cần
   - Hiển thị hướng dẫn điều chỉnh vị trí ngón tay

2. **Chuyển động quá nhiều**:
   - Phát hiện nhiễu từ chuyển động
   - Hướng dẫn người dùng giữ ngón tay ổn định
   - Tạm dừng đo khi nhiễu quá lớn

3. **Tín hiệu yếu**:
   - Phân tích biên độ tín hiệu
   - Đề xuất điều chỉnh áp lực ngón tay
   - Cung cấp hình ảnh minh họa vị trí đặt ngón tay tối ưu

## Hiệu Chuẩn và Độ Chính Xác

- Độ chính xác mục tiêu: ±5 BPM so với thiết bị y tế chuyên dụng
- Thời gian đo tối thiểu: 30 giây để đạt kết quả ổn định
- Thời gian đo tối đa: 45 giây để tránh quá tải và tiêu thụ pin

## Tích Hợp Với Các Module Khác

- **HistoryRepository**: Lưu kết quả đo vào cơ sở dữ liệu
- **StressAnalyzer**: Cung cấp dữ liệu nhịp tim để phân tích mức độ căng thẳng
- **AIService**: Gửi dữ liệu cho tư vấn sức khỏe

## Quy Trình Kiểm Thử

1. **Unit Tests**:
   - Kiểm tra thuật toán xử lý tín hiệu với dữ liệu mẫu
   - Kiểm tra phát hiện đỉnh với các tín hiệu có nhiễu
   - Kiểm tra tính toán BPM từ khoảng thời gian

2. **Instrumentation Tests**:
   - Kiểm tra truy cập camera và đèn flash
   - Kiểm tra luồng UI và phản hồi người dùng
   - Kiểm tra lưu dữ liệu vào cơ sở dữ liệu

3. **Thực Nghiệm**:
   - So sánh kết quả với thiết bị đo nhịp tim y tế
   - Kiểm tra độ ổn định trong các điều kiện ánh sáng khác nhau
   - Kiểm tra với nhiều đối tượng người dùng khác nhau

## Tiêu Chí Hoàn Thành

1. Ứng dụng có thể đo nhịp tim với độ chính xác ±5 BPM trong điều kiện lý tưởng
2. Xử lý tốt các trường hợp ánh sáng kém và nhiễu chuyển động
3. Hiển thị kết quả theo thời gian thực và biểu đồ sóng nhịp tim
4. Lưu trữ kết quả vào cơ sở dữ liệu thành công
5. Tối ưu hiệu suất để giảm thiểu tiêu thụ pin

## Đường Dẫn Tài Nguyên

- [API.md](../../API.md) - Định dạng dữ liệu lưu trữ kết quả đo
- [Nghiên cứu PPG](https://en.wikipedia.org/wiki/Photoplethysmogram)
- [Thuật toán phát hiện đỉnh](https://en.wikipedia.org/wiki/Peak_detection) 