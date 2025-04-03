# Phân Tích Mức Độ Căng Thẳng

## Tổng Quan

Tính năng này phân tích biến thiên nhịp tim (Heart Rate Variability - HRV) để đánh giá mức độ căng thẳng (stress) của người dùng, cung cấp thông tin về trạng thái hoạt động của hệ thần kinh tự chủ.

## Nguyên Lý Hoạt Động

1. **Biến thiên nhịp tim (HRV)**: Đo lường sự thay đổi về khoảng thời gian giữa các nhịp tim liên tiếp.
2. **Hệ thần kinh tự chủ**: HRV phản ánh sự cân bằng giữa hệ thần kinh giao cảm (kích thích) và đối giao cảm (thư giãn).
3. **Chỉ số stress**: Mức độ căng thẳng được tính toán từ các chỉ số HRV, trong đó HRV thấp thường tương ứng với mức độ căng thẳng cao.

## Thành Phần Cần Triển Khai

### 1. StressAnalyzer

**Mô tả**: Core class phân tích dữ liệu nhịp tim và tính toán mức độ căng thẳng.

**Trách nhiệm**:
- Nhận dữ liệu nhịp tim từ HeartRateAnalyzer
- Tính toán các chỉ số HRV
- Phân loại mức độ căng thẳng
- Theo dõi xu hướng theo thời gian

**Chi tiết triển khai**:
- Tính toán SDNN (độ lệch chuẩn của khoảng NN)
- Tính toán RMSSD (căn bậc hai trung bình bình phương hiệu các khoảng NN kề nhau)
- Phân tích tần số thấp (LF) và tần số cao (HF) bằng FFT
- Tính tỷ lệ LF/HF để đánh giá cân bằng thần kinh tự chủ

### 2. HRVProcessor

**Mô tả**: Class chuyên xử lý và tính toán các chỉ số HRV từ dữ liệu nhịp tim thô.

**Trách nhiệm**:
- Tiền xử lý dữ liệu nhịp tim
- Phát hiện và loại bỏ nhịp bất thường (arrhythmia)
- Tính toán các tham số HRV theo miền thời gian
- Thực hiện biến đổi Fourier nhanh (FFT) cho phân tích miền tần số

**Chi tiết triển khai**:
- Thuật toán loại bỏ nhiễu và nhịp bất thường
- Cửa sổ trượt cho phân tích liên tục
- Triển khai FFT nhanh với độ phân giải phù hợp
- Tối ưu hóa thuật toán cho thiết bị di động

### 3. StressHistoryView

**Mô tả**: Component hiển thị lịch sử và xu hướng mức độ căng thẳng.

**Trách nhiệm**:
- Hiển thị mức độ căng thẳng hiện tại
- Vẽ biểu đồ xu hướng theo thời gian
- Cung cấp các chỉ số thống kê
- Hiển thị phân loại mức độ căng thẳng

**Chi tiết triển khai**:
- Biểu đồ đường hiển thị xu hướng 7 ngày gần nhất
- Mã màu cho các mức độ căng thẳng khác nhau
- Hiển thị thời điểm cao/thấp nhất trong ngày
- Tùy chọn xem theo ngày/tuần/tháng

### 4. RecommendationEngine

**Mô tả**: Engine đề xuất bài tập và hoạt động giảm căng thẳng dựa trên mức độ đo được.

**Trách nhiệm**:
- Phân tích mức độ và kiểu căng thẳng
- Đề xuất các bài tập phù hợp
- Cung cấp hướng dẫn chi tiết
- Theo dõi hiệu quả của các bài tập đề xuất

**Chi tiết triển khai**:
- Cơ sở dữ liệu bài tập thư giãn và thiền
- Thuật toán ghép cặp mức độ căng thẳng với bài tập phù hợp
- Giao diện hiển thị hướng dẫn từng bước
- Theo dõi và báo cáo tiến trình

## Yêu Cầu Triển Khai Chi Tiết

### Quy Trình Đo Và Phân Tích

1. **Thu thập dữ liệu**:
   - Yêu cầu ít nhất 2 phút dữ liệu nhịp tim liên tục
   - Tần suất lấy mẫu: tối thiểu 1 mẫu/giây
   - Người dùng cần ở trạng thái nghỉ ngơi để có kết quả chính xác

2. **Tính toán HRV miền thời gian**:
   - SDNN: Độ lệch chuẩn của khoảng NN
   - RMSSD: Căn bậc hai trung bình bình phương hiệu các khoảng NN kề nhau
   - pNN50: Tỷ lệ phần trăm các khoảng NN liên tiếp khác nhau hơn 50ms

3. **Phân tích HRV miền tần số**:
   - VLF (Very Low Frequency): 0.003-0.04 Hz
   - LF (Low Frequency): 0.04-0.15 Hz, phản ánh hoạt động giao cảm và đối giao cảm
   - HF (High Frequency): 0.15-0.4 Hz, phản ánh hoạt động đối giao cảm
   - Tỷ lệ LF/HF: Đánh giá cân bằng thần kinh tự chủ

4. **Tính toán chỉ số căng thẳng**:
   - Chỉ số căng thẳng Baevsky (SI) = AMo/(2 × Mo × MxDMn)
     - AMo: Biên độ mode (% của khoảng R-R phổ biến nhất)
     - Mo: Mode (khoảng R-R phổ biến nhất)
     - MxDMn: Độ biến thiên R-R (Max - Min)
   - Giá trị SI cao = mức độ căng thẳng cao

### Phân Loại Mức Độ Căng Thẳng

| Giá trị SI | Phân loại | Mô tả | Màu hiển thị |
|------------|-----------|-------|--------------|
| <50 | Thư giãn | Trạng thái thư giãn sâu, hồi phục tốt | Xanh dương (#2196F3) |
| 50-100 | Bình thường | Mức độ căng thẳng bình thường trong cuộc sống | Xanh lá (#4CAF50) |
| 100-150 | Căng thẳng nhẹ | Mức độ căng thẳng nhẹ, có thể cần giảm nhẹ | Vàng (#FFC107) |
| 150-200 | Căng thẳng vừa | Trạng thái căng thẳng đáng kể, cần can thiệp | Cam (#FF9800) |
| >200 | Căng thẳng cao | Mức độ căng thẳng cao, cần giảm căng thẳng ngay | Đỏ (#F44336) |

### Thuật Toán Đề Xuất Bài Tập

1. **Căng thẳng nhẹ**:
   - Bài tập thở sâu (5-10 phút)
   - Đi bộ nhẹ nhàng (15-20 phút)
   - Nghe nhạc thư giãn

2. **Căng thẳng vừa**:
   - Thiền tập trung (10-15 phút)
   - Yoga nhẹ nhàng (20-30 phút)
   - Kỹ thuật thư giãn cơ tiến triển

3. **Căng thẳng cao**:
   - Thiền chánh niệm (15-20 phút)
   - Kỹ thuật thở 4-7-8
   - Tập yoga định hướng thư giãn
   - Đề xuất tham vấn chuyên gia nếu kéo dài

### Xử Lý Dữ Liệu Không Đầy Đủ

1. **Dữ liệu thiếu**:
   - Yêu cầu tối thiểu 80% dữ liệu hợp lệ trong cửa sổ 2 phút
   - Nội suy tuyến tính cho các khoảng thiếu nhỏ (<3 nhịp)
   - Thông báo khi chất lượng dữ liệu không đủ

2. **Dữ liệu nhiễu**:
   - Áp dụng bộ lọc cho nhịp tim bất thường
   - Loại bỏ dữ liệu ngoại lai (>3 độ lệch chuẩn)
   - Sử dụng cửa sổ trượt để giảm ảnh hưởng của nhiễu đột biến

3. **Xử lý dữ liệu thiếu tính liên tục**:
   - Tích hợp dữ liệu từ nhiều lần đo trong ngày
   - Tính toán trung bình theo khung giờ
   - Ưu tiên dữ liệu được đo trong điều kiện nghỉ ngơi

## Tích Hợp Với Các Module Khác

- **HeartRateAnalyzer**: Nhận dữ liệu nhịp tim để phân tích
- **HistoryRepository**: Lưu kết quả phân tích vào cơ sở dữ liệu
- **AIService**: Gửi dữ liệu cho tư vấn sức khỏe tâm lý
- **NotificationService**: Thông báo khi phát hiện căng thẳng cao

## Giao Diện Người Dùng

1. **Màn hình phân tích**:
   - Hiển thị mức độ căng thẳng hiện tại với hình ảnh minh họa
   - Biểu đồ sóng HRV theo thời gian thực
   - Hiển thị các chỉ số HRV chính (SDNN, RMSSD, LF/HF)
   - Nút bắt đầu/kết thúc phân tích

2. **Màn hình lịch sử**:
   - Biểu đồ xu hướng 7 ngày gần nhất
   - Phân tích theo khung giờ trong ngày
   - Thống kê mức độ căng thẳng trung bình/cao nhất
   - Tùy chọn xem chi tiết theo ngày

3. **Màn hình đề xuất**:
   - Danh sách bài tập giảm căng thẳng phù hợp
   - Hướng dẫn từng bước với hình ảnh/video
   - Tính năng hẹn giờ và nhắc nhở
   - Đánh giá hiệu quả sau khi hoàn thành bài tập

## Quy Trình Kiểm Thử

1. **Unit Tests**:
   - Kiểm tra thuật toán tính toán HRV với dữ liệu mẫu
   - Kiểm tra phân loại mức độ căng thẳng
   - Kiểm tra thuật toán đề xuất bài tập

2. **Instrumentation Tests**:
   - Kiểm tra quy trình phân tích đầy đủ
   - Kiểm tra hiển thị UI với các mức độ căng thẳng khác nhau
   - Kiểm tra lưu trữ và truy xuất lịch sử

3. **Thực Nghiệm**:
   - So sánh kết quả với các thiết bị đo HRV chuyên dụng
   - Đánh giá độ chính xác trong điều kiện khác nhau
   - Kiểm tra với nhiều đối tượng người dùng

## Tiêu Chí Hoàn Thành

1. Ứng dụng có thể phân tích mức độ căng thẳng từ dữ liệu nhịp tim với độ chính xác tương đối
2. Hiển thị kết quả phân tích và xu hướng trong 7 ngày gần nhất
3. Đề xuất bài tập giảm căng thẳng phù hợp với mức độ được phát hiện
4. Lưu trữ và hiển thị lịch sử phân tích thành công
5. Hoạt động ổn định với ít nhất 3 lần đo mỗi ngày

## Giới Hạn Kỹ Thuật

- HRV phụ thuộc vào nhiều yếu tố (tuổi, giới tính, thể trạng, thời gian trong ngày)
- Chỉ nên so sánh dữ liệu của cùng một người trong các điều kiện tương tự
- Kết quả mang tính tham khảo, không thay thế đánh giá y tế chuyên nghiệp
- Độ chính xác bị ảnh hưởng bởi chất lượng đo nhịp tim

## Đường Dẫn Tài Nguyên

- [API.md](../../API.md) - Endpoints lấy gợi ý thư giãn
- [Về HRV](https://en.wikipedia.org/wiki/Heart_rate_variability)
- [Phân tích tần số HRV](https://en.wikipedia.org/wiki/Frequency-domain_analysis) 