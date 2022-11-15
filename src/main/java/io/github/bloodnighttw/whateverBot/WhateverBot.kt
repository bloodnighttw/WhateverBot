@file:JvmName(name = "WhateverBot")

package io.github.bloodnighttw.whateverBot

import ch.qos.logback.classic.Level
import io.github.bloodnighttw.whateverBot.codeWrapper.MessageHandler
import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.slf4j.LoggerFactory
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.InputStream
import kotlin.system.exitProcess


fun load(bot: JDA) {
	val commandRegister = CommandRegister(bot)
	bot.addEventListener(MessageHandler())
	commandRegister.addToAllServer()
}

fun main(args: Array<String>) {

	var configFileLocation = "./config.yaml"

	for (i in args.indices) {
		when (args[i]) {
			"-d", "--debug" -> run {
				val root = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME) as ch.qos.logback.classic.Logger
				root.level = Level.DEBUG
			}

			"-c", "--config" -> {
				configFileLocation = args[i+1];
			}

		}
	}

	System.getenv("TOKEN")?.let {
		val bot = JDABuilder.createDefault(it)
				.build().awaitReady()
		load(bot)
	} ?: run {
		val s = "Please Enter Your Discord Bot Token in config.yaml"
		println()
		println(s)
	}



}