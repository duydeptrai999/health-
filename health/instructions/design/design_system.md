# Hệ Thống Thiết Kế

## Tổng Quan

Hệ thống thiết kế cho ứng dụng StudyAI Health xác định các nguyên tắc, thành phần và hướng dẫn nhất quán để đảm bảo ứng dụng có giao diện mạch lạc và trải nghiệm người dùng thống nhất.

## Brand Identity

### Logo

- **Primary Logo**: Logo chính của ứng dụng
- **Variant**: Phiên bản đơn giản hóa cho không gian nhỏ
- **Clear Space**: Khoảng trống tối thiểu xung quanh logo = 1/4 chiều cao logo
- **Minimum Size**: 24dp chiều cao trên mobile

### Biểu Tượng Ứng Dụng

- **Foreground**: Biểu tượng chính trên nền trong suốt
- **Background**: Nền gradient từ Primary đến Primary Dark
- **Adaptive Icon**: Hỗ trợ trên Android 8.0+
- **Legacy Icon**: Hỗ trợ các phiên bản Android cũ

## Hệ Thống Màu Sắc

### Màu Sắc Thương Hiệu

- **Primary**: #2196F3 (Blue 500)
- **Secondary**: #FF4081 (Pink A200)
- **Brand Gradient**: Linear gradient từ Primary đến Secondary với góc 45°

### Bảng Màu Mở Rộng

- **Primary Palette**:
  - Primary 900: #0D47A1
  - Primary 800: #1565C0
  - Primary 700: #1976D2
  - Primary 600: #1E88E5
  - Primary 500: #2196F3 (Primary Color)
  - Primary 400: #42A5F5
  - Primary 300: #64B5F6
  - Primary 200: #90CAF9
  - Primary 100: #BBDEFB
  - Primary 50: #E3F2FD

- **Secondary Palette**:
  - Secondary Dark: #C51162
  - Secondary: #FF4081
  - Secondary Light: #FF80AB

### Chức Năng Màu Sắc

- **Feedback Colors**:
  - Success: #4CAF50
  - Warning: #FFC107
  - Error: #F44336
  - Info: #2196F3

- **Surface Colors**:
  - Background: #FFFFFF (Light) / #121212 (Dark)
  - Surface: #FFFFFF (Light) / #1E1E1E (Dark)
  - Card: #FFFFFF (Light) / #2D2D2D (Dark)
  - Divider: #E0E0E0 (Light) / #393939 (Dark)

- **Text Colors**:
  - On Primary: #FFFFFF
  - On Secondary: #FFFFFF
  - On Background: #212121 (Light) / #FFFFFF (Dark)
  - On Surface: #757575 (Light) / #B3B3B3 (Dark)

### Nguyên Tắc Sử Dụng Màu Sắc

- **60-30-10 Rule**: 60% màu chính, 30% màu thứ cấp, 10% màu nhấn
- **Light vs Dark**: Đảm bảo độ tương phản đủ giữa text và background
- **Tương phản**: Đạt WCAG AA standard (tối thiểu 4.5:1 cho văn bản thông thường)
- **Ý nghĩa nhất quán**: Màu Success luôn là xanh lá, Error luôn là đỏ

## Typography

### Font Chính

- **Font Family**: Roboto
- **Fallback Stack**: Roboto, "Helvetica Neue", Helvetica, Arial, sans-serif
- **Định dạng**: Sử dụng bộ font Google's Roboto

### Type Scale

- **Display 1**: 34sp / 40sp line height, Light
- **Headline**: 24sp / 32sp line height, Regular
- **Title**: 20sp / 28sp line height, Medium
- **Subheading**: 16sp / 24sp line height, Regular
- **Body 2**: 14sp / 24sp line height, Medium
- **Body 1**: 14sp / 20sp line height, Regular
- **Caption**: 12sp / 16sp line height, Regular
- **Button**: 14sp / 16sp line height, Medium, ALL CAPS

### Mẫu Chữ

- **Paragraph Text**: 
  - Căn lề trái (không justify)
  - Line height tối thiểu 1.5x font size
  - Max width: 60-70 ký tự mỗi dòng

- **CTA Text**:
  - Bold/Medium weight
  - Đủ lớn để dễ dàng nhấn (tối thiểu 14sp)

- **Link Text**:
  - Màu Primary
  - Gạch chân khi hover

## Iconography

### Hệ Thống Icon

- **Icon Style**: Outlined (line thickness 2dp)
- **Size**: 
  - Standard: 24dp x 24dp
  - Small: 18dp x 18dp
  - Large: 36dp x 36dp
- **Padding**: 2dp padding trong 24dp viewport

### Icon Categories

- **Navigation Icons**: Dùng cho bottom nav, back buttons
- **Action Icons**: Dùng cho buttons, FABs
- **Status Icons**: Hiển thị trạng thái, thông báo
- **Health Icons**: Biểu tượng chuyên dụng cho tính năng sức khỏe

### Icon Library

Sử dụng Material Icons với bổ sung custom health icons:
- Heart rate icon
- SpO2 icon
- Stress level icon
- AI advisor icon

## Spacing System

### Spacing Scale

- **4dp Grid System**: Tất cả spacing là bội số của 4dp
- **Spacing Units**:
  - 4dp (extra small)
  - 8dp (small)
  - 16dp (medium)
  - 24dp (large)
  - 32dp (extra large)
  - 48dp (2x large)
  - 64dp (3x large)

### Layout Spacing

- **Screen Margins**: 16dp ở các cạnh
- **Content Padding**: 16dp bên trong cards/containers
- **Item Spacing**: 8dp giữa các items trong list
- **Section Spacing**: 24dp giữa các sections
- **Group Spacing**: 16dp giữa các nhóm controls

## Elevation & Shadows

### Elevation Scale

- **0dp**: Elements dính với surface
- **1dp**: Cards, Search bars
- **3dp**: Bottom navigation, bottom app bars
- **6dp**: FABs, snackbars
- **8dp**: Bottom sheets, navigation drawers
- **12dp**: Dialogs, pickers
- **24dp**: Modals

### Shadow Properties

- **Ambient Shadows**: Độ mờ thấp, khuếch tán rộng
- **Direct Shadows**: Độ mờ cao hơn, khuếch tán hẹp hơn
- **Soft vs Hard**: Điều chỉnh theo trạng thái màn hình (light/dark)

## Corners & Shapes

### Corner Radius

- **Small**: 4dp (buttons, chips)
- **Medium**: 8dp (cards, sheets)
- **Large**: 16dp (modal dialogs)
- **Full Rounded**: 50% (circular buttons, avatars)

### Shape System

- **Rounded Rectangle**: Component mặc định
- **Cut Corner**: Ít sử dụng, đặc biệt cho branding
- **Circle**: FABs, avatars, indicators

## Motion & Animation

### Timing

- **Standard Duration**: 300ms
- **Enter/Exit**: 225ms
- **Emphasis**: 450ms

### Easing Curves

- **Standard Curve**: Fast out, slow in
- **Acceleration Curve**: Linear out, slow in
- **Deceleration Curve**: Fast out, linear in
- **Sharp Curve**: Linear

### Animation Types

- **Transitions**: Chuyển đổi giữa các màn hình
- **Feedback**: Phản hồi khi tương tác
- **Progress**: Hiển thị tiến trình
- **Attention**: Thu hút chú ý người dùng

## Patterns

### Layout Patterns

- **List**: Hiển thị dữ liệu theo danh sách
- **Grid**: Hiển thị các tính năng hoặc dữ liệu theo lưới
- **Master-Detail**: Xem danh sách và chi tiết (tablet)
- **Bottom Sheet**: Hiển thị thông tin bổ sung không tạo gián đoạn

### Interaction Patterns

- **Pull to Refresh**: Kéo xuống để làm mới dữ liệu
- **Swipe to Dismiss/Delete**: Vuốt để đóng/xóa
- **Drag to Reorder**: Kéo để sắp xếp lại
- **Double Tap to Like/Save**: Nhấn đúp để thích/lưu

### Navigation Patterns

- **Tab Navigation**: Chuyển đổi giữa các phần chính
- **Hierarchical Navigation**: Đào sâu vào nội dung
- **Back Navigation**: Quay lại màn hình trước
- **Lateral Navigation**: Duyệt qua các phần cùng cấp

## Assets Management

### Image Assets

- **Vector vs Raster**: Ưu tiên vector khi có thể
- **Resolution Support**: mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi
- **Compression**: WebP cho hình ảnh lớn

### Asset Naming

- **Icon**: `ic_[name]_[state]_[size]`
- **Background**: `bg_[name]_[state]`
- **Illustration**: `illus_[name]_[state]`

## Adaptive Design

### Breakpoints

- **Extra Small**: < 360dp
- **Small**: 360dp - 599dp
- **Medium**: 600dp - 839dp
- **Large**: 840dp+

### Layout Grids

- **4dp Base Grid**: Component sizing
- **8dp Layout Grid**: Component spacing
- **Column Grid**: 4 columns (phone), 8 columns (tablet)

### Orientation Support

- **Portrait Layouts**: Tối ưu cho sử dụng thông thường
- **Landscape Layouts**: Hỗ trợ cho một số màn hình quan trọng

## Accessibility

### Visual Accessibility

- **Text Contrast**: WCAG AA compliant (4.5:1 cho text thường, 3:1 cho text lớn)
- **Touch Target**: Tối thiểu 48dp x 48dp
- **Focus State**: Hiển thị rõ ràng trạng thái focus

### Content Accessibility

- **Content Scaling**: Hỗ trợ text size từ 80% đến 200%
- **Screen Reader**: Tất cả UI elements có contentDescription
- **Alternative Text**: Cho tất cả hình ảnh có ý nghĩa

## Dark Theme

### Dark Theme Colors

- **Dark Background**: #121212
- **Dark Surface**: #1E1E1E
- **Dark Error**: #CF6679
- **On Dark Background**: #FFFFFF
- **On Dark Surface**: #FFFFFF với độ mờ 87%

### Dark Theme Principles

- **Depth Indication**: Surface càng cao càng sáng
- **Desaturation**: Giảm độ bão hòa của màu sắc
- **Limited Dark Surfaces**: Tránh quá nhiều bề mặt tối

## Design Tokens

### Token Structure

- **Global Tokens**: Giá trị cơ bản (colors, spacing, typography)
- **Alias Tokens**: Ánh xạ global tokens đến use case cụ thể
- **Component Tokens**: Áp dụng alias tokens vào components

### Token Implementation

- **XML Resource Organization**:
  - `colors.xml`: Định nghĩa màu sắc
  - `dimens.xml`: Định nghĩa kích thước, spacing
  - `styles.xml`: Định nghĩa styles
  - `themes.xml`: Định nghĩa themes

## Quy Trình Thiết Kế

### Design-to-Development

- **Handoff**: Figma/Sketch files với đủ thông số kỹ thuật
- **Design System Library**: Thư viện components trong design tool
- **Inspection**: Công cụ để developers xem specs

### Version Control

- **Design Versions**: Đánh số phiên bản cho design system
- **Changelog**: Ghi lại các thay đổi trong design system
- **Semantic Versioning**: major.minor.patch

## Tích Hợp Với Code

### Theme Implementation

```xml
<!-- themes.xml -->
<style name="Theme.StudyAI" parent="Theme.MaterialComponents.DayNight.NoActionBar">
    <item name="colorPrimary">@color/primary</item>
    <item name="colorPrimaryDark">@color/primary_dark</item>
    <item name="colorAccent">@color/accent</item>
    <!-- Additional theme attributes -->
</style>
```

### Component Styling

```xml
<!-- styles.xml -->
<style name="Widget.StudyAI.Button.Primary" parent="Widget.MaterialComponents.Button">
    <item name="android:textSize">@dimen/button_text_size</item>
    <item name="cornerRadius">@dimen/button_corner_radius</item>
    <!-- Additional attributes -->
</style>
```

### Compose Theme

```kotlin
@Composable
fun StudyAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
```

## Tài Nguyên

- [Thư viện Figma](URL_TO_FIGMA_LIBRARY)
- [Material Design Guidelines](https://material.io/design)
- [Android Design Resources](https://developer.android.com/design) 