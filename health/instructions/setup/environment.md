# Thiết Lập Môi Trường Phát Triển

## Tổng Quan

Tài liệu này cung cấp hướng dẫn chi tiết để thiết lập môi trường phát triển cho ứng dụng StudyAI Health. Việc thiết lập đúng môi trường sẽ giúp quá trình phát triển diễn ra suôn sẻ và hiệu quả.

## Yêu Cầu Hệ Thống

### Phần Cứng Tối Thiểu
- CPU: Intel Core i5 hoặc tương đương (khuyến nghị i7 hoặc cao hơn)
- RAM: 8GB (khuyến nghị 16GB)
- Dung lượng trống: 10GB (khuyến nghị 20GB+)
- Kết nối internet ổn định

### Hệ Điều Hành Hỗ Trợ
- Windows 10/11 (64-bit)
- macOS 11.0+ (Big Sur trở lên)
- Linux (Ubuntu 20.04 LTS hoặc tương đương)

## Cài Đặt Android Studio

### Bước 1: Tải Android Studio
1. Truy cập [trang chủ Android Studio](https://developer.android.com/studio)
2. Tải phiên bản mới nhất (hiện tại khuyến nghị "Chipmunk" 2021.2.1 hoặc mới hơn)
3. Lưu file cài đặt vào máy tính

### Bước 2: Cài đặt Android Studio
**Windows**:
1. Chạy file .exe đã tải
2. Làm theo hướng dẫn cài đặt, chọn "Custom" để tùy chỉnh (nếu cần)
3. Chọn cài đặt Android Virtual Device (AVD) nếu muốn sử dụng máy ảo
4. Hoàn tất cài đặt

**macOS**:
1. Mở file .dmg đã tải
2. Kéo thả Android Studio vào thư mục Applications
3. Mở Android Studio từ Launchpad hoặc Applications
4. Làm theo hướng dẫn cài đặt, chọn "Custom" để tùy chỉnh (nếu cần)
5. Hoàn tất cài đặt

**Linux**:
1. Giải nén file .tar.gz đã tải
2. Mở Terminal, di chuyển đến thư mục bin trong thư mục đã giải nén
3. Chạy lệnh `./studio.sh`
4. Làm theo hướng dẫn cài đặt
5. Hoàn tất cài đặt

### Bước 3: Cài đặt SDK Components
1. Mở Android Studio
2. Tại màn hình chào mừng, chọn "More Actions" > "SDK Manager"
3. Trong tab "SDK Platforms", chọn các phiên bản Android cần thiết:
   - Android 12.0 (API 31)
   - Android 11.0 (API 30)
   - Android 10.0 (API 29)
   - Android 9.0 (API 28)
4. Trong tab "SDK Tools", chọn:
   - Android SDK Build-Tools
   - Android Emulator
   - Android SDK Platform-Tools
   - Google Play services
   - Intel x86 Emulator Accelerator (HAXM) nếu dùng Windows/macOS
5. Nhấn "Apply" để tải và cài đặt

## Cài Đặt JDK

Android Studio đi kèm với OpenJDK, nhưng để đảm bảo tính tương thích, nên cài đặt JDK 11 hoặc mới hơn:

### Windows:
1. Tải JDK từ [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) hoặc [AdoptOpenJDK](https://adoptopenjdk.net/)
2. Chạy file cài đặt và làm theo hướng dẫn
3. Thiết lập biến môi trường JAVA_HOME:
   - Mở Control Panel > System > Advanced System Settings
   - Chọn Environment Variables
   - Thêm biến JAVA_HOME với giá trị là đường dẫn cài đặt JDK (vd: C:\Program Files\Java\jdk-11.0.12)
   - Thêm %JAVA_HOME%\bin vào biến Path

### macOS:
1. Sử dụng Homebrew: `brew install --cask adoptopenjdk11`
2. Hoặc tải từ [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
3. Thiết lập JAVA_HOME trong ~/.bash_profile hoặc ~/.zshrc:
   ```
   export JAVA_HOME=$(/usr/libexec/java_home -v 11)
   export PATH=$JAVA_HOME/bin:$PATH
   ```

### Linux:
1. Ubuntu/Debian: `sudo apt install openjdk-11-jdk`
2. Fedora/CentOS: `sudo dnf install java-11-openjdk-devel`
3. Thiết lập JAVA_HOME trong ~/.bashrc:
   ```
   export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
   export PATH=$JAVA_HOME/bin:$PATH
   ```

## Cài Đặt Git

### Windows:
1. Tải [Git for Windows](https://git-scm.com/download/win)
2. Chạy file cài đặt, chọn các tùy chọn phù hợp (khuyến nghị giữ mặc định)
3. Mở Git Bash để xác nhận cài đặt: `git --version`

### macOS:
1. Cài qua Homebrew: `brew install git`
2. Hoặc tải từ [trang chủ Git](https://git-scm.com/download/mac)
3. Kiểm tra cài đặt: `git --version`

### Linux:
1. Ubuntu/Debian: `sudo apt install git`
2. Fedora/CentOS: `sudo dnf install git`
3. Kiểm tra cài đặt: `git --version`

## Clone Dự Án

1. Mở Terminal hoặc Command Prompt
2. Di chuyển đến thư mục muốn lưu dự án
3. Chạy lệnh:
   ```
   git clone [URL_REPOSITORY]
   ```
   (Thay [URL_REPOSITORY] bằng URL thực tế của dự án)
4. Di chuyển vào thư mục dự án:
   ```
   cd StudyAI/health
   ```

## Cấu Hình Dự Án

### Bước 1: Sync Gradle Dependencies
1. Mở dự án trong Android Studio
2. Android Studio sẽ tự động bắt đầu quá trình sync Gradle
3. Nếu không tự động, chọn "Sync Project with Gradle Files" từ toolbar
4. Đợi quá trình sync hoàn tất

### Bước 2: Cấu hình API Key
1. Tạo file `local.properties` tại thư mục gốc của dự án (nếu chưa có)
2. Thêm các API key vào file (nếu cần):
   ```
   sdk.dir=/path/to/Android/sdk
   api.key.health=YOUR_HEALTH_API_KEY
   api.key.ai=YOUR_AI_API_KEY
   ```
3. Lưu file và đóng

### Bước 3: Thiết lập Emulator
1. Mở AVD Manager trong Android Studio (Tools > AVD Manager)
2. Chọn "Create Virtual Device"
3. Chọn loại thiết bị (khuyến nghị Pixel 4 hoặc tương đương)
4. Chọn System Image (khuyến nghị API 30 trở lên)
5. Tùy chỉnh cấu hình nếu cần và hoàn tất thiết lập

### Bước 4: Kết nối Thiết bị Thật (tùy chọn)
1. Bật "Developer options" trên thiết bị Android:
   - Vào Settings > About phone
   - Nhấn "Build number" 7 lần
   - Quay lại Settings, sẽ thấy "Developer options"
2. Trong Developer options, bật "USB debugging"
3. Kết nối thiết bị với máy tính qua USB
4. Chấp nhận yêu cầu "Allow USB debugging" trên thiết bị

## Khởi Động Ứng Dụng

1. Mở Android Studio và dự án
2. Chọn thiết bị (emulator hoặc thiết bị thật) từ dropdown
3. Nhấn nút "Run" (biểu tượng Play) hoặc Shift+F10
4. Đợi ứng dụng được build và cài đặt
5. Ứng dụng sẽ tự động khởi động trên thiết bị đã chọn

## Thiết Lập IDE (Tùy Chọn)

### Tùy Chỉnh Editor
1. Mở Settings/Preferences (Ctrl+Alt+S)
2. Trong Editor > Font, thiết lập font và size phù hợp
3. Trong Editor > Color Scheme, chọn scheme phù hợp
4. Trong Editor > Code Style > Kotlin, thiết lập các quy tắc định dạng code

### Cài Đặt Plugin Hữu Ích
1. Mở Settings/Preferences > Plugins
2. Tìm và cài đặt các plugin sau:
   - Kotlin
   - Material Theme UI (tùy chọn)
   - Rainbow Brackets (tùy chọn)
   - String Manipulation (tùy chọn)
   - Git Integration (đã được cài sẵn)
   - Database Navigator (tùy chọn, cho việc kiểm tra Room Database)

## Xử Lý Sự Cố Thường Gặp

### Gradle Build Failed
1. Kiểm tra kết nối internet
2. Kiểm tra phiên bản Gradle trong `gradle-wrapper.properties`
3. Thử "File > Invalidate Caches / Restart"
4. Kiểm tra file `build.gradle` xem có lỗi cú pháp không

### Emulator Không Khởi Động
1. Kiểm tra RAM và dung lượng trống
2. Đảm bảo Virtualization được bật trong BIOS
3. Kiểm tra xem HAXM đã được cài đặt
4. Thử tạo emulator mới với cấu hình thấp hơn

### Thiết Bị Thật Không Được Nhận Diện
1. Đảm bảo USB debugging đã được bật
2. Thử đổi cáp USB hoặc cổng USB
3. Cài đặt driver USB cho thiết bị (chỉ trên Windows)
4. Khởi động lại Android Studio và thiết bị

## Thông Tin Bổ Sung

### Tài Liệu Tham Khảo
- [Android Developer Documentation](https://developer.android.com/docs)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Android Studio User Guide](https://developer.android.com/studio/intro)

### Công Cụ Hữu Ích
- [Postman](https://www.postman.com/): Kiểm tra API
- [Android Debug Bridge (adb)](https://developer.android.com/studio/command-line/adb): Tương tác với thiết bị qua command line
- [ScreenRecorder](https://developer.android.com/studio/command-line/adb#screenrecord): Ghi lại màn hình thiết bị
- [Logcat](https://developer.android.com/studio/debug/am-logcat): Xem log ứng dụng

## Tiếp Theo

Sau khi thiết lập môi trường phát triển thành công, bạn có thể bắt đầu làm việc với các tính năng của ứng dụng. Hãy tham khảo [Quy Trình Làm Việc](../workflow/process.md) để biết thêm chi tiết về cách tiếp cận và phát triển các tính năng. 