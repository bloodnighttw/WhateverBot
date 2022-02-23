package io.github.bloodnighttw.whateverBot.utils.arknights

import com.fasterxml.jackson.annotation.JsonProperty


data class CharacterInfo(
	@JsonProperty("name") val name:String,
	@JsonProperty("description")val description: String?,
	@JsonProperty("tagList") val tags:Array<String>?,
	@JsonProperty("position") val position: Position,
	@JsonProperty("profession") val profession:Profession,
	@JsonProperty("rarity") val rarity:Byte,
	) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as CharacterInfo

		if (name != other.name) return false
		if (description != other.description) return false
		if (tags != null) {
			if (other.tags == null) return false
			if (!tags.contentEquals(other.tags)) return false
		} else if (other.tags != null) return false
		if (position != other.position) return false
		if (profession != other.profession) return false
		if (rarity != other.rarity) return false

		return true
	}

	override fun hashCode(): Int {
		var result = name.hashCode()
		result = 31 * result + (description?.hashCode() ?: 0)
		result = 31 * result + (tags?.contentHashCode() ?: 0)
		result = 31 * result + position.hashCode()
		result = 31 * result + profession.hashCode()
		result = 31 * result + rarity
		return result
	}
}