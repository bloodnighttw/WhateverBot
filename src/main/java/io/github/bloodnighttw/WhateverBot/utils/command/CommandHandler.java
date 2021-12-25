package io.github.bloodnighttw.WhateverBot.utils.command;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler implements EventListener {

  private Logger logger = LoggerFactory.getLogger(CommandHandler.class);
  private ICommand iCommand;

  public CommandHandler(ICommand iCommand) {
    this.iCommand = iCommand;
  }

  @Override
  public void onEvent(@NotNull GenericEvent event) {
    if (event instanceof SlashCommandEvent) {

      SlashCommandEvent slashCommandEvent = (SlashCommandEvent) event;
      slashCommandEvent.deferReply().queue();

      if (slashCommandEvent.getName().equals(iCommand.getCommand().getName())) {
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
