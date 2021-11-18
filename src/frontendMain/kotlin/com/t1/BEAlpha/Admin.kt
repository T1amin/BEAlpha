package com.t1.BEAlpha

import com.t1.BEAlpha.Model.types
import io.kvision.core.*
import io.kvision.data.dataContainer
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.spinner.Spinner
import io.kvision.form.text.DataType
import io.kvision.form.text.Text
import io.kvision.form.time.DateTime
import io.kvision.form.upload.Upload
import io.kvision.html.*
import io.kvision.modal.Confirm
import io.kvision.modal.Modal
import io.kvision.panel.FlexPanel
import io.kvision.panel.SimplePanel
import io.kvision.table.HeaderCell
import io.kvision.table.Table
import io.kvision.table.TableType
import io.kvision.utils.px
import kotlinx.coroutines.launch

object Admin: SimplePanel() {
    init {

        val cardPanel = FlexPanel(
            wrap = FlexWrap.WRAP,
            direction = FlexDirection.ROW,
            justify = JustifyContent.CENTER,
            alignContent = AlignContent.FLEXSTART,
            alignItems = AlignItems.CENTER,
        ){
        }


        NavPanel.add(Selector)
        NavPanel.add(SearchPanel)

        dataContainer(
            Model.cards, {card, index, _ ->
                val modal = Modal(
                    "Подробнее",
                    closeButton = true,
                    escape = true,
                    className = "modal fade bottom",
                    animation = true,
                ){
                    formPanel<Card> {
                        add(Card::title, Text(label = "Название").apply { maxlength = 255 })
                        add(Card::price, Spinner(label = "Цена"))
                        add(Card::category, Text(label = "Категория").apply { maxlength = 255 })
                        add(Card::tags, Text(label = "Теги").apply { maxlength = 255 })
                        add(Card::description, Text(label = "Описание"))
                        add(Card::createdAt, DateTime(label = "Дата"))
                        add(Card::img, Upload("/", multiple = false, label = "Upload files (images only)"){
                            explorerTheme = true
                            dropZoneEnabled = true
                            allowedFileTypes = setOf("image")
                            TODO()
                        })
                        button("Загрузить", type = ButtonType.SUBMIT)
                    }

//                        div(
//                            className = "modal-body py-1"
//                        ){
//                            image(
//                                className = "img-fluid",
//                                alt = card.img,
//                                src = card.img
//                            )
//                        }
//                        div(className = "mb-0"){
//                            header(className = "card-text", content = card.id.toString())
//                            h5(className = "card-title", content = card.title)
//                            p(className = "card-text", content = card.price.toString() + " руб.")
//                            p(className = "card-text", content = card.description)
//                            p(className = "card-text", content = card.category)
//                            p(className = "card-text", content = "#" + card.tags)
//                            footer(className = "blockquote-footer", content = card.createdAt.toString())
//                        }

                }
                add(position = index, Div(className = "card", ){
                    maxWidth = 450.px
                    minWidth = 180.px
                    margin = 5.px
                    flexShrink = 1
                    div(
                        className = "card-body"
                    ){
                        image(
                            className = "img-fluid",
                            alt = card.img,
                            src = card.img
                        )
                    }
                    div(className = "card-body"){
                        header(className = "card-text", content = card.id.toString())
                        h5(className = "card-title", content = card.title)
                        p(className = "card-text", content = card.price.toString() + " руб.")
                        p(className = "card-text", content = card.description)
                        p(className = "card-text", content = card.category)
                        p(className = "card-text", content = "#" + card.tags)
                        footer(className = "blockquote-footer", content = card.createdAt.toString())
                    }
                    button("Редактор", "fa-duotone fa-pencil").onClick { modal.show() }
                    button("Удалить", "fa-duotone fa-trash-can").onClick { e ->
                        e.stopPropagation()
                        Confirm.show("Are you sure?", "Do you want to delete this address?") {
                            AppScope.launch {
                                Model.cards[index].id?.let {
                                    Model.deleteCard(card.id!!)
                                }
                            }
                        } }
                })
            }, container = cardPanel
        )
    }
}