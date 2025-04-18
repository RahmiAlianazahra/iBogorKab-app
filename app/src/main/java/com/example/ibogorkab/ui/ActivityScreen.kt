package com.example.ibogorkab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import com.example.ibogorkab.ui.theme.PaleGreen

// Data class for activity item
data class ActivityItem(
    val id: Int,
    val userName: String,
    val userImage: Int?,
    val userInitial: String = "",
    val timeAgo: String,
    val activityType: String,
    val bookTitle: String,
    val bookAuthor: String,
    val bookCover: Int,
    val bookRating: Float
)

@Composable
fun ActivityScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToBookshelf: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)
    val white = Color.White
    val backgroundColor = PaleGreen
    val yellow = Color(0xFFFFD700)

    // Define font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Sample data
    val activities = listOf(
        ActivityItem(
            id = 1,
            userName = "Rahmialz",
            userImage = R.drawable.profile_picture,
            userInitial = "",
            timeAgo = "2 jam lalu",
            activityType = "Meminjam buku",
            bookTitle = "Edensor",
            bookAuthor = "Andrea Hirata",
            bookCover = R.drawable.edensor,
            bookRating = 5.0f
        ),
        ActivityItem(
            id = 2,
            userName = "Fahriyan",
            userImage = null,
            userInitial = "F",
            timeAgo = "3 jam lalu",
            activityType = "Meminjam buku",
            bookTitle = "Laut Bercerita",
            bookAuthor = "Leila S. Chudori",
            bookCover = R.drawable.laut_bercerita,
            bookRating = 4.0f
        ),
        ActivityItem(
            id = 3,
            userName = "Assyifa",
            userImage = null,
            userInitial = "A",
            timeAgo = "3 jam lalu",
            activityType = "Meminjam buku",
            bookTitle = "The Midnight Library",
            bookAuthor = "Matt Haig",
            bookCover = R.drawable.midnight_library,
            bookRating = 5.0f
        ),
        ActivityItem(
            id = 4,
            userName = "Gatfan",
            userImage = null,
            userInitial = "G",
            timeAgo = "5 jam lalu",
            activityType = "Meminjam buku",
            bookTitle = "Filosofi Teras",
            bookAuthor = "Henry Manampiring",
            bookCover = R.drawable.filosofi_teras,
            bookRating = 4.0f
        ),
        ActivityItem(
            id = 5,
            userName = "Queenna",
            userImage = R.drawable.profile_picture,
            userInitial = "Q",
            timeAgo = "1 hari lalu",
            activityType = "Meminjam buku",
            bookTitle = "The Kite Runner",
            bookAuthor = "Khaled Hosseini",
            bookCover = R.drawable.the_kite_runner,
            bookRating = 5.0f
        )
    )

    // Selected navigation item state
    var selectedNavItem by remember { mutableStateOf(1) } // 1 for Activity tab

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                NavigationBarItem(
                    selected = selectedNavItem == 0,
                    onClick = {
                        selectedNavItem = 0
                        onNavigateToHome()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Beranda"
                        )
                    },
                    label = {
                        Text(
                            "Beranda",
                            fontFamily = interFontFamily
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkGreen,
                        selectedTextColor = DarkGreen,
                        indicatorColor = Color.White
                    )
                )

                NavigationBarItem(
                    selected = selectedNavItem == 1,
                    onClick = { selectedNavItem = 1 },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_access_time_24),
                            contentDescription = "Aktivitas"
                        )
                    },
                    label = {
                        Text(
                            "Aktivitas",
                            fontFamily = interFontFamily
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkGreen,
                        selectedTextColor = DarkGreen,
                        indicatorColor = Color.White
                    )
                )

                NavigationBarItem(
                    selected = selectedNavItem == 2,
                    onClick = {
                        selectedNavItem = 2
                        onNavigateToBookshelf()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = "Rak Buku"
                        )
                    },
                    label = {
                        Text(
                            "Rak Buku",
                            fontFamily = interFontFamily
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkGreen,
                        selectedTextColor = DarkGreen,
                        indicatorColor = Color.White
                    )
                )

                NavigationBarItem(
                    selected = selectedNavItem == 3,
                    onClick = {
                        selectedNavItem = 3
                        onNavigateToProfile()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Profil"
                        )
                    },
                    label = {
                        Text(
                            "Profil",
                            fontFamily = interFontFamily
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkGreen,
                        selectedTextColor = DarkGreen,
                        indicatorColor = Color.White
                    )
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Activity Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(white)
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = "Aktivitas",
                        color = DarkGreen,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFontFamily,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // Activity List
                LazyColumn(
                    modifier = Modifier.fillMaxSize(
                    )
                ) {
                    items(activities) { activity ->
                        ActivityItemCard(
                            activity = activity,
                            interFontFamily = interFontFamily,
                            highlightColor = DarkGreen,
                            starColor = yellow
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityItemCard(
    activity: ActivityItem,
    interFontFamily: FontFamily,
    highlightColor: Color,
    starColor: Color
) {
    var isLiked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // User info row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // User avatar (profile picture or initial)
                if (activity.userImage != null) {
                    Image(
                        painter = painterResource(id = activity.userImage),
                        contentDescription = activity.userName,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(highlightColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = activity.userInitial,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = interFontFamily
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // User name and activity
                Column {
                    Text(
                        text = activity.userName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFontFamily
                    )
                    Text(
                        text = activity.activityType,
                        fontSize = 14.sp,
                        fontFamily = interFontFamily,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Time ago
                Text(
                    text = activity.timeAgo,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = interFontFamily
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Book info row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Book cover
                Image(
                    painter = painterResource(id = activity.bookCover),
                    contentDescription = activity.bookTitle,
                    modifier = Modifier
                        .width(80.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Book details
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = activity.bookTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = interFontFamily
                    )

                    Text(
                        text = activity.bookAuthor,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = interFontFamily
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Rating stars
                    Row {
                        repeat(5) { index ->
                            val filled = index < activity.bookRating
                            Icon(
                                painter = painterResource(
                                    id = if (filled) R.drawable.baseline_star_24 else R.drawable.baseline_star_24
                                ),
                                contentDescription = null,
                                tint = if (filled) starColor else Color.LightGray,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            // Action buttons row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                // Comment button
                IconButton(
                    onClick = { /* Show comment dialog */ },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chat_bubble_outline_24),
                        contentDescription = "Comment",
                        tint = Color.Gray
                    )
                }

                // Like button
                IconButton(
                    onClick = { isLiked = !isLiked },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isLiked) R.drawable.baseline_thumb_up_alt_24 else R.drawable.baseline_thumb_up_alt_24
                        ),
                        contentDescription = "Like",
                        tint = if (isLiked) highlightColor else Color.Gray
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ActivityScreenPreview() {
    ActivityScreen()
}