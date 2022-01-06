package io.github.bloodnighttw.whateverBot;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import io.github.bloodnighttw.whateverBot.VCCreator.VCCreatorLoader;
import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class WhateverBotOld {

	private static CommandRegister commandRegister;

	public static void main(String[] args) throws LoginException {

		for (String it : args) {
			switch (it) {
				case "-d":
				case "-debug":
					Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
					root.setLevel(Level.DEBUG);
					System.out.println("Enable Debug Mode");
					break;
			}
		}

		JDA bot = JDABuilder.createDefault(System.getenv("TOKEN")).build();
		commandRegister = new CommandRegister(bot);


		bot.addEventListener((EventListener) event -> {
			if (event instanceof ReadyEvent) {
				load(bot);
				commandRegister.addToAllServer();
			}
		});

	}

	static void load(JDA bot) {
		if (System.getenv("CHANNEL_ID") != null)
			bot.addEventListener(new io.github.bloodnighttw.whateverBot.codeWrapper.MessageHandler());
		if (System.getenv("VC_ID") != null)
			VCCreatorLoader.load(bot, commandRegister);

	}
}
