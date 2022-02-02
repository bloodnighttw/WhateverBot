package io.github.bloodnighttw.whateverBot.osu

import io.github.bloodnighttw.whateverBot.osu.OsuUserLink.discordUserId
import io.github.bloodnighttw.whateverBot.utils.callback.onResponse
import io.github.bloodnighttw.whateverBot.utils.command.ISubCommand
import io.github.bloodnighttw.whateverOsu.authorize.getToken
import io.github.bloodnighttw.whateverOsu.userService
import io.github.bloodnighttw.whateverOsu.utils.OsuMode
import io.github.bloodnighttw.whateverOsu.utils.ScoreType
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object SetUser : ISubCommand {
	override val subCommand: SubcommandData
		get() = SubcommandData("setuser", "set user name")
			.addOption(OptionType.STRING, "username", "your osu user name", true)

	override fun subCommandHandler(slashCommandEvent: SlashCommandEvent) {
		val username = slashCommandEvent.getOption("username")!!.asString
		val token = getToken(OCI, OCS)
		if (token == null) {
			slashCommandEvent.hook.sendMessage("Cannot get Token,pls contact author BLOODNIGHTTW#9487 and try later")
				.queue()
			return
		}
		userService.getUser(token, username, OsuMode.OSU)
			.onResponse { _, response ->
				if (response.isSuccessful) {

					if (OsuUserLink.exist(slashCommandEvent.user.id)) {
						transaction {
							OsuUserLink.update({ discordUserId eq slashCommandEvent.user.id }) { data ->
								data[osuUserId] = response.body()!!.id
							}
						}
						slashCommandEvent.hook.sendMessage("Added Successfully").queue()
					} else {
						transaction {
							OsuUserLink.insert { data ->
								data[osuUserId] = response.body()!!.id
								data[discordUserId] = slashCommandEvent.user.id
							}
							slashCommandEvent.hook.sendMessage("Update Successfully").queue()
						}
					}

				} else
					slashCommandEvent.hook.sendMessage("Can't find your userid with your username").queue()
			}
			.onFailure { _, t ->
				slashCommandEvent.hook.sendMessage("Fail due to $t").queue()
			}
			.run()

	}
}

private val OCI = System.getenv("OCI")!!
private val OCS = System.getenv("OCS")!!

object Recent : ISubCommand {
	override val subCommand: SubcommandData
		get() = SubcommandData("recent", "recent play")

	override fun subCommandHandler(slashCommandEvent: SlashCommandEvent) {
		val accessToken = getToken(OCI, OCS)!!
		if (OsuUserLink.exist(slashCommandEvent.user.id)) {
			val username = OsuUserLink.getOsuUsername(slashCommandEvent.user.id)
			userService.getUserScores(accessToken, username.toString(), ScoreType.Recent)
				.onResponse { _, response ->
					if (response.isSuccessful) {

						response.body()?.let {
							if (it.isEmpty()) {
								slashCommandEvent.hook.sendMessage("You don't play any game recently").queue()
							} else
								slashCommandEvent.hook.sendMessage(it[0].toString()).queue()
						}

					} else {
						slashCommandEvent.hook.sendMessage("error ${response.code()}")
					}
				}
				.onFailure { _, t ->
					slashCommandEvent.hook.sendMessage("Cannot find the result due to $t").queue()
				}
				.run()
		} else {
			slashCommandEvent.hook.sendMessage("Please use /osu setuser <username> to use this function").queue()
		}
	}

}