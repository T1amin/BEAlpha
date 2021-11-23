package com.t1.BEAlpha

import com.github.andrewoma.kwery.core.builder.query
import com.t1.BEAlpha.Db.dbQuery
import com.t1.BEAlpha.Db.queryList
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime
import java.sql.ResultSet
import java.time.ZoneId
import java.util.Date


actual class CardService : ICardService {

    override suspend fun getCardsList(search: String?, types: String, sort: Sort) = run{
            dbQuery {
                val query = query {
                    select("SELECT * FROM card")
                    whereGroup {
                        search?.let {
                            where(
                                """(lower(title) like :search
                            OR lower(price) like :search
                            OR lower(category) like :search
                            OR lower(colors) like :search
                            OR lower(locations) like :search
                            OR lower(created_at) like :search
                            OR lower(composes) like :search
                            OR lower(description) like :search)""".trimMargin()
                            )
                        parameter("search", "%${it.lowercase()}%")
                        }
                    }
                    when (sort) {
                        Sort.PRICE -> orderBy("lower(price)")
                        Sort.CATEGORY -> orderBy("lower(category)")
                        Sort.COLORS -> orderBy("lower(colors)")
                        Sort.TITLE -> orderBy("lower(title)")
                        Sort.CREATEAT -> orderBy("lower(created_at)")
                        Sort.LOCATIONS -> orderBy("lower(locations)")
                    }
                }
                queryList(query.sql, query.parameters) {
                    toCard(it)
                }
            }
        }

    override suspend fun addCard(card: Card) = run {
        val key = dbQuery {
            (CardDao.insert {
                it[title] = card.title
                it[price] = card.price
                it[category] = card.category
                it[colors] = card.colors
                it[description] = card.description
                it[img] = card.img
                it[createdAt] = DateTime()
                it[visible] = card.visible
                it[locations] = card.locations
                it[width] = card.width
                it[depth] = card.depth
                it[height] = card.height
                it[weight] = card.weight
                it[composes] = card.composes

            } get CardDao.id)
        }
        getCard(key)!!
    }

    override suspend fun updateCard(card: Card) = run {
        card.id?.let {
            getCard(it)?.let { oldCard ->
                dbQuery {
                    CardDao.update({ CardDao.id eq it }) { e ->
                        e[title] = card.title
                        e[price] = card.price
                        e[category] = card.category
                        e[colors] = card.colors
                        e[description] = card.description
                        e[img] = card.img
                        e[createdAt] = oldCard.createdAt
                            ?.let { DateTime(Date.from(it.atZone(ZoneId.systemDefault()).toInstant())) }
                        e[visible] = card.visible
                        e[locations] = card.locations
                        e[width] = card.width
                        e[depth] = card.depth
                        e[height] = card.height
                        e[weight] = card.weight
                        e[composes] = card.composes
                    }
                }
            }
            getCard(it)
        } ?: throw IllegalArgumentException("The ID of the card not set")
    }

    override suspend fun deleteCard(id: Int): Boolean = run{
        dbQuery {
            CardDao.deleteWhere { (CardDao.id eq id) } > 0
        }
    }

    private suspend fun getCard(id: Int): Card? = dbQuery {
        CardDao.select {
            CardDao.id eq id
        }.mapNotNull { toCard(it) }.singleOrNull()
    }

    private fun toCard(row: ResultRow): Card =
        Card(
            id = row[CardDao.id],
            title = row[CardDao.title],
            price = row[CardDao.price],
            category = row[CardDao.category],
            colors = row[CardDao.colors],
            description = row[CardDao.description],
            img = row[CardDao.img],
            createdAt = row[CardDao.createdAt]?.millis?.let { Date(it) }?.toInstant()
                ?.atZone(ZoneId.systemDefault())?.toLocalDateTime(),
            visible = row[CardDao.visible],
            locations = row[CardDao.locations],
            width = row[CardDao.width],
            depth = row[CardDao.depth],
            height = row[CardDao.height],
            weight = row[CardDao.weight],
            composes = row[CardDao.composes]
        )

    private fun toCard(rs: ResultSet): Card =
        Card(
            id = rs.getInt(CardDao.id.name),
            title = rs.getString(CardDao.title.name),
            price = rs.getInt(CardDao.price.name),
            category = rs.getString(CardDao.category.name),
            colors = rs.getString(CardDao.colors.name),
            description = rs.getString(CardDao.description.name),
            img = rs.getString(CardDao.img.name),
            createdAt = rs.getTimestamp(CardDao.createdAt.name)?.toInstant()
                ?.atZone(ZoneId.systemDefault())?.toLocalDateTime(),
            visible = rs.getBoolean(CardDao.visible.name),
            locations = rs.getString(CardDao.locations.name),
            width = rs.getInt(CardDao.width.name),
            depth = rs.getInt(CardDao.depth.name),
            height = rs.getInt(CardDao.height.name),
            weight = rs.getInt(CardDao.weight.name),
            composes = rs.getString(CardDao.composes.name)
        )
}

