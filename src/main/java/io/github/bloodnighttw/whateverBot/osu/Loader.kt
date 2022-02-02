package io.github.bloodnighttw.whateverBot.osu

import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister
import net.dv8tion.jda.api.JDA
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun loadOsuFunction(bot: JDA, register: CommandRegister?) {
	transaction {
		SchemaUtils.createMissingTablesAndColumns(OsuUserLink)
	}
	register?.registerCommand(Command)
}