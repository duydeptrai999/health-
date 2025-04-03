# Quy Trình Làm Việc

## Tổng Quan

Tài liệu này mô tả quy trình làm việc chi tiết để phát triển các tính năng cho ứng dụng StudyAI Health. Quy trình này được thiết kế để đảm bảo chất lượng code, tính nhất quán và hiệu quả trong quá trình phát triển.

## Quy Trình Phát Triển Tính Năng

### 1. Chọn Tính Năng

1. Mở file [Instruction.md](../../Instruction.md) để xem danh sách các tính năng cần triển khai
2. Chọn một tính năng có trạng thái "❌ Not Started"
3. Cập nhật trạng thái tính năng thành "⏳ In Progress" trong file Instruction.md
4. Commit thay đổi với message "Start implementation of [tên tính năng]"

### 2. Nghiên Cứu và Phân Tích

1. Đọc kỹ mô tả tính năng trong file instruction cụ thể
2. Xác định các thành phần cần triển khai
3. Tham khảo API.md để hiểu cấu trúc dữ liệu và API cần sử dụng
4. Nghiên cứu các thuật toán/kỹ thuật cần thiết
5. Tạo phác thảo cấu trúc các thành phần (lớp, phương thức, giao diện)

### 3. Triển Khai Cơ Sở Dữ Liệu (Nếu Cần)

1. Tạo các Entity class cho Room Database
2. Định nghĩa các DAO (Data Access Object)
3. Thiết lập Repository pattern
4. Viết Unit Tests cho các thao tác CRUD
5. Thực hiện kiểm thử và debug

### 4. Triển Khai Logic Nghiệp Vụ

1. Triển khai các core class xử lý logic nghiệp vụ
2. Tạo các ViewModel cần thiết
3. Triển khai các thuật toán phân tích/xử lý dữ liệu
4. Viết Unit Tests cho các logic nghiệp vụ
5. Thực hiện kiểm thử và debug

### 5. Triển Khai Giao Diện Người Dùng

1. Tạo các layout XML cho Activities/Fragments
2. Triển khai các Adapter/ViewHolder cho RecyclerView
3. Thiết kế các custom view nếu cần
4. Liên kết UI với ViewModel thông qua Data Binding/View Binding
5. Triển khai hiệu ứng chuyển động/animation nếu cần
6. Thực hiện Unit Tests cho UI components
7. Thực hiện UI Tests với Espresso nếu cần

### 6. Tích Hợp Với Các Module Khác

1. Xác định các điểm tích hợp với module khác
2. Triển khai các interface/listener để giao tiếp giữa các module
3. Kiểm thử tích hợp để đảm bảo hoạt động đúng
4. Xử lý các trường hợp ngoại lệ/lỗi khi tích hợp

### 7. Kiểm Thử Toàn Diện

1. Thực hiện kiểm thử đơn vị cho các thành phần riêng lẻ
2. Thực hiện kiểm thử tích hợp cho các thành phần liên quan
3. Kiểm thử giao diện người dùng
4. Kiểm thử hiệu suất:
   - Đo thời gian phản hồi
   - Đo mức sử dụng CPU/RAM
   - Đo mức tiêu thụ pin
5. Kiểm thử trên các thiết bị khác nhau

### 8. Tối Ưu Hóa

1. Phân tích hiệu suất sử dụng Android Profiler
2. Tối ưu hóa sử dụng bộ nhớ
3. Tối ưu hóa thời gian phản hồi
4. Tối ưu hóa kích thước APK
5. Tối ưu hóa sử dụng pin/năng lượng

### 9. Hoàn Thành Tính Năng

1. Kiểm tra lại tất cả các tiêu chí hoàn thành trong file instruction
2. Cập nhật trạng thái tính năng thành "✅ Completed" trong file Instruction.md
3. Tạo bản ghi chi tiết trong file Changelog.md
4. Cập nhật Codebase.md với mô tả về các thành phần mới
5. Commit tất cả thay đổi với message "Complete implementation of [tên tính năng]"

## Quy Tắc Commit

1. **Commit nhỏ và thường xuyên**:
   - Mỗi commit nên tập trung vào một thay đổi cụ thể
   - Tránh commit quá nhiều thay đổi cùng lúc

2. **Cấu trúc message commit**:
   ```
   <type>: <description>
   
   [optional body]
   ```

3. **Các loại commit (type)**:
   - `feat`: Tính năng mới
   - `fix`: Sửa lỗi
   - `refactor`: Cải tiến code mà không thêm tính năng/sửa lỗi
   - `docs`: Cập nhật tài liệu
   - `test`: Thêm/sửa test
   - `chore`: Công việc bảo trì khác

4. **Mô tả commit**:
   - Viết ở thì hiện tại đơn (add, fix, update)
   - Không quá 50 ký tự
   - Không kết thúc bằng dấu chấm

5. **Ví dụ**:
   - `feat: implement heart rate analysis algorithm`
   - `fix: correct SpO2 calculation formula`
   - `docs: update API usage documentation`

## Quy Tắc Code Style

### Kotlin

1. **Đặt tên**:
   - Lớp/Interface: PascalCase (`HeartRateAnalyzer`)
   - Hàm/Biến: camelCase (`calculateHeartRate`)
   - Hằng số: SCREAMING_SNAKE_CASE (`MAX_HEART_RATE`)

2. **Định dạng**:
   - Indent: 4 spaces
   - Dòng không quá 100 ký tự
   - Dấu ngoặc nhọn mở trên cùng dòng
   - Dấu ngoặc nhọn đóng trên dòng riêng

3. **Tổ chức code**:
   - Nhóm các hàm liên quan
   - Đặt các hàm private sau các hàm public
   - Sử dụng các extension function khi thích hợp

4. **Các best practices**:
   - Sử dụng val thay vì var khi có thể
   - Sử dụng các hàm scope (`let`, `apply`, `with`, `run`, `also`)
   - Tận dụng các tính năng của Kotlin (null safety, extension functions)

### XML Layout

1. **Đặt tên ID**:
   - Theo mẫu: `[component_type]_[screen]_[description]`
   - Ví dụ: `btn_heart_rate_start`, `tv_results_bpm`

2. **Tổ chức**:
   - Sử dụng ConstraintLayout cho layouts phức tạp
   - Tránh nested ViewGroups sâu (không quá 3 cấp)
   - Sử dụng styles để tái sử dụng thuộc tính

3. **Tài nguyên**:
   - Đặt strings trong `strings.xml`
   - Đặt dimensions trong `dimens.xml`
   - Đặt colors trong `colors.xml`
   - Sử dụng theme attributes khi có thể

## Xử Lý Lỗi và Debug

### Các Công Cụ Debug

1. **Android Studio Debugger**:
   - Đặt breakpoints tại các vị trí cần kiểm tra
   - Kiểm tra giá trị biến trong quá trình thực thi
   - Sử dụng Evaluate Expression để thử nghiệm code

2. **Logcat**:
   - Sử dụng các level log khác nhau (ERROR, WARN, INFO, DEBUG, VERBOSE)
   - Format log để dễ tìm kiếm: `Log.d("HeartRateTag", "Value: $heartRate")`
   - Lọc log theo tag để tập trung vào module đang debug

3. **Android Profiler**:
   - Theo dõi sử dụng CPU
   - Theo dõi sử dụng RAM
   - Phát hiện memory leaks
   - Phân tích network calls

### Quy Trình Xử Lý Lỗi

1. **Xác định lỗi**:
   - Thu thập thông tin chi tiết (log, stack trace)
   - Tái tạo lỗi với các bước cụ thể
   - Xác định điều kiện gây ra lỗi

2. **Phân tích root cause**:
   - Sử dụng debugger để theo dõi luồng thực thi
   - Kiểm tra các giả thuyết về nguyên nhân
   - Tìm nguồn gốc của vấn đề

3. **Sửa lỗi**:
   - Triển khai các thay đổi cần thiết
   - Đảm bảo không tạo ra vấn đề mới
   - Viết test để đảm bảo lỗi không tái xuất hiện

4. **Kiểm tra**:
   - Kiểm tra lỗi đã được sửa
   - Kiểm tra các tình huống liên quan
   - Chạy regression tests

## Quản Lý Tài Nguyên

### Tài Nguyên Hình Ảnh

1. **Định dạng**:
   - Vector Drawable (.xml) cho icons và hình đơn giản
   - WebP cho photos và hình phức tạp
   - Tránh sử dụng PNG/JPEG khi có thể

2. **Kích thước**:
   - Chuẩn bị cho các mật độ màn hình khác nhau (mdpi, hdpi, xhdpi, xxhdpi)
   - Tối ưu hóa kích thước file

3. **Tên tệp**:
   - Sử dụng snake_case
   - Mô tả rõ ràng nội dung
   - Ví dụ: `ic_heart_rate.xml`, `bg_result_card.xml`

### Tài Nguyên Chuỗi

1. **Cấu trúc**:
   - Nhóm các chuỗi liên quan bằng prefix
   - Sử dụng comment để mô tả ngữ cảnh sử dụng

2. **Định dạng**:
   - Sử dụng placeholders: `%1$s`, `%2$d`
   - Sử dụng quantity strings cho số nhiều
   - Tránh hard-coded strings trong code

3. **Hỗ trợ đa ngôn ngữ**:
   - Đặt chuỗi mặc định trong `values/strings.xml`
   - Đặt chuỗi tiếng Việt trong `values-vi/strings.xml`
   - Sử dụng các công cụ dịch thuật để đảm bảo nhất quán

## Tài Liệu Tham Khảo

- [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Android Developer Guides](https://developer.android.com/guide)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [Material Design Guidelines](https://material.io/design)

## Checklist Trước Khi Hoàn Thành

Sử dụng checklist sau đây để đảm bảo tính năng đã sẵn sàng:

- [ ] Tất cả các yêu cầu chức năng đã được triển khai
- [ ] Giao diện người dùng hoạt động trên các kích thước màn hình khác nhau
- [ ] Các unit tests đã được viết và đều pass
- [ ] Không có lỗi trong Logcat khi sử dụng
- [ ] Hiệu suất đáp ứng yêu cầu (thời gian phản hồi, sử dụng bộ nhớ)
- [ ] Đã xử lý các trường hợp ngoại lệ
- [ ] Code đã được refactor và cleanup
- [ ] Đã cập nhật tài liệu liên quan
- [ ] Đã kiểm tra trên ít nhất 2 thiết bị khác nhau
- [ ] Đã xác nhận tất cả các tiêu chí hoàn thành trong file instruction

## Đường Dẫn Tài Nguyên

- [Instruction.md](../../Instruction.md) - Danh sách tính năng cần triển khai
- [API.md](../../API.md) - Thông tin về API và cấu trúc dữ liệu
- [Help.md](../../Help.md) - Mô tả tính năng đã hoàn thành
- [Codebase.md](../../Codebase.md) - Tóm tắt các class trong codebase 