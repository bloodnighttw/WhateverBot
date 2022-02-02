package io.github.bloodnighttw.whateverBot.osu

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object OsuUserLink : Table() {
	var osuUserId = long("osu_user_id")
	var discordUserId = varchar("discord_user_id", length = 50).default("0")
}

fun OsuUserLink.exist(userId: String) = transaction {
	return@transaction !OsuUserLink.select { discordUserId eq userId }.empty()
}

fun OsuUserLink.getOsuUsername(userId: String): Long = transaction {
	return@transaction OsuUserLink.select { discordUserId eq userId }.first()[osuUserId]
}