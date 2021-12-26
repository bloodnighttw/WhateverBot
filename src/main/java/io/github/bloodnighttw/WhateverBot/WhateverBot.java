package io.github.bloodnighttw.WhateverBot;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import io.github.bloodnighttw.WhateverBot.VoiceChannelAutoCraft.VCAutoCraftEventHandler;
import io.github.bloodnighttw.WhateverBot.utils.command.CommandRegister;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.concurrent.TimeUnit;

public class WhateverBot {

	private static CommandRegister commandRegister;

	public static void main(String[] args) throws LoginException, InterruptedException {

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

		loadCommands();
		loadEvent(bot);
	}

	static void loadCommands() throws InterruptedException {
		TimeUnit.SECONDS.sleep(5);
		commandRegister.addToAllServer();
	}

	static void loadEvent(JDA bot) {
		if (System.getenv("CHANNEL_ID") != null)
			bot.addEventListener(new io.github.bloodnighttw.WhateverBot.CodeAutoResend.MessageHandler());
		if (System.getenv("VC_ID") != null)
			bot.addEventListener(new VCAutoCraftEventHandler());

	}
}
