package io.github.bloodnighttw.whateverBot.musicBot.eventHandler

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.TextChannel

class TrackLoadResultHandler(private val channel: TextChannel, private val trackScheduler: TrackScheduler) :
	AudioLoadResultHandler {

	override fun trackLoaded(track: AudioTrack) {
		channel.sendMessage("hi1").queue()
		trackScheduler.play(track)
	}

	override fun playlistLoaded(playlist: AudioPlaylist) {
		channel.sendMessage("hi2").queue()
		for (i in playlist.tracks) {
			trackScheduler.play(i)
		}
	}

	override fun noMatches() {
		channel.sendMessage("h3").queue()
	}

	override fun loadFailed(exception: FriendlyException?) {
		channel.sendMessage("hi4").queue()
	}

}