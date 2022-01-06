package io.github.bloodnighttw.whateverBot.utils.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CommandRegister {

	private final JDA bot;
	private final Logger logger = LoggerFactory.getLogger(CommandRegister.class);
	private final ArrayList<CommandData> commandDataLocalList;
	private final ArrayList<CommandData> commandDataGlobalList;

	public CommandRegister(JDA bot) {
		this.bot = bot;
		commandDataLocalList = new ArrayList<>();
		commandDataGlobalList = new ArrayList<>();
	}

	public void registerCommand(ICommand iCommand) {
		if (iCommand.isGlobal()) {
			commandDataLocalList.add(iCommand.getCommand());
			for (String alias : iCommand.getAlias()) {
				commandDataLocalList.add(new CommandData(alias, iCommand.getCommand().getDescription()));
			}
		} else {
			commandDataGlobalList.add(iCommand.getCommand());
			for (String alias : iCommand.getAlias()) {
				commandDataGlobalList.add(new CommandData(alias, iCommand.getCommand().getDescription()));
			}
		}

		bot.addEventListener(new CommandHandler(iCommand));

	}

	public void registerPrivateCommand(ICommand iCommand) {
		if (iCommand.isGlobal()) {
			commandDataLocalList.add(iCommand.getCommand());
			for (String alias : iCommand.getAlias()) {
				commandDataLocalList.add(new CommandData(alias, iCommand.getCommand().getDescription()));
			}
		} else {
			commandDataGlobalList.add(iCommand.getCommand());
			for (String alias : iCommand.getAlias()) {
				commandDataGlobalList.add(new CommandData(alias, iCommand.getCommand().getDescription()));
			}
		}
		bot.addEventListener(new CommandHandler(iCommand, true));
	}

	public void addToAllServer() {

		bot.updateCommands().addCommands(commandDataGlobalList).queue();

		for (Guild guild : bot.getGuilds()) {

			logger.debug("Guild Found Name:" + guild.getName());

			logger.debug("Add command to server: " + guild.getName());
			guild.updateCommands()
					.addCommands(commandDataLocalList)
					.addCommands(commandDataGlobalList)
					.queue();
		}
	}
}
