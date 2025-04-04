# Hướng dẫn phát triển

## Tổng quan

Tài liệu này chứa hướng dẫn chi tiết để phát triển ứng dụng sức khỏe StudyAI Health. Hãy đọc [Project.md](Project.md) trước để hiểu tổng quan về dự án.

## Cài đặt môi trường phát triển

Tham khảo hướng dẫn chi tiết tại [instructions/setup/environment.md](instructions/setup/environment.md).

1. Cài đặt các công cụ cần thiết:

   - Android Studio (Chipmunk hoặc mới hơn)
   - JDK 11+
   - Git

2. Thiết lập cấu hình:

   - Clone repo từ GitHub
   - Sync Gradle dependencies
   - Cấu hình API key trong local.properties (nếu cần)

3. Khởi động ứng dụng:
   - Chọn device/emulator > Run 'app'

## Các tính năng cần triển khai

### 1. Đo nhịp tim qua camera ❌

**Mô tả**: Sử dụng camera điện thoại để đo nhịp tim thông qua phân tích thay đổi màu sắc trên đầu ngón tay.

**Xem hướng dẫn chi tiết tại**: [instructions/features/heart_rate_measurement.md](instructions/features/heart_rate_measurement.md)

**Thành phần**:

- HeartRateActivity
- CameraProcessor
- HeartRateAnalyzer
- ResultView

**Yêu cầu chức năng**:

- Truy cập camera và đèn flash
- Phân tích luồng video theo thuật toán PPG
- Hiển thị kết quả nhịp tim thời gian thực
- Lưu lịch sử kết quả đo

**Ràng buộc**:

- Độ chính xác tối thiểu ±5 BPM
- Thời gian đo tối đa 45 giây
- Thông báo khi môi trường ánh sáng không đủ

**Tham chiếu**:

- [API.md](API.md) - Định dạng dữ liệu lưu trữ

**Tiêu chí hoàn thành**:

- Đo được nhịp tim trong điều kiện ánh sáng khác nhau
- Hiển thị và lưu kết quả thành công

### 2. Đo SpO2 qua camera ✅

**Mô tả**: Đo nồng độ oxy trong máu thông qua phân tích quang phổ từ camera.

**Xem hướng dẫn chi tiết tại**: [instructions/features/spo2_measurement.md](instructions/features/spo2_measurement.md)

**Thành phần**:

- SpO2Activity
- OxygenSaturationAnalyzer
- ResultProcessor
- CalibrationHelper

**Yêu cầu chức năng**:

- Truy cập camera với đèn flash
- Phân tích tỷ lệ ánh sáng đỏ/hồng ngoại
- Hiển thị kết quả SpO2 và sức khỏe tương ứng
- Lưu kết quả vào lịch sử

**Ràng buộc**:

- Hiển thị miễn trừ trách nhiệm y tế
- Cảnh báo khi SpO2 dưới 95%
- Yêu cầu hiệu chuẩn ban đầu

**Tham chiếu**:

- [API.md](API.md) - Định dạng API kết nối

**Tiêu chí hoàn thành**:

- Đo SpO2 với sai số ±2% so với thiết bị y tế
- Hiển thị xu hướng kết quả theo thời gian

### 3. Phân tích mức độ căng thẳng ❌

**Mô tả**: Phân tích biến thiên nhịp tim (HRV) để đánh giá mức độ căng thẳng.

**Xem hướng dẫn chi tiết tại**: [instructions/features/stress_analysis.md](instructions/features/stress_analysis.md)

**Thành phần**:

- StressAnalyzer
- HRVProcessor
- StressHistoryView
- RecommendationEngine

**Yêu cầu chức năng**:

- Thu thập dữ liệu HRV từ nhịp tim
- Áp dụng FFT phân tích tần số
- Phân loại mức căng thẳng (thấp/trung bình/cao)
- Đề xuất bài tập thư giãn

**Ràng buộc**:

- Thời gian phân tích tối thiểu 2 phút
- Lưu xu hướng trong 7 ngày gần nhất
- Yêu cầu ít nhất 3 lần đo để có kết quả chính xác

**Tham chiếu**:

- [API.md](API.md) - Endpoints lấy gợi ý thư giãn

**Tiêu chí hoàn thành**:

- Hiển thị chính xác mức căng thẳng
- Cung cấp báo cáo xu hướng theo thời gian

### 4. Lưu trữ và phân tích lịch sử ❌

**Mô tả**: Lưu trữ và hiển thị lịch sử các chỉ số sức khỏe theo thời gian.

**Xem hướng dẫn chi tiết tại**: [instructions/features/health_history.md](instructions/features/health_history.md)

**Thành phần**:

- HealthDatabase
- HistoryRepository
- ChartViewModel
- HistoryFragment

**Yêu cầu chức năng**:

- Lưu trữ tất cả kết quả đo (nhịp tim, SpO2, stress)
- Hiển thị biểu đồ theo ngày/tuần/tháng
- Xuất dữ liệu dạng PDF/CSV
- Phân tích xu hướng và đưa cảnh báo

**Ràng buộc**:

- Dữ liệu phải được mã hóa
- Giới hạn lưu trữ offline 6 tháng
- Tối ưu hiệu suất query trên dataset lớn

**Tham chiếu**:

- [API.md](API.md) - Cấu trúc database

**Tiêu chí hoàn thành**:

- Hiển thị biểu đồ mượt mà với dataset >1000 điểm
- Xuất báo cáo thành công

### 5. Tư vấn sức khỏe với AI ❌

**Mô tả**: Tích hợp AI để tư vấn sức khỏe dựa trên dữ liệu đo được.

**Xem hướng dẫn chi tiết tại**: [instructions/features/ai_health_advisor.md](instructions/features/ai_health_advisor.md)

**Thành phần**:

- AIService
- ChatViewModel
- MessageAdapter
- RecommendationProcessor

**Yêu cầu chức năng**:

- Gửi dữ liệu sức khỏe lên API
- Nhận và hiển thị lời khuyên
- Chat realtime về vấn đề sức khỏe
- Lưu lịch sử hội thoại

**Ràng buộc**:

- Bảo mật dữ liệu người dùng
- Thời gian phản hồi <2 giây
- Hỗ trợ offline với câu trả lời có sẵn

**Tham chiếu**:

- [API.md](API.md) - Endpoints AI và cấu trúc request

**Tiêu chí hoàn thành**:

- AI trả lời chính xác các câu hỏi sức khỏe
- Tư vấn phù hợp với chỉ số đo được

### 6. Lên lịch trình và nhắc nhở ❌

**Mô tả**: Tạo lịch trình hoạt động, đo chỉ số và chế độ dinh dưỡng.

**Xem hướng dẫn chi tiết tại**: [instructions/features/schedule_reminder.md](instructions/features/schedule_reminder.md)

**Thành phần**:

- ScheduleManager
- NotificationService
- DietPlanGenerator
- ReminderRepository

**Yêu cầu chức năng**:

- Tạo lịch đo chỉ số định kỳ
- Nhắc nhở uống nước, tập thể dục
- Đề xuất thực đơn dựa trên chỉ số sức khỏe
- Thông báo theo thời gian thực

**Ràng buộc**:

- Tùy chỉnh thời gian nhắc
- Hoạt động khi app đã đóng
- Tối ưu pin khi chạy background

**Tham chiếu**:

- [API.md](API.md) - Format thực đơn và hoạt động

**Tiêu chí hoàn thành**:

- Thông báo đúng giờ đã hẹn
- Tạo lịch trình phù hợp với dữ liệu người dùng

## Thiết kế giao diện ❌

**Mô tả**: Thiết kế giao diện người dùng hiện đại, thân thiện và dễ sử dụng với Jetpack Compose.

**Xem hướng dẫn chi tiết tại**: [instructions/design/ui_guidelines.md](instructions/design/ui_guidelines.md)

**Tham khảo thêm**:
- [instructions/design/design_system.md](instructions/design/design_system.md) - Hệ thống thiết kế
- [instructions/design/components.md](instructions/design/components.md) - Thư viện component

**Thành phần**:

- ThemeManager
- ComponentLibrary
- NavigationSystem 
- AnimationProcessor

**Yêu cầu chức năng**:

- Xây dựng hệ thống Material 3 theme hoàn chỉnh
- Tạo các composable components tái sử dụng
- Thiết kế giao diện responsive cho mọi kích thước màn hình
- Animations và transitions mượt mà

**Ràng buộc**:

- Hỗ trợ Dark/Light mode
- Accessibility đạt tiêu chuẩn WCAG AA
- Thời gian render không quá 16ms/frame
- Đa ngôn ngữ (Tiếng Anh, Tiếng Việt)

**Tiêu chí hoàn thành**:

- Giao diện nhất quán trên tất cả màn hình
- UI tests thành công trên ít nhất 5 kích thước màn hình khác nhau
- Performance benchmark đạt chỉ số đặt ra

## Quy trình làm việc

Xem hướng dẫn chi tiết về quy trình làm việc tại [instructions/workflow/process.md](instructions/workflow/process.md).

1. Chọn một tính năng chưa được triển khai (đánh dấu ❌)
2. Đánh dấu tính năng đang triển khai (⏳)
3. Triển khai theo các yêu cầu chức năng
4. Kiểm tra theo tiêu chí hoàn thành
5. Đánh dấu tính năng đã hoàn thành (✅)
6. Cập nhật Changelog.md với các thay đổi
7. Cập nhật Codebase.md với mô tả về các thành phần mới

## Legend

- ✅ Completed
- ⏳ In Progress
- ❌ Not Started
