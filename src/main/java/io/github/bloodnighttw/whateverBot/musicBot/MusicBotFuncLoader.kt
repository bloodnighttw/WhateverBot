package io.github.bloodnighttw.whateverBot.musicBot

import io.github.bloodnighttw.whateverBot.musicBot.eventHandler.LeaveEventListener
import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister
import net.dv8tion.jda.api.JDA

fun loadMusicBotFunc(bot: JDA, register: CommandRegister?) {
	allAvailableJDAList.add(bot)
	register?.registerCommand(JoinCommand)
	register?.registerCommand(Pause)
	register?.registerCommand(Resume)
	bot.addEventListener(LeaveEventListener)
}