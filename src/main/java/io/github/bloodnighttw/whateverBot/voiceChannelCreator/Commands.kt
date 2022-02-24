package io.github.bloodnighttw.whateverBot.voiceChannelCreator

import io.github.bloodnighttw.whateverBot.utils.command.ICommand
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

object SetVC : ICommand {
	override val command: SlashCommandData
		get() = Commands.slash("setvc", "setup voice channel")

	override fun commandHandler(event: SlashCommandInteractionEvent) {

		if (!event.member!!.hasPermission(Permission.ADMINISTRATOR)) {
			event.hook.sendMessage("You don't have permission").queue()
			return
		}

		for (category in event.guild!!.categories)
			for (vc in category.voiceChannels)
				if (event.member in vc.members) {
					println("hello")
					addInfoToCreatorChannelTable(vc.id, event.guild!!.id)
					event.hook.sendMessage("Find Your channel,add success").queue()
					return
				}

		event.hook.sendMessage("you are not joined any voice channel").queue()
	}
}

object DeleteALLVoiceChannel : ICommand {
	override val command: SlashCommandData
		get() = Commands.slash("removeallvc", "remove all voice channel")

	override fun commandHandler(event: SlashCommandInteractionEvent) {

		if (!event.member!!.hasPermission(Permission.ADMINISTRATOR)) {
			event.hook.sendMessage("You are not admin of this server").queue()
			return
		}

		transaction {
			CreatorChannelTable.deleteWhere { CreatorChannelTable.guildID eq event.guild!!.id }
		}

		event.hook.sendMessage("Success deleate all voice channel!").queue()

	}

}