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
	var loopQueue = false


	init {
		queue = LinkedBlockingQueue()
	}

	fun play(track: AudioTrack) {

		if (!player.startTrack(track, true)) {
			queue.offer(track)
		}

	}

	fun nextTrack() {
		val temp = queue.poll()
		if (loopQueue)
			queue.put(temp.makeClone())
		player.startTrack(temp, false)
	}

	override fun onTrackEnd(player: AudioPlayer, track: AudioTrack, endReason: AudioTrackEndReason) {
		logger.debug(endReason.toString())
		if (endReason.mayStartNext) {
			this.nextTrack()
		}
	}
}