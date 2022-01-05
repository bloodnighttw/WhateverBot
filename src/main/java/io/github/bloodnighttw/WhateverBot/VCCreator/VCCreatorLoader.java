package io.github.bloodnighttw.WhateverBot.VCCreator;

import io.github.bloodnighttw.WhateverBot.utils.command.CommandRegister;
import net.dv8tion.jda.api.JDA;

public class VCCreatorLoader {

	public static void load(JDA bot, CommandRegister register) {
		VCCreatorEventHandler eventHandler = new VCCreatorEventHandler();
		bot.addEventListener(eventHandler);
	}

}
