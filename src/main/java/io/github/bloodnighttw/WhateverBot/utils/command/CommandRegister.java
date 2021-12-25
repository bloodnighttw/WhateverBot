package io.github.bloodnighttw.WhateverBot.utils.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CommandRegister {

  private JDA bot;
  private final Logger logger = LoggerFactory.getLogger(CommandRegister.class);
  private ArrayList<CommandData> commandDataLocalList;
  private ArrayList<CommandData> commandDataGlobalList;

  public CommandRegister(JDA bot) {
    this.bot = bot;
    commandDataLocalList = new ArrayList<>();
    commandDataGlobalList = new ArrayList<>();
  }

  public void registerCommand(ICommand iCommand) {
    if (!iCommand.isGlobal()) {
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

    logger.debug("Add Command....");
  }

  public void addToAllServer() {

    bot.updateCommands().addCommands(commandDataGlobalList).queue();

    for (Guild guild : bot.getGuilds()) {
      logger.debug("Add command to server: " + guild.getName());
      guild
          .updateCommands()
          .addCommands(commandDataLocalList)
          .addCommands(commandDataGlobalList)
          .queue();
    }
  }
}
