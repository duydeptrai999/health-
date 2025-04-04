# Changelog

## [Unreleased]

### Added

- Hoàn thành tính năng Đo SpO2 qua camera
  - Thêm SpO2MeasurementScreen với giao diện người dùng trực quan
  - Triển khai OxygenSaturationAnalyzer để tính toán nồng độ oxy từ video camera
  - Tích hợp CameraPreview với đèn flash cho độ chính xác cao
  - Thêm SpO2ViewModel để quản lý trạng thái và xử lý dữ liệu
  - Hiển thị kết quả đo với phân loại mức độ (bình thường, thấp, nguy hiểm)
  - Lưu lịch sử đo vào cơ sở dữ liệu
  - Hiển thị miễn trừ trách nhiệm y tế và hướng dẫn chi tiết
- Tích hợp hệ thống lưu trữ kinh nghiệm dự án với cấu trúc thư mục experiences/
- Thêm quy tắc experience-system-workflow.mdc để quản lý việc ghi lại và sử dụng kinh nghiệm
- Tạo template và ví dụ mẫu cho file kinh nghiệm với cấu trúc chuẩn
- Cấu trúc phân loại kinh nghiệm theo frontend, backend, mobile, devops, testing, AI, và common
- Tích hợp hệ thống kinh nghiệm vào các quy trình làm việc hiện có
- Tự động hóa quy trình ghi lại kinh nghiệm sau khi giải quyết vấn đề phức tạp
- Tính năng tạo ngẫu nhiên tính cách AI cho dự án để tăng trải nghiệm thú vị
- Thêm file project-personality-generator.mdc để quản lý các tính cách
- Cập nhật quy trình tạo dự án và nâng cấp dự án để bao gồm bước chọn tính cách
- Hỗ trợ 11 loại tính cách khác nhau với trọng số ưu tiên
- Bổ sung quy trình quản lý resource (icon và rule) vào workflow tạo dự án
- Bổ sung quy trình quản lý resource (icon và rule) vào workflow nâng cấp dự án
- Tích hợp hướng dẫn sử dụng Icon Library API vào quy trình làm việc
- Mô tả chi tiết quy trình đồng bộ hóa Cursor Rules
- Tạo file resource-management.mdc để quản lý tài nguyên trong dự án
- Cập nhật README.md trong thư mục assets/icons để lưu trữ hướng dẫn từ ICON-LIBRARY-API-GUIDE
- Cập nhật .cursorrc để thêm resource-management.mdc vào rules áp dụng tự động
- Bổ sung quy trình sử dụng Supabase MCP trong chế độ nhà phát triển
- Thêm quy tắc kiểm tra và cấu hình .env cho các dự án Supabase
- Tạo file supabase-mcp-workflow.mdc chứa hướng dẫn chi tiết
- Cập nhật workflow dự án mới và nâng cấp dự án để tích hợp Supabase MCP
- Hướng dẫn cài đặt và sử dụng MCP để kiểm tra database changes
- Tích hợp DALL-E API để tạo và chuyển đổi ảnh vector
- Bộ script `scripts/dalle` để tạo ảnh từ prompt, phân tích ảnh, và chuyển đổi thành vector SVG
- Quy trình làm việc mới `dalle-workflow.mdc` cho việc tạo và quản lý ảnh
- Cấu trúc thư mục `assets/icons`, `assets/images`, và `assets/illustrations`
- Cải tiến script DALL-E với tính năng tối ưu prompt tự động cho vector, icon, app icon và UI icon set
- Thêm cảnh báo chi phí sử dụng DALL-E API trước khi tạo ảnh (~0,08$ mỗi ảnh với DALL-E 3)
- Bổ sung tham số để bỏ qua cảnh báo chi phí và lưu prompt đi kèm với ảnh đã tạo
- Cập nhật script analyze_image.js để tối ưu hóa cho việc phân tích và tạo prompt cho ảnh vector
- Cải thiện script vectorize_image.js với xử lý màu sắc thông minh hơn và hỗ trợ nhiều định dạng đầu vào
- Xây dựng hệ thống UI Theme cho ứng dụng sử dụng Material 3
  - Tạo Color.kt với các màu sắc chính, phụ và màu dành cho health metrics
  - Tạo Type.kt với typography style dựa trên Material 3
  - Tạo Shape.kt với các shape khác nhau cho các thành phần UI
  - Tạo Dimensions.kt để chuẩn hóa các kích thước trong app
  - Tạo Theme.kt để kết hợp tất cả thành một theme chung với hỗ trợ Dark Mode và Dynamic Color
  - Tạo Icons.kt để quản lý icons trong ứng dụng

- Xây dựng các UI Components cơ bản
  - Buttons: PrimaryButton, SecondaryButton, IconButtons
  - Cards: MetricCard, StatusCard, SummaryCard

- Thêm documentations:
  - README.md trong thư mục theme giải thích cấu trúc theme
  - Help.md với hướng dẫn sử dụng các components
  - Hướng dẫn tổng quan về toàn bộ hệ thống UI đã xây dựng

### Changed

- Cập nhật cách cài đặt Supabase MCP: sử dụng npm global thay vì cài đặt từ GitHub
- Bổ sung các tham số cụ thể khi chạy mcp-server-postgrest
- Nâng cấp quy trình tương tác tích hợp APK
- Cập nhật tài liệu hướng dẫn
- Cập nhật README.md để giới thiệu cấu trúc tài liệu mới và hệ thống kinh nghiệm
- Nâng cấp phiên bản lên 2.0.0 do thay đổi lớn trong cấu trúc tài liệu
- Cải thiện quy trình làm việc để tập trung vào documentation-first approach
- Tích hợp cảnh báo chi phí trong quy trình tạo ảnh DALL-E để tránh chi phí không cần thiết
- Cải thiện UX của các script DALL-E với giao diện dòng lệnh thân thiện và đầy màu sắc
- Nâng cấp dalle-workflow.mdc với hướng dẫn chi tiết về tối ưu prompt cho từng loại ảnh
- Áp dụng thiết kế chủ đạo là màu xanh lá (primary) và xanh dương (secondary) cho ứng dụng sức khỏe
- Tối ưu hóa cấu trúc folder cho dễ quản lý và mở rộng

### Tech
- Sử dụng Jetpack Compose cho tất cả UI components
- Tích hợp Material 3 design system
- Hỗ trợ Dark Mode và Dynamic Color (Material You) trên Android 12+
- Sử dụng CompositionLocal để truy cập giá trị dimensions

### Deprecated

- Quy trình làm việc cũ không sử dụng cấu trúc "6 Docs"

## [1.0.1] - 2024-03-23

### Added

- Bổ sung quy trình nâng cấp APK từ project Android nguồn
- Hỗ trợ build APK trực tiếp từ project Android với key debug
- Cải tiến quy trình tích hợp package từ APK nguồn sang APK đích
- Tài liệu hướng dẫn cho quy trình tích hợp mới

### Changed

- Nâng cấp quy trình tương tác tích hợp APK
- Cập nhật tài liệu hướng dẫn

## [2.0.0] - 2024-05-24

### Added

- Cấu trúc tài liệu "6 Docs" mới để giảm thiểu AI hallucination
- Templates cho 6 tài liệu chính (PRD, App Flow, Tech Stack, Frontend Guidelines, Backend Structure, Implementation Plan)
- Quy trình tạo dự án mới (project-creation-workflow.mdc)
- Quy trình nâng cấp dự án (project-upgrade-workflow.mdc)
- Thư mục docs/ với README.md giải thích về cấu trúc mới

### Changed

- Cập nhật README.md để giới thiệu cấu trúc tài liệu mới
- Nâng cấp phiên bản lên 2.0.0 do thay đổi lớn trong cấu trúc tài liệu
- Cải thiện quy trình làm việc để tập trung vào documentation-first approach

### Deprecated

- Quy trình làm việc cũ không sử dụng cấu trúc "6 Docs"

## [0.1.2] - 2024-04-03

### Cập nhật
- Cải thiện tài liệu hướng dẫn UI dựa trên mẫu design
- Bổ sung chi tiết về triển khai Dashboard Health
- Thêm hướng dẫn cho biểu đồ Kcal và theo dõi nước uống
- Bổ sung thông tin về màu sắc và gradient cho cards
- Chi tiết hóa thành phần giao diện dựa trên layout mẫu
- Cập nhật hướng dẫn về các component trực quan hóa dữ liệu
