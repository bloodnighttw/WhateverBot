import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.EventListener

object ClearAllCommand : EventListener {
	override fun onEvent(event: GenericEvent) {
		if (event is ReadyEvent) {
			for (i in event.jda.guilds) {
				i.updateCommands().queue()
			}
		}
	}

}

fun main() {
	JDABuilder.createDefault(System.getenv("TOKEN") ?: "").build().addEventListener(ClearAllCommand)
}