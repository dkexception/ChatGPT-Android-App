package com.dkexception.chatgpt.data.remote

import com.dkexception.chatgpt.BuildConfig
import com.dkexception.chatgpt.data.remote.dto.chat_completion.ChatCompletionRequestDTO
import com.dkexception.chatgpt.data.remote.dto.chat_completion.ChatCompletionResponseDTO
import com.dkexception.chatgpt.data.remote.dto.image_generation.ImageGenerationRequest
import com.dkexception.chatgpt.data.remote.dto.image_generation.ImageGenerationResponseDTO
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAIAPI {

    @POST("chat/completions")
    suspend fun getCompletionsForPrompt(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") authToken: String = BuildConfig.openAIBackendSecretKey,
        @Body chatCompletionRequestDTO: ChatCompletionRequestDTO
    ): ChatCompletionResponseDTO

    @POST("images/generations")
    suspend fun generateImagesForPrompt(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") authToken: String = BuildConfig.openAIBackendSecretKey,
        @Body imageGenerationRequest: ImageGenerationRequest
    ): ImageGenerationResponseDTO
}
