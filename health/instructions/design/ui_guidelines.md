# Hướng Dẫn Thiết Kế Giao Diện Người Dùng

## Tổng Quan

Tài liệu này cung cấp các nguyên tắc và hướng dẫn thiết kế giao diện người dùng cho ứng dụng StudyAI Health, giúp tạo ra trải nghiệm nhất quán, trực quan và hấp dẫn cho người dùng.

## Nguyên Tắc Thiết Kế

### 1. Đơn Giản và Tập Trung
- Ưu tiên nội dung quan trọng nhất
- Loại bỏ các yếu tố giao diện không cần thiết
- Mỗi màn hình phục vụ một mục đích chính

### 2. Trực Quan và Hiệu Quả
- Sử dụng biểu đồ và trực quan hóa thay vì văn bản thuần túy
- Tạo ra luồng tự nhiên từ nhiệm vụ này sang nhiệm vụ khác
- Giảm thiểu số lượng thao tác để hoàn thành tác vụ

### 3. Phản Hồi và Hướng Dẫn
- Cung cấp phản hồi tức thì cho mọi tương tác
- Hiển thị hướng dẫn rõ ràng cho các tính năng phức tạp
- Cung cấp giải thích cho các kết quả đo và phân tích

### 4. Nhất Quán
- Duy trì ngôn ngữ thiết kế nhất quán trên toàn ứng dụng
- Sử dụng mẫu UI quen thuộc để giảm bớt đường cong học tập
- Đảm bảo các hành động tương tự có cách thực hiện tương tự

## Thiết Kế Màn Hình

### 1. Splash Screen

**Mục đích**: Tạo ấn tượng ban đầu và hiển thị trong khi app khởi động.

**Yêu cầu**:
- Logo ứng dụng ở trung tâm
- Màu nền primary
- Animation đơn giản (fade/scale)
- Hiển thị trong 1-2 giây hoặc khi app sẵn sàng

**Nguyên tắc**:
- Tối giản và tập trung vào nhận diện thương hiệu
- Không gây phiền nhiễu với animation phức tạp

### 2. Onboarding

**Mục đích**: Giới thiệu người dùng với các tính năng chính của ứng dụng.

**Yêu cầu**:
- 3-4 màn hình tối đa
- Mỗi màn hình tập trung vào một tính năng chính
- Minh họa trực quan (hình ảnh/animation)
- Nút bỏ qua và nút tiếp tục
- Hiển thị chỉ báo trang hiện tại

**Nguyên tắc**:
- Ngắn gọn, thông tin hữu ích
- Không quá chi tiết, để người dùng khám phá dần
- Animation hấp dẫn nhưng không làm chậm trải nghiệm

### 3. Trang Chủ

**Mục đích**: Cung cấp tổng quan về tình trạng sức khỏe và truy cập nhanh đến các tính năng.

**Yêu cầu**:
- Dashboard hiển thị các chỉ số quan trọng
- Quick access buttons cho các tính năng đo lường
- Recent measurements section
- Health tips/insights

**Nguyên tắc**:
- Ưu tiên hiển thị thông tin cập nhật gần nhất
- Sắp xếp thành phần theo tần suất sử dụng
- Thiết kế scannable cho việc kiểm tra nhanh

**Layout**:
- Card-based layout với các sections rõ ràng
- Spacing đủ giữa các sections (24dp)
- Hiển thị 1-2 chỉ số sức khỏe quan trọng nhất đầu trang

**Thiết kế chi tiết Dashboard**:
- **Header section**:
  - Welcome message với tên người dùng (VD: "Hi! Sami Ahmed")
  - INSIGHT TIMER hiển thị ở trên welcome message
  - Menu button ở góc trái trên
  - Calendar và Notification buttons ở góc phải trên

- **Health Metrics Cards**:
  - Sử dụng thẻ với viền bo tròn (corner radius 16dp)
  - Mỗi thẻ có background màu pastel phù hợp với loại chỉ số
  - Icon đặc trưng cho mỗi loại chỉ số (cháy cho calo, nước cho lượng nước uống)
  - Hiển thị số liệu lớn và nổi bật (font size 28sp, semi-bold)
  - Thêm nhãn mô tả ở dưới số liệu (font size 14sp, regular)
  - Sắp xếp cards theo grid layout 2 cột với khoảng cách 16dp

- **Weekly Progress Section**:
  - Card hình chữ nhật với background gradient (xanh dương đến tím)
  - Tiêu đề "Your Weekly Progress" (font size 20sp, semi-bold)
  - Text mô tả "Your weekly report" (font size 14sp)
  - Hiển thị tỷ lệ phần trăm hoàn thành (65%) với vòng tròn tiến độ
  - Vòng tròn có animation khi load

- **Learning Section**:
  - Card với minh họa hấp dẫn (hình ảnh cartoon người đạp xe)
  - Tiêu đề rõ ràng "Learn About Heartbeat"
  - Nút "Check Now" với icon play
  - Corner radius 16dp cho card này

- **Bottom Navigation**:
  - 4 tab cơ bản: Home, Statistics, Profile, Add
  - Icon + label cho mỗi tab
  - Active state với màu primary
  - Inactive state với màu xám nhạt
  - Background trắng với đủ elevation (4dp)

### 4. Màn Hình Profile & Thống Kê

**Mục đích**: Hiển thị thông tin chi tiết và phân tích dữ liệu sức khỏe của người dùng.

**Yêu cầu**:
- Tab navigation để chuyển đổi giữa các loại dữ liệu (Kcal, Water, Steps)
- Hiển thị dữ liệu hiện tại và lịch sử
- Biểu đồ trực quan cung cấp xu hướng

**Tab Navigation System**:
- Tabs nằm ở phần trên cùng với highlight tab đang active
- Background cho tab active sử dụng màu primary với corner radius
- Text và icon tabs không active sử dụng màu trung tính
- Border-bottom nhẹ để phân tách tab bar với nội dung

**Biểu Đồ Kcal (This Week Details)**:
- Tiêu đề section "This Week Details" với icon calendar ở góc phải
- Label "Total kcal burned" làm phụ đề
- Biểu đồ cột theo ngày trong tuần (Sat đến Fri)
- Trục Y hiển thị giá trị từ 0 đến 30k với các mức 5k, 10k, 15k, 20k, 25k
- Sử dụng màu khác nhau cho từng cột (xanh lam nhạt và xanh lá)
- Padding đều 16dp xung quanh biểu đồ

**Biểu Đồ Water Tracking**:
- Hiển thị "Total Drinked" với số lượng cụ thể (10)
- Thêm label "glass" và "Today" để chỉ rõ đơn vị và thời gian
- Biểu đồ dạng area chart với màu gradient (từ xanh dương đến trong suốt)
- Thêm tooltip khi chạm vào điểm dữ liệu trên biểu đồ
- Hiển thị ngày giờ và số lượng trong tooltip (VD: "12 May, 2:00PM - 2:45PM")

**Weekly Performance Card**:
- Card hình chữ nhật với background gradient (tím hoặc xanh dương)
- Tiêu đề "Weekly Performance" (font size 20sp, regular)
- Text mô tả "Your weekly report" (font size 14sp)
- Hiển thị tỷ lệ phần trăm (80% hoặc 30%)
- Vòng tròn progress với stroke width 4dp
- Đặt ở cuối màn hình trước bottom navigation

### 5. Màn Hình Đo Lường

**Mục đích**: Hướng dẫn người dùng trong quá trình đo chỉ số sức khỏe.

**Giai đoạn đo**:
1. **Chuẩn bị**: Hướng dẫn và miễn trừ y tế
2. **Đo lường**: Camera view và trực quan hóa thời gian thực
3. **Kết quả**: Hiển thị giá trị và đánh giá

**Yêu cầu**:
- Hướng dẫn trực quan (hình ảnh/animation) về cách đo
- Hiển thị tiến trình còn lại
- Kết quả với context (so sánh với phạm vi bình thường)
- Các tùy chọn sau khi đo (lưu, đo lại, chia sẻ)

**Nguyên tắc**:
- Rõ ràng và không có yếu tố gây xao nhãng trong quá trình đo
- Chỉ báo trực quan cho chất lượng tín hiệu
- Cảnh báo khi phát hiện vấn đề (chuyển động, ánh sáng)

### 6. Màn Hình Lịch Sử

**Mục đích**: Hiển thị và phân tích lịch sử đo lường.

**Yêu cầu**:
- Filter theo loại dữ liệu và thời gian
- Biểu đồ xu hướng
- Danh sách kết quả với phân loại màu sắc
- Tùy chọn xem chi tiết từng kết quả

**Nguyên tắc**:
- Ưu tiên trực quan hóa xu hướng
- Dễ dàng phát hiện các thay đổi bất thường
- Cung cấp options để phân tích sâu hơn

**Tương tác**:
- Swipe để xóa các kết quả
- Tap để xem chi tiết
- Pinch to zoom trên biểu đồ

### 7. Màn Hình Tư Vấn Sức Khỏe

**Mục đích**: Tương tác với AI tư vấn sức khỏe.

**Yêu cầu**:
- Chat interface với lịch sử hội thoại
- Hiển thị gợi ý câu hỏi
- Hỗ trợ hiển thị kết quả dạng rich (biểu đồ, bảng, card)
- Input field với microphone option

**Nguyên tắc**:
- Giao diện đơn giản, quen thuộc như messaging apps
- Phân biệt rõ ràng giữa tin nhắn người dùng và AI
- Đảm bảo các câu trả lời phức tạp vẫn dễ đọc

**Tính năng đặc biệt**:
- Health insight cards trong chat
- Quick reply buttons
- Topic categorization

## Biểu Thức Trực Quan (Visual Language)

### 1. Màu Sắc và Ý Nghĩa

**Sức Khỏe Tổng Quát**:
- **Rất tốt**: Xanh lá (#4CAF50)
- **Bình thường**: Xanh dương (#2196F3)
- **Cần chú ý**: Vàng (#FFC107)
- **Cần cải thiện**: Cam (#FF9800)
- **Cảnh báo**: Đỏ (#F44336)

**Chỉ Số Cụ Thể**:
- **Nhịp Tim**: Đỏ hồng (#E91E63)
- **SpO2**: Xanh dương (#2196F3)
- **Căng Thẳng**: Cam (#FF9800)
- **Giấc Ngủ**: Tím (#673AB7)
- **Calorie**: Vàng cam (#FF9800)
- **Nước uống**: Xanh lam nhạt (#64B5F6)
- **Số bước**: Hồng nhạt (#F8BBD0)

**Gradients cho Cards**:
- **Progress Cards**: Linear gradient từ #7986CB đến #3F51B5 (góc 45°)
- **Calorie Cards**: Linear gradient từ #FFB74D đến #FF9800 (góc 45°)
- **Water Cards**: Linear gradient từ #4FC3F7 đến #2196F3 (góc 45°)
- **Weekly Performance Card (Tốt)**: Linear gradient từ #AB47BC đến #7E57C2 (góc 45°)
- **Weekly Performance Card (Trung bình)**: Linear gradient từ #29B6F6 đến #42A5F5 (góc 45°)

**Background cho Charts**:
- **Area Charts**: Gradient từ màu primary (opacity 0.6) đến transparent
- **Bar Charts**: Sử dụng 2 màu xen kẽ #B3E5FC và #A5D6A7 cho các cột

### 2. Icons và Biểu Tượng

**Nhịp Tim**: Icon trái tim đập
**SpO2**: Icon giọt máu với O₂
**Căng Thẳng**: Icon sóng não/biểu đồ stress
**Giấc Ngủ**: Icon mặt trăng/giường ngủ

**Nguyên tắc**:
- Sử dụng outlined icons nhất quán
- Kích thước icon đủ lớn để nhận diện (24dp x 24dp)
- Đảm bảo contrast đủ lớn với background

### 3. Trực Quan Hóa Dữ Liệu

**Biểu Đồ Đường**: Cho dữ liệu liên tục như nhịp tim, SpO2, lượng nước uống theo thời gian
- Line width: 2dp
- Point radius: 4dp (highlight point: 6dp)
- Gradient fill từ màu chính đến transparent
- Thêm grid lines nhẹ theo trục X và Y

**Biểu Đồ Cột**: Cho dữ liệu theo ngày/tuần như calories, steps
- Column width: 16dp minimum
- Spacing giữa các cột: 8dp
- Corner radius top: 4dp
- Sử dụng màu xen kẽ cho dễ phân biệt

**Circular Progress**: Cho tiến độ và mục tiêu
- Track color: #E0E0E0 (light) / #424242 (dark)
- Progress color: Màu tương ứng với chỉ số
- Stroke width: 4dp (small) / 8dp (large)
- Text ở giữa với font size lớn hơn (24sp)

**Tooltips và Data Labels**:
- Background: White với alpha 0.9
- Text color: Dark Grey (#424242)
- Padding: 8dp
- Corner radius: 4dp
- Arrow indicator chỉ vào điểm dữ liệu
- Hiển thị giá trị và thời gian/ngày

**Nguyên tắc chung cho trực quan hóa**:
- Luôn có chú thích và đơn vị rõ ràng
- Sử dụng gradient thay vì màu đơn để tăng chiều sâu
- Hiển thị ngưỡng tham chiếu (bình thường, cao, thấp)
- Hỗ trợ tương tác (zoom, select)
- Animation khi dữ liệu load hoặc thay đổi
- Responsive scales dựa trên dữ liệu

## Tương Tác Và Animation

### 1. Touch Feedback

- Ripple effect cho tất cả elements có thể nhấn
- Highlight trạng thái press/hover
- Haptic feedback cho các hành động quan trọng
- Disable states rõ ràng

### 2. Transitions

- Page transitions: Slide và fade kết hợp
- Content transitions: Fade
- Shared element transitions khi chuyển đến chi tiết
- Modal dialogs: Fade in với slight scale

### 3. Progress Indicators

- Hiển thị spinner cho tác vụ ngắn (<2 giây)
- Progress bar có xác định tiến độ khi biết thời gian
- Skeleton loading cho content loading
- Pull-to-refresh cho cập nhật dữ liệu

### 4. Micro-interactions

- Các animation nhỏ để tạo cảm giác phản hồi
- Thiết kế theo quy tắc: subtle, meaningful, quick
- Ví dụ: icon tim đập theo BPM, biểu tượng oxy "lấp lánh"
- Thời lượng animation: 200-500ms

## Accessibility

### 1. Text và Typography

- Kích thước text tối thiểu: 12sp cho caption, 14sp cho text thường
- Hỗ trợ dynamic text sizing (phóng to lên đến 200%)
- Đảm bảo contrast text-background đạt WCAG AA (4.5:1)
- Không dùng text làm hình ảnh

### 2. Touch Targets

- Kích thước tối thiểu: 48dp x 48dp
- Spacing giữa các targets: tối thiểu 8dp
- Đảm bảo các interactive elements đủ lớn
- Tránh đặt các touch targets gần nhau

### 3. Assistive Technologies

- Content descriptions cho tất cả non-text elements
- Support for screen readers
- Logical navigation order
- Keyboard navigation support

### 4. Inclusive Design

- Không chỉ dựa vào màu sắc để truyền tải thông tin
- Cung cấp text alternatives cho visual elements
- Khả năng đọc trong điều kiện ánh sáng khác nhau
- Đảm bảo UI có thể sử dụng với một tay

## Responsive Design

### 1. Kích Thước Màn Hình

**Phone (Small)**:
- Width: 360dp - 400dp
- Một cột layout
- Bottom navigation

**Phone (Large)**:
- Width: 400dp - 600dp
- Một cột với optional side panels
- Bottom navigation

**Tablet**:
- Width: >600dp
- Hai cột layout
- Side navigation option

### 2. Orientation

**Portrait**:
- Focus vào một task/view
- Vertical scrolling
- Bottom navigation

**Landscape**:
- Side-by-side panels
- More horizontal space for charts
- Side/top navigation

### 3. Adaptative Layouts

- Sử dụng ConstraintLayout để thích ứng
- Avoid fixed dimensions
- Screen-size dependent UI components
- Different layouts for significantly different sizes

### 4. Multi-Window Support

- Proper handling for split-screen mode
- Saving state when resizing
- Graceful UI scaling

## Ngôn Ngữ và Hỗ Trợ Đa Ngôn Ngữ

### 1. Tone of Voice

- Thân thiện và khuyến khích
- Rõ ràng, không dùng thuật ngữ chuyên môn nếu không cần thiết
- Tập trung vào việc giúp đỡ, không phán xét
- Ngắn gọn, không lặp lại

### 2. UX Writing

- Nhất quán trong cách gọi các tính năng
- Tập trung vào hành động người dùng
- Tránh các thông báo tiêu cực
- Cung cấp gợi ý cho errors và empty states

### 3. Internationalization

- Resource files riêng cho từng ngôn ngữ
- Hỗ trợ các chiều dài text khác nhau
- Không hardcode text trong layouts
- Test UI với cả tiếng Anh và tiếng Việt

### 4. Right-to-Left (RTL) Support

- Xem xét RTL cho hỗ trợ ngôn ngữ tương lai
- Sử dụng start/end thay vì left/right
- Test layout với RTL displays

## Dark Mode

### 1. Màu Sắc

**Light Mode**:
- Background: #FFFFFF
- Surface: #F5F5F5
- Text Primary: #212121
- Text Secondary: #757575

**Dark Mode**:
- Background: #121212
- Surface: #1E1E1E
- Text Primary: #FFFFFF
- Text Secondary: #B3B3B3

### 2. Contrast và Readability

- Tăng contrast cho text trên các background tối
- Giảm độ bão hòa của màu sắc chính
- Kiểm tra readability trong điều kiện ánh sáng thấp
- Avoid pure black (#000000) và pure white (#FFFFFF)

### 3. Elevation và Shadows

- Trong dark mode, surfaces càng cao càng sáng
- Sử dụng overlay thay vì shadows
- Đảm bảo layers phân biệt được

### 4. Icons và Images

- Tự động đảo ngược màu sắc icons khi cần
- Đảm bảo images và illustrations vẫn rõ ràng
- Cung cấp phiên bản thay thế cho dark mode nếu cần

## Performance Considerations

### 1. Animations và Transitions

- Tối ưu performance với Hardware Acceleration
- Giới hạn số lượng animations đồng thời
- Target 60fps cho tất cả animations
- Fallback gracefully trên thiết bị yếu hơn

### 2. Loading States

- Luôn hiển thị loading state cho operations >300ms
- Sử dụng placeholder/skeleton trước khi content load
- Ưu tiên tải content quan trọng trước
- Background loading cho dữ liệu không thiết yếu

### 3. Image Handling

- Lazy loading cho images
- Sử dụng thumbnails trước khi load full images
- Đảm bảo aspect ratio correct
- Compression và caching phù hợp

### 4. Rendering Optimization

- Tránh overdraw
- Sử dụng ClipPath/ClipRect khi cần
- Optimize list rendering với recycling
- Sử dụng hardware accelerated rendering

## Kiểm Thử UI

### 1. Design Review

- Kiểm tra nhất quán với design system
- Đánh giá usability và information architecture
- Đánh giá trực quan, sự hấp dẫn
- Nhất quán với platform guidelines

### 2. Usability Testing

- Test với người dùng thực
- Các tác vụ cụ thể để hoàn thành
- Quan sát và ghi lại phản hồi
- Lặp lại thiết kế dựa trên feedback

### 3. Compatibility Testing

- Test trên nhiều thiết bị khác nhau
- Xác minh trên các kích thước màn hình
- Kiểm tra với các phiên bản Android
- Test trên thiết bị thực, không chỉ emulators

### 4. Accessibility Testing

- Kiểm tra với TalkBack
- Test với các cài đặt font size khác nhau
- Color blindness simulation
- Keyboard-only navigation test

## Implementation Guidelines

### 1. Jetpack Compose

- Sử dụng Compose UI cho tất cả UI mới
- Theo dõi Material Design Components cho Compose
- Sử dụng state hoisting pattern
- Tối ưu recomposition

### 2. XML Layouts (Legacy)

- Sử dụng ConstraintLayout cho layouts phức tạp
- Avoid deep view hierarchies
- Tận dụng ViewBinding
- Material Components từ MDC library

### 3. Sử Dụng Resources

- Tất cả strings trong strings.xml
- Tất cả dimensions trong dimens.xml
- Tất cả colors trong colors.xml
- Tất cả styles trong styles.xml

### 4. Asset Management

- Sử dụng vector drawables khi có thể
- WebP format cho bitmap images
- Density-specific resources khi cần
- Proper naming conventions

## Tiêu Chí Hoàn Thành

Một màn hình UI được coi là hoàn thành khi:

1. Tuân thủ tất cả guidelines thiết kế
2. Hiển thị chính xác trong cả light và dark mode
3. Hoạt động trên tất cả kích thước màn hình mục tiêu
4. Đạt các tiêu chuẩn accessibility
5. Performant với ít nhất 60fps
6. Hỗ trợ đầy đủ các ngôn ngữ yêu cầu

## Tích Hợp Với Design System

Tham khảo các tài liệu sau để hiểu rõ hơn về design system:

- [design_system.md](design_system.md) - Chi tiết về design tokens, components
- [components.md](components.md) - Thư viện UI components
- [Diagram.md](../../Diagram.md) - Flow giữa các màn hình 