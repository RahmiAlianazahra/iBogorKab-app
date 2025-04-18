package com.example.ibogorkab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun KoleksiBukuScreen(
    onBackPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onFilterPressed: () -> Unit = {},
    onBookClicked: (BookData) -> Unit = {}
) {
    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)
    val white = Color.White
    val gray = Color(0xFF979797)
    val yellow = Color(0xFFFFD700)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Sample data for book collection
    val bookCollection = listOf(
        BookData(
            1,
            "Atomic Habits",
            "James Clear",
            R.drawable.atomic_habits,
            likes = 5,
            queue = 20
        ),
        BookData(
            2,
            "Logika Asa",
            "Valerie Patkar",
            R.drawable.logika_asa,
            likes = 4,
            queue = 5
        ),
        BookData(
            3,
            "Pergi",
            "Tere Liye",
            R.drawable.pergi,
            likes = 5,
            queue = 35
        ),
        BookData(
            4,
            "Untuk Negeriku",
            "Mohammad Hatta",
            R.drawable.untuk_negeriku,
            likes = 5,
            queue = 0
        ),
        BookData(
            5,
            "Python Basic",
            "David Amos",
            R.drawable.python_basic,
            likes = 5,
            queue = 10
        ),
        BookData(
            6,
            "The Alchemist",
            "Paulo Coelho",
            R.drawable.the_alchemist,
            likes = 5,
            queue = 10
        ),
        BookData(
            7,
            "Laut Bercerita",
            "Leila S. Chudori",
            R.drawable.laut_bercerita,
            likes = 5,
            queue = 10
        ),
        BookData(
            8,
            "Aroma Karsa",
            "Dee Lestari",
            R.drawable.aroma_karsa,
            likes = 5,
            queue = 10
        )
    )

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PaleGreen)
        ) {
            // Header with back button, title, and search button
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
                        text = "Koleksi Buku",
                        color = white,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFontFamily,
                        modifier = Modifier.weight(1f)
                    )

                    // Search Icon
                    IconButton(
                        onClick = onSearchPressed,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = white
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Filter Icon
                    IconButton(
                        onClick = onFilterPressed,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_filter_list_24),
                            contentDescription = "Filter",
                            tint = white
                        )
                    }
                }
            }

            // Search Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(white, RoundedCornerShape(16.dp))
                    .clickable { onSearchPressed() }
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(
                            "Temukan buku favoritmu",
                            fontFamily = interFontFamily,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = white,
                        focusedContainerColor = white
                    ),
                    enabled = false,
                    singleLine = true
                )
            }

            // Book Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(bookCollection) { book ->
                    BookCardGrid(
                        book = book,
                        interFontFamily = interFontFamily,
                        onBookClicked = { onBookClicked(book) }
                    )
                }
            }
        }
    }
}

@Composable
fun BookCardGrid(
    book: BookData,
    interFontFamily: FontFamily,
    onBookClicked: () -> Unit = {}
) {
    val gray = Color(0xFF979797)
    val yellow = Color(0xFFFFD700)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onBookClicked() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Book cover
            Image(
                painter = painterResource(id = book.coverResId),
                contentDescription = book.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Book title
            Text(
                text = book.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = interFontFamily,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))

            // Author
            Text(
                text = book.author,
                fontSize = 12.sp,
                color = gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = interFontFamily,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Queue and likes
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
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

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    painter = painterResource(id = R.drawable.baseline_star_24),
                    contentDescription = "Likes",
                    tint = yellow,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = " ${book.likes}",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontFamily = interFontFamily
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(id = R.drawable.baseline_more_vert_24),
                    contentDescription = "More options",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun KoleksiBukuScreenPreview() {
    KoleksiBukuScreen()
}