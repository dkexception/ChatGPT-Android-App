package com.dkexception.chatgpt.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dkexception.chatgpt.features.screen_selection.SelectionScreen
import com.dkexception.chatgpt.ui.theme.ChatGPTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatGPTTheme {
                SelectionScreen {
                    startActivity(
                        Intent(this, ChatActivity::class.java).apply {
                            putExtra("screenInt", it)
                            flags =
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                    )
                }
            }
        }
    }
}
