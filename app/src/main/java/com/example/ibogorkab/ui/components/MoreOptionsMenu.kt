package com.example.ibogorkab.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoreOptionsMenu(
    onShare: () -> Unit = {},
    onRecommend: () -> Unit = {},
    tint: Color = Color.Gray,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }

    // Define colors
    val DarkGreen = Color(0xFF0D7600)

    Box(modifier = modifier) {
        IconButton(onClick = { showMenu = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                Modifier.size(20.dp),
                tint = tint
            )
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier
                .background(Color.White)
                .width(200.dp)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Bagikan",
                        fontFamily = fontFamily,
                        fontSize = 14.sp
                    )
                },
                onClick = {
                    onShare()
                    showMenu = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Rekomendasikan",
                        fontFamily = fontFamily,
                        fontSize = 14.sp
                    )
                },
                onClick = {
                    onRecommend()
                    showMenu = false
                }

            )
        }
    }
}

@Composable
fun MoreOptionsMenuBorowwed(
    onShare: () -> Unit = {},
    onRecommend: () -> Unit = {},
    onReturn: () -> Unit = {},
    tint: Color = Color.Gray,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }


    Box(modifier = modifier) {
        IconButton(onClick = { showMenu = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                Modifier.size(20.dp),
                tint = tint
            )
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier
                .background(Color.White)
                .width(200.dp)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Kembalikan Buku",
                        fontFamily = fontFamily,
                        fontSize = 14.sp
                    )
                },
                onClick = {
                    onReturn()
                    showMenu = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Bagikan",
                        fontFamily = fontFamily,
                        fontSize = 14.sp
                    )
                },
                onClick = {
                    onShare()
                    showMenu = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Rekomendasikan",
                        fontFamily = fontFamily,
                        fontSize = 14.sp
                    )
                },
                onClick = {
                    onRecommend()
                    showMenu = false
                }

            )
        }
    }
}

@Composable
fun MoreOptionsMenuQueue(
    onShare: () -> Unit = {},
    onRecommend: () -> Unit = {},
    onLeave: () -> Unit = {},
    tint: Color = Color.Gray,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }

    // Define colors
    val DarkGreen = Color(0xFF0D7600)

    Box(modifier = modifier) {
        IconButton(onClick = { showMenu = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                Modifier.size(20.dp),
                tint = tint
            )
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier
                .background(Color.White)
                .width(200.dp)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Keluar Antrean",
                        fontFamily = fontFamily,
                        fontSize = 14.sp
                    )
                },
                onClick = {
                    onLeave()
                    showMenu = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Bagikan",
                        fontFamily = fontFamily,
                        fontSize = 14.sp
                    )
                },
                onClick = {
                    onShare()
                    showMenu = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Rekomendasikan",
                        fontFamily = fontFamily,
                        fontSize = 14.sp
                    )
                },
                onClick = {
                    onRecommend()
                    showMenu = false
                }

            )
        }
    }
}
