package com.t1.BEAlpha

import io.kvision.annotations.KVService

enum class Sort{
    TITLE, COLORS, CREATEAT, PRICE, CATEGORY
}

@KVService
interface ICardService{
    suspend fun getCardsList(search: String?, types: String, sort: Sort): List<Card>
    suspend fun addCard(card: Card): Card
    suspend fun updateCard(card: Card): Card
    suspend fun deleteCard(id: Int): Boolean
}

