package com.t1.BEAlpha

import io.kvision.annotations.KVBindingRoute
import io.kvision.annotations.KVService
import io.kvision.remote.HttpMethod
import io.kvision.types.KFile

enum class Sort{
    TITLE, COLORS, CREATEAT, PRICE, CATEGORY, LOCATIONS
}

@KVService
interface ICardService{
    suspend fun getCardsList(search: String?, types: String, sort: Sort): List<Card>
    suspend fun addCard(card: Card): Card
    suspend fun updateCard(card: Card): Card
    suspend fun deleteCard(id: Int): Boolean
}

@KVService
interface IImageService{
    suspend fun addImage(img: Image): Image
    suspend fun getImages(cardId: Int?): List<Image>
    suspend fun deleteImage(id: Int): Boolean
}