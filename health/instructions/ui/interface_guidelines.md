# Hướng Dẫn Giao Diện Người Dùng

## Tổng Quan

Tài liệu này định nghĩa các tiêu chuẩn và hướng dẫn thiết kế giao diện người dùng cho ứng dụng StudyAI Health, đảm bảo trải nghiệm nhất quán, trực quan và thân thiện trên toàn bộ ứng dụng.

## Nguyên Tắc Thiết Kế

### 1. Đơn Giản và Rõ Ràng
- Thiết kế tối giản, tập trung vào nội dung
- Loại bỏ các yếu tố gây xao nhãng
- Sử dụng ngôn ngữ đơn giản và dễ hiểu

### 2. Nhất Quán
- Sử dụng đồng nhất các thành phần UI trên toàn ứng dụng
- Cùng một hành động phải có cùng một cách thực hiện
- Duy trì nhất quán về màu sắc, font chữ và kiểu dáng

### 3. Phản Hồi và Tương Tác
- Cung cấp phản hồi tức thì cho mọi tương tác
- Sử dụng hiệu ứng chuyển động có ý nghĩa
- Hiển thị trạng thái hệ thống một cách rõ ràng

### 4. Tối Ưu Hóa Quy Trình
- Giảm thiểu số lượng bước để hoàn thành tác vụ
- Ưu tiên các tác vụ thường xuyên sử dụng
- Thiết kế cho tính hiệu quả và tốc độ

## Bảng Màu

### Màu Chính
- **Primary Color**: #2196F3 (Blue)
- **Primary Dark**: #1976D2
- **Primary Light**: #BBDEFB
- **Accent Color**: #FF4081 (Pink)

### Màu Chức Năng
- **Success**: #4CAF50 (Green)
- **Warning**: #FFC107 (Amber)
- **Error**: #F44336 (Red)
- **Info**: #2196F3 (Blue)

### Màu Trung Tính
- **Background**: #FFFFFF
- **Surface**: #F5F5F5
- **On Background**: #212121
- **On Surface**: #757575

### Màu Y Tế Chuyên Biệt
- **Heart Rate**: #E91E63 (Pink)
- **SpO2**: #2196F3 (Blue)
- **Stress Level**: #FF9800 (Orange)
- **Sleep**: #673AB7 (Deep Purple)

## Typography

### Font Family
- **Primary Font**: Roboto
- **Secondary Font**: Roboto Condensed (cho tiêu đề)

### Kích Thước
- **Display Large**: 34sp
- **Display Medium**: 24sp
- **Display Small**: 20sp
- **Body Large**: 16sp
- **Body Medium**: 14sp
- **Body Small**: 12sp
- **Caption**: 10sp

### Weights
- **Regular**: 400
- **Medium**: 500
- **Bold**: 700

## Thành Phần UI

### 1. App Bar

**Mô tả**: Thanh điều hướng chính ở đầu màn hình.

**Thông số kỹ thuật**:
- Chiều cao: 56dp
- Màu nền: Primary Color
- Màu chữ: White
- Elevation: 4dp

**Hành vi**:
- Cố định ở đầu màn hình
- Hiển thị tiêu đề màn hình hiện tại
- Có nút back khi cần
- Có thể chứa các action buttons

### 2. Bottom Navigation

**Mô tả**: Thanh điều hướng chính ở dưới màn hình, giúp chuyển đổi giữa các mục chính.

**Thông số kỹ thuật**:
- Chiều cao: 56dp
- Màu nền: Surface Color
- Items: 3-5 items
- Active Icon: Primary Color
- Inactive Icon: On Surface với độ mờ 60%

**Hành vi**:
- Cố định ở cuối màn hình
- Item được chọn đổi màu và có hiệu ứng
- Chuyển đổi giữa các tab với hiệu ứng fade

### 3. Cards

**Mô tả**: Hiển thị nội dung và hành động về một chủ đề.

**Thông số kỹ thuật**:
- Padding nội dung: 16dp
- Góc bo tròn: 8dp
- Elevation: 2dp
- Màu nền: Surface Color

**Hành vi**:
- Có thể nhấn để xem chi tiết
- Hỗ trợ swipe actions khi cần
- Có thể mở rộng/thu gọn

### 4. Buttons

#### Primary Button

**Mô tả**: Nút hành động chính trên màn hình.

**Thông số kỹ thuật**:
- Padding: 16dp ngang, 8dp dọc
- Góc bo tròn: 4dp
- Màu nền: Primary Color
- Màu chữ: White
- Text Style: Medium, 14sp

**Hành vi**:
- Ripple effect khi nhấn
- Disabled state với độ mờ 50%

#### Secondary Button

**Mô tả**: Nút hành động thứ cấp.

**Thông số kỹ thuật**:
- Padding: 16dp ngang, 8dp dọc
- Góc bo tròn: 4dp
- Màu nền: Transparent
- Viền: Primary Color, 1dp
- Màu chữ: Primary Color
- Text Style: Medium, 14sp

**Hành vi**:
- Ripple effect khi nhấn
- Disabled state với độ mờ 50%

#### Floating Action Button (FAB)

**Mô tả**: Nút hành động nổi cho hành động chính của màn hình.

**Thông số kỹ thuật**:
- Kích thước: 56dp x 56dp
- Màu nền: Accent Color
- Icon màu: White
- Elevation: 6dp

**Hành vi**:
- Nổi trên màn hình, thường ở góc dưới phải
- Có thể ẩn/hiện khi scroll
- Ripple effect khi nhấn

### 5. Text Fields

**Mô tả**: Trường nhập liệu cho người dùng.

**Thông số kỹ thuật**:
- Chiều cao: 56dp
- Padding: 16dp ngang
- Màu viền: On Surface với độ mờ 40%
- Màu viền focus: Primary Color
- Màu text: On Surface
- Hint text: On Surface với độ mờ 60%
- Error text: Error Color

**Hành vi**:
- Hiển thị hint khi trống
- Hiển thị lỗi bên dưới khi có lỗi
- Hỗ trợ helper text
- Animation khi focus

### 6. Dialogs

**Mô tả**: Hiển thị thông báo hoặc yêu cầu quyết định từ người dùng.

**Thông số kỹ thuật**:
- Padding: 24dp
- Góc bo tròn: 8dp
- Màu nền: Surface Color
- Title: 20sp, Medium
- Content: 16sp, Regular
- Buttons: Text Button style, căn phải

**Hành vi**:
- Hiển thị trên modal overlay
- Đóng khi nhấn nút hoặc nhấn bên ngoài (tùy trường hợp)
- Không cho phép tương tác với UI bên dưới

### 7. Progress Indicators

#### Linear Progress

**Mô tả**: Hiển thị tiến trình theo chiều ngang.

**Thông số kỹ thuật**:
- Chiều cao: 4dp
- Màu: Primary Color
- Track color: Primary Light

**Hành vi**:
- Determinate: Hiển thị % hoàn thành
- Indeterminate: Animation liên tục

#### Circular Progress

**Mô tả**: Hiển thị tiến trình dạng vòng tròn.

**Thông số kỹ thuật**:
- Kích thước: 40dp
- Stroke width: 4dp
- Màu: Primary Color

**Hành vi**:
- Determinate: Hiển thị % hoàn thành
- Indeterminate: Animation quay liên tục

### 8. Biểu Đồ và Đồ Thị

**Mô tả**: Hiển thị dữ liệu sức khỏe dưới dạng trực quan.

**Thông số kỹ thuật**:
- Padding: 16dp
- Trục X, Y: On Surface với độ mờ 60%
- Đường biểu đồ: Sử dụng màu chức năng
- Nhãn: 12sp, Regular

**Hành vi**:
- Hỗ trợ zoom/pan
- Hiển thị tooltip khi nhấn vào điểm dữ liệu
- Animation khi loading dữ liệu

## Màn Hình Chính

### 1. Splash Screen

**Mô tả**: Màn hình khởi động ứng dụng.

**Thành phần**:
- Logo ứng dụng (trung tâm)
- Animation ngắn (1-2 giây)
- Màu nền: Primary Color

**Hành vi**:
- Hiển thị ngắn khi khởi động ứng dụng
- Tự động chuyển sang màn hình chính sau 2 giây
- Có thể hiển thị trạng thái loading nếu cần

### 2. Home Screen

**Mô tả**: Màn hình chính của ứng dụng, hiển thị tổng quan và truy cập nhanh đến các tính năng.

**Thành phần**:
- App Bar với logo và action buttons
- Dashboard card hiển thị chỉ số sức khỏe mới nhất
- Quick action buttons cho các tính năng chính
- Health summary charts
- Bottom navigation

**Layout**:
- Sử dụng NestedScrollView với các section
- Cards cho từng nhóm thông tin
- Grid layout cho quick actions

### 3. Measurement Screens

**Mô tả**: Các màn hình đo lường sức khỏe (nhịp tim, SpO2, stress).

**Cấu trúc chung**:
- 3 trạng thái: Preparation, Measuring, Results
- Preparation: Hướng dẫn, nút bắt đầu
- Measuring: Hiển thị camera, tiến trình, data thời gian thực
- Results: Kết quả, phân tích, nút lưu/chia sẻ

**Thành phần UI**:
- Camera preview (khi đang đo)
- Progress indicator
- Real-time visualization
- Result card
- Instructions step stepper
- Action buttons

### 4. History Screen

**Mô tả**: Hiển thị lịch sử các chỉ số sức khỏe.

**Thành phần**:
- Filters cho loại dữ liệu và khoảng thời gian
- Timeline view với các điểm dữ liệu
- Charts hiển thị xu hướng
- Detail cards cho từng mục

**Layout**:
- Tab layout cho các loại dữ liệu
- RecyclerView với các cards
- Date range selector
- Các biểu đồ tương tác

### 5. AI Chat Screen

**Mô tả**: Giao diện trò chuyện với AI health advisor.

**Thành phần**:
- Danh sách tin nhắn
- Text input field
- Suggestion chips
- Health insights cards

**Hành vi**:
- Hiển thị lịch sử chat
- Typing indicators
- Hỗ trợ rich content (text, buttons, charts)
- Auto-scroll to bottom khi có tin nhắn mới

## Hiệu Ứng Chuyển Động

### Transitions Giữa Màn Hình

- Sử dụng shared element transitions khi phù hợp
- Standard slide transitions:
  - Enter: Slide up
  - Exit: Fade out
- Thời lượng: 300ms
- Timing curve: Fast out, slow in

### Animations Trong Màn Hình

- Ripple effects cho các elements có thể nhấn
- Progress indicators với animation mượt mà
- Content loading với fade in/skeleton loading
- Card expansion/collapse với smooth animation

## Hỗ Trợ Đa Ngôn Ngữ

### Cấu Trúc Resources

- Default (English): `values/strings.xml`
- Vietnamese: `values-vi/strings.xml`

### Quy Tắc Đặt Tên String

- Prefix theo tính năng: `feature_component_description`
- Ví dụ: `heart_rate_instruction_placement`

### Xử Lý Độ Dài Chuỗi

- Thiết kế UI phải linh hoạt với độ dài text khác nhau
- Sử dụng ellipsize khi cần
- Test UI với cả hai ngôn ngữ

## Accessibility

### Text Sizes

- Hỗ trợ Dynamic Type (text scaling)
- Tối thiểu: 12sp cho nội dung ít quan trọng
- Tiêu đề: 16-24sp tùy cấp độ
- Contrast ratio tối thiểu: 4.5:1

### Color Usage

- Không dựa vào màu sắc để truyền đạt thông tin
- Sử dụng kết hợp biểu tượng và màu sắc
- Test với color blindness filters

### Touch Targets

- Kích thước tối thiểu: 48dp x 48dp
- Khoảng cách tối thiểu: 8dp

## Responsive Design

### Orientation Support

- Hỗ trợ cả portrait và landscape khi có thể
- Điều chỉnh layout phù hợp với từng orientation

### Screen Sizes

- Sử dụng constraint layouts
- Tránh fixed dimensions khi có thể
- Sử dụng density-independent pixels (dp)
- Các breakpoints:
  - Small: < 360dp
  - Medium: 360dp - 600dp
  - Large: > 600dp

### Fragments and Navigation

- Sử dụng single-activity, multiple-fragments architecture
- TabletUIs sử dụng master-detail pattern khi phù hợp
- Tuân thủ chuẩn back navigation

## Quy Tắc Đặt Tên

### Layout Files

- activity_[screen_name].xml
- fragment_[screen_name].xml
- item_[list_item_name].xml
- view_[custom_view_name].xml
- dialog_[dialog_name].xml

### Resource IDs

- [component_type]_[screen]_[description]
- Ví dụ: `btn_heart_rate_start`, `tv_results_bpm`

### Drawable Files

- ic_[name]_[size] - For icons
- bg_[name] - For backgrounds
- shape_[name] - For shape drawables
- selector_[name] - For state selectors

## Kiểm Thử UI

### Kiểm Thử Manual

- Kiểm tra trên các kích thước màn hình khác nhau
- Kiểm tra trong điều kiện ánh sáng khác nhau
- Kiểm tra các trường hợp đặc biệt (no data, error states)

### Kiểm Thử Tự Động

- Sử dụng Espresso cho UI testing
- Screenshot tests để phát hiện thay đổi không mong muốn
- Test với các font size khác nhau

## Thư Viện UI

### Sử Dụng Material Components

```gradle
implementation 'com.google.android.material:material:1.5.0'
```

### Chart Libraries

```gradle
implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
```

### Image Loading

```gradle
implementation 'com.github.bumptech.glide:glide:4.12.0'
```

## Tiêu Chí Hoàn Thành

1. Tất cả các màn hình có giao diện nhất quán
2. UI thích ứng với các kích thước màn hình khác nhau
3. Hỗ trợ đầy đủ tiếng Anh và tiếng Việt
4. Đạt độ tương phản và kích thước phù hợp với tiêu chuẩn accessibility
5. Chuyển động mượt mà, không bị giật lag
6. Hiển thị đúng trên cả light và dark theme
7. Kiểm thử thành công trên các thiết bị khác nhau

## Đường Dẫn Tài Nguyên

- [Material Design Guidelines](https://material.io/design)
- [Android Accessibility Guidelines](https://developer.android.com/guide/topics/ui/accessibility)
- [API.md](../../API.md) - Định dạng dữ liệu cần hiển thị
- [Diagram.md](../../Diagram.md) - Sơ đồ liên kết giữa các màn hình 