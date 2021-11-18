package com.t1.BEAlpha

import com.t1.BEAlpha.Model.types
import io.kvision.panel.SimplePanel
import io.kvision.table.HeaderCell
import io.kvision.table.Table
import io.kvision.table.TableType

object Admin: SimplePanel() {
    init {
        val tabPanel = Table( types = setOf(TableType.STRIPED, TableType.HOVER)){

        }
    }
}