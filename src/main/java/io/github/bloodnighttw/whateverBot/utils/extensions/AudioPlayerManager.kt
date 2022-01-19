package io.github.bloodnighttw.whateverBot.utils.extensions

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack


private class Nothing(val a: AudioLoadResultHandler) : AudioLoadResultHandler {
	override fun trackLoaded(track: AudioTrack) {
		a.trackLoaded(track)
	}

	override fun playlistLoaded(playlist: AudioPlaylist) {
		a.trackLoaded(playlist.tracks[0])
	}

	override fun noMatches() {
		a.noMatches()
	}

	override fun loadFailed(exception: FriendlyException?) {
		a.loadFailed(exception)
	}
}

fun AudioPlayerManager.loadSingle(identifier: String, audioLoadResultHandler: AudioLoadResultHandler) {
	this.loadItem(identifier, Nothing(audioLoadResultHandler))
}