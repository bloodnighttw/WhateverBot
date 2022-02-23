import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import io.github.bloodnighttw.whateverBot.utils.arknights.CharacterInfo
import org.junit.jupiter.api.Test

internal class Arknights {

	@Test
	fun parseJson(){
		val json = object {}.javaClass.getResource("arknights/zh_TW/gamedata/excel/character_table.json")?.readText()
		val objectMapper = ObjectMapper()
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

		val map:Map<String, CharacterInfo> = objectMapper.readValue(json!!,
			TypeFactory.defaultInstance().constructMapType(
				Map::class.java,String::class.java, CharacterInfo::class.java
			))
		map.forEach{ it ->
			println(it.value.name)
			for( i in it.value.tags ?: arrayOf()){
				println("$i,")
			}
			println(it.value.description?.replace("<([@a-z/]+) *[^/]*?>".toRegex(),"**"))
			println(it.value.position)
			println(it.value.profession)
		}

	}

	@Test
	fun parseRecruit(){
		val json = object {}.javaClass.getResource("arknights/zh_TW/gamedata/excel/gacha_table.json")?.readText()!!
			.replace(regex = "<([@a-z/]+) *[^/]*?>".toRegex(),"")
		val objectMapper = ObjectMapper()

		val map = objectMapper.readValue(json,Map::class.java)

		val info = map["recruitDetail"] as String
		println(info.replaceBefore("\n★","").split("\n--------------------\n").map {
			it.substringAfterLast("★\n").split(" / ")
		}.toList())

	}

}