package io.github.bloodnighttw.whateverBot.utils.command

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.EventListener
import org.slf4j.LoggerFactory

class CommandHandler : EventListener {
	private val iCommand: ICommand
	private var ephemeral = false

	constructor(iCommand: ICommand) {
		this.iCommand = iCommand
	}

	constructor(iCommand: ICommand, ephemeral: Boolean) {
		this.iCommand = iCommand
		this.ephemeral = ephemeral
	}

	override fun onEvent(event: GenericEvent) {
		if (event is SlashCommandInteractionEvent) {
			if (event.name == iCommand.command.name || event.name in iCommand.alias) {
				event.deferReply().setEphemeral(ephemeral).queue()
				iCommand.commandHandler(event)
			}

		}
	}
}