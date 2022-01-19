package io.github.bloodnighttw.whateverBot.musicBot

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import io.github.bloodnighttw.whateverBot.musicBot.eventHandler.TrackLoadResultHandler
import io.github.bloodnighttw.whateverBot.musicBot.eventHandler.TrackScheduler
import io.github.bloodnighttw.whateverBot.utils.extensions.loadSingle
import io.github.bloodnighttw.whateverBot.utils.music.AudioSendHandler
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.VoiceChannel

val allAvailableJDAList = mutableListOf<JDA>()

private val guildMap = HashMap<String, GuildMusicProvider>()

fun getMusicProviderByGuild(guild: Guild, voiceChannel: VoiceChannel): GuildMusicProvider {
	val a: GuildMusicProvider? = guildMap[guild.id]

	return if (a == null) {
		guildMap[guild.id] = GuildMusicProvider(guild.jda, guild, voiceChannel)
		guildMap[guild.id]!!
	} else a
}

fun removeMusicProviderByGuildId(id: String) {
	guildMap.remove(id)
}

class GuildMusicProvider(private val bot: JDA, guild: Guild, voiceChannel: VoiceChannel) {

	private val musicManager: AudioPlayerManager = DefaultAudioPlayerManager()
	private val player: AudioPlayer = musicManager.createPlayer()
	private val trackScheduler = TrackScheduler(player)

	init {
		AudioSourceManagers.registerRemoteSources(musicManager)
		guild.audioManager.sendingHandler = AudioSendHandler(player)
		player.addListener(trackScheduler)
		guild.audioManager.openAudioConnection(voiceChannel)

	}

	fun search(str: String) {
		musicManager.loadSingle(
			"ytsearch:$str",
			TrackLoadResultHandler(bot.getTextChannelById("924200786924281866")!!, trackScheduler)
		)
	}

	fun loadUrl(url: String) {
		musicManager.loadItem(
			url,
			TrackLoadResultHandler(bot.getTextChannelById("924200786924281866")!!, trackScheduler)
		)
	}

	fun pause() {
		player.isPaused = true

	}

	fun resume() {
		player.isPaused = false
	}

	fun next() {
		TODO("Next not yet implement")
	}

	fun enableLoop() {
		TODO("Loop not yet implement")
	}

}