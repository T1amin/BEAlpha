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
        NavPanel.add(Button("Добавить").onClick { EditPanel(Card(null)) })

        dataContainer(
            Model.cards, {card, index, _ ->
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
                        footer(className = "blockquote-footer", content = card.createdAt?.toLocaleString("ru-Ru"))
                    }
                    button("Редактор", "fas fa-pencil").onClick { EditPanel(card) }
                    button("Удалить", "fas fa-trash-can").onClick { e ->
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