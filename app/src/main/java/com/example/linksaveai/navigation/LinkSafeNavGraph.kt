package com.example.linksaveai.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.linksaveai.ui.components.LinkSafeBottomBar
import com.example.linksaveai.ui.screens.*
import com.example.linksaveai.viewmodel.LinkSafeViewModel

@Composable
fun LinkSafeNavGraph() {
    val navController = rememberNavController()
    val viewModel: LinkSafeViewModel = viewModel()

    val history by viewModel.scanHistory.collectAsState()
    val savedLinks by viewModel.savedLinks.collectAsState()
    val selectedResult by viewModel.selectedScanResult.collectAsState()
    val currentUrl by viewModel.currentUrl.collectAsState()

    val navigateToBottomTab: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(Screen.Home.route) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToWelcome = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onGetStarted = { navController.navigate(Screen.HowItWorks.route) },
                onLogin = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.HowItWorks.route) {
            HowItWorksScreen(
                onNext = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) }
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Screen.Home.route) {
            Scaffold(
                bottomBar = { LinkSafeBottomBar(currentRoute = Screen.Home.route, onNavigate = navigateToBottomTab) }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    HomeScreen(
                        history = history,
                        onScanUrlClicked = { url ->
                            viewModel.updateCurrentUrl(url)
                            viewModel.scanUrl(url) { item ->
                                val route = if (item.status == "Safe") Screen.SafeResult.route else Screen.SuspiciousResult.route
                                navController.navigate(route)
                            }
                        },
                        onNavigateToScanner = { navController.navigate(Screen.Scanner.route) },
                        onNavigateToHistory = { navController.navigate(Screen.ScanHistory.route) },
                        onNavigateToSaved = { navController.navigate(Screen.SavedLinks.route) },
                        onNavigateToTips = { navController.navigate(Screen.SecurityTips.route) },
                        onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                        onItemClick = { item ->
                            val route = if (item.status == "Safe") Screen.SafeResult.route else Screen.SuspiciousResult.route
                            navController.navigate(route)
                        }
                    )
                }
            }
        }
        composable(Screen.Scanner.route) {
            Scaffold(
                bottomBar = { LinkSafeBottomBar(currentRoute = Screen.Scanner.route, onNavigate = navigateToBottomTab) }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    UrlScannerScreen(
                        onBack = { navController.popBackStack() },
                        onStartScan = { url ->
                            viewModel.updateCurrentUrl(url)
                            navController.navigate(Screen.ScanningProgress.route)
                        }
                    )
                }
            }
        }
        composable(Screen.ScanningProgress.route) {
            ScanningProgressScreen(
                url = currentUrl,
                onScanFinished = {
                    viewModel.scanUrl(currentUrl) { item ->
                        val route = if (item.status == "Safe") Screen.SafeResult.route else Screen.SuspiciousResult.route
                        navController.navigate(route) {
                            popUpTo(Screen.ScanningProgress.route) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(Screen.SafeResult.route) {
            selectedResult?.let { item ->
                SafeResultScreen(
                    linkItem = item,
                    onToggleSave = { viewModel.toggleSaveLink(it) },
                    onViewDetails = { navController.navigate(Screen.ScanDetails.route) },
                    onHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                )
            }
        }
        composable(Screen.SuspiciousResult.route) {
            selectedResult?.let { item ->
                SuspiciousResultScreen(
                    linkItem = item,
                    onToggleSave = { viewModel.toggleSaveLink(it) },
                    onViewDetails = { navController.navigate(Screen.ScanDetails.route) },
                    onHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                )
            }
        }
        composable(Screen.ScanDetails.route) {
            selectedResult?.let { item ->
                ScanDetailsScreen(
                    linkItem = item,
                    onBack = { navController.popBackStack() }
                )
            }
        }
        composable(Screen.ScanHistory.route) {
            Scaffold(
                bottomBar = { LinkSafeBottomBar(currentRoute = Screen.ScanHistory.route, onNavigate = navigateToBottomTab) }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    ScanHistoryScreen(
                        history = history,
                        onBack = { navController.popBackStack() },
                        onClearHistory = { viewModel.clearHistory() },
                        onItemClick = { item ->
                            navController.navigate(Screen.HistoryDetails.route)
                        }
                    )
                }
            }
        }
        composable(Screen.SavedLinks.route) {
            Scaffold(
                bottomBar = { LinkSafeBottomBar(currentRoute = Screen.SavedLinks.route, onNavigate = navigateToBottomTab) }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    SavedLinksScreen(
                        savedLinks = savedLinks,
                        onBack = { navController.popBackStack() },
                        onItemClick = { item ->
                            navController.navigate(Screen.SavedLinkDetails.route)
                        }
                    )
                }
            }
        }
        composable(Screen.SecurityTips.route) {
            SecurityTipsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Settings.route) {
            Scaffold(
                bottomBar = { LinkSafeBottomBar(currentRoute = Screen.Settings.route, onNavigate = navigateToBottomTab) }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    SettingsScreen(
                        onBack = { navController.popBackStack() },
                        onLogout = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
        composable(Screen.HistoryDetails.route) {
            selectedResult?.let { item ->
                HistoryDetailsScreen(
                    linkItem = item,
                    onBack = { navController.popBackStack() },
                    onScanAgain = { url ->
                        viewModel.updateCurrentUrl(url)
                        viewModel.scanUrl(url) { res ->
                            val route = if (res.status == "Safe") Screen.SafeResult.route else Screen.SuspiciousResult.route
                            navController.navigate(route)
                        }
                    }
                )
            }
        }
        composable(Screen.SavedLinkDetails.route) {
            selectedResult?.let { item ->
                SavedLinkDetailsScreen(
                    linkItem = item,
                    onBack = { navController.popBackStack() },
                    onScanAgain = { url ->
                        viewModel.updateCurrentUrl(url)
                        viewModel.scanUrl(url) { res ->
                            val route = if (res.status == "Safe") Screen.SafeResult.route else Screen.SuspiciousResult.route
                            navController.navigate(route)
                        }
                    },
                    onRemoveBookmark = { viewModel.toggleSaveLink(it) }
                )
            }
        }
        composable(Screen.NotificationCenter.route) {
            NotificationCenterScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.NotificationMonitoringSetup.route) {
            NotificationMonitoringSetupScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.DetectedLinkAlert.route) {
            DetectedLinkAlertScreen(
                onCheckLink = { url ->
                    viewModel.updateCurrentUrl(url)
                    viewModel.scanUrl(url) { item ->
                        val route = if (item.status == "Safe") Screen.SafeResult.route else Screen.SuspiciousResult.route
                        navController.navigate(route)
                    }
                },
                onDismiss = { navController.popBackStack() }
            )
        }
        composable(Screen.ReportLink.route) {
            ReportLinkScreen(
                onBack = { navController.popBackStack() },
                onSubmitReport = { _, _ -> }
            )
        }
        composable(Screen.SecurityTipsDetails.route) {
            SecurityTipsDetailsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.LearningCenter.route) {
            LearningCenterScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.EditProfile.route) {
            EditProfileScreen(
                onBack = { navController.popBackStack() },
                onSaveProfile = { _, _ -> }
            )
        }
        composable(Screen.PrivacyData.route) {
            PrivacyDataScreen(
                onBack = { navController.popBackStack() },
                onClearHistory = { viewModel.clearHistory() }
            )
        }
        composable(Screen.AboutLinkSafe.route) {
            AboutLinkSafeScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.HelpFaq.route) {
            HelpFaqScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.ErrorOffline.route) {
            ErrorOfflineScreen(
                onRetry = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.ScanSummary.route) {
            ScanSummaryScreen(
                history = history,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
