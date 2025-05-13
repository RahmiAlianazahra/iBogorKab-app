package com.example.ibogorkab.ui

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
fun EPustakaScreen(
    onBackPressed: () -> Unit = {},
    onSearchPressed: () -> Unit = {},
    onEPustakaClicked: (EPustakaData) -> Unit = {}
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

    // Sample data for ePustaka
    val ePustakaList = listOf(
        EPustakaData(
            1,
            "Radar Bogor",
            R.drawable.radar_bogor,
            "Perpustakaan Digital Radar Bogor"
        ),
        EPustakaData(
            2,
            "Dinas Arsip dan Perpustakaan Daerah",
            R.drawable.dinas_arsip,
            "Perpustakaan Resmi Kabupaten Bogor"
        ),
        EPustakaData(
            3,
            "BSE SMP",
            R.drawable.bse_smp,
            "Buku Sekolah Elektronik SMP"
        ),
        EPustakaData(
            4,
            "BSE SMA",
            R.drawable.bse_sma,
            "Buku Sekolah Elektronik SMA"
        ),
        EPustakaData(
            5,
            "BSE SD",
            R.drawable.bse_sd,
            "Buku Sekolah Elektronik SD"
        ),
        EPustakaData(
            6,
            "SMPN 2 Ciawi",
            R.drawable.smp2ciawi,
            "Perpustakaan Digital SMPN 2 Ciawi"
        ),
        EPustakaData(
            7,
            "SMPN 2 Cibinong",
            R.drawable.smp2cibinong,
            "Perpustakaan Digital SMPN 2 Cibinong"
        ),
        EPustakaData(
            8,
            "SMPIT Al-Madinah",
            R.drawable.smpitalmadinah,
            "Perpustakaan Digital SMPIT Al-Madinah"
        ),
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
                            "Temukan ePustaka",
                            fontFamily = interFontFamily,
                            fontSize = 12.sp
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

            // EPustaka Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(ePustakaList) { ePustaka ->
                    EPustakaCard(
                        ePustaka = ePustaka,
                        interFontFamily = interFontFamily,
                        onEPustakaClicked = { onEPustakaClicked(ePustaka) }
                    )
                }
            }
        }
    }
}

@Composable
fun EPustakaCard(
    ePustaka: EPustakaData,
    interFontFamily: FontFamily,
    onEPustakaClicked: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)  // Fixed height for all cards
            .clickable { onEPustakaClicked() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ePustaka Logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = ePustaka.logoResId),
                    contentDescription = ePustaka.name,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
            }

            // ePustaka Name
            Text(
                text = ePustaka.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontFamily = interFontFamily,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

data class EPustakaData(
    val id: Int,
    val name: String,
    val logoResId: Int,
    val description: String = ""
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EPustakaScreenPreview() {
    EPustakaScreen()
}