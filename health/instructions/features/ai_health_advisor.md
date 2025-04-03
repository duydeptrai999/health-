# Tư Vấn Sức Khỏe Với AI

## Tổng Quan

Tính năng này tích hợp trí tuệ nhân tạo để phân tích dữ liệu sức khỏe của người dùng, cung cấp lời khuyên cá nhân hóa, và cho phép người dùng tương tác thông qua giao diện chat để hỏi đáp về các vấn đề sức khỏe.

## Nguyên Lý Hoạt Động

1. **Phân tích dữ liệu**: AI phân tích dữ liệu sức khỏe thu thập từ các tính năng khác (nhịp tim, SpO2, mức độ căng thẳng).
2. **Cá nhân hóa lời khuyên**: Dựa trên dữ liệu và mục tiêu cá nhân để đưa ra lời khuyên phù hợp.
3. **Tương tác hai chiều**: Cho phép người dùng đặt câu hỏi và nhận câu trả lời từ AI về sức khỏe.

## Thành Phần Cần Triển Khai

### 1. AIService

**Mô tả**: Service core xử lý giao tiếp với API AI, gửi dữ liệu và nhận phản hồi.

**Trách nhiệm**:
- Quản lý kết nối với API AI
- Xử lý việc gửi dữ liệu sức khỏe lên server
- Nhận và phân tích phản hồi từ AI
- Xử lý lỗi và kết nối offline

**Chi tiết triển khai**:
- Sử dụng Retrofit cho HTTP requests
- Triển khai caching cho hoạt động offline
- Mã hóa dữ liệu trước khi truyền tải
- Quản lý authentication và token

### 2. ChatViewModel

**Mô tả**: ViewModel quản lý trạng thái của chat và dữ liệu trao đổi.

**Trách nhiệm**:
- Quản lý lịch sử chat
- Xử lý việc gửi tin nhắn mới
- Cập nhật UI khi có phản hồi
- Lưu trữ lịch sử hội thoại

**Chi tiết triển khai**:
- Sử dụng LiveData/StateFlow để cập nhật UI
- Triển khai pagination cho lịch sử chat dài
- Xử lý typing indicators và trạng thái gửi
- Lưu cache tin nhắn cho trường hợp offline

### 3. MessageAdapter

**Mô tả**: Adapter hiển thị các tin nhắn trong giao diện chat.

**Trách nhiệm**:
- Hiển thị các loại tin nhắn khác nhau
- Render định dạng phong phú (text, link, hình ảnh)
- Hiển thị các indicator (đã gửi, đã đọc)
- Xử lý tương tác người dùng với tin nhắn

**Chi tiết triển khai**:
- RecyclerView với nhiều loại ViewHolder
- Hỗ trợ markdown hoặc định dạng rich text
- Hỗ trợ các thao tác sao chép, chia sẻ
- Tối ưu hiệu suất render cho chat dài

### 4. RecommendationProcessor

**Mô tả**: Thành phần xử lý và định dạng các khuyến nghị từ AI.

**Trách nhiệm**:
- Phân tích phản hồi AI để tách lời khuyên
- Phân loại lời khuyên theo danh mục
- Ưu tiên các khuyến nghị theo mức độ quan trọng
- Theo dõi việc thực hiện các khuyến nghị

**Chi tiết triển khai**:
- Phân tích cấu trúc JSON từ API
- Áp dụng các luật tùy chỉnh để phân loại
- System cho phép người dùng đánh dấu đã thực hiện
- Tích hợp với hệ thống thông báo để nhắc nhở

## Yêu Cầu Triển Khai Chi Tiết

### API Integration

1. **Endpoints**:
   - `/api/health/analyze`: Gửi dữ liệu sức khỏe để phân tích
   - `/api/chat/messages`: Gửi và nhận tin nhắn chat
   - `/api/recommendations`: Lấy và cập nhật khuyến nghị

2. **Định dạng request**:
   ```json
   {
     "user_id": "string",
     "health_data": {
       "heart_rate": [
         {"timestamp": 1623456789, "value": 72, "confidence": 0.95}
       ],
       "spo2": [
         {"timestamp": 1623456789, "value": 98, "confidence": 0.92}
       ],
       "stress_level": [
         {"timestamp": 1623456789, "value": 65, "stress_category": "medium"}
       ]
     },
     "query": "string",
     "context": {
       "previous_messages": [
         {"role": "user", "content": "string", "timestamp": 1623456789},
         {"role": "assistant", "content": "string", "timestamp": 1623456789}
       ]
     }
   }
   ```

3. **Định dạng response**:
   ```json
   {
     "message_id": "string",
     "content": "string",
     "recommendations": [
       {
         "id": "string",
         "category": "exercise|nutrition|sleep|stress|general",
         "priority": 1-5,
         "content": "string",
         "explanation": "string",
         "references": ["string"]
       }
     ],
     "analysis": {
       "overall_health_score": 0-100,
       "areas_of_concern": ["string"],
       "positive_trends": ["string"]
     }
   }
   ```

### Bảo Mật Dữ Liệu

1. **Mã hóa dữ liệu**:
   - Sử dụng HTTPS cho tất cả API calls
   - Mã hóa dữ liệu nhạy cảm trên thiết bị
   - Xóa dữ liệu sau khi phân tích hoàn tất

2. **Quản lý quyền riêng tư**:
   - Cho phép người dùng kiểm soát dữ liệu được chia sẻ
   - Tùy chọn tắt lưu trữ dữ liệu trên server
   - Xóa dữ liệu theo yêu cầu

3. **Xác thực**:
   - JWT authentication
   - Refresh token rotation
   - Rate limiting để ngăn abuse

### Chức Năng Chat

1. **Giao diện người dùng**:
   - Text input với microphone option
   - Hỗ trợ gửi hình ảnh và tài liệu
   - Hiển thị typing indicator
   - Cửa sổ chat tự động cuộn

2. **Xử lý ngôn ngữ tự nhiên**:
   - Hỗ trợ tiếng Việt và tiếng Anh
   - Hiểu các biệt ngữ và thuật ngữ y tế
   - Xử lý lỗi chính tả
   - Context-aware để duy trì cuộc trò chuyện liên tục

3. **Tính năng nâng cao**:
   - Gợi ý câu hỏi phổ biến
   - Phát hiện tình huống khẩn cấp
   - Lưu trữ và tìm kiếm lịch sử chat
   - Xuất lịch sử trò chuyện

### Hỗ Trợ Offline

1. **Caching dữ liệu**:
   - Lưu trữ các câu trả lời phổ biến offline
   - Duy trì trạng thái chat khi mất kết nối
   - Queue tin nhắn để gửi khi có kết nối

2. **Phản hồi tự động offline**:
   - Model AI nhẹ chạy trên thiết bị
   - Trả lời các câu hỏi cơ bản về sức khỏe
   - Thông báo giới hạn khi offline

3. **Đồng bộ hóa**:
   - Tự động đồng bộ tin nhắn khi có kết nối
   - Thông báo xung đột nếu có
   - Quản lý merge lịch sử chat

### Phân Tích Dữ Liệu Sức Khỏe

1. **Phát hiện xu hướng**:
   - Phân tích dữ liệu từ 7-30 ngày
   - Phát hiện sự thay đổi đáng kể
   - Dự đoán xu hướng tiềm năng

2. **Cá nhân hóa lời khuyên**:
   - Dựa trên profile người dùng (tuổi, giới tính, mục tiêu)
   - Điều chỉnh theo tiến trình
   - Tích hợp thông tin mùa/thời tiết

3. **Đánh giá tổng thể**:
   - Điểm sức khỏe tổng hợp (0-100)
   - Báo cáo chi tiết theo từng lĩnh vực
   - So sánh với tiêu chuẩn và mục tiêu

## Giao Diện Người Dùng

1. **Màn hình chính**:
   - Chat interface với AI
   - Tóm tắt lời khuyên mới nhất
   - Shortcuts đến báo cáo sức khỏe

2. **Màn hình khuyến nghị**:
   - Danh sách lời khuyên được phân loại
   - Tùy chọn đánh dấu đã thực hiện
   - Biểu đồ theo dõi tiến trình

3. **Màn hình phân tích**:
   - Biểu đồ tổng quan sức khỏe
   - Các chỉ báo và xu hướng
   - Liên kết đến dữ liệu chi tiết

## Đặc Điểm Trí Tuệ Nhân Tạo

1. **Khả năng**:
   - Hiểu và trả lời câu hỏi y tế cơ bản
   - Phân tích dữ liệu sức khỏe
   - Đề xuất cải thiện lối sống
   - Giải thích thuật ngữ y khoa

2. **Giới hạn**:
   - Không chẩn đoán bệnh (chỉ đề xuất tham vấn bác sĩ)
   - Không kê đơn thuốc
   - Không thay thế tư vấn y tế chuyên nghiệp
   - Độ chính xác phụ thuộc vào dữ liệu đầu vào

3. **Miễn trừ trách nhiệm**:
   - Hiển thị rõ ràng khi AI không chắc chắn
   - Cảnh báo khi phát hiện vấn đề tiềm ẩn
   - Khuyến nghị tham vấn bác sĩ trong trường hợp cần thiết

## Tích Hợp Với Các Module Khác

- **HeartRateAnalyzer**: Nhận dữ liệu nhịp tim
- **OxygenSaturationAnalyzer**: Nhận dữ liệu SpO2
- **StressAnalyzer**: Nhận dữ liệu mức độ căng thẳng
- **HistoryRepository**: Lấy dữ liệu lịch sử
- **NotificationService**: Gửi nhắc nhở thực hiện khuyến nghị

## Quy Trình Kiểm Thử

1. **Unit Tests**:
   - Kiểm tra xử lý dữ liệu API
   - Kiểm tra logic phân loại khuyến nghị
   - Kiểm tra caching và xử lý offline

2. **Instrumentation Tests**:
   - Kiểm tra giao diện chat
   - Kiểm tra hiệu suất với nhiều tin nhắn
   - Kiểm tra tương tác người dùng

3. **User Acceptance Testing**:
   - Kiểm tra độ chính xác của câu trả lời
   - Kiểm tra tính hữu ích của lời khuyên
   - Đánh giá trải nghiệm người dùng tổng thể

## Tiêu Chí Hoàn Thành

1. AI trả lời chính xác các câu hỏi sức khỏe phổ biến trong thời gian <2 giây
2. Khuyến nghị phù hợp với dữ liệu sức khỏe và mục tiêu người dùng
3. Giao diện chat hoạt động mượt mà với pagination và caching
4. Hệ thống hoạt động ổn định trong điều kiện kết nối kém
5. Đảm bảo bảo mật và quyền riêng tư của dữ liệu người dùng

## Giới Hạn Kỹ Thuật

- Độ chính xác của AI phụ thuộc vào dữ liệu huấn luyện và cập nhật
- Thời gian phản hồi có thể chậm hơn trên kết nối mạng kém
- Phiên bản offline có khả năng giới hạn
- Không thể thay thế ý kiến y tế chuyên nghiệp

## Đường Dẫn Tài Nguyên

- [API.md](../../API.md) - Endpoints AI và cấu trúc requests
- [Thư viện MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) - Hiển thị biểu đồ
- [Retrofit](https://square.github.io/retrofit/) - HTTP client
- [Room](https://developer.android.com/training/data-storage/room) - Local database 