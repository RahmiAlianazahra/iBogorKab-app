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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R
import com.example.ibogorkab.ui.theme.PaleGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackPressed: () -> Unit = {},
    onFilterPressed: () -> Unit = {}
) {
    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)
    val white = Color.White
    val gray = Color(0xFF979797)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // State for search query
    var searchQuery by remember { mutableStateOf("") }

    // State for selected tab
    var selectedTabIndex by remember { mutableStateOf(0) }

    // State for filter options
    var filterOptions by remember { mutableStateOf<FilterOptions?>(null) }

    // Focus requester and manager for text field
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    // Tab items
    val tabs = listOf("Buku", "ePustaka", "Pengguna")

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(white)
        ) {
            // Search bar with back button and filter
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                        .clip(RoundedCornerShape(16.dp))
                        .focusRequester(focusRequester),
                    placeholder = {
                        Text(
                            "Temukan buku favoritmu",
                            fontFamily = interFontFamily,
                            fontSize = 12.sp,
                            color = gray
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
                    textStyle = TextStyle(
                        fontFamily = interFontFamily,
                        fontSize = 14.sp
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = DarkGreen,
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        focusedContainerColor = Color(0xFFF5F5F5)
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            focusManager.clearFocus()
                            // Perform search
                        }
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

            // Display applied filters if any
            if (filterOptions != null) {
                DisplayAppliedFilters(
                    filterOptions = filterOptions!!,
                    onClearFilter = { filterOptions = null }
                )

                Spacer(modifier = Modifier.height(8.dp))
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

            // Content area with pale green background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PaleGreen)
            ) {
                // Content will be shown based on the selected tab and search query
                // This is a placeholder. In a real implementation, you would show
                // different content based on the selected tab, search query, and filter options.

                when (selectedTabIndex) {
                    0 -> {
                        // Books tab content
                    }
                    1 -> {
                        // ePustaka tab content
                    }
                    2 -> {
                        // Users tab content
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayAppliedFilters(
    filterOptions: FilterOptions,
    onClearFilter: () -> Unit = {}
) {
    val DarkGreen = Color(0xFF0D7600)
    val PaleGreen = Color(0xFFEAFFE5)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .background(
                color = PaleGreen,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Filter aktif: ",
            fontFamily = interFontFamily,
            fontSize = 12.sp,
            color = DarkGreen
        )

        // Display summary of applied filters
        val filterTexts = mutableListOf<String>()

        if (filterOptions.genres.isNotEmpty()) {
            filterTexts.add("Genre (${filterOptions.genres.size})")
        }

        if (filterOptions.authors.isNotEmpty()) {
            filterTexts.add("Pengarang (${filterOptions.authors.size})")
        }

        if (filterOptions.years.isNotEmpty()) {
            filterTexts.add("Tahun (${filterOptions.years.size})")
        }

        if (filterOptions.noQueueOnly) {
            filterTexts.add("Tanpa antri")
        }

        Text(
            text = filterTexts.joinToString(", "),
            fontFamily = interFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGreen,
            modifier = Modifier.weight(1f)
        )

        // Clear filters button
        IconButton(
            onClick = onClearFilter,
            modifier = Modifier.padding(0.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                contentDescription = "Clear filters",
                tint = DarkGreen,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}