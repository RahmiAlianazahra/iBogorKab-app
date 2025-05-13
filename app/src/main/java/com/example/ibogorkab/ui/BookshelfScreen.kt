package com.example.ibogorkab.ui

import android.graphics.Paint.Align
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
import androidx.compose.foundation.shape.RoundedCornerShape
// Add these imports for the pager functionality
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
// Add these imports for coroutine scope and LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
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
import com.example.ibogorkab.ui.components.MoreOptionsMenu
import com.example.ibogorkab.ui.components.MoreOptionsMenuBorowwed
import com.example.ibogorkab.ui.components.MoreOptionsMenuQueue
import com.example.ibogorkab.ui.theme.PaleGreen
// Add this import for launching coroutines
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookshelfScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToActivity: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToBookDetail: (Int) -> Unit = {},
    onSearchPressed: () -> Unit = {}
) {
    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)
    val White = Color.White
    val Gray = Color(0xFF979797)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Tab selection state
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Pinjaman", "Antrian", "Riwayat")

    // Explicitly define the coroutineScope - fixes first error
    val coroutineScope = rememberCoroutineScope()

    // Correctly initialize the pagerState - fixes second and third errors
    val pagerState = rememberPagerState(initialPage = selectedTabIndex) { tabs.size }

    // Keep tab selection and pager in sync
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    // Bottom navigation selection state
    var selectedNavItem by remember { mutableIntStateOf(2) } // Rak Buku is selected by default

    // Sample books data for each tab
    val borrowedBooks = remember {
        listOf(
            BookModel(
                id = 1,
                title = "Edensor",
                author = "Andrea Hirata",
                coverResId = R.drawable.edensor,
                progress = 18,
                remainingDays = 3
            ),
            BookModel(
                id = 2,
                title = "Laskar Pelangi",
                author = "Andrea Hirata",
                coverResId = R.drawable.laskar_pelangi,
                progress = 74,
                remainingDays = 1
            )
        )
    }

    val queueBooks = remember {
        listOf(
            BookModel(
                id = 3,
                title = "Aroma Karsa",
                author = "Dee Lestari",
                coverResId = R.drawable.aroma_karsa,
                queuePosition = 5,
                totalQueue = 10
            )
        )
    }

    val historyBooks = remember {
        listOf(
            BookModel(
                id = 4,
                title = "Parable",
                author = "Brian Khrisna",
                coverResId = R.drawable.parable,
                borrowDate = "20 Februari 2025"
            )
        )
    }

    Scaffold(
        topBar = {
            // Updated header with green background and rounded corners
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = DarkGreen,
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(32.dp))  // Space for balance since we don't have a back button

                    // Title
                    Text(
                        text = "Rak Buku",
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFontFamily,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )

                    // Search icon
                    IconButton(
                        onClick = onSearchPressed,
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = White
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = White
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
                        indicatorColor = White
                    )
                )

                NavigationBarItem(
                    selected = selectedNavItem == 1,
                    onClick = {
                        selectedNavItem = 1
                        onNavigateToActivity()
                    },
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
                        indicatorColor = White
                    )
                )

                NavigationBarItem(
                    selected = selectedNavItem == 2,
                    onClick = { selectedNavItem = 2 },
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
                        indicatorColor = White
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
                        indicatorColor = White
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PaleGreen)
        ) {
            // Tab selector - allows clicking tabs
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = PaleGreen,
                contentColor = DarkGreen,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        height = 3.dp,
                        color = DarkGreen
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            // When tab is clicked, animate the pager
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = title,
                                fontFamily = interFontFamily,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selectedContentColor = DarkGreen,
                        unselectedContentColor = Gray
                    )
                }
            }

            // Content with horizontal pager for swipe gestures
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { pageIndex ->
                // Display content based on page index
                when (pageIndex) {
                    0 -> BookList(
                        books = borrowedBooks,
                        interFontFamily = interFontFamily,
                        listType = BookListType.BORROWED,
                        onBookClick = onNavigateToBookDetail
                    )
                    1 -> BookList(
                        books = queueBooks,
                        interFontFamily = interFontFamily,
                        listType = BookListType.QUEUE,
                        onBookClick = onNavigateToBookDetail
                    )
                    2 -> BookList(
                        books = historyBooks,
                        interFontFamily = interFontFamily,
                        listType = BookListType.HISTORY,
                        onBookClick = onNavigateToBookDetail
                    )
                }
            }
        }
    }
}

enum class BookListType {
    BORROWED, QUEUE, HISTORY
}

@Composable
fun BookList(
    books: List<BookModel>,
    interFontFamily: FontFamily,
    listType: BookListType,
    onBookClick: (Int) -> Unit
) {
    if (books.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (listType) {
                    BookListType.BORROWED -> "Tidak ada buku yang dipinjam"
                    BookListType.QUEUE -> "Tidak ada buku dalam antrian"
                    BookListType.HISTORY -> "Tidak ada riwayat peminjaman"
                },
                fontFamily = interFontFamily,
                color = Color.Gray
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(books) { book ->
                when (listType) {
                    BookListType.BORROWED -> BorrowedBookCard(
                        book = book,
                        interFontFamily = interFontFamily,
                        onClick = { onBookClick(book.id) }
                    )
                    BookListType.QUEUE -> QueueBookCard(
                        book = book,
                        interFontFamily = interFontFamily,
                        onClick = { onBookClick(book.id) }
                    )
                    BookListType.HISTORY -> HistoryBookCard(
                        book = book,
                        interFontFamily = interFontFamily,
                        onClick = { onBookClick(book.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun BorrowedBookCard(
    book: BookModel,
    interFontFamily: FontFamily,
    onClick: () -> Unit
) {
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Book cover
            Image(
                painter = painterResource(id = book.coverResId),
                contentDescription = book.title,
                modifier = Modifier
                    .width(70.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Book details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = book.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = book.author,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = interFontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Progress bar with percentage
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(10.dp)
                            .background(Color.LightGray, RoundedCornerShape(5.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(book.progress / 100f)
                                .background(LightGreen, RoundedCornerShape(5.dp))
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                            .padding(start = 4.dp)
                    ) {
                        Text(
                            text = "${book.progress}%",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontFamily = interFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Remaining time
                Text(
                    text = "Sisa waktu: ${book.remainingDays} hari",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = interFontFamily
                )
            }
            Box(modifier = Modifier.align(Alignment.Top)) {
                MoreOptionsMenuBorowwed(
                    onShare = {
                    },
                    onRecommend = {
                    },
                    onReturn = {
                    },
                    fontFamily = interFontFamily
                )
            }
        }
    }
}

@Composable
fun QueueBookCard(
    book: BookModel,
    interFontFamily: FontFamily,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Book cover
            Image(
                painter = painterResource(id = book.coverResId),
                contentDescription = book.title,
                modifier = Modifier
                    .width(70.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Book details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = book.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = book.author,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = interFontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Queue position
                Text(
                    text = "Antrean: ${book.queuePosition}/${book.totalQueue}",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = interFontFamily
                )
            }


            Box(modifier = Modifier.align(Alignment.Top)) {
                MoreOptionsMenuQueue(
                    onShare = {
                    },
                    onRecommend = {
                    },
                    onLeave = {
                    },
                    fontFamily = interFontFamily
                )
            }
        }
    }
}

@Composable
fun HistoryBookCard(
    book: BookModel,
    interFontFamily: FontFamily,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Book cover
            Image(
                painter = painterResource(id = book.coverResId),
                contentDescription = book.title,
                modifier = Modifier
                    .width(70.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Book details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = book.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = book.author,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = interFontFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Borrow date
                Text(
                    text = "Tanggal pinjam: ${book.borrowDate}",
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontFamily = interFontFamily
                )
            }


            // MoreOptionsMenu positioned at the end of the row
            Box(modifier = Modifier.align(Alignment.Top)) {
                MoreOptionsMenu(
                    onShare = {
                    },
                    onRecommend = {
                    },
                    fontFamily = interFontFamily
                )
            }
        }
    }
}

data class BookModel(
    val id: Int,
    val title: String,
    val author: String,
    val coverResId: Int,
    val progress: Int = 0,
    val remainingDays: Int = 0,
    val queuePosition: Int = 0,
    val totalQueue: Int = 0,
    val borrowDate: String = ""
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookshelfScreenPreview() {
    BookshelfScreen()
}