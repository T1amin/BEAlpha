package com.t1.BEAlpha

import io.kvision.form.select.SelectInput
import io.kvision.form.select.selectInput
import io.kvision.form.text.TextInput
import io.kvision.form.text.TextInputType
import io.kvision.form.text.text
import io.kvision.html.button
import io.kvision.navbar.*
import io.kvision.panel.HPanel
import io.kvision.panel.hPanel

object NavPanel: Navbar(){
    init {
        nav {
            navLink("Контакты")
            navLink("Товары")
        }
    }
}