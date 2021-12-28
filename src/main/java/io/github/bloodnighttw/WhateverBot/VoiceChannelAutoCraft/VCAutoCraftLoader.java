package io.github.bloodnighttw.WhateverBot.VoiceChannelAutoCraft;

import io.github.bloodnighttw.WhateverBot.utils.command.CommandRegister;
import net.dv8tion.jda.api.JDA;

public class VCAutoCraftLoader {

	public static void load(JDA bot, CommandRegister register) {
		bot.addEventListener(new VCAutoCraftEventHandler());
		register.registerCommand(new PrivateCommand());
	}

}
