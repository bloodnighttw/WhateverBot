package io.github.bloodnighttw.whateverBot.utils.command


import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import org.slf4j.LoggerFactory

class CommandRegister(private val bot: JDA) {
	private val logger = LoggerFactory.getLogger(CommandRegister::class.java)
	private val commandDataLocalList: ArrayList<CommandData> = ArrayList()
	private val commandDataGlobalList: ArrayList<CommandData> = ArrayList()

	private fun register(iCommand: ICommand) {
		var tempCommandData = iCommand.command
		val tempDataList: ArrayList<CommandData> =
			if (iCommand.isGlobal) commandDataGlobalList else commandDataLocalList

		tempCommandData = tempCommandData.addSubcommands(iCommand.subCommandMap.values.map { it.subCommand })
		logger.debug("add ${if(tempDataList === commandDataGlobalList) "GLOBAL" else "LOCAL"} command for ${iCommand.command.name}")
		tempDataList.add(tempCommandData)
		for (alias in iCommand.alias) {
			tempCommandData =
				Commands.slash(alias, tempCommandData.description).addSubcommands(tempCommandData.subcommands)
			tempDataList.add(tempCommandData)
			logger.debug("add ${if(tempDataList === commandDataGlobalList) "GLOBAL" else "LOCAL"} command for ${iCommand.command.name}")
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
		for (guild in bot.guilds) {
			logger.debug("Guild Found Name:" + guild.name)
			logger.debug("Add command to server: " + guild.name)
			guild.updateCommands()
					.addCommands(commandDataLocalList)
					//.addCommands(commandDataGlobalList)
					.queue()
		}
	}

}