package com.dkexception.chatgpt.features.image_generation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkexception.chatgpt.R
import com.dkexception.chatgpt.data.models.ChatModel
import com.dkexception.chatgpt.ui.common.ChatGPTScaffold
import com.dkexception.chatgpt.ui.common.ChatListLazyColumn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ImageGenerationScreen(
    viewModel: ImageGenerationViewModel = hiltViewModel()
) {
    val state: ImageGenerationScreenState by viewModel.state.collectAsState()

    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    ImageGenerationScreenScaffold(
        state = state,
        onSubmitButtonClicked = {
            viewModel.submitPrompt()
            coroutineScope.launch {
                it.scrollToItem(state.chatList.size - 1)
            }
        },
        onPromptChanged = viewModel::onPromptChanged
    )
}

@Preview
@Composable
fun ImageGenerationScreenScaffold(
    state: ImageGenerationScreenState = ImageGenerationScreenState(
        isLoading = false,
        chatList = ChatModel.getDummyChatListForUIPreview().toMutableList()
    ),
    onSubmitButtonClicked: ((LazyListState) -> Unit)? = null,
    onPromptChanged: ((String) -> Unit)? = null
) {

    val listState: LazyListState = rememberLazyListState()

    ChatGPTScaffold(
        titleText = stringResource(R.string.image_gen_screen_title),
        userInputPromptText = state.userEnteredPrompt,
        onSubmitButtonClicked = {
            onSubmitButtonClicked?.invoke(listState)
        },
        onPromptChanged = onPromptChanged
    ) {
        ChatListLazyColumn(
            chatList = state.chatList,
            listState = listState,
            isLoading = state.isLoading
        )
    }
}
