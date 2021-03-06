package io.github.bloodnighttw.whateverBot.arknights

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import io.github.bloodnighttw.whateverBot.utils.arknights.CharacterInfo
import io.github.bloodnighttw.whateverBot.utils.arknights.Position
import io.github.bloodnighttw.whateverBot.utils.arknights.Profession
import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister
import io.github.bloodnighttw.whateverBot.utils.extensions.contains
import net.dv8tion.jda.api.JDA


lateinit var characterMap: Map<String, CharacterInfo>

val recruitCharacterMap:MutableMap<String,MutableSet<CharacterInfo>> = mutableMapOf()


fun MutableMap<String,MutableSet<CharacterInfo>>.add(key:String,characterInfo:CharacterInfo) =
	recruitCharacterMap[key]?.add(characterInfo) ?: run{
	recruitCharacterMap.put(key, mutableSetOf(characterInfo))
}

fun loadArknights(bot:JDA, register: CommandRegister?){
	register?.registerCommand(Arknights)

	loadCharacterMap()
	loadRecruitCharacterMap()


	for( guild in bot.guilds){
		val roleMiss = recruitCharacterMap.keys.filter { !
		guild.roles.contains(it)}
		roleMiss.forEach{
			println(it)
			guild.createRole()
				.setName(it)
				.queue()
		}
	}



}

fun loadCharacterMap(){

	val json = Thread.currentThread().contextClassLoader.getResource("arknights/zh_TW/gamedata/excel/character_table.json")?.readText()!!
		.replace(regex = "<([@a-z/]+) *[^/]*?>".toRegex(),"**")
	val objectMapper = ObjectMapper()
	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

	characterMap = objectMapper.readValue<Map<String, CharacterInfo>?>(json,
		TypeFactory.defaultInstance().constructMapType(Map::class.java,String::class.java,
		CharacterInfo::class.java)).filter { it.value.position != Position.NONE }.map {
				it.value.name to it.value
		}.toMap()

	characterMap.forEach {
		println("${it.key} ${it.value}")
	}
}

fun loadRecruitCharacterMap(){
	val json = Thread.currentThread().contextClassLoader.getResource("arknights/zh_TW/gamedata/excel/gacha_table.json")?.readText()!!
		.replace(regex = "<([@a-z/]+) *[^/]*?>".toRegex(),"")
	val objectMapper = ObjectMapper()

	val map = objectMapper.readValue(json,Map::class.java)

	val info = map["recruitDetail"] as String
	val list = info.replaceBefore("\n???","").split("\n--------------------\n").map {
		it.substringAfterLast("???\n").split(" / ")
	}.toList()

	val pro = "????????????"
	val proMax = "??????????????????"

	for( agents in list){

		for ( i in agents){
			val characterInfo = characterMap[i]!!

			// add with tags
			for( tag in characterInfo.tags ?: emptyArray() ){
				recruitCharacterMap.add(tag,characterInfo)
			}

			// add with Position

			when(characterInfo.position){

				Position.RANGED -> recruitCharacterMap.add("?????????",characterInfo)
				Position.MELEE -> recruitCharacterMap.add("?????????",characterInfo)
				Position.ALL -> run {
					recruitCharacterMap.add("?????????",characterInfo)
					recruitCharacterMap.add("?????????",characterInfo)
				}

				else -> {}
			}

			val professionZhant = when(characterInfo.profession){
				Profession.CASTER -> "??????"
				Profession.MEDIC -> "??????"
				Profession.WARRIOR -> "??????"
				Profession.SPECIAL -> "??????"
				Profession.PIONEER -> "??????"
				Profession.TANK -> "??????"
				Profession.SNIPER -> "??????"
				Profession.SUPPORT -> "??????"
				Profession.TOKEN -> "?????????"
				Profession.TRAP -> "??????"
			}

			recruitCharacterMap.add(professionZhant,characterInfo)

			if(characterInfo.rarity == 4.toByte()){
				recruitCharacterMap.add(pro,characterInfo)
			}

			if(characterInfo.rarity == 5.toByte()){
				recruitCharacterMap.add(proMax,characterInfo)
			}

		}
	}

	recruitCharacterMap.keys.forEach{
		println(it)
	}

}