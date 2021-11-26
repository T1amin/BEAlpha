package com.t1.BEAlpha

import org.jetbrains.exposed.sql.Table

object CardDao: Table("card") {
    val id = integer("id").primaryKey().autoIncrement().uniqueIndex()
    val title = varchar("title", 255).nullable()
    val price = integer("price").nullable()
    val category = varchar("category", 255).nullable()
    val colors = varchar("colors", 255).nullable()
    val description = text("description").nullable()
    val img = varchar("img", 255).nullable()
    val createdAt = datetime("created_at").nullable()
    val visible = bool("visible")
    val locations = varchar("locations", 255).nullable()
    val width = integer("width").nullable()
    val depth = integer("depth").nullable()
    val height = integer("height").nullable()
    val weight = integer("weight").nullable()
    val composes = varchar("composes", 255).nullable()
}

object ImageDao: Table("image"){
    val id = integer("id").primaryKey().autoIncrement().uniqueIndex()
    val cardId = integer("card_id").nullable()
    val url = varchar("url", 255).nullable()
    val value = binary("value", 255).nullable()
}
