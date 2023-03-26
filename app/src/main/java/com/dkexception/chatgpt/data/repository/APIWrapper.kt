package com.dkexception.chatgpt.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> apiWrapper(
    dispatcher: CoroutineDispatcher,
    apiBlock: suspend () -> T?
): Pair<T?, String?> = withContext(dispatcher) {
    try {
        apiBlock() to null
    } catch (e: Exception) {
        null to (e.message ?: "Something went wrong!")
    }
}
