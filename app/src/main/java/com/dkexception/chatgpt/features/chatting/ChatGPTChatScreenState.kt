package com.dkexception.chatgpt.features.chatting

import com.dkexception.chatgpt.data.models.ChatModel
import com.dkexception.chatgpt.data.models.ChatUserType
import com.dkexception.chatgpt.data.remote.dto.chat_completion.Message

data class ChatGPTChatScreenState(

    val isLoading: Boolean = false,

    val userEnteredPrompt: String = "",

    val chatList: MutableList<ChatModel> = ChatModel.getChatStart()
) {

    fun toMessageListForAPI(): List<Message> = chatList.map {
        Message(
            content = it.textOrUrl,
            role = when (it.userType) {
                ChatUserType.HUMAN -> "user"
                ChatUserType.AI -> "assistant"
            }
        )
    }
}
