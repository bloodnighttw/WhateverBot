package io.github.bloodnighttw.WhateverBot.VoiceChannelAutoCraft;

import io.github.bloodnighttw.WhateverBot.utils.command.CommandRegister;
import net.dv8tion.jda.api.JDA;

public class VCAutoCraftLoader {

	public static void load(JDA bot, CommandRegister register) {
		VCAutoCraftEventHandler eventHandler = new VCAutoCraftEventHandler();
		bot.addEventListener(eventHandler);
		register.registerPrivateCommand(new PrivateCommand(eventHandler.getVcOwner()));
	}

}
