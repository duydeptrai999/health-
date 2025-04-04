# Hướng Dẫn và Tài Liệu Hỗ Trợ

## Tính Năng Đo SpO2 (Nồng Độ Oxy Trong Máu) (05/05/2024)

### Tổng Quan
Tính năng đo SpO2 cho phép người dùng đo nồng độ oxy trong máu bằng cách sử dụng camera và đèn flash của điện thoại. Phép đo này chỉ mang tính tham khảo và không thay thế cho thiết bị y tế chuyên dụng.

### Cách Sử Dụng
1. **Mở tính năng đo SpO2:**
   - Từ màn hình chính, chọn "SpO2" trong các tùy chọn đo lường nhanh 
   - Hoặc chọn "SpO2" từ bottom navigation bar

2. **Miễn trừ trách nhiệm y tế:**
   - Đọc và chấp nhận miễn trừ trách nhiệm y tế trước khi sử dụng
   - Nhấn "I Understand" để tiếp tục

3. **Chuẩn bị đo:**
   - Đặt đầu ngón tay che kín camera và đèn flash
   - Giữ ngón tay ổn định và không nhấn quá mạnh
   - Nhấn "Start Measurement" để bắt đầu đo

4. **Trong quá trình đo:**
   - Quy trình đo kéo dài khoảng 20 giây
   - Thanh tiến trình hiển thị quá trình đo
   - Giữ ngón tay ổn định cho đến khi hoàn thành
   - Giá trị đo hiện tại sẽ hiển thị trong quá trình đo

5. **Kết quả đo:**
   - Kết quả sẽ hiển thị dưới dạng % và được phân loại như sau:
     * 95-100%: Bình thường (màu xanh lá)
     * 90-94%: Thấp (màu vàng) - Theo dõi thêm
     * <90%: Nguy hiểm (màu đỏ) - Tham vấn y tế
   - Nhấn "Save Result" để lưu kết quả vào lịch sử
   - Nhấn "Measure Again" để thực hiện phép đo mới

### Lưu Ý Quan Trọng
- Đảm bảo ngón tay sạch và không bị lạnh
- Tránh đo trong điều kiện ánh sáng yếu hoặc quá sáng
- Kết quả có thể sai lệch ±2% so với thiết bị y tế chuyên dụng
- Nếu gặp lỗi, hãy thử lau ống kính camera và đảm bảo ngón tay che kín cả camera lẫn đèn flash
- Đây không phải là thiết bị y tế, không sử dụng để chẩn đoán hoặc điều trị bệnh
- Nếu kết quả dưới 90% liên tục, hãy tham khảo ý kiến bác sĩ

### Xem Lịch Sử SpO2
- Vào phần "History" (Lịch sử) từ menu chính
- Lọc kết quả theo "SpO2" để xem lịch sử đo
- Biểu đồ xu hướng sẽ hiển thị sự thay đổi SpO2 theo thời gian

## Cải Thiện Tài Liệu UI Guidelines (03/04/2024)

### Tổng Quan
Dựa trên mẫu thiết kế được cung cấp, tài liệu UI Guidelines đã được cập nhật và chi tiết hóa để giúp developers triển khai chính xác và nhất quán các thành phần giao diện.

### Các Cải Thiện Chính
1. **Dashboard Health:**
   - Thêm chi tiết về header section với welcome message và INSIGHT TIMER
   - Mô tả cụ thể về Health Metrics Cards theo layout 2 cột
   - Thông số chi tiết về Weekly Progress card với vòng tròn tiến độ 65%
   - Hướng dẫn về thiết kế Learning section với hình minh họa

2. **Màn Hình Profile & Thống Kê:**
   - Chi tiết về tab navigation system cho chuyển đổi giữa Kcal, Water, Steps
   - Hướng dẫn triển khai biểu đồ "This Week Details" theo mẫu
   - Thông số cụ thể cho biểu đồ water tracking với tooltips
   - Mô tả Weekly Performance card với gradients và tỷ lệ % khác nhau

3. **Màu Sắc & Gradients:**
   - Bổ sung bảng màu mở rộng cho các metrics cụ thể (calorie, nước uống, số bước)
   - Định nghĩa gradients cụ thể cho các loại cards khác nhau
   - Hướng dẫn về backgrounds cho charts với mã màu cụ thể

4. **Trực Quan Hóa Dữ Liệu:**
   - Chi tiết hóa thông số kỹ thuật cho biểu đồ đường (line width, point radius)
   - Hướng dẫn cụ thể cho biểu đồ cột (column width, spacing, corner radius)
   - Thông số cho circular progress indicators (track color, stroke width)
   - Yêu cầu về tooltips và data labels khi tương tác với charts

### Cách Sử Dụng
1. Tham khảo tài liệu chi tiết tại `instructions/design/ui_guidelines.md`
2. Áp dụng các thông số cụ thể (kích thước, màu sắc, bố cục) theo mô tả
3. Đảm bảo nhất quán giữa các thành phần theo hướng dẫn
4. Kiểm tra sự phù hợp với mẫu thiết kế trước khi hoàn thiện

### Lưu Ý Quan Trọng
- Tuân thủ nghiêm ngặt hệ thống màu sắc và gradients đã định nghĩa
- Đảm bảo các thành phần trực quan hóa dữ liệu (charts, graphs) có tương tác đúng
- Triển khai responsive design cho các kích thước màn hình khác nhau
- Bản cập nhật này là phần bổ sung cho tài liệu design_system.md hiện có 