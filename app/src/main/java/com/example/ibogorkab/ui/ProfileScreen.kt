package com.example.ibogorkab.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ibogorkab.R
import com.example.ibogorkab.ui.theme.PaleGreen

@Composable
fun ProfileScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToActivity: () -> Unit = {},
    onNavigateToBookshelf: () -> Unit = {},
    onNavigateToChat: (String) -> Unit = {},
    onNavigateToEditProfile: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    // Define colors
    val DarkGreen = Color(0xFF0D7600)
    val LightGreen = Color(0xFF50AD42)
    val White = Color.White
    val Black = Color.Black
    val Gray = Color(0xFF979797)
    val LightGray = Color(0xFFE0E0E0)

    // Define Inter font family
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Tab selection state
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Obrolan", "Pengaturan")

    // Selected navigation item state
    var selectedNavItem by remember { mutableIntStateOf(3) } // Profile is selected by default

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = White
            ) {
                NavigationBarItem(
                    selected = selectedNavItem == 0,
                    onClick = {
                        selectedNavItem = 0
                        onNavigateToHome()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "Beranda"
                        )
                    },
                    label = {
                        Text(
                            "Beranda",
                            fontFamily = interFontFamily
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkGreen,
                        selectedTextColor = DarkGreen,
                        indicatorColor = White
                    )
                )

                NavigationBarItem(
                    selected = selectedNavItem == 1,
                    onClick = {
                        selectedNavItem = 1
                        onNavigateToActivity()
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_more_time_24),
                            contentDescription = "Aktivitas"
                        )
                    },
                    label = {
                        Text(
                            "Aktivitas",
                            fontFamily = interFontFamily
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkGreen,
                        selectedTextColor = DarkGreen,
                        indicatorColor = White
                    )
                )

                NavigationBarItem(
                    selected = selectedNavItem == 2,
                    onClick = {
                        selectedNavItem = 2
                        onNavigateToBookshelf()
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = "Rak Buku"
                        )
                    },
                    label = {
                        Text(
                            "Rak Buku",
                            fontFamily = interFontFamily
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkGreen,
                        selectedTextColor = DarkGreen,
                        indicatorColor = White
                    )
                )

                NavigationBarItem(
                    selected = selectedNavItem == 3,
                    onClick = { selectedNavItem = 3 },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Profil"
                        )
                    },
                    label = {
                        Text(
                            "Profil",
                            fontFamily = interFontFamily
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkGreen,
                        selectedTextColor = DarkGreen,
                        indicatorColor = White
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PaleGreen)
        ) {
            // Profile Header with gradient background - only covering half of card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Green background with curved bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp) // Only covers half of the card
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    DarkGreen,
                                    LightGreen
                                )
                            ),
                            shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                        )
                )

                // Profile card placed over the background
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Card
                    Card(
                        modifier = Modifier
                            .width(320.dp)
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Profile Image
                            Image(
                                painter = painterResource(id = R.drawable.profile_picture),
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(LightGray),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Username
                            Text(
                                text = "Rahmialz",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = interFontFamily
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Bio
                            Text(
                                text = "Lost in pages, found in words",
                                fontSize = 14.sp,
                                color = Gray,
                                fontFamily = interFontFamily,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Stats: Following and Followers
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                // Following
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                ) {
                                    Text(
                                        text = "100",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = interFontFamily
                                    )
                                    Text(
                                        text = "Following",
                                        fontSize = 12.sp,
                                        color = Gray,
                                        fontFamily = interFontFamily
                                    )
                                }

                                // Followers
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                ) {
                                    Text(
                                        text = "358",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = interFontFamily
                                    )
                                    Text(
                                        text = "Followers",
                                        fontSize = 12.sp,
                                        color = Gray,
                                        fontFamily = interFontFamily
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Edit Profile Button
                            Button(
                                onClick = { onNavigateToEditProfile () },
                                modifier = Modifier
                                    .width(128.dp)
                                    .height(38.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DarkGreen,
                                    contentColor = White
                                )
                            ) {
                                Text(
                                    text = "Ubah Profil",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = interFontFamily
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tab Row for switching between Chats and Settings
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = White,
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
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selectedContentColor = DarkGreen,
                        unselectedContentColor = Gray
                    )
                }
            }

            // Content based on selected tab
            when (selectedTabIndex) {
                0 -> ChatsList(interFontFamily) { chatName -> onNavigateToChat(chatName) }
                1 -> SettingsList(interFontFamily, onLogout)
            }
        }
    }
}


@Composable
fun ChatsList(interFontFamily: FontFamily, onChatClick: (String) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(12.dp)
    ) {
        // Chat items with click handler
        ChatItem(
            name = "Salsabila",
            message = "Halo! Apa buku favoritmu?",
            time = "Kemarin",
            profilePic = R.drawable.salsabila,
            interFontFamily = interFontFamily,
            onClick = { onChatClick("Salsabila") }
        )

        Divider(color = Color.LightGray, thickness = 1.dp)

        ChatItem(
            name = "Ronaldo",
            message = "Terima kasih atas rekomendasimu ya!",
            time = "2 hari lalu",
            profilePic = R.drawable.ronaldo,
            interFontFamily = interFontFamily,
            onClick = { onChatClick("Ronaldo") }
        )
    }
}

@Composable
fun ChatItem(
    name: String,
    message: String,
    time: String,
    profilePic: Int,
    interFontFamily: FontFamily,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile picture
        Image(
            painter = painterResource(id = profilePic),
            contentDescription = "Chat Profile Picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Name and message
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = interFontFamily
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = message,
                fontSize = 12.sp,
                color = Color.Gray,
                fontFamily = interFontFamily
            )
        }

        // Time
        Text(
            text = time,
            fontSize = 10.sp,
            color = Color.Gray,
            fontFamily = interFontFamily
        )
    }
}

@Composable
fun SettingsList(interFontFamily: FontFamily, onLogout: () -> Unit) {
    val DarkGreen = Color(0xFF0D7600)
    val Gray = Color(0xFF979797)
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // Settings items
        SettingsItem(
            title = "Password",
            icon = R.drawable.baseline_lock_outline_24,
            interFontFamily = interFontFamily
        )

        Divider(color = Color.LightGray, thickness = 1.dp)

        SettingsItem(
            title = "Catatan Pribadi",
            icon = R.drawable.baseline_notes_24,
            interFontFamily = interFontFamily
        )

        Divider(color = Color.LightGray, thickness = 1.dp)

        SettingsItem(
            title = "Kebijakan & Privasi",
            icon = R.drawable.baseline_policy_24,
            interFontFamily = interFontFamily
        )

        Divider(color = Color.LightGray, thickness = 1.dp)

        SettingsItem(
            title = "Bantuan",
            icon = R.drawable.baseline_help_outline_24,
            interFontFamily = interFontFamily
        )


        Divider(color = Color.LightGray, thickness = 1.dp)

        // Logout button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clickable { onLogout() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_logout_24),
                contentDescription = "Logout",
                tint = DarkGreen,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Logout",
                fontSize = 12.sp,
                fontFamily = interFontFamily,
                color = Black
            )
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    icon: Int,
    interFontFamily: FontFamily
) {
    val DarkGreen = Color(0xFF0D7600)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable { /* Open setting */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = DarkGreen,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 12.sp,
            fontFamily = interFontFamily,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Open",
            tint = Color.Gray
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}