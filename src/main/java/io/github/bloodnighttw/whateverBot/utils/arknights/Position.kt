package io.github.bloodnighttw.whateverBot.utils.arknights

import com.fasterxml.jackson.annotation.JsonProperty

enum class Position {

	@JsonProperty("RANGED") RANGED,
	@JsonProperty("MELEE") MELEE,
	@JsonProperty("ALL") ALL,
	@JsonProperty("NONE") NONE,

}