# StudyAI Health - Hướng dẫn sử dụng

## Hệ thống Theme

Ứng dụng sử dụng Material 3 làm nền tảng thiết kế chính với hệ thống theme được tùy biến để phù hợp với ứng dụng sức khỏe. Theme được định nghĩa trong package `com.studyai.health.ui.theme`.

### Màu sắc (Color.kt)
Hệ thống màu sắc được thiết kế để thể hiện trực quan về các thông tin sức khỏe:
- **Màu chính (Primary)**: Màu xanh lá (#4CAF50) biểu thị sức khỏe và sự tươi mới
- **Màu thứ cấp (Secondary)**: Màu xanh dương (#03A9F4) biểu thị sự tin cậy
- **Màu cảnh báo**: Từ xanh đến đỏ biểu thị mức độ nghiêm trọng

### Typography (Type.kt)
Hệ thống typography sử dụng font sans-serif với các kích thước và trọng lượng khác nhau để tạo nên hệ thống phân cấp rõ ràng.

### Kích thước (Dimensions.kt)
Các kích thước chuẩn giúp tạo nên giao diện nhất quán từ spacing, padding đến kích thước các thành phần.

### Hình dạng (Shape.kt)
Các góc bo tròn được áp dụng cho các thành phần UI, tạo cảm giác thân thiện với người dùng.

## Components

### Buttons
Hệ thống button bao gồm:
- **PrimaryButton**: Nút chính cho các hành động quan trọng
- **SecondaryButton**: Nút thứ cấp cho các hành động phụ
- **PrimaryIconButton, SecondaryIconButton, FloatingIconButton**: Các nút chỉ có icon

### Cards
Các loại card để hiển thị thông tin sức khỏe:
- **MetricCard**: Hiển thị một chỉ số cụ thể (nhịp tim, bước chân, v.v.)
- **StatusCard**: Hiển thị trạng thái sức khỏe với thanh tiến trình
- **SummaryCard**: Tóm tắt nhiều chỉ số trong một card

## Cách sử dụng

### Theme
Để sử dụng theme, bọc ứng dụng trong `StudyAIHealthTheme`:

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyAIHealthTheme {
                // Nội dung ứng dụng
            }
        }
    }
}
```

### Truy cập giá trị Theme
Để đảm bảo tính nhất quán, hãy sử dụng các giá trị từ Theme:

```kotlin
@Composable
fun MyComponent() {
    val primaryColor = MaterialTheme.colorScheme.primary
    val typography = MaterialTheme.typography.titleLarge
    val standardPadding = MaterialTheme.dimens.contentPaddingMedium
    
    // Sử dụng các giá trị này trong UI
}
```

### Sử dụng Components
Các components được thiết kế để tái sử dụng và dễ dàng tùy chỉnh:

```kotlin
@Composable
fun HomeScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        StatusCard(
            title = "Sức khỏe tổng thể",
            description = "Dựa trên các chỉ số gần đây",
            status = HealthStatus.GOOD,
            progress = 0.75f,
            icon = AppIcons.Heart,
            onClick = { /* Xử lý click */ }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        MetricCard(
            title = "Nhịp tim",
            value = "72",
            unit = "bpm",
            icon = AppIcons.Heart,
            color = Heart,
            onClick = { /* Xử lý click */ }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        PrimaryButton(
            text = "Xem chi tiết",
            onClick = { /* Xử lý click */ }
        )
    }
}
```

## Dark Mode
Ứng dụng hỗ trợ cả Light Mode và Dark Mode với các màu sắc được tự động điều chỉnh. Theme sẽ theo thiết lập hệ thống hoặc có thể được chỉ định rõ ràng:

```kotlin
StudyAIHealthTheme(darkTheme = true) {
    // UI trong Dark Mode
}
```

## Material You Support
Trên Android 12 trở lên, ứng dụng hỗ trợ Material You để lấy màu sắc từ hình nền của người dùng. Tính năng này có thể tắt bằng tham số `dynamicColor`:

```kotlin
StudyAIHealthTheme(dynamicColor = false) {
    // UI không sử dụng Dynamic Color
}
```

## Accessibility
Các components được thiết kế có khả năng tiếp cận cao:
- Tỷ lệ tương phản đủ lớn giữa text và background
- Content description cho các icon và hình ảnh
- Hỗ trợ font size lớn

## Hướng dẫn chi tiết
Để biết thêm chi tiết về cách sử dụng từng component, hãy tham khảo:
- `app/src/main/java/com/studyai/health/ui/components/Help.md`: Hướng dẫn sử dụng UI components
- `app/src/main/java/com/studyai/health/ui/theme/README.md`: Chi tiết về hệ thống Theme

## Best Practices
1. Sử dụng các giá trị từ Theme thay vì hardcode
2. Tuân thủ hệ thống phân cấp visual (visual hierarchy)
3. Đảm bảo UI thích ứng với các kích thước màn hình khác nhau
4. Cung cấp phản hồi trực quan khi người dùng tương tác
5. Duy trì tính nhất quán về UI/UX trên toàn bộ ứng dụng 

# Tính năng đo SpO2 qua camera

## Tổng quan

Tính năng đo SpO2 (Đo nồng độ oxy trong máu) sử dụng camera và đèn flash của điện thoại để phân tích ánh sáng phản xạ từ ngón tay người dùng, từ đó ước tính nồng độ oxy trong máu.

## Cách sử dụng

1. **Khởi động tính năng**: Chọn "SpO2" từ menu chính hoặc màn hình chính
2. **Đọc và đồng ý miễn trừ trách nhiệm**: Đây không phải thiết bị y tế, chỉ dùng tham khảo
3. **Thực hiện đo**:
   - Đặt đầu ngón tay (thường là ngón trỏ) lên camera và đèn flash
   - Giữ yên ngón tay, không nhấn quá mạnh
   - Đảm bảo che kín cả camera và đèn flash
4. **Xem kết quả**: Sau khi hoàn thành, kết quả sẽ hiển thị với phân loại mức độ sức khỏe
5. **Lưu kết quả**: Chọn "Save Result" để lưu kết quả vào lịch sử

## Công nghệ và nguyên lý

- Sử dụng nguyên lý quang phổ (photoplethysmography - PPG)
- Phân tích tỷ lệ hấp thụ ánh sáng giữa kênh đỏ và kênh xanh lá
- Hemoglobin giàu oxy và nghèo oxy hấp thụ ánh sáng khác nhau
- Thuật toán phân tích tín hiệu để tính tỷ lệ R (tỷ lệ của tỷ lệ)
- Áp dụng công thức: SpO2 = 110 - 25 * R (công thức đơn giản hóa)

## Phân loại kết quả

- **Excellent (95-100%)**: Mức oxy trong máu bình thường
- **Average (90-94%)**: Oxy trong máu thấp, cần theo dõi
- **Bad (<90%)**: Oxy trong máu nguy hiểm, nên tham khảo ý kiến y tế

## Lưu ý

- Đây không phải thiết bị y tế được chứng nhận
- Chỉ nên dùng cho mục đích tham khảo
- Độ chính xác phụ thuộc vào chất lượng camera, ánh sáng và cách đặt ngón tay
- Nếu bạn gặp vấn đề về sức khỏe, hãy tham khảo ý kiến bác sĩ
- Nên thực hiện đo ở môi trường ánh sáng ổn định
- Tránh di chuyển ngón tay trong quá trình đo

## Cải thiện độ chính xác

- Thực hiện đo trong môi trường ánh sáng ổn định
- Đảm bảo ngón tay sạch và không quá lạnh
- Giữ ngón tay thật yên trong suốt quá trình đo
- Không nhấn quá mạnh lên camera
- Thực hiện nhiều lần đo và lấy giá trị trung bình

## Lưu trữ dữ liệu

Kết quả đo sẽ được lưu vào cơ sở dữ liệu cục bộ với các thông tin:
- Giá trị SpO2
- Thời gian đo
- Độ tin cậy của phép đo
- Ghi chú (nếu có) 