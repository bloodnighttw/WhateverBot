package io.github.bloodnighttw.whateverBot.musicBot

import io.github.bloodnighttw.whateverBot.utils.command.ICommand
import io.github.bloodnighttw.whateverBot.utils.extensions.isUrl
import io.github.bloodnighttw.whateverBot.utils.extensions.nowVoiceChannel
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData

object JoinCommand : ICommand {
	override val command: CommandData
		get() = CommandData("play", "join command")
			.addOption(OptionType.STRING, "name", "url or song name", true)

	override fun commandHandler(event: SlashCommandEvent) {
		val guildMusicManager = event.member?.nowVoiceChannel()?.let { getMusicProviderByGuild(event.guild!!, it) }
		//guildMusicManager.search("alan walker alone")

		event.getOption("name")?.asString?.let {
			if (it.isUrl()) {
				guildMusicManager?.loadUrl(it)
			} else
				guildMusicManager?.search(it)
		}
	}

}

object Pause : ICommand {
	override val command: CommandData
		get() = CommandData("pause", "pause the music")

	override fun commandHandler(event: SlashCommandEvent) {
		event.member?.nowVoiceChannel()?.let { getMusicProviderByGuild(event.guild!!, it).pause() }
		event.hook.sendMessage("Pause the song").queue()
	}
}

object Resume : ICommand {
	override val command: CommandData
		get() = CommandData("resume", "resume the play")

	override fun commandHandler(event: SlashCommandEvent) {
		event.member?.nowVoiceChannel()?.let { getMusicProviderByGuild(event.guild!!, it).resume() }
		event.hook.sendMessage("Resume the play").queue()
	}

}