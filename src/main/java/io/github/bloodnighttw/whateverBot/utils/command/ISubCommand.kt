package io.github.bloodnighttw.whateverBot.utils.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData

interface ISubCommand {
	val subCommand: SubcommandData
	fun subCommandHandler(slashCommandEvent: SlashCommandInteractionEvent)
}