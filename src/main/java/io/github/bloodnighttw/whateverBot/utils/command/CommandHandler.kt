package io.github.bloodnighttw.whateverBot.utils.command;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler implements EventListener {

	private final Logger logger = LoggerFactory.getLogger(CommandHandler.class);
	private final ICommand iCommand;
	private boolean ephemeral = false;

	public CommandHandler(ICommand iCommand) {
		this.iCommand = iCommand;
	}

	public CommandHandler(ICommand iCommand, boolean ephemeral) {
		this.iCommand = iCommand;
		this.ephemeral = ephemeral;
	}

	@Override
	public void onEvent(@NotNull GenericEvent event) {
		if (event instanceof SlashCommandEvent) {

			SlashCommandEvent slashCommandEvent = (SlashCommandEvent) event;

			if (slashCommandEvent.getName().equals(iCommand.getCommand().getName())) {
				slashCommandEvent.deferReply().setEphemeral(ephemeral).queue();
				iCommand.commandHandler(slashCommandEvent);
			}

			for (String it : iCommand.getAlias()) {
				if (slashCommandEvent.getName().equals(it)) {
					iCommand.commandHandler(slashCommandEvent);
				}
			}
		}
	}
}
