package io.github.bloodnighttw.whateverBot.voiceChannelCreator;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class VCCreatorEventHandler implements EventListener {

	private final Logger logger = LoggerFactory.getLogger(VCCreatorEventHandler.class);

	public HashMap<AudioChannel, String> getVcOwner() {
		return vcOwner;
	}

	private final HashMap<AudioChannel, String> vcOwner;

	public VCCreatorEventHandler() {
		vcOwner = new HashMap<>();
	}

	@Override
	public void onEvent(@NotNull GenericEvent event) {

		if (event instanceof GuildVoiceUpdateEvent) {
			onGuildVoiceUpdate((GuildVoiceUpdateEvent) event);
		}

	}

	public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {

		if (event.getChannelJoined() != null) {

			if (event.getChannelJoined().getId().equals(System.getenv("VC_ID"))) {

				L1:
				for (Category it : event.getGuild().getCategories()) {
					for (VoiceChannel voiceChannelIterator : it.getVoiceChannels()) {

						if (voiceChannelIterator.getId().equals(System.getenv("VC_ID"))) {

							logger.debug(voiceChannelIterator.getId() + "  " + System.getenv("VC_ID") +
									"  " + voiceChannelIterator.getId().equals(System.getenv("VC_ID")));

							it.createVoiceChannel(" + " + event.getMember().getUser().getName() + " + ").queue(
									voiceChannel -> {
										voiceChannel.getGuild().moveVoiceMember(event.getMember(), voiceChannel).queue();
										vcOwner.put(voiceChannel, event.getMember().getUser().getId());

										logger.debug("create channel for user" +
												event.getMember().getUser().getName() + " in " + voiceChannel.getId());

										voiceChannel.getManager().getChannel()
												.createPermissionOverride(event.getMember())
												.setAllow(Permission.VOICE_MOVE_OTHERS)
												.queue();
									});

							break L1;
						}

					}
				}

			}

		}

		if (event.getChannelLeft() != null) {

			if (vcOwner.containsKey(event.getChannelLeft())) {
				if (event.getChannelLeft().getMembers().size() == 0) {
					vcOwner.remove(event.getChannelLeft());
					event.getChannelLeft().delete().queue();
				}
			}

		}

	}


}
