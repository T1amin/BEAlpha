@file:UseContextualSerialization(LocalDateTime::class)


package com.t1.BEAlpha

import io.kvision.types.KFile
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
    val createdAt: LocalDateTime? = null,
    val visible: Boolean = true,
    val locations: String? = null,
    val width: Int? = null,
    val depth: Int? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val composes: String? = null,
)

@Serializable
data class Image(
    val id: Int? = 0,
    val cardId: Int? = null,
    val url: String? = null,
    val value: ByteArray? = null
)
