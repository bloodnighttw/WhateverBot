package io.github.bloodnighttw.whateverBot.voiceChannelCreator

import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister
import net.dv8tion.jda.api.JDA
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun voiceChannelCreatorLoad(bot: JDA, register: CommandRegister?) {
	val eventHandler = VCCreatorEventHandler()
	bot.addEventListener(eventHandler)
	register?.registerPrivateCommand(SetVC)
	register?.registerPrivateCommand(DeleteALLVoiceChannel)
	transaction {
		SchemaUtils.createMissingTablesAndColumns(CreatorChannelTable)
		SchemaUtils.createMissingTablesAndColumns(CreatorTable)
	}
}
