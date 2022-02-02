@file:JvmName(name = "WhateverBot")

package io.github.bloodnighttw.whateverBot

import ch.qos.logback.classic.Level
import io.github.bloodnighttw.whateverBot.codeWrapper.MessageHandler
import io.github.bloodnighttw.whateverBot.musicBot.loadMusicBotFunc
import io.github.bloodnighttw.whateverBot.osu.loadOsuFunction
import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister
import io.github.bloodnighttw.whateverBot.voiceChannelCreator.voiceChannelCreatorLoad
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.EventListener
import org.jetbrains.exposed.sql.Database
import org.slf4j.LoggerFactory
import kotlin.system.exitProcess

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
	bot.addEventListener(MessageHandler())
	voiceChannelCreatorLoad(bot, commandRegister)
	loadMusicBotFunc(bot, commandRegister)
	loadOsuFunction(bot, commandRegister)
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

	System.getenv("JDBC_DATABASE_URL")?.let {
		Database.connect(it, user = System.getenv("DB_USER") ?: "", password = System.getenv("DB_PASSWORD") ?: "")
	} ?: run {
		val s = """
            ██████  ██      ███████     ███████ ███    ██ ████████ ███████ ██████      ██    ██  ██████  ██    ██ ██████      
            ██   ██ ██      ██          ██      ████   ██    ██    ██      ██   ██      ██  ██  ██    ██ ██    ██ ██   ██     
            ██████  ██      ███████     █████   ██ ██  ██    ██    █████   ██████        ████   ██    ██ ██    ██ ██████      
            ██      ██           ██     ██      ██  ██ ██    ██    ██      ██   ██        ██    ██    ██ ██    ██ ██   ██     
            ██      ███████ ███████     ███████ ██   ████    ██    ███████ ██   ██        ██     ██████   ██████  ██   ██                                                                                                                   
                                                                                                                              
                 ██ ██████  ██████   ██████     ██    ██  █████  ██      ██    ██ ███████                                     
                 ██ ██   ██ ██   ██ ██          ██    ██ ██   ██ ██      ██    ██ ██                                          
                 ██ ██   ██ ██████  ██          ██    ██ ███████ ██      ██    ██ █████                                       
            ██   ██ ██   ██ ██   ██ ██           ██  ██  ██   ██ ██      ██    ██ ██                                          
             █████  ██████  ██████   ██████       ████   ██   ██ ███████  ██████  ███████       
                                           
            ██ ███    ██                                                                                   
            ██ ████   ██                                                                                   
            ██ ██ ██  ██                                                                                   
            ██ ██  ██ ██                                                                                   
            ██ ██   ████                                                                                   
                                                                                                                                                                                                              
            ███████ ███    ██ ██    ██ ██ ██████   ██████  ███    ██ ███    ███ ███████ ███    ██ ████████ 
            ██      ████   ██ ██    ██ ██ ██   ██ ██    ██ ████   ██ ████  ████ ██      ████   ██    ██    
            █████   ██ ██  ██ ██    ██ ██ ██████  ██    ██ ██ ██  ██ ██ ████ ██ █████   ██ ██  ██    ██    
            ██      ██  ██ ██  ██  ██  ██ ██   ██ ██    ██ ██  ██ ██ ██  ██  ██ ██      ██  ██ ██    ██    
            ███████ ██   ████   ████   ██ ██   ██  ██████  ██   ████ ██      ██ ███████ ██   ████    ██    
                                                                                                                                                                                                                 
            ██    ██  █████  ██████  ██  █████  ██████  ██      ███████                                    
            ██    ██ ██   ██ ██   ██ ██ ██   ██ ██   ██ ██      ██                                         
            ██    ██ ███████ ██████  ██ ███████ ██████  ██      █████                                      
             ██  ██  ██   ██ ██   ██ ██ ██   ██ ██   ██ ██      ██                                         
              ████   ██   ██ ██   ██ ██ ██   ██ ██████  ███████ ███████
              
            ███████ pls see wiki ███████
        """.trimIndent()
		println()
		println(s)
		exitProcess(1)
	}

	System.getenv("TOKEN")?.let {
		JDABuilder.createDefault(it)
				.addEventListeners(Ready)
				.build()
	} ?: run {
		val s = """
            ██████  ██      ███████     ███████ ███    ██ ████████ ███████ ██████      ██    ██  ██████  ██    ██ ██████      
            ██   ██ ██      ██          ██      ████   ██    ██    ██      ██   ██      ██  ██  ██    ██ ██    ██ ██   ██     
            ██████  ██      ███████     █████   ██ ██  ██    ██    █████   ██████        ████   ██    ██ ██    ██ ██████      
            ██      ██           ██     ██      ██  ██ ██    ██    ██      ██   ██        ██    ██    ██ ██    ██ ██   ██     
            ██      ███████ ███████     ███████ ██   ████    ██    ███████ ██   ██        ██     ██████   ██████  ██   ██                                                                                                                  
                                                                                                                              
            ██████  ██ ███████  ██████  ██████  ██████  ██████      ████████  ██████  ██   ██ ███████ ███    ██               
            ██   ██ ██ ██      ██      ██    ██ ██   ██ ██   ██        ██    ██    ██ ██  ██  ██      ████   ██               
            ██   ██ ██ ███████ ██      ██    ██ ██████  ██   ██        ██    ██    ██ █████   █████   ██ ██  ██               
            ██   ██ ██      ██ ██      ██    ██ ██   ██ ██   ██        ██    ██    ██ ██  ██  ██      ██  ██ ██               
            ██████  ██ ███████  ██████  ██████  ██   ██ ██████         ██     ██████  ██   ██ ███████ ██   ████     
                      
            ██ ███    ██                                                                                   
            ██ ████   ██                                                                                   
            ██ ██ ██  ██                                                                                   
            ██ ██  ██ ██                                                                                   
            ██ ██   ████                                                                                                                                                                                   
                                                                                                           
            ███████ ███    ██ ██    ██ ██ ██████   ██████  ███    ██ ███    ███ ███████ ███    ██ ████████ 
            ██      ████   ██ ██    ██ ██ ██   ██ ██    ██ ████   ██ ████  ████ ██      ████   ██    ██    
            █████   ██ ██  ██ ██    ██ ██ ██████  ██    ██ ██ ██  ██ ██ ████ ██ █████   ██ ██  ██    ██    
            ██      ██  ██ ██  ██  ██  ██ ██   ██ ██    ██ ██  ██ ██ ██  ██  ██ ██      ██  ██ ██    ██    
            ███████ ██   ████   ████   ██ ██   ██  ██████  ██   ████ ██      ██ ███████ ██   ████    ██                                                                                                   
                                                                                                           
            ██    ██  █████  ██████  ██  █████  ██████  ██      ███████                                    
            ██    ██ ██   ██ ██   ██ ██ ██   ██ ██   ██ ██      ██                                         
            ██    ██ ███████ ██████  ██ ███████ ██████  ██      █████                                      
             ██  ██  ██   ██ ██   ██ ██ ██   ██ ██   ██ ██      ██                                         
              ████   ██   ██ ██   ██ ██ ██   ██ ██████  ███████ ███████
              
            ███████ pls see wiki ███████
                                                                                                                                                                          
        """.trimIndent()
		println()
		println(s)
	}


}