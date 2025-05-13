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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R
import com.example.ibogorkab.ui.theme.PaleGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingReviewScreen(
    bookId: Int = 1,
    bookTitle: String = "Edensor",
    bookAuthor: String = "Andrea Hirata",
    bookCoverResId: Int = R.drawable.edensor,
    onBackPressed: () -> Unit = {},
    onSubmitReview: (bookId: Int, rating: Int, review: String) -> Unit = { _, _, _ -> }
) {

    val coroutineScope = rememberCoroutineScope()

    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)
    val Yellow = Color(0xFFFFD700)
    val White = Color.White
    val Gray = Color(0xFF979797)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // State for rating and review
    var rating by remember { mutableStateOf(0) }
    var reviewText by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    // Validation state
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
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
                            tint = White
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Title
                    Text(
                        text = "Beri Ulasan",
                        color = White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = interFontFamily,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PaleGreen)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Book info header
            Text(
                text = bookTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = interFontFamily,
                textAlign = TextAlign.Center
            )

            Text(
                text = bookAuthor,
                fontSize = 14.sp,
                color = Gray,
                fontFamily = interFontFamily,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Rating prompt
            Text(
                text = "Berikan penilaian anda untuk buku ini",
                fontSize = 12.sp,
                fontFamily = interFontFamily,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Star rating
            StarRating(
                currentRating = rating,
                onRatingChanged = {
                    rating = it
                    if (isError && rating > 0) {
                        isError = false
                        errorMessage = ""
                    }
                },
                starSize = 40.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (isError && errorMessage.contains("bintang")) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontFamily = interFontFamily,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Review text field
            Text(
                text = "Tulis Ulasan",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = interFontFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = reviewText,
                onValueChange = {
                    reviewText = it
                    if (isError && reviewText.isNotBlank()) {
                        isError = false
                        errorMessage = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DarkGreen,
                    unfocusedBorderColor = Gray,
                    unfocusedContainerColor = White,
                    focusedContainerColor = White,
                    errorBorderColor = Color.Red,
                    errorContainerColor = White
                ),
                placeholder = {
                    Text(
                        text = "Bagikan pendapat Anda tentang buku ini...",
                        fontFamily = interFontFamily,
                        fontSize = 12.sp,
                        color = Gray
                    )
                },
                isError = isError && errorMessage.contains("ulasan")
            )

            if (isError && errorMessage.contains("ulasan")) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontFamily = interFontFamily,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Submit button
            Button(
                onClick = {
                    when {
                        rating == 0 -> {
                            isError = true
                            errorMessage = "Silakan pilih bintang untuk penilaian"
                        }

                        reviewText.isBlank() -> {
                            isError = true
                            errorMessage = "Silakan tulis ulasan Anda"
                        }

                        else -> {
                            submitted = true
                            // Use coroutine to add delay before navigating back
                            coroutineScope.launch {
                                // Wait 1.5 seconds to show the success message
                                delay(1500)
                                // Then submit and navigate back
                                onSubmitReview(bookId, rating, reviewText)
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGreen,
                    contentColor = White
                )
            ) {
                Text(
                    text = "Kirim Ulasan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFontFamily
                )
            }

            // Success message
            if (submitted) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = LightGreen.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_check_circle_24),
                            contentDescription = "Success",
                            tint = DarkGreen,
                            modifier = Modifier.size(24.dp)
                        )

                        Text(
                            text = "Ulasan berhasil dikirim",
                            color = DarkGreen,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = interFontFamily,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StarRating(
    currentRating: Int,
    onRatingChanged: (Int) -> Unit,
    starSize: Dp = 24.dp,
    modifier: Modifier = Modifier
) {
    val Yellow = Color(0xFFFFD700)
    val Gray = Color(0xFFD3D3D3)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(
                    id = if (i <= currentRating) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
                ),
                contentDescription = "Star $i",
                tint = if (i <= currentRating) Yellow else Gray,
                modifier = Modifier
                    .size(width = starSize, height = starSize)
                    .padding(start = 4.dp, end = 4.dp)
                    .clickable { onRatingChanged(i) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RatingReviewScreenPreview() {
    RatingReviewScreen()
}

// Data class for a user review
data class UserReview(
    val userId: String = "", // In a real app, this would be the user's ID
    val userName: String = "",
    val userImage: Int = R.drawable.profile_picture, // Default image
    val rating: Int = 0,
    val comment: String = "",
    val timestamp: String = "" // In a real app, this would be a Date/Time
)
