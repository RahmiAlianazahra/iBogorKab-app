package com.example.ibogorkab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import com.example.ibogorkab.ui.theme.PaleGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    filterOptions: FilterOptions? = null,
    onBackPressed: () -> Unit = {},
    onFilterPressed: () -> Unit = {},
    onBookClicked: (SearchResultBook) -> Unit = {}
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

    // State for search query
    var searchQuery by remember { mutableStateOf("") }

    // State for selected tab
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Tab items
    val tabs = listOf("Buku", "ePustaka", "Pengguna")

    // Sample books data
    val books = listOf(
        SearchResultBook(
            id = 1,
            title = "Hujan",
            author = "Tere Liye",
            coverResId = R.drawable.hujan,
            borrowCount = 0,
            rating = 5.0f
        ),
        SearchResultBook(
            id = 2,
            title = "Tentang Kamu",
            author = "Tere Liye",
            coverResId = R.drawable.tentang_kamu,
            borrowCount = 0,
            rating = 5.0f
        ),
        SearchResultBook(
            id = 3,
            title = "Negeri 5 Menara",
            author = "Ahmad Fuadi",
            coverResId = R.drawable.negeri_5_menara,
            borrowCount = 0,
            rating = 4.9f
        ),
        SearchResultBook(
            id = 4,
            title = "Dear Nathan",
            author = "Erisca Febriani",
            coverResId = R.drawable.dear_nathan,
            borrowCount = 0,
            rating = 4.5f
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PaleGreen)
    ) {
        // Search header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = DarkGreen
                )
            }

            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .height(50.dp),
                placeholder = {
                    Text(
                        "Temukan buku favoritmu",
                        fontSize = 12.sp,
                        fontFamily = interFontFamily
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = gray
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5)
                )
            )

            // Filter button
            IconButton(onClick = onFilterPressed) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_filter_list_24),
                    contentDescription = "Filter",
                    tint = DarkGreen
                )
            }
        }

        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = white,
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
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontFamily = interFontFamily,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    },
                    selectedContentColor = DarkGreen,
                    unselectedContentColor = gray
                )
            }
        }

        // Book grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(books) { book ->
                BookCard(
                    book = book,
                    interFontFamily = interFontFamily,
                    onBookClick = { onBookClicked(book) }
                )
            }
        }
    }
}

@Composable
fun BookCard(
    book: SearchResultBook,
    interFontFamily: FontFamily,
    onBookClick: () -> Unit = {}
) {
    val white = Color.White
    val gray = Color(0xFF979797)
    val yellow = Color(0xFFFFD700)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onBookClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = white),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Book cover
            Image(
                painter = painterResource(id = book.coverResId),
                contentDescription = book.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(185.dp)
                    .clip(RoundedCornerShape(5.dp)),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Book title
            Text(
                text = book.title,
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Author
            Text(
                text = book.author,
                fontFamily = interFontFamily,
                fontSize = 10.sp,
                color = gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Stats row (borrow count & rating)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Borrow count
                Icon(
                    painter = painterResource(id = R.drawable.queue_logo),
                    contentDescription = "Borrow Count",
                    tint = gray,
                    modifier = Modifier.size(14.dp)
                )

                Text(
                    text = " ${book.borrowCount}",
                    fontFamily = interFontFamily,
                    fontSize = 10.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Rating
                Icon(
                    painter = painterResource(id = R.drawable.baseline_star_24),
                    contentDescription = "Rating",
                    tint = yellow,
                    modifier = Modifier.size(14.dp)
                )

                Text(
                    text = " ${book.rating}",
                    fontFamily = interFontFamily,
                    fontSize = 10.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.weight(1f))

                // More options
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Options",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

data class SearchResultBook(
    val id: Int,
    val title: String,
    val author: String,
    val coverResId: Int,
    val borrowCount: Int,
    val rating: Float
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchResultScreenPreview() {
    // Sample filter options for preview
    val filterOptions = FilterOptions(
        genres = listOf("Novel"),
        authors = emptyList(),
        years = emptyList(),
        noQueueOnly = false
    )

    SearchResultScreen(filterOptions = filterOptions)
}