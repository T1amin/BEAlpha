package com.t1.BEAlpha

import io.kvision.state.ObservableList
import io.kvision.state.observableListOf
import io.kvision.utils.syncWithList
import kotlinx.coroutines.launch

object CardModel {
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

object ImageModel {
    private val imageService = ImageService()
    var images: ObservableList<Image> = observableListOf()
    var search: Int? = null
        set(value) {
            field = value
            AppScope.launch { ImageModel.getImages() }
        }
    suspend fun getImages(){
        val newImage = imageService.getImages(search)
        images.syncWithList(newImage)
    }

    suspend fun addImage(image: Image){
        imageService.addImage(image)
        getImages()
    }

    suspend fun deleteImage(id: Int): Boolean{
        val result = imageService.deleteImage(id)
        getImages()
        return result
    }
}
