package com.example.ibogorkab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: String,
    val content: String,
    val isFromMe: Boolean,
    val timestamp: String,
    val senderName: String = ""
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatRoomScreen(
    chatName: String = "Ronaldo",
    onBackPressed: () -> Unit = {}
) {
    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)
    val White = Color.White
    val PaleGreen = Color(0xFFEFF4ED)
    val Gray = Color(0xFF979797)
    val MessageGreen = Color(0xFF006400)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Chat messages state
    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                id = "1",
                content = "Hey Rahmi!",
                isFromMe = false,
                timestamp = "11:30",
                senderName = "Ronaldo"
            ),
            ChatMessage(
                id = "2",
                content = "Buku apa yang seru menurutmu?",
                isFromMe = false,
                timestamp = "11:31",
                senderName = "Ronaldo"
            ),
            ChatMessage(
                id = "3",
                content = "Hi Ronaldo!",
                isFromMe = true,
                timestamp = "11:32",
                senderName = "Rahmialz"
            ),
            ChatMessage(
                id = "4",
                content = "Coba baca buku 'Tentang Kamu'. Seru!",
                isFromMe = true,
                timestamp = "11:33",
                senderName = "Rahmialz"
            ),
            ChatMessage(
                id = "5",
                content = "Terima kasih atas rekomendasimu ya!",
                isFromMe = false,
                timestamp = "11:35",
                senderName = "Ronaldo"
            )
        )
    }

    // New message state
    var messageText by remember { mutableStateOf("") }

    // Keyboard controller
    val keyboardController = LocalSoftwareKeyboardController.current

    // Lazy list state for auto-scrolling to bottom
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll to bottom when messages change
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    // Function to send a new message
    fun sendMessage() {
        if (messageText.isNotBlank()) {
            val newMessage = ChatMessage(
                id = (messages.size + 1).toString(),
                content = messageText,
                isFromMe = true,
                timestamp = "Now",
                senderName = "Rahmialz"
            )
            messages.add(newMessage)
            messageText = ""
            keyboardController?.hide()

            // Auto-scroll to bottom
            coroutineScope.launch {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ronaldo),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = chatName,
                            fontFamily = interFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = DarkGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = PaleGreen,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Message input field
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 48.dp),
                    placeholder = {
                        Text("Type a message...", color = Gray, fontFamily = interFontFamily)
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = LightGreen,
                        focusedBorderColor = DarkGreen,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    maxLines = 4,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = { sendMessage() }),
                    trailingIcon = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Send button
                IconButton(
                    onClick = { sendMessage() },
                    modifier = Modifier
                        .size(48.dp)
                        .background(DarkGreen, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = listState
        ) {
            // Date header
            item {
                Text(
                    text = "Today",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center,
                    fontFamily = interFontFamily,
                    color = Gray,
                    fontSize = 12.sp
                )
            }

            // Messages
            items(messages) { message ->
                ChatMessageItem(
                    message = message,
                    interFontFamily = interFontFamily
                )
            }

            // Extra space at bottom for better UX
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ChatMessageItem(
    message: ChatMessage,
    interFontFamily: FontFamily
) {
    val DarkGreen = Color(0xFF006400)
    val LightGreen = Color(0xFF50AD42)
    val White = Color.White
    val PaleGreen = Color(0xFFEAFFE5)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.isFromMe) Arrangement.End else Arrangement.Start
    ) {
        // Display the message in a card with appropriate styling
        Card(
            shape = RoundedCornerShape(
                topStart = if (message.isFromMe) 16.dp else 4.dp,
                topEnd = if (message.isFromMe) 4.dp else 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isFromMe) DarkGreen else White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                // Display sender name for group chats (optional)
                if (!message.isFromMe && message.senderName.isNotEmpty()) {
                    Text(
                        text = message.senderName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightGreen,
                        fontFamily = interFontFamily
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                }

                // Message content
                Text(
                    text = message.content,
                    color = if (message.isFromMe) White else Color.Black,
                    fontFamily = interFontFamily,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Timestamp
                Text(
                    text = message.timestamp,
                    fontSize = 10.sp,
                    color = if (message.isFromMe) Color.LightGray else Color.Gray,
                    modifier = Modifier.align(Alignment.End),
                    fontFamily = interFontFamily
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatRoomScreenPreview() {
    ChatRoomScreen()
}