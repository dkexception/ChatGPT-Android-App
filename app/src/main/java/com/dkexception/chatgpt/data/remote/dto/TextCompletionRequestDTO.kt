package com.dkexception.chatgpt.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TextCompletionRequestDTO(

    @SerializedName("model")
    val model: String = "gpt-3.5-turbo",

    @SerializedName("messages")
    val messages: List<Message>
)
