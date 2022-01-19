package io.github.bloodnighttw.whateverBot.musicBot.eventHandler

import io.github.bloodnighttw.whateverBot.musicBot.removeMusicProviderByGuildId
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent
import net.dv8tion.jda.api.hooks.EventListener

object LeaveEventListener : EventListener {
	override fun onEvent(event: GenericEvent) {
		if (event !is GuildVoiceLeaveEvent)
			return

		if (event.member.user == event.jda.selfUser) {
			removeMusicProviderByGuildId(event.guild.id)
		}

	}
}