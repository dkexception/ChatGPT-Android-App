package com.dkexception.chatgpt.ui.screens.chatting

import com.dkexception.chatgpt.data.remote.dto.Message

data class ChatGPTChatScreenState(

    val isLoading: Boolean = false,

    val userEnteredPrompt: String = "",

    val chatList: MutableList<ChatModel> = ChatModel.getChatStart()
) {

    fun toMessageListForAPI(): List<Message> = chatList.map {
        Message(
            content = it.text,
            role = when (it.userType) {
                ChatUserType.HUMAN -> "user"
                ChatUserType.AI -> "assistant"
            }
        )
    }
}

data class ChatModel(
    val text: String,
    val userType: ChatUserType
) {
    companion object {

        fun getChatStart() = mutableListOf(
            ChatModel("Hello, how may I help you today?", ChatUserType.AI)
        )

        fun getDummyChatListForUIPreview() = listOf(
            ChatModel("Hello, how may I help you today?", ChatUserType.AI),
            ChatModel("Hi.. wanted to discuss something", ChatUserType.HUMAN)
        )
    }
}

enum class ChatUserType {
    HUMAN, AI
}
