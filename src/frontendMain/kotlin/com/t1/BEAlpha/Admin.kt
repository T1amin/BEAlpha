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
            Model.cards, {card, index, _ ->
                add(position = index, Div(className = "polaroid"){
                        maxWidth = 350.px
                        minWidth = 160.px
//                        margin = 5.px
//                        flexShrink = 1
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
                            header(className = "card-text", content = card.id.toString())
                            h5(className = "card-title", content = card.title)
                            p(className = "card-text", content = card.price.toString() + " руб.")
                            p(className = "card-text", content = card.description)
                            p(className = "card-text", content = card.category)
                            p(className = "card-text", content = card.colors)
                            footer(className = "blockquote-footer", content = card.createdAt?.toLocaleString("ru-Ru"))
                        }
                        button("Редактор", "bi bi-pen").onClick { EditPanel(card) }
                        button("Удалить", "bi bi-trash").onClick { e ->
                            e.stopPropagation()
                            Confirm.show("Are you sure?", "Do you want to delete this address?") {
                                AppScope.launch {
                                    Model.cards[index].id?.let {
                                        Model.deleteCard(card.id!!)
                                    }
                                }
                            }
                        }
                    }})
            }, container = cardPanel
        )
    }
}