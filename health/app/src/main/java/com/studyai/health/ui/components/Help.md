# Hướng dẫn sử dụng UI Components

## Button Components

### PrimaryButton
Nút chính cho các hành động quan trọng trong ứng dụng.

```kotlin
PrimaryButton(
    text = "Đăng nhập",
    onClick = { /* xử lý click */ },
    isLoading = false, // Hiển thị trạng thái loading
    icon = AppIcons.HomeFilled // Tùy chọn thêm icon
)
```

### SecondaryButton
Nút thứ cấp cho các hành động ít quan trọng hơn.

```kotlin
SecondaryButton(
    text = "Hủy",
    onClick = { /* xử lý click */ },
    enabled = true
)
```

### IconButton Components
Các nút chỉ hiển thị icon, có 3 kiểu:
- `PrimaryIconButton`: Nút icon với nền màu đặc
- `SecondaryIconButton`: Nút icon với viền
- `FloatingIconButton`: Nút nổi kiểu FAB

```kotlin
PrimaryIconButton(
    icon = AppIcons.Add,
    onClick = { /* xử lý click */ },
    contentDescription = "Thêm mới"
)

SecondaryIconButton(
    icon = AppIcons.Close,
    onClick = { /* xử lý click */ }
)

FloatingIconButton(
    icon = AppIcons.Add,
    onClick = { /* xử lý click */ }
)
```

## Card Components

### MetricCard
Hiển thị thông tin về các chỉ số sức khỏe với icon và giá trị.

```kotlin
MetricCard(
    title = "Nhịp tim",
    value = "72",
    unit = "bpm",
    icon = AppIcons.Heart,
    color = Heart,
    onClick = { /* xử lý click */ },
    trendValue = "+3%" // Tùy chọn hiển thị xu hướng
)
```

### StatusCard
Hiển thị trạng thái sức khỏe với thanh tiến trình và màu sắc tương ứng.

```kotlin
StatusCard(
    title = "Sức khỏe tổng thể",
    description = "Tình trạng sức khỏe của bạn tốt dựa trên các chỉ số gần đây",
    status = HealthStatus.GOOD,
    progress = 0.75f,
    icon = AppIcons.Heart,
    onClick = { /* xử lý click */ }
)
```

### SummaryCard
Hiển thị tóm tắt nhiều thông tin sức khỏe trong một card.

```kotlin
// Định nghĩa các mục cần hiển thị
val summaryItems = listOf(
    SummaryItem("Số bước", "8,543", AppIcons.HomeFilled),
    SummaryItem("Quãng đường", "4.2 km", AppIcons.Location),
    SummaryItem("Calories", "367 kcal", AppIcons.FavoriteFilled)
)

// Sử dụng trong composable
SummaryCard(
    title = "Tóm tắt ngày",
    items = summaryItems,
    onClick = { /* xử lý click */ },
    showDividers = true // Có hiển thị đường phân cách giữa các mục hay không
)
```

## Cách sử dụng theme

Để đảm bảo giao diện nhất quán, hãy luôn sử dụng các giá trị từ Theme:

```kotlin
// Sử dụng màu sắc
val primaryColor = MaterialTheme.colorScheme.primary

// Sử dụng typography
val titleStyle = MaterialTheme.typography.titleLarge

// Sử dụng shapes
val cardShape = MaterialTheme.shapes.medium

// Sử dụng dimensions
val padding = MaterialTheme.dimens.contentPaddingMedium
```

## Lưu ý quan trọng

1. **Accessibility**: Luôn cung cấp `contentDescription` cho các icon và hình ảnh để hỗ trợ người dùng khiếm thị.

2. **Dark/Light Theme**: Các component tự động thích ứng với chế độ tối/sáng, không cần thêm xử lý đặc biệt.

3. **Color Usage**: Sử dụng màu từ theme thay vì hardcode để đảm bảo tính nhất quán.

4. **Size Adaptability**: Các component được thiết kế để thích ứng với nhiều kích thước màn hình.

5. **Best Practices**:
   - Sử dụng `PrimaryButton` cho hành động chính
   - Mỗi màn hình chỉ nên có 1 hành động chính
   - Cung cấp phản hồi trực quan khi người dùng tương tác (loading state, ripple effect)
   - Đảm bảo contrast ratio đủ lớn giữa text và background 