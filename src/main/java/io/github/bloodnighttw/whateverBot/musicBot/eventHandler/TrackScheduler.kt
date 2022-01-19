package io.github.bloodnighttw.whateverBot.musicBot.eventHandler

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue


class TrackScheduler(private val player: AudioPlayer) :
	AudioEventAdapter() {
	private val queue: BlockingQueue<AudioTrack>
	private val logger: Logger = LoggerFactory.getLogger(this.javaClass)


	init {
		queue = LinkedBlockingQueue()
	}

	fun queue(track: AudioTrack) {
		// Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
		// something is playing, it returns false and does nothing. In that case the player was already playing so this
		// track goes to the queue instead.

		if (!player.startTrack(track, true)) {
			queue.offer(track)
		}
	}


	fun nextTrack() {

		queue.forEach {
			logger.debug("hello " + it.info.title)
		}
		player.startTrack(queue.poll(), true)
	}

	override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
		// Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
		logger.debug(endReason.toString())
		if (endReason.mayStartNext) {
			this.nextTrack()
		}
	}
}