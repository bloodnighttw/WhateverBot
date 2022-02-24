@file:JvmName(name = "WhateverBot")

package io.github.bloodnighttw.whateverBot

import ch.qos.logback.classic.Level
import io.github.bloodnighttw.whateverBot.arknights.loadArknights
import io.github.bloodnighttw.whateverBot.codeWrapper.MessageHandler
import io.github.bloodnighttw.whateverBot.musicBot.loadMusicBotFunc
import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister
import io.github.bloodnighttw.whateverBot.voiceChannelCreator.voiceChannelCreatorLoad
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.jetbrains.exposed.sql.Database
import org.slf4j.LoggerFactory
import kotlin.system.exitProcess


fun load(bot: JDA) {
	val commandRegister = CommandRegister(bot)
	bot.addEventListener(MessageHandler())
	voiceChannelCreatorLoad(bot, commandRegister)
	loadMusicBotFunc(bot, commandRegister)
	loadArknights(bot, commandRegister)
	commandRegister.addToAllServer()
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
		val bot = JDABuilder.createDefault(it)
				.build().awaitReady()
		load(bot)
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