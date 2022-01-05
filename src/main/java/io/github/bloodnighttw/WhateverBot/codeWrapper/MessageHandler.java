package io.github.bloodnighttw.WhateverBot.codeWrapper;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageHandler extends ListenerAdapter {

	private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;

		if (event.getChannel().getId().equals(System.getenv("CHANNEL_ID"))) {
			if (event.getMessage().getContentRaw().contains("```"))
				return;

			Language lang = LanguageDetector.detectCode(event.getMessage().getContentRaw());

			switch (lang) {
				case Java:
					event.getChannel().sendMessage("```java\n" + event.getMessage().getContentRaw() + "\n```").queue();
					break;
				case C:
					event.getChannel().sendMessage("```c\n" + event.getMessage().getContentRaw() + "\n```").queue();
					break;
				case CPP:
					event.getChannel().sendMessage("```c++\n" + event.getMessage().getContentRaw() + "\n```").queue();
					break;
			}

		}

	}
}
