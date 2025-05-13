package com.example.ibogorkab.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ibogorkab.R


@Composable
fun JoinEPustakaDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    onJoin: () -> Unit
) {
    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val white = Color.White

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Animated visibility state for the dialog
    val visibilityState = remember {
        MutableTransitionState(false).apply { targetState = visible }
    }

    // Update the state when visible changes
    LaunchedEffect(visible) {
        visibilityState.targetState = visible
    }

    // Only show dialog when the state is relevant
    if (visibilityState.currentState || visibilityState.targetState) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(onClick = onDismiss),
                contentAlignment = Alignment.BottomCenter
            ) {
                AnimatedVisibility(
                    visibleState = visibilityState,
                    enter = fadeIn(animationSpec = tween(300)) +
                            slideInVertically(
                                animationSpec = tween(300),
                                initialOffsetY = { it }  // Slide in from the bottom
                            ),
                    exit = fadeOut(animationSpec = tween(300)) +
                            slideOutVertically(
                                animationSpec = tween(300),
                                targetOffsetY = { it }  // Slide out to the bottom
                            )
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                            )
                            .clickable(onClick = {}), // Prevent clicks from passing through
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                        colors = CardDefaults.cardColors(containerColor = white)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Divider line at top
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(4.dp)
                                    .background(Color.LightGray, RoundedCornerShape(2.dp))
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Title
                            Text(
                                text = "Gabung ePustaka",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkGreen,
                                fontFamily = interFontFamily
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // ePustaka logo and name
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Logo
                                    Image(
                                        painter = painterResource(id = R.drawable.dinas_arsip),
                                        contentDescription = "ePustaka Logo",
                                        modifier = Modifier.size(48.dp),
                                        contentScale = ContentScale.Fit
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    // Name
                                    Text(
                                        text = "Dinas Arsip dan\nPerpustakaan Daerah",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = interFontFamily
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Information text
                            Text(
                                text = "Anda belum terdaftar sebagai anggota ePustaka. Tekan tombol 'Gabung' untuk menjadi anggota dan meminjam koleksi di ePustaka.",
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = interFontFamily
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            // Buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Cancel button
                                OutlinedButton(
                                    onClick = onDismiss,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = DarkGreen
                                    )
                                ) {
                                    Text(
                                        text = "Batal",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = interFontFamily
                                    )
                                }

                                // Join button
                                Button(
                                    onClick = onJoin,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(50.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = DarkGreen,
                                        contentColor = white
                                    )
                                ) {
                                    Text(
                                        text = "Gabung",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
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
}