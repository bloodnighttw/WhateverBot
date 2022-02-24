package io.github.bloodnighttw.whateverBot.utils.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

interface ICommand {
	val alias: Array<String>
		get() = arrayOf()
	val command: SlashCommandData
	fun commandHandler(event: SlashCommandInteractionEvent) {
		println(event.subcommandName)
		subCommandMap[event.subcommandName]?.subCommandHandler(event)
	}

	val isGlobal: Boolean
		get() = true
	val subCommandMap: Map<String, ISubCommand>
		get() = mapOf()
}