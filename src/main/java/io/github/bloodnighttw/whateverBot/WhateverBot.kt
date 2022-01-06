package io.github.bloodnighttw.whateverBot

import ch.qos.logback.classic.Level
import io.github.bloodnighttw.whateverBot.VCCreator.VCCreatorLoader
import io.github.bloodnighttw.whateverBot.codeWrapper.MessageHandler
import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.EventListener
import org.slf4j.LoggerFactory

var commandRegister: CommandRegister? = null

object Ready : EventListener {
    override fun onEvent(event: GenericEvent) {
        if (event is ReadyEvent) {
            load(event.jda)
        }
    }
}

fun load(bot: JDA) {
    commandRegister = CommandRegister(bot)
    if (System.getenv("CHANNEL_ID") != null) bot.addEventListener(MessageHandler())
    if (System.getenv("VC_ID") != null) VCCreatorLoader.load(bot, commandRegister)
    commandRegister!!.addToAllServer()
}

fun main(args: Array<String>) {

    for (a in args) {
        when (a) {
            "-d", "-debug" -> run {
                val root = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME) as ch.qos.logback.classic.Logger
                root.level = Level.DEBUG
            }

        }
    }

    JDABuilder.createDefault(System.getenv("TOKEN")!!)
            .addEventListeners(Ready)
            .build()
}