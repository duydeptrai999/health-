# Thư Viện Components

## Tổng Quan

Thư viện components của StudyAI Health cung cấp các thành phần UI tái sử dụng được xây dựng với Jetpack Compose, giúp đảm bảo tính nhất quán và hiệu quả trong phát triển giao diện người dùng.

## Các Nguyên Tắc

1. **Compose First**: Tất cả components được xây dựng với Jetpack Compose
2. **Tái Sử Dụng**: Thiết kế để sử dụng lại trên toàn ứng dụng
3. **Theme-Aware**: Tự động điều chỉnh theo theme (light/dark)
4. **Responsive**: Thích ứng với các kích thước màn hình khác nhau
5. **Accessible**: Hỗ trợ accessibility đầy đủ

## Core Components

### 1. StudyAIButton

**Mô tả**: Button tuỳ chỉnh với các biến thể khác nhau.

**Variants**:
- Primary: Filled button với màu Primary
- Secondary: Outlined button với màu Primary
- Text: Text-only button
- Icon: Button kèm icon

**Thuộc tính**:
- `text`: Văn bản hiển thị
- `onClick`: Lambda function khi click
- `enabled`: Trạng thái enable/disable
- `loading`: Hiển thị loading indicator thay vì text
- `leadingIcon`/`trailingIcon`: Icons (tùy chọn)

**Sử dụng**:
```kotlin
StudyAIButton(
    text = "Bắt Đầu Đo",
    onClick = { /* action */ },
    variant = ButtonVariant.PRIMARY,
    enabled = true
)
```

### 2. StudyAICard

**Mô tả**: Card hiển thị thông tin với các layout được định nghĩa sẵn.

**Variants**:
- Standard: Card tiêu chuẩn
- Elevated: Card với shadow mạnh hơn
- Outlined: Card với viền thay vì shadow
- Interactive: Card nhấn được

**Thuộc tính**:
- `title`: Tiêu đề card (tùy chọn)
- `subtitle`: Phụ đề (tùy chọn)
- `content`: Composable content
- `onClick`: Lambda function khi click (cho Interactive)
- `actions`: Composable cho phần actions (tùy chọn)

**Sử dụng**:
```kotlin
StudyAICard(
    title = "Nhịp Tim",
    subtitle = "Cập nhật gần nhất: 10:30",
    onClick = { /* action */ }
) {
    // Content here
}
```

### 3. StudyAITextField

**Mô tả**: Text field tuỳ chỉnh với validation và states.

**Variants**:
- Outlined: TextField với outline
- Filled: TextField với background filled

**Thuộc tính**:
- `value`: Giá trị hiện tại
- `onValueChange`: Lambda cho thay đổi giá trị
- `label`: Label text
- `placeholder`: Placeholder text
- `error`: Error message (null nếu không có lỗi)
- `leadingIcon`/`trailingIcon`: Icons (tùy chọn)
- `keyboardType`: Loại bàn phím

**Sử dụng**:
```kotlin
StudyAITextField(
    value = text,
    onValueChange = { text = it },
    label = "Tên người dùng",
    error = if (text.isEmpty()) "Không được để trống" else null
)
```

### 4. StudyAITopBar

**Mô tả**: Top app bar với các cấu hình khác nhau.

**Variants**:
- Default: Tiêu đề và back button
- Search: Với search field
- Contextual: Hiển thị khi đa chọn

**Thuộc tính**:
- `title`: Tiêu đề
- `onBackClick`: Lambda cho nút back
- `actions`: Composable cho menu actions
- `elevation`: Mức độ shadow

**Sử dụng**:
```kotlin
StudyAITopBar(
    title = "Lịch Sử Đo",
    onBackClick = { navController.popBackStack() },
    actions = {
        IconButton(onClick = { /* action */ }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
        }
    }
)
```

### 5. StudyAIBottomNavigation

**Mô tả**: Bottom navigation bar với các tab navigation chính.

**Thuộc tính**:
- `items`: Danh sách các navigation items
- `selectedItemIndex`: Index của item được chọn
- `onItemSelected`: Lambda khi chọn item

**Sử dụng**:
```kotlin
StudyAIBottomNavigation(
    items = listOf(
        BottomNavItem("Trang chủ", Icons.Default.Home),
        BottomNavItem("Đo lường", Icons.Default.FavoriteBoard),
        BottomNavItem("Lịch sử", Icons.Default.History),
        BottomNavItem("Tư vấn", Icons.Default.Chat)
    ),
    selectedItemIndex = selectedTab,
    onItemSelected = { selectedTab = it }
)
```

## Health Components

### 1. HealthMetricDisplay

**Mô tả**: Hiển thị chỉ số sức khỏe với các thang đo trực quan.

**Variants**:
- Circular: Gauge tròn với kim chỉ giá trị
- Linear: Progressbar linear
- Numeric: Hiển thị số với đơn vị

**Thuộc tính**:
- `value`: Giá trị hiện tại
- `minValue`/`maxValue`: Giới hạn thang đo
- `label`: Nhãn chỉ số
- `unit`: Đơn vị đo (bpm, %, etc.)
- `warningThreshold`/`dangerThreshold`: Ngưỡng cảnh báo
- `textStyle`: Typography style

**Sử dụng**:
```kotlin
HealthMetricDisplay(
    value = 72f,
    minValue = 40f,
    maxValue = 200f,
    label = "Nhịp Tim",
    unit = "bpm",
    variant = MetricVariant.CIRCULAR
)
```

### 2. HealthHistoryChart

**Mô tả**: Biểu đồ hiển thị dữ liệu sức khỏe theo thời gian.

**Variants**:
- Line: Biểu đồ đường
- Bar: Biểu đồ cột
- Area: Biểu đồ filled area

**Thuộc tính**:
- `data`: Danh sách dữ liệu [(timestamp, value)]
- `timeRange`: Khoảng thời gian hiển thị
- `valueLabel`: Nhãn giá trị
- `timeLabel`: Nhãn thời gian
- `thresholds`: Danh sách các threshold hiển thị
- `interactive`: Cho phép zoom/pan

**Sử dụng**:
```kotlin
HealthHistoryChart(
    data = heartRateData,
    timeRange = TimeRange.WEEK,
    valueLabel = "Nhịp tim (bpm)",
    variant = ChartVariant.LINE,
    interactive = true
)
```

### 3. CameraPreviewBox

**Mô tả**: Component hiển thị camera preview cho các tính năng đo lường.

**Thuộc tính**:
- `onFrameAnalyzed`: Callback khi phân tích frame
- `overlayContent`: Composable hiển thị phủ lên camera
- `guideText`: Text hướng dẫn đặt ngón tay
- `flashEnabled`: Điều khiển đèn flash
- `analyzerType`: Loại phân tích (HeartRate, SpO2)

**Sử dụng**:
```kotlin
CameraPreviewBox(
    onFrameAnalyzed = { frame -> analyzer.processFrame(frame) },
    flashEnabled = true,
    guideText = "Đặt ngón tay vào camera và giữ yên",
    analyzerType = AnalyzerType.HEART_RATE
)
```

### 4. MeasurementProgressIndicator

**Mô tả**: Hiển thị tiến trình đo với animation và countdown.

**Thuộc tính**:
- `progress`: Giá trị tiến trình (0-100)
- `timeRemaining`: Thời gian còn lại (giây)
- `currentValue`: Giá trị đo được hiện tại
- `status`: Trạng thái đo (Preparing, Measuring, Finalizing)

**Sử dụng**:
```kotlin
MeasurementProgressIndicator(
    progress = progressState,
    timeRemaining = 25,
    currentValue = "72",
    status = MeasurementStatus.MEASURING
)
```

### 5. AIMessageBubble

**Mô tả**: Hiển thị tin nhắn trong chat với AI.

**Variants**:
- User: Tin nhắn người dùng
- Assistant: Tin nhắn từ AI
- SystemMessage: Thông báo hệ thống

**Thuộc tính**:
- `text`: Nội dung tin nhắn
- `timestamp`: Thời gian tin nhắn
- `status`: Trạng thái tin nhắn (Sending, Sent, Read)
- `actions`: Các hành động đính kèm (tùy chọn)

**Sử dụng**:
```kotlin
AIMessageBubble(
    text = "Bạn nên nghỉ ngơi khi nhịp tim cao hơn bình thường.",
    timestamp = System.currentTimeMillis(),
    variant = MessageVariant.ASSISTANT
)
```

## Layout Components

### 1. PageScaffold

**Mô tả**: Layout scaffold chuẩn cho các màn hình.

**Thuộc tính**:
- `topBar`: Composable cho top bar (tùy chọn)
- `bottomBar`: Composable cho bottom bar (tùy chọn)
- `snackbarHost`: Host cho snackbars
- `floatingActionButton`: FAB (tùy chọn)
- `content`: Main content

**Sử dụng**:
```kotlin
PageScaffold(
    topBar = { StudyAITopBar(title = "Trang Chủ") },
    bottomBar = { StudyAIBottomNavigation(...) }
) {
    // Page content
}
```

### 2. SectionContainer

**Mô tả**: Container cho một phần nội dung với tiêu đề.

**Thuộc tính**:
- `title`: Tiêu đề section
- `subtitle`: Phụ đề (tùy chọn)
- `action`: Action button/link (tùy chọn)
- `content`: Nội dung section

**Sử dụng**:
```kotlin
SectionContainer(
    title = "Đo Lường Gần Đây",
    action = {
        TextButton(onClick = { /* action */ }) {
            Text("Xem tất cả")
        }
    }
) {
    // Section content
}
```

### 3. EmptyStateView

**Mô tả**: Hiển thị trạng thái trống (không có dữ liệu).

**Thuộc tính**:
- `icon`: Icon hiển thị
- `title`: Tiêu đề 
- `message`: Thông điệp chi tiết
- `action`: Action button (tùy chọn)

**Sử dụng**:
```kotlin
EmptyStateView(
    icon = Icons.Default.Timeline,
    title = "Chưa có dữ liệu",
    message = "Bạn chưa thực hiện đo lường nào",
    action = {
        StudyAIButton(
            text = "Đo Ngay",
            onClick = { /* action */ }
        )
    }
)
```

### 4. LoadingStateView

**Mô tả**: Hiển thị trạng thái đang tải.

**Variants**:
- Overlay: Hiển thị phủ toàn màn hình
- Inline: Hiển thị trong content flow
- Shimmer: Effect shimmer cho skeleton loading

**Thuộc tính**:
- `message`: Thông điệp loading (tùy chọn)
- `cancelable`: Cho phép hủy (tùy chọn)
- `onCancel`: Lambda khi hủy

**Sử dụng**:
```kotlin
LoadingStateView(
    message = "Đang tải dữ liệu...",
    variant = LoadingVariant.OVERLAY
)
```

### 5. ErrorStateView

**Mô tả**: Hiển thị trạng thái lỗi.

**Thuộc tính**:
- `icon`: Icon lỗi
- `title`: Tiêu đề lỗi
- `message`: Thông điệp chi tiết
- `actionLabel`: Nhãn nút hành động
- `onAction`: Lambda khi click action

**Sử dụng**:
```kotlin
ErrorStateView(
    title = "Không thể kết nối",
    message = "Vui lòng kiểm tra kết nối mạng và thử lại",
    actionLabel = "Thử lại",
    onAction = { viewModel.retry() }
)
```

## Feedback Components

### 1. StudyAISnackbar

**Mô tả**: Snackbar tùy chỉnh với các biến thể khác nhau.

**Variants**:
- Info: Thông tin thông thường
- Success: Thành công
- Warning: Cảnh báo
- Error: Lỗi

**Thuộc tính**:
- `message`: Thông điệp chính
- `action`: Action text (tùy chọn)
- `onAction`: Lambda khi click action
- `duration`: Thời gian hiển thị

**Sử dụng**:
```kotlin
StudyAISnackbar(
    message = "Đã lưu kết quả thành công",
    action = "Xem",
    onAction = { navController.navigate("history") },
    variant = SnackbarVariant.SUCCESS
)
```

### 2. DialogBox

**Mô tả**: Dialog box tùy chỉnh cho các tình huống khác nhau.

**Variants**:
- Confirmation: Xác nhận hành động
- Input: Nhập dữ liệu
- Selection: Chọn từ danh sách
- Info: Hiển thị thông tin

**Thuộc tính**:
- `title`: Tiêu đề dialog
- `message`: Nội dung chi tiết
- `confirmText`: Text nút xác nhận
- `dismissText`: Text nút hủy
- `onConfirm`/`onDismiss`: Lambda khi xác nhận/hủy
- `content`: Custom content (tùy chọn)

**Sử dụng**:
```kotlin
DialogBox(
    title = "Xóa kết quả đo",
    message = "Bạn có chắc muốn xóa kết quả đo này?",
    confirmText = "Xóa",
    dismissText = "Hủy",
    onConfirm = { viewModel.deleteRecord(recordId) },
    variant = DialogVariant.CONFIRMATION
)
```

### 3. TooltipBox

**Mô tả**: Tooltip hiển thị thông tin bổ sung khi hover/long press.

**Thuộc tính**:
- `tooltip`: Nội dung tooltip
- `placementOffset`: Vị trí hiển thị offset
- `delayMillis`: Độ trễ hiển thị (ms)
- `content`: Content được bao bọc

**Sử dụng**:
```kotlin
TooltipBox(
    tooltip = { Text("Nhịp tim ổn định") },
    delayMillis = 500
) {
    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "Heart Rate"
    )
}
```

## Input Components

### 1. DateTimePicker

**Mô tả**: Bộ chọn ngày/giờ tùy chỉnh.

**Variants**:
- Date: Chọn ngày
- Time: Chọn giờ
- DateTime: Chọn cả ngày và giờ

**Thuộc tính**:
- `value`: Giá trị hiện tại
- `onValueChange`: Lambda khi thay đổi
- `minValue`/`maxValue`: Giới hạn chọn
- `format`: Định dạng hiển thị

**Sử dụng**:
```kotlin
DateTimePicker(
    value = selectedDate,
    onValueChange = { selectedDate = it },
    minValue = minDate,
    variant = DateTimeVariant.DATE
)
```

### 2. FilterChipGroup

**Mô tả**: Nhóm các chip dùng cho lọc dữ liệu.

**Thuộc tính**:
- `items`: Danh sách các item
- `selectedItems`: Danh sách items đã chọn
- `onSelectionChanged`: Lambda khi selection thay đổi
- `singleSelection`: Chỉ cho phép chọn một

**Sử dụng**:
```kotlin
FilterChipGroup(
    items = listOf("Nhịp tim", "SpO2", "Căng thẳng"),
    selectedItems = selectedFilters,
    onSelectionChanged = { selectedFilters = it }
)
```

### 3. SearchBar

**Mô tả**: Search bar với various capabilities.

**Thuộc tính**:
- `query`: Query hiện tại
- `onQueryChange`: Lambda khi query thay đổi
- `onSearch`: Lambda khi submit search
- `placeholder`: Placeholder text
- `filters`: Filter options (tùy chọn)

**Sử dụng**:
```kotlin
SearchBar(
    query = searchQuery,
    onQueryChange = { searchQuery = it },
    onSearch = { viewModel.search(searchQuery) },
    placeholder = "Tìm kiếm trong lịch sử..."
)
```

## Data Display Components

### 1. MetricSummaryCard

**Mô tả**: Card hiển thị tóm tắt một chỉ số sức khỏe.

**Thuộc tính**:
- `title`: Tên chỉ số
- `value`: Giá trị hiện tại
- `unit`: Đơn vị đo
- `trend`: Xu hướng (Up, Down, Stable)
- `trendValue`: Giá trị thay đổi
- `icon`: Icon đại diện
- `color`: Màu chủ đạo

**Sử dụng**:
```kotlin
MetricSummaryCard(
    title = "Nhịp Tim",
    value = 72f,
    unit = "bpm",
    trend = Trend.STABLE,
    icon = Icons.Default.Favorite,
    color = MaterialTheme.colors.primary
)
```

### 2. TimelineView

**Mô tả**: Hiển thị timeline các sự kiện.

**Thuộc tính**:
- `items`: Danh sách các timeline items
- `onItemClick`: Lambda khi click item
- `groupBy`: Cách nhóm items (Day, Week, Month)

**Sử dụng**:
```kotlin
TimelineView(
    items = healthEvents,
    onItemClick = { navController.navigate("detail/${it.id}") },
    groupBy = TimelineGroupBy.DAY
)
```

### 3. ComparisonTable

**Mô tả**: Bảng so sánh dữ liệu.

**Thuộc tính**:
- `headers`: Danh sách headers
- `rows`: Danh sách các hàng dữ liệu
- `onRowClick`: Lambda khi click row

**Sử dụng**:
```kotlin
ComparisonTable(
    headers = listOf("Ngày", "Nhịp tim", "SpO2", "Căng thẳng"),
    rows = weeklyData
)
```

## Animation Components

### 1. AnimatedCounter

**Mô tả**: Hiển thị số với animation đếm.

**Thuộc tính**:
- `targetValue`: Giá trị đích
- `duration`: Thời lượng animation
- `formatter`: Định dạng hiển thị số

**Sử dụng**:
```kotlin
AnimatedCounter(
    targetValue = heartRate,
    duration = 1000,
    formatter = { "$it bpm" }
)
```

### 2. PulseAnimation

**Mô tả**: Animation nhịp đập cho hiển thị nhịp tim.

**Thuộc tính**:
- `beatsPerMinute`: Nhịp tim (BPM)
- `color`: Màu animation
- `size`: Kích thước

**Sử dụng**:
```kotlin
PulseAnimation(
    beatsPerMinute = 72f,
    color = Color.Red,
    size = 100.dp
)
```

## Triển Khai

### Component Library Structure

```
com.studyai.health.ui.components/
├── core/              # Core components
├── health/            # Health-specific components
├── layout/            # Layout components
├── feedback/          # Feedback components
├── input/             # Input components
├── display/           # Data display components
├── animation/         # Animation components
└── theme/             # Theme components
```

### Sử Dụng MaterialTheme

Tất cả components phải sử dụng MaterialTheme để đảm bảo nhất quán:

```kotlin
@Composable
fun StudyAIButton(
    // params
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        // other settings
    ) {
        // content
    }
}
```

### Composable Preview

Mỗi component phải có Preview để kiểm tra:

```kotlin
@Preview(showBackground = true)
@Composable
fun StudyAIButtonPreview() {
    StudyAITheme {
        Column(Modifier.padding(16.dp)) {
            StudyAIButton(
                text = "Primary Button",
                onClick = {},
                variant = ButtonVariant.PRIMARY
            )
            Spacer(Modifier.height(8.dp))
            StudyAIButton(
                text = "Secondary Button",
                onClick = {},
                variant = ButtonVariant.SECONDARY
            )
        }
    }
}
```

## Testing

### Unit Tests

Mỗi component cần có Unit Tests để kiểm tra behavior:

```kotlin
@Test
fun studyAIButton_whenClicked_callsOnClick() {
    // Test code
}

@Test
fun studyAIButton_whenDisabled_hasCorrectAppearance() {
    // Test code
}
```

### UI Tests

Các component quan trọng cần có UI Tests:

```kotlin
@Test
fun healthMetricDisplay_showsCorrectValueAndColor() {
    // Test code
}
```

## Thiết Kế Responsiveness

Tất cả components phải responsive với việc sử dụng:

1. **ConstraintLayout** cho layouts phức tạp
2. **Dynamic Sizing** với `fillMaxWidth()`, `weight()`
3. **Screen Size Awareness** với các composition locals

```kotlin
@Composable
fun ResponsiveComponent() {
    BoxWithConstraints {
        if (maxWidth < 600.dp) {
            MobileLayout()
        } else {
            TabletLayout()
        }
    }
}
```

## Accessibility

Components phải đảm bảo:

1. **Content Description** cho tất cả UI elements
2. **Semantic Properties** cho screen readers
3. **Large Touch Targets** (tối thiểu 48dp)
4. **High Contrast** giữa text và background

```kotlin
Icon(
    imageVector = Icons.Default.Favorite,
    contentDescription = "Heart Rate",
    modifier = Modifier.semantics { contentDescription = "Heart Rate Icon" }
)
```

## Kết Hợp Component

Ví dụ kết hợp các components:

```kotlin
@Composable
fun HeartRateMeasurementScreen(viewModel: HeartRateViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    
    PageScaffold(
        topBar = { 
            StudyAITopBar(
                title = "Đo Nhịp Tim",
                onBackClick = { /* navigate back */ }
            )
        }
    ) {
        when (uiState) {
            is LoadingState -> LoadingStateView()
            is MeasuringState -> {
                CameraPreviewBox(
                    onFrameAnalyzed = viewModel::processFrame,
                    flashEnabled = true
                )
                MeasurementProgressIndicator(
                    progress = (uiState as MeasuringState).progress,
                    timeRemaining = (uiState as MeasuringState).timeRemaining,
                    currentValue = (uiState as MeasuringState).currentValue.toString()
                )
            }
            is ResultState -> {
                MetricSummaryCard(
                    title = "Nhịp Tim",
                    value = (uiState as ResultState).heartRate.toFloat(),
                    unit = "bpm"
                )
                StudyAIButton(
                    text = "Lưu Kết Quả",
                    onClick = viewModel::saveResult
                )
            }
            is ErrorState -> {
                ErrorStateView(
                    title = "Lỗi Đo Lường",
                    message = (uiState as ErrorState).message,
                    actionLabel = "Thử Lại",
                    onAction = viewModel::retry
                )
            }
        }
    }
} 