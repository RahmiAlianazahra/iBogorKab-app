package com.example.ibogorkab.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
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
    val DarkGreen = Color(0xFF0C6B00)
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
    val tabs = listOf("Obrolan", "Pengaturan")

    // Coroutine scope for animations and tab selection
    val coroutineScope = rememberCoroutineScope()

    // Pager state for horizontal swiping between tabs
    val pagerState = rememberPagerState(initialPage = 0) { tabs.size }

    // Selected navigation item state
    var selectedNavItem by remember { mutableIntStateOf(3) } // Profile is selected by default

    // Lazy column state to track scroll position
    val lazyListState = rememberLazyListState()

    // Determine if profile card should be visible based on scroll position
    val showProfileCard by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 &&
                    (lazyListState.firstVisibleItemScrollOffset < 200)
        }
    }

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
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(PaleGreen)
        ) {
            item {
                // Profile Header with gradient background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Green background with curved bottom
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp)
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
                    AnimatedVisibility(
                        visible = showProfileCard,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
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

                                    Spacer(modifier = Modifier.height(6.dp))

                                    // Bio
                                    Text(
                                        text = "Lost in pages, found in words",
                                        fontSize = 14.sp,
                                        color = Gray,
                                        fontFamily = interFontFamily,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(14.dp))

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

                                    Spacer(modifier = Modifier.height(14.dp))

                                    // Edit Profile Button
                                    Button(
                                        onClick = { onNavigateToEditProfile() },
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
                                            fontSize = 12.sp,
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

            item {
                Spacer(modifier = Modifier.height(24.dp))

                // Tab Row for switching between Chats and Settings
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = White,
                    contentColor = DarkGreen,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            height = 3.dp,
                            color = DarkGreen
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = title,
                                    fontFamily = interFontFamily,
                                    fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            selectedContentColor = DarkGreen,
                            unselectedContentColor = Gray
                        )
                    }
                }
            }

            item {
                // Horizontal Pager for swiping between tabs
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp)
                ) { page ->
                    when (page) {
                        0 -> ChatsTab(interFontFamily) { chatName -> onNavigateToChat(chatName) }
                        1 -> SettingsTab(interFontFamily, onLogout)
                    }
                }
            }
        }

        // Synchronize the pager with tab selection
        LaunchedEffect(pagerState.currentPage) {
            // Keep tab selection in sync with pager
            // This block intentionally left empty as the tabs are already updated in the TabRow
        }
    }
}

@Composable
fun ChatsTab(interFontFamily: FontFamily, onChatClick: (String) -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
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

            Divider(color = Color.LightGray, thickness = 1.dp)

            ChatItem(
                name = "Karina",
                message = "Sepertinya buku itu seru",
                time = "4 hari lalu",
                profilePic = R.drawable.karina,
                interFontFamily = interFontFamily,
                onClick = { onChatClick("Karina") }
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            ChatItem(
                name = "Ahmad",
                message = "Boleh tanya tentang buku terbaru?",
                time = "1 minggu lalu",
                profilePic = R.drawable.ahmad,
                interFontFamily = interFontFamily,
                onClick = { onChatClick("Ahmad") }
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            ChatItem(
                name = "Dinda",
                message = "Sudah selesai baca bukunya?",
                time = "2 minggu lalu",
                profilePic = R.drawable.dinda,
                interFontFamily = interFontFamily,
                onClick = { onChatClick("Dinda") }
            )
        }
    }
}

@Composable
fun SettingsTab(interFontFamily: FontFamily, onLogout: () -> Unit) {
    val DarkGreen = Color(0xFF0D7600)
    val Gray = Color(0xFF979797)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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
                    color = Color.Black
                )
            }
        }
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
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = interFontFamily
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = message,
                fontSize = 10.sp,
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