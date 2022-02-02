package io.github.bloodnighttw.whateverBot.osu

import io.github.bloodnighttw.whateverBot.utils.command.ICommand
import io.github.bloodnighttw.whateverBot.utils.command.ISubCommand
import net.dv8tion.jda.api.interactions.commands.build.CommandData

object Command : ICommand {
	override val command: CommandData
		get() = CommandData("osu", "osu command")

	override val subCommandMap: Map<String, ISubCommand>
		get() = mapOf(
			"setuser" to SetUser,
			"recent" to Recent
		)
}