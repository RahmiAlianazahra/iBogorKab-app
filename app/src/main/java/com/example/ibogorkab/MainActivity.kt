package com.example.ibogorkab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ibogorkab.ui.ActivityScreen
import com.example.ibogorkab.ui.BookDetailScreen
import com.example.ibogorkab.ui.BookshelfScreen
import com.example.ibogorkab.ui.ChatRoomScreen
import com.example.ibogorkab.ui.EPustakaData
import com.example.ibogorkab.ui.EPustakaDetailScreen
import com.example.ibogorkab.ui.EPustakaScreen
import com.example.ibogorkab.ui.EReaderScreen
import com.example.ibogorkab.ui.EditProfileScreen
import com.example.ibogorkab.ui.FilterOptions
import com.example.ibogorkab.ui.FilterScreen
import com.example.ibogorkab.ui.HomeScreen
import com.example.ibogorkab.ui.KoleksiBukuScreen
import com.example.ibogorkab.ui.LoginScreen
import com.example.ibogorkab.ui.NotificationScreen
import com.example.ibogorkab.ui.OnboardingScreen
import com.example.ibogorkab.ui.ProfileScreen
import com.example.ibogorkab.ui.RatingReviewScreen
import com.example.ibogorkab.ui.RegisterScreen
import com.example.ibogorkab.ui.SearchResultScreen
import com.example.ibogorkab.ui.SearchScreen
import com.example.ibogorkab.ui.SplashScreen
import com.example.ibogorkab.ui.TooltipController
import com.example.ibogorkab.ui.VerificationScreen
import com.example.ibogorkab.ui.theme.IBogorKabTheme
import kotlinx.coroutines.delay
import java.net.URLEncoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IBogorKabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Define Inter font family - for tooltips
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_bold, FontWeight.Bold)
    )

    // Common filter state to be shared between search and filter screens
    val filterOptions = rememberSaveable { mutableStateOf<FilterOptions?>(null) }

    // State to track if user has just logged in (to show tooltips)
    var justLoggedIn by rememberSaveable { mutableStateOf(false) }

    // State to control the tooltips visibility
    var showTooltips by rememberSaveable { mutableStateOf(false) }

    // Define a preference key for first login (in a real app, use DataStore)
    val isFirstLogin = rememberSaveable { mutableStateOf(true) }

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate("onboarding") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("onboarding") {
            OnboardingScreen(
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        composable("login") {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onSuccessfulLogin = {
                    // Set the flag to show tooltips after successful login
                    justLoggedIn = isFirstLogin.value

                    // Navigate to home
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToVerification = {
                    // Navigate to verification screen after registration
                    navController.navigate("verification") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        composable("verification") {
            VerificationScreen(
                onNavigateToLogin = {
                    // Navigate to login screen
                    navController.navigate("login") {
                        // Clear the back stack so user can't go back to verification
                        popUpTo("verification") { inclusive = true }
                    }
                },
                onResendEmail = {
                    // Logic to resend verification email
                    // This could be implemented with your authentication service
                }
            )
        }

        // Home Screen Navigation
        composable("home") {
            LaunchedEffect(justLoggedIn) {
                if (justLoggedIn) {
                    delay(5000) // Show home screen for 5 seconds
                    showTooltips = true
                    justLoggedIn = false
                    isFirstLogin.value = false // Mark that first login is complete
                }
            }

            HomeScreen(
                onNavigateToCollection = {
                    navController.navigate("collection")
                },
                onNavigateToEPustaka = {
                    navController.navigate("epustaka")
                },
                onNavigateToNotifications = {
                    navController.navigate("notification")
                },
                onNavigateToSearch = {
                    navController.navigate("search")
                },
                onBookClicked = { bookId ->
                    navController.navigate("book_details/$bookId")
                },
                onNavigateToActivity = {
                    navController.navigate("activity") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onNavigateToBookshelf = {
                    navController.navigate("bookshelf"){
                        popUpTo("home") {inclusive = true}
                    }
                },
                onNavigateToProfile = {
                    navController.navigate("profile") {
                        popUpTo("rak_buku") { inclusive = true }
                    }
                }
            )

            if (showTooltips) {
                TooltipController(
                    show = true,
                    onComplete = { showTooltips = false },
                    interFontFamily = interFontFamily
                )
            }
        }

        // Collection Screen Navigation
        composable("collection") {
            KoleksiBukuScreen(
                onBackPressed = { navController.navigateUp() },
                onSearchPressed = { navController.navigate("search") },
                onFilterPressed = { navController.navigate("filter") },
                onBookClicked = { book ->
                    navController.navigate("book_details/${book.id}")
                }
            )
        }

        // EPustaka Screen Navigation
        composable("epustaka") {
            EPustakaScreen(
                onBackPressed = { navController.navigateUp() },
                onSearchPressed = { navController.navigate("search") },
                onEPustakaClicked = { ePustaka ->
                    navController.navigate("epustaka_detail/${ePustaka.id}")
                }
            )
        }

        // EPustaka Detail Screen
        composable(
            route = "epustaka_detail/{ePustakaId}",
            arguments = listOf(navArgument("ePustakaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val ePustakaId = backStackEntry.arguments?.getInt("ePustakaId") ?: 1
            EPustakaDetailScreen(
                ePustakaId = ePustakaId,
                onBackPressed = { navController.navigateUp() },
                onSearchPressed = { navController.navigate("search") },
                onCollectionItemClicked = { item ->
                    // Navigate to book details or reader when collection item is clicked
                    navController.navigate("book_details/${item.id}")
                }
            )
        }

        // Notification Screen
        composable("notification") {
            NotificationScreen(
                onBackPressed = { navController.navigateUp() }
            )
        }

        // Search Screen
        composable("search") {
            SearchScreen(
                onBackPressed = { navController.navigateUp() },
                onFilterPressed = {
                    navController.navigate("filter")
                }
            )
        }

        // Filter Screen
        composable("filter") {
            FilterScreen(
                onBackPressed = { navController.navigateUp() },
                onApplyFilter = { options ->
                    filterOptions.value = options
                    // Navigate to search results screen instead of going back
                    navController.navigate("search_results") {
                        popUpTo("filter") { inclusive = true }
                    }
                }
            )
        }

        // Search Results Screen
        composable("search_results") {
            SearchResultScreen(
                filterOptions = filterOptions.value,
                onBackPressed = {
                    navController.navigateUp()
                },
                onFilterPressed = {
                    navController.navigate("filter")
                },
                onBookClicked = { book ->
                }
            )
        }

        // Book Details Screen
        composable(
            route = "book_details/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 1
            BookDetailScreen(
                bookId = bookId,
                onBackPressed = { navController.navigateUp() },
                onBorrowBook = {
                },
                onReadBook = {
                    val bookTitle = "Edensor"
                    val encodedTitle = URLEncoder.encode(bookTitle, "UTF-8")

                    // Navigate to eReader screen
                    navController.navigate("ereader/$bookId/$encodedTitle")
                },
                onNavigateToReview = { id, title, author ->
                    navController.navigate("rating_review/$id/$title/$author")
                }
            )
        }
        composable(
            route = "rating_review/{bookId}/{bookTitle}/{bookAuthor}",
            arguments = listOf(
                navArgument("bookId") { type = NavType.IntType },
                navArgument("bookTitle") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("bookAuthor") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 1
            val bookTitle = backStackEntry.arguments?.getString("bookTitle") ?: "Unknown"
            val bookAuthor = backStackEntry.arguments?.getString("bookAuthor") ?: "Unknown"

            // Decode URL-encoded title and author
            val decodedTitle = java.net.URLDecoder.decode(bookTitle, "UTF-8")
            val decodedAuthor = java.net.URLDecoder.decode(bookAuthor, "UTF-8")

            RatingReviewScreen(
                bookId = bookId,
                bookTitle = decodedTitle,
                bookAuthor = decodedAuthor,
                bookCoverResId = R.drawable.edensor, // You might want to pass this as a parameter too
                onBackPressed = { navController.navigateUp() },
                onSubmitReview = { id, rating, review ->
                    // Handle the submitted review (e.g., save to database)
                    // Then navigate back
                    navController.navigateUp()
                }
            )
        }


        // EReader Screen
        composable(
            route = "ereader/{bookId}/{bookTitle}",
            arguments = listOf(
                navArgument("bookId") { type = NavType.IntType },
                navArgument("bookTitle") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 1
            val bookTitle = backStackEntry.arguments?.getString("bookTitle") ?: "Untitled"
            val decodedTitle = java.net.URLDecoder.decode(bookTitle, "UTF-8")

            EReaderScreen(
                bookId = bookId,
                bookTitle = decodedTitle,
                chapterTitle = "Bab 1 dari 20", // This could be dynamic based on book data
                onBackPressed = { navController.navigateUp() }
            )
        }

        composable("activity") {
            ActivityScreen(
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("activity") { inclusive = true }
                    }
                },
                onNavigateToBookshelf = {
                    navController.navigate("bookshelf") {
                        popUpTo("activity") { inclusive = true }
                    }
                },
                onNavigateToProfile = {
                    navController.navigate("profile") {
                        popUpTo("activity") { inclusive = true }
                    }
                }
            )
        }

        composable("bookshelf") {
            BookshelfScreen(
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("bookshelf") { inclusive = true }
                    }
                },
                onNavigateToActivity = {
                    navController.navigate("activity") {
                        popUpTo("bookshelf") { inclusive = true }
                    }
                },
                onNavigateToProfile = {
                    navController.navigate("profile") {
                        popUpTo("rak_buku") { inclusive = true }
                    }
                },
                onNavigateToBookDetail = { bookId ->
                    navController.navigate("book_details/$bookId")
                }

            )
        }

        // Profile Screen
        composable("profile") {
            ProfileScreen(
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("profile") { inclusive = true }
                    }
                },
                onNavigateToActivity = {
                    navController.navigate("activity") {
                        popUpTo("profile") { inclusive = true }
                    }
                },
                onNavigateToBookshelf = {
                    navController.navigate("bookshelf") {
                        popUpTo("profile") { inclusive = true }
                    }
                },
                onNavigateToChat = { chatName ->
                    // URL encode the chat name for safe navigation
                    val encodedName = java.net.URLEncoder.encode(chatName, "UTF-8")
                    navController.navigate("chat/$encodedName")
                },
                onNavigateToEditProfile = {
                    navController.navigate("edit_profile") {
                        popUpTo("profile") {inclusive = true}
                    }
                },
                onLogout = {
                    // Navigate to login screen when user logs out
                    navController.navigate("login") {
                        popUpTo("profile") { inclusive = true }
                    }
                }
            )
        }

        composable("chat/{chatName}") { backStackEntry ->
            val chatName = backStackEntry.arguments?.getString("chatName") ?: "Chat"
            ChatRoomScreen(
                chatName = chatName,
                onBackPressed = { navController.navigateUp() }
            )
        }

        composable("edit_profile") {
            EditProfileScreen(
                onBackPressed = { navController.navigateUp() },
                onSaveProfile = { name, email, phone, bio ->
                    // Save profile data to your data source
                    // Then navigate back
                    navController.navigateUp()
                }
            )
        }
    }
}