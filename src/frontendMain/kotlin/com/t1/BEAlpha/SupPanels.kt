package com.t1.BEAlpha

import io.kvision.core.onChangeLaunch
import io.kvision.form.select.SelectInput
import io.kvision.form.text.TextInput
import io.kvision.form.text.TextInputType
import io.kvision.form.text.text
import io.kvision.panel.HPanel

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