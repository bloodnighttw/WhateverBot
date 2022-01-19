package io.github.bloodnighttw.whateverBot.musicBot

import io.github.bloodnighttw.whateverBot.musicBot.eventHandler.LeaveEventListener
import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister
import net.dv8tion.jda.api.JDA

fun loadMusicBotFunc(bot: JDA, register: CommandRegister?) {
	allAvailableJDAList.add(bot)
	register?.registerCommand(Play)
	register?.registerCommand(Pause)
	register?.registerCommand(Resume)
	register?.registerCommand(Skip)
	register?.registerCommand(Stop)
	register?.registerCommand(Loop)
	register?.registerCommand(NowPlaying)
	bot.addEventListener(LeaveEventListener)
}