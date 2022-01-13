package io.github.bloodnighttw.whateverBot.voiceChannelCreator

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object CreatorChannelTable : Table() {
    var channelID = varchar("channel_id", 50).default("0")
    var guildID = varchar("guild_id", 50).default("0")
}

fun CreatorChannelTable.exist(channel: String, guild: String): Boolean = transaction {
    return@transaction !CreatorChannelTable.select { channelID.eq(channel) and guildID.eq(guild) }.empty()
}

fun addInfoToCreatorChannelTable(channel: String, guild: String) {

    if (!CreatorChannelTable.exist(channel, guild)) {
        transaction {
            CreatorChannelTable.insert {
                it[channelID] = channel
                it[guildID] = guild
            }
        }
    }
}