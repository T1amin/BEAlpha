package com.t1.BEAlpha

import io.kvision.core.onChangeLaunch
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.select.SelectInput
import io.kvision.form.spinner.Spinner
import io.kvision.form.text.Text
import io.kvision.form.text.TextInput
import io.kvision.form.text.TextInputType
import io.kvision.form.text.text
import io.kvision.form.time.DateTime
import io.kvision.html.Button
import io.kvision.html.button
import io.kvision.modal.Modal
import io.kvision.panel.HPanel
import kotlinx.coroutines.launch

val sortOptions = listOf(
    "${Sort.TITLE}" to "Название",
    "${Sort.PRICE}" to "Цена",
    "${Sort.CATEGORY}" to "Категория",
    "${Sort.TAG}" to "Тег",
//    "${Sort.CREATEAT}" to "Дата"
)

object Selector: SelectInput(sortOptions, "${Sort.TITLE}") {
    init {
        onChangeLaunch {
            this.value?.let { opt ->
                Model.sort = Sort.valueOf(opt)
            }
        }
    }
}

object SearchPanel: HPanel() {
    init {
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
            add(Card::tags, Text(label = "Теги", value = editingCard.tags).apply { maxlength = 255 })
            add(Card::description, Text(label = "Описание", value = editingCard.description))
            add(Card::img, Text(label = "Ссылка на изображение", value = editingCard.img))
        }
        saveButton = button("Сохранить") {
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