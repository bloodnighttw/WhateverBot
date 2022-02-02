package io.github.bloodnighttw.whateverBot.utils.command


import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import org.slf4j.LoggerFactory

class CommandRegister(private val bot: JDA) {
	private val logger = LoggerFactory.getLogger(CommandRegister::class.java)
	private val commandDataLocalList: ArrayList<CommandData> = ArrayList()
	private val commandDataGlobalList: ArrayList<CommandData> = ArrayList()

	private fun register(iCommand: ICommand) {
		var tempCommandData = iCommand.command
		val tempDataList: ArrayList<CommandData> =
			if (iCommand.isGlobal) commandDataGlobalList else commandDataLocalList

		val allSubCommand = iCommand.subCommandMap.values.map { it.subCommand }
		tempCommandData = tempCommandData.addSubcommands(allSubCommand)

		tempDataList.add(tempCommandData)

		for (alias in iCommand.alias) {
			tempCommandData =
				CommandData(alias, tempCommandData.description).addSubcommands(allSubCommand)
			tempDataList.add(tempCommandData)
		}

	}

	fun registerCommand(iCommand: ICommand) {
		register(iCommand)
		bot.addEventListener(CommandHandler(iCommand))
	}

	fun registerPrivateCommand(iCommand: ICommand) {
		register(iCommand)
		bot.addEventListener(CommandHandler(iCommand, true))
	}

	fun addToAllServer() {
		bot.updateCommands().addCommands(commandDataGlobalList).queue()
		commandDataLocalList.forEach { logger.debug("Local Command " + it.name) }
		commandDataGlobalList.forEach { logger.debug("Global Command " + it.name) }
		for (guild in bot.guilds) {
			logger.debug("Guild Found Name:" + guild.name)
			logger.debug("Add command to server: " + guild.name)
			guild.updateCommands()
				.addCommands(commandDataLocalList)
				.addCommands(commandDataGlobalList)
				.queue()
		}
	}

}