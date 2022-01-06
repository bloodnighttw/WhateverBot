package io.github.bloodnighttw.whateverBot.utils.command;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

public interface ICommand {

	default String[] getAlias() {
		return new String[]{};
	}

	CommandData getCommand();

	void commandHandler(@NotNull SlashCommandEvent event);

	default boolean isGlobal() {
		return true;
	}
}
