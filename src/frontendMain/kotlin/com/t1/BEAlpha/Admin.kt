package com.t1.BEAlpha

import io.kvision.data.dataContainer
import io.kvision.form.check.CheckBox
import io.kvision.form.check.checkBox
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
                            header("№" + card.id.toString() + " \"${card.title}\"")
                            p("Цена " + card.price.toString() + " руб.")
                            p(card.description)
                            p(card.category + ", основные цвета: ${card.colors}")
                            if (!card.locations.isNullOrBlank()) p("В наличии: ${card.locations}")
                            if ((card.width != null) and (card.depth != null) and (card.height != null)) {
                                p("Размеры: ${card.width}мм на ${card.depth}мм на ${card.height}мм.")
                            }
                            if (card.weight != null) p("Вес ${card.weight}гр.")
                            if (!card.composes.isNullOrBlank()) p("Состав: ${card.composes}")
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