package io.github.bloodnighttw.whateverBot.musicBot

import io.github.bloodnighttw.whateverBot.utils.command.ICommand
import io.github.bloodnighttw.whateverBot.utils.extensions.isUrl
import io.github.bloodnighttw.whateverBot.utils.extensions.nowVoiceChannel
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

object Play : ICommand {
	override val command: SlashCommandData
		get() = Commands.slash("play", "join command")
			.addOption(OptionType.STRING, "name", "url or song name", true)

	override fun commandHandler(event: SlashCommandInteractionEvent) {
		val guildMusicManager = event.member?.nowVoiceChannel()?.let { getMusicProviderByGuild(event.guild!!, it) }
		event.hook.sendMessage("Play ths song for u").queue()

		event.getOption("name")?.asString?.let {
			if (it.isUrl()) {
				guildMusicManager?.loadUrl(it)
			} else
				guildMusicManager?.search(it)
		}
	}

}

object Pause : ICommand {
	override val command: SlashCommandData
		get() = Commands.slash("pause", "pause the music")

	override fun commandHandler(event: SlashCommandInteractionEvent) {

		event.member?.nowVoiceChannel()?.let { getMusicProviderByGuild(event.guild!!, it).pause() } ?: run { print("hi") }
		event.hook.sendMessage("Pause the song").queue()
	}
}

object Resume : ICommand {
	override val command: SlashCommandData
		get() = Commands.slash("resume", "resume the play")

	override fun commandHandler(event: SlashCommandInteractionEvent) {
		event.member?.nowVoiceChannel()?.let { getMusicProviderByGuild(event.guild!!, it).resume() }
		event.hook.sendMessage("Resume the play").queue()
	}

}

object Skip : ICommand {
	override val command: SlashCommandData
		get() = Commands.slash("skip", "skip the song")

	override fun commandHandler(event: SlashCommandInteractionEvent) {
		event.member?.nowVoiceChannel()?.let { getMusicProviderByGuild(event.guild!!, it).next() }
		event.hook.sendMessage("skip the song").queue()
	}

}

object Stop : ICommand {
	override val command: SlashCommandData
		get() = Commands.slash("stop", "stop and leave")

	override fun commandHandler(event: SlashCommandInteractionEvent) {
		event.member?.nowVoiceChannel()?.let { getMusicProviderByGuild(event.guild!!, it).stop() }
		event.guild?.audioManager?.closeAudioConnection()
		event.hook.sendMessage("Done").queue()
	}

}

object Loop : ICommand {
	override val command: SlashCommandData
		get() = Commands.slash("loop", "loop the queue")

	override fun commandHandler(event: SlashCommandInteractionEvent) {
		event.member?.nowVoiceChannel()?.let {
			getMusicProviderByGuild(event.guild!!, it).toggleLoop()
			val a = getMusicProviderByGuild(event.guild!!, it).loop
			event.hook.sendMessage("Toggle Loop Mode to ``$a``").queue()
		}
	}

}

object NowPlaying : ICommand {

	override val alias: Array<String>
		get() = arrayOf("np")

	override val command: SlashCommandData
		get() = Commands.slash("nowplaying", "now playing")

	override fun commandHandler(event: SlashCommandInteractionEvent) {
		val track = event.member?.nowVoiceChannel()?.let { getMusicProviderByGuild(event.guild!!, it).nowPlaying() }
		if (track != null) {
			event.hook.sendMessage("now playing ${track.info.title}").queue()
		}
	}

}