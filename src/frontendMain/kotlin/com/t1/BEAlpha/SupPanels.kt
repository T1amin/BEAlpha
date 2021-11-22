package com.t1.BEAlpha

import io.kvision.core.*
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.select.selectInput
import io.kvision.form.spinner.Spinner
import io.kvision.form.text.Text
import io.kvision.form.text.TextInput
import io.kvision.form.text.TextInputType
import io.kvision.form.text.text
import io.kvision.html.Button
import io.kvision.html.button
import io.kvision.modal.Modal
import io.kvision.navbar.*
import io.kvision.panel.FlexPanel
import io.kvision.panel.hPanel
import io.kvision.utils.perc
import kotlinx.coroutines.launch

val sortOptions = listOf(
    "${Sort.TITLE}" to "Название",
    "${Sort.PRICE}" to "Цена",
    "${Sort.CATEGORY}" to "Категория",
    "${Sort.COLORS}" to "Тег",
)

object cardPanel: FlexPanel(
    wrap = FlexWrap.WRAP,
    direction = FlexDirection.ROW,
    justify = JustifyContent.CENTER,
    alignContent = AlignContent.FLEXSTART,
    alignItems = AlignItems.CENTER,
){
}

object NavPanel: Navbar(
    expand = NavbarExpand.MD,
    type = NavbarType.STICKYTOP,
){

    init {
        height = 30.perc
        alignContent = AlignContent.SPACEBETWEEN
        verticalAlign =VerticalAlign.BASELINE
        nav {
            navLink("Контакты")
            navLink("Товары")
        }
        navForm {
            selectInput(sortOptions, "${Sort.TITLE}") {
                onChangeLaunch {
                    this.value?.let { opt ->
                        Model.sort = Sort.valueOf(opt)
                    }
                }
            }
        }
        navForm {
            hPanel() {
                text(TextInputType.SEARCH) {
                    placeholder = "Найти ..."
                    setEventListener<TextInput> {
                        input = {
                            Model.search = self.value
                        }
                    }
                }
            }
        }
    }
}

class EditPanel(card: Card) : Modal(
    "Подробнее",
    closeButton = true,
    escape = true,
    className = "modal fade bottom",
    animation = true,
) {
    private lateinit var editingCard: Card
    private lateinit var formPanel: FormPanel<Card>
    private lateinit var saveButton: Button

    init {
        editingCard = card
        formPanel = formPanel {
            add(Card::title, Text(label = "Название", value = editingCard.title).apply { maxlength = 255 })
            add(Card::price, Spinner(label = "Цена", value = editingCard.price))
            add(Card::category, Text(label = "Категория", value = editingCard.category).apply { maxlength = 255 })
            add(Card::colors, Text(label = "Теги", value = editingCard.colors).apply { maxlength = 255 })
            add(Card::description, Text(label = "Описание", value = editingCard.description))
            add(Card::img, Text(label = "Ссылка на изображение", value = editingCard.img))
        }
        saveButton = button("Сохранить", "bi bi-check2-circle") {
            onClick {
                AppScope.launch {
                    val editCard = formPanel.getData()
                    if (card.id == null){
                        Model.addCard(editCard.copy(id = editCard.id))
                    }else {
                        Model.updateCard(editCard.copy(id = editingCard.id))
                    }
                    this@EditPanel.hide()
                }
            }
        }
        super.show()
    }
}