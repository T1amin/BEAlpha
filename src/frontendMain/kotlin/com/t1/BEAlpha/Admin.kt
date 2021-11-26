package com.t1.BEAlpha

import io.kvision.data.dataContainer
import io.kvision.html.*
import io.kvision.modal.Confirm
import io.kvision.panel.SimplePanel
import io.kvision.utils.px
import kotlinx.coroutines.launch

object Admin: SimplePanel() {
    init {
        NavPanel.add(Button(
            "Добавить товар",
            icon = "fas fa-plus",
            style = ButtonStyle.OUTLINEINFO,
            separator = " "
        ).onClick { EditPanel(Card(null)) })

        dataContainer(
            CardModel.cards, { card, index, _ ->
                add(position = index, Div(className = "polaroid"){
                    opacity = if (card.visible) 1.0 else 0.3
                    maxWidth = 350.px
                    minWidth = 160.px

                    article {
                    div(
                            className = "card-body"
                        ) {
                            image(
                                className = "img-fluid",
                                alt = card.img,
                                src = card.img
                            )
                        }
                        div(className = "card-body") {
                            header("№" + card.id.toString() + " \"${card.title}\"")
                            p("Цена " + card.price.toString() + " руб.")
                            p(card.description)
                            p(card.category + ", основные цвета: ${card.colors}")
                            if (!card.locations.isNullOrBlank()) p("В наличии: ${card.locations}")
                            if ((card.width != 0) or (card.depth != 0) or (card.height != 0)) {
                                p("Размеры: "
                                   + if (card.width != 0) { "${card.width} мм. " } else {""}
                                   + if (card.depth != 0) { "${card.depth} мм. " } else {""}
                                   + if (card.height != 0) { "${card.height} мм. " } else {""}
                                     )}
                            if (card.weight != 0) p("Вес ${card.weight}гр.")
                            if (!card.composes.isNullOrBlank()) p("Состав: ${card.composes}")
                            footer(className = "blockquote-footer", content = card.createdAt?.toLocaleString("ru-Ru"))
                        }
                        button("Редактор", "bi bi-pen").onClick { EditPanel(card) }
                        button("Удалить", "bi bi-trash").onClick { e ->
                            e.stopPropagation()
                            Confirm.show("Are you sure?", "Do you want to delete this address?") {
                                AppScope.launch {
                                    CardModel.cards[index].id?.let {
                                        CardModel.deleteCard(card.id!!)
                                    }
                                }
                            }
                        }
                    }})
            }, container = cardPanel
        )
    }
}