package com.dkexception.chatgpt.ui.screens.chatting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkexception.chatgpt.data.remote.dto.TextCompletionRequestDTO
import com.dkexception.chatgpt.data.remote.dto.TextCompletionResponseDTO
import com.dkexception.chatgpt.data.repository.TextCompletionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatGPTChatViewModel @Inject constructor(
    private val textCompletionRepository: TextCompletionRepository
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
                                text = prompt,
                                userType = ChatUserType.HUMAN
                            )
                        )
                    },
                    isLoading = true,
                    userEnteredPrompt = ""
                )
            }

            val response: TextCompletionResponseDTO =
                textCompletionRepository.getTextCompletionForPrompt(
                    requestDTO = TextCompletionRequestDTO(
                        messages = _state.value.toMessageListForAPI()
                    )
                )

            _state.update { state ->
                state.copy(
                    chatList = state.chatList.apply {
                        add(
                            ChatModel(
                                text = response.choices?.firstOrNull()?.message?.content.orEmpty()
                                    .trim(),
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
