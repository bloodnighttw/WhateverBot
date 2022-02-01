package io.github.bloodnighttw.whateverBot.utils.command

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData

interface ISubCommand {
	val subCommand: SubcommandData
	fun subCommandHandler(slashCommandEvent: SlashCommandEvent)
}