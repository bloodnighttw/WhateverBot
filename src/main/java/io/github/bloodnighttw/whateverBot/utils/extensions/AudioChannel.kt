package io.github.bloodnighttw.whateverBot.utils.extensions

import net.dv8tion.jda.api.entities.AudioChannel
import net.dv8tion.jda.api.entities.Category

fun AudioChannel.category(): Category? {
	for (category in this.guild.categories) {
		if (this in category.voiceChannels)
			return category
	}
	return null
}