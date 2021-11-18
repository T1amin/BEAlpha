package com.t1.BEAlpha

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList
import kotlinx.coroutines.launch

object Model {
    private val cardService = CardService()
    var cards: ObservableList<Card> = observableListOf()

    var search: String? = null
        set(value) {
            field = value
            AppScope.launch { getCardList() }
        }

    var types: String = "all"
        set(value) {
            field = value
            AppScope.launch { getCardList() }
        }

    var sort = Sort.TITLE
        set(value) {
            field = value
            AppScope.launch { getCardList() }
        }


    suspend fun getCardList(){
        val newCard = cardService.getCardsList(search, types, sort)
        cards.syncWithList(newCard)
    }

    suspend fun addCard(card: Card){
        cardService.addCard(card)
        getCardList()
    }

    suspend fun updateCard(card: Card){
        cardService.updateCard(card)
        getCardList()
    }

    suspend fun deleteCard(id: Int): Boolean{
        val result = cardService.deleteCard(id)
        getCardList()
        return result
    }
}
