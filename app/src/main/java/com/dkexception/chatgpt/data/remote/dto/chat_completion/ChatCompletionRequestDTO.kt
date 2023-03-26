package com.dkexception.chatgpt.data.remote.dto.chat_completion

import com.google.gson.annotations.SerializedName

data class ChatCompletionRequestDTO(

    @SerializedName("model")
    val model: String = "gpt-3.5-turbo",

    @SerializedName("messages")
    val messages: List<Message>
)
