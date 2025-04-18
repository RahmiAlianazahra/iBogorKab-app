package com.example.ibogorkab.ui

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R
import com.example.ibogorkab.ui.theme.PaleGreen

@Composable
fun FilterScreen(
    onBackPressed: () -> Unit = {},
    onApplyFilter: (FilterOptions) -> Unit = {}
) {
    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)
    val white = Color.White

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Filter states
    var selectedFilter by remember { mutableStateOf<String?>("genre") }
    var showGenreSelection by remember { mutableStateOf(false) }
    var showAuthorSelection by remember { mutableStateOf(false) }
    var showPublishYearSelection by remember { mutableStateOf(false) }
    var nqBooksOnly by remember { mutableStateOf(false) }

    // Selected options
    var selectedGenres by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedAuthors by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedYears by remember { mutableStateOf<List<String>>(emptyList()) }

    // Function to handle filter selection
    fun selectFilter(filter: String) {
        if (selectedFilter == filter) {
            // If already selected, toggle detailed selection screens
            when (filter) {
                "genre" -> showGenreSelection = !showGenreSelection
                "author" -> showAuthorSelection = !showAuthorSelection
                "year" -> showPublishYearSelection = !showPublishYearSelection
            }
        } else {
            // Select a new filter
            selectedFilter = filter
            // Reset all selection screens
            showGenreSelection = false
            showAuthorSelection = false
            showPublishYearSelection = false
            // Show the appropriate selection screen
            when (filter) {
                "genre" -> showGenreSelection = true
                "author" -> showAuthorSelection = true
                "year" -> showPublishYearSelection = true
            }
        }
    }

    if (showGenreSelection) {
        GenreSelectionScreen(
            selectedGenres = selectedGenres,
            onGenreSelected = { genre ->
                // Toggle selection
                selectedGenres = if (selectedGenres.contains(genre)) {
                    selectedGenres - genre
                } else {
                    selectedGenres + genre
                }
            },
            onCancel = { showGenreSelection = false },
            onSave = { showGenreSelection = false }
        )
    } else if (showAuthorSelection) {
        // Author selection screen would go here
        // To be implemented
    } else if (showPublishYearSelection) {
        YearSelectionScreen(
            selectedYears = selectedYears,
            onYearSelected = { year ->
                // Toggle selection
                selectedYears = if (selectedYears.contains(year)) {
                    selectedYears - year
                } else {
                    selectedYears + year
                }
            },
            onCancel = { showPublishYearSelection = false },
            onSave = { showPublishYearSelection = false }
        )
    } else {
        // Main filter screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PaleGreen)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header with back button and title
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = DarkGreen
                        )
                    }

                    Text(
                        text = "Filter Kategori",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGreen,
                        fontFamily = interFontFamily,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Filter options
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Genre Filter
                    FilterOptionCard(
                        icon = painterResource(id = R.drawable.baseline_menu_book_24),
                        title = "Genre",
                        subtitle = if (selectedGenres.isEmpty())
                            "Tentukan berdasarkan genre buku"
                        else
                            selectedGenres.joinToString(", "),
                        isSelected = false, // Do not show checkmark based on selection state
                        hasSelections = selectedGenres.isNotEmpty(), // Show checkmark when genres are selected
                        onOptionClick = { selectFilter("genre") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Author Filter
                    FilterOptionCard(
                        icon = painterResource(id = R.drawable.baseline_people_24),
                        title = "Pengarang",
                        subtitle = if (selectedAuthors.isEmpty())
                            "Tentukan berdasarkan pengarang buku"
                        else
                            selectedAuthors.joinToString(", "),
                        isSelected = false, // Do not show checkmark based on selection state
                        hasSelections = selectedAuthors.isNotEmpty(), // Show checkmark when authors are selected
                        onOptionClick = { selectFilter("author") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Year Filter
                    FilterOptionCard(
                        icon = painterResource(id = R.drawable.baseline_calendar_month_24),
                        title = "Tahun Terbit",
                        subtitle = if (selectedYears.isEmpty())
                            "Tentukan berdasarkan tahun terbit buku"
                        else
                            selectedYears.joinToString(", "),
                        isSelected = false, // Do not show checkmark based on selection state
                        hasSelections = selectedYears.isNotEmpty(), // Show checkmark when years are selected
                        onOptionClick = { selectFilter("year") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // No Queue Option
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = white),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Pinjam koleksi tanpa antri",
                                fontSize = 16.sp,
                                fontFamily = interFontFamily
                            )

                            Switch(
                                checked = nqBooksOnly,
                                onCheckedChange = { nqBooksOnly = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = white,
                                    checkedTrackColor = DarkGreen,
                                    checkedBorderColor = DarkGreen,
                                    uncheckedThumbColor = white,
                                    uncheckedTrackColor = Color.LightGray,
                                    uncheckedBorderColor = Color.LightGray
                                )
                            )
                        }
                    }
                }

                // Bottom buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            // Reset all filters
                            selectedGenres = emptyList()
                            selectedAuthors = emptyList()
                            selectedYears = emptyList()
                            nqBooksOnly = false
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = DarkGreen
                        )
                    ) {
                        Text(
                            text = "Atur Ulang",
                            fontSize = 16.sp,
                            fontFamily = interFontFamily
                        )
                    }

                    Button(
                        onClick = {
                            onApplyFilter(
                                FilterOptions(
                                    genres = selectedGenres,
                                    authors = selectedAuthors,
                                    years = selectedYears,
                                    noQueueOnly = nqBooksOnly
                                )
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkGreen,
                            contentColor = white
                        )
                    ) {
                        Text(
                            text = "Terapkan",
                            fontSize = 16.sp,
                            fontFamily = interFontFamily
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterOptionCard(
    icon: androidx.compose.ui.graphics.painter.Painter,
    title: String,
    subtitle: String = "",
    isSelected: Boolean = false,
    hasSelections: Boolean = false, // New parameter to show checkmark when filter has selections
    onOptionClick: () -> Unit = {}
) {
    val DarkGreen = Color(0xFF0D7600)
    val white = Color.White
    val gray = Color(0xFF757575)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOptionClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = white),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = title,
                tint = DarkGreen,
                modifier = Modifier.size(24.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFontFamily
                )

                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = gray,
                        fontFamily = interFontFamily
                    )
                }
            }

            // Show checkmark if the filter is selected OR has selections
            if (isSelected || hasSelections) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_circle_24),
                    contentDescription = "Selected",
                    tint = DarkGreen,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun GenreSelectionScreen(
    selectedGenres: List<String> = emptyList(),
    onGenreSelected: (String) -> Unit = {},
    onCancel: () -> Unit = {},
    onSave: () -> Unit = {}
) {
    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val white = Color.White
    val PaleGreen = Color(0xFFEAFFE5)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // List of available genres
    val genres = listOf(
        "Agama", "Alam & Lingkungan", "Anak", "Arsitektur",
        "Bahasa", "Biografi", "Ekonomi", "Teknologi",
        "Novel", "Pendidikan", "Pertanian & Peternakan",
        "Psikologi", "Memasak", "Kamus"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PaleGreen)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with back button and title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCancel) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = DarkGreen
                    )
                }

                Text(
                    text = "Pilihan Genre",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen,
                    fontFamily = interFontFamily,
                    modifier = Modifier.weight(1f)
                )
            }

            // Genre options
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                genres.forEach { genre ->
                    val isSelected = selectedGenres.contains(genre)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onGenreSelected(genre) },
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = white)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = genre,
                                fontSize = 16.sp,
                                fontFamily = interFontFamily
                            )

                            if (isSelected) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_check_circle_24),
                                    contentDescription = "Selected",
                                    tint = DarkGreen,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Bottom buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = DarkGreen
                    )
                ) {
                    Text(
                        text = "Batal",
                        fontSize = 16.sp,
                        fontFamily = interFontFamily
                    )
                }

                Button(
                    onClick = onSave,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen,
                        contentColor = white
                    )
                ) {
                    Text(
                        text = "Simpan",
                        fontSize = 16.sp,
                        fontFamily = interFontFamily
                    )
                }
            }
        }
    }
}

@Composable
fun YearSelectionScreen(
    selectedYears: List<String> = emptyList(),
    onYearSelected: (String) -> Unit = {},
    onCancel: () -> Unit = {},
    onSave: () -> Unit = {}
) {
    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val white = Color.White
    val PaleGreen = Color(0xFFEAFFE5)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // List of available years
    val years = listOf(
        "2024", "2023", "2022", "2021",
        "2020", "2019", "2018", "2017",
        "2016", "2015", "2014",
        "2013", "2012", "2011", "2010"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PaleGreen)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with back button and title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCancel) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = DarkGreen
                    )
                }

                Text(
                    text = "Pilih Tahun Terbit",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen,
                    fontFamily = interFontFamily,
                    modifier = Modifier.weight(1f)
                )
            }

            // Year options
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                years.forEach { year ->
                    val isSelected = selectedYears.contains(year)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onYearSelected(year) },
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = white)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = year,
                                fontSize = 16.sp,
                                fontFamily = interFontFamily
                            )

                            if (isSelected) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_check_circle_24),
                                    contentDescription = "Selected",
                                    tint = DarkGreen,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Bottom buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = DarkGreen
                    )
                ) {
                    Text(
                        text = "Batal",
                        fontSize = 16.sp,
                        fontFamily = interFontFamily
                    )
                }

                Button(
                    onClick = onSave,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen,
                        contentColor = white
                    )
                ) {
                    Text(
                        text = "Simpan",
                        fontSize = 16.sp,
                        fontFamily = interFontFamily
                    )
                }
            }
        }
    }
}

// Data class to hold filter options
data class FilterOptions(
    val genres: List<String> = emptyList(),
    val authors: List<String> = emptyList(),
    val years: List<String> = emptyList(),
    val noQueueOnly: Boolean = false
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FilterScreenPreview() {
    FilterScreen()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GenreSelectionScreenPreview() {
    GenreSelectionScreen(
        selectedGenres = listOf("Novel")
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun YearSelectionScreenPreview() {
    YearSelectionScreen(
        selectedYears = listOf("2022")
    )
}