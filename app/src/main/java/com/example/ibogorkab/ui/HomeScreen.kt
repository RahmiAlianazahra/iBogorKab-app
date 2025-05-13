package com.example.ibogorkab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R
import com.example.ibogorkab.ui.theme.DarkGreen
import com.example.ibogorkab.ui.theme.PaleGreen

@Composable
fun HomeScreen(
    onNavigateToCollection: () -> Unit = {},
    onNavigateToEPustaka: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToSearch: () -> Unit = {},
    onBookClicked: (Int) -> Unit = {},
    onNavigateToActivity: () -> Unit = {},
    onNavigateToBookshelf: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},

    ) {
    // Define colors
    val DarkGreen = Color(0xFF0C6B00)
    val LightGreen = Color(0xFF50AD42)
    val gray = Color(0xFF979797)
    val yellow = Color(0xFFFFEB3B)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Sample data for books
    val continuedReadingBook = BookData(
        id = 1,
        title = "Laskar Pelangi",
        author = "Andrea Hirata",
        coverResId = R.drawable.laskar_pelangi,
        progress = 74
    )

    val recommendedBooks = listOf(
        BookData(
            1,
            "Edensor",
            "Andrea Hirata",
            R.drawable.edensor,
            likes = 5,
            queue = 0
        ),
        BookData(
            2,
            "The Kite Runner",
            "Khaled Hosseini",
            R.drawable.the_kite_runner,
            likes = 4,
            queue = 15
        ),
        BookData(
            3,
            "Tentang Kamu",
            "Tere Liye",
            R.drawable.tentang_kamu,
            likes = 4,
            queue = 0
        ),
        BookData(
            4,
            "Aroma Karsa",
            "Dee Lestari",
            R.drawable.aroma_karsa,
            likes = 4,
            queue = 0
        ),
        BookData(
            5,
            "Bumi Manusia",
            "Pramoedya Ananta Toer",
            R.drawable.bumi_manusia,
            likes = 5,
            queue = 27
        )

    )

    val newAndPopularBooks = listOf(
        BookData(
            6,
            "Educated",
            "Tara Westover",
            R.drawable.educated,
            likes = 5,
            queue = 20
        ),
        BookData(
            7,
            "The Alchemist",
            "Paulo Coelho",
            R.drawable.the_alchemist,
            likes = 4,
            queue = 6
        ),
        BookData(
            8,
            "Soekarno",
            "Taufik Adi Susilo",
            R.drawable.soekarno,
            likes = 5,
            queue = 20
        ),
        BookData(
            9,
            "Midnight Library",
            "Matt Haig",
            R.drawable.midnight_library,
            likes = 4,
            queue = 10
        ),
        BookData(
            10,
            "Filosofi Teras",
            "Henry Manampiring",
            R.drawable.filosofi_teras,
            likes = 5,
            queue = 15
        )
    )

    // Selected navigation item state
    var selectedNavItem by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                NavigationBarItem(
                    selected = selectedNavItem == 0,
                    onClick = { selectedNavItem = 0 },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Beranda"
                        )
                    },
                    label = { Text(
                        "Beranda",
                        fontFamily = interFontFamily) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkGreen,
                        selectedTextColor = DarkGreen,
                        indicatorColor = Color.White
                    )
                )

                NavigationBarItem(
                    selected = selectedNavItem == 1,
                    onClick = { selectedNavItem = 1
                        onNavigateToActivity() },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_more_time_24),
                            contentDescription = "Aktivitas"
                        )
                    },
                    label = { Text(
                        "Aktivitas",
                        fontFamily = interFontFamily) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkGreen,
                        selectedTextColor = DarkGreen,
                        indicatorColor = Color.White
                    )
                )

                NavigationBarItem(
                    selected = selectedNavItem == 2,
                    onClick = { selectedNavItem = 2
                        onNavigateToBookshelf() },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = "Rak Buku"
                        )
                    },
                    label = { Text(
                        "Rak Buku",
                        fontFamily = interFontFamily) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkGreen,
                        selectedTextColor = DarkGreen,
                        indicatorColor = Color.White
                    )
                )

                NavigationBarItem(
                    selected = selectedNavItem == 3,
                    onClick = { selectedNavItem = 3
                        onNavigateToProfile()},
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Profil"
                        )
                    },
                    label = { Text(
                        "Profil",
                        fontFamily = interFontFamily) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkGreen,
                        selectedTextColor = DarkGreen,
                        indicatorColor = Color.White
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    color = PaleGreen
                )
                .verticalScroll(rememberScrollState())
        ) {
            // Green header with gradient background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                DarkGreen,
                                DarkGreen,
                                LightGreen
                            )
                        ),
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // User greeting and notification
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Profile picture
                            Image(
                                painter = painterResource(id = R.drawable.profile_picture),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(35.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // Greeting text
                            Text(
                                text = "Halo, Rahmi!",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFontFamily
                            )
                        }

                        // Notification icon
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_notifications_24),
                            contentDescription = "Notifications",
                            tint = Color.White,
                            modifier = Modifier
                                .size(22.dp)
                                .clickable { onNavigateToNotifications() }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Reading suggestion text and illustration - with overlapping layout
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                    ) {
                        // Text on the left side
                        Text(
                            text = "Mau baca buku apa hari ini?",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = interFontFamily,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(end = 100.dp) // Give space for illustration
                        )

                        // Reading illustration on the right - zoomed in
                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .height(150.dp)
                                .align(Alignment.CenterEnd)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.reading),
                                contentDescription = "Reading Illustration",
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop // Zoomed in effect
                            )
                        }
                    }
                    // Search box - with WHITE background
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { onNavigateToSearch() }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = gray,
                                modifier = Modifier.size(24.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Temukan buku favoritmu",
                                color = gray,
                                fontSize = 12.sp,
                                fontFamily = interFontFamily
                            )
                        }
                    }
                }
            }

            // Content area with white background
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                // Buttons for Koleksi Buku and ePustaka
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { onNavigateToCollection() },
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = DarkGreen,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .weight(1f)
                            .height(40.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = DarkGreen
                        )
                    ) {
                        Text(
                            text = "Koleksi Buku",
                            fontFamily = interFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }

                    Button(
                        onClick = { onNavigateToEPustaka() },
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = DarkGreen,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .weight(1f)
                            .height(40.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = DarkGreen
                        )
                    ) {
                        Text(
                            text = "ePustaka",
                            fontFamily = interFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))

                // Continue Reading Section
                Text(
                    text = "Lanjutkan Membaca",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFontFamily
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Currently reading book card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Navigate to book details */ },
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Book cover
                        Image(
                            painter = painterResource(id = continuedReadingBook.coverResId),
                            contentDescription = continuedReadingBook.title,
                            modifier = Modifier
                                .width(70.dp)
                                .height(100.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        // Book details
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = continuedReadingBook.title,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFontFamily
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = continuedReadingBook.author,
                                fontSize = 10.sp,
                                color = gray,
                                fontFamily = interFontFamily
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Reading progress
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp)
                                ) {
                                    LinearProgressIndicator(
                                        progress = continuedReadingBook.progress / 100f,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp)
                                            .clip(RoundedCornerShape(4.dp)),
                                        color = LightGreen,
                                        trackColor = Color.LightGray
                                    )
                                }

                                Text(
                                    text = "${continuedReadingBook.progress}%",
                                    fontSize = 10.sp,
                                    color = gray,
                                    fontFamily = interFontFamily
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Recommendations Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rekomendasi",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFontFamily
                    )

                    TextButton(onClick = { /* View all recommendations */ }) {
                        Text(
                            text = "Selengkapnya",
                            color = DarkGreen,
                            fontFamily = interFontFamily,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Horizontal scrolling book list for recommendations
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(recommendedBooks) { book ->
                        BookCard(
                            book = book,
                            interFontFamily = interFontFamily,
                            onClick = { onBookClicked(book.id) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // New & Popular Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Baru & Populer",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFontFamily
                    )

                    TextButton(onClick = { /* View all new & popular */ }) {
                        Text(
                            text = "Selengkapnya",
                            color = DarkGreen,
                            fontFamily = interFontFamily,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Horizontal scrolling book list for new & popular
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(newAndPopularBooks) { book ->
                        BookCard(
                            book = book,
                            interFontFamily = interFontFamily,
                            onClick = { onBookClicked(book.id) })
                    }
                }

                // Add some padding at the bottom
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun BookCard(
    book: BookData,
    interFontFamily: FontFamily,
    onClick: () -> Unit = {}
) {
    val gray = Color(0xFF979797)
    val yellow = Color(0xFFFFD700)

    Column(
        modifier = Modifier
            .width(125.dp)
            .clickable { onClick() }
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
    ) {
        // Book cover
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Image(
                painter = painterResource(id = book.coverResId),
                contentDescription = book.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Book title
        Text(
            text = book.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontFamily = interFontFamily
        )

        // Author
        Text(
            text = book.author,
            fontSize = 10.sp,
            color = gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontFamily = interFontFamily
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Queue and likes
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.queue_logo), // Replace with eye icon
                contentDescription = "Queue",
                tint = gray,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = " ${book.queue}",
                fontSize = 10.sp,
                color = Color.Black,
                fontFamily = interFontFamily
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(id = R.drawable.baseline_star_24), // Replace with star icon
                contentDescription = "Likes",
                tint = yellow,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = " ${book.likes}",
                fontSize = 10.sp,
                color = Color.Black,
                fontFamily = interFontFamily
            )

            Spacer(modifier = Modifier.weight(1f))

            var showMenu by remember { mutableStateOf(false) }
            // Small more options button
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_vert_24),
                contentDescription = "More options",
                tint = Color.Black,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { showMenu = true }
            )

            // Custom dropdown positioned at the end of the row
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                modifier = Modifier
                    .background(Color.White)
                    .width(180.dp)
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Bagikan",
                            fontFamily = interFontFamily,
                            fontSize = 12.sp
                        )
                    },
                    onClick = {
                        // Handle share action
                        showMenu = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Rekomendasikan",
                            fontFamily = interFontFamily,
                            fontSize = 12.sp
                        )
                    },
                    onClick = {
                        // Handle recommend action
                        showMenu = false
                    }
                )
            }
        }
    }
}


data class BookData(
    val id: Int,
    val title: String,
    val author: String,
    val coverResId: Int,
    val progress: Int = 0,
    val queue: Int = 0,
    val likes: Int = 0
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}