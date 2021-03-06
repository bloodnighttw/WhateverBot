package io.github.bloodnighttw.whateverBot.codeWrapper

import io.github.bloodnighttw.whateverBot.codeWrapper.Language.Other
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MessageHandler : EventListener {

	private val logger: Logger = LoggerFactory.getLogger(MessageHandler::class.java)

	override fun onEvent(event: GenericEvent) {
		if (event is MessageReceivedEvent) {
			val lang = detectCode(event.message.contentRaw)

			if (event.author.isBot || event.message.contentRaw.contains("```") || lang == Other) return
			logger.debug("Find $lang")

			event.channel.sendMessage("```" + lang + "\n" + event.message.contentRaw + "```").queue()

		}
	}
}