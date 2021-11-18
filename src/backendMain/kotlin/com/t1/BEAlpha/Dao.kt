package com.t1.BEAlpha

import org.jetbrains.exposed.sql.Table

object CardDao: Table("card") {
    val id = integer("id").primaryKey().autoIncrement().uniqueIndex()
    val title = varchar("title", 255).nullable()
    val price = integer("price").nullable()
    val category = varchar("category", 255).nullable()
    val tags = varchar("tags", 255).nullable()
    val description = text("description").nullable()
    val img = varchar("img", 255).nullable()
    val createdAt = datetime("created_at").nullable()

}
