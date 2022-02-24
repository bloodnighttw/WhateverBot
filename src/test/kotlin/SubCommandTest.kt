import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister
import io.github.bloodnighttw.whateverBot.utils.command.ICommand
import io.github.bloodnighttw.whateverBot.utils.command.ISubCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData

object Sub : ISubCommand {

	override val subCommand: SubcommandData
		get() = SubcommandData("test", "nothing")

	override fun subCommandHandler(slashCommandEvent: SlashCommandEvent) {
		slashCommandEvent.hook.sendMessage("hi").queue()
	}

}

object Command : ICommand {

	override val alias: Array<String>
		get() = arrayOf("ac")

	override val command: CommandData
		get() = CommandData("t", "nothn")
	override val subCommandMap: Map<String, ISubCommand>
		get() = mapOf(
			"test" to Sub
		)
}

object Ready : EventListener {
	override fun onEvent(event: GenericEvent) {
		if (event is ReadyEvent) {
			load(event.jda)
		}
	}
}

fun load(bot: JDA) {
	val commandRegister = CommandRegister(bot)
	commandRegister.registerCommand(Command)
	commandRegister.addToAllServer()
}


fun main() {
	JDABuilder.createDefault(System.getenv("TOKEN")).addEventListeners(Ready).build()
}