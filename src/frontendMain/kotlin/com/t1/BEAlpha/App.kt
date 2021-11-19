package com.t1.BEAlpha

import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.BootstrapModule
import io.kvision.BootstrapCssModule
import io.kvision.BootstrapSelectModule
import io.kvision.BootstrapDatetimeModule
import io.kvision.BootstrapSpinnerModule
import io.kvision.BootstrapUploadModule
import io.kvision.BootstrapTypeaheadModule
import io.kvision.BootstrapIconsModule
import io.kvision.FontAwesomeModule
import io.kvision.RichTextModule
import io.kvision.ChartModule
import io.kvision.TabulatorModule
import io.kvision.MomentModule
import io.kvision.MapsModule
import io.kvision.ToastModule
import io.kvision.PrintModule
import io.kvision.html.Span
import io.kvision.module
import io.kvision.panel.root
import io.kvision.startApplication
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {
    init {
        io.kvision.require("css/style.css")
    }

    override fun start() {
        root("kvapp") {
            add(NavPanel)
            add(Admin)
//            add(View)
        }
        AppScope.launch {
            Model.getCardList()
            test()
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        BootstrapSelectModule,
        BootstrapDatetimeModule,
        BootstrapSpinnerModule,
        BootstrapUploadModule,
        BootstrapTypeaheadModule,
        BootstrapIconsModule,
        FontAwesomeModule,
        RichTextModule,
        ChartModule,
        TabulatorModule,
        MomentModule,
        MapsModule,
        ToastModule,
        PrintModule,
        CoreModule
    )
}
