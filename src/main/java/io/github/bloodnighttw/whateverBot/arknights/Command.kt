package io.github.bloodnighttw.whateverBot.arknights

import io.github.bloodnighttw.whateverBot.utils.arknights.CharacterInfo
import io.github.bloodnighttw.whateverBot.utils.command.ICommand
import io.github.bloodnighttw.whateverBot.utils.command.ISubCommand
import io.github.bloodnighttw.whateverBot.utils.extensions.isAllNull
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import java.util.concurrent.TimeUnit

object Arknights:ICommand{
	override val command: SlashCommandData
		get() = Commands.slash("arknights","arknights related command")

	override val subCommandMap: Map<String, ISubCommand>
		get() = mapOf(
			"recruit" to Recruit
		)
}

object Recruit:ISubCommand{
	override val subCommand: SubcommandData
		get() = SubcommandData("recruit","recruit calc")
			.addOption(OptionType.ROLE,"slot1","slot 1")
			.addOption(OptionType.ROLE,"slot2","slot 2")
			.addOption(OptionType.ROLE,"slot3","slot 3")
			.addOption(OptionType.ROLE,"slot4","slot 4")
			.addOption(OptionType.ROLE,"slot5","slot 5")


	override fun subCommandHandler(slashCommandEvent: SlashCommandInteractionEvent) {

		slashCommandEvent.hook.sendMessage("正在計算中").queue()


		val slot = MutableList<String?>(5){
			slashCommandEvent.getOption("slot${it+1}")?.asRole?.name
		}

		val set = MutableList<MutableSet<CharacterInfo>?>(5){
			recruitCharacterMap[slot[it]]
		}


		if(set.isAllNull())
			return

		for( i in 0..4){
			if(set[i] == null)
				continue

			set[i]?.let { characterInfoSet ->
				slashCommandEvent.channel.sendMessage(
						"------------------------------------------------------------\n" +
						":mag_right:**以下為公開招募 tag: ${slot[i]} 可能出現的結果**\n" +
						"------------------------------------------------------------\n" +
						characterInfoSet.joinToString(separator = " | "){it.name}
				).queue()
			}

		}

		for( i in 0..4){
			if(set[i] == null)
				continue

			for ( j in i+1..4){
				if(set[j] == null)
					continue

				val result = set[i]!! intersect set[j]!!

				if(result.isEmpty())
					continue

				slashCommandEvent.channel.sendMessage(
					"------------------------------------------------------------\n" +
							":mag_right:**以下為公開招募 tag: ${slot[i]},${slot[j]} 可能出現的結果**\n" +
							"------------------------------------------------------------\n" +
							result.joinToString(separator = " | "){it.name}
				).queue()

			}

		}


		for( i in 0..4){
			if(set[i] == null)
				continue

			for ( j in i+1..4){
				if(set[j] == null)
					continue

				for( k in j+1 ..4){
					if(set[k] == null)
						continue

					val result = set[i]!! intersect set[j]!! intersect set[k]!!

					if(result.isEmpty())
						continue

					slashCommandEvent.channel.sendMessage(
						"------------------------------------------------------------\n" +
								":mag_right:**以下為公開招募 tag: ${slot[i]},${slot[j]},${slot[k]} 可能出現的結果**\n" +
								"------------------------------------------------------------\n"+
								result.joinToString(separator = " | "){it.name}
					).queue()
				}


			}

		}

		slashCommandEvent.hook.deleteOriginal().queueAfter(1,TimeUnit.SECONDS)

	}

}

