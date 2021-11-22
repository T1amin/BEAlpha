@file:UseContextualSerialization(LocalDateTime::class)


package com.t1.BEAlpha

import io.kvision.types.LocalDateTime
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseContextualSerialization

@Serializable
data class Card(
    val id: Int? = 0,
    val title: String? = "",
    val price: Int? = 0,
    val category: String? = null,
    val colors: String? = null,
    val description: String? = null,
    val img: String? = null,
    val createdAt: LocalDateTime? = null
)