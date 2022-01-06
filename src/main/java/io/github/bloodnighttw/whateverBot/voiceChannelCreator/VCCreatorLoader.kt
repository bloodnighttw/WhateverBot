package io.github.bloodnighttw.whateverBot.voiceChannelCreator

import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister
import net.dv8tion.jda.api.JDA

fun voiceChannelCreatorLoad(bot: JDA, register: CommandRegister?) {
    val eventHandler = VCCreatorEventHandler()
    bot.addEventListener(eventHandler)
}
