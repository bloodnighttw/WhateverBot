package io.github.bloodnighttw.whateverBot.voiceChannelCreator

import io.github.bloodnighttw.whateverBot.utils.command.ICommand
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

object SetVC : ICommand {
    override val command: CommandData
        get() = CommandData("setvc", "setup voice channel")

    override fun commandHandler(event: SlashCommandEvent) {

        if (!event.member!!.hasPermission(Permission.ADMINISTRATOR)) {
            event.hook.sendMessage("You don't have permission").queue()
            return
        }

        for (category in event.guild!!.categories)
            for (vc in category.voiceChannels)
                if (event.member in vc.members) {
                    println("hello")
                    addInfoToCreatorChannelTable(vc.id, event.guild!!.id)
                    event.hook.sendMessage("Find Your channel,add success").queue()
                    return
                }

        event.hook.sendMessage("you are not joined any voice channel").queue()
    }
}

object DeleateVoiceChannel : ICommand {
    override val command: CommandData
        get() = TODO("Not yet implemented")

    override fun commandHandler(event: SlashCommandEvent) {
        TODO("Not yet implemented")
    }

}