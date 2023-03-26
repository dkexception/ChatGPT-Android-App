package com.dkexception.chatgpt.features.chatting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkexception.chatgpt.data.models.ChatModel
import com.dkexception.chatgpt.data.models.ChatUserType
import com.dkexception.chatgpt.data.remote.dto.chat_completion.ChatCompletionRequestDTO
import com.dkexception.chatgpt.data.repository.OpenAIAPIsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatGPTChatViewModel @Inject constructor(
    private val openAIAPIsRepository: OpenAIAPIsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChatGPTChatScreenState())
    val state = _state.asStateFlow()

    fun onPromptChanged(newPrompt: String) = _state.update { state ->
        state.copy(userEnteredPrompt = newPrompt)
    }

    fun submitPrompt() {

        // Validation
        if (_state.value.userEnteredPrompt.isBlank()) return
        val prompt = _state.value.userEnteredPrompt.trim()

        viewModelScope.launch {

            _state.update { state ->
                state.copy(
                    chatList = state.chatList.apply {
                        add(
                            ChatModel(
                                textOrUrl = prompt,
                                userType = ChatUserType.HUMAN
                            )
                        )
                    },
                    isLoading = true,
                    userEnteredPrompt = ""
                )
            }

            val (response, error) = openAIAPIsRepository.getTextCompletionForPrompt(
                requestDTO = ChatCompletionRequestDTO(
                    messages = _state.value.toMessageListForAPI()
                )
            )

            val completionResponse: String =
                error ?: response?.choices?.firstOrNull()?.message?.content.orEmpty().trim()

            _state.update { state ->
                state.copy(
                    chatList = state.chatList.apply {
                        add(
                            ChatModel(
                                textOrUrl = completionResponse,
                                userType = ChatUserType.AI
                            )
                        )
                    },
                    isLoading = false
                )
            }
        }
    }
}
