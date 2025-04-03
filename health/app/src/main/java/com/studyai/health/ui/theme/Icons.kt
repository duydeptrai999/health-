package com.studyai.health.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings

/**
 * App Icons - Centralizing icon access in the app
 * Using Material icons by default, but can be extended with custom icons
 */
object AppIcons {
    // Basic UI Icons
    val Back = Icons.Default.ArrowBack
    val Forward = Icons.Default.ArrowForward
    val Close = Icons.Default.Close
    val Add = Icons.Default.Add
    val Edit = Icons.Default.Edit
    val Delete = Icons.Default.Delete
    val Done = Icons.Default.Done
    val Menu = Icons.Default.Menu
    val Search = Icons.Default.Search
    val Share = Icons.Default.Share
    
    // Navigation Icons - Filled versions
    val HomeFilled = Icons.Filled.Home
    val FavoriteFilled = Icons.Filled.Favorite
    val ProfileFilled = Icons.Filled.Person
    val SettingsFilled = Icons.Filled.Settings
    val NotificationsFilled = Icons.Filled.Notifications
    
    // Navigation Icons - Outlined versions
    val HomeOutlined = Icons.Outlined.Home
    val FavoriteOutlined = Icons.Outlined.FavoriteBorder
    val ProfileOutlined = Icons.Outlined.Person
    val SettingsOutlined = Icons.Outlined.Settings
    val NotificationsOutlined = Icons.Outlined.Notifications
    
    // Navigation Icons - Rounded versions
    val HomeRounded = Icons.Rounded.Home
    val FavoriteRounded = Icons.Rounded.Favorite
    val ProfileRounded = Icons.Rounded.Person
    val SettingsRounded = Icons.Rounded.Settings
    
    // Utility Icons
    val Calendar = Icons.Default.DateRange
    val CalendarOutlined = Icons.Outlined.DateRange
    val Location = Icons.Default.LocationOn
    val Star = Icons.Default.Star
    val Info = Icons.Default.Info
    val InfoOutlined = Icons.Outlined.Info
    
    // Profile related
    val Account = Icons.Default.AccountCircle
    val AccountOutlined = Icons.Outlined.AccountCircle
    
    // Health specific icons can be added here
    // These would typically be custom icons or extensions of the Material icons
    val Heart = Icons.Default.Favorite
    val HeartOutline = Icons.Default.FavoriteBorder
    
    // Additional icons can be added as needed
} 