package com.dkexception.chatgpt.data.repository

import com.dkexception.chatgpt.data.remote.OpenAIAPI
import com.dkexception.chatgpt.data.remote.dto.chat_completion.ChatCompletionRequestDTO
import com.dkexception.chatgpt.data.remote.dto.chat_completion.ChatCompletionResponseDTO
import com.dkexception.chatgpt.data.remote.dto.image_generation.ImageGenerationRequest
import com.dkexception.chatgpt.data.remote.dto.image_generation.ImageGenerationResponseDTO
import com.dkexception.chatgpt.di.StandardDispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenAIAPIsRepository @Inject constructor(
    private val openAIAPI: OpenAIAPI,
    private val standardDispatchers: StandardDispatchers
) {

    suspend fun getTextCompletionForPrompt(
        requestDTO: ChatCompletionRequestDTO
    ): Pair<ChatCompletionResponseDTO?, String?> = apiWrapper(
        dispatcher = standardDispatchers.io
    ) {
        openAIAPI.getCompletionsForPrompt(chatCompletionRequestDTO = requestDTO)
    }

    suspend fun generateImagesForPrompt(
        imageGenerationRequest: ImageGenerationRequest
    ): Pair<ImageGenerationResponseDTO?, String?> = apiWrapper(
        dispatcher = standardDispatchers.io
    ) {
        openAIAPI.generateImagesForPrompt(imageGenerationRequest = imageGenerationRequest)
    }
}
