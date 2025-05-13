package com.example.ibogorkab.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.ibogorkab.ui.components.MoreOptionsMenu
import com.example.ibogorkab.ui.theme.DarkGreen
import com.example.ibogorkab.ui.theme.PaleGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


enum class BookInteractionState {
    INITIAL,        // Initial state - "Pinjam" button
    JOINED,         // After joining ePustaka - "Unduh" button
    DOWNLOADING,    // During download - show progress
    READY           // Ready to read - "Baca" button
}

@Composable
fun BookDetailScreen(
    bookId: Int = 1,
    onBackPressed: () -> Unit = {},
    onBorrowBook: () -> Unit = {},
    onReadBook: () -> Unit = {},
    onNavigateToReview: (Int, String, String) -> Unit = { _, _, _ -> }
) {


    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)
    val white = Color.White
    val black = Color.Black
    val gray = Color(0xFF979797)
    val yellow = Color(0xFFFFD700)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Sample book data - in a real app, this would be fetched based on bookId
    val book = BookDetail(
        id = 1,
        title = "Edensor",
        author = "Andrea Hirata",
        coverResId = R.drawable.edensor,
        category = "Novel, Fiksi",
        publisher = "PT Gramedia Pustaka Utama",
        year = "2018",
        isbn = "978-602-0822-12-9",
        fileSize = "3.5 MB",
        queues = 0,
        reads = 58,
        copies = 20,
        rating = 5,
        synopsis = "Edensor ini mengisahkan kegigihan dua orang anak melayu Belitong yang ingin mewujudkan mimpi-mimpi mereka sejak kecil meraih puncak sourbone Paris. Tamat SMA Ikal dan Arai merantau ke Pulau jawa. Di sana mereka kuliah sambil bekerja demi mencukupi kebutuhan sehari-hari.",
        showFullSynopsis = false
    )

    // Expanded synopsis state
    var showFullSynopsis by remember { mutableStateOf(false) }

    // State for join ePustaka dialog - using rememberSaveable to persist through recompositions
    var showJoinDialog by rememberSaveable { mutableStateOf(false) }

    // State to track the book interaction state
    var interactionState by rememberSaveable { mutableStateOf(BookInteractionState.INITIAL) }

    // Download progress state
    var downloadProgress by rememberSaveable { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = downloadProgress,
        label = "downloadProgressAnimation"
    )

    // Coroutine scope for simulating download
    val coroutineScope = rememberCoroutineScope()


    // Sample similar books
    val similarBooks = listOf(
        SimilarBook(1, "The Kite Runner", "Khaled Hosseini", R.drawable.the_kite_runner, 5, 10),
        SimilarBook(2, "Pergi", "Tere Liye", R.drawable.laskar_pelangi, 5, 10),
        SimilarBook(3, "Tentang Kamu", "Tere Liye", R.drawable.tentang_kamu, 5, 10),
        SimilarBook(4, "Aroma Karsa", "Dee Lestari", R.drawable.aroma_karsa, 5, 10)
    )

    // Sample review
    val review = Review(
        reviewerName = "Fairuza Cahya",
        reviewerImage = R.drawable.profile_picture,
        rating = 5,
        comment = "Tamat dalam 3 hari. Recommended banget! Alurnya bagus, saya jadi pengen baca lagi."
    )

    // Function to simulate download process
    fun simulateDownload() {
        interactionState = BookInteractionState.DOWNLOADING
        downloadProgress = 0f

        coroutineScope.launch {
            // Simulate a download that takes 3 seconds
            repeat(100) {
                delay(30)  // 3000ms total / 100 steps = 30ms per step
                downloadProgress = (it + 1) / 100f
            }
            // After download completes
            interactionState = BookInteractionState.READY
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PaleGreen)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = DarkGreen,
                            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                        )
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Back button
                        IconButton(
                            onClick = onBackPressed,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = white
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Title
                        Text(
                            text = "Detail Buku",
                            color = white,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = interFontFamily,
                            modifier = Modifier.weight(1f)
                        )

                        // MoreOptionsMenu with white icon
                        MoreOptionsMenu(
                            onShare = {
                                /* Handle share action */
                            },
                            onRecommend = {
                                /* Handle recommend action */
                            },
                            fontFamily = interFontFamily,
                            tint = Color.White
                        )
                    }
                }

                // Book Cover and Basic Info
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Book Cover
                        Image(
                            painter = painterResource(id = book.coverResId),
                            contentDescription = "Book Cover",
                            modifier = Modifier
                                .height(190.dp)
                                .width(120.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Book Title
                        Text(
                            text = book.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontFamily = interFontFamily
                        )

                        // Author
                        Text(
                            text = book.author,
                            fontSize = 14.sp,
                            color = gray,
                            textAlign = TextAlign.Center,
                            fontFamily = interFontFamily
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Availability and Rating
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            // Copies available
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.queue_logo),
                                        contentDescription = "Queues",
                                        tint = DarkGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = " ${book.queues}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = interFontFamily
                                    )
                                }
                            }

                            // Rating
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.reader_icon),
                                        contentDescription = "Reads",
                                        tint = DarkGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = " ${book.reads}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = interFontFamily
                                    )
                                }
                            }
                        }
                    }
                }

                // Book Info Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    // File Size, Copies, Rating
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            // File Size
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Ukuran file",
                                    fontSize = 12.sp,
                                    color = gray,
                                    fontFamily = interFontFamily
                                )
                                Text(
                                    text = book.fileSize,
                                    fontSize = 14.sp,
                                    color = DarkGreen,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = interFontFamily
                                )
                            }

                            Divider(
                                modifier = Modifier
                                    .height(36.dp)
                                    .width(1.dp),
                                color = Color.LightGray
                            )

                            // Copies
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Jumlah Copy",
                                    fontSize = 12.sp,
                                    color = gray,
                                    fontFamily = interFontFamily
                                )
                                Text(
                                    text = "${book.copies}",
                                    fontSize = 14.sp,
                                    color = DarkGreen,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = interFontFamily
                                )
                            }

                            Divider(
                                modifier = Modifier
                                    .height(36.dp)
                                    .width(1.dp),
                                color = Color.LightGray
                            )

                            // Rating
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Rating",
                                    fontSize = 12.sp,
                                    color = gray,
                                    fontFamily = interFontFamily
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_star_24),
                                        contentDescription = "Rating",
                                        tint = yellow,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = " ${book.rating}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = interFontFamily
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Book Metadata
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Category
                            Text(
                                text = "Kategori",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkGreen,
                                fontFamily = interFontFamily
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = book.category,
                                fontSize = 14.sp,
                                fontFamily = interFontFamily
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Publisher
                            Text(
                                text = "Penerbit",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkGreen,
                                fontFamily = interFontFamily
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = book.publisher,
                                fontSize = 14.sp,
                                fontFamily = interFontFamily
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Year
                            Text(
                                text = "Tahun Terbit",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkGreen,
                                fontFamily = interFontFamily
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = book.year,
                                fontSize = 14.sp,
                                fontFamily = interFontFamily
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // ISBN
                            Text(
                                text = "EISBN",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkGreen,
                                fontFamily = interFontFamily
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = book.isbn,
                                fontSize = 14.sp,
                                fontFamily = interFontFamily
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Synopsis
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Sinopsis Buku",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFontFamily
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = book.synopsis,
                                fontSize = 12.sp,
                                fontFamily = interFontFamily,
                                maxLines = if (showFullSynopsis) Int.MAX_VALUE else 4,
                                overflow = if (showFullSynopsis) TextOverflow.Visible else TextOverflow.Ellipsis
                            )

                            TextButton(
                                onClick = { showFullSynopsis = !showFullSynopsis },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text(
                                    text = if (showFullSynopsis) "Sembunyikan" else "Selengkapnya",
                                    color = DarkGreen,
                                    fontSize = 10.sp,
                                    fontFamily = interFontFamily
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Reviews
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Ulasan",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFontFamily
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Reviewer info
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Reviewer profile picture
                                Image(
                                    painter = painterResource(id = review.reviewerImage),
                                    contentDescription = "Reviewer",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .background(Color.LightGray)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = review.reviewerName,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = interFontFamily
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                // Rating stars
                                Row {
                                    repeat(5) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_star_24),
                                            contentDescription = "Star",
                                            tint = yellow,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Review text
                            Text(
                                text = review.comment,
                                fontSize = 12.sp,
                                fontFamily = interFontFamily
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextButton(onClick = { /* Show more reviews */ }) {
                                    Text(
                                        text = "Lainnya",
                                        color = DarkGreen,
                                        fontFamily = interFontFamily,
                                        fontSize = 10.sp
                                    )
                                }

                                TextButton(onClick = {
                                    // Navigate to review screen passing book info
                                    onNavigateToReview(book.id, book.title, book.author)
                                }) {
                                    Text(
                                        text = "Beri ulasan",
                                        color = DarkGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = interFontFamily,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Similar Books Section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(PaleGreen)
                            .padding(vertical = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                        ) {
                            Text(
                                text = "Buku Serupa",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFontFamily,
                                color = Color.Black,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            // Horizontal scrollable list of similar books
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(end = 16.dp)
                            ) {
                                items(similarBooks) { similarBook ->
                                    SimilarBookCard(
                                        book = similarBook,
                                        interFontFamily = interFontFamily,
                                        onClick = { /* Navigate to book details */ }
                                    )
                                }
                            }
                        }
                    }

                    // Add space for the borrow button
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }

            // Action Button (Fixed at bottom)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                when (interactionState) {
                    BookInteractionState.INITIAL -> {
                        // "Pinjam" button
                        Button(
                            onClick = { showJoinDialog = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkGreen,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Pinjam",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFontFamily
                            )
                        }
                    }
                    BookInteractionState.JOINED -> {
                        // "Unduh" button
                        Button(
                            onClick = { simulateDownload() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkGreen,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Unduh",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFontFamily
                            )
                        }
                    }
                    BookInteractionState.DOWNLOADING -> {
                        // Download progress
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = white,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Mengunduh...",
                                fontSize = 12.sp,
                                fontFamily = interFontFamily,
                                color = DarkGreen
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            LinearProgressIndicator(
                                progress = { animatedProgress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp),
                                color = DarkGreen,
                                trackColor = PaleGreen
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "${(animatedProgress * 100).toInt()}%",
                                fontSize = 12.sp,
                                fontFamily = interFontFamily,
                                color = gray
                            )
                        }
                    }
                    BookInteractionState.READY -> {
                        // "Baca" button
                        Button(
                            onClick = { onReadBook() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkGreen,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Baca",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFontFamily
                            )
                        }
                    }
                }
            }
        }

        // Join ePustaka Dialog - Placed outside the main content but inside the parent Box
        JoinEPustakaDialog(
            visible = showJoinDialog,
            onDismiss = { showJoinDialog = false },
            onJoin = {
                interactionState = BookInteractionState.JOINED
                showJoinDialog = false
            }
        )
    }
}

@Composable
fun SimilarBookCard(
    book: SimilarBook,
    interFontFamily: FontFamily,
    onClick: () -> Unit
) {
    val yellow = Color(0xFFFFD700)
    val gray = Color(0xFF979797)

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

        // Queue and likes row with more options
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Queue
                Icon(
                    painter = painterResource(id = R.drawable.queue_logo),
                    contentDescription = "Queue",
                    tint = gray,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = " ${book.queue}",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontFamily = interFontFamily
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Likes/Rating
                Icon(
                    painter = painterResource(id = R.drawable.baseline_star_24),
                    contentDescription = "Rating",
                    tint = yellow,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = " ${book.rating}",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontFamily = interFontFamily
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            // For compact layout, use a smaller icon with a custom dropdown
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
                            fontSize = 14.sp
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
                            fontSize = 14.sp
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

// Data classes
data class BookDetail(
    val id: Int,
    val title: String,
    val author: String,
    val coverResId: Int,
    val category: String,
    val publisher: String,
    val year: String,
    val isbn: String,
    val fileSize: String,
    val queues: Int,
    val reads: Int,
    val copies: Int,
    val rating: Int,
    val synopsis: String,
    val showFullSynopsis: Boolean = false
)

data class SimilarBook(
    val id: Int,
    val title: String,
    val author: String,
    val coverResId: Int,
    val rating: Int,
    val queue: Int
)

data class Review(
    val reviewerName: String,
    val reviewerImage: Int,
    val rating: Int,
    val comment: String
)

@Preview(showBackground = true)
@Composable
fun BookDetailScreenPreview() {
    BookDetailScreen()
}