package io.github.bloodnighttw.whateverBot.utils.extensions

import net.dv8tion.jda.api.entities.Role

fun List<Role>.contains(roleName:String): Boolean {
	for( i in this){
		if(i.name == roleName)
			return true
	}
	return false
}