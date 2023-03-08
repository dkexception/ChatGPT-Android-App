@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.dkexception.chatgpt.ui.screens.chatting

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkexception.chatgpt.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ChatGPTChatScreen(
    chatGPTChatViewModel: ChatGPTChatViewModel = hiltViewModel()
) {
    val state: ChatGPTChatScreenState by chatGPTChatViewModel.state.collectAsState()

    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    ChatScreenScaffold(
        state = state,
        onSubmitButtonClicked = {
            chatGPTChatViewModel.submitPrompt()
            coroutineScope.launch {
                it.scrollToItem(state.chatList.size - 1)
            }
        },
        onPromptChanged = chatGPTChatViewModel::onPromptChanged
    )
}

@Preview
@Composable
private fun ChatScreenScaffold(
    state: ChatGPTChatScreenState = ChatGPTChatScreenState(
        isLoading = false,
        chatList = ChatModel.getDummyChatListForUIPreview().toMutableList()
    ),
    onSubmitButtonClicked: ((LazyListState) -> Unit)? = null,
    onPromptChanged: ((String) -> Unit)? = null
) {

    val listState: LazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.chat_screen_title)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            ChatTextFieldBottomBar(
                currentPromptText = state.userEnteredPrompt,
                onSubmitButtonClicked = {
                    onSubmitButtonClicked?.invoke(listState)
                },
                onPromptChanged = onPromptChanged
            )
        }
    ) { paddingValues ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.inversePrimary
        ) {

            LaunchedEffect(state.chatList.size) {
                listState.scrollToItem(state.chatList.size - 1)
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(state.chatList) {
                    ChatListItem(it)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (state.isLoading) {
                    item { Text("...") }
                }
            }
        }
    }
}

@Composable
private fun ChatTextFieldBottomBar(
    currentPromptText: String,
    onSubmitButtonClicked: (() -> Unit)? = null,
    onPromptChanged: ((String) -> Unit)? = null
) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    elevation = CardDefaults.cardElevation(8.dp),
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(
        containerColor = Color(
            if (isSystemInDarkTheme()) 0xFF414148 else 0xFFFFFFFF
        )
    )
) {

    val keyboardManager = LocalSoftwareKeyboardController.current

    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = currentPromptText,
            onValueChange = { onPromptChanged?.invoke(it) },
            label = { Text(stringResource(R.string.chat_query_field_hint)) },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )
        Spacer(modifier = Modifier.width(8.dp))
        ElevatedButton(
            onClick = {
                onSubmitButtonClicked?.invoke()
                keyboardManager?.hide()
            }
        ) {
            Text(text = stringResource(R.string.submit), modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
private fun ChatListItem(model: ChatModel) = Box(Modifier.fillMaxWidth()) {

    val clipboardManager = LocalClipboardManager.current

    Card(
        modifier = Modifier
            .wrapContentWidth()
            .widthIn(min = 0.dp, max = LocalConfiguration.current.screenWidthDp.dp * .6f)
            .align(
                when (model.userType) {
                    ChatUserType.HUMAN -> Alignment.CenterEnd
                    ChatUserType.AI -> Alignment.CenterStart
                }
            ),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(
                when (model.userType) {
                    ChatUserType.HUMAN -> R.color.chat_bubble_human
                    ChatUserType.AI -> R.color.chat_bubble_ai
                }
            )
        ),
        onClick = {
            clipboardManager.setText(AnnotatedString(model.text))
        }
    ) {
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .padding(16.dp),
            text = model.text,
            color = Color.White
        )
    }
}
