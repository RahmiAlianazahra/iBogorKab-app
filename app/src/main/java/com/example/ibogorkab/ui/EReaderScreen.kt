package com.example.ibogorkab.ui

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import com.example.ibogorkab.R
import com.example.ibogorkab.ui.theme.PaleGreen
import kotlinx.coroutines.launch

data class ReaderTheme(
    val backgroundColor: Color,
    val cardColor: Color,
    val textColor: Color,
    val isDark: Boolean
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EReaderScreen(
    bookId: Int = 1,
    bookTitle: String = "Si Babi Hutan",
    chapterTitle: String = "Bab 1 dari 20",
    onBackPressed: () -> Unit = {}
) {
    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)
    val white = Color.White
    val black = Color.Black
    val darkGray = Color(0xFF121212)
    val lightGray = Color(0xFFE0E0E0)
    val darkModeTextColor = Color(0xFFE0E0E0)
    val darkModeGreen = Color(0xFF2E8B57)  // A more visible green for dark mode

    // Define themes
    val lightTheme = remember {
        ReaderTheme(
            backgroundColor = PaleGreen,
            cardColor = white,
            textColor = black,
            isDark = false
        )
    }

    val darkTheme = remember {
        ReaderTheme(
            backgroundColor = darkGray,
            cardColor = Color(0xFF202020),
            textColor = darkModeTextColor,
            isDark = true
        )
    }

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // State for text size
    var textSize by remember { mutableFloatStateOf(16f) }

    // State for showing the settings dialog
    var showSettings by remember { mutableStateOf(false) }

    // State for showing navigation bottom sheet
    var showNavSheet by remember { mutableStateOf(false) }

    // Current page state
    var currentPage by remember { mutableIntStateOf(14) }
    val totalPages = 280

    // Theme state (default is light)
    var currentTheme by remember { mutableStateOf(lightTheme) }
    var isDarkMode by remember { mutableStateOf(false) }

    // Function to toggle theme
    fun toggleTheme(useDarkMode: Boolean) {
        isDarkMode = useDarkMode
        currentTheme = if (useDarkMode) darkTheme else lightTheme
    }

    // Pager state for horizontal scrolling between pages
    val pagerState = rememberPagerState(initialPage = 0) { 2 } // 2 pages total

    // Scroll state for vertical scrolling
    val verticalScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Bottom sheet state
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = bookTitle,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = interFontFamily,
                            color = if (isDarkMode) darkModeTextColor else black
                        )
                        Text(
                            text = chapterTitle,
                            fontSize = 12.sp,
                            fontFamily = interFontFamily,
                            color = if (isDarkMode) Color.LightGray else Color.Gray
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isDarkMode) darkModeGreen else DarkGreen
                        )
                    }
                },
                actions = {
                    // Search
                    IconButton(onClick = { /* Search functionality */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = "Search",
                            tint = if (isDarkMode) darkModeGreen else DarkGreen
                        )
                    }
                    // Table of contents
                    IconButton(onClick = { /* Show table of contents */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_format_list_bulleted_24),
                            contentDescription = "Table of Contents",
                            tint = if (isDarkMode) darkModeGreen else DarkGreen
                        )
                    }
                    // Bookmark
                    IconButton(onClick = { /* Toggle bookmark */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_bookmark_border_24),
                            contentDescription = "Bookmark",
                            tint = if (isDarkMode) darkModeGreen else DarkGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (isDarkMode) darkTheme.cardColor else white
                )
            )
        },
        containerColor = currentTheme.backgroundColor // Set scaffold container color
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(currentTheme.backgroundColor)
                .clickable { showNavSheet = true }
        ) {
            // CONDITIONAL CONTENT BASED ON DIALOG STATE
            if (showNavSheet) {
                // When dialog is showing, use horizontal pager
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    // Page content
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxSize(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = currentTheme.cardColor),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Text(
                                text = if (page == 0) {
                                    """
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sit amet volutpat quam. Aenean fermentum enim quis aliquam gravida. Mauris pulvinar felis non consectetur rutrum. Donec placerat tempor iaculis. Morbi volutpat arcu a nisl pharetra congue. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt euismod sem, at molestie nunc. Maecenas efficitur eros vulputate, pellentesque ligula porttitor, consequat lacus.

                                        Null am a nisl quam. Aliquam aliquam nunc non volutpat feugiat. Donec at lectus tincidunt, gravida odio quis, volutpat urna. Nam at cursus dui. Donec quam nulla, venenatis commodo ipsum quis, consectetur volutpat sapien. Vivamus et dolor nec purus auctor condimentum. Praesent nec lorem orci. Nullam pharetra, lectus ut aliquam tempor, ipsum lorem ultrices dolor, nec gravida augue orci posuere lectus. Donec ullamcorper eros quis luctus imperdiet.

                                        Ut et congue tortor. Integer laoreet lorem id nulla dignissim condimentum non vitae ligula. Ut pretium vitae sapien a ultricies. Duis sit amet elit ac leo vestibulum mollis quis nec arcu. Vestibulum quis tempor ipsum. In ultricies tristique dictum.
                                    """.trimIndent()
                                } else {
                                    """
                                        Fusce suscipit, wisi nec facilisis facilisis, est dui fermentum leo, quis tempor ligula erat quis odio. Nunc porta vulputate tellus. Nunc rutrum turpis sed pede. Sed bibendum. Aliquam posuere. Nunc aliquet, augue nec adipiscing interdum, lacus tellus malesuada massa, quis varius mi purus non odio.

                                        Pellentesque condimentum, magna ut suscipit hendrerit, ipsum augue ornare nulla, non luctus diam neque sit amet urna. Curabitur vulputate vestibulum lorem. Fusce sagittis, libero non molestie mollis, magna orci ultrices dolor, at vulputate neque nulla lacinia eros. Sed id ligula quis est convallis tempor. Curabitur lacinia pulvinar nibh.

                                        Nam a sapien. Aliquam erat volutpat. Nunc tincidunt ante vitae massa. Duis ante orci, molestie vitae vehicula venenatis, tincidunt ac pede. Nulla accumsan, elit sit amet varius semper, nulla mauris mollis quam, tempor suscipit diam nulla vel leo. Maecenas fermentum, sem in pharetra pellentesque, velit turpis volutpat ante, in pharetra metus odio a lectus. Maecenas aliquet orci.
                                    """.trimIndent()
                                },
                                fontSize = textSize.sp,
                                lineHeight = (textSize * 1.5).sp,
                                fontFamily = interFontFamily,
                                textAlign = TextAlign.Justify,
                                color = currentTheme.textColor,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            } else {
                // When dialog is hidden, use vertical scroll with cards
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(verticalScrollState)
                        .padding(16.dp)
                ) {
                    // Page 1
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = currentTheme.cardColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Text(
                            text = """
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sit amet volutpat quam. Aenean fermentum enim quis aliquam gravida. Mauris pulvinar felis non consectetur rutrum. Donec placerat tempor iaculis. Morbi volutpat arcu a nisl pharetra congue. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt euismod sem, at molestie nunc. Maecenas efficitur eros vulputate, pellentesque ligula porttitor, consequat lacus.

                                Null am a nisl quam. Aliquam aliquam nunc non volutpat feugiat. Donec at lectus tincidunt, gravida odio quis, volutpat urna. Nam at cursus dui. Donec quam nulla, venenatis commodo ipsum quis, consectetur volutpat sapien. Vivamus et dolor nec purus auctor condimentum. Praesent nec lorem orci. Nullam pharetra, lectus ut aliquam tempor, ipsum lorem ultrices dolor, nec gravida augue orci posuere lectus. Donec ullamcorper eros quis luctus imperdiet.

                                Ut et congue tortor. Integer laoreet lorem id nulla dignissim condimentum non vitae ligula. Ut pretium vitae sapien a ultricies. Duis sit amet elit ac leo vestibulum mollis quis nec arcu. Vestibulum quis tempor ipsum. In ultricies tristique dictum.
                            """.trimIndent(),
                            fontSize = textSize.sp,
                            lineHeight = (textSize * 1.5).sp,
                            fontFamily = interFontFamily,
                            textAlign = TextAlign.Justify,
                            color = currentTheme.textColor,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    // Page 2
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = currentTheme.cardColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Text(
                            text = """
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sit amet volutpat quam. Aenean fermentum enim quis aliquam gravida. Mauris pulvinar felis non consectetur rutrum. Donec placerat tempor iaculis. Morbi volutpat arcu a nisl pharetra congue. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt euismod sem, at molestie nunc. Maecenas efficitur eros vulputate, pellentesque ligula porttitor, consequat lacus.

                                Null am a nisl quam. Aliquam aliquam nunc non volutpat feugiat. Donec at lectus tincidunt, gravida odio quis, volutpat urna. Nam at cursus dui. Donec quam nulla, venenatis commodo ipsum quis, consectetur volutpat sapien. Vivamus et dolor nec purus auctor condimentum. Praesent nec lorem orci. Nullam pharetra, lectus ut aliquam tempor, ipsum lorem ultrices dolor, nec gravida augue orci posuere lectus. Donec ullamcorper eros quis luctus imperdiet.

                                Ut et congue tortor. Integer laoreet lorem id nulla dignissim condimentum non vitae ligula. Ut pretium vitae sapien a ultricies. Duis sit amet elit ac leo vestibulum mollis quis nec arcu. Vestibulum quis tempor ipsum. In ultricies tristique dictum.
                            """.trimIndent(),
                            fontSize = textSize.sp,
                            lineHeight = (textSize * 1.5).sp,
                            fontFamily = interFontFamily,
                            textAlign = TextAlign.Justify,
                            color = currentTheme.textColor,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    // Add some space at the bottom for better scrolling
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

            // Navigation bottom sheet
            if (showNavSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showNavSheet = false },
                    sheetState = bottomSheetState,
                    containerColor = currentTheme.cardColor,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .windowInsetsPadding(WindowInsets.navigationBars),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Navigation controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Previous page button
                            IconButton(
                                onClick = {
                                    if (currentPage > 1) {
                                        currentPage--
                                        // Scroll to previous page
                                        coroutineScope.launch {
                                            if (pagerState.currentPage > 0) {
                                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                            }
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowLeft,
                                    contentDescription = "Previous Page",
                                    tint = if (isDarkMode) darkModeGreen else DarkGreen,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            // Page indicator
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, DarkGreen, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "$currentPage",
                                        color = if (isDarkMode) darkModeGreen else DarkGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = interFontFamily,
                                        fontSize = 16.sp
                                    )
                                }

                                Text(
                                    text = " / $totalPages",
                                    color = currentTheme.textColor,
                                    fontSize = 16.sp,
                                    fontFamily = interFontFamily
                                )
                            }

                            // Next page button
                            IconButton(
                                onClick = {
                                    if (currentPage < totalPages) {
                                        currentPage++
                                        // Scroll to next page
                                        coroutineScope.launch {
                                            if (pagerState.currentPage < 1) {
                                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                            }
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "Next Page",
                                    tint = if (isDarkMode) darkModeGreen else DarkGreen,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Reading settings button
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    bottomSheetState.hide()
                                }.invokeOnCompletion {
                                    showNavSheet = false
                                    showSettings = true
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isDarkMode) darkModeGreen else DarkGreen,
                                contentColor = white
                            )
                        ) {
                            Text(
                                text = "Atur Gaya Membaca",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFontFamily
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            if (showSettings) {
                Dialog(
                    onDismissRequest = { showSettings = false }
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = currentTheme.cardColor)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Pengaturan",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFontFamily,
                                color = currentTheme.textColor,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // Text Size options - vertical layout
                            Text(
                                text = "Ukuran Teks",
                                fontSize = 14.sp,
                                fontFamily = interFontFamily,
                                color = currentTheme.textColor,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            // Text size slider with A- and A+ buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Smaller text button
                                IconButton(
                                    onClick = { if (textSize > 12) textSize -= 2 }
                                ) {
                                    Text(
                                        text = "A",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isDarkMode) darkModeGreen else DarkGreen
                                    )
                                }

                                // Slider
                                Slider(
                                    value = textSize,
                                    onValueChange = { textSize = it },
                                    valueRange = 12f..24f,
                                    steps = 5,
                                    modifier = Modifier.weight(1f),
                                    colors = SliderDefaults.colors(
                                        thumbColor = if (isDarkMode) darkModeGreen else DarkGreen,
                                        activeTrackColor = if (isDarkMode) darkModeGreen else DarkGreen,
                                        inactiveTrackColor = Color.LightGray
                                    )
                                )

                                // Larger text button
                                IconButton(
                                    onClick = { if (textSize < 24) textSize += 2 }
                                ) {
                                    Text(
                                        text = "A",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isDarkMode) darkModeGreen else DarkGreen
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Theme options
                            Text(
                                text = "Tema",
                                fontSize = 14.sp,
                                color = currentTheme.textColor,
                                fontFamily = interFontFamily,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Light theme
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp)
                                        .padding(end = 8.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(
                                            width = if (!isDarkMode) 2.dp else 1.dp,
                                            color = if (!isDarkMode) DarkGreen else Color.LightGray,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .background(
                                            color = PaleGreen,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { toggleTheme(false) }
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Terang",
                                        fontSize = 12.sp,
                                        fontWeight = if (!isDarkMode) FontWeight.Bold else FontWeight.Normal,
                                        fontFamily = interFontFamily,
                                        color = if (isDarkMode) Color.DarkGray else black
                                    )
                                }

                                // Dark theme
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp)
                                        .padding(start = 8.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(
                                            width = if (isDarkMode) 2.dp else 1.dp,
                                            color = if (isDarkMode) darkModeGreen else Color.Gray,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .background(
                                            color = Color.DarkGray,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { toggleTheme(true) }
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Gelap",
                                        fontSize = 12.sp,
                                        fontWeight = if (isDarkMode) FontWeight.Bold else FontWeight.Normal,
                                        fontFamily = interFontFamily,
                                        color = darkModeTextColor
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Apply settings button
                            IconButton(
                                onClick = {
                                    // Apply theme changes when clicking "Atur"
                                    currentTheme = if (isDarkMode) darkTheme else lightTheme
                                    showSettings = false
                                },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text(
                                    text = "Atur",
                                    color = if (isDarkMode) darkModeGreen else DarkGreen,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = interFontFamily
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EReaderScreenPreview() {
    EReaderScreen()
}