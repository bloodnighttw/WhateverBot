package io.github.bloodnighttw.whateverBot.VCCreator;

import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister;
import net.dv8tion.jda.api.JDA;

public class VCCreatorLoader {

	public static void load(JDA bot, CommandRegister register) {
		VCCreatorEventHandler eventHandler = new VCCreatorEventHandler();
		bot.addEventListener(eventHandler);
	}

}
