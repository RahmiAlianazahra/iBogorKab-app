package com.example.ibogorkab.ui

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R
import com.example.ibogorkab.ui.theme.PaleGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    onBackPressed: () -> Unit = {}
) {
    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val white = Color.White
    val black = Color.Black
    val gray = Color(0xFF979797)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Sample notifications data
    val notifications = listOf(
        NotificationItem(
            id = 1,
            icon = R.drawable.baseline_library_books_24,
            title = "Selamat Membaca!",
            message = "Anda berhasil meminjam buku \"Edensor\"",
            timeStamp = System.currentTimeMillis()
        ),
        NotificationItem(
            id = 2,
            icon = R.drawable.baseline_people_24,
            title = "Pengikut Baru",
            message = "Cahyani mulai mengikuti Anda.",
            timeStamp = System.currentTimeMillis() - 3600000
        ),
        NotificationItem(
            id = 3,
            icon = R.drawable.baseline_access_time_24,
            title = "Selesaikan Bukumu",
            message = "Masa pinjam \"Laskar Pelangi\" tersisa 1 hari lagi",
            timeStamp = System.currentTimeMillis() - 86400000
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notifikasi",
                        color = DarkGreen,
                        fontFamily = interFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = DarkGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = white
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PaleGreen)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(notifications) { notification ->
                    NotificationCard(
                        notification = notification,
                        interFontFamily = interFontFamily
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun NotificationCard(
    notification: NotificationItem,
    interFontFamily: FontFamily
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with green circle background
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8F5E9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = notification.icon),
                    contentDescription = null,
                    tint = Color(0xFF0D7600),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Notification content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = notification.title,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = notification.message,
                    fontFamily = interFontFamily,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

data class NotificationItem(
    val id: Int,
    val icon: Int,
    val title: String,
    val message: String,
    val timeStamp: Long
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationScreenPreview() {
    NotificationScreen()
}