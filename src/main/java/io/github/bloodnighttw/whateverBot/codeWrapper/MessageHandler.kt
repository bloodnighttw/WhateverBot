package io.github.bloodnighttw.whateverBot.codeWrapper

import io.github.bloodnighttw.whateverBot.codeWrapper.Language.Other
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener

class MessageHandler : EventListener {
    override fun onEvent(event: GenericEvent) {
        if (event is MessageReceivedEvent) {
            val lang = detectCode(event.message.contentRaw)

            if (event.author.isBot || event.message.contentRaw.contains("```") || lang == Other) return

            event.channel.sendMessage("```" + lang + "\n" + event.message.contentRaw + "```").queue()
        }
    }
}