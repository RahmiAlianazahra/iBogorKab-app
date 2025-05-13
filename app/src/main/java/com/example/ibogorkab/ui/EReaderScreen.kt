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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.example.ibogorkab.R
import com.example.ibogorkab.ui.theme.PaleGreen
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

// Enum for reading modes
enum class ReadingMode {
    SCROLL,
    PAGE_TURN
}

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
    val red = Color(0xFFD32F2F)
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

    // State for reading mode
    var readingMode by remember { mutableStateOf(ReadingMode.SCROLL) }

    // State for text size
    var textSize by remember { mutableFloatStateOf(16f) }

    // State for showing the settings dialog
    var showSettings by remember { mutableStateOf(false) }

    // State for showing navigation bottom sheet
    var showNavSheet by remember { mutableStateOf(false) }

    // Current page state
    var currentPage by remember { mutableIntStateOf(14) }
    val totalPages = 280

    // State for page input field
    var pageInput by remember { mutableStateOf(currentPage.toString()) }
    var hasPageInputError by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var previousValue by remember { mutableStateOf(currentPage.toString()) }

    // Theme state (default is light)
    var currentTheme by remember { mutableStateOf(lightTheme) }
    var isDarkMode by remember { mutableStateOf(false) }

    // Get coroutine scope
    val coroutineScope = rememberCoroutineScope()

    // Sample content pages - each representing a page in the book
    val pages = listOf(
        """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sit amet volutpat quam. Aenean fermentum enim quis aliquam gravida. Mauris pulvinar felis non consectetur rutrum. Donec placerat tempor iaculis. Morbi volutpat arcu a nisl pharetra congue. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tincidunt euismod sem, at molestie nunc. Maecenas efficitur eros vulputate, pellentesque ligula porttitor, consequat lacus.

            Null am a nisl quam. Aliquam aliquam nunc non volutpat feugiat. Donec at lectus tincidunt, gravida odio quis, volutpat urna. Nam at cursus dui. Donec quam nulla, venenatis commodo ipsum quis, consectetur volutpat sapien. Vivamus et dolor nec purus auctor condimentum. Praesent nec lorem orci. Nullam pharetra, lectus ut aliquam tempor, ipsum lorem ultrices dolor, nec gravida augue orci posuere lectus. Donec ullamcorper eros quis luctus imperdiet.

            Ut et congue tortor. Integer laoreet lorem id nulla dignissim condimentum non vitae ligula. Ut pretium vitae sapien a ultricies. Duis sit amet elit ac leo vestibulum mollis quis nec arcu. Vestibulum quis tempor ipsum. In ultricies tristique dictum.
        """.trimIndent(),
        """
            Fusce suscipit, wisi nec facilisis facilisis, est dui fermentum leo, quis tempor ligula erat quis odio. Nunc porta vulputate tellus. Nunc rutrum turpis sed pede. Sed bibendum. Aliquam posuere. Nunc aliquet, augue nec adipiscing interdum, lacus tellus malesuada massa, quis varius mi purus non odio.

            Pellentesque condimentum, magna ut suscipit hendrerit, ipsum augue ornare nulla, non luctus diam neque sit amet urna. Curabitur vulputate vestibulum lorem. Fusce sagittis, libero non molestie mollis, magna orci ultrices dolor, at vulputate neque nulla lacinia eros. Sed id ligula quis est convallis tempor. Curabitur lacinia pulvinar nibh.

            Nam a sapien. Aliquam erat volutpat. Nunc tincidunt ante vitae massa. Duis ante orci, molestie vitae vehicula venenatis, tincidunt ac pede. Nulla accumsan, elit sit amet varius semper, nulla mauris mollis quam, tempor suscipit diam nulla vel leo. Maecenas fermentum, sem in pharetra pellentesque, velit turpis volutpat ante, in pharetra metus odio a lectus. Maecenas aliquet orci.
        """.trimIndent(),
        """
            In hac habitasse platea dictumst. Donec ullamcorper, odio nec tempor volutpat, felis lorem varius mi, at semper lorem libero quis urna. Sed sagittis, nibh a imperdiet convallis, elit odio faucibus erat, quis dapibus enim massa quis lectus. Maecenas bibendum, sapien vel sollicitudin eleifend, odio libero imperdiet turpis, vel tempus sem massa non erat. Quisque faucibus, nibh id faucibus eleifend, sapien dolor ultrices arcu, vel tincidunt ipsum turpis quis augue.

            Fusce nec facilisis eros. Vestibulum eu justo id nulla lobortis ullamcorper. Integer vel pede eu purus tempor eleifend. Morbi ut tortor eu eros aliquam faucibus. Sed ac metus sit amet mauris faucibus elementum. Aenean eu lectus non eros lobortis faucibus. Nulla facilisi. Suspendisse at nunc eu justo pharetra pharetra. Sed id justo a nulla placerat hendrerit.

            Mauris eget pede vel nulla volutpat volutpat. Sed tincidunt, nibh nec ultricies tristique, nunc nibh varius odio, vel vehicula sapien justo non nunc. Fusce elementum, nunc quis pharetra vulputate, magna magna rhoncus magna, vel sagittis nisl justo non magna. Integer vitae magna nec nisl pharetra tempor.
        """.trimIndent()
    )

    // Pager state for page turn mode
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { pages.size }

    // Scroll state for vertical scrolling
    val verticalScrollState = rememberScrollState()

    // State to track the visible pages in scroll mode
    var visiblePageIndex by remember { mutableIntStateOf(0) }

    // Function to calculate which page is currently visible in scroll mode
    // This is a simplified version - in a real app, this would need to be more sophisticated
    LaunchedEffect(verticalScrollState.value, readingMode) {
        if (readingMode == ReadingMode.SCROLL) {
            // Simplified calculation - in a real app, we would need more precise logic
            val scrollPercentage = if (verticalScrollState.maxValue > 0) {
                verticalScrollState.value.toFloat() / verticalScrollState.maxValue.toFloat()
            } else 0f

            // Map scroll position to page index
            visiblePageIndex = (scrollPercentage * (pages.size - 1)).toInt().coerceIn(0, pages.size - 1)

            // Update current page based on visible page
            // Add 1 because pages are 1-indexed in the UI
            val mappedBookPage = (visiblePageIndex + 1) + ((currentPage / pages.size) * pages.size)
            if (mappedBookPage <= totalPages && mappedBookPage != currentPage) {
                currentPage = mappedBookPage
                pageInput = currentPage.toString()
            }
        }
    }

    // Bottom sheet state
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Function to toggle theme
    fun toggleTheme(useDarkMode: Boolean) {
        isDarkMode = useDarkMode
        currentTheme = if (useDarkMode) darkTheme else lightTheme
    }

    // Function to handle page navigation with validation
    fun navigateToPage(pageNumber: String) {
        try {
            val newPage = pageNumber.toInt()
            if (newPage in 1..totalPages) {
                // Only update if page actually changes
                if (currentPage != newPage) {
                    currentPage = newPage
                    pageInput = newPage.toString()

                    if (readingMode == ReadingMode.PAGE_TURN) {
                        // Map book page to pager index (for demo purposes just use modulo)
                        val pagerIndex = (newPage - 1) % pages.size
                        // Animate pager to simulate page turn
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerIndex)
                        }
                    } else if (readingMode == ReadingMode.SCROLL) {
                        // Calculate approximate scroll position for the page
                        // This is a simplified version - in a real app, we'd need more precise positioning
                        val targetPageIndex = (newPage - 1) % pages.size
                        val approxScrollPosition = (targetPageIndex.toFloat() / pages.size.toFloat()) * verticalScrollState.maxValue

                        // Scroll to the calculated position
                        coroutineScope.launch {
                            verticalScrollState.animateScrollTo(approxScrollPosition.toInt())
                        }
                    }
                }
                hasPageInputError = false
            } else {
                hasPageInputError = true
            }
        } catch (e: NumberFormatException) {
            hasPageInputError = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = bookTitle,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = interFontFamily,
                            color = if (isDarkMode) darkModeTextColor else black
                        )
                        Text(
                            text = chapterTitle,
                            fontSize = 10.sp,
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
                    // Toggle reading mode button
                    IconButton(onClick = {
                        readingMode = if (readingMode == ReadingMode.SCROLL)
                            ReadingMode.PAGE_TURN else ReadingMode.SCROLL
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (readingMode == ReadingMode.SCROLL)
                                    R.drawable.baseline_menu_book_24
                                else
                                    R.drawable.baseline_keyboard_double_arrow_down_24
                            ),
                            contentDescription = if (readingMode == ReadingMode.SCROLL)
                                "Switch to Page Turn Mode" else "Switch to Scroll Mode",
                            tint = if (isDarkMode) darkModeGreen else DarkGreen
                        )
                    }

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
            // Display content based on reading mode
            when (readingMode) {
                ReadingMode.SCROLL -> {
                    // Scrollable content with cards
                    ScrollModeContent(
                        pages = pages,
                        textSize = textSize,
                        interFontFamily = interFontFamily,
                        currentTheme = currentTheme,
                        verticalScrollState = verticalScrollState
                    )
                }
                ReadingMode.PAGE_TURN -> {
                    // Page turn animation with HorizontalPager
                    PageTurnModeContent(
                        pages = pages,
                        pagerState = pagerState,
                        textSize = textSize,
                        interFontFamily = interFontFamily,
                        currentTheme = currentTheme
                    )
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
                                        pageInput = currentPage.toString()
                                        hasPageInputError = false

                                        if (readingMode == ReadingMode.PAGE_TURN) {
                                            // Map book page to pager index (for demo purposes just use modulo)
                                            val pagerIndex = (currentPage - 1) % pages.size
                                            // Animate page turn
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(pagerIndex)
                                            }
                                        } else if (readingMode == ReadingMode.SCROLL) {
                                            // Calculate approximate scroll position for the page
                                            val targetPageIndex = (currentPage - 1) % pages.size
                                            val approxScrollPosition = (targetPageIndex.toFloat() / pages.size.toFloat()) * verticalScrollState.maxValue

                                            // Scroll to the calculated position
                                            coroutineScope.launch {
                                                verticalScrollState.animateScrollTo(approxScrollPosition.toInt())
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

                            // Page indicator with editable text field
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                // Editable page number field
                                OutlinedTextField(
                                    value = pageInput,
                                    onValueChange = { input ->
                                        // Only allow numeric input with reasonable length
                                        if (input.all { it.isDigit() } && input.length <= 4) {
                                            pageInput = input
                                            hasPageInputError = false
                                            isEditing = true
                                        }
                                    },
                                    modifier = Modifier
                                        .width(70.dp)
                                        .onFocusChanged { focusState ->
                                            if (!focusState.isFocused && isEditing) {
                                                // Only navigate if the value actually changed
                                                if (previousValue != pageInput) {
                                                    navigateToPage(pageInput)
                                                    previousValue = pageInput
                                                }
                                                isEditing = false
                                            }
                                        },
                                    textStyle = androidx.compose.ui.text.TextStyle(
                                        textAlign = TextAlign.Center,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = interFontFamily,
                                        color = if (isDarkMode) darkModeGreen else DarkGreen
                                    ),
                                    singleLine = true,
                                    isError = hasPageInputError,
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = if (hasPageInputError) red else if (isDarkMode) darkModeGreen else DarkGreen,
                                        unfocusedBorderColor = if (hasPageInputError) red else if (isDarkMode) darkModeGreen else DarkGreen,
                                        errorBorderColor = red,
                                        focusedContainerColor = currentTheme.cardColor,
                                        unfocusedContainerColor = currentTheme.cardColor
                                    ),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            if (isEditing) {
                                                navigateToPage(pageInput)
                                                isEditing = false
                                                previousValue = pageInput
                                            }
                                        }
                                    )
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = "/ $totalPages",
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
                                        pageInput = currentPage.toString()
                                        hasPageInputError = false

                                        if (readingMode == ReadingMode.PAGE_TURN) {
                                            // Map book page to pager index (for demo purposes just use modulo)
                                            val pagerIndex = (currentPage - 1) % pages.size
                                            // Animate page turn
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(pagerIndex)
                                            }
                                        } else if (readingMode == ReadingMode.SCROLL) {
                                            // Calculate approximate scroll position for the page
                                            val targetPageIndex = (currentPage - 1) % pages.size
                                            val approxScrollPosition = (targetPageIndex.toFloat() / pages.size.toFloat()) * verticalScrollState.maxValue

                                            // Scroll to the calculated position
                                            coroutineScope.launch {
                                                verticalScrollState.animateScrollTo(approxScrollPosition.toInt())
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

                        // Error message for invalid page input
                        if (hasPageInputError) {
                            Text(
                                text = "Halaman harus antara 1-$totalPages",
                                color = red,
                                fontSize = 12.sp,
                                fontFamily = interFontFamily,
                                modifier = Modifier.padding(top = 4.dp)
                            )
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
                                fontSize = 14.sp,
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

                            // Reading mode selector
                            Text(
                                text = "Mode Membaca",
                                fontSize = 14.sp,
                                fontFamily = interFontFamily,
                                color = currentTheme.textColor,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Scroll mode button
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp)
                                        .padding(end = 8.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(
                                            width = if (readingMode == ReadingMode.SCROLL) 2.dp else 1.dp,
                                            color = if (readingMode == ReadingMode.SCROLL)
                                                (if (isDarkMode) darkModeGreen else DarkGreen)
                                            else Color.LightGray,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .background(
                                            color = if (isDarkMode) Color(0xFF2A2A2A) else lightGray,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { readingMode = ReadingMode.SCROLL }
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_format_list_bulleted_24),
                                            contentDescription = "Scroll Mode",
                                            tint = if (readingMode == ReadingMode.SCROLL)
                                                (if (isDarkMode) darkModeGreen else DarkGreen)
                                            else Color.LightGray,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Gulir",
                                            fontSize = 12.sp,
                                            fontWeight = if (readingMode == ReadingMode.SCROLL)
                                                FontWeight.Bold else FontWeight.Normal,
                                            fontFamily = interFontFamily,
                                            color = if (readingMode == ReadingMode.SCROLL)
                                                (if (isDarkMode) darkModeGreen else DarkGreen)
                                            else Color.LightGray
                                        )
                                    }
                                }

                                // Page turn mode button
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp)
                                        .padding(start = 8.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(
                                            width = if (readingMode == ReadingMode.PAGE_TURN) 2.dp else 1.dp,
                                            color = if (readingMode == ReadingMode.PAGE_TURN)
                                                (if (isDarkMode) darkModeGreen else DarkGreen)
                                            else Color.LightGray,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .background(
                                            color = if (isDarkMode) Color(0xFF2A2A2A) else lightGray,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { readingMode = ReadingMode.PAGE_TURN }
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_menu_book_24),
                                            contentDescription = "Page Turn Mode",
                                            tint = if (readingMode == ReadingMode.PAGE_TURN)
                                                (if (isDarkMode) darkModeGreen else DarkGreen)
                                            else Color.LightGray,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Lembaran",
                                            fontSize = 12.sp,
                                            fontWeight = if (readingMode == ReadingMode.PAGE_TURN)
                                                FontWeight.Bold else FontWeight.Normal,
                                            fontFamily = interFontFamily,
                                            color = if (readingMode == ReadingMode.PAGE_TURN)
                                                (if (isDarkMode) darkModeGreen else DarkGreen)
                                            else Color.LightGray
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Text Size options
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

// Scroll Mode Content
@Composable
fun ScrollModeContent(
    pages: List<String>,
    textSize: Float,
    interFontFamily: FontFamily,
    currentTheme: ReaderTheme,
    verticalScrollState: ScrollState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(verticalScrollState)
            .padding(16.dp)
    ) {
        // Display all pages in scrollable cards
        pages.forEachIndexed { index, content ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = if (index < pages.size - 1) 16.dp else 0.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = currentTheme.cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = content,
                        fontSize = textSize.sp,
                        lineHeight = (textSize * 1.5).sp,
                        fontFamily = interFontFamily,
                        textAlign = TextAlign.Justify,
                        color = currentTheme.textColor,
                        modifier = Modifier.padding(16.dp)
                    )

                    // Page indicator at bottom
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 8.dp)
                            .size(32.dp)
                            .background(
                                color = if (currentTheme.isDark) Color(0xFF303030) else Color.LightGray.copy(alpha = 0.7f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            fontSize = 12.sp,
                            fontFamily = interFontFamily,
                            color = currentTheme.textColor
                        )
                    }
                }
            }
        }

        // Add some space at the bottom for better scrolling
        Spacer(modifier = Modifier.height(32.dp))
    }
}

// Page Turn Mode Content with page flip animation
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageTurnModeContent(
    pages: List<String>,
    pagerState: PagerState,
    textSize: Float,
    interFontFamily: FontFamily,
    currentTheme: ReaderTheme
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        pageSpacing = 0.dp,
        pageSize = PageSize.Fill
    ) { page ->
        // Page content with animation
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                // Apply page turning animation effect
                .graphicsLayer {
                    // Calculate the page offset fraction for animation
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                            ).absoluteValue

                    // Apply a slightly modified page curl effect
                    // Rotate around Y axis as the page turns
                    rotationY = lerp(0f, 60f, pageOffset)

                    // Scale down slightly when turning
                    scaleX = lerp(1f, 0.85f, pageOffset)
                    scaleY = lerp(1f, 0.85f, pageOffset)

                    // Apply alpha fading during page turn
                    alpha = lerp(1f, 0.5f, pageOffset)

                    // Adding slight 3D perspective effect
                    cameraDistance = 8 * density
                },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = currentTheme.cardColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Page content
                Text(
                    text = pages[page],
                    fontSize = textSize.sp,
                    lineHeight = (textSize * 1.5).sp,
                    fontFamily = interFontFamily,
                    textAlign = TextAlign.Justify,
                    color = currentTheme.textColor,
                    modifier = Modifier.padding(16.dp)
                )

                // Page number indicator at bottom
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                        .size(32.dp)
                        .background(
                            color = if (currentTheme.isDark) Color(0xFF303030) else Color.LightGray.copy(alpha = 0.7f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${page + 1}",
                        fontSize = 12.sp,
                        fontFamily = interFontFamily,
                        color = currentTheme.textColor
                    )
                }
            }
        }
    }
}

// Helper function for animation interpolation
private fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction.coerceIn(0f, 1f)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EReaderScreenPreview() {
    EReaderScreen()
}