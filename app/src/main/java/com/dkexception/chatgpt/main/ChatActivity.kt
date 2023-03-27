package com.dkexception.chatgpt.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dkexception.chatgpt.features.chatting.ChatGPTChatScreen
import com.dkexception.chatgpt.features.image_generation.ImageGenerationScreen
import com.dkexception.chatgpt.ui.theme.ChatGPTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {

    private val screenInt: Int by lazy {
        intent.getIntExtra("screenInt", 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatGPTTheme {
                when (screenInt) {
                    0 -> ChatGPTChatScreen()
                    else -> ImageGenerationScreen()
                }
            }
        }
    }
}
