package io.github.bloodnighttw.WhateverBot.VoiceChannelAutoCraft;

import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

public class VCAutoCraftEventHandler extends ListenerAdapter {

	private final Logger logger = LoggerFactory.getLogger(VCAutoCraftEventHandler.class);
	private HashSet<AudioChannel> vcBotCreated;

	public VCAutoCraftEventHandler() {
		vcBotCreated = new HashSet<>();
	}

	@Override
	public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {


	}


	@Override
	public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {

		if (event.getChannelJoined() != null) {

			if (event.getChannelJoined().getId().equals(System.getenv("VC_ID"))) {
				L1:
				for (Category it : event.getGuild().getCategories()) {
					for (VoiceChannel vcit : it.getVoiceChannels()) {
						if (vcit.getId().equals(System.getenv("VC_ID"))) {

							logger.debug(vcit.getId() + "  " + System.getenv("VC_ID") + "  " + vcit.getId().equals(System.getenv("VC_ID")));

							it.createVoiceChannel(" + " + event.getMember().getUser().getName() + " + ").queue(
									voiceChannel -> {
										voiceChannel.getGuild().moveVoiceMember(event.getMember(), voiceChannel).queue();
										vcBotCreated.add(voiceChannel);
										logger.debug("create channel for user" + event.getMember().getUser().getName() + " in " + voiceChannel.getId());
									}
							);
							break L1;
						}
					}
				}

			}

		}

		if (event.getChannelLeft() != null) {

			if (vcBotCreated.contains(event.getChannelLeft())) {
				if (event.getChannelLeft().getMembers().size() == 0) {
					event.getChannelLeft().delete().queue();
				}
			}

		}


	}

	@Override
	public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {

	}
}
