package io.github.bloodnighttw.WhateverBot.VoiceChannelAutoCraft;

import io.github.bloodnighttw.WhateverBot.utils.command.ICommand;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PrivateCommand implements ICommand {

	private final HashMap<AudioChannel, String> VCOwner;


	public PrivateCommand(HashMap<AudioChannel, String> VCOwner) {
		this.VCOwner = VCOwner;
	}

	@Override
	public CommandData getCommand() {
		return new CommandData("private", "a private command");
	}

	@Override
	public void commandHandler(@NotNull SlashCommandEvent event) {
		List<VoiceChannel> list = Objects.requireNonNull(event.getGuild()).getVoiceChannels();
		for (AudioChannel iterator : event.getGuild().getVoiceChannels()) {
			if (VCOwner.containsKey(iterator) && VCOwner.get(iterator).equals(Objects.requireNonNull(event.getMember()).getUser().getId())) {
				event.getHook().sendMessage("hello here").queue();
				return;
			}
		}
		event.getHook().sendMessage("can't find the channel crafted for you").queue();
	}

	@Override
	public boolean isGlobal() {
		return false;
	}
}
