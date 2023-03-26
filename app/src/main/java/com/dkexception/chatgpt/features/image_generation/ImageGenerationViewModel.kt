package com.dkexception.chatgpt.features.image_generation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkexception.chatgpt.data.models.ChatModel
import com.dkexception.chatgpt.data.models.ChatType
import com.dkexception.chatgpt.data.models.ChatUserType
import com.dkexception.chatgpt.data.remote.dto.image_generation.ImageGenerationRequest
import com.dkexception.chatgpt.data.repository.OpenAIAPIsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageGenerationViewModel @Inject constructor(
    private val openAIAPIsRepository: OpenAIAPIsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ImageGenerationScreenState())
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

            val (response, error) = openAIAPIsRepository.generateImagesForPrompt(
                imageGenerationRequest = ImageGenerationRequest(
                    prompt = prompt,
                    numberOfImages = 1,
                    size = "512x512"
                )
            )

            _state.update { state ->
                state.copy(
                    chatList = state.chatList.apply {

                        val imageUrl = response?.data?.firstOrNull()?.url

                        add(
                            when {
                                error != null -> ChatModel(
                                    textOrUrl = error,
                                    userType = ChatUserType.AI
                                )
                                imageUrl == null -> ChatModel(
                                    textOrUrl = "Something went wrong!",
                                    userType = ChatUserType.AI
                                )
                                else -> ChatModel(
                                    textOrUrl = imageUrl,
                                    userType = ChatUserType.AI,
                                    chatType = ChatType.IMAGE
                                )
                            }
                        )
                    },
                    isLoading = false
                )
            }
        }
    }
}
