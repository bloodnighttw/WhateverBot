package io.github.bloodnighttw.whateverBot.utils.extensions

import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.VoiceChannel


fun Member.nowVoiceChannel(): VoiceChannel? {
	for (i in this.guild.voiceChannels) {
		if (this in i.members) {
			return i
		}
	}
	return null
}