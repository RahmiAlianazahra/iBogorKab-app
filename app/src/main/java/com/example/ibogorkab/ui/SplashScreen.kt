package com.example.ibogorkab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToOnboarding: () -> Unit) {
    // Define exact colors from the design
    val DarkGreen = Color(0xFF0C6B00)
    val LightGreen = Color(0xFF50AD42)
    val Yellow = Color(0xFFFDEF02)
    val white = Color.White

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Timer for splash screen (5 seconds)
    LaunchedEffect(key1 = true) {
        delay(5000) // 5 seconds
        onNavigateToOnboarding()
    }

    // Gradient background for the entire screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DarkGreen,
                        DarkGreen
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Persembahan dari",
                style = TextStyle(
                    color = white,
                    fontSize = 16.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.kab_bogor_logo),
                contentDescription = "iBogorkab Logo",
                modifier = Modifier.size(230.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Dinas Perpustakaan dan Arsip Daerah",
                style = TextStyle(
                    color = white,
                    fontSize = 16.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = "Kabupaten Bogor",
                style = TextStyle(
                    color = white,
                    fontSize = 18.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
   SplashScreen {

   }
}
