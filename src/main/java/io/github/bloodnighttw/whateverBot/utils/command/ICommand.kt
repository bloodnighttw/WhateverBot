package io.github.bloodnighttw.whateverBot.utils.command

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

interface ICommand {
	val alias: Array<String>
		get() = arrayOf()
	val command: CommandData
	fun commandHandler(event: SlashCommandEvent) {
		println(event.subcommandName)
		subCommandMap[event.subcommandName]?.subCommandHandler(event)
	}

	val isGlobal: Boolean
		get() = false
	val subCommandMap: Map<String, ISubCommand>
		get() = mapOf()
}