package io.github.bloodnighttw.whateverBot.voiceChannelCreator

import io.github.bloodnighttw.whateverBot.utils.extensions.category
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.hooks.EventListener
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction


class VCCreatorEventHandler : EventListener {

	override fun onEvent(event: GenericEvent) {
		if (event is GuildVoiceUpdateEvent) {
			onGuildVoiceUpdate(event)
		}
	}

	private fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {

		event.channelJoined?.let { vc ->
			if (CreatorChannelTable.exist(vc.id, event.guild.id)) {
				vc.category()?.createVoiceChannel(event.member.nickname ?: event.member.user.name)?.queue { newVC ->
					newVC.createPermissionOverride(event.member)
							.setAllow(Permission.VOICE_MUTE_OTHERS, Permission.VOICE_MOVE_OTHERS)
							.queue()

					event.guild.moveVoiceMember(event.member, newVC).queue()

					transaction {
						CreatorTable.insert {
							it[guildID] = event.guild.id
							it[channelID] = newVC.id
						}
					}

				}
			}
		}

		event.channelLeft?.let { vc ->
			if (CreatorTable.exist(vc.id, event.guild.id) && vc.members.isEmpty()) {

				transaction {
					CreatorTable.deleteWhere { CreatorTable.channelID eq vc.id }
				}

				vc.delete().queue()
			}
		}
	}
}