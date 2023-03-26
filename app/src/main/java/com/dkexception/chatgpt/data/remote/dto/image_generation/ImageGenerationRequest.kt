package com.dkexception.chatgpt.data.remote.dto.image_generation

import com.google.gson.annotations.SerializedName

data class ImageGenerationRequest(
    @SerializedName("prompt")
    val prompt: String,
    @SerializedName("n")
    val numberOfImages: Int,
    @SerializedName("size")
    val size: String
)
