@file:OptIn(ExperimentalMaterial3Api::class)

package com.dkexception.chatgpt.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dkexception.chatgpt.R
import com.dkexception.chatgpt.data.models.ChatModel
import com.dkexception.chatgpt.data.models.ChatType
import com.dkexception.chatgpt.data.models.ChatUserType

@Composable
fun ChatListLazyColumn(
    chatList: List<ChatModel>,
    listState: LazyListState,
    isLoading: Boolean
) {

    LaunchedEffect(chatList.size) {
        listState.scrollToItem(chatList.size - 1)
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(chatList) {
            ChatListItem(it)
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (isLoading) {
            item { Text("...") }
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
            if (model.chatType == ChatType.TEXT) {
                clipboardManager.setText(AnnotatedString(model.textOrUrl))
            }
        }
    ) {
        if (model.chatType == ChatType.TEXT) {
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(16.dp),
                text = model.textOrUrl,
                color = Color.White
            )
        } else {
            AsyncImage(
                model = model.textOrUrl,
                contentDescription = null
            )
        }
    }
}
