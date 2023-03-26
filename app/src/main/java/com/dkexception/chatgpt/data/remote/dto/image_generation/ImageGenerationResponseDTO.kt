package com.dkexception.chatgpt.data.remote.dto.image_generation

import com.google.gson.annotations.SerializedName

data class ImageGenerationResponseDTO(
    @SerializedName("created")
    val created: Int? = null,
    @SerializedName("data")
    val `data`: List<Data?>? = null
)

data class Data(
    @SerializedName("url")
    val url: String? = null
)
