package io.github.bloodnighttw.whateverBot.voiceChannelCreator


import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.hooks.EventListener



class VCCreatorEventHandler : EventListener {

	override fun onEvent(event: GenericEvent) {
		if (event is GuildVoiceUpdateEvent) {
			onGuildVoiceUpdate(event)
		}
	}

	private fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {

		event.channelJoined?.let { vc ->

		}

		event.channelLeft?.let { vc ->

		}
	}
}