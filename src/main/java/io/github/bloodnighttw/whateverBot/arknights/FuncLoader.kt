package io.github.bloodnighttw.whateverBot.arknights

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import io.github.bloodnighttw.whateverBot.commandRegister
import io.github.bloodnighttw.whateverBot.utils.arknights.CharacterInfo
import io.github.bloodnighttw.whateverBot.utils.arknights.Position
import io.github.bloodnighttw.whateverBot.utils.arknights.Profession
import io.github.bloodnighttw.whateverBot.utils.command.CommandRegister
import io.github.bloodnighttw.whateverBot.utils.extensions.contains
import net.dv8tion.jda.api.JDA


lateinit var characterMap: Map<String, CharacterInfo>

val recruitCharacterMap:MutableMap<String,MutableSet<CharacterInfo>> = mutableMapOf()


fun MutableMap<String,MutableSet<CharacterInfo>>.add(key:String,characterInfo:CharacterInfo) =
	recruitCharacterMap[key]?.let {
		it.add(characterInfo)
} ?: run{
	recruitCharacterMap.put(key, mutableSetOf(characterInfo))
}

fun loadArknights(bot:JDA, register: CommandRegister?){
	commandRegister?.registerCommand(Arknights)

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
	val list = info.replaceBefore("\n★","").split("\n--------------------\n").map {
		it.substringAfterLast("★\n").split(" / ")
	}.toList()

	val pro = "資深幹員"
	val proMax = "高級資深幹員"

	for( (index,agents) in list.withIndex() ){

		for ( i in agents){
			val characterInfo = characterMap[i]!!

			// add with tags
			for( tag in characterInfo.tags ?: emptyArray() ){
				recruitCharacterMap.add(tag,characterInfo)
			}

			// add with Position

			when(characterInfo.position){

				Position.RANGED -> recruitCharacterMap.add("遠程位",characterInfo)
				Position.MELEE -> recruitCharacterMap.add("近戰位",characterInfo)
				Position.ALL -> run {
					recruitCharacterMap.add("遠程位",characterInfo)
					recruitCharacterMap.add("近戰位",characterInfo)
				}

				else -> {}
			}

			val professionZhant = when(characterInfo.profession){
				Profession.CASTER -> "法師"
				Profession.MEDIC -> "醫療"
				Profession.WARRIOR -> "近衛"
				Profession.SPECIAL -> "特種"
				Profession.PIONEER -> "先鋒"
				Profession.TANK -> "重裝"
				Profession.SNIPER -> "狙擊"
				Profession.SUPPORT -> "輔助"
				Profession.TOKEN -> "召喚物"
				Profession.TRAP -> "陷阱"
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