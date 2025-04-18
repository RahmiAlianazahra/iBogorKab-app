package com.example.ibogorkab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R

@Composable
fun VerificationScreen(
    onNavigateToLogin: () -> Unit,
    onResendEmail: () -> Unit
) {
    // Define exact colors from the design
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)
    val Yellow = Color(0xFFCCE600) // Approximation of the yellow at the bottom
    val white = Color.White

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Gradient background for the entire screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DarkGreen,
                        LightGreen,
                        Yellow
                    )
                )
            )
    ) {
        // White rounded card for content
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = white)
        ) {
            // Main content column with centered alignment
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Email verification image
                    Image(
                        painter = painterResource(id = R.drawable.emailverification),
                        contentDescription = "Email Verification",
                        modifier = Modifier.size(150.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Heading
                    Text(
                        text = "Email Verifikasi Terkirim!",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkGreen,
                            fontFamily = interFontFamily,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Instruction text in a bordered box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append("Silakan periksa kotak masuk Anda, lalu klik tombol ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Verifikasi Email")
                                }
                                append(" pada email yang Anda terima.")
                            },
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.DarkGray,
                                fontFamily = interFontFamily,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Gmail button that navigates to login
                    Button(
                        onClick = { onNavigateToLogin() },
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(40.dp)
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(4.dp)
                            ),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = white
                        )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gmail_icon),
                            contentDescription = "Gmail icon",
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Buka Gmail",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black,
                                fontFamily = interFontFamily
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(80.dp))

                    // Resend email section with border
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // "Belum menerima email?" text in border
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Belum menerima email?",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.DarkGray,
                                    fontFamily = interFontFamily
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // "Kirim Ulang" button with border
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = DarkGreen,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable { onResendEmail() }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Kirim Ulang",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkGreen,
                                    fontFamily = interFontFamily
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerificationScreenPreview() {
    VerificationScreen(
        onNavigateToLogin = { /* TODO */ },
        onResendEmail = { /* TODO */ }
    )
}