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
                            OR lower(tags) like :search
                            OR lower(created_at) like :search)""".trimMargin()
                            )
                        parameter("search", "%${it.lowercase()}%")
                        }
                    }
                    when (sort) {
                        Sort.PRICE -> orderBy("lower(title)")
                        Sort.CATEGORY -> orderBy("lower(price)")
                        Sort.TAG -> orderBy("lower(category)")
                        Sort.TITLE -> orderBy("lower(tags)")
                        Sort.CREATEAT -> orderBy("lower(created_at)")
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
                it[tags] = card.tags
                it[description] = card.description
                it[img] = card.img
                it[createdAt] = DateTime()

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
                        e[tags] = card.tags
                        e[description] = card.description
                        e[img] = card.img
                        e[createdAt] = oldCard.createdAt
                            ?.let { DateTime(Date.from(it.atZone(ZoneId.systemDefault()).toInstant())) }
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
            tags = row[CardDao.tags],
            description = row[CardDao.description],
            img = row[CardDao.img],
            createdAt = row[CardDao.createdAt]?.millis?.let { Date(it) }?.toInstant()
                ?.atZone(ZoneId.systemDefault())?.toLocalDateTime(),
        )

    private fun toCard(rs: ResultSet): Card =
        Card(
            id = rs.getInt(CardDao.id.name),
            title = rs.getString(CardDao.title.name),
            price = rs.getInt(CardDao.price.name),
            category = rs.getString(CardDao.category.name),
            tags = rs.getString(CardDao.tags.name),
            description = rs.getString(CardDao.description.name),
            img = rs.getString(CardDao.img.name),
            createdAt = rs.getTimestamp(CardDao.createdAt.name)?.toInstant()
                ?.atZone(ZoneId.systemDefault())?.toLocalDateTime(),
        )
}

