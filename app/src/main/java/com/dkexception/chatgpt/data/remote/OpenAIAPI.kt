package com.dkexception.chatgpt.data.remote

import com.dkexception.chatgpt.BuildConfig
import com.dkexception.chatgpt.data.remote.dto.TextCompletionRequestDTO
import com.dkexception.chatgpt.data.remote.dto.TextCompletionResponseDTO
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAIAPI {

    @POST("chat/completions")
    suspend fun getCompletionsForPrompt(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") authToken: String = BuildConfig.openAIBackendSecretKey,
        @Body textCompletionRequestDTO: TextCompletionRequestDTO
    ): TextCompletionResponseDTO
}
