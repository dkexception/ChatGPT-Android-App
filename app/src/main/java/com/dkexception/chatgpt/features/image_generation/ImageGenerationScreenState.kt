package com.dkexception.chatgpt.features.image_generation

import com.dkexception.chatgpt.data.models.ChatModel

data class ImageGenerationScreenState(

    val isLoading: Boolean = false,

    val userEnteredPrompt: String = "",

    val chatList: MutableList<ChatModel> = ChatModel.getChatStart()
)
