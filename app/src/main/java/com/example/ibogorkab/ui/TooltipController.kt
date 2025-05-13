package com.example.ibogorkab.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Enum to track which tooltip we're showing
enum class TooltipStep {
    BERANDA,
    AKTIVITAS,
    RAK_BUKU,
    PROFIL,
    COMPLETED
}

/**
 * Main TooltipController that manages the tooltip state and navigation
 */
@Composable
fun TooltipController(
    show: Boolean,
    onComplete: () -> Unit,
    interFontFamily: FontFamily, // Pass in the font family
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    // Current tooltip step
    var currentStep by remember { mutableStateOf(TooltipStep.BERANDA) }

    // Only show tooltips if the show flag is true and we haven't completed the tour
    val showTooltip = remember { mutableStateOf(show && currentStep != TooltipStep.COMPLETED) }

    // Overlay that darkens the background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(if (showTooltip.value) 0.7f else 0f)
            .background(Color.Black)
            .clickable(enabled = showTooltip.value) {
                // Clicking outside advances to the next tooltip
                advanceToNextStep(currentStep) { nextStep ->
                    currentStep = nextStep
                    if (nextStep == TooltipStep.COMPLETED) {
                        showTooltip.value = false
                        onComplete()
                    }
                }
            }
            .zIndex(if (showTooltip.value) 10f else -1f)
    )

    // Show the appropriate tooltip based on the current step
    when (currentStep) {
        TooltipStep.BERANDA -> {
            TooltipCard(
                title = "Beranda",
                description = "Temukan rekomendasi dan buku populer favoritmu disini!",
                steps = listOf(
                    "Pilih buku yang kamu suka",
                    "Gabung ePustaka penyedia buku",
                    "Mulai baca buku selama 3 hari"
                ),
                showSteps = true,
                buttonText = "Selanjutnya",
                onButtonClick = {
                    advanceToNextStep(currentStep) { nextStep ->
                        currentStep = nextStep
                    }
                },
                onSkipClick = {
                    currentStep = TooltipStep.COMPLETED
                    showTooltip.value = false
                    onComplete()
                },
                visible = showTooltip.value && currentStep == TooltipStep.BERANDA,
                fontFamily = interFontFamily,
                navIcon = "home",
                navText = "Beranda"
            )
        }
        TooltipStep.AKTIVITAS -> {
            TooltipCard(
                title = "Aktivitas",
                description = "Berinteraksi dengan sesama pengguna disini!\n\nBerikan suka dan komentar untuk saling terhubung.",
                buttonText = "Selanjutnya",
                onButtonClick = {
                    advanceToNextStep(currentStep) { nextStep ->
                        currentStep = nextStep
                    }
                },
                onSkipClick = {
                    currentStep = TooltipStep.COMPLETED
                    showTooltip.value = false
                    onComplete()
                },
                visible = showTooltip.value && currentStep == TooltipStep.AKTIVITAS,
                fontFamily = interFontFamily,
                navIcon = "activity",
                navText = "Aktivitas"
            )
        }
        TooltipStep.RAK_BUKU -> {
            TooltipCard(
                title = "Rak Buku",
                description = "Semuanya tersimpan disini!\n\nLihat pinjaman, antrian, dan riwayat buku bacaanmu dalam rak buku.",
                buttonText = "Selanjutnya",
                onButtonClick = {
                    advanceToNextStep(currentStep) { nextStep ->
                        currentStep = nextStep
                    }
                },
                onSkipClick = {
                    currentStep = TooltipStep.COMPLETED
                    showTooltip.value = false
                    onComplete()
                },
                visible = showTooltip.value && currentStep == TooltipStep.RAK_BUKU,
                fontFamily = interFontFamily,
                navIcon = "book",
                navText = "Rak Buku"
            )
        }
        TooltipStep.PROFIL -> {
            TooltipCard(
                title = "Profil",
                description = "Obrolan dan pengaturan ada disini!\n\nAtur profil, bahasa, dan yang lainnya. Kami siap membantumu.",
                buttonText = "Selesai",
                onButtonClick = {
                    currentStep = TooltipStep.COMPLETED
                    showTooltip.value = false
                    onComplete()
                },
                onSkipClick = {
                    currentStep = TooltipStep.COMPLETED
                    showTooltip.value = false
                    onComplete()
                },
                visible = showTooltip.value && currentStep == TooltipStep.PROFIL,
                fontFamily = interFontFamily,
                navIcon = "profile",
                navText = "Profil"
            )
        }
        TooltipStep.COMPLETED -> {
            // Do nothing when completed
        }
    }

    // Auto-proceed through tooltips after delay if user doesn't interact
    LaunchedEffect(currentStep, showTooltip.value) {
        if (showTooltip.value && currentStep != TooltipStep.COMPLETED) {
            delay(10000) // Wait 10 seconds before auto-advancing
            advanceToNextStep(currentStep) { nextStep ->
                currentStep = nextStep
                if (nextStep == TooltipStep.COMPLETED) {
                    showTooltip.value = false
                    onComplete()
                }
            }
        }
    }

    // Clean up when the component is removed from composition
    DisposableEffect(Unit) {
        onDispose {
            // Any cleanup needed when tooltips are dismissed
        }
    }
}

/**
 * Helper function to advance to the next tooltip step
 */
private fun advanceToNextStep(
    currentStep: TooltipStep,
    onStepChange: (TooltipStep) -> Unit
) {
    val nextStep = when (currentStep) {
        TooltipStep.BERANDA -> TooltipStep.AKTIVITAS
        TooltipStep.AKTIVITAS -> TooltipStep.RAK_BUKU
        TooltipStep.RAK_BUKU -> TooltipStep.PROFIL
        TooltipStep.PROFIL -> TooltipStep.COMPLETED
        TooltipStep.COMPLETED -> TooltipStep.COMPLETED
    }
    onStepChange(nextStep)
}

/**
 * Tooltip card UI component that exactly matches the reference screenshots
 */
@Composable
fun TooltipCard(
    title: String,
    description: String,
    steps: List<String> = emptyList(),
    showSteps: Boolean = false,
    buttonText: String = "Selanjutnya",
    onButtonClick: () -> Unit = {},
    onSkipClick: () -> Unit = {},
    visible: Boolean = true,
    fontFamily: FontFamily,
    navIcon: String = "", // Icon name for navbar indication
    navText: String = ""  // Text for navbar indication
) {
    // Colors exactly as they appear in screenshots
    val darkGreen = Color(0xFF1B5E20) // Dark green for card background
    val lightGreen = Color(0xFF4CAF50) // Light green for button
    val white = Color.White

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300)),
        modifier = Modifier
            .fillMaxSize()
            .zIndex(20f)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Main tooltip card - centered in screen
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(darkGreen)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = title,
                    color = white,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Description
                Text(
                    text = description,
                    color = white,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    fontFamily = fontFamily,
                    modifier = Modifier.padding(bottom = if (showSteps) 12.dp else 24.dp)
                )

                // Numbered steps (for Beranda tooltip)
                if (showSteps) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Cara meminjam buku:",
                            color = white,
                            fontSize = 14.sp,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        steps.forEachIndexed { index, step ->
                            Row(
                                modifier = Modifier.padding(bottom = 4.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "${index + 1}.",
                                    color = white,
                                    fontSize = 14.sp,
                                    fontFamily = fontFamily,
                                    modifier = Modifier.padding(end = 4.dp)
                                )
                                Text(
                                    text = step,
                                    color = white,
                                    fontSize = 14.sp,
                                    fontFamily = fontFamily
                                )
                            }
                        }
                    }
                }

                // Button row with Lewati and Selanjutnya buttons - matching exact position in screenshots
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Skip text
                    Text(
                        text = "Lewati",
                        color = white,
                        fontSize = 12.sp,
                        fontFamily = fontFamily,
                        modifier = Modifier
                            .clickable { onSkipClick() }
                            .padding(8.dp)
                    )

                    // Main action button with green background
                    Button(
                        onClick = onButtonClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = lightGreen,
                            contentColor = white
                        ),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = buttonText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }

            // Position the arrow and navigation icon at the bottom, correctly aligned
            // with the corresponding navigation item as shown in the Figma design
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Position adjustments based on which navigation item is targeted
                val horizontalOffset = when(navText) {
                    "Beranda" -> -130.dp  // Far left
                    "Aktivitas" -> -45.dp // Left-center
                    "Rak Buku" -> 45.dp   // Right-center
                    "Profil" -> 145.dp    // Far right
                    else -> 0.dp
                }

                Column(
                    modifier = Modifier
                        .offset(x = horizontalOffset, y = 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Down arrow
                    Text(
                        text = "↓",
                        color = white,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // Nav icon box - matching screenshots exactly
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(white)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // We would use an actual icon here in a real app
                        // but for simplicity we're just using text
                        if (navIcon.isNotEmpty()) {
                            Text(
                                text = navText,
                                color = darkGreen,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = fontFamily
                            )
                        }
                    }
                }
            }
        }
    }
}