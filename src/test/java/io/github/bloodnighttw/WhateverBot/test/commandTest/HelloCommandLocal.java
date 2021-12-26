package io.github.bloodnighttw.WhateverBot.test.commandTest;

import io.github.bloodnighttw.WhateverBot.utils.command.ICommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloCommandLocal implements ICommand {

	private final Logger logger = LoggerFactory.getLogger(HelloCommandLocal.class);

	@Override
	public String[] getAlias() {
		return new String[]{"hello", "h"};
	}

	@Override
	public CommandData getCommand() {
		return new CommandData("hi", "command make bot say hi");
	}

	@Override
	public void commandHandler(@NotNull SlashCommandEvent event) {
		event.getHook().sendMessage("hello here").queue();
		logger.info("hello from console");
	}

	@Override
	public boolean isGlobal() {
		return false;
	}
}
