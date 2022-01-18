package io.github.bloodnighttw.whateverBot.utils.command


import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import org.slf4j.LoggerFactory

class CommandRegister(private val bot: JDA) {
	private val logger = LoggerFactory.getLogger(CommandRegister::class.java)
	private val commandDataLocalList: ArrayList<CommandData> = ArrayList()
	private val commandDataGlobalList: ArrayList<CommandData> = ArrayList()

	private fun register(iCommand: ICommand) {
		if (iCommand.isGlobal) {
			commandDataLocalList.add(iCommand.command)
			for (alias in iCommand.alias) {
				commandDataLocalList.add(CommandData(alias, iCommand.command.description))
			}
		} else {
			commandDataGlobalList.add(iCommand.command)
			for (alias in iCommand.alias) {
				commandDataGlobalList.add(CommandData(alias, iCommand.command.description))
			}
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
					.addCommands(commandDataGlobalList)
					.queue()
		}
	}

}