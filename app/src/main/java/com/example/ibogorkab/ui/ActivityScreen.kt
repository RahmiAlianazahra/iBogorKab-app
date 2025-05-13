package com.example.ibogorkab.ui

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R
import com.example.ibogorkab.ui.theme.PaleGreen

// Data class for activity item
data class ActivityItem(
    val id: Int,
    val userName: String,
    val userImage: Int?,
    val userInitial: String = "",
    val timeAgo: String,
    val activityType: ActivityType,
    val bookTitle: String,
    val bookAuthor: String,
    val bookCover: Int,
    val bookRating: Float,
    val reviewText: String = "",
    var likes: Int = 0,
    var comments: MutableList<Comment> = mutableStateListOf()
)

// Data class for comments
data class Comment(
    val id: Int,
    val userName: String,
    val text: String,
    val timestamp: String
)

// Enum for activity types
enum class ActivityType {
    BORROWING,  // Meminjam buku
    REVIEWING   // Mengulas buku
}

@Composable
fun ActivityScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToBookshelf: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    // Define colors
    val DarkGreen = Color(0xFF0C6B00)
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

    // Sample comments
    val comment1 = Comment(1, "Ahmad", "Bukunya bagus!", "5 menit lalu")
    val comment2 = Comment(2, "Dinda", "Setuju, alurnya menarik", "3 menit lalu")

    // Sample data with both activity types - matching exactly the design
    val activities = remember {
        mutableStateListOf(
            ActivityItem(
                id = 1,
                userName = "Rahmialz",
                userImage = R.drawable.profile_picture,
                userInitial = "",
                timeAgo = "1 jam lalu",
                activityType = ActivityType.REVIEWING,
                bookTitle = "Laskar Pelangi",
                bookAuthor = "Andrea Hirata",
                bookCover = R.drawable.laskar_pelangi,
                bookRating = 5.0f,
                reviewText = "Buku pertama yang aku baca tahun ini. Seru dan banyak plot twist banget!",
                likes = 24,
                comments = mutableStateListOf(
                    Comment(1, "Lutfi", "Setuju banget! Bukunya keren", "30 menit lalu"),
                    Comment(2, "Nana", "Pengen baca juga nih", "15 menit lalu")
                )
            ),
            ActivityItem(
                id = 2,
                userName = "Fahriyan",
                userImage = null,
                userInitial = "F",
                timeAgo = "1 jam lalu",
                activityType = ActivityType.BORROWING,
                bookTitle = "Laut Bercerita",
                bookAuthor = "Leila S. Chudori",
                bookCover = R.drawable.laut_bercerita,
                bookRating = 4.0f,
                likes = 13,
                comments = mutableStateListOf(comment1)
            ),
            ActivityItem(
                id = 3,
                userName = "Assyifa23",
                userImage = null,
                userInitial = "A",
                timeAgo = "2 jam lalu",
                activityType = ActivityType.BORROWING,
                bookTitle = "The Midnight Library",
                bookAuthor = "Matt Haig",
                bookCover = R.drawable.midnight_library,
                bookRating = 4.0f,
                likes = 9,
                comments = mutableStateListOf()
            )
        )
    }

    // State for tracking selected navigation item
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
                            painter = painterResource(id = R.drawable.baseline_more_time_24),
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = DarkGreen,
                            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(32.dp))

                        // Title
                        Text(
                            text = "Aktivitas",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = interFontFamily,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.width(32.dp))
                    }
                }

                // Activity List
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = if (activities.isEmpty())
                        androidx.compose.foundation.layout.PaddingValues(16.dp) else
                        androidx.compose.foundation.layout.PaddingValues(vertical = 8.dp)
                ) {
                    if (activities.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Belum ada aktivitas",
                                    fontFamily = interFontFamily,
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    } else {
                        items(activities) { activity ->
                            ActivityItemCard(
                                activity = activity,
                                interFontFamily = interFontFamily,
                                highlightColor = DarkGreen,
                                starColor = yellow,
                                onLikeClick = {
                                    // Find the activity and update its likes count
                                    val index = activities.indexOfFirst { it.id == activity.id }
                                    if (index != -1) {
                                        val updatedActivity = activities[index].copy(
                                            likes = activities[index].likes + 1
                                        )
                                        activities[index] = updatedActivity
                                    }
                                },
                                onCommentAdd = { comment ->
                                    // Find the activity and add the new comment
                                    val index = activities.indexOfFirst { it.id == activity.id }
                                    if (index != -1) {
                                        activities[index].comments.add(comment)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityItemCard(
    activity: ActivityItem,
    interFontFamily: FontFamily,
    highlightColor: Color,
    starColor: Color,
    onLikeClick: () -> Unit,
    onCommentAdd: (Comment) -> Unit
) {
    var isLiked by remember { mutableStateOf(false) }
    var showCommentDialog by remember { mutableStateOf(false) }
    var commentText by remember { mutableStateOf("") }

    // Animate the like button color
    val likeButtonColor by animateColorAsState(
        if (isLiked) highlightColor else Color.Gray,
        label = "likeButtonColor"
    )

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
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFontFamily
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = when(activity.activityType) {
                            ActivityType.BORROWING -> "Meminjam buku"
                            ActivityType.REVIEWING -> "Memberi ulasan"
                        },
                        fontSize = 10.sp,
                        fontFamily = interFontFamily,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Time ago
                Text(
                    text = activity.timeAgo,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = interFontFamily
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Book info row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
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
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = interFontFamily
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = activity.bookAuthor,
                        fontSize = 10.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = interFontFamily
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Rating stars
                    Row {
                        repeat(5) { index ->
                            val filled = index < activity.bookRating
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.baseline_star_24
                                ),
                                contentDescription = null,
                                tint = if (filled) starColor else Color.LightGray,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    // Show review text if this is a review activity
                    if (activity.activityType == ActivityType.REVIEWING && activity.reviewText.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = activity.reviewText,
                            fontSize = 12.sp,
                            fontFamily = interFontFamily,
                            color = Color.DarkGray,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }



            // Action buttons and counters row - exactly matching design layout
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                // Comment icon and count - clicking opens comment dialog
                IconButton(
                    onClick = { showCommentDialog = true },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chat_bubble_outline_24),
                        contentDescription = "Comment",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "${activity.comments.size}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = interFontFamily,
                    modifier = Modifier.padding(top = 10.dp, end = 16.dp)
                )

                // Like icon and count
                IconButton(
                    onClick = {
                        if (!isLiked) {
                            isLiked = true
                            onLikeClick()
                        }
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_thumb_up_alt_24),
                        contentDescription = "Like",
                        tint = likeButtonColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Text(
                    text = "${activity.likes}",
                    fontSize = 12.sp,
                    color = if (isLiked) highlightColor else Color.Gray,
                    fontFamily = interFontFamily,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }

    // Comment dialog
    if (showCommentDialog) {
        AlertDialog(
            onDismissRequest = { showCommentDialog = false },
            title = {
                Text(
                    text = "Tambah Komentar",
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    label = { Text("Tulis komentar Anda", fontFamily = interFontFamily) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = highlightColor,
                        cursorColor = highlightColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (commentText.isNotEmpty()) {
                            val newComment = Comment(
                                id = activity.comments.size + 1,
                                userName = "Anda",
                                text = commentText,
                                timestamp = "Baru saja"
                            )
                            onCommentAdd(newComment)
                            commentText = ""
                            showCommentDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = highlightColor
                    )
                ) {
                    Text("Kirim", fontFamily = interFontFamily)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showCommentDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray
                    )
                ) {
                    Text("Batal", fontFamily = interFontFamily)
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ActivityScreenPreview() {
    ActivityScreen()
}