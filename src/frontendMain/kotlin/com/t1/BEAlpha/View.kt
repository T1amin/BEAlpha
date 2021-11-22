package com.t1.BEAlpha

import io.kvision.core.*
import io.kvision.data.dataContainer
import io.kvision.form.select.*
import io.kvision.form.text.TextInput
import io.kvision.form.text.TextInputType
import io.kvision.form.text.text
import io.kvision.html.*
import io.kvision.modal.Modal
import io.kvision.panel.FlexPanel
import io.kvision.panel.HPanel
import io.kvision.panel.SimplePanel
import io.kvision.panel.hPanel
import io.kvision.utils.px

object View : SimplePanel() {
    init {

        val cardPanel = FlexPanel(
            wrap = FlexWrap.WRAP,
            direction = FlexDirection.ROW,
            justify = JustifyContent.CENTER,
            alignContent = AlignContent.FLEXSTART,
            alignItems = AlignItems.CENTER,
        ){
        }

        dataContainer(
            Model.cards, {card, order, _ ->
                val modal = Modal(
                    "Подробнее",
                    closeButton = true,
                    escape = true,
                    className = "modal fade bottom",
                    animation = true,
                ){
                    div(
                        className = "polaroid",
                    ) {
                        maxWidth = 450.px
                        minWidth = 180.px
                        article {
                            div(
                                className = "modal-body py-1"
                            ) {
                                image(
                                    className = "img-fluid",
                                    alt = card.img,
                                    src = card.img
                                )
                            }
                            div(className = "mb-0") {
                                h5(className = "card-title", content = card.title)
                                p(className = "card-text", content = card.price.toString() + " руб.")
                                p(className = "card-text", content = card.description)
                                p(className = "card-text", content = card.category)
                                p(className = "card-text", content = "#" + card.tags)
                                footer(className = "blockquote-footer",
                                    content = card.createdAt?.toLocaleString("ru-Ru"))
                            }
                        }
                    }
                }
                add(position = order, Div(className = "polaroid", ){
                    maxWidth = 450.px
                    minWidth = 180.px
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
                            h5(className = "card-title", content = card.title)
                            p(className = "card-text", content = card.price.toString() + " руб")
                        }
                        onClick {
                            modal.show()
                        }
                    }
                })
            }, container = cardPanel
        )
    }
}