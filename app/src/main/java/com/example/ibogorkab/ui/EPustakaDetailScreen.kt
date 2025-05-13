package com.example.ibogorkab.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R
import com.example.ibogorkab.ui.theme.DarkGreen
import com.example.ibogorkab.ui.theme.PaleGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EPustakaDetailScreen(
    ePustakaId: Int = 1,
    onBackPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onCollectionItemClicked: (EPustakaCollectionItem) -> Unit = {}
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
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // State to track if user has joined the ePustaka
    var hasJoined by remember { mutableStateOf(false) }

    // State to track grid scroll position
    val gridState = rememberLazyGridState()

    // Derive whether the profile card should be shown based on scroll position
    val showProfileCard by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex == 0 &&
                    (gridState.firstVisibleItemScrollOffset < 200) // Hide when scrolled past a threshold
        }
    }

    // ePustaka information (this would come from a repository or viewModel in a real app)
    val ePustaka = EPustakaDetailData(
        id = 1,
        name = "Radar Bogor",
        logoResId = R.drawable.radar_bogor,
        memberCount = 200,
        collectionCount = 145,
        isJoined = hasJoined
    )

    // Sample collection items
    val collectionItems = listOf(
        EPustakaCollectionItem(
            id = 1,
            title = "Radar Bogor",
            edition = "Edisi 15 April 2025",
            coverResId = R.drawable.radarbogor1,
            views = 10,
            rating = 5.0f
        ),
        EPustakaCollectionItem(
            id = 2,
            title = "Radar Bogor",
            edition = "Edisi 08 April 2025",
            coverResId = R.drawable.radarbogor2,
            views = 5,
            rating = 4.5f
        ),
        EPustakaCollectionItem(
            id = 3,
            title = "Radar Bogor",
            edition = "Edisi 01 April 2025",
            coverResId = R.drawable.radarbogor3,
            views = 0,
            rating = 5.0f
        ),
        EPustakaCollectionItem(
            id = 4,
            title = "Radar Bogor",
            edition = "Edisi 24 Maret 2025",
            coverResId = R.drawable.radarbogor4,
            views = 0,
            rating = 4.5f
        ),
        EPustakaCollectionItem(
            id = 5,
            title = "Radar Bogor",
            edition = "Edisi 17 Maret 2025",
            coverResId = R.drawable.radarbogor5,
            views = 0,
            rating = 5.0f
        ),
        EPustakaCollectionItem(
            id = 6,
            title = "Radar Bogor",
            edition = "Edisi 10 Maret 2025",
            coverResId = R.drawable.radarbogor6,
            views = 0,
            rating = 4.5f
        ),
        EPustakaCollectionItem(
            id = 7,
            title = "Radar Bogor",
            edition = "Edisi 03 Maret 2025",
            coverResId = R.drawable.radarbogor3,
            views = 0,
            rating = 5.0f
        ),
        EPustakaCollectionItem(
            id = 8,
            title = "Radar Bogor",
            edition = "Edisi 24 Februari 2025",
            coverResId = R.drawable.radarbogor2,
            views = 0,
            rating = 4.5f
        )
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PaleGreen)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
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
                            text = "ePustaka",
                            color = white,
                            fontSize = 18.sp,
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
                    }
                }

                // Content with LazyVerticalGrid containing fixed header and collections
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Profile card as a grid item that spans the full width
                    item(span = { GridItemSpan(2) }) {
                        AnimatedVisibility(
                            visible = showProfileCard,
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + slideOutVertically()
                        ) {
                            // ePustaka Profile Card
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, bottom = 8.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = white),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    // ePustaka Logo
                                    Image(
                                        painter = painterResource(id = ePustaka.logoResId),
                                        contentDescription = ePustaka.name,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Fit
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    // ePustaka Name
                                    Text(
                                        text = ePustaka.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        fontFamily = interFontFamily
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Stats: Members and Collections
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        // Members
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.padding(horizontal = 24.dp)
                                        ) {
                                            Text(
                                                text = "${ePustaka.memberCount}",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = interFontFamily
                                            )
                                            Text(
                                                text = "Anggota",
                                                fontSize = 10.sp,
                                                color = gray,
                                                fontFamily = interFontFamily
                                            )
                                        }

                                        // Collections
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.padding(horizontal = 24.dp)
                                        ) {
                                            Text(
                                                text = "${ePustaka.collectionCount}",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = interFontFamily
                                            )
                                            Text(
                                                text = "Koleksi",
                                                fontSize = 10.sp,
                                                color = gray,
                                                fontFamily = interFontFamily
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Join ePustaka Button
                                    Button(
                                        onClick = {
                                            // Update the joined state when button is clicked
                                            hasJoined = true
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = DarkGreen,
                                            contentColor = white
                                        ),
                                        enabled = !hasJoined // Disable button after joining
                                    ) {
                                        Text(
                                            text = if (hasJoined) "Telah Bergabung" else "Gabung ePustaka",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            fontFamily = interFontFamily
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // "Koleksi" title as a grid item that spans the full width
                    item(span = { GridItemSpan(2) }) {
                        Text(
                            text = "Koleksi",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = interFontFamily,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                    }

                    // Collection items
                    items(collectionItems) { item ->
                        EPustakaCollectionItemCard(
                            item = item,
                            interFontFamily = interFontFamily,
                            onClick = { onCollectionItemClicked(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EPustakaCollectionItemCard(
    item: EPustakaCollectionItem,
    interFontFamily: FontFamily,
    onClick: () -> Unit = {}
) {
    val gray = Color(0xFF979797)
    val yellow = Color(0xFFFFD700)
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            // Cover Image
            Image(
                painter = painterResource(id = item.coverResId),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 5f)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = item.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = interFontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Edition
            Text(
                text = item.edition,
                fontSize = 12.sp,
                color = gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = interFontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Views and Rating row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Views
                Icon(
                    painter = painterResource(id = R.drawable.queue_logo),
                    contentDescription = "Views",
                    tint = gray,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = " ${item.views}",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontFamily = interFontFamily
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Rating
                Icon(
                    painter = painterResource(id = R.drawable.baseline_star_24),
                    contentDescription = "Rating",
                    tint = yellow,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = " ${item.rating}",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontFamily = interFontFamily
                )

                Spacer(modifier = Modifier.weight(1f))

                // More options
                Box {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Options",
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
                                    text = "Unduh",
                                    fontFamily = interFontFamily,
                                    fontSize = 14.sp
                                )
                            },
                            onClick = {
                                // Handle download action
                                showMenu = false
                            }
                        )
                    }
                }
            }
        }
    }
}

// Data Classes
data class EPustakaDetailData(
    val id: Int,
    val name: String,
    val logoResId: Int,
    val memberCount: Int,
    val collectionCount: Int,
    val isJoined: Boolean
)

data class EPustakaCollectionItem(
    val id: Int,
    val title: String,
    val edition: String,
    val coverResId: Int,
    val views: Int,
    val rating: Float
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EPustakaDetailScreenPreview() {
    EPustakaDetailScreen()
}