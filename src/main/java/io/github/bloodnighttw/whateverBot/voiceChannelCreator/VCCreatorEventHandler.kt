package io.github.bloodnighttw.whateverBot.voiceChannelCreator

import io.github.bloodnighttw.whateverBot.utils.extensions.category
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.AudioChannel
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.hooks.EventListener
import org.slf4j.LoggerFactory


class VCCreatorEventHandler : EventListener {
    private val logger = LoggerFactory.getLogger(VCCreatorEventHandler::class.java)
    private val vcOwner: HashMap<AudioChannel?, String> = HashMap()

    override fun onEvent(event: GenericEvent) {
        if (event is GuildVoiceUpdateEvent) {
            onGuildVoiceUpdate(event)
        }
    }

    private fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {

        event.channelJoined?.let { vc ->
            if (CreatorChannelTable.exist(vc.id, event.guild.id)) {
                vc.category()?.createVoiceChannel(event.member.nickname!!)?.queue {
                    it.createPermissionOverride(event.member)
                            .setAllow(Permission.VOICE_MUTE_OTHERS, Permission.VOICE_MOVE_OTHERS)
                            .queue()
                    event.guild.moveVoiceMember(event.member, it).queue()
                }
            }
        }


    }
}