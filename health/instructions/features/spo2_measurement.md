# Đo SpO2 Qua Camera

## Tổng Quan

Tính năng này sử dụng camera điện thoại kết hợp với đèn flash để phân tích quang phổ ánh sáng phản xạ từ ngón tay, từ đó ước tính nồng độ oxy trong máu (SpO2) của người dùng.

## Nguyên Lý Hoạt Động

1. **Quang phổ ánh sáng**: Hemoglobin giàu oxy (HbO2) và hemoglobin nghèo oxy (Hb) hấp thụ ánh sáng ở các bước sóng khác nhau.
2. **Tỷ lệ hấp thụ**: Phân tích tỷ lệ hấp thụ ánh sáng đỏ (660nm) và hồng ngoại (940nm).
3. **Tính toán SpO2**: Áp dụng các thuật toán để chuyển đổi từ tỷ lệ hấp thụ sang phần trăm oxy trong máu.

## Thành Phần Cần Triển Khai

### 1. SpO2Activity

**Mô tả**: Activity chính cho việc đo SpO2, quản lý UI và luồng người dùng.

**Trách nhiệm**:
- Hiển thị miễn trừ trách nhiệm y tế trước khi sử dụng
- Hướng dẫn đặt ngón tay lên camera
- Quản lý vòng đời camera và quyền truy cập
- Hiển thị kết quả đo và phân loại sức khỏe

**Chi tiết triển khai**:
- Layout: Miễn trừ trách nhiệm, khu vực camera, vùng kết quả, nút bấm
- Kiểm tra và yêu cầu quyền CAMERA và đèn flash
- Hiển thị đồng hồ đếm ngược và tiến trình đo
- Cảnh báo khi SpO2 dưới mức bình thường (<95%)

### 2. OxygenSaturationAnalyzer

**Mô tả**: Core class phân tích tín hiệu quang phổ và tính toán SpO2.

**Trách nhiệm**:
- Phân tích tỷ lệ ánh sáng đỏ/hồng ngoại từ frames
- Lọc nhiễu và xử lý tín hiệu
- Áp dụng thuật toán chuyển đổi sang SpO2
- Tính toán độ tin cậy của kết quả

**Chi tiết triển khai**:
- Phân tích kênh màu đỏ và hồng ngoại (IR) từ frames
- Tính toán tỷ lệ R = (AC_Red/DC_Red)/(AC_IR/DC_IR)
- Áp dụng công thức: SpO2 = 110 - 25 * R (công thức đơn giản hoá)
- Hiệu chỉnh thông qua hiệu chuẩn với thiết bị y tế

### 3. ResultProcessor

**Mô tả**: Class xử lý và phân loại kết quả SpO2.

**Trách nhiệm**:
- Phân loại mức độ SpO2 (bình thường, thấp, nguy hiểm)
- Tạo thông báo và khuyến nghị dựa trên kết quả
- Chuẩn bị dữ liệu để lưu vào cơ sở dữ liệu
- Hiển thị xu hướng theo thời gian (nếu có dữ liệu lịch sử)

**Chi tiết triển khai**:
- Phân loại: >=95% (bình thường), 90-94% (thấp), <90% (nguy hiểm)
- Tạo thông báo với mức độ khác nhau dựa trên kết quả
- Định dạng dữ liệu để lưu trữ và hiển thị
- Tạo báo cáo chi tiết (thời gian đo, kết quả, khuyến nghị)

### 4. CalibrationHelper

**Mô tả**: Class hỗ trợ hiệu chuẩn hệ thống đo SpO2.

**Trách nhiệm**:
- Hướng dẫn người dùng qua quy trình hiệu chuẩn
- Lưu các tham số hiệu chuẩn
- Áp dụng hiệu chỉnh vào kết quả đo
- Quản lý thời hạn hiệu chuẩn

**Chi tiết triển khai**:
- Giao diện hướng dẫn hiệu chuẩn step-by-step
- Lưu trữ tham số hiệu chuẩn trong SharedPreferences
- Tạo yếu tố hiệu chỉnh cho thuật toán SpO2
- Nhắc nhở người dùng tái hiệu chuẩn định kỳ (mỗi 3 tháng)

## Yêu Cầu Triển Khai Chi Tiết

### Quyền Hạn Cần Thiết

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.FLASHLIGHT" />
```

### Thuật Toán Cốt Lõi

1. **Thu thập frames**:
   - Phân tích kênh màu đỏ (R) và kênh màu xanh lá (G) từ camera
   - Kênh R tương ứng với bước sóng ~660nm
   - Kênh G tạm thay thế cho cảm biến IR thông thường (~940nm)
   - Tần suất lấy mẫu: 20-30fps

2. **Xử lý tín hiệu**:
   - Áp dụng bộ lọc để loại bỏ nhiễu
   - Tách thành phần AC (biến thiên) và DC (hằng số) từ tín hiệu
   - Chuẩn hóa tín hiệu để giảm ảnh hưởng của ánh sáng môi trường

3. **Tính toán tỷ lệ R**:
   - R = (AC_Red/DC_Red)/(AC_Green/DC_Green)
   - Tính toán trong cửa sổ trượt 4-5 giây

4. **Chuyển đổi từ R sang SpO2**:
   - Áp dụng công thức thực nghiệm: SpO2 = 110 - 25 * R
   - Áp dụng hiệu chỉnh từ quá trình hiệu chuẩn
   - Làm tròn kết quả tới 1 chữ số thập phân

### Miễn Trừ Trách Nhiệm Y Tế

Ứng dụng cần hiển thị rõ ràng thông báo miễn trừ trách nhiệm:

```
CẢNH BÁO: Tính năng này không phải thiết bị y tế được chứng nhận và chỉ nên được sử dụng cho mục đích tham khảo. Không sử dụng để chẩn đoán hoặc điều trị bệnh. Luôn tham khảo ý kiến bác sĩ về các vấn đề sức khỏe.
```

### Hiệu Chuẩn

1. **Quy trình hiệu chuẩn**:
   - Yêu cầu người dùng nhập giá trị SpO2 từ thiết bị y tế chuẩn
   - Thực hiện đo đồng thời với ứng dụng
   - Tính toán hệ số hiệu chỉnh dựa trên sự khác biệt

2. **Phương pháp thay thế**:
   - Nếu không có thiết bị y tế, sử dụng giá trị mặc định 97% cho người khỏe mạnh
   - Cung cấp cảnh báo về độ chính xác giảm khi không hiệu chuẩn

### Xử Lý Lỗi và Phản Hồi

1. **Ánh sáng không đủ**:
   - Phát hiện độ sáng thấp và tự động bật đèn flash
   - Hướng dẫn điều chỉnh áp lực ngón tay

2. **Chuyển động quá nhiều**:
   - Phát hiện và lọc nhiễu chuyển động
   - Hướng dẫn giữ ngón tay ổn định
   - Tạm dừng đo khi nhiễu quá lớn

3. **Tín hiệu bất thường**:
   - Phát hiện kết quả ngoài phạm vi hợp lý (70-100%)
   - Đề xuất thử lại và cung cấp hướng dẫn chi tiết
   - Ghi log lỗi để phân tích và cải thiện thuật toán

## Phân Loại Kết Quả SpO2

| Phạm vi SpO2 | Phân loại | Hành động | Màu sắc hiển thị |
|--------------|-----------|-----------|------------------|
| 95-100% | Bình thường | Thông báo kết quả bình thường | Xanh lá (#4CAF50) |
| 90-94% | Thấp | Cảnh báo SpO2 thấp, đề xuất theo dõi | Vàng (#FFC107) |
| <90% | Nguy hiểm | Cảnh báo nghiêm trọng, đề xuất tham vấn y tế | Đỏ (#F44336) |

## Tích Hợp Với Các Module Khác

- **HistoryRepository**: Lưu kết quả đo vào cơ sở dữ liệu
- **AIService**: Gửi dữ liệu cho tư vấn sức khỏe
- **NotificationService**: Tạo thông báo cho kết quả bất thường

## Quy Trình Kiểm Thử

1. **Unit Tests**:
   - Kiểm tra thuật toán chuyển đổi từ R sang SpO2
   - Kiểm tra phân loại kết quả
   - Kiểm tra xử lý tín hiệu với dữ liệu mẫu

2. **Instrumentation Tests**:
   - Kiểm tra truy cập camera và đèn flash
   - Kiểm tra luồng UI và phản hồi người dùng
   - Kiểm tra lưu kết quả vào cơ sở dữ liệu

3. **Thực Nghiệm**:
   - So sánh kết quả với máy đo SpO2 y tế
   - Kiểm tra độ lặp lại của kết quả
   - Đánh giá độ chính xác trong các điều kiện khác nhau

## Tiêu Chí Hoàn Thành

1. Ứng dụng có thể đo SpO2 với sai số không quá ±2% so với thiết bị y tế
2. Hiển thị kết quả và phân loại mức độ theo thời gian thực
3. Có khả năng phát hiện và xử lý các trường hợp đo không thành công
4. Lưu trữ và hiển thị xu hướng SpO2 theo thời gian
5. Hoàn thành quy trình hiệu chuẩn và cải thiện độ chính xác

## Giới Hạn Kỹ Thuật

- Camera trên điện thoại không được thiết kế cho mục đích y tế
- Kết quả có thể bị ảnh hưởng bởi loại điện thoại, camera, và điều kiện ánh sáng
- Không thể hoàn toàn thay thế thiết bị y tế chuyên dụng
- Hiệu quả giảm với làn da sẫm màu hoặc thâm quầng

## Đường Dẫn Tài Nguyên

- [API.md](../../API.md) - Định dạng API kết nối
- [Nguyên lý đo SpO2](https://en.wikipedia.org/wiki/Pulse_oximetry)
- [Thuật toán phân tích quang phổ](https://en.wikipedia.org/wiki/Spectral_analysis) 