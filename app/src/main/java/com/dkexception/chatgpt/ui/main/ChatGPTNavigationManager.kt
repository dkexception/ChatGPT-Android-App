package com.dkexception.chatgpt.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dkexception.chatgpt.features.chatting.ChatGPTChatScreen
import com.dkexception.chatgpt.features.image_generation.ImageGenerationScreen
import com.dkexception.chatgpt.features.screen_selection.SelectionScreen

enum class AppNavigationRoutes(val routeName: String) {

    SCREEN_SELECTION("screen_selection"),

    CHAT("chat"),

    IMAGE_GENERATION("image_generation")
}

@Preview
@Composable
fun ChatGPTNavigationManager(
    onDestinationChanged: ((NavDestination) -> Unit)? = null
) {

    val navController = rememberNavController().apply {
        addOnDestinationChangedListener { _, destination, _ ->
            onDestinationChanged?.invoke(destination)
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppNavigationRoutes.SCREEN_SELECTION.routeName
    ) {

        composable(AppNavigationRoutes.SCREEN_SELECTION.routeName) {
            SelectionScreen(navController)
        }

        composable(AppNavigationRoutes.CHAT.routeName) {
            ChatGPTChatScreen()
        }

        composable(AppNavigationRoutes.IMAGE_GENERATION.routeName) {
            ImageGenerationScreen()
        }
    }
}
