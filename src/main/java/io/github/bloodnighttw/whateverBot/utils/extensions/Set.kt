package io.github.bloodnighttw.whateverBot.utils.extensions

fun <E> List<E>.isAllNull(): Boolean {
	for( i in this){
		if(i!=null)
			return false
	}
	return true
}