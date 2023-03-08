package com.dkexception.chatgpt.data.repository

import com.dkexception.chatgpt.data.remote.OpenAIAPI
import com.dkexception.chatgpt.data.remote.dto.TextCompletionRequestDTO
import com.dkexception.chatgpt.data.remote.dto.TextCompletionResponseDTO
import com.dkexception.chatgpt.di.StandardDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextCompletionRepository @Inject constructor(
    private val openAIAPI: OpenAIAPI,
    private val standardDispatchers: StandardDispatchers
) {

    suspend fun getTextCompletionForPrompt(
        requestDTO: TextCompletionRequestDTO
    ): TextCompletionResponseDTO = withContext(standardDispatchers.io) {
        openAIAPI.getCompletionsForPrompt(textCompletionRequestDTO = requestDTO)
    }
}
