package io.github.bloodnighttw.WhateverBot.VoiceChannelAutoCraft;

import io.github.bloodnighttw.WhateverBot.utils.command.ICommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

public class PrivateCommand implements ICommand {
	@Override
	public CommandData getCommand() {
		return new CommandData("private", "a private command");
	}

	@Override
	public void commandHandler(@NotNull SlashCommandEvent event) {
		event.getHook().sendMessage("hi").queue();
	}

	@Override
	public boolean isGlobal() {
		return true;
	}
}
